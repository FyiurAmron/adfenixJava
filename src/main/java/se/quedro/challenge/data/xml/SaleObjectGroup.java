package se.quedro.challenge.data.xml;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public record SaleObjectGroup
    (
        int count,
        @JsonProperty( "SaleObject" )
        ArrayList<SaleObject> saleObjects
    ) {
}
