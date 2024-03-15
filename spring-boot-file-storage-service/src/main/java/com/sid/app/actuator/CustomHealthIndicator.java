package com.sid.app.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * @author Siddhant Patni
 */
@Component
public class CustomHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        // Perform health check logic here
        boolean isHealthy = true; // Example health check

        if (isHealthy) {
            return Health.up().build(); // Application is healthy
        } else {
            return Health.down().withDetail("Error", "Health check failed").build(); // Application is unhealthy
        }
    }

}