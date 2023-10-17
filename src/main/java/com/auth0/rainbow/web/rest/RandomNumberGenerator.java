package com.auth0.rainbow.web.rest;

import com.auth0.rainbow.domain.AppOrder;
import java.util.List;
import java.util.Random;

public class RandomNumberGenerator {

    private static final int RANDOM_NUMBER_LENGTH = 8;
    private static final Random random = new Random();

    public static Long generateUniqueRandomNumber(List<AppOrder> appOrders) {
        Long randomNumber;
        boolean isUnique = false;
        do {
            randomNumber = generateRandomNumber();
            isUnique = isUniqueNumber(randomNumber, appOrders);
        } while (!isUnique);
        return randomNumber;
    }

    private static Long generateRandomNumber() {
        return Long.valueOf(String.format("%08d", random.nextInt(100000000)));
    }

    private static boolean isUniqueNumber(Long number, List<AppOrder> appOrders) {
        for (AppOrder order : appOrders) {
            if (order.getPaymentID().equals(number)) {
                return false;
            }
        }
        return true;
    }
}
