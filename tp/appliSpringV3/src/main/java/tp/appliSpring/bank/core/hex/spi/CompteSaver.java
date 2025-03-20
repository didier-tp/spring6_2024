package tp.appliSpring.bank.core.hex.spi;

import tp.appliSpring.generic.hex.spi.Saver;
import tp.appliSpring.bank.core.model.Compte;

public interface CompteSaver extends Saver<Compte,Long> {
    public void setAccountOwnerNumber(long accountNumber, long customerNumber);
}
