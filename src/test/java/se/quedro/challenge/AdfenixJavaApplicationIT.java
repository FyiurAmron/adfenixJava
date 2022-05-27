package se.quedro.challenge;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
public class AdfenixJavaApplicationIT {
    public static final String SALE_OBJECT_PROCESSOR_URL = "/v0/saleObjectProcessor";
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void parsesJsonCorrectly() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );
        HttpEntity<String> httpEntity =
            new HttpEntity<>( Files.readString( Path.of( "src/test/data/SaleObjects.json" ) ), headers );
        ResponseEntity<String> response = restTemplate.
            postForEntity(
                SALE_OBJECT_PROCESSOR_URL + "/processJson",
                httpEntity,
                String.class
            );
        assertEquals( "Processed.", response.getBody() );
    }

    @Test
    void parsesXmlCorrectly() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.TEXT_XML );
        HttpEntity<String> httpEntity =
            new HttpEntity<>( Files.readString( Path.of( "src/test/data/SaleObjects.xml" ) ), headers );
        ResponseEntity<String> response = restTemplate.
            postForEntity(
                SALE_OBJECT_PROCESSOR_URL + "/processXml",
                httpEntity,
                String.class
            );
        assertEquals( "Processed.", response.getBody() );
    }
}
