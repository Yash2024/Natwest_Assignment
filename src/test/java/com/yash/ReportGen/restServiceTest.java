package com.yash.ReportGen;


import com.yash.ReportGen.Entity.InputData;
import com.yash.ReportGen.Entity.OutputData;
import com.yash.ReportGen.Entity.ReferenceData;
import com.yash.ReportGen.repository.InputDataRepository;
import com.yash.ReportGen.repository.ReferenceDataRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import com.yash.ReportGen.service.RestService;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class restServiceTest {

    @InjectMocks
    private RestService restService;

    @Mock
    private InputDataRepository ipRepository;

    @Mock
    private ReferenceDataRepository refRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetInput() throws Exception {
        String csvContent = "field1,field2,field3,field4,field5,refkey1,refkey2\n" +
                "data1,data2,data3,data4,123.45,ref1,ref2";
        MockMultipartFile file = new MockMultipartFile("file", "input.csv", "text/csv", csvContent.getBytes());

        restService.getInput(file);

        verify(ipRepository, times(1)).saveAll(anyList());
    }

    @Test
    public void testGetRefs() throws Exception {
        String csvContent = "refkey1,refdata1,refkey2,refdata2,refdata3,refdata4\n" +
                "ref1,refdata1,ref2,refdata2,refdata3,123.45";
        MockMultipartFile file = new MockMultipartFile("file", "reference.csv", "text/csv", csvContent.getBytes());

        restService.getRefs(file);

        verify(refRepository, times(1)).saveAll(anyList());
    }

    @Test
    public void testGetOutput() {
        List<Object[]> data = new ArrayList<>();
        data.add(new Object[]{1, "field1", "field2", "field3", "field4", 123.45, "refdata1", "refdata2", "refdata3", 678.90});

        List<OutputData> outputList = restService.getOutput(data);

        assertEquals(1, outputList.size());
        OutputData output = outputList.get(0);
        assertEquals("field1field2", output.getOutfield1());
        assertEquals("refdata1", output.getOutfield2());
        assertEquals("refdata2refdata3", output.getOutfield3());
        assertEquals(678.90, output.getOutfield4());
        assertEquals(678.90, output.getOutfield5());
    }

    @Test
    public void testWriteOutputToCsv() {
        List<OutputData> outputList = new ArrayList<>();
        outputList.add(new OutputData("field1field2", "refdata1", "refdata2refdata3", 678.90, 678.90));

        byte[] csvBytes = restService.writeOutputToCsv(outputList);
        String expectedCsv = "\"outfield1\",\"outfield2\",\"outfield3\",\"outfield4\",\"outfield5\"\n" +
                "\"field1field2\",\"refdata1\",\"refdata2refdata3\",\"678.9\",\"678.9\"\n";
        
//        System.out.println("Expected CSV Length: " + expectedCsv.getBytes().length);
//        System.out.println("Actual CSV Length: " + csvBytes.length);
//
//        System.out.println("Expected CSV:");
//        System.out.println(expectedCsv);
//
//        System.out.println("Actual CSV:");
//        System.out.println(new String(csvBytes));

        assertArrayEquals(expectedCsv.getBytes(), csvBytes);

    }
}

