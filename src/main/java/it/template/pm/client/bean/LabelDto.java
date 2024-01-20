package it.template.pm.client.bean;

import java.io.Serializable;

public class LabelDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String label;
	
	public LabelDto(String label) {
		super();
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
