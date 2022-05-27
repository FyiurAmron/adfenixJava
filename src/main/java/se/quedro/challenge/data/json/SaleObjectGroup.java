package se.quedro.challenge.data.json;

import java.util.ArrayList;

public record SaleObjectGroup(
    int numberOfSaleObjects,
    ArrayList<SaleObject> saleObjects
) {
}
