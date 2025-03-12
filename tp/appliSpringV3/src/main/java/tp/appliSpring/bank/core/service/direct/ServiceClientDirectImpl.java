package tp.appliSpring.bank.core.service.direct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tp.appliSpring.bank.core.exception.BankException;
import tp.appliSpring.bank.core.model.Client;
import tp.appliSpring.bank.core.service.ServiceClient;
import tp.appliSpring.bank.persistence.entity.ClientEntity;
import tp.appliSpring.bank.persistence.repository.ClientRepository;
import tp.appliSpring.generic.converter.GenericMapper;
import tp.appliSpring.generic.service.direct.GenericServiceDirectImpl;

import java.util.List;

/*
implementation en s'appuyant directement sur le dao/repository
(sans abstraction spi.Loader/Saver et sans adaptateur de persistance)
 */


@Service //@Component de type Service
@Transactional
@Qualifier("direct")
@Primary
public class ServiceClientDirectImpl extends GenericServiceDirectImpl<Client,ClientEntity,Long> implements ServiceClient {

	private ClientRepository daoClient;


	@Autowired
	public ServiceClientDirectImpl(ClientRepository daoClient){
		super(Client.class, ClientEntity.class,daoClient);
		this.daoClient=daoClient;
	}




	@Override
	public  Client searchByIdWithAccounts(long numeroCli) {
		ClientEntity clientEntity = daoClient.findWithAccountById(numeroCli);
		return GenericMapper.MAPPER.map(clientEntity,Client.class);
	}

}
