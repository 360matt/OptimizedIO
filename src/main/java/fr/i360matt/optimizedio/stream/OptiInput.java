package fr.i360matt.optimizedio.stream;

import fr.i360matt.optimizedio.utils.IOHelper;
import fr.i360matt.optimizedio.utils.IOType;
import org.jetbrains.annotations.NotNull;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public abstract class OptiInput implements Closeable {

    public final InputStream input;
    public final byte[] array;
    public int index;

    public OptiInput (InputStream input, byte[] array) {
        this.input = input;
        this.array = array;
        this.index = 0;
    }

    public abstract int read () throws IOException;
    public abstract int read (int size) throws IOException;
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
        this.input.close();
    }

    public int readInt () {
        checkIndex(IOType.INT_SIZE());
        int value = IOHelper.readInt(this.array, this.index);
        incrIndex(IOType.INT_SIZE());
        return value;
    }

    public short readShort () {
        checkIndex(IOType.SHORT_SIZE());
        short value = IOHelper.readShort(this.array, this.index);
        incrIndex(IOType.SHORT_SIZE());
        return value;
    }

    public byte readByte () {
        checkIndex(IOType.BYTE_SIZE());
        byte value = IOHelper.readByte(this.array, this.index);
        incrIndex(IOType.BYTE_SIZE());
        return value;
    }

    public long readLong () {
        checkIndex(IOType.LONG_SIZE());
        long value = IOHelper.readLong(this.array, this.index);
        incrIndex(IOType.LONG_SIZE());
        return value;
    }

    public float readFloat () {
        checkIndex(IOType.FLOAT_SIZE());
        float value = IOHelper.readFloat(this.array, this.index);
        incrIndex(IOType.FLOAT_SIZE());
        return value;
    }

    public double readDouble () {
        checkIndex(IOType.DOUBLE_SIZE());
        double value = IOHelper.readDouble(this.array, this.index);
        incrIndex(IOType.DOUBLE_SIZE());
        return value;
    }

    public char readChar () {
        checkIndex(IOType.CHAR_SIZE());
        char value = IOHelper.readChar(this.array, this.index);
        incrIndex(IOType.CHAR_SIZE());
        return value;
    }

    public String readUTF8 () {
        checkIndex(IOType.INT_SIZE());
        int length = IOHelper.readInt(this.array, this.index);
        incrIndex(IOType.INT_SIZE());

        checkIndex(length);
        String str = IOHelper.readRawUTF8(this.array, this.index, length);
        incrIndex(length);
        return str;
    }

    public boolean readBoolean () {
        checkIndex(IOType.BYTE_SIZE());
        boolean value = IOHelper.readBool(this.array, this.index);
        incrIndex(IOType.BYTE_SIZE());
        return value;
    }

    public void readBytes (@NotNull byte[] array) {
        checkIndex(IOType.INT_SIZE());
        int length = IOHelper.readInt(this.array, this.index);
        incrIndex(IOType.INT_SIZE());

        checkIndex(length);
        IOHelper.readRawBytes(this.array, array, this.index, length);
        incrIndex(length);
    }

}
