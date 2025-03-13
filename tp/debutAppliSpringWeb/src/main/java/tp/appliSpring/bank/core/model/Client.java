package tp.appliSpring.bank.core.model;

import tp.appliSpring.generic.model.WithId;

public class Client implements WithId<Long> {
	private Long numero;
	private String prenom;
	private String nom;
	private String email;
	private String adresse;
	private String password;

	public Client(Long numero, String prenom, String nom, String email, String adresse, String password) {
		super();
		this.numero = numero;
		this.prenom = prenom;
		this.nom = nom;
		this.email = email;
		this.adresse = adresse;
		this.password=password;
}

	@Override
	public Long extractId() {
		return this.numero;
	}

	public Client(Long numero, String prenom, String nom, String adresse, String email) {
		this(numero,prenom,nom,adresse,email,null);
	}

	public Client() {
		this(null, null, null, null, null);
	}

	@Override
	public String toString() {
		return "ClientDto [numero=" + numero + ", prenom=" + prenom + ", nom=" + nom + ", email=" + email + ", adresse="
				+ adresse + "]";
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getPassword() {	return password;	}

	public void setPassword(String password) {		this.password = password;	}

}
