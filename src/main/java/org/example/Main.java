package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>(1000);

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[1000];
        String steps = "RLLF";
        for (int i = 0; i < 1000; i++) {

            threads[i] = new Thread(() -> {
                String total = generateRoute(steps, 100);
                int repitOfR = total.split("R", -1).length - 1;
                synchronized (sizeToFreq) {
                    sizeToFreq.put(repitOfR, sizeToFreq.getOrDefault(repitOfR, 0) + 1);
                }
            });
            threads[i].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        sizeToFreq.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .forEach(System.out::println); //
    }


    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}