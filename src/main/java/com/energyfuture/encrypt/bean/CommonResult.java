package com.energyfuture.encrypt.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 公共返回对象
 * </p>
 *
 * @author Parker
 * @version v1.0
 * @date 2022/5/18 14:58
 */
@Data
public class CommonResult<T> implements Serializable {

    /**
     * 返回状态码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;


    public CommonResult(Integer code,String message) {
        this.code = code;
        this.msg = message;
    }
    public CommonResult(String message) {
        this.code = 500;
        this.msg = message;
    }

    public CommonResult() {
    }

    /**
     * 成功返回对象
     * @param data 返回数据
     * @param <T> 类型
     * @return 公共返回结果
     */
    public static <T> CommonResult<T> success(T data){
        CommonResult<T> result = new CommonResult<>();
        result.setCode(200);
        result.setMsg("成功");
        result.setData(data);
        return result;
    }
    /**
     * 成功返回对象
     * @param <T> 类型
     * @return 公共返回结果
     */
    public static <T> CommonResult<T> success(){
        CommonResult<T> result = new CommonResult<>();
        result.setCode(200);
        result.setMsg("成功");
        return result;
    }

    /**
     * 错误返回
     * @param code 错误代码
     * @param message 错误信息
     * @param <T> 类型
     * @return 错误返回
     */
    public static <T> CommonResult<T> error(Integer code,String message){
        return new CommonResult<>(code,message);
    }

    /**
     * 错误返回
     * @param message 错误信息
     * @param <T> 类型
     * @return 错误返回
     */
    public static <T> CommonResult<T> error(String message){
        return new CommonResult<>(message);
    }
}
