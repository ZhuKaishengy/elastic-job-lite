package io.elasticjob.elasticjobspringbootexample.guava;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/28 18:51
 * @Description: 测试使用guava的eventbus
 */
public class EventBusTest {

    private AsyncEventBus asyncEventBus;

    @Before
    public void before() {
        asyncEventBus = new AsyncEventBus(Executors.newFixedThreadPool(3));
        // param为消费者对象
        asyncEventBus.register(this);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void subscribe(Object object){
        System.out.println("收到:"+object);
    }

    @Test
    public void testSendMsg() throws InterruptedException {
        System.out.println("开始发送消息");
        asyncEventBus.post("这是消息");
        System.out.println("开始睡眠");
        TimeUnit.SECONDS.sleep(5L);
    }

}
