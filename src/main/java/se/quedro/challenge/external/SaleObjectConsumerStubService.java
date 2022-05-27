package se.quedro.challenge.external;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

/**
 * Note that this should be basically a test-only stub, but since we don't have any actual implementation/API definition
 * to run/test against (e.g. Feign def etc.), it's provided here for the sake of being able to run the app at all.
 */
@Log
@Service
public class SaleObjectConsumerStubService implements SaleObjectConsumer {
    private boolean started;
    @Getter
    @Setter
    private boolean technicalProblem;
    @Setter
    private PriorityOrderAttribute priorityOrderAttribute = PriorityOrderAttribute.City; // change this if you want
    private String lastCity = "";
    private int lastSquareMeters;
    private int lastPricePerSquareMeter;

    @Override
    public PriorityOrderAttribute getPriorityOrderAttribute() {
        return priorityOrderAttribute;
    }

    @Override
    public void startSaleObjectTransaction() {
        if ( started ) {
            throw new IllegalStateException( "already started" );
        }
        started = true;
    }

    @Override
    public void reportSaleObject(
        int squareMeters,
        String pricePerSquareMeter,
        String city,
        String street,
        Integer floor
    ) throws TechnicalException {
        if ( !started ) {
            throw new IllegalStateException( "not started yet" );
        }

        int pricePerSquareMeterInt = Integer.parseInt( pricePerSquareMeter );

        switch ( priorityOrderAttribute ) {
            case City:
                if ( city.compareToIgnoreCase( lastCity ) < 0 ) {
                    throw new IllegalArgumentException( "object is not sorted by city" );
                }
                break;
            case SquareMeters:
                if ( squareMeters < lastSquareMeters ) {
                    throw new IllegalArgumentException( "object is not sorted by square meters" );
                }
                break;
            case PricePerSquareMeter:
                if ( pricePerSquareMeterInt < lastPricePerSquareMeter ) {
                    throw new IllegalArgumentException( "object is not sorted by square meters" );
                }
                break;
        }

        lastCity = city;
        lastSquareMeters = squareMeters;
        lastPricePerSquareMeter = pricePerSquareMeterInt;

        log.fine(
            "reportSaleObject() called!\n" +
                "squareMeters: " + squareMeters + "\n" +
                "pricePerSquareMeter: " + pricePerSquareMeter + "\n" +
                "city: " + city + "\n" +
                "street: " + street + "\n" +
                "floor: " + floor + "\n"
        );
    }

    @Override
    public void commitSaleObjectTransaction() {
        if ( !started ) {
            throw new IllegalStateException( "not started yet" );
        }
        started = false;
    }
}
