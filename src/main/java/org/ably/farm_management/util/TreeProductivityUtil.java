package org.ably.farm_management.util;

import org.ably.farm_management.domain.enums.TreeStatus;

public class TreeProductivityUtil {
    public static double calculateProductivity(TreeStatus status) {
        switch (status) {
            case YOUNG:
                return 2.5;
            case Mature :
                return 12.0;
            case OLD:
                return 20.0;
            case NON_PRODUCTIVE :
                return 0.0;
            default:
                throw new IllegalArgumentException("Invalid tree status");
        }
    }
}
