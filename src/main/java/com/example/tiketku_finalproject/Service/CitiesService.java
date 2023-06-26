package com.example.tiketku_finalproject.Service;

import com.example.tiketku_finalproject.Model.CitiesEntity;
import com.example.tiketku_finalproject.Repository.CitiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitiesService {
    @Autowired
    CitiesRepository citiesRepository;

    public List<CitiesEntity> findAllCities() {
        return citiesRepository.findAll();
    }
}
