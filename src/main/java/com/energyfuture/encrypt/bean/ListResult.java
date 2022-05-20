package com.energyfuture.encrypt.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
  * <p>查询列表返回</p>
  * @author Parker
  * @date 2022-05-20 14:00
  */
@Data
public class ListResult<T> implements Serializable {

    /**
     * 数据列表
     */
    private List<T> records;

    /**
     * 总数
     */
    private Long total;


    /**
     * 当前页码
     */
    private Long current;

    public ListResult(List<T> records, Long total, Long current) {
        this.records = records;
        this.total = total;
        this.current = current;
    }

    public ListResult() {
    }

    public static <T> ListResult<T> create(List<T> records, Long total, Long current){
        return new ListResult<>(records,total,current);
    }
}
