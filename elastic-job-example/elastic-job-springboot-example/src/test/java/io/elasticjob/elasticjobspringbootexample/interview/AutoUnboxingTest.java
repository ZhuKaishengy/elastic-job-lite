package io.elasticjob.elasticjobspringbootexample.interview;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/28 11:21
 * @Description: 包装类、自动拆箱装箱、java对象的引用
 */
public class AutoUnboxingTest {

    public static void main(String[] args) {
       Integer a = new Integer(3);
       Integer b = 3;
       int c = 3;
       System.out.println(a == b); // false
       System.out.println(a == c); // true
       System.out.println(b == c); // true
    }
}
