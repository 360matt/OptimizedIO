package fr.i360matt.optimizedio;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.function.Consumer;

public class OptimizedIO {

    public static int SIZE = 1024*256;
    // Taille optimale pour un meilleur rendement (temps + consommation RAM)

    public static int MAX_TAMPON = 4;
    // Le nombre d'array peut augmenter jusqu'au nombre de MAX_TAMPON

    private static Tampon[] store;

    /**
     * Permet d'incrémenter la taille de l'array
     */
    private static void upgradeArray (int size) {
        Tampon[] newer = new Tampon[store.length+size];
        System.arraycopy(store, 0, newer, 0, store.length);
        store = newer;
    }

    /**
     * Crée un tampon et le place en fin de tableau de la bibliothèque de tampon.
     * Nécessite un appelle de upgradeArray(), sinon la dernière valeur est écrasée.
     * @return
     */
    @NotNull
    private static Tampon createTamponToLast () {
        final byte[] b = new byte[SIZE];
        Tampon tampon = new Tampon(b);
        store[store.length-1] = tampon;
        return tampon;
    }

    /**
     * Permet de créer de nouveaux tampons
     * @param size le nombre de tampons à créer
     */
    public static void createNewsTampons (int size) {
        if (store == null) {
            store = new Tampon[size];
            for (int i = 0; i < size; i++) {
                store[i] = new Tampon(new byte[SIZE]);
            }
        } else {
            upgradeArray(size);
            for (int i = 0; i < size; i++) {
                store[store.length-1-i] = createTamponToLast();
            }
        }
    }

    /**
     * Permet d'ajouter artificiellement un nouveau tampon array.
     * La méthode init() doit avoir été appellée au moins une fois avant.
     * @return le tampon array nouvellement créé
     */
    @NotNull
    public static Tampon createNewTampon () {
        upgradeArray(1);
        return createTamponToLast();
    }

    public static void init () {
        if (store != null) return;
        store = new Tampon[1];
        createTamponToLast();
    }

    /**
     * Cette méthode renvoie le tampon array le plus approprié,
     * Si le nombre de tampon maximal n'est pas atteint, on en crée un,
     * Sinon, on renvoie le tampon où la file d'attente est la plus basse.
     * @return le tampon le plus approprié
     */
    @NotNull
    public synchronized static Tampon getTampon () {
        init();

        Tampon candidate = null;
        for (final Tampon iter : store) {
            if (iter == null) continue;
            if (candidate != null && candidate.getQueue() <= iter.getQueue())
                continue;
            candidate = iter;
            if (iter.getQueue() == 0)
                break;
        }

        if (candidate == null || (candidate.getQueue() > 0 && store.length < MAX_TAMPON)) {
            candidate = createNewTampon();
        }

        return candidate;
    }

    public static void syncTampon (@NotNull Consumer<Tampon> consumer) {
        Tampon tampon = getTampon();
        tampon.execute(consumer);
    }

    /**
     * Cette méthode permet de copier un flux en réutilisant un tampon array déjà existant
     * Afin de ne pas re-allouer un nouvel array dans la RAM,
     * Et ainsi, limiter les fuites de mémoires
     *
     * @param in entrée du flux
     * @param out sortie du flux
     * @param count nombre de bytes à transférer
     * @throws IOException erreurs IO communes
     */
    public static void copy (@NotNull InputStream in, OutputStream out, long count) throws IOException {
        Tampon tampon = getTampon();
        tampon.setQueue(tampon.getQueue()+1);

        byte[] array;
        synchronized (array = tampon.getArray()) {
            copy(in, out, count, array);
        }

        tampon.setQueue(tampon.getQueue()-1);
    }

    /**
     * Cette méthode permet de copier un flux à l'aide d'un tampon array donné en argument.
     * Ce qui donne la possibilité de choisir la taille du tampon pour cette copie spécifiquement.
     *
     * Veuillez vous assurer que l'array ne sera pas utilisé dans un autre thread pendant la copie.
     * Les données de l'array dernièrement écrites ne seront pas nettoyées.
     *
     * @param in entrée du flux
     * @param out sortie du flux
     * @param count nombre de bytes à transférer
     * @param tampon tampon array à utiliser
     * @throws IOException erreurs IO communes
     */
    public static void copy (@NotNull InputStream in, OutputStream out, long count, byte[] tampon) throws IOException {
        /* if (!(in instanceof BufferedInputStream))
            in = new BufferedInputStream(in);
        if (!(out instanceof BufferedOutputStream))
            out = new BufferedOutputStream(out); */
        /*
         * 18/07/2022 : Finalement, utiliser un buffered est inutile, car les fluxs sont déjà bufférés.
         */

        int toSend;
        while (count > 0 && (toSend = in.read(tampon, 0, (int) Math.min(count, SIZE))) != -1) {
            out.write(tampon, 0, toSend);
            count -= SIZE;
        }
        out.flush();
    }





}
