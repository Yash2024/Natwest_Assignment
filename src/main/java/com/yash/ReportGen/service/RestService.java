package com.yash.ReportGen.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVWriter;
import com.yash.ReportGen.Controller.Rest;
import com.yash.ReportGen.Entity.InputData;
import com.yash.ReportGen.Entity.OutputData;
import com.yash.ReportGen.Entity.ReferenceData;
import com.yash.ReportGen.repository.InputDataRepository;
import com.yash.ReportGen.repository.ReferenceDataRepository;

@Service
public class RestService {
	
		@Autowired
		private InputDataRepository ipRepository;
		
		@Autowired
	    private ReferenceDataRepository refRepository;
	
		private org.slf4j.Logger logger = LoggerFactory.getLogger(Rest.class);
	
		
		int BATCH_SIZE = 100000; // Adjust the batch size as needed

	    public void getInput(MultipartFile file) {
	        List<InputData> batch = new ArrayList<>();

	        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
	             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

	            for (CSVRecord csvRecord : csvParser) {
	                String field1 = csvRecord.get(0);
	                String field2 = csvRecord.get(1);
	                String field3 = csvRecord.get(2);
	                String field4 = csvRecord.get(3);
	                Double field5 = Double.parseDouble(csvRecord.get(4));
	                String refkey1 = csvRecord.get(5);
	                String refkey2 = csvRecord.get(6);

	                InputData record = new InputData(field1, field2, field3, field4, field5, refkey1, refkey2);
	                batch.add(record);

	                if (batch.size() >= BATCH_SIZE) {
	                    saveBatch(batch);
	                    batch.clear();
	                }
	            }
	            if (!batch.isEmpty()) {
	                saveBatch(batch);
	            }

	        } catch (IOException e) {
	            logger.error("Error processing CSV file", e);
	        }
	    }

	    private void saveBatch(List<InputData> batch) {
	        ipRepository.saveAll(batch);
	        logger.info("Saved batch of size: {}", batch.size());
	    }
	
	    
	    public void getRefs(MultipartFile file) {
	        List<ReferenceData> batch = new ArrayList<>();

	        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
	             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

	            for (CSVRecord csvRecord : csvParser) {
	                String refkey1 = csvRecord.get(0);
	                String refdata1 = csvRecord.get(1);
	                String refkey2 = csvRecord.get(2);
	                String refdata2 = csvRecord.get(3);
	                String refdata3 = csvRecord.get(4);
	                Double refdata4 = Double.parseDouble(csvRecord.get(5));

	                ReferenceData record = new ReferenceData(refkey1, refdata1, refkey2, refdata2, refdata3, refdata4);
	                batch.add(record);

	                if (batch.size() >= BATCH_SIZE) {
	                    saveReferenceBatch(batch);
	                    batch.clear();
	                }
	            }
	            if (!batch.isEmpty()) {
	                saveReferenceBatch(batch);
	            }

	        } catch (IOException e) {
	            logger.error("Error processing reference CSV file", e);
	        }
	    }
	    
	    private void saveReferenceBatch(List<ReferenceData> batch) {
	        refRepository.saveAll(batch);
	        logger.info("Saved batch of size: {}", batch.size());
	    }
		
	
		
		public List<OutputData> getOutput(List<Object[]> data) {
	        List<OutputData> outputList = new ArrayList<>();
	        
	        for (Object[] record : data) {
	        	
	            Integer id = (Integer) record[0];
	            String field1 = (String) record[1];
	            String field2 = (String) record[2];
	            String field3 = (String) record[3];
	            String field4 = (String) record[4];
	            Double field5 = ((Number) record[5]).doubleValue(); 
	            String refdata1 = (String) record[6];
	            String refdata2 = (String) record[7];
	            String refdata3 = (String) record[8];
	            Double refdata4 = ((Number) record[9]).doubleValue(); 

	            
	            String outfield1 = field1 + field2;
	            String outfield2 = refdata1;
	            String outfield3 = refdata2 + refdata3;
	            Double outfield4 = Math.max(field5, refdata4);
	            Double outfield5 = Math.max(field5, refdata4);

	            OutputData output = new OutputData(outfield1, outfield2, outfield3, outfield4, outfield5);
	            outputList.add(output);
	        }
	
	        return outputList;
    }
	
		public byte[] writeOutputToCsv(List<OutputData> outputList) {
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	        try (Writer writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
	             CSVWriter csvWriter = new CSVWriter(writer)) {

	            // Set up CSV file header
	            String[] header = {"outfield1", "outfield2", "outfield3", "outfield4", "outfield5"};
	            csvWriter.writeNext(header);

	            // Write data rows
	            for (OutputData output : outputList) {
	                String[] data = {
	                    output.getOutfield1(),
	                    output.getOutfield2(),
	                    output.getOutfield3(),
	                    String.valueOf(output.getOutfield4()),
	                    String.valueOf(output.getOutfield5())
	                };
	                csvWriter.writeNext(data);
	            }

	            csvWriter.flush();
	        } catch (IOException e) {
	            logger.error("Error writing CSV file", e);
	        }

	        return outputStream.toByteArray();
	    }
	
}
