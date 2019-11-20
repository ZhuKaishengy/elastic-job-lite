package io.elasticjob.elasticjobspringbootexample;

import io.elasticjob.elasticjobspringbootexample.util.IdWorker;
import org.junit.Test;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/23 19:31
 * @Description:
 */
public class IdWorkerTest {

    @Test
    public void test() {

        IdWorker idWorker = new IdWorker(0, 1, 1);
        for (int i = 0; i < 10; i++) {
            System.out.println(idWorker.nextId());
        }
    }
}
