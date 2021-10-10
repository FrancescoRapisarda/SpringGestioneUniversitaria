package spring.universita2021.SpringHibernateGestioneUniversitaria2021.services;

import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.universita2021.SpringHibernateGestioneUniversitaria2021.models.Studente;
import spring.universita2021.SpringHibernateGestioneUniversitaria2021.repositories.DataAccessRepo;



@Service		//Service si occuperà della manipolazione dei dati (@Service o altre annotazioni speciali trasformano l'oggetto in un BEAN (contenitore) che puo essere inniettato nella nostra struttura.
public class StudenteService implements DataAccessRepo<Studente>{
	
	@Autowired  		//Quando il Service viene creato (inizializzato) come StudenteService vai a ricrearti automaticamente la struttura della EntityManager senza che nemmeno me ne accorgo! 
	private EntityManager entMan;
	
	private Session getSessione() {
		return entMan.unwrap(Session.class);		//estraggo semplicemente la Gestione delle Sessioni dalla "Mega" Classe EntityManager!
	}

	/*
	 * INSERT
	 * Inserimento di un nuovo oggetto tramite la creazione di un oggetto temporaneo
	 * nel quale riverso i dati dell'oggetto originale.
	 * Nel caso in cui l'operazione di salvataggio vada a buon fine
	 * restituisco l'oggetto.
	 * objStu -> OGGETTO ORIGINALE
	 * return -> OGGETTO CON ID ASSEGNATO O NULL
	 */
	public Studente insert(Studente objStu) {
		
		Studente temp = new Studente();
		temp.setNome(objStu.getNome());
		temp.setCognome(objStu.getCognome());
		temp.setFacolta(objStu.getFacolta());
		temp.setCodMatricola(objStu.getCodMatricola());
		
		Session sessione = getSessione();		//il getSessione() da qualche parte lo devo creare.. per farlo devo aggiungere Autowired (mi risparmio di creare new EntityManager). CON Autowired E' GIA DISPONIBILE ALLA CREAZIONE DELL'OGGETTO! 
		
		try {
			
			sessione.save(temp);
			
			return temp;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());

			return null;
			
		} finally {				//la finally è gestita dalla JPA di Spring e quindi puo essere eclissata, qui la metto per eccesso di zelo!
			sessione.close();
		}
		
	}
	
	
	/*
	 * PROBLEMA CON IL BYTE BUDDY PER IL RECUPERO DEI DATI NEL DATABASE 
	 */
