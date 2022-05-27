package se.quedro.challenge.data.xml;

public record SaleObject(
    String type,
    String id,
    int sizeSqm,
    String startingPrice,
    Address address
) {
}
