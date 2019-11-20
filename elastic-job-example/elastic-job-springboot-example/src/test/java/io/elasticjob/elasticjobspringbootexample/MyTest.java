package io.elasticjob.elasticjobspringbootexample;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.junit.Test;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/22 14:58
 * @Description: 测试面试题的测试类
 */
public class MyTest {

    @Data
    @Accessors(chain = true)
    @ToString
    class Person {

        private String id;
        private String name;
    }

    @Test
    public void test1() {
        Person person = new Person();
        this.changeProperties(person);
        System.out.println(person);
    }

    private void changeProperties(Person person) {
        person.setId("1001").setName("zks");
    }

    @Test
    public void test2() {
        Integer a = new Integer(3);
        Integer b = 3;
        int c = 3;
        System.out.println(a == b);
        System.out.println(a.equals(b));
        System.out.println(a == c);
    }

    @Test
    public void test3() {
        Integer f1 = 100, f2 = 100, f3 = 150, f4 = 150;
        System.out.println(f1 == f2);
        System.out.println(f3 == f4);
    }

}
