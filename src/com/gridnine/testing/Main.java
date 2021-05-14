package com.gridnine.testing;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> list = FlightBuilder.createFlights();

        System.out.println(FilterModule.filter(list,
                FilterModule.DEPARTURE_DATE_IS_BEFORE_NOW));

        System.out.println("_________________");

        System.out.println(FilterModule.filter(list,
                FilterModule.WRONG_ARRIVAL_DATE));

        System.out.println("_________________");

        System.out.println(FilterModule.filter(list,
                FilterModule.TRANSFER_MORE_THEN_TWO_HOURS));

        System.out.println("_________________");

        /*
        System.out.println(FilterModule.filter(list,
                FilterModule.DEPARTURE_DATE_IS_BEFORE_NOW,
                FilterModule.TRANSFER_MORE_THEN_TWO_HOURS,
                FilterModule.WRONG_ARRIVAL_DATE));

        System.out.println(FilterModule.filter(list,
                new FilterModule.FilterRule<Flight>("departureDateIsAfterNow", x -> x
                        .getSegments()
                        .get(0)
                        .getDepartureDate()
                        .isAfter(LocalDateTime.now())),
                FilterModule.TRANSFER_MORE_THEN_TWO_HOURS));
        */
    }
}
