package com.example.tiketku_finalproject.Response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private int price;
    private String departure_city;
    private String arrival_city;
    private String departure_airport;
    private String arrival_airport;
    private LocalDateTime departure_time;
    private LocalDateTime arrival_time;
    @JsonIgnore
    private LocalDate departure_date;
    @JsonIgnore
    private LocalDate arrival_date;
}