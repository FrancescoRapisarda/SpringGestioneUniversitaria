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



@Entity
@Table (name = "studente")
public class Studente {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "studenteID")
	private int id;
	
	private String nome;
	private String cognome;
	private String facolta;
	
	@Column(name = "cod_matricola")
	private String codMatricola;
	
	
	@ManyToMany
	@JoinTable(name="studente_esame",
				joinColumns = { @JoinColumn(name="studente_rif", referencedColumnName = "studenteID") },
				inverseJoinColumns = { @JoinColumn(name="esame_rif", referencedColumnName = "esameID") }
			)
	private List<Esame> elencoEsami;
	
	
	public Studente() {
		
	}
	
	
	
	public Studente(String nome, String cognome, String facolta, String codMatricola) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.facolta = facolta;
		this.codMatricola = codMatricola;
	}




	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getFacolta() {
		return facolta;
	}
	public void setFacolta(String facolta) {
		this.facolta = facolta;
	}
	public String getCodMatricola() {
		return codMatricola;
	}
	public void setCodMatricola(String codMatricola) {
		this.codMatricola = codMatricola;
	}
	public List<Esame> getElencoEsami() {
		return elencoEsami;
	}
	public void setElencoEsami(List<Esame> elencoEsami) {
		this.elencoEsami = elencoEsami;
	}



	@Override
	public String toString() {
		return "Studente [id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", facolta=" + facolta
				+ ", codMatricola=" + codMatricola + "]";
	}
	
	
	/*
	 * DETTAGLIO STUDENTE
	 */
	public void dettaglioStudente() {
		System.out.println("Studente \n"
				+ "id= " + id + "\n"
				+ "nome= " + nome + "\n"
				+ "cognome= " + cognome + "\n"
				+ "facolta= " + facolta + "\n"
				+ "codMatricola= " + codMatricola + "\n"
				+ "elencoEsami= " + elencoEsami);
	}
	
	
}
