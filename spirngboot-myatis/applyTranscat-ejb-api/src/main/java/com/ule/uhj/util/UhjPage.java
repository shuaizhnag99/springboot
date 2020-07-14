package com.ule.uhj.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UhjPage implements Serializable {

	private static final long serialVersionUID = -136710993596396251L;

	/**
	 * 当前页码
	 * currPage
	 */
	public static final String name_currPage = "currPage";
	/**
	 * 记录总数
	 * total
	 */
	public static final String name_total = "total";
	/**
	 * 每页条数
	 * pageSize
	 */
	public static final String name_pageSize = "pageSize";

	/**
	 * 总页数
	 * pages
	 */
	public static final String name_pages = "pages";

	/**
	 * 默认页 1
	 */
	public static final Integer default_currPage = 1;
	/**
	 * 默认每页条数 5
	 */
	public static final Integer default_pageSize = 5;
	
	/**
	 * 排序类型
	 */
	private String sortType = "desc";
	
	/**
	 * 排序列
	 */
	private int sortCol = 1;
	
	/**
	 * 当前页
	 */
	private int currPage;
	/**
	 * 每页条数
	 */
	private int pageSize = default_pageSize;
	/**
	 * 总条数
	 */
	private int total;
	
	
	
	public static UhjPage getInstance(){
		return new UhjPage();
	}

	/**
	 * 当前页
	 */
	public int getCurrPage() {
		return currPage > default_currPage ? currPage : default_currPage;
	}

	/**
	 * 当前页
	 */
	public UhjPage setCurrPage(int currPage) {
		this.currPage = currPage;
		return this;
	}

	/**
	 * 每页条数
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 每页条数
	 */
	public UhjPage setPageSize(int pageSize) {
		this.pageSize = pageSize;
		return this;
	}

	/**
	 * 总条数
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * 总条数
	 */
	public UhjPage setTotal(int total) {
		this.total = total;
		return this;
	}

	/**
	 * 获取总页数
	 */
	public int getPages() {
		return (int) Math.ceil((double)getTotal() / getPageSize());
	}
	
	/**
	 * 获取总页数
	 * @param total 总条数
	 * @param perPageSize 每页条数
	 */
	public static int getPages(long total, int perPageSize) {
		return (int) Math.ceil((double)total / perPageSize);
	}
	
	/**
	 * 获取总页数
	 * @param total 总条数
	 * @param perPageSize 每页条数
	 */
	public static int getPages(int total, int perPageSize) {
		return (int) Math.ceil((double)total / perPageSize);
	}
	
	/**
	 * 获取第一条记录位置
	 */
	public int getFirstResult() {
		return (getCurrPage() - 1) * getPageSize();
	}
	
	/**
	 * 获取最后一条记录位置
	 */
	public int getLastResult() {
		return getCurrPage() * getPageSize();
	}
	
	/**
	 * 根据设置的值 返回正常的翻页信息
	 * @return
	 */
	public Map<String, Object> toMap(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(name_currPage, getCurrPage());
		map.put(name_pageSize, getPageSize());
		map.put(name_total, getTotal());
		map.put(name_pages, getPages());
		return map;
	}

	public static void main(String[] args) {
		System.out.println(new UhjPage().setTotal(12).getPages());
	}

	public int getSortCol() {
		return sortCol;
	}

	public void setSortCol(int sortCol) {
		this.sortCol = sortCol;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
}
