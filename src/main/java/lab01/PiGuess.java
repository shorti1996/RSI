package lab01;

import java.util.Arrays;
import java.util.Random;

/**
 * Klasa implementujaca obliczanie liczby Pi metoda monte carlo
 */
public class PiGuess {


    private static final ThreadLocal<Random> LOCAL_RANDOM = new ThreadLocal<Random>() {
        protected Random initialValue() {
            return new Random();
        };
    };

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    /**
     * Split a number of samples as evenly as possible over the number of available processors.
     * @param samples the samples to split
     * @return an array containing the number of samples to process on each processor.
     */
    private static final long[] apportion(final long samples) {
        int core = CPU_COUNT;
        final long[] portions = new long[core];
        long remaining = samples;

        while (core > 0) {
            final long part = (remaining - 1 + core) / core;
            core--;
            portions[core] = part;
            remaining -= part;
        }
        return portions;
    }

    /**
     * Calculate the approximate area of a circle (radius 1.0) based on a sample system on a single quadrant of the circle.
     * A parallel mechanism is used to improve performance.
     * @param samples the number of samples to take
     * @return the area of the circle.
     */
    public static final double sampleCircleArea(final long samples) {

        // how many samples to process in each thread.
        long[] counts = apportion(samples);

        // add up how many samples appear in the circle
        long inside = Arrays.stream(counts).parallel().map(s -> samplePortion(s)).sum();

        // convert the quadrant area back to the circle area.
        return (4.0 * inside) / samples;
    }

    /**
     * Internal sampling method that counts the number of input samples that are inside the circle too.
     * @param samples the samples to calculate
     * @return the count of samples that are inside the circle.
     */
    private static final long samplePortion(final long samples) {
        final Random rand = LOCAL_RANDOM.get();
        long inside = 0;
        for (int i = 0; i < samples; i++) {
            if (isInside(rand)) {
                inside++;
            }
        }
        return inside;
    }

    /**
     * The core test for each sample, does a random point in the quadrant lie inside the circle.
     * @param rand the source for the random circle.
     * @return true if the random sample is inside the circle.
     */
    private static final boolean isInside(final Random rand) {
        final double x = rand.nextDouble();
        final double y = rand.nextDouble();
        return x * x + y * y <= 1.0;
    }


    public static void main(String[] args) {
        pi_calc(100);
    }

    public static double pi_calc(int iloscLosowan) {
        iloscLosowan = 1000;
        double[] calculations = new double[iloscLosowan];
        for (int i = 0; i < calculations.length; i++) {
            double calc = sampleCircleArea(100000);
            calculations[i] = calc;
//            System.out.printf("Loop %d guesses Pi at %.6f%n", i, calc);
        }
        double result = Arrays.stream(calculations).average().getAsDouble();
//        System.out.printf("Overall calculation is %.6f%n", result);
        return result;
    }

}
