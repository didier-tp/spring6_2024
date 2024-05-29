package tp.appliSpring.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//code de l'annotation @MesurerPerf

//@Target(ElementType.TYPE)//annotation que l'on peut placer sur une classe entière
@Target(ElementType.METHOD) //annotation que l'on peut placer sur une méthode
@Retention(RetentionPolicy.RUNTIME) //annotation conservée dans code compilé
public @interface MesurerPerf {

}
