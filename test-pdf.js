const { generatePDF } = require('./nsdms-next/lib/pdf/playwright-engine');

(async () => {
    try {
        console.log("Testing Chromium Launch...");
        // Hit google.com just to test chromium is working
        const pdf = await generatePDF({
            url: "https://example.com",
            format: "A4"
        });
        console.log("SUCCESS! Got PDF buffer of size:", pdf.length);
    } catch(e) {
        console.error("FAIL", e);
    }
})();
