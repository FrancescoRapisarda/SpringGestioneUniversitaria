package spring.universita2021.SpringHibernateGestioneUniversitaria2021.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository								//Interfaccia simile a quella usata per il DAO! Le REPOSITORY hanno dentro di loro anche dei metodi implementati [APPROFONDIRE QUESTA TEMATICA]!
public interface DataAccessRepo<T> {
	
	T insert(T t);
	
	T findById(int id);
	
	List<T> findAll();
	
	boolean delete (int id);
	
	boolean update(T t);

}
