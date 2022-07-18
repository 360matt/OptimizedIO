import fr.i360matt.optimizedio.OptimizedIO;
import fr.i360matt.optimizedio.Tampon;
import fr.i360matt.optimizedio.stream.tampon.TamponInput;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ReadStreamTest {

    public static long start;

    public static void main (String[] args) throws IOException {

        OptimizedIO.SIZE = 1024*512*16;
        OptimizedIO.MAX_TAMPON = 1;

        OptimizedIO.createNewsTampons(OptimizedIO.MAX_TAMPON);

        start = System.currentTimeMillis();


        for (int i = 0; i < 1; i++) {
            int finalI = i;
            new Thread(() -> {

             /*   try {
                    System.out.println("Thread " + finalI + " started");
                    withFile(finalI, null);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }*/

                OptimizedIO.syncTampon((tampon -> {
                    try {
                        withFile(finalI, tampon);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }));


            }).start();
        }



    }


    public static void withFile (int id, Tampon tampon) throws IOException {

        File file = new File("G:\\test\\dest" + id + ".txt");
        try (FileInputStream fis = new FileInputStream(file)) {

            // BufferedOutputStream bos = new BufferedOutputStream(fos, 1024*128);


            try (TamponInput in = new TamponInput(fis, tampon)) {


                in.read();

                for (int i = 0; i < 200_000_000; i++) {
                    in.readFloat();
                    in.readDouble();
                    in.readChar();
                    in.readUTF8();
                    in.readBoolean();

                    byte[] bytes = new byte[4];
                    in.readBytes(bytes);
                }

                long end = System.currentTimeMillis();
                System.out.println("Time: " + (end - start));

            }
        }

    }





}
