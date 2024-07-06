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
	public static byte[] exportToPDF(List<DoctorEntity> patients) throws DocumentException, IOException {
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // Add title
        Paragraph title = new Paragraph("Patient Details");
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Add empty line
        document.add(new Paragraph(" "));

        // Create table
        PdfPTable table = new PdfPTable(15); // Number of columns
        table.setWidthPercentage(100); // Width 100%

        // Add table headers
        table.addCell("Patient ID");
        table.addCell("Doctor ID");
        table.addCell("Doctor Name");
        table.addCell("Email");
        table.addCell("Phone Number");
        table.addCell("Address");
        table.addCell("About");
        table.addCell("Patient Name");
        table.addCell("Patient Age");
        table.addCell("Blood Group");
        table.addCell("Blood Pressure (BP)");
        table.addCell("Sugar Level");
        table.addCell("Disease");
        table.addCell("Health Condition");
        table.addCell("Precaution");
       

        // Add data rows
        for (DoctorEntity patient : patients) {
        	table.addCell(String.valueOf(patient.getId()));
        	table.addCell(String.valueOf(patient.getDoctorid()));
        	table.addCell(patient.getDoctorname());
        	table.addCell(patient.getEmail());
        	table.addCell(patient.getPhoneno());
        	table.addCell(patient.getAddress());
        	table.addCell(patient.getAbout());
        	table.addCell(patient.getPatientname());
        	table.addCell(String.valueOf(patient.getPatientage()));
        	table.addCell(patient.getBloodgroup());
        	table.addCell(patient.getBP());
        	table.addCell(patient.getSugar());
        	table.addCell(patient.getDisease());
        	table.addCell(patient.getHealthcondition());
        	table.addCell(patient.getPrecaution());
        	
        }

        document.add(table);
        document.close();

        return outputStream.toByteArray();
    }

}
