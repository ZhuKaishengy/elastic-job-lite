package io.elasticjob.elasticjobspringbootexample.interview;

import java.util.Objects;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/28 11:31
 * @Description:
 */
public class LogicAndTest {
    
    public static void main(String[] args) {

        String str = null;
        if (Objects.nonNull(str) & str.equals("str")) {
            System.out.println(str);
        }
    }
}
