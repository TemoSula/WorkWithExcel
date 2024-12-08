package com.example.WorkWithExcel.Controllers;

import com.example.WorkWithExcel.Models.Person;
import com.example.WorkWithExcel.Request.PersonRequest;
import com.example.WorkWithExcel.Services.PersonService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class PesonController {

    @Autowired
    PersonService service;

    @GetMapping("/excel")
    public void generateExcelReport(HttpServletResponse response) throws Exception    {

        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=person.xls";

        response.setHeader(headerKey,headerValue);
        service.generateExcel(response);

    }

    @PostMapping("/create-person")
    public Person createPerson(PersonRequest request)
    {
        return service.AddPerson(request);
    }


    @PostMapping("/upoad-excelfile")
    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile file)
    {
        try
        {
          service.processExcelFile(file);
          return ResponseEntity.ok("file uploaded and data save and successsfully.");
        }catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file: " + e.getMessage());
        }
    }

}
