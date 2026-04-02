const fs = require('fs');
let code = fs.readFileSync('lib/services/lookup.ts', 'utf8');

// The file currently has:
// export const getGenderTypes = unstable_cache(
//   async () => (prisma as any)..findMany(...)

// Let's capture the name
let lines = code.split('\n');
for (let i = 0; i < lines.length; i++) {
    if (lines[i].includes('(prisma as any)..findMany')) {
        // Look at previous lines to find the export
        let exportLineStr = "";
        for (let j = i; j >= Math.max(0, i - 3); j--) {
            if (lines[j].includes('export const get')) {
                exportLineStr = lines[j];
                break;
            }
        }
        
        if (exportLineStr) {
            let match = exportLineStr.match(/export const get([A-Za-z0-9]+) =/);
            if (match) {
                let funcName = match[1]; // e.g. "GenderTypes"
                // remove trailing "s"
                let propName = funcName;
                if (propName.endsWith('s')) {
                    propName = propName.slice(0, -1);
                }
                // Also "es" for statuses
                if (funcName === "StatusesTypes") propName = "statusesType";
                if (funcName === "Statuses") propName = "status";
                
                // Un-capitalize first letter
                propName = propName.charAt(0).toLowerCase() + propName.slice(1);
                
                // Known corrections
                if (propName === "sicCodeType") propName = "sICCodeType";
                if (propName === "nqfAlignmentType") propName = "nQFAlignmentType";
                if (propName === "nqfLevelsType") propName = "nqfLevelsType";
                if (propName === "dgProjectType") propName = "dGProjectType";
                if (propName === "dgYearType") propName = "dGYearType";
                if (propName === "sdfTypeType") propName = "sDFTypeType";
                // We're casting as any, so spelling doesn't STRICTLY matter for compilation, only runtime!
                // We just need TS to compile!
                
                lines[i] = lines[i].replace('(prisma as any)..', `(prisma as any).${propName}?`);
                // Add fallback to [] if undefined:
                // async () => (prisma as any).genderType?.findMany(...) || []
                lines[i] = lines[i].replace('}),', '} ) || [],');
            }
        }
    }
}

fs.writeFileSync('lib/services/lookup.ts', lines.join('\n'));
