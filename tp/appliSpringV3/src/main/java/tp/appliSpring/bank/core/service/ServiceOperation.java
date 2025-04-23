package tp.appliSpring.bank.core.service;

import java.util.List;

import tp.appliSpring.bank.core.model.Operation;

public interface ServiceOperation {
    public Operation create(Operation op, Long numCpt);
    public List<Operation> searchByCompte(long numCpt);
}
