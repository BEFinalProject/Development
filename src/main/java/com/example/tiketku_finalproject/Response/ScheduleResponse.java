package com.example.tiketku_finalproject.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Date;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleResponse {
    private UUID uuid_schedules;
    private String airplane_id;
    private String iata_code;
    private UUID routes_uid;
    private Integer limits;
    private String departure_city;
    private String arrival_city;
    private String departure_airport;
    private String arrival_airport;
//    private Date departure_date;
//    private Date arrival_date;
    private Time departure_time;
    private Time arrival_time;
}