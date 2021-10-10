package spring.universita2021.SpringHibernateGestioneUniversitaria2021.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;




@Entity
@Table(name="esame")
public class Esame {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="esameID")
	private int id;
	
	@Column(name="materia")
	private String subject;
	
	@Column(name="data_esame")
	private String examDate;
	
	private int cfu;
	
	@JsonBackReference
	@ManyToMany
	@JoinTable(name="studente_esame",
				joinColumns = { @JoinColumn(name="esame_rif", referencedColumnName = "esameID") },
				inverseJoinColumns = { @JoinColumn(name="studente_rif", referencedColumnName = "studenteID") }
			)
	private List<Studente> elencoStudenti; 
	
	
	
	public Esame() {
		
	}
	
	

	public Esame(String subject, String examDate, int cfu) {
		super();
		this.subject = subject;
		this.examDate = examDate;
		this.cfu = cfu;
	}





	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getExamDate() {
		return examDate;
	}
	public void setExamDate(String examDate) {
		this.examDate = examDate;
	}
	public int getCfu() {
		return cfu;
	}
	public void setCfu(int cfu) {
		this.cfu = cfu;
	}
	public List<Studente> getElencoStudenti() {
		return elencoStudenti;
	}
	public void setElencoStudenti(List<Studente> elencoStudenti) {
		this.elencoStudenti = elencoStudenti;
	}



	@Override
	public String toString() {
		return "Esame [id=" + id + ", subject=" + subject + ", examDate=" + examDate + ", cfu=" + cfu + "]";
	}
	
	/*
	 * DETTAGLIO ESAME
	 */
	public void dettaglioEsame() {
		System.out.println("Esame \n"
				+ "id= " + id + "\n"
				+ "materia= " + subject + "\n"
				+ "dataEsame= " + examDate + "\n"
				+ "cfu= " + cfu + "\n"
				+ "elencoStudenti= " + elencoStudenti);
	}


}
