package tp.appliSpring.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/site/app")
public class AppCtrl {
	
	
	@RequestMapping("/calcul-tva")
	public String calculTva(Model model ,
			@RequestParam(name="ht",required=false) Double ht , 
			... taux) {
		
		//calculer tva et ttc
		
		model.addAttribute("tva", ....);
		model.addAttribute("ttc", ....);
		return "tva"; // aiguiller sur la vue "tva" (.html)
	}
	
	@RequestMapping("/hello-world-th")
	public String helloWorld(Model model) {
		model.addAttribute("message", "Hello World!");
		return "showMessage"; // aiguiller sur la vue "showMessage"
	}
	
	@RequestMapping("/to-welcome")
	public String toWelcome(Model model) {
		model.addAttribute("message", "Welcome dans mon appli Spring");
		return "welcome"; // aiguiller sur la vue "welcome" (.jsp ou .html)
	}
}