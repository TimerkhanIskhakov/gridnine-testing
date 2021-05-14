package com.gridnine.testing;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FilterModule {

    public static Map<String, FilterRule> rules = new HashMap<>();

    public static final FilterRule DEPARTURE_DATE_IS_BEFORE_NOW = new FilterRule<Flight>("departureDateIsBeforeNow",
            flight -> flight
                    .getSegments()
                    .get(0)
                    .getDepartureDate()
                    .isBefore(LocalDateTime.now()));

    public static final FilterRule WRONG_ARRIVAL_DATE = new FilterRule<Flight>("wrongArrivalDate",
            flight -> flight
                    .getSegments()
                    .stream()
                    .anyMatch(x -> x.getArrivalDate().isBefore(x.getDepartureDate())));

    public static final FilterRule TRANSFER_MORE_THEN_TWO_HOURS = new FilterRule<Flight>("transferMoreThanTwoHours",
            flight -> {
                var segmentsCalculate = flight
                        .getSegments()
                        .stream()
                        .map(x -> Duration.between(x.getDepartureDate(), x.getArrivalDate()))
                        .reduce(Duration::plus)
                        .get();

                var flightCalculate = Duration.between(flight.getSegments().get(0).getDepartureDate(),
                                flight.getSegments().get(flight.getSegments().size() - 1).getArrivalDate());

                return flightCalculate
                        .minus(segmentsCalculate)
                        .compareTo(Duration.ofHours(2)) >= 0;
            });

    public static <T> List<T> filter (final List<T> list, Predicate<T>... predicates) {

        return list.stream()
                .filter(Arrays.stream(predicates).reduce(x -> true, Predicate::and))
                .collect(Collectors.toList());
    }

    public static <T> List<T> filter (final List<T> list, FilterRule<T>... rules) {

        return filter(list, Arrays.stream(rules)
                .map(FilterRule::getPredicate)
                .reduce(x -> true, Predicate::and));
    }

    public static class FilterRule<T> implements Serializable {
        String name;
        Predicate<T> predicate;

        public FilterRule(String name, Predicate<T> predicate) {
            this.name = name;
            this.predicate = predicate;
            rules.put(getName(), this);
        }

        public String getName() {
            return name;
        }

        public Predicate<T> getPredicate() {
            return predicate;
        }
    }
}