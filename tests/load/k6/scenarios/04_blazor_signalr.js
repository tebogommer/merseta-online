import http from 'k6/http';
import ws from 'k6/ws';
import { check, sleep } from 'k6';
import { BASE_URL, WS_URL, defaultThresholds, getProfile } from '../k6.config.js';

const profileName = __ENV.PROFILE || 'smoke';

export const options = {
    ...getProfile(profileName),
    thresholds: defaultThresholds,
};

export default function () {
    // Step 1: SignalR Negotiate Handshake
    const negotiateUrl = `${BASE_URL}/_blazor/negotiate?negotiateVersion=1`;
    const negotiateRes = http.post(negotiateUrl, '', {
        headers: { 'Content-Type': 'text/plain;charset=UTF-8' },
        tags: { name: 'SignalR_Negotiate' },
    });

    const isNegotiateSuccess = check(negotiateRes, {
        'negotiate status 200': (r) => r.status === 200,
    });

    if (!isNegotiateSuccess) {
        // App might not be running or is purely static SSR in current mode; exit gracefully
        sleep(1);
        return;
    }

    let connectionToken = '';
    try {
        const body = JSON.parse(negotiateRes.body);
        connectionToken = body.connectionToken || body.connectionId || '';
    } catch (e) {
        // fallback
    }

    // Step 2: Establish SignalR WebSocket connection
    const targetWsUrl = `${WS_URL}/_blazor?id=${connectionToken}`;

    const res = ws.connect(targetWsUrl, {}, function (socket) {
        socket.on('open', function () {
            // SignalR Protocol Handshake (JSON with Record Separator ASCII 0x1e)
            const handshake = JSON.stringify({ protocol: 'json', version: 1 }) + '\x1e';
            socket.send(handshake);

            // Send ping heartbeat after 1s
            socket.setTimeout(function () {
                const ping = JSON.stringify({ type: 6 }) + '\x1e';
                socket.send(ping);
            }, 1000);

            // Keep circuit open for 5 seconds to simulate user session
            socket.setTimeout(function () {
                socket.close();
            }, 5000);
        });

        socket.on('message', function (msg) {
            // Received Blazor circuit message
        });

        socket.on('error', function (err) {
            // Circuit error handled
        });
    });

    check(res, {
        'websocket connection established': (r) => r && r.status === 101,
    });

    sleep(1);
}
