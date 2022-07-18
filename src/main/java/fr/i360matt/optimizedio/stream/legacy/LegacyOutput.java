package fr.i360matt.optimizedio.stream.legacy;

import fr.i360matt.optimizedio.utils.IOStreamHelper;
import org.jetbrains.annotations.NotNull;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

public class LegacyOutput implements Closeable {

    private final OutputStream output;

    public LegacyOutput (OutputStream output) {
        this.output = output;
    }

    public void flush () throws IOException {
        this.output.flush();
    }

    public void close () throws IOException {
        this.output.close();
    }

    public void writeInt (int value) throws IOException {
        IOStreamHelper.writeInt(value, this.output);
    }

    public void writeShort (short value) throws IOException {
        IOStreamHelper.writeShort(value, this.output);
    }

    public void writeByte (byte value) throws IOException {
        IOStreamHelper.writeByte(value, this.output);
    }

    public void writeLong (long value) throws IOException {
        IOStreamHelper.writeLong(value, this.output);
    }

    public void writeFloat (float value) throws IOException {
        IOStreamHelper.writeFloat(value, this.output);
    }

    public void writeDouble (double value) throws IOException {
        IOStreamHelper.writeDouble(value, this.output);
    }

    public void writeChar (char value) throws IOException {
        IOStreamHelper.writeChar(value, this.output);
    }

    public void writeUTF8 (@NotNull String value) throws IOException {
        IOStreamHelper.writeUTF8(value, this.output);
    }

    public void writeBoolean (boolean value) throws IOException {
        IOStreamHelper.writeBool(value, this.output);
    }

    public void writeBytes (@NotNull byte[] value) throws IOException {
        IOStreamHelper.writeBytes(value, this.output);
    }


}
