package org.mycontrib.basic.comp;

public class BasicPrefixeur implements Prefixeur {
	
	private String prefixe;
	
	public BasicPrefixeur(String prefixe) {
		this.prefixe = prefixe;
	}
	
	public BasicPrefixeur() {
		this("");
	}

	@Override
	public String prefixer(String s) {
		return this.prefixe+s;
	}

	@Override
	public String prefixerMaj(String s) {
		return prefixer(s).toUpperCase();
	}

}
