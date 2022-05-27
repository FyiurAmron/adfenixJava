package se.quedro.challenge.data.json;

public record SaleObject(
    String type,
    int id,
    String sizeSqm,
    int startingPrice,
    PostalAddress postalAddress
) {
}
