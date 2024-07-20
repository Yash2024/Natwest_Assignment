package com.yash.ReportGen;

import com.yash.ReportGen.Controller.Rest;
import com.yash.ReportGen.Entity.InputData;
import com.yash.ReportGen.Entity.OutputData;
import com.yash.ReportGen.Entity.ReferenceData;
import com.yash.ReportGen.service.RestService;
import com.yash.ReportGen.repository.InputDataRepository;
import com.yash.ReportGen.repository.ReferenceDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class restTest {

    @InjectMocks
    private Rest restController;

    @Mock
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
    public void testUploadCsvFile() throws Exception {
        MockMultipartFile file1 = new MockMultipartFile("file1", "input.csv", "text/csv", "content".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("file2", "reference.csv", "text/csv", "content".getBytes());

        restController.uploadCsvFile(file1, file2);

        verify(ipRepository, times(1)).deleteAll();
        verify(refRepository, times(1)).deleteAll();
        verify(restService, times(1)).getInput(file1);
        verify(restService, times(1)).getRefs(file2);
        
    }

    @Test
    public void testProduceOutput() {
        List<Object[]> mockData = Collections.singletonList(new Object[]{
            1, "field1", "field2", "field3", "field4", 123.45, "refdata1", "refdata2", "refdata3", 678.90
        });
        when(ipRepository.fetchData()).thenReturn(mockData);

        List<OutputData> mockOutputList = Collections.singletonList(
            new OutputData("field1field2", "refdata1", "refdata2refdata3", 678.90, 678.90)
        );
        when(restService.getOutput(mockData)).thenReturn(mockOutputList);

        byte[] mockCsvBytes = "output".getBytes();
        when(restService.writeOutputToCsv(mockOutputList)).thenReturn(mockCsvBytes);

        ResponseEntity<byte[]> response = restController.produceOutput();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(mockCsvBytes, response.getBody());
        assertEquals("text/csv", response.getHeaders().getContentType().toString());
        assertEquals("form-data; name=\"attachment\"; filename=\"output.csv\"", response.getHeaders().getContentDisposition().toString());
    }
}
