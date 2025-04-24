package tp.appliSpring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import tp.appliSpring.service.ServiceCompte;

@Controller //composant spring de type controller mvc
//traitements avant vue .jsp ou thymleaf
@RequestMapping("/web")
public class CompteController {
	
	@Autowired
	private ServiceCompte serviceCompte;

	//url complète de déclenchement:
	//http://localhost:8181/appliSpring/web/listeComptes
	@RequestMapping("/listeComptes")
	public String listeComptes(Model model) {
	   model.addAttribute("allComptes", serviceCompte.searchAll());
	   return "comptes"; //.jsp
	}
}
