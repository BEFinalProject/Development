package com.example.tiketku_finalproject.Controller;

import com.example.tiketku_finalproject.Model.CitiesEntity;
import com.example.tiketku_finalproject.Model.RoutesEntity;
import com.example.tiketku_finalproject.Response.CommonResponse;
import com.example.tiketku_finalproject.Response.CommonResponseGenerator;
import com.example.tiketku_finalproject.Service.CitiesService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "City")
@Api("City")
@Slf4j
public class CityController {
    @Autowired
    CitiesService citiesService;

    @Autowired
    CommonResponseGenerator commonResponseGenerator;

    @GetMapping(value = "/findAll")
    @Operation(description = "Get all cities data")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public CommonResponse<List<CitiesEntity>> findAllCities(){
        try{
            log.info("Successfully found all routes");
            return commonResponseGenerator.succsesResponse(citiesService.findAllCities(), "Successfully find all routes data");
        }catch(Exception e){
            log.warn(String.valueOf(e));
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }
}
