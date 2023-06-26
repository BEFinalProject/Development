package com.example.tiketku_finalproject.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TempTransactionResponse {
    private UUID uuid_transaction;
    private UUID uuid_schedules;
    private UUID uuid_user;
    private int passenger;
    private String airplane_name;
    private String airplane_type;
    private String departure_airport;
    private String arrival_airport;
    private String departure_city;
    private String arrival_city;
    private LocalDateTime departure_time;
    private LocalDateTime arrival_time;
    private int price;
    private int seats_id;
    private String seat_type;
    private String title;
    private String full_name;
    private String given_name;
    private Date birth_date;
    private String id_card;
    private LocalDateTime valid_until;
}
