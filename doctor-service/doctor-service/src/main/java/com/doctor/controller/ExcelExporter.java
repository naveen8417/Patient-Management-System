package com.doctor.controller;

import org.apache.poi.ss.usermodel.*;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.doctor.entity.DoctorEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelExporter {

    public static byte[] exportToExcel(List<DoctorEntity> patients) throws IOException {
    	try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Patients");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Patient ID");
            headerRow.createCell(1).setCellValue("Doctor ID");
            headerRow.createCell(2).setCellValue("Doctor Name");
            headerRow.createCell(3).setCellValue("Email");
            headerRow.createCell(4).setCellValue("Phone Number");
            headerRow.createCell(5).setCellValue("Address");
            headerRow.createCell(6).setCellValue("About");
            headerRow.createCell(7).setCellValue("Patient Name");
            headerRow.createCell(8).setCellValue("Patient Age");
            headerRow.createCell(9).setCellValue("Blood Group");
            headerRow.createCell(10).setCellValue("Blood Pressure (BP)");
            headerRow.createCell(11).setCellValue("Sugar Level");
            headerRow.createCell(12).setCellValue("Disease");
            headerRow.createCell(13).setCellValue("Health Condition");
            headerRow.createCell(14).setCellValue("Precaution");
           



            // Create data rows
            int rowNum = 1;
            for (DoctorEntity patient : patients) {
            	Row row = sheet.createRow(rowNum++);
            	row.createCell(0).setCellValue(patient.getId());
            	row.createCell(1).setCellValue(patient.getDoctorid());
            	row.createCell(2).setCellValue(patient.getDoctorname());
            	row.createCell(3).setCellValue(patient.getEmail());
            	row.createCell(4).setCellValue(patient.getPhoneno());
            	row.createCell(5).setCellValue(patient.getAddress());
            	row.createCell(6).setCellValue(patient.getAbout());
            	row.createCell(7).setCellValue(patient.getPatientname());
            	row.createCell(8).setCellValue(patient.getPatientage());
            	row.createCell(9).setCellValue(patient.getBloodgroup());
            	row.createCell(10).setCellValue(patient.getBP());
            	row.createCell(11).setCellValue(patient.getSugar());
            	row.createCell(12).setCellValue(patient.getDisease());
            	row.createCell(13).setCellValue(patient.getHealthcondition());
            	row.createCell(14).setCellValue(patient.getPrecaution());



            }
            // Write workbook to ByteArrayOutputStream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }
    
}
