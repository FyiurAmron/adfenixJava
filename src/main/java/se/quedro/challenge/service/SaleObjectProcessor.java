package se.quedro.challenge.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import se.quedro.challenge.data.SaleObject;

import java.util.List;
import java.util.stream.Collectors;

@Log
@Service
public class SaleObjectProcessor {
    public void process( List<SaleObject> saleObjects ) {
        log.fine( "saleObjects: "
                      + saleObjects.stream()
                                   .map( Record::toString )
                                   .collect( Collectors.joining( "," ) )
        );
    }
}
