package tp.appliSpring.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/site/app")
public class AppCtrl {
	
	
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