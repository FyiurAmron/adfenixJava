package se.quedro.challenge;

import org.junit.jupiter.api.Test;
import se.quedro.challenge.external.SaleObjectConsumer;
import se.quedro.challenge.external.SaleObjectConsumerStubService;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Note: this test suite is verifying a stub, which makes little sense by itself.
 * It's needed to provide a stable TDD API contract for the related service (and its integration tests) instead.
 */
public class SaleObjectConsumerStubServiceTest {
    private final SaleObjectConsumerStubService saleObjectConsumerStubService = new SaleObjectConsumerStubService();

    @Test
    void throwsOnRepeatedStarts() {
        saleObjectConsumerStubService.startSaleObjectTransaction();
        assertThrows( IllegalStateException.class, saleObjectConsumerStubService::startSaleObjectTransaction );
    }

    @Test
    void throwsOnRepeatedCommits() {
        saleObjectConsumerStubService.startSaleObjectTransaction();
        saleObjectConsumerStubService.commitSaleObjectTransaction();
        assertThrows( IllegalStateException.class, saleObjectConsumerStubService::commitSaleObjectTransaction );
    }

    @Test
    void throwsOnCommitWithNoStart() {
        assertThrows( IllegalStateException.class, saleObjectConsumerStubService::commitSaleObjectTransaction );
    }

    @Test
    void throwsOnReportWithNoStart() {
        assertThrows(
            IllegalStateException.class,
            () -> saleObjectConsumerStubService.reportSaleObject( 10, "5", "foo", "bar", null )
        );
    }

    @Test
    void handlesASimpleTransaction() {
        assertNotNull( saleObjectConsumerStubService.getPriorityOrderAttribute() ); // just a sanity check
        saleObjectConsumerStubService.startSaleObjectTransaction();
        saleObjectConsumerStubService.reportSaleObject( 10, "5", "foo", "bar", null );
        saleObjectConsumerStubService.reportSaleObject( 10, "5", "foo", "bar", null );
        saleObjectConsumerStubService.commitSaleObjectTransaction();
    }

    @Test
    void detectsInvalidSorting() {
        // #setPriorityOrderAttribute() is added in the stub to allow changing its internal state for this test

        saleObjectConsumerStubService.startSaleObjectTransaction();

        saleObjectConsumerStubService.setPriorityOrderAttribute( SaleObjectConsumer.PriorityOrderAttribute.City );
        saleObjectConsumerStubService.reportSaleObject( 10, "5", "foo", "bar", null );
        saleObjectConsumerStubService.reportSaleObject( 10, "5", "goo", "bar", null );
        assertThrows(
            IllegalArgumentException.class,
            () -> saleObjectConsumerStubService.reportSaleObject( 10, "5", "aoo", "bar", null )
        );

        saleObjectConsumerStubService.setPriorityOrderAttribute( SaleObjectConsumer.PriorityOrderAttribute.SquareMeters );
        assertThrows(
            IllegalArgumentException.class,
            () -> saleObjectConsumerStubService.reportSaleObject( 5, "5", "foo", "bar", null )
        );
        saleObjectConsumerStubService.reportSaleObject( 15, "5", "foo", "bar", null );

        saleObjectConsumerStubService.setPriorityOrderAttribute( SaleObjectConsumer.PriorityOrderAttribute.PricePerSquareMeter );
        assertThrows(
            IllegalArgumentException.class,
            () -> saleObjectConsumerStubService.reportSaleObject( 10, "2", "aoo", "bar", null )
        );
        saleObjectConsumerStubService.reportSaleObject( 10, "15", "foo", "bar", null );

        saleObjectConsumerStubService.commitSaleObjectTransaction();
    }
}
