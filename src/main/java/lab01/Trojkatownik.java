package lab01;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Klasa badajaca mozliwosci budowania trojkatow
 */
public class Trojkatownik {
    public static void main(String[] args) {
        int x = 3;
        int y = 4;
        int z = 5;
//        System.out.println(czyMoznaTrojkat(x, y, z));
        System.out.println(jakiTrojkat(x, y, z));

    }

    private static boolean czyMoznaTrojkat(int x, int y, int z) {
        return x + y > z
                && x + z > y
                && y + z > x;
    }

    /**
     * Glowna logika klasy
     * @param x bok trojkata
     * @param y bok trojkata
     * @param z bok trojkata
     * @return wiadomosc o typie trojkata
     */
    public static String jakiTrojkat(int x, int y, int z) {
        if(!czyMoznaTrojkat(x, y, z)) {
            return "ZADEN";
        } else if (x == y && x == z && y == z) {
            return "ROWNOBOCZNY";
        } else {
            LinkedList<Integer> boki = new LinkedList<>();
            boki.add(x);
            boki.add(y);
            boki.add(z);
            Collections.sort(boki);
            if (boki.get(0)*boki.get(0) + boki.get(1) * boki.get(1) == boki.get(2) * boki.get(2)) {
                return "PROSTOKATNY";
            } else {
                Set<Integer> set = new HashSet<>(boki);
                if(set.size() < boki.size()){
                    return "ROWNORAMIENNY";
                } else {
                    return "ROZNOBOCZNY";
                }
            }
        }
    }
}
