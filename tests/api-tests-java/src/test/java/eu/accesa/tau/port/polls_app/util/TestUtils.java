package eu.accesa.tau.port.polls_app.util;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Random;

public class TestUtils {
    private TestUtils() {
    }

    public static String getRandomString(int length, boolean mixNumbers) {
        String[] charactersPool = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        String[] numericCharactersPool = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

        if (mixNumbers) {
            charactersPool = ArrayUtils.addAll(charactersPool, numericCharactersPool);
        }

        Random r = new Random();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < length; i++) {
            result.append(charactersPool[r.nextInt(charactersPool.length - 1)]);
        }

        return result.toString();
    }

    public static int getRandomNumber(int min, int max) {
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }
}
