package com.patient.controller;

import org.apache.poi.ss.usermodel.*;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.patient.entity.PatientEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelExporter {

	public static byte[] exportToExcel(List<PatientEntity> patients) throws IOException {
		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Patients");

			// Create header row
			Row headerRow = sheet.createRow(0);
			headerRow.createCell(0).setCellValue("Patient ID");
			headerRow.createCell(1).setCellValue("Name");
			headerRow.createCell(2).setCellValue("Gender");
			headerRow.createCell(3).setCellValue("Disease");
			headerRow.createCell(4).setCellValue("Blood Group");
			headerRow.createCell(5).setCellValue("Address");
			headerRow.createCell(6).setCellValue("Email");
			headerRow.createCell(7).setCellValue("Phone Number");
			headerRow.createCell(8).setCellValue("Date of Birth");
			headerRow.createCell(9).setCellValue("Age");
			headerRow.createCell(10).setCellValue("Guardian Name");
			headerRow.createCell(11).setCellValue("Guardian Phone");
			headerRow.createCell(12).setCellValue("Current Street");
			headerRow.createCell(13).setCellValue("Current Village");
			headerRow.createCell(14).setCellValue("Current Post Office");
			headerRow.createCell(15).setCellValue("Current District");
			headerRow.createCell(16).setCellValue("Current Country");
			headerRow.createCell(17).setCellValue("Current Pincode");
			headerRow.createCell(18).setCellValue("Permanent Street");
			headerRow.createCell(19).setCellValue("Permanent Village");
			headerRow.createCell(20).setCellValue("Permanent Post Office");
			headerRow.createCell(21).setCellValue("Permanent District");
			headerRow.createCell(22).setCellValue("Permanent Country");
			headerRow.createCell(23).setCellValue("Permanent Pincode");
			headerRow.createCell(24).setCellValue("Booking Date");
			headerRow.createCell(25).setCellValue("Appointment Time");

			// Create data rows
			int rowNum = 1;
			for (PatientEntity patient : patients) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(patient.getId());
				row.createCell(1).setCellValue(patient.getPatientName());
				row.createCell(2).setCellValue(patient.getPatientGen());
				row.createCell(3).setCellValue(patient.getDisease());
				row.createCell(4).setCellValue(patient.getBloodGroup());

				row.createCell(6).setCellValue(patient.getEmail());
				row.createCell(7).setCellValue(patient.getPhoneno());
				row.createCell(8).setCellValue(patient.getDob());
				row.createCell(9).setCellValue(patient.getAge());
				row.createCell(10).setCellValue(patient.getGuardianName());
				row.createCell(11).setCellValue(patient.getGuardianPhone());
				row.createCell(12).setCellValue(patient.getCurrentStreet());
				row.createCell(13).setCellValue(patient.getCurrentVillage());
				row.createCell(14).setCellValue(patient.getCurrentPostOffice());
				row.createCell(15).setCellValue(patient.getCurrentDistrict());
				row.createCell(16).setCellValue(patient.getCurrentCountry());
				row.createCell(17).setCellValue(patient.getCurrentPincode());
				row.createCell(18).setCellValue(patient.getPermanentStreet());
				row.createCell(19).setCellValue(patient.getPermanentVillage());
				row.createCell(20).setCellValue(patient.getPermanentPostOffice());
				row.createCell(21).setCellValue(patient.getPermanentDistrict());
				row.createCell(22).setCellValue(patient.getPermanentCountry());
				row.createCell(23).setCellValue(patient.getPermanentPincode());
				row.createCell(24).setCellValue(patient.getBookingDate());
				row.createCell(25).setCellValue(patient.getAppointmentTime());

			}
			// Write workbook to ByteArrayOutputStream
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);
			return outputStream.toByteArray();
		}
	}

}
