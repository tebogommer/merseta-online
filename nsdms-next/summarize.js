const fs = require('fs'); 
const err = require('./tests_final3.json'); 
const out = []; 
out.push(`Stats: ${JSON.stringify(err.stats)}`); 
err.suites.forEach(s => 
  s.suites?.forEach(ss => 
    ss.specs?.forEach(sp => 
      sp.tests.forEach(t => { 
        if(t.status !== 'expected') {
          out.push(`${s.title} -> ${sp.title}: ${t.results[0].error?.message}`);
        }
      })
    )
  )
); 
fs.writeFileSync('tests_summary3.txt', out.join('\n'));
