import fr.i360matt.optimizedio.OptimizedIO;
import fr.i360matt.optimizedio.Tampon;
import fr.i360matt.optimizedio.stream.tampon.TamponOutput;

import java.io.*;

public class StreamTest {

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
        try (FileOutputStream fos = new FileOutputStream(file)) {

           // BufferedOutputStream bos = new BufferedOutputStream(fos, 1024*128);


            try (TamponOutput out = new TamponOutput(fos, tampon)) {



                for (int i = 0; i < 200_000_000; i++) {
                    out.writeFloat(1.23456f);
                    out.writeDouble(9.87654d);
                    out.writeChar('3');
                    out.writeUTF8("0123456789ABCDEF");
                    out.writeBoolean(true);
                    out.writeBytes(new byte[]{65, 66, 67, 68});
                }

                out.write();
                out.flush();

                long end = System.currentTimeMillis();
                System.out.println("Time: " + (end - start));

            }
        }

    }





}
