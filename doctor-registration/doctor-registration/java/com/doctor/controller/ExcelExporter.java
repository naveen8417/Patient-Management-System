package com.doctor.controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.doctor.entity.DoctorEntity;
import com.doctor.repo.DoctorRepo;
import com.itextpdf.text.pdf.codec.Base64.InputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Component
public class ExcelExporter {
	@Autowired
	private static DoctorRepo doctorRepository;

	public ExcelExporter(DoctorRepo doctorRepository) {
		this.doctorRepository = doctorRepository;
	}

	public static byte[] exportToExcel(List<DoctorEntity> doctors) throws IOException {

		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Doctors");

			// Create header row
			Row headerRow = sheet.createRow(0);
			headerRow.createCell(0).setCellValue("Doctor ID");
			headerRow.createCell(1).setCellValue("Doctor Name");
			headerRow.createCell(2).setCellValue("Email");
			headerRow.createCell(3).setCellValue("Phone Number");
			headerRow.createCell(4).setCellValue("Address");
			headerRow.createCell(5).setCellValue("Specialization");
			headerRow.createCell(6).setCellValue("Availability");

			// Create data rows
			int rowNum = 1;
			for (DoctorEntity doctor : doctors) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(doctor.getDoctorid());
				row.createCell(1).setCellValue(doctor.getDoctorname());
				row.createCell(2).setCellValue(doctor.getDoctoremail());
				row.createCell(3).setCellValue(doctor.getDoctorphoneno());
				row.createCell(4).setCellValue(doctor.getAddress());
				row.createCell(5).setCellValue(doctor.getSpecialisation());
				row.createCell(6).setCellValue(doctor.getAvailability());
			}

			// Write workbook to ByteArrayOutputStream
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);
			return outputStream.toByteArray();
		}
	}

	 public static void importFromExcel(java.io.InputStream inputStream) throws IOException {
	        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
	            Sheet sheet = workbook.getSheetAt(0);
	            Iterator<Row> rows = sheet.iterator();

	            // Skip header row
	            if (rows.hasNext()) {
	                rows.next();
	            }

	            while (rows.hasNext()) {
	                Row row = rows.next();
	                DoctorEntity doctor = new DoctorEntity();

	                doctor.setDoctorid((int) row.getCell(0).getNumericCellValue());
	                doctor.setDoctorname(row.getCell(1).getStringCellValue());
	                doctor.setDoctoremail(row.getCell(2).getStringCellValue());
	                doctor.setDoctorphoneno(row.getCell(3).getStringCellValue());
	                doctor.setAddress(row.getCell(4).getStringCellValue());
	                doctor.setSpecialisation(row.getCell(5).getStringCellValue());
	                doctor.setAvailability(row.getCell(6).getStringCellValue());

	                // Update or save the doctor entity
	                Optional<DoctorEntity> existingDoctor = doctorRepository.findById(doctor.getDoctorid());
	                if (existingDoctor.isPresent()) {
	                    DoctorEntity updatedDoctor = existingDoctor.get();
	                    updateDoctor(updatedDoctor, doctor);
	                } else {
	                    doctorRepository.save(doctor);
	                }
	            }
	        }
	    }

	    private static void updateDoctor(DoctorEntity existingDoctor, DoctorEntity newDoctor) {
	        existingDoctor.setDoctorname(newDoctor.getDoctorname());
	        existingDoctor.setDoctoremail(newDoctor.getDoctoremail());
	        existingDoctor.setDoctorphoneno(newDoctor.getDoctorphoneno());
	        existingDoctor.setAddress(newDoctor.getAddress());
	        existingDoctor.setSpecialisation(newDoctor.getSpecialisation());
	        existingDoctor.setAvailability(newDoctor.getAvailability());

	        doctorRepository.save(existingDoctor);
	    }

	
	}


