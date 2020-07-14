package com.ule.uhj.util;

import java.util.HashMap;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

public class PagingUtils {
	/**
	 * 分页
	 * @param request
	 * @param total 记录总数
	 * @param currentPage 当前页
	 * @param custPageSize 每页记录条数
	 */
	public static void pageUtil(HttpServletRequest request, Long total, int currentPage,
			int custPageSize) {
		Pagination pageInfo = new Pagination();
		pageInfo.custPageSize = custPageSize;
		Hashtable<String, String> pageTable = new Hashtable<String, String>();
		HashMap page = new HashMap();
		pageTable = pageInfo.getPageInfo(total.intValue(), currentPage);
		page.put("totalPage", ts(pageTable.get("totalPage")));
		String totalPage = ts(pageTable.get("totalPage"));
		if("0".equals(totalPage)){
			currentPage = 0;
		}
		page.put("currentPage", currentPage);
		page.put("lastPage", ts(pageTable.get("lastPage")));
		page.put("firstPage", ts(pageTable.get("firstPage")));
		page.put("total", total);
		request.setAttribute("page", page);
	}

	private static String ts(Object obj) {
		if (null == obj) {
			return "0";
		}
		return String.valueOf(obj).trim();
	}
}

