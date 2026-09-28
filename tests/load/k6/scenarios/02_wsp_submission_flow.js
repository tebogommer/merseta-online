import http from 'k6/http';
import { check, group, sleep } from 'k6';
import { BASE_URL, defaultThresholds, getProfile } from '../k6.config.js';

const profileName = __ENV.PROFILE || 'smoke';
const payloads = JSON.parse(open('../data/sample_payloads.json'));

export const options = {
    ...getProfile(profileName),
    thresholds: defaultThresholds,
};

const jsonHeaders = {
    'Content-Type': 'application/json',
    'Accept': 'application/json, text/plain, */*',
    'User-Agent': 'k6-NSDMS-WspRunner/1.0',
};

export default function () {
    const randomOrg = payloads.organisations[Math.floor(Math.random() * payloads.organisations.length)];
    const randomWsp = payloads.wspSubmissions[Math.floor(Math.random() * payloads.wspSubmissions.length)];

    group('01. Initiate WSP/ATR Application', function () {
        const initPayload = JSON.stringify({
            finYear: randomWsp.finYear,
            sdlNumber: randomOrg.sdlNumber,
            employeeCount: randomWsp.employeeCount,
            plannedTrainingBudget: randomWsp.plannedTrainingBudget,
        });

        const res = http.post(`${BASE_URL}/api/wsp/draft`, initPayload, {
            headers: jsonHeaders,
            tags: { name: 'PostWspDraft' },
        });

        // Accept 200, 201, or 404 (if mock/endpoint depends on blazor circuit)
        check(res, {
            'draft initiated without 5xx error': (r) => r.status < 500,
            'latency under threshold': (r) => r.timings.duration < 600,
        });

        sleep(1);
    });

    group('02. Submit Skills Development Planning Grid', function () {
        const planPayload = JSON.stringify({
            finYear: randomWsp.finYear,
            sdlNumber: randomOrg.sdlNumber,
            programmes: [
                {
                    ofoCode: randomWsp.ofoCode,
                    occupationalCategory: randomWsp.occupationalCategory,
                    programmeTypeCode: "02",
                    nqfLevel: 4,
                    beneficiaryCount: 15,
                    estimatedCost: 65000.00
                }
            ]
        });

        const res = http.post(`${BASE_URL}/api/wsp/training-plan`, planPayload, {
            headers: jsonHeaders,
            tags: { name: 'PostTrainingPlan' },
        });

        check(res, {
            'training plan response valid': (r) => r.status < 500,
        });

        sleep(0.8);
    });

    group('03. Digital Quorum Sign-off & Final Submission', function () {
        const signoffPayload = JSON.stringify({
            sdlNumber: randomOrg.sdlNumber,
            finYear: randomWsp.finYear,
            signoffRole: "SDF",
            digitalSecuritySeal: "SHA256_SEAL_SIMULATED_" + Date.now(),
        });

        const res = http.post(`${BASE_URL}/api/wsp/signoff`, signoffPayload, {
            headers: jsonHeaders,
            tags: { name: 'PostWspSignoff' },
        });

        check(res, {
            'signoff completed without crash': (r) => r.status < 500,
        });

        sleep(1.2);
    });
}
