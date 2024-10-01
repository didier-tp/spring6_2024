package tp.appliSpring.exemplev2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import java.lang.reflect.InvocationTargetException;


@Configuration
@ComponentScan(basePackages = { "tp.appliSpring.exemplev2" })
//@EnableAspectJAutoProxy //pour prendre en compte les @Aspect
@PropertySource("classpath:exemples.properties")
public class ExempleConfigExplicte {

    @Value("${exemple.calculateur:tp.appliSpring.exemplev2.MonCalculateurCarre}")
    private String calculateurClassName;

    @Bean()
    public MonCalculateur monCalculateur(){
        if(calculateurClassName.equals("tp.appliSpring.exemplev2.MonCalculateurCarre"))
             return new MonCalculateurCarre();
        else if(calculateurClassName.equals("tp.appliSpring.exemplev2.MonCalculateurDouble"))
            return new MonCalculateurDouble();
        else return null;
        /*
        MonCalculateur calculateur = null;
        try {
            calculateur = (MonCalculateur) Class.forName(calculateurClassName).getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return calculateur;
        */
    }

    @Bean()
    @Profile("V1")
    public MonAfficheur monAfficheurV1(){
        return new MonAfficheurV1();
    }

    @Bean()
    @Profile("V2")
    public MonAfficheur monAfficheurV2(){
        return new MonAfficheurV2();
    }


    @Bean()
    public Coordinateur coordinateur(MonAfficheur aff, MonCalculateur calculateur){
        Coordinateur coordinateur = new Coordinateur();
        coordinateur.setMonAfficheur(aff);
        coordinateur.setMonCalculateur(calculateur);
        return coordinateur;
    }

    @Bean()
    public CoordinateurAvecInjectionParConstructeur coordinateurAvecInjectionParConstructeur(
                 MonAfficheur aff, MonCalculateur calculateur){
        CoordinateurAvecInjectionParConstructeur coordinateur =
                new CoordinateurAvecInjectionParConstructeur(aff,calculateur);
        return coordinateur;
    }

}
