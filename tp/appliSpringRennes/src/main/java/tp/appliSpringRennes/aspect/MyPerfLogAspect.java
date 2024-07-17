package tp.appliSpringRennes.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Profile("dev")
public class MyPerfLogAspect {

    @Pointcut("execution(* tp.appliSpringRennes.service.*.*(..))")
    public void servicePointcut(){
    }
    @Pointcut("execution(* tp.appliSpringRennes.dao.*.*(..))")
    public void daoPointcut(){
    }

    //@annotation pour annotation @LogExecutionTime (avec @Target(ElementType.METHOD))
    // placée sur une méthode (sujet de l'application de l'aspect)
    @Pointcut("@annotation(tp.appliSpringRennes.annotation.LogExecutionTime)")
    public void annotLogExecutionTimePointcut(){
    }

    @Around("(servicePointcut() || daoPointcut()) && annotLogExecutionTimePointcut()")
    public Object doXxxLog(ProceedingJoinPoint pjp)
            throws Throwable {
        System.out.println("<< trace == debut == "
                + pjp.getSignature().toLongString() + " <<");
        long td=System.nanoTime();
        Object objRes = pjp.proceed();
        long tf=System.nanoTime();
        System.out.println(">> trace == fin == "
                + pjp.getSignature().toShortString() +
                " [" + (tf-td)/1000000.0 + " ms] >>");
        return objRes;
    }
}

