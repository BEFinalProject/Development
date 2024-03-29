package com.example.tiketku_finalproject.Repository;

import com.example.tiketku_finalproject.Model.SchedulesEntity;
import com.example.tiketku_finalproject.Model.TempTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface SchedulesRepository extends JpaRepository<SchedulesEntity, UUID> {

        @Query("SELECT s.uuid_schedules, s.airplane_id, s.iata_code, s.routes_uid, s.limits, s.price, r.departure_city, r.arrival_city, r.departure_airport, r.arrival_airport, r.departure_time, r.arrival_time " +
                "FROM SchedulesEntity s " +
                "JOIN RoutesEntity r ON r.routes_uid = s.routes_uid " +
                "WHERE r.departure_city = :departure_city " +
                "AND r.arrival_city = :arrival_city " +
                "AND r.departure_date = :departure_date " +
                "AND s.limits >= :total_passenger")
        List<Object[]> searching(@Param("departure_city") String departure_city,
                                 @Param("arrival_city") String arrival_city,
                                 @Param("departure_date") LocalDate departure_date,
                                 @Param("total_passenger") int total_passenger);

        @Query("SELECT s from SchedulesEntity s WHERE s.uuid_schedules = :uuid_schedules")
        List<SchedulesEntity> searchByUUIDSchedules(@Param("uuid_schedules") UUID uuid_schedules);

        /*@Modifying
        @Query("UPDATE SchedulesEntity s SET s.limits = 30")
        void resetLimit();*/

}
