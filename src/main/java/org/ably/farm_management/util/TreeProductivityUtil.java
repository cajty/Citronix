package org.ably.farm_management.util;

import org.ably.farm_management.domain.enums.TreeStatus;

public class TreeProductivityUtil {
    public static double calculateProductivity(TreeStatus status) {
        switch (status) {
            case YOUNG:
                return 2.5;  //  (< 3 ans)
            case PRODUCTIVE:
                return 12.0;  //  (3-10 ans)
            case NON_PRODUCTIVE:
                return 20.0;  //  (> 10 ans)
            default:
                throw new IllegalArgumentException("Invalid tree status");
        }
    }
}
