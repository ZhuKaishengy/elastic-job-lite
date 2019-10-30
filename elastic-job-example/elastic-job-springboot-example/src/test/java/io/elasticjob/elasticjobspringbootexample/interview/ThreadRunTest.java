package io.elasticjob.elasticjobspringbootexample.interview;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/28 13:02
 * @Description: 线程run方法
 */
public class ThreadRunTest {

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            System.out.println("ping");
        });
        thread.run();
        System.out.println("pong");
    }
}
