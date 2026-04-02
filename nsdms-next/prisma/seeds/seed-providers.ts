import { PrismaClient } from '@prisma/client'

const prisma = new PrismaClient()

async function main() {
  console.log('🌱 Seeding providers with RAG statuses...')

  // 1. Ensure Organizations exist for them
  const org1 = await prisma.organisation.upsert({
    where: { sdlNumber: 'L123456789' },
    update: {},
    create: {
      sdlNumber: 'L123456789',
      organisationName: 'Cape Town Technical College',
      email: 'info@ct-tvet.edu.za',
    }
  });

  const org2 = await prisma.organisation.upsert({
    where: { sdlNumber: 'L987654321' },
    update: {},
    create: {
      sdlNumber: 'L987654321',
      organisationName: 'Johannesburg Industry Trainers',
      email: 'admin@jit.co.za',
    }
  });

  const org3 = await prisma.organisation.upsert({
    where: { sdlNumber: 'L555666777' },
    update: {},
    create: {
      sdlNumber: 'L555666777',
      organisationName: 'Expired Training Solutions',
      email: 'expired@solutions.co.za',
    }
  });

  // 2. Clear then seed providers
  await prisma.trainingProvider.deleteMany({});

  const providers = [
    {
      organisationId: org1.id,
      accreditationNumber: 'MER-44231',
      status: 'ACTIVE',
    },
    {
      organisationId: org2.id,
      accreditationNumber: 'MER-99012',
      status: 'PROVISIONAL',
    },
    {
      organisationId: org3.id,
      accreditationNumber: 'MER-00123',
      status: 'SUSPENDED',
    }
  ]

  for (const p of providers) {
    await prisma.trainingProvider.create({ data: p })
  }

  console.log('✅ Provider seeding complete.')
}

main().finally(() => prisma.$disconnect())
