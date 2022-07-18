package fr.i360matt.optimizedio.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class IOType {

    public static int INT_SIZE () { return 4; }
    public static int SHORT_SIZE () { return 2; }
    public static int BYTE_SIZE () { return 1; }
    public static int LONG_SIZE () { return 8; }
    public static int FLOAT_SIZE () { return 4; }
    public static int DOUBLE_SIZE () { return 8; }
    public static int CHAR_SIZE () { return 2; }
    public static int STRING_SIZE (int value) { return 4 + (value*2); }
    public static int BOOL_SIZE () { return 1; }
    public static int BYTES_SIZE (int value) { return 4 + value; }

    @Contract(pure = true)
    public static int UTF8_SIZE (@NotNull String str) {
        int bytes = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c < 0x80) bytes += 1;
            else if (c < 0x800) bytes += 2;
            else if (c < 0xD800 || c >= 0xE000) bytes += 3;
            else bytes += 4;
        }
        return bytes;
    }

    @Contract(pure = true)
    public static int ASCII_SIZE (@NotNull String str) {
        int bytes = 0;
        for (char c : str.toCharArray()) {
            if (c < 0x80) bytes += 1;
            else bytes += 2;
        }
        return bytes;
    }

    public static int getSize (final Class<?> clazz) {
        if (clazz == int.class) {
            return INT_SIZE();
        } else if (clazz == short.class) {
            return SHORT_SIZE();
        } else if (clazz == byte.class) {
            return BYTE_SIZE();
        } else if (clazz == long.class) {
            return LONG_SIZE();
        } else if (clazz == float.class) {
            return FLOAT_SIZE();
        } else if (clazz == double.class) {
            return DOUBLE_SIZE();
        } else if (clazz == char.class) {
            return CHAR_SIZE();
        } else if (clazz == boolean.class) {
            return BOOL_SIZE();
        } else if (clazz == String.class) {
            return STRING_SIZE(0);
        } else if (clazz == byte[].class) {
            return BYTES_SIZE(0);
        } else {
            return 0;
        }
    }

}
