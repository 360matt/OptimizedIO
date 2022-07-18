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
        return (short) ((array[offset] << 8) | array[offset+1]);
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
        return array[offset];
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
        return (char) readShort(array, offset);
    }

    public static char readChar (byte[] array) {
        return readChar(array, 0);
    }

    public static void writeRawString (@NotNull String value, @NotNull byte[] array, int offset) {
        for (int i = 0; i < value.length(); i++) {
            writeChar(value.charAt(i), array, offset + (i * 2));
        }
    }

    public static void writeRawASCII (@NotNull String value, @NotNull byte[] array, int offset) {
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c < 0x80) {
                writeByte((byte) c, array, offset + i);
            } else {
                writeShort((short) c, array, offset + (i * 2));
            }
        }
    }

    public static int writeRawUTF8 (@NotNull String value, @NotNull byte[] array, int offset) {
        // write string in UTF-8 (from 1 to 4 bytes)
        int bytes = 0;
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c < 0x80) {
                writeByte((byte) c, array, offset + i);
                bytes += 1;
            } else if (c < 0x800) {
                writeByte((byte) (0xC0 | (c >> 6)), array, offset + i);
                writeByte((byte) (0x80 | (c & 0x3F)), array, offset + i + 1);
                bytes += 2;
            } else if (c < 0xD800 || c >= 0xE000) {
                writeByte((byte) (0xE0 | (c >> 12)), array, offset + i);
                writeByte((byte) (0x80 | ((c >> 6) & 0x3F)), array, offset + i + 1);
                writeByte((byte) (0x80 | (c & 0x3F)), array, offset + i + 2);
                bytes += 3;
            } else {
                i++;
                writeByte((byte) (0xF0 | (0)), array, offset + i);
                writeByte((byte) (0x80 | ((c >> 12) & 0x3F)), array, offset + i + 1);
                writeByte((byte) (0x80 | ((c >> 6) & 0x3F)), array, offset + i + 2);
                writeByte((byte) (0x80 | (c & 0x3F)), array, offset + i + 3);
                bytes += 4;
            }
        }
        return bytes;
    }

    public static void writeString (@NotNull String value, @NotNull byte[] array, int offset) {
        writeInt(value.length(), array, offset);
        writeRawString(value, array, offset + IOType.INT_SIZE());
    }

    public static void writeString (@NotNull String value, byte[] array) {
        writeString(value, array, 0);
    }

    @NotNull
    @Contract(value = "_, _, _ -> new", pure = true)
    public static String readRawString (@NotNull byte[] array, int offset, int length) {
        return new String(array, offset, length);
    }

    @NotNull
    @Contract(value = "_, _, _ -> new", pure = true)
    public static String readRawASCII (@NotNull byte[] array, int offset, int length) {
        return new String(array, offset, length, StandardCharsets.US_ASCII);
    }

    @NotNull
    @Contract(value = "_, _, _ -> new", pure = true)
    public static String readRawUTF8 (@NotNull byte[] array, int offset, int length) {
        return new String(array, offset, length, StandardCharsets.UTF_8);
    }

    @NotNull
    public static String readString (@NotNull byte[] array, int offset) {
        int length = readInt(array, offset);
        return readRawString(array, offset + IOType.INT_SIZE(), length * 2);
    }

    @NotNull
    public static String readString (byte[] array) {
        return readString(array, 0);
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

    public static void writeBytes (@NotNull byte[] value, byte[] array, int offset) {
        writeInt(value.length, array, offset);
        writeRawBytes(value, array, offset + IOType.INT_SIZE());
    }

    public static void writeBytes (@NotNull byte[] value, byte[] array) {
        writeBytes(value, array, 0);
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

    @NotNull
    public static byte[] readBytes (byte[] array, int offset) {
        int length = readInt(array, offset);
        return readRawBytes(array, offset + IOType.INT_SIZE(), length);
    }

    @NotNull
    public static byte[] readBytes (byte[] array) {
        return readBytes(array, 0);
    }

    public static void readBytes (byte[] array, int offset, @NotNull byte[] result) {
        System.arraycopy(array, offset, result, 0, result.length);
    }

    public static void readBytes (byte[] array, byte[] result) {
        readBytes(array, 0, result);
    }


}
