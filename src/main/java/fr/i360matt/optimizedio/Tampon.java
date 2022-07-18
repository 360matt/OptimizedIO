package fr.i360matt.optimizedio;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class Tampon /*implements Closeable*/ {

    private final byte[] array;
    private int queue;

    public Tampon (byte[] array) {
        this.array = array;
        this.queue = 0;
    }

    @NotNull
    public byte[] getArray () {
        return this.array;
    }

    public int getQueue () {
        return this.queue;
    }

    public void setQueue (int queue) {
        this.queue = queue;
    }

   /* @Override
    public void close () {
        this.queue--;
    }*/

    public void execute (@NotNull Consumer<Tampon> consumer) {
        this.queue++;
        synchronized (this.array) {
            consumer.accept(this);
        }
        this.queue--;
    }
}
