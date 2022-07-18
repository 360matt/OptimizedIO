package fr.i360matt.optimizedio.stream;

import fr.i360matt.optimizedio.utils.IOHelper;
import fr.i360matt.optimizedio.utils.IOType;
import org.jetbrains.annotations.NotNull;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public abstract class OptiOutput implements Closeable {

    public final OutputStream output;
    public final byte[] array;
    public int index;

    public OptiOutput (OutputStream output, byte[] array) {
        this.output = output;
        this.array = array;
        this.index = 0;
    }

    public abstract void write () throws IOException;
    public abstract void write (int size) throws IOException;
    protected abstract void checkIndex (int size);
    protected abstract void incrIndex (int size);

    protected int getIndex () {
        return this.index;
    }

    protected byte[] getArray () {
        return this.array;
    }

    @Override
    public void close () throws IOException {
        this.output.close();
    }

    public void flush () throws IOException {
        this.output.flush();
    }

    public void softReset () {
        this.index = 0;
    }

    public void hardReset () {
        this.index = 0;
        Arrays.fill(array, (byte) 0);
    }

    public void writeInt (int value) {
        checkIndex(IOType.INT_SIZE());
        IOHelper.writeInt(value, this.array, this.index);
        incrIndex(IOType.INT_SIZE());
    }

    public void writeShort (short value) {
        checkIndex(IOType.SHORT_SIZE());
        IOHelper.writeShort(value, this.array, this.index);
        incrIndex(IOType.SHORT_SIZE());
    }

    public void writeByte (byte value) {
        checkIndex(IOType.BYTE_SIZE());
        IOHelper.writeByte(value, this.array, this.index);
        incrIndex(IOType.BYTE_SIZE());
    }

    public void writeLong (long value) {
        checkIndex(IOType.LONG_SIZE());
        IOHelper.writeLong(value, this.array, this.index);
        incrIndex(IOType.LONG_SIZE());
    }

    public void writeFloat (float value) {
        checkIndex(IOType.FLOAT_SIZE());
        IOHelper.writeFloat(value, this.array, this.index);
        incrIndex(IOType.FLOAT_SIZE());
    }

    public void writeDouble (double value) {
        checkIndex(IOType.DOUBLE_SIZE());
        IOHelper.writeDouble(value, this.array, this.index);
        incrIndex(IOType.DOUBLE_SIZE());
    }

    public void writeChar (char value) {
        checkIndex(IOType.CHAR_SIZE());
        IOHelper.writeChar(value, this.array, this.index);
        incrIndex(IOType.CHAR_SIZE());
    }

    public void writeString (@NotNull String value) {
        checkIndex(IOType.INT_SIZE());
        IOHelper.writeInt(value.length(), this.array, this.index);
        incrIndex(IOType.INT_SIZE());

        checkIndex(IOType.CHAR_SIZE() * value.length());
        IOHelper.writeRawString(value, this.array, this.index);
        incrIndex(IOType.CHAR_SIZE() * value.length());
    }

    public void writeUTF8 (@NotNull String value) {
        int size = IOType.UTF8_SIZE(value);
        checkIndex(IOType.INT_SIZE());
        IOHelper.writeInt(size, this.array, this.index);
        incrIndex(IOType.INT_SIZE());

        checkIndex(size);
        IOHelper.writeRawUTF8(value, this.array, this.index);
        incrIndex(size);
    }

    public void writeASCII (@NotNull String value) {
        int size = IOType.ASCII_SIZE(value);
        checkIndex(IOType.INT_SIZE());
        IOHelper.writeInt(size, this.array, this.index);
        incrIndex(IOType.INT_SIZE());

        checkIndex(size);
        IOHelper.writeRawASCII(value, this.array, this.index);
        incrIndex(size);
    }

    public void writeBoolean (boolean value) {
        checkIndex(IOType.BOOL_SIZE());
        IOHelper.writeBool(value, this.array, this.index);
        incrIndex(IOType.BOOL_SIZE());
    }

    public void writeBytes (@NotNull byte[] value) {
        checkIndex(IOType.INT_SIZE());
        IOHelper.writeInt(value.length, this.array, this.index);
        incrIndex(IOType.INT_SIZE());

        checkIndex(IOType.BYTE_SIZE() * value.length);
        IOHelper.writeRawBytes(value, this.array, this.index);
        incrIndex(IOType.BYTE_SIZE() * value.length);
    }


}
