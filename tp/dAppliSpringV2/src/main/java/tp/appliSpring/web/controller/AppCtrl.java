package tp.appliSpring.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/site/app")
public class AppCtrl {
	
	
	//équivalent à model.addAttribute("username","unknown");
	@ModelAttribute("username")
	public String defaultUsernameInModel() {
		return "unknown";
	}
	
	@RequestMapping("/calcul-tva")
	public String calculTva(Model model ,
			@RequestParam(name="ht",required=false) Double ht , 
			@RequestParam(name="taux",required=false) Double taux) {
		
		//calculer tva et ttc
		if(ht==null) ht=0.0;
		if(taux==null) taux =0.0;
		Double tva = ht * taux/100 ;
		Double ttc = ht + tva ;
		
		model.addAttribute("ht", ht);
		model.addAttribute("taux", taux);
		model.addAttribute("tva", tva);
		model.addAttribute("ttc", ttc);
		return "tva"; // aiguiller sur la vue "tva" (.html)
	}
	
	@RequestMapping("/to-login")
	public String verifLogin() {
		return "login"; // aiguiller sur la vue "login" (.html)
	}
	
	@RequestMapping("/verif-login")
	public String verifLogin(Model model ,
			@RequestParam(name="username",required=false) String username) {
		//V1 (sans verif password)
		model.addAttribute("username", username);
		return "welcome"; // aiguiller sur la vue "welcome" (.html)
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