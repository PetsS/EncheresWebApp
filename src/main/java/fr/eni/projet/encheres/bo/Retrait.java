package fr.eni.projet.encheres.bo;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;

public class Retrait implements Serializable  {

	private static final long serialVersionUID = 1L;
 
	@NotBlank
	private String rue;

	@NotBlank
	private String code_postal;
	
	@NotBlank
	private String ville;
	
	public Retrait() {
	}
	
	public Retrait(String rue, String code_postal, String ville) {
		this.rue = rue;
		this.code_postal = code_postal;
		this.ville = ville;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getCode_postal() {
		return code_postal;
	}

	public void setCode_postal(String code_postal) {
		this.code_postal = code_postal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Retrait [rue=");
		builder.append(rue);
		builder.append(", code_postal=");
		builder.append(code_postal);
		builder.append(", ville=");
		builder.append(ville);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
