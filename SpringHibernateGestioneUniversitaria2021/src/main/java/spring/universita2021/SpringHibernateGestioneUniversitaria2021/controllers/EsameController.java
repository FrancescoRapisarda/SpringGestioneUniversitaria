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

import spring.universita2021.SpringHibernateGestioneUniversitaria2021.models.Esame;
import spring.universita2021.SpringHibernateGestioneUniversitaria2021.models.EsameResponse;
import spring.universita2021.SpringHibernateGestioneUniversitaria2021.services.EsameService;

@RestController
@RequestMapping("/esame")
public class EsameController {
	
	@Autowired
	private EsameService service;

	
	@PostMapping("/inserisci")
	public EsameResponse inserisciEsame(@RequestBody Esame t) {
		
		EsameResponse response = new EsameResponse();
		Esame temp = service.insert(t); 
		
		if(temp != null) {
			response.setStatus("SUCCESS");
			response.getElenco().add(temp);
		} else {
			
			response.setStatus("ERROR");
		}
		
		return response;
		
	}
	
	
	@GetMapping("/trova/{esameID}")
	public EsameResponse findexam(@PathVariable(name="esameID") int id) {
		
		EsameResponse response = new EsameResponse();
		Esame temp = service.findById(id);
		
		if(temp != null) {
			response.setStatus("SUCCESS");
			response.getElenco().add(temp);
		} else {
			response.setStatus("NOT FOUND");
		}
		
		return response;
		
	}
	
	
	@GetMapping("/all")
	public EsameResponse ricercaEsami() {
		
		EsameResponse response = new EsameResponse();
		List<Esame> risultati = service.findAll();
		
		if(risultati.size() != 0) {
			
			response.setStatus("SUCCESS");
			response.setElenco(risultati);
			
		} else {
			 response.setStatus("IS EMPTY");
		}
		
		return response;
	}
	
	
	@DeleteMapping("/delete/{esameID}")
	public boolean deleteId(@PathVariable(name="esameID") int id) {
		return service.delete(id);
	}
	
	@PutMapping("/update")
	public boolean updateEsame(@RequestBody Esame t) {
		
		return service.update(t);
	}
}
