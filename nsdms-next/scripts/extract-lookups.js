const fs = require('fs');
const schema = fs.readFileSync('../prisma/schema.prisma', 'utf8');
const models = schema.match(/model \w+Type \{/g);

const result = models.map(m => m.split(' ')[1]).map(m => {
  const route = m.replace(/([a-z])([A-Z])/g, '$1-$2').toLowerCase();
  const name = m.replace(/([a-z])([A-Z])/g, '$1 $2');
  return { model: m, route: route, name: name };
});

const categories = {
  Demographics: ['GenderType', 'EquityType', 'NationalityType', 'ProvinceType', 'DisabilityStatusType', 'CitizenResidentStatusType', 'OfoCodesType'],
  Financial: ['BankType', 'PaymentMethodType', 'FundingModelType', 'SarsEmployerFileType'],
  Institutional: ['ProviderTypeType', 'ProviderClassType', 'AccreditationStatusType', 'ChamberType', 'DepartmentType'],
  Legacy: []
};

// Auto-group legacy/uncategorized ones
const classifiedModels = result.map(obj => {
  let matchedCat = 'Uncategorized';
  for (const [cat, items] of Object.entries(categories)) {
     if (items.includes(obj.model)) {
        matchedCat = cat;
        break;
     }
  }
  return { ...obj, category: matchedCat };
});

const tsContent = `export interface LookupDef {
  model: string;
  route: string;
  name: string;
  category: string;
}

export const LOOKUP_MODELS: LookupDef[] = ${JSON.stringify(classifiedModels, null, 2)};
`;

fs.mkdirSync('../app/admin/lookups/_config', { recursive: true });
fs.writeFileSync('../app/admin/lookups/_config/lookup-registry.ts', tsContent);
console.log('Registry created with', classifiedModels.length, 'models.');
