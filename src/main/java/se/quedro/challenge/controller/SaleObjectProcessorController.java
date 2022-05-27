package se.quedro.challenge.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.quedro.challenge.data.SaleObject;
import se.quedro.challenge.service.SaleObjectProcessor;

@Log
@RequiredArgsConstructor
@RestController
@RequestMapping( "v0/saleObjectProcessor" )
public class SaleObjectProcessorController {
    final private SaleObjectProcessor saleObjectProcessor;

    @PostMapping( value = "/processJson", consumes = { MediaType.APPLICATION_JSON_VALUE } )
    String processSaleObjectGroupJson( @RequestBody se.quedro.challenge.data.json.SaleObjectGroup saleObjectGroup ) {
        log.fine( "saleObjectGroup: " + saleObjectGroup );

        saleObjectProcessor.process( SaleObject.listFrom( saleObjectGroup ) );

        return "Processed.";
    }

    @PostMapping( value = "/processXml", consumes = { MediaType.TEXT_XML_VALUE, MediaType.APPLICATION_XML_VALUE } )
    String processSaleObjectGroupXml( @RequestBody se.quedro.challenge.data.xml.SaleObjectGroup saleObjectGroup ) {
        log.fine( "saleObjectGroup: " + saleObjectGroup );

        saleObjectProcessor.process( SaleObject.listFrom( saleObjectGroup ) );

        return "Processed.";
    }
}
