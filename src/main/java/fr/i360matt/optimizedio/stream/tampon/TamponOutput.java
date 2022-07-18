package fr.i360matt.optimizedio.stream.tampon;

import fr.i360matt.optimizedio.Tampon;
import fr.i360matt.optimizedio.stream.OptiOutput;
import fr.i360matt.optimizedio.utils.IOHelper;
import fr.i360matt.optimizedio.utils.IOType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;

public class TamponOutput extends OptiOutput {

    @Contract(pure = true)
    public static int calc (@NotNull int... sizes) {
        int size = 0;
        for (int s : sizes)
            size += s;
        return size;
    }

    public TamponOutput (OutputStream output, @NotNull Tampon tampon) {
        super(output, tampon.getArray());
        this.index = IOType.INT_SIZE();
    }

    @Override
    public void write () throws IOException {
        IOHelper.writeInt(this.index, this.array, 0);
        this.output.write(this.array, 0, this.index);
        this.index = IOType.INT_SIZE();
    }

    @Override
    @Deprecated
    public void write (int size) throws IOException {
        IOHelper.writeInt(this.index, this.array, 0);
        this.output.write(this.array, 0, size);
        this.index = IOType.INT_SIZE();
    }

    @Override
    public void checkIndex (int size) {
        if (this.index + size >= this.array.length) {
            try {
                this.write();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void incrIndex (int size) {
        this.index += size;
    }


}
