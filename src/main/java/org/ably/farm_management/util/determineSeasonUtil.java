package org.ably.farm_management.util;

import org.ably.farm_management.domain.enums.SeasonType;

import java.time.LocalDate;
import java.time.Month;

public class determineSeasonUtil {
    public static SeasonType determineSeason(LocalDate date) {
        Month month = date.getMonth();

        switch (month) {
            case DECEMBER:
            case JANUARY:
            case FEBRUARY:
                return SeasonType.WINTER;
            case MARCH:
            case APRIL:
            case MAY:
                return SeasonType.SPRING;
            case JUNE:
            case JULY:
            case AUGUST:
                return SeasonType.SUMMER;
            case SEPTEMBER:
            case OCTOBER:
            case NOVEMBER:
                return SeasonType.FALL;
            default:
                throw new IllegalArgumentException("Invalid month");
        }
    }
}
