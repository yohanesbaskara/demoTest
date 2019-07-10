package com.example.demoHomeCredit.common.util;

import java.util.List;

public class PartialList<T> {
	
	List<T> list;
	long total;
	
	public PartialList(List<T> list, long total) {
		super();
		this.list = list;
		this.total = total;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
	
	
	
}
