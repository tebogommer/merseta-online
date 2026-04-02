import * as fs from 'fs';

const data = JSON.parse(fs.readFileSync('./test-results.json', 'utf16le' as BufferEncoding));

const suites = data.suites || [];
for (const suite of suites) {
   for (const spec of suite.specs || []) {
       for (const test of spec.tests || []) {
           for (const result of test.results || []) {
               for (const error of result.errors || []) {
                   console.log("FAIL:", error.message);
               }
               
               for (const step of result.steps || []) {
                    if (step.error) console.log("STEP ERROR:", step.title, step.error.message);
               }
           }
       }
   }
}
