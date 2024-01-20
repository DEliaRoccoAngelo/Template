package it.template.pm.client.bean;

import java.io.Serializable;
import java.util.List;

public class TokenInfoDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String label;
	
	private List<LabelDto> lista;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<LabelDto> getLista() {
		return lista;
	}

	public void setLista(List<LabelDto> lista) {
		this.lista = lista;
	}

}
