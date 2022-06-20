import fr.i360matt.optimizedio.OptimizedIO;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

public class TestCopy {

    public static void main(String[] args) throws IOException {

        OptimizedIO.SIZE = 1024 * 256;
        OptimizedIO.MAX_TAMPON = 1;


        long start = System.currentTimeMillis();

        /*
        for (int i = 0; i < 200; i++) {
            doExec();
        }
         */

        doExecMultiThread(4);




        long stop = System.currentTimeMillis();
        long diff = stop - start;

        System.out.printf("%dms%n", diff);

        long free = Runtime.getRuntime().freeMemory();
        long max = Runtime.getRuntime().maxMemory();
        long total = Runtime.getRuntime().totalMemory();

        System.out.printf("%d | %d | %d", free, max, total);

    }


    public static void doExecMultiThread (int threads) {
        final CompletableFuture<Void>[] tasks = new CompletableFuture[threads];

        for (int i = 0; i < threads; i++) {
            final CompletableFuture<Void> future = new CompletableFuture<>();
            tasks[threads-1] = future;
            Thread thread = new Thread(() -> {
                try {
                    for (int k = 0; k < 20; k++) {
                        doExec("alpha");
                        doExec("beta");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                future.complete(null);
            });
            thread.start();
        }

        for (int i = 0; i < threads; i++) {
            tasks[threads-1].join();
        }
    }


    public static void doExec (String id) throws IOException {


        int rand = (int) Math.floor(Math.random()*(8000+1)+0);

        String source = "G:\\test\\source_" + id + ".zip";
        String destination = "G:\\test\\dest\\" + id + "--" + rand + ".zip";

        File srcFile = new File(source);

        File destinationFIle = new File(destination);
        destinationFIle.createNewFile();


        try (BufferedInputStream in = new BufferedInputStream(Files.newInputStream(Paths.get(source)))) {
            try (BufferedOutputStream out = new BufferedOutputStream(Files.newOutputStream(Paths.get(destination)))) {
                OptimizedIO.copy(in, out, srcFile.length());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }


}
