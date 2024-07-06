package com.doctor.controller;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.doctor.entity.DoctorEntity;
import com.doctor.exception.DoctorNotFoundException;
import com.doctor.repo.DoctorRepo;
import com.doctor.service.DoctorService;
import com.itextpdf.text.DocumentException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
@RequestMapping({ "doctor", "/" })
public class DoctorController {

    @Autowired
    DoctorRepo patrepo;

    private DoctorService doctorService;

    @GetMapping( "register")
    public String dashboard() {
        return "register";
    }

    @PostMapping("save")
    public String data(@ModelAttribute DoctorEntity doctorEntity, Model model) {
        if (doctorService.existsById(doctorEntity.getId())) {
            String errorMessage = "User ID already exists";
            model.addAttribute("errorMessage", errorMessage);
            return "register";
        }

      

        Long id = doctorService.insert(doctorEntity);
       
        String message = "doctordetails '" + id + "' Created";
        model.addAttribute("message", message);
        return "register";
    }

    @GetMapping({"all","/"})
    public String getPatients(@PageableDefault(page = 0, size = 3) Pageable pageable,
                              @RequestParam(required = false) String message, Model model) {
        Page<DoctorEntity> page = doctorService.getAllPatients(pageable);
        model.addAttribute("list", page.getContent());
        model.addAttribute("page", page);
        model.addAttribute("message", message);
        return "doctorList";
    }

    @GetMapping("/delete")
    public String doDelete(@RequestParam Long id, RedirectAttributes attributes) {
        try {
            doctorService.deletePatient(id);
            attributes.addAttribute("message", "Patient '"+id+"' Deleted");
        } catch (DoctorNotFoundException e) {
            e.printStackTrace();
            attributes.addAttribute("message", e.getMessage());
        }
        return "redirect:http://localhost:8888/doctor/all";
    }

    @GetMapping("/edit")
    public String showEdit(@RequestParam Long id, Model model) {
        String page = null;
        try {
            DoctorEntity doctorEntity = doctorService.getOnePatient(id);
            model.addAttribute("patient", doctorEntity);
            page = "doctorEdit";
        } catch (DoctorNotFoundException e) {
            e.printStackTrace();
            model.addAttribute("message", e.getMessage());
            page ="redirect:all";
        }
        return page;
    }

    @GetMapping("/view")
    public String showView(@RequestParam Long id, Model model) {
        String page = null;
        try {
            DoctorEntity doctorEntity = doctorService.getOnePatient(id);
            model.addAttribute("patient", doctorEntity);
            page = "doctorView";
        } catch (DoctorNotFoundException e) {
            e.printStackTrace();
            model.addAttribute("message", e.getMessage());
            page ="redirect:all";
        }
        return page;
    }

    @PostMapping("/update")
    public String updateForm(@ModelAttribute DoctorEntity patient, RedirectAttributes attributes) {
        doctorService.updatePatient(patient);
        attributes.addAttribute("message", "DOCTOR '"+patient.getId()+"' Updated");
        return "redirect:http://localhost:8888/doctor/all";
    }

    @GetMapping("/register/{id}")
    public ResponseEntity<List<DoctorEntity>> showPatientById(@PathVariable long id) {
        java.util.Optional<DoctorEntity> patientOptional = patrepo.findById(id);
        if (patientOptional.isPresent()) {
            DoctorEntity patient = patientOptional.get();
            return ResponseEntity.ok(Collections.singletonList(patient));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/downloadExcel")
    public ResponseEntity<byte[]> downloadExcel() {
        List<DoctorEntity> patients = doctorService.getAllPatients();
        byte[] excelBytes;
        try {
            excelBytes = ExcelExporter.exportToExcel(patients);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDispositionFormData("attachment", "doctors.xlsx");
        headers.setContentLength(excelBytes.length);

        return ResponseEntity.ok()
                            .headers(headers)
                            .body(excelBytes);
    }

    @GetMapping("/exportToPDF")
    public ResponseEntity<byte[]> exportToPDF() {
        try {
            List<DoctorEntity> patients = doctorService.getAllPatients();
            byte[] pdfBytes = ExcelExporters.exportToPDF(patients);
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=doctors.pdf")
                    .body(pdfBytes);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
