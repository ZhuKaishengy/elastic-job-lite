package io.elasticjob.elasticjobspringbootexample.interview;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/28 10:51
 * @Description: 线程安全的原子性
 */
public class ThreadTest {

    private volatile static int a = 0;

    private static void increase() {
        a++;
    }
    public static void main(String[] args) throws InterruptedException {

        Thread[] threads = new Thread[10];

        for (int i = 0; i < threads.length; i++) {
            threads[i]= new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    increase();
                }
            });
            threads[i].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println(a);
    }
}
