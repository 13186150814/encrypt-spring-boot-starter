package com.energyfuture.encrypt.web;

import com.energyfuture.encrypt.utils.ServletUtils;
import com.github.pagehelper.PageHelper;

/**
 * <p>
 * BaseController
 * </p>
 *
 * @author Parker
 * @version v1.0
 * @date 2022/5/20 14:09
 */
public class BaseController {

    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "pageSize";


    /**
     * 设置请求分页数据
     */
    protected void startPage() {
        PageHelper.startPage(ServletUtils.getParameterToInt(PAGE_NUM,1), ServletUtils.getParameterToInt(PAGE_SIZE,200));
    }


}
