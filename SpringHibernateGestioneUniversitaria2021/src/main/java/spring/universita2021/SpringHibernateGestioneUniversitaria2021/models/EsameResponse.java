package spring.universita2021.SpringHibernateGestioneUniversitaria2021.models;

import java.util.ArrayList;
import java.util.List;

public class EsameResponse {
	
	private String status;
	private List<Esame> elenco = new ArrayList<Esame>();
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Esame> getElenco() {
		return elenco;
	}
	public void setElenco(List<Esame> elenco) {
		this.elenco = elenco;
	}
	
	

}
