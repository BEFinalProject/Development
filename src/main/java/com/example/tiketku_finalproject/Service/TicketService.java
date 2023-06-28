package com.example.tiketku_finalproject.Service;

import com.example.tiketku_finalproject.Model.HistoryTransactionEntity;
import com.example.tiketku_finalproject.Repository.HistoryTransactionRepository;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.util.List;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class TicketService {
    @Autowired
    HistoryTransactionRepository historyTransactionRepository;

    public String printReportbyUuidHistory(UUID uuid_history) throws FileNotFoundException, JRException {
        List<HistoryTransactionEntity> ticketEntity = historyTransactionRepository.findByUUIDHistory(uuid_history);
        String path = "C:\\Users\\ARJ\\Downloads";
        File file = ResourceUtils.getFile("classpath:TesJasper.jrxml");
        JasperReport jasperReport= JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(ticketEntity);
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("createBy", "Kelompok B1 Final Project Binar Academy KM 4");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        JasperExportManager.exportReportToPdfFile(jasperPrint, path+"\\Ticket.pdf");

        return "Report generated in " + path;
    }
}



/*package com.example.tiketku_finalproject.Service;

import com.example.tiketku_finalproject.Model.HistoryTransactionEntity;
import com.example.tiketku_finalproject.Repository.HistoryTransactionRepository;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class TicketService {

    private final HistoryTransactionRepository historyTransactionRepository;
    private final DataSource dataSource;

    @Autowired
    public TicketService(HistoryTransactionRepository historyTransactionRepository, DataSource dataSource) {
        this.historyTransactionRepository = historyTransactionRepository;
        this.dataSource = dataSource;
    }

    public String generateTicketReport(UUID uuid_history) {
        HistoryTransactionEntity historyTransaction = historyTransactionRepository.findByUUIDHistory(uuid_history);
        String path = "C:\\Users\\ARJ\\Downloads";
        if (historyTransaction != null) {
            try {
                File reportFile = ResourceUtils.getFile("classpath:TesJasper.jrxml");
                Connection connection = dataSource.getConnection();


                // Set parameters for the report
                Map<String, Object> parameters = new HashMap<>();
                parameters.put("uuid_history", historyTransaction.getUuid_history());
                parameters.put("uuid_schedules", historyTransaction.getUuid_schedules());
                parameters.put("uuid_user", historyTransaction.getUuid_user());
                parameters.put("airplane_name", historyTransaction.getAirplane_name());
                parameters.put("departure_airport", historyTransaction.getDeparture_airport());
                parameters.put("arrival_airport", historyTransaction.getArrival_airport());
                parameters.put("departure_city", historyTransaction.getDeparture_city());
                parameters.put("arrival_city", historyTransaction.getArrival_city());
                parameters.put("departure_date", historyTransaction.getDeparture_date());
                parameters.put("departure_time", historyTransaction.getDeparture_time());
                parameters.put("arrival_date", historyTransaction.getArrival_date());
                parameters.put("arrival_time", historyTransaction.getArrival_time());
                parameters.put("seat_type", historyTransaction.getSeat_type());
                parameters.put("title", historyTransaction.getTitle());
                parameters.put("full_name", historyTransaction.getFull_name());
                parameters.put("given_name", historyTransaction.getGiven_name());
                parameters.put("valid_until", historyTransaction.getValid_until());

                // Compile the Jasper Report from the .jrxml file
                JasperReport jasperReport = JasperCompileManager.compileReport(reportFile.getAbsolutePath());

                // Create JasperPrint object passing report, parameters, and data source
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

                // Export the Jasper Report to PDF
                JasperExportManager.exportReportToPdfFile(jasperPrint,  path+"\\ticket_report.pdf");

                // Close the database connection
                connection.close();
            } catch (FileNotFoundException | JRException | SQLException e) {
                e.printStackTrace();
            }
        }
        return "Report generated to " + path+"\\ticket_report.pdf";
    }
}*/
