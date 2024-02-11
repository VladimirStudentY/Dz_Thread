
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // заполнил текстовый массив
        String[] texts = new String[25];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("aab", 30_000);
        }

        List<Thread> threads = new ArrayList<Thread>();
        // заполняю арей лист новыми потоками используя лямбду с логикой работы в run
        for (int m = 0; m < texts.length; m++) {
            threads.add(new Thread(
                    () -> {
                        for (String text : texts) {
                            int maxSize = 0;
                            for (int i = 0; i < text.length(); i++) {
                                for (int j = 0; j < text.length(); j++) {
                                    if (i >= j) {
                                        continue;
                                    }
                                    boolean bFound = false;
                                    for (int k = i; k < j; k++) {
                                        if (text.charAt(k) == 'b') {
                                            bFound = true;
                                            break;
                                        }
                                    }
                                    if (!bFound && maxSize < j - i) {
                                        maxSize = j - i;
                                    }
                                }
                            }
                            System.out.println(text.substring(0, 100) + " -> " + maxSize);
                        }
                    }
            ));
            
        }
        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        long startTs = System.currentTimeMillis(); // start time


        long endTs = System.currentTimeMillis(); // end time

        System.out.println("Time: " + (endTs - startTs) + "ms");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}