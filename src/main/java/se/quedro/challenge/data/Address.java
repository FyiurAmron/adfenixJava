package se.quedro.challenge.data;

public record Address(
    String city,
    String street,
    Integer floor
) {
    public static Address from( se.quedro.challenge.data.json.PostalAddress address ) {
        return new Address(
            address.city(),
            address.street(),
            address.floor()
        );
    }

    public static Address from( se.quedro.challenge.data.xml.Address address ) {
        return new Address(
            address.city(),
            address.street(),
            address.floor()
        );
    }
}
