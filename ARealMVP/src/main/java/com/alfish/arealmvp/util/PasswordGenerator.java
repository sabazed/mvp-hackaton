package com.alfish.arealmvp.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
public class PasswordGenerator {

    public String generateSecureRandomPassword() {
        int[] symbolRatio = generateIntArray(4, 8);
        Stream<Character> pwdStream =
                Stream.concat(getRandomNumbers(symbolRatio[0]),
                Stream.concat(getRandomSpecialChars(symbolRatio[1]),
                Stream.concat(getRandomAlphabets(symbolRatio[2], true), getRandomAlphabets(symbolRatio[3], false))));
        List<Character> charList = pwdStream.collect(Collectors.toList());
        Collections.shuffle(charList);
        return charList.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    private Stream<Character> getRandomNumbers(int count) {
        Random random = new SecureRandom();
        IntStream specialChars = random.ints(count, 48, 58);
        return specialChars.mapToObj(data -> (char) data);
    }

    private Stream<Character> getRandomSpecialChars(int count) {
        Random random = new SecureRandom();
        IntStream specialChars = random.ints(count, 33, 47);
        return specialChars.mapToObj(data -> (char) data);
    }

    private Stream<Character> getRandomAlphabets(int count, boolean uppercase) {
        Random random = new SecureRandom();
        IntStream specialChars = uppercase
                ? random.ints(count, 65, 91)
                : random.ints(count, 97, 123);
        return specialChars.mapToObj(data -> (char) data);
    }

    private int[] generateIntArray(int size, int targetSum) {
        if (size < 1 || targetSum < size) throw new IllegalArgumentException("Invalid input");

        Random rand = new Random();
        int[] arr = new int[size];
        // Initialize the array with values between 1 and targetSum - size + 1
        for (int i = 0; i < size - 1; i++) {
            arr[i] = rand.nextInt(targetSum - size + i) + 1;
            targetSum -= arr[i];
        }
        // The last element is assigned the remaining value to ensure the sum is exactly targetSum
        arr[size - 1] = targetSum;

        return arr;
    }

}