//	public Studente findById(int id) {
//		
//		Session sessione = getSessione();
//		
//		try {
//			
//			Studente temp = sessione.load(Studente.class, id);
//			return temp;
//			
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			
//			return null;
//		} finally {
//			sessione.close();
//		}
//		
//	}
	
	
	/*
	 * FINDBYID
	 * Ricerca di un oggetto tramite ID, il CRITERIA applice delle RESTRIZIONI (Restrictions) sulla classe STUDENTE,
	 * in particolare restituisce un UNIQUERESULT se va a buon fine altrimenti NULL (specificato come RESTRICTION).
	 * varId -> INTERO
	 * return -> o OGGETTO TROVATO oppure NULL
	 * Agiamo in questo modo per evitare gli errori del BYTE BUDDY (vedi metodo sopra)
	 */
	public Studente findById(int varId) {
		
		Session sessione = getSessione();
		
//		return (Studente) sessione.createCriteria(Studente.class)		//Deprecato ma funziona! TUTTAVIA (fra due o tre anni) POTREBBE ANDARE IN DISUSO.. (Il CreateCriteria viene da HIBERNATE!)
//				.add(Restrictions.eqOrIsNull("id", varId))
//				.uniqueResult();		//Creo dei sistemi di ricerca sulla classe STUDENTE!

		try {
			
			CriteriaBuilder cBuilder = entMan.getCriteriaBuilder();			//Costruisco il Builder dei criteri di ricerca tramite il quale posso generare le query. CriteriaBuilder è una funzionalità del EntityManager (ma EntityManager è una Classe "speciale" che non ha niente a che fare con HIBERNATE!) In pratica Hibernate da solo non c'è la fa a sviluppare tutte le funzionalità del JPA (ovvero la versione di persistenza del Java che è una delle funzionalità dell'EntityManager). In pratica hibernate sta cercando di ritornare indietro e di utilizzare degli standard che sono portati avanti da JAVA (da ORACLE quindi), Hibernate in parole spicciole alza le mani e dice: "Ragazzi io non ci riesco a gestire questa libreria utiliziamo quella di JAVA"! QUESTO UTILIZZO E' IMPORTANTE QUANDO FACCIAMO CAMBI DI TECNOLOGIA, PERCHE' SE AD ESEMPIO USASSIMO ALTRI ORM (Object Relational Mapping) come ad esempio Hibernate che è il piu performante (ma ce ne stanno a bizzeffe) non dobbiamo stravolgere il codice, cambia solo qualcosa! 
			CriteriaQuery<Studente> cQuery = cBuilder.createQuery(Studente.class);	//Creo una query di ricerca che si applica su tutti i campi della classe Studente (ATTRIBUTI) ovvero su quello che viene definito all'interno della nostra stuttura vsibile in JAVA!
			
			Root<Studente> root = cQuery.from(Studente.class);	//Equivalente a: FROM Studente
		 	cQuery.select(root).where(cBuilder.equal(root.get("id"), varId));  //Equivalente di SELECT * ... WHERE Studente id = varId
			
			Query<Studente> query = sessione.createQuery(cQuery); //Eqivalente di PreparedStatement
			
			return(Studente) query.getSingleResult();	//Restituisco un singolo risultato dalla query
			
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
			return null;
			
		} finally {
			sessione.close();
		}
		
	}
	
	
	/*
	 * FINDALL
	 * Lista di Studenti corrispondenti al createCriteria (riferimento a Studente.class) sottoforma di Lista.
	 * return -> List di prodotto, vuota se non trova niente
	 */
	public List<Studente> findAll() {
		
		Session sessione = getSessione();
		
//		return sessione.createCriteria(Studente.class).list();		//createCriteria DEPRECATED!
		
		CriteriaQuery<Studente> cquery = sessione.getCriteriaBuilder().createQuery(Studente.class);
		cquery.from(Studente.class); //Equivalente di: FROM Studente
		
		return sessione.createQuery(cquery).getResultList();
	}
	
	
	/*
	 * DELETE
	 */
	@Transactional							// Grazie a Transactional tutta la classe riesce a gestire in maniera totalmente automatica e autonoma le transazioni, SEQUENZIALMENTE e nello stesso istante; e NESSUNO puo mettersi in mezzo alle transazioni (esempio: Studente temp -> sessione.delete -> sessione.flash)
	public boolean delete(int varId) {
		
		Session sessione = getSessione();
		
		try {
			
			Studente temp = sessione.load(Studente.class, varId);
			
			sessione.delete(temp);	//la DELETE marca l'oggetto come "eliminabile" ma SOLO nella memoria di hibernate!
			sessione.flush();       //la FLUSH rende PERMANENTE sul database l'eliminazione!
			
			return true;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
			return false;
			
		} finally {
			sessione.close();
		}
	}
	
	
	/*
	 * UPDATE
	 */
	@Transactional
	public boolean update(Studente objStu) {
		
		Session sessione = getSessione();
		
		try {
			
			Studente temp = sessione.load(Studente.class, objStu.getId());
			
			if(objStu.getNome() != null)				
				temp.setNome(objStu.getNome());
			
			if(objStu.getCognome() != null)
				temp.setCognome(objStu.getCognome());
			
			if(objStu.getFacolta() != null)
				temp.setFacolta(objStu.getFacolta());
			
			if(objStu.getCodMatricola() != null)
				temp.setCodMatricola(objStu.getCodMatricola());
			
			sessione.save(temp);
			sessione.flush();
			
			return true;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
			return false;
			
		} finally {
			sessione.close();
		}

	}
	
}
