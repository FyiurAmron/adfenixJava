package se.quedro.challenge.data.json;

public record SaleObject(
    String type,
    int id,
    String sizeSqm,
    long startingPrice,
    PostalAddress postalAddress
) {
}
