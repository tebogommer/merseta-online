import { PrismaClient } from '@prisma/client'

const prisma = new PrismaClient()

async function main() {
  console.log('🌱 Seeding normalized lookup tables (Batch 0 & 1)...')

  // BATCH 0
  const genderData = [
    { code: 'M', name: 'Male' },
    { code: 'F', name: 'Female' },
    { code: 'O', name: 'Other' },
    { code: 'U', name: 'Unknown' },
  ]
  for (const item of genderData) {
    await prisma.genderType.upsert({ where: { code: item.code }, update: {}, create: { ...item, active: true } })
  }

  const nationalityData = [
    { code: 'RSA', name: 'South African' },
    { code: 'NON_RSA', name: 'Non-South African' },
  ]
  for (const item of nationalityData) {
    await prisma.nationalityType.upsert({ where: { code: item.code }, update: {}, create: { ...item, active: true } })
  }

  const equityData = [
    { code: 'AFR', name: 'African' },
    { code: 'COL', name: 'Coloured' },
    { code: 'IND', name: 'Indian' },
    { code: 'WHI', name: 'White' },
  ]
  for (const item of equityData) {
    await prisma.equityType.upsert({ where: { code: item.code }, update: {}, create: { ...item, active: true } })
  }

  const provinceData = [
    { code: 'GP', name: 'Gauteng' },
    { code: 'WC', name: 'Western Cape' },
    { code: 'KZN', name: 'KwaZulu-Natal' },
    { code: 'EC', name: 'Eastern Cape' },
    { code: 'NW', name: 'North West' },
  ]
  for (const item of provinceData) {
    await prisma.provinceType.upsert({ where: { code: item.code }, update: {}, create: { ...item, active: true } })
  }

  // BATCH 1 - Identity & Foundation
  const titleData = [
    { code: 'MR', name: 'Mr' },
    { code: 'MRS', name: 'Mrs' },
    { code: 'MS', name: 'Ms' },
    { code: 'DR', name: 'Dr' },
    { code: 'PROF', name: 'Prof' },
  ]
  for (const item of titleData) {
    await prisma.titleType.upsert({ where: { code: item.code }, update: {}, create: { ...item } })
  }

  const maritalStatusData = [
    { code: 'SIN', name: 'Single' },
    { code: 'MAR', name: 'Married' },
    { code: 'DIV', name: 'Divorced' },
    { code: 'WID', name: 'Widowed' },
  ]
  for (const item of maritalStatusData) {
    await prisma.maritalStatusType.upsert({ where: { code: item.code }, update: {}, create: { ...item } })
  }

  const languageData = [
    { code: 'ENG', name: 'English' },
    { code: 'AFR', name: 'Afrikaans' },
    { code: 'ZUL', name: 'isiZulu' },
    { code: 'XHO', name: 'isiXhosa' },
  ]
  for (const item of languageData) {
    await prisma.languageType.upsert({ where: { code: item.code }, update: {}, create: { ...item } })
  }

  // BATCH 1 - Socio-Economic
  const disabilityStatusData = [
    { code: 'NONE', name: 'None' },
    { code: 'VISUAL', name: 'Visual Impairment' },
    { code: 'PHYSICAL', name: 'Physical Disability' },
  ]
  for (const item of disabilityStatusData) {
    await prisma.disabilityStatusType.upsert({ where: { code: item.code }, update: {}, create: { ...item } })
  }

  const townData = [
    { code: 'JHB', name: 'Johannesburg' },
    { code: 'PTA', name: 'Pretoria' },
    { code: 'CPT', name: 'Cape Town' },
    { code: 'DBN', name: 'Durban' },
  ]
  for (const item of townData) {
    await prisma.townType.upsert({ where: { code: item.code }, update: {}, create: { ...item } })
  }

  const setaData = [
    { code: '17', name: 'MerSETA' },
    { code: '01', name: 'AgriSETA' },
    { code: '03', name: 'BankSETA' },
  ]
  for (const item of setaData) {
    await prisma.setaType.upsert({ where: { code: item.code }, update: {}, create: { ...item } })
  }

  // BATCH 2 - ETQA & Training Core
  const accreditationData = [
    { code: 'ACC', name: 'Accredited' },
    { code: 'PROV', name: 'Provisional' },
    { code: 'NONE', name: 'Not Accredited' },
  ]
  for (const item of accreditationData) {
    await prisma.accreditationType.upsert({ where: { code: item.code }, update: {}, create: { ...item } })
  }

  const nqfLevelData = [
    { code: '1', name: 'Level 1' },
    { code: '2', name: 'Level 2' },
    { code: '3', name: 'Level 3' },
    { code: '4', name: 'Level 4' },
    { code: '5', name: 'Level 5' },
    { code: '6', name: 'Level 6' },
  ]
  for (const item of nqfLevelData) {
    await prisma.nqfLevelsType.upsert({ where: { code: item.code }, update: {}, create: { ...item } })
  }

  const learnerAchievementData = [
    { code: 'COMP', name: 'Competent' },
    { code: 'NYC', name: 'Not Yet Competent' },
  ]
  for (const item of learnerAchievementData) {
    await prisma.learnerAchievementStatusType.upsert({ where: { code: item.code }, update: {}, create: { ...item } })
  }

  const etqaData = [
    { code: '17', name: 'MerSETA' },
    { code: '597', name: 'QCTO' },
  ]
  for (const item of etqaData) {
    await prisma.etqaType.upsert({ where: { code: item.code }, update: {}, create: { ...item } })
  }

  // BATCH 3 - Grants & Workforce
  const financialYearData = [
    { code: '2024', name: '2024/2025' },
    { code: '2025', name: '2025/2026' },
  ]
  for (const item of financialYearData) {
    await prisma.financialYearsType.upsert({ where: { code: item.code }, update: {}, create: { ...item } })
  }

  const ofoData = [
    { code: '2021-111101', name: 'Legislator' },
    { code: '2021-111201', name: 'Senior Government Official' },
  ]
  for (const item of ofoData) {
    await prisma.ofoType.upsert({ where: { code: item.code }, update: {}, create: { ...item } })
  }

  const occupationCategoryData = [
    { code: 'MGR', name: 'Managers' },
    { code: 'PROF', name: 'Professionals' },
    { code: 'TECH', name: 'Technicians and Associate Professionals' },
  ]
  for (const item of occupationCategoryData) {
    await prisma.occupationCategoryType.upsert({ where: { code: item.code }, update: {}, create: { ...item } })
  }

  const fundingData = [
    { code: 'DG', name: 'Discretionary Grant' },
    { code: 'MG', name: 'Mandatory Grant' },
    { code: 'SF', name: 'Self Funded' },
  ]
  for (const item of fundingData) {
    await prisma.fundingType.upsert({ where: { code: item.code }, update: {}, create: { ...item } })
  }

  // BATCH 4 - WSP, ATR & Approvals
  const approvalStatusData = [
    { code: 'APP', name: 'Approved' },
    { code: 'REJ', name: 'Rejected' },
    { code: 'PEND', name: 'Pending' },
  ]
  for (const item of approvalStatusData) {
    await prisma.approvalStatusType.upsert({ where: { code: item.code }, update: {}, create: { ...item } })
  }

  const meetingTypeData = [
    { code: 'BOARD', name: 'Board Meeting' },
    { code: 'COMM', name: 'Committee Meeting' },
    { code: 'WORK', name: 'Workshop' },
  ]
  for (const item of meetingTypeData) {
    await prisma.meetingTypeType.upsert({ where: { code: item.code }, update: {}, create: { ...item } })
  }

  const statusData = [
    { code: 'ACT', name: 'Active' },
    { code: 'INACT', name: 'Inactive' },
    { code: 'DRAFT', name: 'Draft' },
  ]
  for (const item of statusData) {
    await prisma.statusType.upsert({ where: { code: item.code }, update: {}, create: { ...item } })
  }

  // BATCH 5 - Academic & Sensory Metadata
  const sensoryRatingData = [
    { code: 'NONE', name: 'No difficulty' },
    { code: 'SOME', name: 'Some difficulty' },
    { code: 'A_LOT', name: 'A lot of difficulty' },
    { code: 'UNABLE', name: 'Unable to do' },
  ]
  for (const item of sensoryRatingData) {
    await prisma.hearingRatingType.upsert({ where: { code: item.code }, update: {}, create: { ...item } })
    await prisma.seeingRatingType.upsert({ where: { code: item.code }, update: {}, create: { ...item } })
    await prisma.walkingRatingType.upsert({ where: { code: item.code }, update: {}, create: { ...item } })
  }

  const visitPurposeData = [
    { code: 'ACCRED', name: 'Accreditation Visit' },
    { code: 'MONITOR', name: 'Monitoring Visit' },
    { code: 'WPA', name: 'Workplace Approval' },
  ]
  for (const item of visitPurposeData) {
    await prisma.purposeOfSiteVisitType.upsert({ where: { code: item.code }, update: {}, create: { ...item } })
  }

  // BATCH 6 - Statistical & System Metadata
  const enrolmentStatusData = [
    { code: 'ACTIVE', name: 'Active' },
    { code: 'PENDING', name: 'Pending Approval' },
    { code: 'COMPLETED', name: 'Completed' },
    { code: 'WITHDRAWN', name: 'Withdrawn' },
  ]
  for (const item of enrolmentStatusData) {
    await prisma.enrolmentStatusType.upsert({ where: { code: item.code }, update: {}, create: { ...item } })
  }

  const statssaData = [
    { code: 'GP', name: 'Gauteng' },
    { code: 'WC', name: 'Western Cape' },
    { code: 'KZN', name: 'KwaZulu-Natal' },
  ]
  for (const item of statssaData) {
    await prisma.sTATSSAAreaCodeType.upsert({ where: { code: item.code }, update: {}, create: { ...item } })
  }

  console.log('✅ Batch 0, 1, 2, 3, 4, 5 & 6 lookup sync complete.')
}

main()
  .catch((e) => {
    console.error(e)
    process.exit(1)
  })
  .finally(async () => {
    await prisma.$disconnect()
  })
