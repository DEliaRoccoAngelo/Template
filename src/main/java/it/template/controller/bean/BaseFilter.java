package it.template.controller.bean;

import java.io.Serializable;

public class BaseFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Integer rowNumber = 10;
	
	private Integer page = 0;

	public Integer getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(Integer rowNumber) {
		if(rowNumber.intValue() == 0) {
			this.rowNumber = 10;
		} else {
			this.rowNumber = rowNumber;
		}
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
	
	
}
