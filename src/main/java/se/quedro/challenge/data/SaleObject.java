package se.quedro.challenge.data;

import java.util.List;

public record SaleObject(
    int id,
    SaleObjectType type,
    int sizeSqm,
    int startingPrice,
    int startingPricePerSquareMeter,
    Address address
) {
    public static List<SaleObject> listFrom( se.quedro.challenge.data.json.SaleObjectGroup saleObjectGroup ) {
        if ( saleObjectGroup.saleObjects().size() != saleObjectGroup.numberOfSaleObjects() ) {
            throw new IllegalArgumentException( "sale objects count: actual != expected" );
        }
        return saleObjectGroup.saleObjects().stream().map( SaleObject::from ).toList();
    }

    public static List<SaleObject> listFrom( se.quedro.challenge.data.xml.SaleObjectGroup saleObjectGroup ) {
        if ( saleObjectGroup.saleObjects().size() != saleObjectGroup.count() ) {
            throw new IllegalArgumentException( "sale objects count: actual != expected" );
        }
        return saleObjectGroup.saleObjects().stream().map( SaleObject::from ).toList();
    }

    private static int calcStartingPricePerSquareMeter( int startingPrice, int sizeSqm ) {
        return startingPrice * 1000 / sizeSqm;
    }

    public static SaleObject from( se.quedro.challenge.data.json.SaleObject saleObject ) {
        int sizeSqm = Integer.parseInt( saleObject.sizeSqm() );
        int startingPrice = saleObject.startingPrice();

        return new SaleObject(
            saleObject.id(),
            SaleObjectType.from( saleObject.type() ),
            sizeSqm,
            startingPrice,
            calcStartingPricePerSquareMeter( startingPrice, sizeSqm ),
            Address.from( saleObject.postalAddress() )
        );
    }

    public static SaleObject from( se.quedro.challenge.data.xml.SaleObject saleObject ) {
        int sizeSqm = saleObject.sizeSqm();
        int startingPrice = Integer.parseInt( saleObject.startingPrice().replace( ".", "" ) );

        return new SaleObject(
            Integer.parseInt( saleObject.id() ),
            SaleObjectType.from( saleObject.type() ),
            sizeSqm,
            startingPrice,
            calcStartingPricePerSquareMeter( startingPrice, sizeSqm ),
            Address.from( saleObject.address() )
        );
    }
}
