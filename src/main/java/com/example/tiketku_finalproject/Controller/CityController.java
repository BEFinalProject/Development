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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
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
    @CrossOrigin(origins = "https://novel-tomatoes-production.up.railway.app", maxAge=3600)
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public CommonResponse<List<CitiesEntity>> findAllCities(){
        try{
            log.info("Successfully found all cities data");
            return commonResponseGenerator.succsesResponse(citiesService.findAllCities(), "Successfully find all cities data");
        }catch(Exception e){
            log.warn(String.valueOf(e));
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }
}
