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
    public static final String TEST_DATA_PATH = "src/test/data/";
    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<String> postHttp( MediaType mediaType, String testDataFilename, String endpointPath )
        throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( mediaType );
        HttpEntity<String> httpEntity =
            new HttpEntity<>( Files.readString( Path.of( TEST_DATA_PATH + testDataFilename ) ), headers );
        return restTemplate.postForEntity( SALE_OBJECT_PROCESSOR_URL + endpointPath, httpEntity, String.class );
    }

    @Test
    void parsesJsonCorrectly() throws IOException {
        ResponseEntity<String> responseEntity =
            postHttp( MediaType.APPLICATION_JSON, "SaleObjects.json", "/processJson" );

        assertEquals( "Processed.", responseEntity.getBody() );
    }

    @Test
    void parsesXmlCorrectly() throws IOException {
        ResponseEntity<String> responseEntity =
            postHttp( MediaType.TEXT_XML, "SaleObjects.xml", "/processXml" );

        assertEquals( "Processed.", responseEntity.getBody() );
    }
}
