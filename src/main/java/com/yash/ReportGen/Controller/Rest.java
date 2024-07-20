package com.yash.ReportGen.Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.yash.ReportGen.Entity.InputData;
import com.yash.ReportGen.Entity.OutputData;
import com.yash.ReportGen.Entity.ReferenceData;
import com.yash.ReportGen.repository.InputDataRepository;
import com.yash.ReportGen.repository.ReferenceDataRepository;
import com.yash.ReportGen.service.RestService;

import ch.qos.logback.classic.Logger;


@RestController
@RequestMapping("/api")
public class Rest {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(Rest.class);
    
    @Autowired
    private RestService restservice;
    
    @Autowired
    private InputDataRepository ipRepository;

    @Autowired
    private ReferenceDataRepository refRepository;

    @PostMapping("/upload")
    public String uploadCsvFile(@RequestParam("file1") MultipartFile file1,
    									 		@RequestParam("file2") MultipartFile file2) {
    	
    	try {
    		
    		ipRepository.deleteAll();
            refRepository.deleteAll();
    		restservice.getInput(file1);
    		restservice.getRefs(file2);
    		logger.info("Uploaded Files Successfully");
    		return "Uploaded Files Successfully";
        } catch (Exception e) {
            logger.error("Error processing CSV files", e);
            return "Error processing CSV files";
        }    
    }
//    ResponseEntity<byte[]>
    @GetMapping("/output")
    public ResponseEntity<byte[]> produceOutput(){
        
        
        List<Object[]> ol= ipRepository.fetchData(); // implementing the join
        
        List<OutputData> outputList = restservice.getOutput(ol);
        byte[] csvBytes = restservice.writeOutputToCsv(outputList);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.setContentDispositionFormData("attachment", "output.csv");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }
   

}





