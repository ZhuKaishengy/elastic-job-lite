package io.elasticjob.elasticjobspringbootexample.resp;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author zhukaishengy
 */
@Data
@ToString
@Accessors(chain = true )
public class ServiceResult<T> implements Serializable {

    private Integer code;
    private String msg;
    private T data;

    public static ServiceResult successWithDefaultMsg(){
        return new ServiceResult().setCode(200).setMsg("success");
    }

    public static ServiceResult failWithDefaultMsg(){
        return new ServiceResult().setCode(404).setMsg("fail");
    }

    public static <T> ServiceResult<T> successWithData(T value){
        return new ServiceResult<T>().setCode(200).setMsg("success").setData(value);
    }

    public static ServiceResult fail(){
        return new ServiceResult().setCode(404);
    }

    public static <T> ServiceResult<T> success(T value){
        return new ServiceResult<T>().setCode(200).setData(value);
    }
}
