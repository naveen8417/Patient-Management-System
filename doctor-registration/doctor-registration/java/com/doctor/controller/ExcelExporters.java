package com.doctor.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import com.doctor.entity.DoctorEntity;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ExcelExporters {

    public static byte[] exportToPDF(List<DoctorEntity> doctors) throws DocumentException, IOException {
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // Add title
        Paragraph title = new Paragraph("Doctor Details");
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Add empty line
        document.add(new Paragraph(" "));

        // Create table
        PdfPTable table = new PdfPTable(7); // Number of columns
        table.setWidthPercentage(100); // Width 100%

        // Add table headers
        table.addCell("Doctor ID");
        table.addCell("Doctor Name");
        table.addCell("Email");
        table.addCell("Phone Number");
        table.addCell("Address");
        table.addCell("Specialization");
        table.addCell("Availability");

        // Add data rows
        for (DoctorEntity doctor : doctors) {
            table.addCell(String.valueOf(doctor.getDoctorid()));
            table.addCell(doctor.getDoctorname());
            table.addCell(doctor.getDoctoremail());
            table.addCell(doctor.getDoctorphoneno());
            table.addCell(doctor.getAddress());
            table.addCell(doctor.getSpecialisation());
            table.addCell(doctor.getAvailability());
        }

        document.add(table);
        document.close();

        return outputStream.toByteArray();
    }
}
