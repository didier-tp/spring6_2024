package tp.appliSpring.bank.obs;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import io.micrometer.observation.ObservationTextPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObservabilityHandlerConfig {

    private static final Logger log = LoggerFactory.getLogger(ObservabilityHandlerConfig.class);

    /*
    // ObservationRegistry
    @Bean
    ObservationRegistry observationRegistry() {
        ObservationRegistry myObservationRegistry =ObservationRegistry.create();
        //myObservationRegistry.observationHandler(new ObservationTextPublisher(System.out::println)); //for basic local display
        return myObservationRegistry;
    }
    */

    @Bean
    public ObservationHandler<Observation.Context> observationTextPublisher() {
        return new ObservationTextPublisher(log::info); //to display observation in logger
    }

    /*
    // To have the @Observed support we need to register this aspect (it's seems already done in spring boot )
    @Bean
    ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
        return new ObservedAspect(observationRegistry);
    }
    */

}
