package spring.universita2021.SpringHibernateGestioneUniversitaria2021.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.universita2021.SpringHibernateGestioneUniversitaria2021.models.Studente;
import spring.universita2021.SpringHibernateGestioneUniversitaria2021.models.StudenteResponse;
import spring.universita2021.SpringHibernateGestioneUniversitaria2021.services.StudenteService;

@RestController
@RequestMapping("/studente")
public class StudenteController {
	
	@Autowired
	private StudenteService service;

	@PostMapping("/inserisci")
	public StudenteResponse inserisciStudente(@RequestBody Studente objStu) {
		
		StudenteResponse response = new StudenteResponse();
		Studente temp = service.insert(objStu);
		
		if(temp != null) {
			
			response.setStatus("SUCCESS");
			response.getElenco().add(temp);
		
		} else {
			response.setStatus("ERROR");
		}
		
		return response;
		
		
	}
	
	@GetMapping("/trova/{studenteID}")		//http://localhost:8085/studente/trova/idqualsiasi
	public StudenteResponse ricercaStudenteId(@PathVariable(name = "studenteID") int id) {
		
		StudenteResponse response = new StudenteResponse();		//Creo un oggetto uniforme chiamato response!
		Studente temp = service.findById(id);
		
		if(temp != null) {							//nel caso in cui l'oggetto studente... allora:
			response.setStatus("SUCCESS");
			response.getElenco().add(temp);

		} else {
			response.setStatus("NOT FOUND");
		}
		
		return response;
		
	}
	
	@GetMapping("/")
	public StudenteResponse ricercaStudenti() {
		
		StudenteResponse response = new StudenteResponse();
		
		List<Studente> risultati = service.findAll();
		
		if(risultati.size() != 0) {
			response.setStatus("SUCCESS");
			response.setElenco(risultati);
		} else {
			
			response.setStatus("EMPTY SET");
		}
		
		return response;
	}
	
	@DeleteMapping("/elimina/{studenteID}")
	public boolean eliminaStudente(@PathVariable(name = "studenteID") int id) {
		return service.delete(id);
	}
	
	@PutMapping("/update")
	public boolean updateStudente(@RequestBody Studente objStu) {
		return service.update(objStu);
		
	}
	
	
	
}
