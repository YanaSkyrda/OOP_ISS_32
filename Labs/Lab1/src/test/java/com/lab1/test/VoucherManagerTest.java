package com.lab1.test;

import com.lab1.main.VoucherManager;
import com.lab1.main.builders.VoucherComparatorBuilder;
import com.lab1.main.builders.VoucherPredicateBuilder;
import com.lab1.main.vouchers.*;
import com.lab1.main.vouchers.enums.Meals;
import com.lab1.main.vouchers.enums.Transport;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class VoucherManagerTest {
    private static final List<Voucher> VOUCHERS = new ArrayList<>() {
        {
            add(new CruiseVoucher("Port Elizabeth, South Africa", Transport.PLANE, 21, 5000,
                    "Take a chance and try our Trans-Pacific cruise!"));
            add(new ExcursionVoucher("Rivne, Ukraine", Transport.BUS, Meals.ONE_MEAL_A_DAY, 1,
                    30, "Visit famous Rivne Zoo with your family!"));
            add(new ShoppingVoucher("Warsaw, Poland", Transport.TRAIN, Meals.NO_MEALS, 1, 100,
                    "Set out for a day-trip to the best boutiques in a capital of Poland!"));
            add(new VacationVoucher("Phuket, Thailand", Transport.PLANE, Meals.THREE_MEALS_A_DAY, 14,
                    3500, "Spend your vacation on exotic beaches of Thailand!"));
            add(new WellnessVoucher("Bern, Switzerland", Transport.BUS, Meals.TWO_MEALS_A_DAY, 14,
                    2000, "Best treatment services here!"));
        }
    };
    private static final VoucherManager VOUCHER_MANAGER = new VoucherManager(VOUCHERS);
    private static final VoucherComparatorBuilder COMPARATOR_BUILDER = new VoucherComparatorBuilder();
    private static final VoucherPredicateBuilder PREDICATE_BUILDER = new VoucherPredicateBuilder();

    private static final PrintStream CONSOLE_OUT = System.out;
    private final ByteArrayOutputStream outData = new ByteArrayOutputStream();

    @BeforeEach
    public void setOutputStream() {
        System.setOut(new PrintStream(outData));
    }

    @AfterEach
    public void clearFilters() {
        VOUCHER_MANAGER.clearFilters();
        COMPARATOR_BUILDER.reset();
        PREDICATE_BUILDER.reset();
    }

    @Test
    public void testFilteringAndSorting() {
        VOUCHER_MANAGER.filter(PREDICATE_BUILDER.byPrice(100, 5000)
                                                .byTransport(Transport.TRAIN, Transport.PLANE)
                                                .getPredicate())
                       .sort(COMPARATOR_BUILDER.byDestination().getComparator());

        List<Voucher> selected = VOUCHER_MANAGER.getSelectedVouchers();
        Assertions.assertTrue(selected.size() == 3 && selected.get(0) == VOUCHERS.get(3) &&
                               selected.get(1) == VOUCHERS.get(0) && selected.get(2) == VOUCHERS.get(2));
    }

    @Test
    public void testShowingVouchers() {
        VOUCHER_MANAGER.filter(PREDICATE_BUILDER.byPrice(100, 5000)
                .byTransport(Transport.PLANE)
                .getPredicate())
                .sort(COMPARATOR_BUILDER.byDestination().getComparator());

        VOUCHER_MANAGER.showSelectedVouchers();
        String output = outData.toString().trim().replace("\r", "");

        Assertions.assertEquals("-----VACATION VOUCHER-----\n" +
                                "Destination: Phuket, Thailand.\n" +
                                "Transport: plane.\n" +
                                "Meals: three meals a day provided.\n" +
                                "Number of days: 14.\n" +
                                "Price: $3500.0.\n" +
                                "Spend your vacation on exotic beaches of Thailand!\n" +
                                "--------------------------\n" +
                                "\n" +
                                "------CRUISE VOUCHER------\n" +
                                "Destination: Port Elizabeth, South Africa.\n" +
                                "Transport: plane.\n" +
                                "Meals: free food 24/7.\n" +
                                "Number of days: 21.\n" +
                                "Price: $5000.0.\n" +
                                "Take a chance and try our Trans-Pacific cruise!\n" +
                                "--------------------------", output);
    }

    @AfterAll
    public static void restoreStream() { System.setOut(CONSOLE_OUT); }
}
