package org.ably.farm_management.util;

import org.ably.farm_management.domain.enums.TreeStatus;

import java.time.LocalDate;

public class determineTreeStatusUtil {
    public static TreeStatus determineTreeStatus(LocalDate plantedAt) {
        int age = LocalDate.now().getYear() - plantedAt.getYear();

        if (age < 3) {
            return TreeStatus.YOUNG;
        } else if (age < 10) {
            return TreeStatus.PRODUCTIVE;
        } else if (age < 20) {
            return TreeStatus.NON_PRODUCTIVE;
        } else {
            throw new IllegalArgumentException("Invalid tree age");
        }
    }
}
