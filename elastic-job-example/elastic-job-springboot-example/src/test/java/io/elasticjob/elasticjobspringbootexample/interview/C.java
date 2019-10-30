package io.elasticjob.elasticjobspringbootexample.interview;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/28 10:46
 * @Description: 泛型的使用
 */
public class C {
    static class A {
    }

    static class B extends A {
    }

    public static void main(String[] args) {
        ArrayList<A> list = new ArrayList<A>();
        list.add(new B());
        method1(list);
    }

    private static void method1(List< ? super A> list) {
        for (int i = 0; i < list.size(); i++) {
//            A a = list.get(0);
        }
    }
}
