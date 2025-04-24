package tp.appliSpring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import tp.appliSpring.service.ServiceCompte;

@Controller //commposant spring de type controller de springMVC
//traitement avant vue générant HTML
@RequestMapping("/web")
public class CompteController {
	
	@Autowired
	private ServiceCompte serviceCompte;
	
	//URL de déclenchement:
	//http://localhost:8181/appliSpring/web/listeComptes
	@RequestMapping("/listeComptes")  
	public String listeComptes(Model model) {
		model.addAttribute("allComptes", serviceCompte.searchAll());
		return "comptes"; //l'affichage se fera via comptes.html (ou .jsp)
	}

}
