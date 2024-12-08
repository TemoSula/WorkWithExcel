package com.example.WorkWithExcel.Services;

import com.example.WorkWithExcel.Models.Person;
import com.example.WorkWithExcel.Repositories.PersonRepository;
import com.example.WorkWithExcel.Request.Mapper;
import com.example.WorkWithExcel.Request.PersonRequest;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.MultiPixelPackedSampleModel;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepo;


    //generate excell
    public void generateExcel(HttpServletResponse response) throws IOException {
        List<Person> listOfPerson = personRepo.findAll();


        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Courses Info");
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("ID");
        row.createCell(1).setCellValue("Full Name");
        row.createCell(2).setCellValue("Age");
        row.createCell(3).setCellValue("Course Name");
        row.createCell(4).setCellValue("ID Number");

        int dataRowIndex = 1;
        for(Person person : listOfPerson)
        {
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(person.getId());
            dataRow.createCell(1).setCellValue(person.getFullName());
            dataRow.createCell(2).setCellValue(person.getAge());
            dataRow.createCell(3).setCellValue(person.getCourseName());
            dataRow.createCell(4).setCellValue(person.getIDnumber());
            dataRowIndex ++;
        }
        ServletOutputStream ops = response.getOutputStream();
        workbook.write(ops);
        workbook.close();
        ops.close();


    }

    public void processExcelFile(MultipartFile file) throws IOException {

            /*InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            List<Person> persons = new ArrayList<>();

            for(int i = 1; i <= sheet.getLastRowNum();i++)
            {

                Row row = sheet.getRow(i);

                Person person = new Person();
                person.setId(row.getCell(0).getStringCellValue());
                person.setFullName(row.getCell(1).getStringCellValue());
                person.setAge(row.getCell(2).getStringCellValue());
                person.setCourseName(row.getCell(3).getStringCellValue());
                person.setIDnumber(row.getCell(4).getStringCellValue());
                persons.add(person);
                personRepo.save(person);



        }*/

        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            List<Person> personList = new ArrayList<>();

            //rows.next(); // amas gamoviyenebdit mashin tuki zemot pirvel lainze gveqneboda ubralod sataurebi

            while (rows.hasNext()) {
                Row row = rows.next();
                Person person = new Person();
                person.setId(row.getCell(0).getStringCellValue());
                person.setFullName(row.getCell(1).getStringCellValue());
                person.setAge((int) row.getCell(2).getNumericCellValue());
                person.setCourseName(row.getCell(3).getStringCellValue());
                person.setIDnumber((long) row.getCell(4).getNumericCellValue());
                personList.add(person);
            }


            personRepo.saveAll(personList);
        }catch (Exception exception)
        {
            throw new RuntimeException("Failed to process Excel file: " + exception.getMessage());
        }
    }


    public Person AddPerson(PersonRequest request)
    {
        Person person =  Mapper.mapFromRequestToThePerson(request);
        return personRepo.save(person);
    }

}
