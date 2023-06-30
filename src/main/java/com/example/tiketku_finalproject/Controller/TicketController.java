package com.example.tiketku_finalproject.Controller;

import com.example.tiketku_finalproject.Service.TicketService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/Ticket")
@Api(value = "Ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/{uuid_history}")
    @CrossOrigin(origins = "*", maxAge=3600)
    public ResponseEntity<byte[]> generateTicket(@PathVariable UUID uuid_history) throws JRException, IOException {
        JasperPrint jasperPrint = ticketService.generateTicket(uuid_history);
        byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment().filename("Invoice Tiketku.pdf").build());

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
