package se.quedro.challenge.data;

import java.util.List;

public record SaleObject(
    int id,
    SaleObjectType type,
    int sizeSqm,
    int startingPrice,
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

    public static SaleObject from( se.quedro.challenge.data.json.SaleObject saleObject ) {
        return new SaleObject(
            saleObject.id(),
            SaleObjectType.from( saleObject.type() ),
            Integer.parseInt( saleObject.sizeSqm() ),
            saleObject.startingPrice(),
            Address.from( saleObject.postalAddress() )
        );
    }

    public static SaleObject from( se.quedro.challenge.data.xml.SaleObject saleObject ) {
        return new SaleObject(
            Integer.parseInt( saleObject.id() ),
            SaleObjectType.from( saleObject.type() ),
            saleObject.sizeSqm(),
            Integer.parseInt( saleObject.startingPrice().replace( ".", "" ) ),
            Address.from( saleObject.address() )
        );
    }
}
