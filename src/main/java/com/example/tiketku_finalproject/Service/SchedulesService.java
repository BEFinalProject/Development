package com.example.tiketku_finalproject.Service;

import com.example.tiketku_finalproject.Model.*;
import com.example.tiketku_finalproject.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SchedulesService {
    @Autowired
    SchedulesRepository schedulesRepository;

    public List<SchedulesEntity> searchByUuidSchedules(UUID uuid_schedules){
        return schedulesRepository.searchByUUIDSchedules(uuid_schedules);
    }
    public List<SchedulesEntity> saveDataLimit(List<SchedulesEntity> schedulesEntities) {

        List<SchedulesEntity> savedSchedules = new ArrayList<>();
        for (SchedulesEntity schedulesEntity : schedulesEntities) {
            Optional<SchedulesEntity> optionalSavedSchedule = schedulesRepository.findById(schedulesEntity.getUuid_schedules());
            if (optionalSavedSchedule.isPresent()) {
                SchedulesEntity savedSchedule = optionalSavedSchedule.get();
                savedSchedule.setUuid_schedules(schedulesEntity.getUuid_schedules());
                savedSchedule.setAirplane_id(schedulesEntity.getAirplane_id());
                savedSchedule.setIata_code(schedulesEntity.getIata_code());
                savedSchedule.setRoutes_uid(schedulesEntity.getRoutes_uid());
                savedSchedule.setLimits(schedulesEntity.getLimits());
                savedSchedules.add(savedSchedule);
            }
        }
        return schedulesRepository.saveAll(savedSchedules);
    }

    public Optional<SchedulesEntity> getByUuidSchedules(UUID uuidSchedules) {
        return schedulesRepository.findById(uuidSchedules);
    }

    public List<SchedulesEntity> searchTiket(String departure_city, String arrival_city, Date departure_date, Integer total_passenger) {
        List<Object[]> results = schedulesRepository.searching(departure_city, arrival_city, departure_date, total_passenger);
        List<SchedulesEntity> schedules = new ArrayList<>();

        for (Object[] result : results) {
            SchedulesEntity schedule = new SchedulesEntity();
            schedule.setUuid_schedules((UUID) result[0]);
            schedules.add(schedule);
        }

        return schedules;
    }

}
