package com.patient.controller;

import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.patient.entity.PatientEntity;

public class ExcelExporters {
	public static byte[] exportToPDF(List<PatientEntity> patients) throws DocumentException, IOException {
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
		PdfPTable table = new PdfPTable(9); // Number of columns
		table.setWidthPercentage(100); // Width 100%

		// Add table headers
		table.addCell("Patient ID");
		table.addCell("Name");
		table.addCell("Gender");
		table.addCell("Disease");
		table.addCell("Blood Group");
		table.addCell("Address");
		table.addCell("Email");
		table.addCell("Phone Number");
		table.addCell("Date of Birth");
		table.addCell("Age");
		table.addCell("Guardian Name");
		table.addCell("Guardian Phone");
		table.addCell("Current Street");
		table.addCell("Current Village");
		table.addCell("Current Post Office");
		table.addCell("Current District");
		table.addCell("Current Country");
		table.addCell("Current Pincode");
		table.addCell("Permanent Street");
		table.addCell("Permanent Village");
		table.addCell("Permanent Post Office");
		table.addCell("Permanent District");
		table.addCell("Permanent Country");
		table.addCell("Permanent Pincode");
		table.addCell("Booking Date");
		table.addCell("Appointment Time");
		// Add data rows
		for (PatientEntity patient : patients) {
			table.addCell(String.valueOf(patient.getId()));
			table.addCell(patient.getPatientName());
			table.addCell(patient.getPatientGen());
			table.addCell(patient.getDisease());
			table.addCell(patient.getBloodGroup());
			table.addCell(patient.getEmail());
			table.addCell(patient.getPhoneno());
			table.addCell(patient.getDob());
			table.addCell(String.valueOf(patient.getAge()));
			table.addCell(patient.getGuardianName());
			table.addCell(patient.getGuardianPhone());
			table.addCell(patient.getCurrentStreet());
			table.addCell(patient.getCurrentVillage());
			table.addCell(patient.getCurrentPostOffice());
			table.addCell(patient.getCurrentDistrict());
			table.addCell(patient.getCurrentCountry());
			table.addCell(patient.getCurrentPincode());
			table.addCell(patient.getPermanentStreet());
			table.addCell(patient.getPermanentVillage());
			table.addCell(patient.getPermanentPostOffice());
			table.addCell(patient.getPermanentDistrict());
			table.addCell(patient.getPermanentCountry());
			table.addCell(patient.getPermanentPincode());
			table.addCell(patient.getBookingDate());
			table.addCell(patient.getAppointmentTime());
		}

		document.add(table);
		document.close();

		return outputStream.toByteArray();
	}

}
