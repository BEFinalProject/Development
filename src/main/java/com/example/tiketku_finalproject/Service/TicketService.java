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
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class TicketService {
    @Autowired
    HistoryTransactionRepository historyTransactionRepository;

    public JasperPrint generateTicket(UUID uuid_history) throws JRException, IOException {
        List<HistoryTransactionEntity> ticketEntity = historyTransactionRepository.findByUUIDHistory(uuid_history);
        String reportPath = "classpath:TesJasper.jrxml";
        InputStream reportFile = ResourceUtils.getURL(reportPath).openStream();
        JasperReport jasperReport = JasperCompileManager.compileReport(reportFile);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(ticketEntity);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createBy", "Kelompok B1 Final Project Binar Academy KM 4");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        return jasperPrint;
    }
}
