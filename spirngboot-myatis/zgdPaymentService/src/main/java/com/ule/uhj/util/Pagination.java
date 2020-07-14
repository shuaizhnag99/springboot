package com.ule.uhj.util;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * 邮政商城使用的分页工具类
 * @author quan
 * 
 */
public class Pagination {
	private int pageSize = 20;//每页显示多少条数据
	   int  viewPage=0;//显示总页数其中的N页 
	   int  viewCurrentPage=0;//显示当前页的前几页和后几页	
	   int custPageSize;	// 自定义每页显示数据条数，为0的话则使用默认PAGE
	
	/**
	 * 
	 * @param total 总数据条数
	 * @param beginPage 当前页
	 * @return page 分页用的信息{totlaPage:总页数;list:显示在界面的页码数;firstPage:是否第一页;
	 *                         lastPage:是否最后一页}
	 */	
	public Hashtable getPageInfo(int total, int beginPage) {
		Hashtable page = new Hashtable();
		int pageList = 0;
		int totlaPage = 0;// 总页数
		
		int intCustPageSize = pageSize;		// 每页显示行数
		if(custPageSize > 0) {
			intCustPageSize = custPageSize;
		}
		
		if (total != 0) {
			pageList = total / intCustPageSize;
			if (total % intCustPageSize > 0) {
				totlaPage = pageList + 1;
			} else {
				totlaPage = pageList;
			}
		}
		List list = new ArrayList();// 显示在界面的页码数
		if (totlaPage <= this.getViewPage()) {
			for (int i = 1; i <= totlaPage; i++) {
				list.add(i);
			}
		} else {
			if (beginPage <= (this.getViewCurrentPage() + 1)) {
				for (int j = 1; j <= this.getViewPage(); j++) {
					list.add(j);
				}
			} else if (beginPage > this.getViewCurrentPage()
					&& beginPage < totlaPage - this.getViewCurrentPage()) {
				for (int i = beginPage - this.getViewCurrentPage(); i <= beginPage
						+ this.getViewCurrentPage(); i++) {
					list.add(i);
				}
			} else {
				for (int i = totlaPage - this.getViewCurrentPage() * 2; i <= totlaPage; i++) {
					list.add(i);
				}
			}
		}
		page.put("pageList", list);
		page.put("totalPage", totlaPage);
		if (beginPage == totlaPage) {
			page.put("lastPage", "0"); // 0:表示当前页是最后一页
		} else {
			page.put("lastPage", "1");
		}
		if (beginPage == 1) {
			page.put("firstPage", "0"); // 0:表示当前页是第一页
		} else {
			page.put("firstPage", "1");
		}
		return page;

	}
	
	public int getViewCurrentPage() {
		return viewCurrentPage;
	}
	public void setViewCurrentPage(int viewCurrentPage) {
		this.viewCurrentPage = viewCurrentPage;
	}
	public int getViewPage() {
		return viewPage;
	}
	public void setViewPage(int viewPage) {
		this.viewPage = viewPage;
	}

	public int getCustPageSize() {
		return custPageSize;
	}

	public void setCustPageSize(int custPageSize) {
		this.custPageSize = custPageSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
}
