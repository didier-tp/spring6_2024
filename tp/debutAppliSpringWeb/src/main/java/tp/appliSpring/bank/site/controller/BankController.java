package tp.appliSpring.bank.site.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import tp.appliSpring.bank.core.model.Client;
import tp.appliSpring.bank.core.model.Compte;
import tp.appliSpring.bank.core.service.ServiceClient;
import tp.appliSpring.bank.core.service.ServiceCompte;
import tp.appliSpring.bank.site.form.VirementForm;

import java.util.List;


@Controller
@RequestMapping("/site/bank")
@SessionAttributes({"client" , "numClient" , "password" })
public class BankController {
	@Autowired
	private ServiceClient serviceClient;

	@Autowired
	private ServiceCompte serviceCompte;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@ModelAttribute("client")
	public Client addDefaultClientAttributeInModel() {
		//NB: new ClientDto(Long numero, String prenom, String nom, String email, String adresse)
		return new Client(null,"prenom?", "nom?" ,"ici_ou_la@xyz.com" , "adresse ?");
	}

	@ModelAttribute("compte")
	public Compte addDefaultCompteAttributeInModel() {
		//NB: new CompteDto( numero,  label, solde)
		return new Compte(null,"", 0.0);
	}

	@ModelAttribute("virement")
	public VirementForm addDefaultVirementAttributeInModel() {
		//NB: new VirementForm(montant, numCptDeb, numCptCred)
		return new VirementForm(null,null, null);
	}


	@RequestMapping("/clientLoginWithSecurity")
	public String clientLoginWithSecurity(Model model) {
		//avec "navigation hook" géré automatiquement par spring-security (redirection automatique vers login.html , ...)

		//on récupère le username de l'utilisateur loggé avec spring security
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){
			String username =auth.getName();
			System.out.println("clientLoginWithSecurity , username="+username);
			//on considère que username vaut (par convention dans ce Tp) "client_" + numClient
			//et on extrait donc le numero du client authentifié:
			Long numClient= Long.parseLong(username.substring(7));
			System.out.println("clientLoginWithSecurity , numClient="+numClient);
			if(numClient!=null) {
				Client client = serviceClient.searchById(numClient);
				model.addAttribute("client", client);

				model.addAttribute("password", "pwd");//cas d'école (tp)
				model.addAttribute("message", "successful login");
				model.addAttribute("numClient", numClient);
			}
			return "client_direct_login";
		}
		return "welcome";
	}

	//direct , without spring security
	@RequestMapping("/clientLoginDirect")
	 public String clientLogin(Model model,
			 @RequestParam(name="numClient", required = false)  Long numClient,
			 @RequestParam(name="password", required = false)  String password) {
		System.out.println("/site/compte/clientLoginDirect with numClient="+numClient + " and tempPassword=" + password);
		String message="";
		if(numClient==null )
			message="numClient is required";
		else {
			if(password==null || password.isEmpty())
				message="tempPassword is required";
			/*
			else {
				if(!password.equals("pwd")) {
					message="wrong tempPassword";
				}
				else {
					message="successful login";
				}
			}*/
		}
		if(numClient!=null) {
			Client client = serviceClient.searchById(numClient);
			String cryptedPwd = client.getPassword();
			System.out.println("/site/compte/clientLoginDirect: cryptedPwd="+cryptedPwd);
			if(passwordEncoder.matches(password,cryptedPwd)){
				message="successful login";
				model.addAttribute("client", client);
			}else{
				message="wrong password";
			}
		}
	    
		model.addAttribute("message", message);
		model.addAttribute("numClient", numClient);
		model.addAttribute("password", password);
	    return "client_direct_login"; //aiguiller sur la vue "client_direct_login"
	 }

	@RequestMapping("comptesDuClient")
	public String comptesDuClient(Model model) {
		/* Long numClient=(Long)model.getAttribute("numClient");

		 */
		Client client = (Client) model.getAttribute("client");
		Long numClient = client!=null ? client.getNumero() : null;

		if(numClient==null)
			return "clientLogin";
		/*else*/
		List<Compte> listeComptes = serviceCompte.searchCustomerAccounts(numClient);
		//System.out.println("listeComptes="+listeComptes);
		model.addAttribute("listeComptes", listeComptes);
		return "comptes";
	}

	@RequestMapping("toAddCompte")
	public String toAddCompte(Model model) {
		Long numClient=(Long)model.getAttribute("numClient");
		if(numClient==null)
			return "client_login";
		return "add_compte";
	}

	//NB: un @ModelAttribute("xxx") défini dans un controller 1
	//semble être accessible depuis un controller 2 .


	@RequestMapping("doAddCompte")
	public String doAddCompte(Model model,
							  @Valid @ModelAttribute("compte") Compte compte,
							  BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			// form validation error
			System.out.println("form validation error: " + bindingResult.toString());
			return "add_compte";
		}
		/*else*/
		try {
			Long numClient=(Long)model.getAttribute("numClient");
			if(numClient==null)
				return "clientLogin";
			compte = serviceCompte.create(compte);
			serviceCompte.fixerProprietaireCompte(compte.getNumero(), numClient);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			return "add_compte";
		}
		return comptesDuClient(model); //réactualiser et afficher nouvelle liste des comptes
	}

	@RequestMapping("toVirement")
	public String toVirement(Model model,HttpSession httpSession) {
		Long numClient=(Long)model.getAttribute("numClient");
		if(numClient==null)
			return "clientLogin";
		List<Compte> listeComptes = serviceCompte.searchCustomerAccounts(numClient);
		model.addAttribute("listeComptes", listeComptes);  //pour listes déroulantes (choix numCptDeb et numCptCred)
		return "virement";
	}


	@RequestMapping("doVirement")
	public String doVirement(Model model,
							 HttpSession httpSession,
							 @Valid @ModelAttribute("virement") VirementForm virement,
							 BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			// form validation error
			System.out.println("form validation error: " + bindingResult.toString());
			return "virement";
		}
		try {
			serviceCompte.transfer(virement.getMontant(), virement.getNumCptDeb(), virement.getNumCptCred());
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			Long numClient=(Long)model.getAttribute("numClient");
			List<Compte> listeComptes = serviceCompte.searchCustomerAccounts(numClient);
			model.addAttribute("listeComptes", listeComptes);
			return "virement";
		}
		return comptesDuClient(model); //réactualiser et afficher nouvelle liste des comptes
	}

}
