package com.energyfuture.encrypt.bean;

import cn.hutool.core.convert.Convert;
import com.energyfuture.encrypt.enums.ErrorEnum;
import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>公共返回对象</p>
 * @author Parker
 * @date 2022-05-20 13:52
 */
@Data
public class CommonResult<T> implements Serializable {

    /**
     * 返回状态码
     */
    protected Integer code;

    /**
     * 消息
     */
    protected String msg;

    /**
     * 数据
     */
    private T data;


    public CommonResult(Integer code,String msg) {
        this.code = code;
        this.msg = msg;
    }
    public CommonResult(String msg) {
        this.code = ErrorEnum.ERROR.getCode();
        this.msg = msg;
    }

    public CommonResult(ErrorEnum errorEnum) {
        this.code = errorEnum.getCode();
        this.msg = errorEnum.getMessage();
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
        CommonResult<T> result = new CommonResult<>(ErrorEnum.SECUESS);
        result.setData(data);
        return result;
    }
    /**
     * 成功返回分页列表
     * @param records 数据列表
     * @param total 数据总条数
     * @param current 页码
     * @param <T> 类型
     * @return 公共返回结果
     */
    public static <T> CommonResult<ListResult<T>> success(List<T> records, Long total, Long current){
        CommonResult<ListResult<T>> result = new CommonResult<>(ErrorEnum.SECUESS);
        result.setData(ListResult.create(records,total,current));
        return result;
    }

    /**
     * <p>返回分页列表，使用pagehelper分页时使用</p>
     * @author Parker
     * @param list 查询的列表
     * @return com.energyfuture.encrypt.bean.CommonResult<com.energyfuture.encrypt.bean.ListResult<T>>
     * @date 2022-05-20 14:16
     */
    public static <T> CommonResult<ListResult<T>> successPage(List<T> list){
        PageInfo<T> pageInfo = new PageInfo<>(list);
        CommonResult<ListResult<T>> result = new CommonResult<>(ErrorEnum.SECUESS);
        result.setData(ListResult.create(pageInfo.getList(),pageInfo.getTotal(), Convert.toLong(pageInfo.getPageNum())));
        return result;
    }


    /**
     * 成功返回
     * @param <T> 类型
     * @return 公共返回结果
     */
    public static <T> CommonResult<T> success(){
        return new CommonResult<>(ErrorEnum.SECUESS);
    }

    /**
     * 错误返回
     * @param errorEnum 错误枚举
     * @param <T> 类型
     * @return 公共返回结果
     */
    public static <T> CommonResult<T> error(ErrorEnum errorEnum){
        return new CommonResult<>(errorEnum);
    }

    /**
     * 错误返回
     * @param code 错误代码
     * @param msg 错误信息
     * @param <T> 类型
     * @return 错误返回
     */
    public static <T> CommonResult<T> error(Integer code,String msg){
        return new CommonResult<>(code,msg);
    }

    /**
     * 错误返回
     * @param msg 错误信息
     * @param <T> 类型
     * @return 错误返回
     */
    public static <T> CommonResult<T> error(String msg){
        return new CommonResult<>(msg);
    }

}
