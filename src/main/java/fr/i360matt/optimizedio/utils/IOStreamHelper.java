package fr.i360matt.optimizedio.utils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class IOStreamHelper {


    public static void writeInt (int value, @NotNull OutputStream out) throws IOException {
        out.write((byte) (value >> 24));
        out.write((byte) (value >> 16));
        out.write((byte) (value >> 8));
        out.write((byte) value);
    }

    public static int readInt (@NotNull InputStream in) throws IOException {
        // not negative
        byte b1 = (byte) (in.read() & 0xFF);
        byte b2 = (byte) (in.read() & 0xFF);
        byte b3 = (byte) (in.read() & 0xFF);
        byte b4 = (byte) (in.read() & 0xFF);
        return ((b1 << 24) | (b2 << 16) | (b3 << 8) | b4);
    }

    public static void writeShort (short value, @NotNull OutputStream out) throws IOException {
        out.write((byte) (value >> 8));
        out.write((byte) value);
    }
    public static short readShort (@NotNull InputStream in) throws IOException {
        int b1 = in.read() & 0xFF;
        int b2 = in.read() & 0xFF;
        return (short) ((b1 << 8) | b2);
    }

    public static void writeByte (byte value, @NotNull OutputStream out) throws IOException {
        out.write(value);
    }

    public static byte readByte (@NotNull InputStream in) throws IOException {
        return (byte) (in.read() & 0xFF);
    }

    public static void writeLong (long value, @NotNull OutputStream out) throws IOException {
        out.write((byte) (value >> 56));
        out.write((byte) (value >> 48));
        out.write((byte) (value >> 40));
        out.write((byte) (value >> 32));
        out.write((byte) (value >> 24));
        out.write((byte) (value >> 16));
        out.write((byte) (value >> 8));
        out.write((byte) value);
    }

    public static long readLong (@NotNull InputStream in) throws IOException {
        int b1 = in.read() & 0xFF;
        int b2 = in.read() & 0xFF;
        int b3 = in.read() & 0xFF;
        int b4 = in.read() & 0xFF;
        int b5 = in.read() & 0xFF;
        int b6 = in.read() & 0xFF;
        int b7 = in.read() & 0xFF;
        int b8 = in.read() & 0xFF;
        return (((long) b1 << 56) | ((long) b2 << 48) | ((long) b3 << 40) | ((long) b4 << 32) | ((long) b5 << 24) | (b6 << 16) | (b7 << 8) | b8);
    }

    public static void writeFloat (float value, OutputStream out) throws IOException {
        writeInt(Float.floatToIntBits(value), out);
    }

    public static float readFloat (InputStream in) throws IOException {
        return Float.intBitsToFloat(readInt(in));
    }

    public static void writeDouble (double value, OutputStream out) throws IOException {
        writeLong(Double.doubleToLongBits(value), out);
    }
    public static double readDouble (InputStream in) throws IOException {
        return Double.longBitsToDouble(readLong(in));
    }

    public static void writeChar (char value, @NotNull OutputStream out) throws IOException {
        out.write((byte) (value >> 8));
        out.write((byte) value);
    }

    public static char readChar (@NotNull InputStream in) throws IOException {
        int b1 = in.read() & 0xFF;
        int b2 = in.read() & 0xFF;
        return (char) ((b1 << 8) | b2);
    }

    public static void writeUTF8 (@NotNull String value, OutputStream out) throws IOException {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        writeInt(bytes.length, out);
        out.write(bytes);
    }

    @NotNull
    public static String readUTF8 (InputStream in) throws IOException {
        int length = readInt(in);
        byte [] bytes = new byte[length];
        in.read(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static void writeBool (boolean value, OutputStream out) throws IOException {
        writeByte((byte) (value ? 1 : 0), out);
    }

    public static boolean readBool (InputStream in) throws IOException {
        return readByte(in) != 0;
    }

    public static void writeRawBytes (@NotNull byte[] value, @NotNull OutputStream out) throws IOException {
        out.write(value);
    }

    public static void writeBytes (@NotNull byte[] value, OutputStream out) throws IOException {
        writeInt(value.length, out);
        writeRawBytes(value, out);
    }

    public static void readRawBytes (@NotNull InputStream in, byte[] value) throws IOException {
        in.read(value);
    }

    @NotNull
    public static byte[] readBytes (InputStream in) throws IOException {
        int length = readInt(in);
        byte[] bytes = new byte[length];
        readRawBytes(in, bytes);
        return bytes;
    }


}