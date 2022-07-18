package fr.i360matt.optimizedio.stream.basic;

import fr.i360matt.optimizedio.stream.OptiOutput;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;

public class BasicOutput extends OptiOutput {

    @Contract(pure = true)
    private static int calc (@NotNull int... sizes) {
        int size = 0;
        for (int s : sizes)
            size += s;
        return size;
    }

    public BasicOutput (OutputStream output, int size) {
        super(output, new byte[size]);
    }

    public BasicOutput (OutputStream output, byte[] array) {
        super(output, array);
    }

    @Contract(pure = true)
    public BasicOutput (OutputStream output, @NotNull int... sizes) {
        super(output, new byte[calc(sizes)]);
    }

    @Override
    public void write () throws IOException {
        this.output.write(this.array);
    }

    @Override
    @Deprecated
    public void write (int size) throws IOException {
        this.output.write(this.array, this.index, size);
    }

    @Override
    public void checkIndex (int size) {
        // nothing
    }

    @Override
    protected void incrIndex (int size) {
        this.index += size;
    }

}
