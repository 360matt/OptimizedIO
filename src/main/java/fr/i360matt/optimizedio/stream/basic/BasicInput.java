package fr.i360matt.optimizedio.stream.basic;

import fr.i360matt.optimizedio.stream.OptiInput;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public class BasicInput extends OptiInput implements Closeable {

    @Contract(pure = true)
    private static int calc (@NotNull int... sizes) {
        int size = 0;
        for (int s : sizes)
            size += s;
        return size;
    }

    public BasicInput (InputStream input, int size) {
        super(input, new byte[size]);
    }

    public BasicInput (InputStream input, byte[] array) {
        super(input, array);
    }

    @Contract(pure = true)
    public BasicInput (InputStream input, @NotNull int... sizes) {
        super(input, new byte[sizes.length]);
    }

    public long skip (int size) throws IOException {
        return this.input.skip(size);
    }

    @Override
    public void checkIndex (int size) {
        // nothing
    }

    @Override
    protected void incrIndex (int size) {
        this.index += size;
    }

    public int read () throws IOException {
        return this.input.read(this.array);
    }

    @Deprecated
    public int read (int size) throws IOException {
        return this.input.read(this.array, this.index, size);
    }

    @Override
    public void close () throws IOException {
        this.input.close();
    }


}
