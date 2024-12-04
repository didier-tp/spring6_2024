package tp.appliSpring.bank.core.service;

import tp.appliSpring.bank.core.model.Operation;

public interface ServiceOperation {
    public Operation create(Operation op, Long numCpt);
}
