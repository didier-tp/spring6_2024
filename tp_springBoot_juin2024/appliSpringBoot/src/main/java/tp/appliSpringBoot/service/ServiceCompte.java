package tp.appliSpringBoot.service;

public interface ServiceCompte {
    //crud
    public void transferer(Double montant  , long numCptDeb, long numCptCred);
}
