package tp.appliSpring.bank.core.hex.spi;

import tp.appliSpring.generic.hex.spi.Loader;
import tp.appliSpring.bank.core.model.Compte;

import java.util.List;

public interface CompteLoader extends Loader<Compte,Long> {

    //with .loadById(id,"withOperations" as wishedDetails) impl

    List<Compte> findBySoldeMini(double soldeMini);
    List<Compte> findByCustomerNumber(long customerNum);
}
