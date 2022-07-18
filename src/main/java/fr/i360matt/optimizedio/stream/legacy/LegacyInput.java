package fr.i360matt.optimizedio.stream.legacy;


import fr.i360matt.optimizedio.utils.IOStreamHelper;
import org.jetbrains.annotations.NotNull;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public class LegacyInput implements Closeable {

    private final InputStream input;

    public LegacyInput (InputStream input) {
        this.input = input;
    }

    public long skip (int size) throws IOException {
        return this.input.skip(size);
    }


    @Override
    public void close () throws IOException {
        this.input.close();
    }


    public int readInt () throws IOException {
        return IOStreamHelper.readInt(this.input);
    }

    public short readShort () throws IOException {
        return IOStreamHelper.readShort(this.input);
    }

    public byte readByte () throws IOException {
        return IOStreamHelper.readByte(this.input);
    }

    public long readLong () throws IOException {
        return IOStreamHelper.readLong(this.input);
    }

    public float readFloat () throws IOException {
        return IOStreamHelper.readFloat(this.input);
    }

    public double readDouble () throws IOException {
        return IOStreamHelper.readDouble(this.input);
    }

    public char readChar () throws IOException {
        return IOStreamHelper.readChar(this.input);
    }

    public String readUTF8 () throws IOException {
        return IOStreamHelper.readUTF8(this.input);
    }

    public boolean readBoolean () throws IOException {
        return IOStreamHelper.readBool(this.input);
    }

    public byte[] readBytes () throws IOException {
        return IOStreamHelper.readBytes(this.input);
    }

    public void readBytes (@NotNull byte[] array) throws IOException {
        IOStreamHelper.readRawBytes(this.input, array);
    }


}
