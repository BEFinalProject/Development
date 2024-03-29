package com.example.tiketku_finalproject.Controller;

import com.example.tiketku_finalproject.Model.HistoryTransactionEntity;
import com.example.tiketku_finalproject.Response.CommonResponse;
import com.example.tiketku_finalproject.Response.CommonResponseGenerator;
import com.example.tiketku_finalproject.Response.TransactionSummaryResponse;
import com.example.tiketku_finalproject.Service.HistoryTransactionService;
import com.example.tiketku_finalproject.Service.TicketService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value ="/HistoryTransaction")
@Api(value = "HistoryTransaction")
public class HistoryTransactionController {
    @Autowired
    HistoryTransactionService historyTransactionService;
    @Autowired
    TicketService ticketService;
    @Autowired
    CommonResponseGenerator commonResponseGenerator;

    @GetMapping(value = "/findAll")
    @Operation(description = "Get All History Data")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public CommonResponse<List<HistoryTransactionEntity>> getAllHistory(){
        return commonResponseGenerator.succsesResponse(historyTransactionService.findAll(), "Show All History data");
    }

    @GetMapping(value = "/user/{uuid_user}")
    @Operation(description = "Show All Transaction Users ")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public CommonResponse<List<HistoryTransactionEntity>> getHistory(@PathVariable UUID uuid_user){
        try {
            List<HistoryTransactionEntity> historyTransaction = historyTransactionService.searchHistoryUsers(uuid_user);
            log.info(String.valueOf(historyTransaction));

            if(!historyTransaction.isEmpty()) {
                return commonResponseGenerator.succsesResponse(historyTransaction,"Sukses Mencari Jadwal Transaction");
            }else {
                return commonResponseGenerator.succsesResponse(historyTransaction, "Data tidak ditemukan");
            }
        } catch (Exception e) {
            log.warn(String.valueOf(e));
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }
    @GetMapping(value = "/date/{departure_date}/{uuid_user}")
    @Operation(description = "Show All Transaction User By Date")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public CommonResponse<List<HistoryTransactionEntity>> getHistory(@PathVariable LocalDate departure_date, @PathVariable UUID uuid_user){
        try {
            List<HistoryTransactionEntity> historyTransaction = historyTransactionService.searchHistoryByDateAndUUIDUsers(departure_date,uuid_user);
            log.info(String.valueOf(historyTransaction));
            if (!historyTransaction.isEmpty()) {
                return commonResponseGenerator.succsesResponse(historyTransaction,"Sukses Mencari Jadwal Transaction");
            }else {
                return commonResponseGenerator.succsesResponse(historyTransaction, "Data tidak ditemukan");
            }
        } catch (Exception e) {
            log.warn(String.valueOf(e));
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }
    @GetMapping(value = "/uuid/{uuid_user}/{uuid_history}")
    @Operation(description = "Show Specific User Transaction")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public CommonResponse<List<HistoryTransactionEntity>> getHistory(@PathVariable UUID uuid_user, @PathVariable UUID uuid_history){
        try {
            List<HistoryTransactionEntity> historyTransaction = historyTransactionService.searchHistoryByUUIDUserAndHistory(uuid_user,uuid_history);
            log.info(String.valueOf(historyTransaction));

            if(!historyTransaction.isEmpty()) {
                return commonResponseGenerator.succsesResponse(historyTransaction,"Sukses Mencari Jadwal Transaction");
            }else {
                return commonResponseGenerator.succsesResponse(historyTransaction, "Data tidak ditemukan");
            }
        } catch (Exception e) {
            log.warn(String.valueOf(e));
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }
    @GetMapping(value = "/total/{uuid_user}/{created_at}")
    @Operation(description = "Show Total Price And Total Passenger")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public CommonResponse<List<TransactionSummaryResponse>> getPriceAndTotalPassenger(@PathVariable UUID uuid_user, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime created_at) {
        try {
            List<TransactionSummaryResponse> historyTransaction = historyTransactionService.SearchPriceAndTotalPassanger(uuid_user, created_at);
            log.info(String.valueOf(historyTransaction));
            return commonResponseGenerator.succsesResponse(historyTransaction, "Sukses Mencari Jadwal Transaction");
        } catch (Exception e) {
            log.warn(String.valueOf(e));
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }
}
