package com.tzg.common.page;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PaginationQuery implements Serializable {

	private static final long serialVersionUID = 8469180008535352019L;

	public static final int DESCENDING = 1;

	public static final int ASCENDING = 2;

	public static final int DEFAULT_ROWS_PER_PAGE = 10;

	private int rowsPerPage = DEFAULT_ROWS_PER_PAGE;

	protected int pageIndex = 1;

	private int orderBy;
	
	private int status=1;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	private Map<String, Object> queryData = new HashMap<String, Object>();

	

	public int getRowsPerPage() {
		return rowsPerPage;
	}

	public void setRowsPerPage(int pageSize) {
		this.rowsPerPage = pageSize;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		if (pageIndex < 1) {
			this.pageIndex = 1;
		} else {
			this.pageIndex = pageIndex;
		}
	}

	public int getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}

	public Map<String, Object> getQueryData() {
		return queryData;
	}

	public void setQueryData(Map<String, Object> queryData) {
		this.queryData = queryData;
	}

	public String getQueryParameters() {
		if (queryData.containsKey("startRecord")) {
			queryData.remove("startRecord");
		}
		if (queryData.containsKey("endRecord")) {
			queryData.remove("endRecord");
		}
		StringBuffer sb = new StringBuffer();
		if (!queryData.isEmpty()) {
			for (Iterator<String> i = queryData.keySet().iterator(); i
					.hasNext();) {
				String key = i.next();
				if (null != queryData.get(key)) {
					sb.append(key);
					sb.append("=");
					sb.append(queryData.get(key));
					if (i.hasNext()) {
						sb.append("&");
					}
				}
			}
		}
		if (sb.toString() == null || "".equals(sb.toString()))
			return null;
		return sb.toString();
	}

	public void addQueryData(String key, Object value) {
		if (null != key && !"".equals(key)) {
			queryData.put(key, value);
		}
	}

	public void removeQueryData(String key) {
		if (null != key && !"".equals(key)) {
			queryData.remove(key);
		}
	}
}
