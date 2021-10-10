package spring.universita2021.SpringHibernateGestioneUniversitaria2021.models;

import java.util.ArrayList;
import java.util.List;

/*
 * Questo uniforma le risposte (In questa classe diamo una "risposta" a chi lancia una request fornendo una response che indica se cio che si sta cercando è presente o no ("SUCCESS" or "NOT FOUND")
 * lo STATUS puo contenere una Stringa contenente "SUCCESS" oppure "ERROR".
 */
public class StudenteResponse {
	
	private String status;
	private List<Studente> elenco = new ArrayList<Studente>();
	
	
	public StudenteResponse() {
		
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Studente> getElenco() {
		return elenco;
	}
	public void setElenco(List<Studente> elenco) {
		this.elenco = elenco;
	}
	
	
	
	

}
