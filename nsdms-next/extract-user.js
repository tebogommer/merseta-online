const fs = require('fs');
const text = fs.readFileSync('prisma/schema.prisma', 'utf8');
const start = text.indexOf('model User {');
const end = text.indexOf('\n}', start) + 2;
fs.writeFileSync('_temp_user_utf8.txt', text.substring(start, end), 'utf8');
