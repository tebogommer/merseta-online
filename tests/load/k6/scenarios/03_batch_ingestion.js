import http from 'k6/http';
import { check, group, sleep } from 'k6';
import { BASE_URL, defaultThresholds, getProfile } from '../k6.config.js';

const profileName = __ENV.PROFILE || 'smoke';
const payloads = JSON.parse(open('../data/sample_payloads.json'));

export const options = {
    ...getProfile(profileName),
    thresholds: defaultThresholds,
};

export default function () {
    const batch = payloads.batchSubmissions[Math.floor(Math.random() * payloads.batchSubmissions.length)];

    group('01. Submit High-Volume SETMIS Batch', function () {
        const batchMetadata = {
            batchType: batch.batchType,
            recordCount: batch.recordCount,
            sourceChamber: batch.sourceChamber,
            timestamp: new Date().toISOString(),
        };

        const boundary = '----k6FormBoundary' + Math.random().toString(36).substring(2);
        const dummyFileContent = 'SETMIS_HEADER_REC\n' + 'VAL_LINE_DATA,'.repeat(10) + '\n'.repeat(50);

        const res = http.post(`${BASE_URL}/api/batches/upload`, JSON.stringify(batchMetadata), {
            headers: {
                'Content-Type': 'application/json',
                'User-Agent': 'k6-BatchIngestor/1.0',
            },
            tags: { name: 'PostBatchIngestion' },
        });

        check(res, {
            'batch accepted without server 5xx error': (r) => r.status < 500,
        });

        sleep(1.5);
    });

    group('02. Poll Batch Processing Status', function () {
        const batchId = Math.floor(Math.random() * 100) + 1;
        const res = http.get(`${BASE_URL}/api/batches/${batchId}/status`, {
            headers: { 'Accept': 'application/json' },
            tags: { name: 'GetBatchStatus' },
        });

        check(res, {
            'polling status returned without server crash': (r) => r.status < 500,
            'polling latency < 400ms': (r) => r.timings.duration < 400,
        });

        sleep(1);
    });
}
