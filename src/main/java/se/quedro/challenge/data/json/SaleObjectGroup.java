package se.quedro.challenge.data.json;

import java.util.List;

public record SaleObjectGroup(
    int numberOfSaleObjects,
    List<SaleObject> saleObjects
) {
}
