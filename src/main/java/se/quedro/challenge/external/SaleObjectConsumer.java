package se.quedro.challenge.external;

public interface SaleObjectConsumer {
    /**
     * Gets the attribute by which to sort/order the following {@link #reportSaleObject} calls
     *
     * @return the attribute by which to order the data sequence
     */
    PriorityOrderAttribute getPriorityOrderAttribute();

    /**
     * Must be called <b>before</b> using {@link #reportSaleObject}
     */
    void startSaleObjectTransaction();

    /**
     * Called once per SaleObject.
     * Must be called in order decided by a prior call to {@link #getPriorityOrderAttribute()}
     *
     * @param squareMeters        The size of the house or apartment in square meters.
     * @param pricePerSquareMeter The price of the house or apartment divided by square meters.
     *                            The price must be in fixed-point representation with scaling 1/1000.
     *                            Also, it's absurd since there's no indication how to sort this: it's a String,
     *                            so it should be sorted lexicographically, but it's actually a numeric value...
     * @param city                The city where the house is located.
     * @param street              The street where the house is located.
     * @param floor               The floor if eligible, otherwise null.
     * @throws TechnicalException if an object can not be reported successfully
     */
    void reportSaleObject( int squareMeters, String pricePerSquareMeter, String city, String street, Integer floor )
        throws TechnicalException;

    /**
     * Must be called <b>after</b> using {@link #reportSaleObject}
     */
    void commitSaleObjectTransaction();

    // ??? is this ascending or descending? I'll assume ascending...
    // ??? also, why doesn't this have such obvious value as just `Price`?
    enum PriorityOrderAttribute {
        City,
        SquareMeters,
        PricePerSquareMeter,
        ;
    }

    class TechnicalException extends RuntimeException {
    }
}
