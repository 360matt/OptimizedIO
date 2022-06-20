package fr.i360matt.optimizedio;

import org.jetbrains.annotations.NotNull;

public class Tampon {

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
}
