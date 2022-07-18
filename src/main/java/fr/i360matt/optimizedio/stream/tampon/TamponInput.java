package fr.i360matt.optimizedio.stream.tampon;

import fr.i360matt.optimizedio.Tampon;
import fr.i360matt.optimizedio.stream.OptiInput;
import fr.i360matt.optimizedio.utils.IOHelper;
import fr.i360matt.optimizedio.utils.IOType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class TamponInput extends OptiInput {

    private int maxIndex;

    @Contract(pure = true)
    public static int calc (@NotNull int... sizes) {
        int size = 0;
        for (int s : sizes)
            size += s;
        return size;
    }

    public TamponInput (InputStream input, @NotNull Tampon tampon) {
        super(input, tampon.getArray());
    }

    @Override
    public int read () throws IOException {
        this.index = 0;

        byte[] numb = new byte[IOType.INT_SIZE()];
        this.input.read(numb);
        this.maxIndex = IOHelper.readInt(numb, 0) - IOType.INT_SIZE();

        return this.input.read(this.array, 0, this.maxIndex);
    }

    @Override
    @Deprecated
    public int read (int size) throws IOException {
        this.index = IOType.INT_SIZE();
        int read = this.input.read(this.array, 0, size);
        this.maxIndex = IOHelper.readInt(this.array, 0);
        return read;
    }

    @Override
    public void checkIndex (int size) {
        if ((this.index + size > this.array.length) || (this.index + size > this.maxIndex)) {
            try {
                this.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void incrIndex (int size) {
        this.index += size;
    }

}
