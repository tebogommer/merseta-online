import http from 'k6/http';
import { check, group, sleep } from 'k6';
import { BASE_URL, defaultThresholds, getProfile } from '../k6.config.js';

const profileName = __ENV.PROFILE || 'smoke';

export const options = {
    ...getProfile(profileName),
    thresholds: defaultThresholds,
};

const headers = {
    'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8',
    'User-Agent': 'k6-NSDMS-LoadTester/1.0',
    'Accept-Language': 'en-ZA,en;q=0.9',
};

export default function () {
    group('01. Dashboard & Landing Page', function () {
        const res = http.get(`${BASE_URL}/`, { headers, tags: { name: 'GetDashboard' } });
        check(res, {
            'dashboard status is 200 or 302': (r) => r.status === 200 || r.status === 302,
            'dashboard responds < 500ms': (r) => r.timings.duration < 500,
        });
        sleep(0.5);
    });

    group('02. Employers & Organisations Master Grid', function () {
        const res = http.get(`${BASE_URL}/employers`, { headers, tags: { name: 'GetEmployers' } });
        check(res, {
            'employers status is 200 or 302': (r) => r.status === 200 || r.status === 302,
        });
        sleep(0.8);
    });

    group('03. Mandatory Grants (WSP/ATR) Module', function () {
        const res = http.get(`${BASE_URL}/wsp`, { headers, tags: { name: 'GetWspList' } });
        check(res, {
            'wsp status is 200 or 302': (r) => r.status === 200 || r.status === 302,
        });
        sleep(0.6);
    });

    group('04. Discretionary Grants Windows & Applications', function () {
        const res = http.get(`${BASE_URL}/discretionary-grants`, { headers, tags: { name: 'GetDiscretionaryGrants' } });
        check(res, {
            'dg status is 200 or 302': (r) => r.status === 200 || r.status === 302,
        });
        sleep(0.5);
    });

    group('05. Learnership & Skills Programmes Register', function () {
        const res = http.get(`${BASE_URL}/learners`, { headers, tags: { name: 'GetLearners' } });
        check(res, {
            'learners status is 200 or 302': (r) => r.status === 200 || r.status === 302,
        });
        sleep(1);
    });
}
