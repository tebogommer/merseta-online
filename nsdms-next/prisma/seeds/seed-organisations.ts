import { PrismaClient } from '@prisma/client';
import { faker } from '@faker-js/faker';

const prisma = new PrismaClient();

async function main() {
  console.log('Seeding organisations...');
  
  const organisationPromises = Array.from({ length: 50 }).map(() => {
    return prisma.organisation.create({
      data: {
        sdlNumber: `L${faker.string.numeric(9)}`,
        organisationName: faker.company.name(),
        tradingName: faker.company.catchPhrase(),
        companyRegistrationNumber: `20${faker.string.numeric(2)}/${faker.string.numeric(6)}/${faker.string.numeric(2)}`,
        email: faker.internet.email(),
        telNumber: faker.phone.number({ style: 'national' }),
        address: faker.location.streetAddress() + ', ' + faker.location.city() + ', ' + faker.location.zipCode(),
        bankAccountNumber: faker.finance.accountNumber(10),
        bankBranchCode: faker.finance.routingNumber(),
        sicCode: faker.string.numeric(5),
      },
    });
  });

  await Promise.all(organisationPromises);
  
  console.log('Successfully seeded 50 organisations 🌱');
}

main()
  .catch((e) => {
    console.error(e);
    process.exit(1);
  })
  .finally(async () => {
    await prisma.$disconnect();
  });
