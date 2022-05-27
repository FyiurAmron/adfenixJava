package se.quedro.challenge.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import se.quedro.challenge.data.SaleObject;
import se.quedro.challenge.external.SaleObjectConsumer;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Log
@Service
public class SaleObjectProcessor {
    private final SaleObjectConsumer saleObjectConsumer;

    public SaleObjectProcessor( SaleObjectConsumer saleObjectConsumer ) {
        this.saleObjectConsumer = saleObjectConsumer;
    }

    public void process( List<SaleObject> saleObjects ) {
        log.fine( "saleObjects: "
                      + saleObjects.stream()
                                   .map( Record::toString )
                                   .collect( Collectors.joining( "," ) )
        );

        Comparator<SaleObject> saleObjectComparator = switch ( saleObjectConsumer.getPriorityOrderAttribute() ) {
            case City -> Comparator.comparing( saleObject -> saleObject.address().city() );
            case SquareMeters -> Comparator.comparing( SaleObject::sizeSqm );
            case PricePerSquareMeter -> Comparator.comparing( SaleObject::startingPricePerSquareMeter );
        };

        saleObjectConsumer.startSaleObjectTransaction();

        saleObjects.stream()
                   .sorted( saleObjectComparator )
                   .forEach( saleObject -> saleObjectConsumer.reportSaleObject(
                       saleObject.sizeSqm(),
                       "" + saleObject.startingPricePerSquareMeter(),
                       saleObject.address().city(),
                       saleObject.address().street(),
                       saleObject.address().floor()
                   ) );

        saleObjectConsumer.commitSaleObjectTransaction();
    }
}
