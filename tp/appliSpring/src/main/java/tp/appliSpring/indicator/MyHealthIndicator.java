package tp.appliSpring.indicator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class MyHealthIndicator implements HealthIndicator {
	
	long checkSomething(){
		return 1;
	}
	
	@Override
	public Health health() {
		long result = checkSomething();
		if (result <= 0) {
			return Health.down().withDetail("Something Result", result).build();
		}
		return Health.up()
				.withDetail("isXxxOk", true)
				.withDetail("isYyyOk", true)
				.build();
	}
}
