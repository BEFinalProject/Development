package com.example.tiketku_finalproject.Controller;

import com.example.tiketku_finalproject.Model.SchedulesEntity;
import com.example.tiketku_finalproject.Response.CommonResponse;
import com.example.tiketku_finalproject.Response.CommonResponseGenerator;
import com.example.tiketku_finalproject.Service.SchedulesService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value ="/Schedules")
@Api(value = "Schedules")
public class SchedulesController {
    @Autowired
    SchedulesService schedulesService;
    @Autowired
    CommonResponseGenerator commonResponseGenerator;

    @GetMapping(value = "/findAll")
    @Operation(description = "Get All Schedules Data")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public CommonResponse<List<SchedulesEntity>> findAllSchedules() {
        try{
            log.info("Successfully found all data for all schedules");
            return commonResponseGenerator.succsesResponse(schedulesService.findAllSchedulesData(), "Success get all schedules");
        }catch (Exception e){
            log.warn(String.valueOf(e));
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }

    @GetMapping(value = "/findTiket/{departure_city}/{arrival_city}/{departure_date}/{passenger}") //yang ada di dalam {} disamakan dengan
    @Operation(description = "Search Ticket Available By Departure City, Arrival City, Departure Date And Total Passenger User Needed")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public CommonResponse<List<SchedulesEntity>> searchTiket(@PathVariable String departure_city, @PathVariable String arrival_city,
                                                            @PathVariable Date departure_date, @PathVariable Integer passenger){ // yang ini "id_user"
        try {
            List<SchedulesEntity> schedules = schedulesService.searchTiket(departure_city, arrival_city, departure_date, passenger);
            log.info(String.valueOf(schedules),"Sukses Mencari Data " + schedules);
            return commonResponseGenerator.succsesResponse(schedules,"Sukses Mencari Data " + schedules);
        }
        catch (Exception e){
            log.warn(String.valueOf(e));
            return commonResponseGenerator.failedResponse(e.getMessage());
        }

    }

    @GetMapping(value = "/getTicket/{uuid_schedules}") //yang ada di dalam {} disamakan dengan
    @Operation(description = "Search Ticket Available By UUID Schedule")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public CommonResponse<List<SchedulesEntity>> searchTiketByUUID(@PathVariable UUID uuid_schedules){
        try {
            Optional<SchedulesEntity> schedules = schedulesService.getByUuidSchedules(uuid_schedules);
            log.info(String.valueOf(schedules),"Sukses Mencari Data " + schedules);
            return commonResponseGenerator.succsesResponse(schedules,"Sukses Mencari Data " + schedules);
        }
        catch (Exception e){
            log.warn(String.valueOf(e));
            return commonResponseGenerator.failedResponse(e.getMessage());
        }

    }
}
