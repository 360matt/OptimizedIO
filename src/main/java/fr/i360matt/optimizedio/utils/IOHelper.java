package fr.i360matt.optimizedio.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;

public class IOHelper {


    public static void writeInt (int value, @NotNull byte[] array, int offset) {
        array[offset] = (byte) (value >> 24);
        array[offset+1] = (byte) (value >> 16);
        array[offset+2] = (byte) (value >> 8);
        array[offset+3] = (byte) value;
    }

    public static void writeInt (int value, byte[] array) {
        writeInt(value, array, 0);
    }

    public static int readInt (@NotNull byte[] array, int offset) {
        // not negative byte
        int b1 = array[offset] & 0xFF;
        int b2 = array[offset+1] & 0xFF;
        int b3 = array[offset+2] & 0xFF;
        int b4 = array[offset+3] & 0xFF;
        return ((b1 << 24) | (b2 << 16) | (b3 << 8) | b4);
    }

    public static int readInt (byte[] array) {
        return readInt(array, 0);
    }

    public static void writeShort (short value, @NotNull byte[] array, int offset) {
        array[offset] = (byte) (value >> 8);
        array[offset+1] = (byte) value;
    }

    public static void writeShort (short value, byte[] array) {
        writeShort(value, array, 0);
    }

    public static short readShort (@NotNull byte[] array, int offset) {
        // not negative byte
        int b1 = array[offset] & 0xFF;
        int b2 = array[offset+1] & 0xFF;
        return (short) ((b1 << 8) | b2);
    }

    public static short readShort (byte[] array) {
        return readShort(array, 0);
    }

    public static void writeByte (byte value, @NotNull byte[] array, int offset) {
        array[offset] = value;
    }

    public static void writeByte (byte value, byte[] array) {
        writeByte(value, array, 0);
    }

    public static byte readByte (@NotNull byte[] array, int offset) {
        // not negative byte
        return (byte) (array[offset] & 0xFF);
    }

    public static byte readByte (byte[] array) {
        return readByte(array, 0);
    }


    public static void writeLong (long value, @NotNull byte[] array, int offset) {
        array[offset] = (byte) (value >> 56);
        array[offset+1] = (byte) (value >> 48);
        array[offset+2] = (byte) (value >> 40);
        array[offset+3] = (byte) (value >> 32);
        array[offset+4] = (byte) (value >> 24);
        array[offset+5] = (byte) (value >> 16);
        array[offset+6] = (byte) (value >> 8);
        array[offset+7] = (byte) value;
    }

    public static void writeLong (long value, byte[] array) {
        writeLong(value, array, 0);
    }

    public static long readLong (@NotNull byte[] array, int offset) {
        // not negative byte
        long b1 = array[offset] & 0xFF;
        long b2 = array[offset+1] & 0xFF;
        long b3 = array[offset+2] & 0xFF;
        long b4 = array[offset+3] & 0xFF;
        long b5 = array[offset+4] & 0xFF;
        long b6 = array[offset+5] & 0xFF;
        long b7 = array[offset+6] & 0xFF;
        long b8 = array[offset+7] & 0xFF;
        return ((b1 << 56) | (b2 << 48) | (b3 << 40) | (b4 << 32) | (b5 << 24) | (b6 << 16) | (b7 << 8) | b8);
    }

    public static long readLong (byte[] array) {
        return readLong(array, 0);
    }


    public static void writeFloat (float value, @NotNull byte[] array, int offset) {
        writeInt(Float.floatToIntBits(value), array, offset);
    }

    public static void writeFloat (float value, byte[] array) {
        writeFloat(value, array, 0);
    }

    public static float readFloat (@NotNull byte[] array, int offset) {
        return Float.intBitsToFloat(readInt(array, offset));
    }

    public static float readFloat (byte[] array) {
        return readFloat(array, 0);
    }


    public static void writeDouble (double value, @NotNull byte[] array, int offset) {
        writeLong(Double.doubleToLongBits(value), array, offset);
    }

    public static void writeDouble (double value, byte[] array) {
        writeDouble(value, array, 0);
    }

    public static double readDouble (@NotNull byte[] array, int offset) {
        return Double.longBitsToDouble(readLong(array, offset));
    }

    public static double readDouble (byte[] array) {
        return readDouble(array, 0);
    }


    public static void writeChar (char value, @NotNull byte[] array, int offset) {
        array[offset] = (byte) (value >> 8);
        array[offset+1] = (byte) value;
    }

    public static void writeChar (char value, byte[] array) {
        writeChar(value, array, 0);
    }

    public static char readChar (@NotNull byte[] array, int offset) {
        // not negative byte
        int b1 = array[offset] & 0xFF;
        int b2 = array[offset+1] & 0xFF;
        return (char) ((b1 << 8) | b2);
    }

    public static char readChar (byte[] array) {
        return readChar(array, 0);
    }

    public static int writeRawUTF8 (@NotNull String value, @NotNull byte[] array, int offset) {
        // write string in UTF-8 (from 1 to 4 bytes)
        int bytes = 0;
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c < 0x80) {
                array[offset + bytes++] = (byte) c;
            } else if (c < 0x800) {
                array[offset + bytes++] = (byte) (0xC0 | (c >> 6));
                array[offset + bytes++] = (byte) (0x80 | (c & 0x3F));
            } else if (c < 0xD800 || c >= 0xE000) {
                array[offset + bytes++] = (byte) (0xE0 | (c >> 12));
                array[offset + bytes++] = (byte) (0x80 | ((c >> 6) & 0x3F));
                array[offset + bytes++] = (byte) (0x80 | (c & 0x3F));
            } else {
                char c2 = value.charAt(++i);
                int codePoint = ((c - 0xD800) << 10) + (c2 - 0xDC00) + 0x10000;
                array[offset + bytes++] = (byte) (0xF0 | (codePoint >> 18));
                array[offset + bytes++] = (byte) (0x80 | ((codePoint >> 12) & 0x3F));
                array[offset + bytes++] = (byte) (0x80 | ((codePoint >> 6) & 0x3F));
                array[offset + bytes++] = (byte) (0x80 | (codePoint & 0x3F));
            }
        }
        return bytes;
    }

    @NotNull
    @Contract(value = "_, _, _ -> new", pure = true)
    public static String readRawUTF8 (@NotNull byte[] array, int offset, int length) {
        return new String(array, offset, length, StandardCharsets.UTF_8);
    }

    public static void writeBool (boolean value, @NotNull byte[] array, int offset) {
        array[offset] = (byte) (value ? 0x01 : 0x00);
    }

    public static void writeBool (boolean value, byte[] array) {
        writeBool(value, array, 0);
    }

    public static boolean readBool (@NotNull byte[] array, int offset) {
        return array[offset] == 0x01;
    }

    public static boolean readBool (byte[] array) {
        return readBool(array, 0);
    }


    public static void writeRawBytes (byte[] value, byte[] array, int offset) {
        System.arraycopy(value, 0, array, offset, value.length);
    }

    public static void readRawBytes (byte[] array, byte[] dest, int offset, int length) {
        System.arraycopy(array, offset, dest, 0, length);
    }

    @NotNull
    public static byte[] readRawBytes (byte[] array, int offset, int length) {
        byte[] dest = new byte[length];
        readRawBytes(array, dest, offset, length);
        return dest;
    }

}
