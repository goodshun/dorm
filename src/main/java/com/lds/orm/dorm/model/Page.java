package com.lds.orm.dorm.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: Page
 * <p>
 * Description:分页查询数据封装
 * </p>
 *
 * @author liudongshun
 * @version V1.0
 * @since 2018/08/09
 */
public class Page<E> {
    private static final ThreadLocal<Map<String,Integer>> pageInfos = new ThreadLocal<>();

    /**
     * 页数
     */
    private Integer pageNum;

    /**
     * 每页大小
     */
    private Integer pageSize;

    /**
     * 总数
     */
    private  Integer total;


    private Integer pages;


    private List<E> result;


    public static void page(Integer pageNum,Integer pageSize){
        Map<String,Integer> pageInfo = new HashMap<>(5);
        pageInfo.put("pageNum",pageNum);
        pageInfo.put("pageSize",pageSize);
        pageInfos.set(pageInfo);
    }


    public static Map<String,Integer> getPageinfo(){
        return pageInfos.get();
    }

    public Page(Integer pageNum, Integer pageSize, Integer total, List<E> result) {
        super();
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.result = result;
        pages = total%pageSize==0?total/pageSize:total/pageSize+1;
    }

    /**
     * method name   : getPageSize
     * description   : 获取每页条数
     * @return       : Integer
     * @param        : @return
     * modified      : zhukaipeng ,  2017年9月15日
     * @see          : *
     */
    public Integer getPageSize() {
        return pageSize;
    }
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    /**
     * method name   : getPageNum
     * description   : 获取当前页
     * @return       : Integer
     * @param        : @return
     * modified      : zhukaipeng ,  2017年9月15日
     * @see          : *
     */
    public Integer getPageNum() {
        return pageNum;
    }
    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
    /**
     * method name   : getTotal
     * description   : 获取总条数
     * @return       : Integer
     * @param        : @return
     * modified      : zhukaipeng ,  2017年9月15日
     * @see          : *
     */
    public Integer getTotal() {
        return total;
    }
    public void setTotal(Integer total) {
        this.total = total;
    }
    /**
     * method name   : getPages
     * description   : 获取总页数
     * @return       : Integer
     * @param        : @return
     * modified      : zhukaipeng ,  2017年9月15日
     * @see          : *
     */
    public Integer getPages() {
        return pages;
    }
    public void setPages(Integer pages) {
        this.pages = pages;
    }
    /**
     * method name   : getResult
     * description   : 获取查询结果
     * @return       : List<E>
     * @param        : @return
     * modified      : zhukaipeng ,  2017年9月15日
     * @see          : *
     */
    public List<E> getResult() {
        return result;
    }
    public void setResult(List<E> result) {
        this.result = result;
    }
    @Override
    public String toString() {
        return "Page [pageNum=" + pageNum + ", pageSize=" + pageSize + ", total=" + total + ", pages=" + pages
                + ", result=" + result + "]";
    }

}
