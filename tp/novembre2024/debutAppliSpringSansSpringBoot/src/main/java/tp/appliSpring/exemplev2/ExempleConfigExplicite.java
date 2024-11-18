package tp.appliSpring.exemplev2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource("classpath:exemples.properties")
public class ExempleConfigExplicite {

    @Value("${exemple.calculateur:tp.appliSpring.exemplev2.MonCalculateurCarre}") // :defaultValue
    private String calculateurClassName;

    @Bean
    public MonCalculateur monCalculateur() throws Exception{
        MonCalculateur monCalculateur = null;
        /*
        switch(calculateurClassName){
            case "tp.appliSpring.exemplev2.MonCalculateurCarre" :
                monCalculateur = new MonCalculateurCarre();
                break;
            case "tp.appliSpring.exemplev2.MonCalculateurDouble" :
                monCalculateur = new MonCalculateurDouble();
                break;
        }
        */
        monCalculateur=(MonCalculateur) Class.forName(calculateurClassName).getDeclaredConstructor().newInstance();
        return monCalculateur;
    }

    @Bean
    @Profile({"v1"})
    public MonAfficheur monAfficheurV1(){
        return new MonAfficheurV1();
    }

    @Bean
    @Profile({"v2"}) //ou bien @Profile({"!v1"})
    public MonAfficheur monAfficheurV2(){
        return new MonAfficheurV2();
    }

    @Bean()
    public Coordinateur coordinateur(MonCalculateur monCalculateur,MonAfficheur monAfficheur){
        Coordinateur coordinateur = new Coordinateur();
        coordinateur.setMonAfficheur(monAfficheur); //avec .setMonAfficheur() à éventuellement coder/ajouter
        coordinateur.setMonCalculateur(monCalculateur);
        return coordinateur;
    }

    @Bean()
    public CoordinateurAvecInjectionParConstructeur coordinateurAvecInjectionParConstructeur(MonCalculateur monCalculateur,MonAfficheur monAfficheur){
        CoordinateurAvecInjectionParConstructeur coordinateur =
                new CoordinateurAvecInjectionParConstructeur(monAfficheur,monCalculateur);
        return coordinateur;
    }

}

/*
  @Bean
    public MonCalculateur monCalculateur(){
        //if, else selon properties
        if(this.calculateurClassName.equals("tp.appliSpring.exemplev2.MonCalculateurCarre"))
              return new MonCalculateurCarre();
        else if(this.calculateurClassName.equals("tp.appliSpring.exemplev2.MonCalculateurDouble"))
            return new MonCalculateurDouble();
        else return null;
    }
 */