package com.example.tiketku_finalproject.Controller;

import com.example.tiketku_finalproject.Model.HistoryTransactionEntity;
import com.example.tiketku_finalproject.Model.SchedulesEntity;
import com.example.tiketku_finalproject.Model.TempTransactionEntity;
import com.example.tiketku_finalproject.Response.*;
import com.example.tiketku_finalproject.Service.HistoryTransactionService;
import com.example.tiketku_finalproject.Service.SchedulesService;
import com.example.tiketku_finalproject.Service.TempTransactionService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value ="/TempTransacation")
@Api(value = "TempTransacation")
public class TempTransactionController {

    @Autowired
    TempTransactionService tempTransactionService;
    @Autowired
    CommonResponseGenerator commonResponseGenerator;
    @Autowired
    SchedulesService schedulesService;
    @Autowired
    HistoryTransactionService historyTransactionService;


    @PostMapping(value = "/addTempTransaction")
    @Operation(description = "Add Transaction")
    public CommonResponse<List<TempTransactionEntity>> addTransaction(@RequestBody List<TempAddTransactionResponse> param) {
        try {
            List<TempTransactionEntity> transactionEntities = new ArrayList<>();
            List<SchedulesEntity> schedulesEntities = new ArrayList<>();

            for (TempAddTransactionResponse tempAddTransaction : param) {
                TempTransactionEntity tempTransaction = new TempTransactionEntity();
                tempTransaction.setUuid_user(tempAddTransaction.getUuid_user());
                tempTransaction.setUuid_schedules(tempAddTransaction.getSchedule_uid());
                tempTransaction.setSeats_id(tempAddTransaction.getSeats_id());
                transactionEntities.add(tempTransaction);

                Optional<SchedulesEntity> optionalSchedulesEntity = schedulesService.getByUuidSchedules(tempAddTransaction.getSchedule_uid());

                if (optionalSchedulesEntity.isPresent()) {
                    SchedulesEntity schedulesEntity = optionalSchedulesEntity.get();
                    int updatedLimits = schedulesEntity.getLimits() - 1;

                    if (updatedLimits < 0) {
                        String message = "Limit exceeded. Data not saved for TempTransaction with UUID: " + tempAddTransaction.getSchedule_uid();
                        log.info(message);
                        throw new Exception(message);
                    } else {
                        schedulesEntity.setLimits(updatedLimits);
                        schedulesEntities.add(schedulesEntity);
                        log.info(String.valueOf(schedulesEntities));

                        // Create and save the TempTransactionEntity
                        List<TempTransactionEntity> savedTempTransactions = tempTransactionService.addTransaction(Collections.singletonList(tempTransaction));
                        TempTransactionEntity savedTempTransaction = savedTempTransactions.get(0);
                        tempTransaction.setUuid_transaction(savedTempTransaction.getUuid_transaction());
                    }
                }

            }

            List<HistoryTransactionEntity> historyTransactions = new ArrayList<>();
            for (TempTransactionEntity savedTransaction : transactionEntities) {
                HistoryTransactionEntity historyTransaction = new HistoryTransactionEntity();
                historyTransaction.setUuid_history(savedTransaction.getUuid_transaction());
                historyTransactions.add(historyTransaction);
            }
            List<HistoryTransactionEntity> savedHistory = historyTransactionService.saveDataHistory(historyTransactions);

            // Save schedulesEntities only if there are valid data
            if (!schedulesEntities.isEmpty()) {
                List<SchedulesEntity> savedLimitDown = schedulesService.saveDataLimit(schedulesEntities);

                // Remove TempTransactionEntity entries for which the limit was exceeded
                transactionEntities.removeIf(tempTransaction -> {
                    for (SchedulesEntity schedulesEntity : savedLimitDown) {
                        if (tempTransaction.getUuid_schedules().equals(schedulesEntity.getUuid_schedules())) {
                            return false;
                        }
                    }
                    return true;
                });
            }

            log.info(String.valueOf(transactionEntities));
            return commonResponseGenerator.succsesResponse(transactionEntities, "Sukses Menambahkan Data");
        } catch (Exception e) {
            log.warn(String.valueOf(e));
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }

    @PutMapping(value = "/checkout")
    @Operation(description = "Checkout Transaction")
    public CommonResponse<TempTransactionEntity> checkoutTransaction(@RequestBody CheckoutTransactionResponse param){
        try {
            TempTransactionEntity tempTransaction = new TempTransactionEntity();
            tempTransaction.setUuid_transaction(param.getUuid_transaction());
            tempTransaction.setTitle(param.getTitle());
            tempTransaction.setFull_name(param.getFull_name());
            tempTransaction.setGiven_name(param.getGiven_name());
            tempTransaction.setBirth_date(param.getBirth_date());
            tempTransaction.setId_card(param.getId_card());
            TempTransactionEntity tempTransactionEntity = tempTransactionService.updateTempData(tempTransaction);

            HistoryTransactionEntity historyTransaction = new HistoryTransactionEntity();
            historyTransaction.setUuid_history(tempTransactionEntity.getUuid_transaction());
            log.info(String.valueOf(historyTransaction));
            HistoryTransactionEntity saveHistory = historyTransactionService.updateDataHistory(historyTransaction);

            log.info(String.valueOf(tempTransaction));
            log.info(String.valueOf(tempTransactionEntity), "Successfully updated " + param.getFull_name());
            return commonResponseGenerator.succsesResponse(tempTransactionEntity, "Successfully updated " + param.getUuid_transaction());
        }catch (Exception e){
            log.warn(String.valueOf(e));
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }


    @PutMapping(value = "/cancelCheckout")
    @Operation(description = "Cancel Checkout")
    public CommonResponse<TempTransactionEntity> cancelCheckout(@RequestBody CancelAndRefundCheckoutResponse param) {
        try {
            HistoryTransactionEntity historyTransaction = new HistoryTransactionEntity();
            historyTransaction.setUuid_history(param.getUuid_transaction());
            HistoryTransactionEntity savedHistory = historyTransactionService.cancelOrder(historyTransaction);

            TempTransactionEntity tempTransaction = new TempTransactionEntity();
            tempTransaction.setUuid_transaction(savedHistory.getUuid_history());
            tempTransaction.setTitle(savedHistory.getTitle());
            tempTransaction.setFull_name(savedHistory.getFull_name());
            tempTransaction.setGiven_name(savedHistory.getGiven_name());
            tempTransaction.setBirth_date(savedHistory.getBirth_date());
            tempTransaction.setId_card(savedHistory.getId_card());
            TempTransactionEntity updatedTempTransaction = tempTransactionService.updateTempData(tempTransaction);

            log.info("Successfully updated " + param.getUuid_transaction());
            return commonResponseGenerator.succsesResponse(updatedTempTransaction, "Successfully updated status to canceled");
        } catch (Exception e) {
            log.warn(String.valueOf(e));
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }

    @PutMapping(value = "/refundCheckout")
    @Operation(description = "Refund Transaction")
    public CommonResponse<TempTransactionEntity> refundCheckout(@RequestBody CancelAndRefundCheckoutResponse param) {
        try {
            HistoryTransactionEntity historyTransaction = new HistoryTransactionEntity();
            historyTransaction.setUuid_history(param.getUuid_transaction());
            log.info(String.valueOf(historyTransaction));
            HistoryTransactionEntity savedHistory = historyTransactionService.refundOrder(historyTransaction);

            TempTransactionEntity tempTransaction = new TempTransactionEntity();
            tempTransaction.setUuid_transaction(savedHistory.getUuid_history());
            tempTransaction.setTitle(savedHistory.getTitle());
            tempTransaction.setFull_name(savedHistory.getFull_name());
            tempTransaction.setGiven_name(savedHistory.getGiven_name());
            tempTransaction.setBirth_date(savedHistory.getBirth_date());
            tempTransaction.setId_card(savedHistory.getId_card());
            TempTransactionEntity updatedTempTransaction = tempTransactionService.updateTempData(tempTransaction);

            log.info("Successfully updated " + param.getUuid_transaction());
            return commonResponseGenerator.succsesResponse(updatedTempTransaction, "Successfully updated status to refunded");
        } catch (Exception e) {
            log.warn(String.valueOf(e));
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }
}
