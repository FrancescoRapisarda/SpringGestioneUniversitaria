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

import spring.universita2021.SpringHibernateGestioneUniversitaria2021.models.Esame;
import spring.universita2021.SpringHibernateGestioneUniversitaria2021.repositories.DataAccessRepo;


@Service
public class EsameService implements DataAccessRepo<Esame> {
	
	@Autowired
	private EntityManager entMan;
	
	private Session getSessione() {
		return entMan.unwrap(Session.class);
		
	}
	

	/*
	 * INSERT
	 */
	@Override
	public Esame insert(Esame t) {
		
		//Cloning Factory struttura che non rompe le scatole all'oggetto principale!
		//Sto riversando quello che sta in Esame t all'interno di un nuovo oggetto Esame che poi passo in output;
		Esame temp = new Esame();
		temp.setSubject(t.getSubject());
		temp.setExamDate(t.getExamDate());
		temp.setCfu(t.getCfu());
		
		Session sessione = getSessione();
		
		try {
			
			sessione.save(temp);
			
			return temp;
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
			return null;
		
		} finally {
			sessione.close();
		}
		
	}
	

	/*
	 * FINDBYID (Problema con Byte Buddy)
	 */
//	@Override
//	public Esame findById(int id) {
//		
//		Session sessione = getSessione();
//		
//		try {
//			
//			Esame temp = sessione.load(Esame.class, id);
//			
//			return temp;
//			
//		} catch (Exception e) {
//		
//			System.out.println(e.getMessage());
//			return null;
//		
//		} finally {
//			sessione.close();
//		}
//		
//	}
	

	@Override
	public Esame findById(int id) {
		
		Session sessione = getSessione();
		
//		return (Esame) sessione.createCriteria(Esame.class)
//				.add(Restrictions.eqOrIsNull("id", id))
//				.uniqueResult();
		
		
		try {
			
			CriteriaBuilder cBuilder = entMan.getCriteriaBuilder();			//Costruisco il Builder dei criteri di ricerca tramite il quale posso generare le query. CriteriaBuilder è una funzionalità del EntityManager (ma EntityManager è una Classe "speciale" che non ha niente a che fare con HIBERNATE!) In pratica Hibernate da solo non c'è la fa a sviluppare tutte le funzionalità del JPA (ovvero la versione di persistenza del Java che è una delle funzionalità dell'EntityManager). In pratica hibernate sta cercando di ritornare indietro e di utilizzare degli standard che sono portati avanti da JAVA (da ORACLE quindi), Hibernate in parole spicciole alza le mani e dice: "Ragazzi io non ci riesco a gestire questa libreria utiliziamo quella di JAVA"! QUESTO UTILIZZO E' IMPORTANTE QUANDO FACCIAMO CAMBI DI TECNOLOGIA, PERCHE' SE AD ESEMPIO USASSIMO ALTRI ORM (Object Relational Mapping) come ad esempio Hibernate che è il piu performante (ma ce ne stanno a bizzeffe) non dobbiamo stravolgere il codice, cambia solo qualcosa! 
			CriteriaQuery<Esame> cQuery = cBuilder.createQuery(Esame.class);	//Creo una query di ricerca che si applica su tutti i campi della classe Esame (ATTRIBUTI) ovvero su quello che viene definito all'interno della nostra stuttura vsibile in JAVA!
			
			Root<Esame> root = cQuery.from(Esame.class);	//Equivalente a: FROM Esame
		 	cQuery.select(root).where(cBuilder.equal(root.get("id"), id));  //Equivalente di SELECT * ... WHERE Esame id = varId
			
			Query<Esame> query = sessione.createQuery(cQuery); //Eqivalente di PreparedStatement
			
			return(Esame) query.getSingleResult();	//Restituisco un singolo risultato dalla query
			
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
			return null;
		
		} finally {
			
			sessione.close();
		
		}
		
	}
	
	
	/*
	 * FINDALL
	 */
	@Override
	public List<Esame> findAll() {
		
		Session sessione = getSessione();
		
//		return sessione.createCriteria(Esame.class).list();
		
		CriteriaQuery<Esame> cquery = sessione.getCriteriaBuilder().createQuery(Esame.class);
		cquery.from(Esame.class); //Equivalente di: FROM Esame
		
		return sessione.createQuery(cquery).getResultList();
		
	}

	
	/*
	 * DELETE
	 */
	@Override
	@Transactional										// Grazie a TRANSACTIONAL tutta la classe riesce a gestire in maniera totalmente automatica e autonoma le transazioni, SEQUENZIALMENTE e nello stesso istante; e NESSUNO puo mettersi in mezzo alle transazioni (esempio: Studente temp -> sessione.delete -> sessione.flash)
	public boolean delete(int id) {
		
		Session sessione = getSessione();
		
		try {
			
			Esame temp = sessione.load(Esame.class, id);	//la DELETE avviene in tre tempi: passa alla variabile temp la load + sessione.delete + sessione.flush.
			
			sessione.delete(temp);			//la DELETE marca l'oggetto come "eliminabile" ma SOLO nella memoria di hibernate!
			sessione.flush();				//la FLUSH rende PERMANENTE SUL DB l'eliminazione!
			
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
	@Override
	@Transactional
	public boolean update(Esame t) {
		
		Session sessione = getSessione();
		
		try {
			
			Esame temp = sessione.load(Esame.class, t.getId());
			
			if(t.getSubject() != null)
				temp.setSubject(t.getSubject());
			if(t.getExamDate() != null)
				temp.setExamDate(t.getExamDate());
			if(t.getCfu() != 0)
				temp.setCfu(t.getCfu());
		
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