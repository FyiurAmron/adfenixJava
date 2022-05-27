package se.quedro.challenge.data;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum SaleObjectType {
    Apartment( "apt" ),
    House( "house" ),
    ;

    private static Map<String, SaleObjectType> map = null;
    private final String shortName;

    SaleObjectType( String shortName ) {
        this.shortName = shortName;
    }

    private static Map<String, SaleObjectType> getMap() {
        if ( map == null ) {
            map = Arrays.stream( SaleObjectType.values() )
                        .collect( Collectors.toMap( v -> v.shortName, v -> v ) );
        }
        return map;
    }

    public static SaleObjectType from( String shortName ) {
        return getMap().get( shortName.toLowerCase() );
    }
}
