package tp.appliSpring.bank.core.service;

import tp.appliSpring.bank.core.exception.BankException;
import tp.appliSpring.bank.core.model.Client;
import tp.appliSpring.generic.service.GenericService;

import java.util.List;

//par defaut , les méthodes peuvent renvoyer RuntimeException
public interface ServiceClient extends GenericService<Client,Long> {

	public Client searchByIdWithAccounts(long numeroCli);
	//...
}
