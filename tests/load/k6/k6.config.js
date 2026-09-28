/**
 * merSETA NSDMS - k6 Global Configuration & Profiles
 * Defines target URLs, SLA thresholds, and execution stages.
 */

export const BASE_URL = __ENV.BASE_URL || 'http://localhost:5121';
export const WS_URL = __ENV.WS_URL || 'ws://localhost:5121';

// Standard statutory SLA thresholds
export const defaultThresholds = {
    // 95% of requests must complete below 500ms; 99% below 1000ms
    http_req_duration: ['p(95)<500', 'p(99)<1000'],
    // HTTP failure rate must remain below 1%
    http_req_failed: ['rate<0.01'],
};

// Workload Profiles
export const profiles = {
    smoke: {
        vus: 2,
        duration: '10s',
    },
    average_load: {
        stages: [
            { duration: '30s', target: 20 },  // Ramp-up
            { duration: '1m', target: 50 },   // Steady state
            { duration: '30s', target: 50 },  // Plateau
            { duration: '20s', target: 0 },   // Ramp-down
        ],
    },
    stress: {
        stages: [
            { duration: '30s', target: 50 },
            { duration: '1m', target: 150 },
            { duration: '1m', target: 300 },
            { duration: '30s', target: 0 },
        ],
    },
    spike: {
        stages: [
            { duration: '10s', target: 10 },
            { duration: '20s', target: 250 }, // Instant spike (e.g. deadline rush)
            { duration: '40s', target: 250 },
            { duration: '15s', target: 10 },
            { duration: '10s', target: 0 },
        ],
    }
};

export function getProfile(profileName) {
    return profiles[profileName] || profiles.smoke;
}
