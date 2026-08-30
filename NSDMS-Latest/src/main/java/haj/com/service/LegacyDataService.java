package haj.com.service;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import haj.com.constants.HAJConstants;
import haj.com.entity.lookup.HomeAffairs;
import haj.com.entity.lookup.LegacyApprenticeship;
import haj.com.entity.lookup.LegacyApprenticeshipTradeTest;
import haj.com.entity.lookup.LegacyAssessorAccreditation;
import haj.com.entity.lookup.LegacyAssessorLearnership;
import haj.com.entity.lookup.LegacyAssessorQualification;
import haj.com.entity.lookup.LegacyAssessorSkillsProgramme;
import haj.com.entity.lookup.LegacyAssessorUnitStandard;
import haj.com.entity.lookup.LegacyBursary;
import haj.com.entity.lookup.LegacyDTTCApproval;
import haj.com.entity.lookup.LegacyDTTCQualification;
import haj.com.entity.lookup.LegacyDTTCSkillsProgramme;
import haj.com.entity.lookup.LegacyDTTCTrade;
import haj.com.entity.lookup.LegacyDTTCUnitStandard;
import haj.com.entity.lookup.LegacyEmployerWA2Learnership;
import haj.com.entity.lookup.LegacyEmployerWA2Qualification;
import haj.com.entity.lookup.LegacyEmployerWA2SkillsProgramme;
import haj.com.entity.lookup.LegacyEmployerWA2Trade;
import haj.com.entity.lookup.LegacyEmployerWA2UnitStandard;
import haj.com.entity.lookup.LegacyEmployerWA2Workplace;
import haj.com.entity.lookup.LegacyExperiential;
import haj.com.entity.lookup.LegacyInternship;
import haj.com.entity.lookup.LegacyLearnership;
import haj.com.entity.lookup.LegacyLearnershipAssessment;
import haj.com.entity.lookup.LegacyLearnershipAssessment2;
import haj.com.entity.lookup.LegacyLearnershipAssessment3;
import haj.com.entity.lookup.LegacyModeratorAccreditation;
import haj.com.entity.lookup.LegacyModeratorLearnership;
import haj.com.entity.lookup.LegacyModeratorQualification;
import haj.com.entity.lookup.LegacyModeratorSkillsProgramme;
import haj.com.entity.lookup.LegacyModeratorUnitStandard;
import haj.com.entity.lookup.LegacyOrganisationLevyPaying;
import haj.com.entity.lookup.LegacyOrganisationNonLevyPaying;
import haj.com.entity.lookup.LegacyOrganisationSites;
import haj.com.entity.lookup.LegacyPerson;
import haj.com.entity.lookup.LegacyProviderAccreditation;
import haj.com.entity.lookup.LegacyProviderLearnership;
import haj.com.entity.lookup.LegacyProviderQualification;
import haj.com.entity.lookup.LegacyProviderSkillsProgramme;
import haj.com.entity.lookup.LegacyProviderTrade;
import haj.com.entity.lookup.LegacyProviderUnitStandard;
import haj.com.entity.lookup.LegacySECTTwentyEight;
import haj.com.entity.lookup.LegacySectionTwentyEightTradeTest;
import haj.com.entity.lookup.LegacySkillsProgramme;
import haj.com.entity.lookup.LegacySkillsProgrammeAssessments;
import haj.com.entity.lookup.LegacyTvet;
import haj.com.entity.lookup.LegacyUnitStandard;
import haj.com.entity.lookup.LegacyUnitStandardAssessment;
import haj.com.framework.AbstractService;
import haj.com.service.lookup.HomeAffairsService;
import haj.com.service.lookup.LegacyApprenticeshipService;
import haj.com.service.lookup.LegacyApprenticeshipTradeTestService;
import haj.com.service.lookup.LegacyAssessorAccreditationService;
import haj.com.service.lookup.LegacyAssessorLearnershipService;
import haj.com.service.lookup.LegacyAssessorQualificationService;
import haj.com.service.lookup.LegacyAssessorSkillsProgrammeService;
import haj.com.service.lookup.LegacyAssessorUnitStandardService;
import haj.com.service.lookup.LegacyBursaryService;
import haj.com.service.lookup.LegacyDTTCApprovalService;
import haj.com.service.lookup.LegacyDTTCQualificationService;
import haj.com.service.lookup.LegacyDTTCSkillsProgrammeService;
import haj.com.service.lookup.LegacyDTTCTradeService;
import haj.com.service.lookup.LegacyDTTCUnitStandardService;
import haj.com.service.lookup.LegacyEmployerWA2LearnershipService;
import haj.com.service.lookup.LegacyEmployerWA2QualificationService;
import haj.com.service.lookup.LegacyEmployerWA2SkillsProgrammeService;
import haj.com.service.lookup.LegacyEmployerWA2TradeService;
import haj.com.service.lookup.LegacyEmployerWA2UnitStandardService;
import haj.com.service.lookup.LegacyEmployerWA2WorkplaceService;
import haj.com.service.lookup.LegacyExperientialService;
import haj.com.service.lookup.LegacyInternshipService;
import haj.com.service.lookup.LegacyLearnershipAssessment2Service;
import haj.com.service.lookup.LegacyLearnershipAssessment3Service;
import haj.com.service.lookup.LegacyLearnershipAssessmentService;
import haj.com.service.lookup.LegacyLearnershipService;
import haj.com.service.lookup.LegacyModeratorAccreditationService;
import haj.com.service.lookup.LegacyModeratorLearnershipService;
import haj.com.service.lookup.LegacyModeratorQualificationService;
import haj.com.service.lookup.LegacyModeratorSkillsProgrammeService;
import haj.com.service.lookup.LegacyModeratorUnitStandardService;
import haj.com.service.lookup.LegacyOrganisationLevyPayingService;
import haj.com.service.lookup.LegacyOrganisationNonLevyPayingService;
import haj.com.service.lookup.LegacyOrganisationSitesService;
import haj.com.service.lookup.LegacyPersonService;
import haj.com.service.lookup.LegacyProviderAccreditationService;
import haj.com.service.lookup.LegacyProviderLearnershipService;
import haj.com.service.lookup.LegacyProviderQualificationService;
import haj.com.service.lookup.LegacyProviderSkillsProgrammeService;
import haj.com.service.lookup.LegacyProviderTradeService;
import haj.com.service.lookup.LegacyProviderUnitStandardService;
import haj.com.service.lookup.LegacySECTTwentyEightService;
import haj.com.service.lookup.LegacySectionTwentyEightTradeTestService;
import haj.com.service.lookup.LegacySkillsProgrammeAssessmentsService;
import haj.com.service.lookup.LegacySkillsProgrammeService;
import haj.com.service.lookup.LegacyTvetService;
import haj.com.service.lookup.LegacyUnitStandardAssessmentService;
import haj.com.service.lookup.LegacyUnitStandardService;
import haj.com.utils.CSVUtil;
import haj.com.utils.GenericUtility;

public class LegacyDataService extends AbstractService {

	private LegacyApprenticeshipService legacyApprenticeshipService = new LegacyApprenticeshipService();
	private LegacyApprenticeshipTradeTestService legacyApprenticeshipTradeTestService = new LegacyApprenticeshipTradeTestService();
	private LegacyAssessorAccreditationService legacyAssessorAccreditationService = new LegacyAssessorAccreditationService();
	private LegacyAssessorLearnershipService legacyAssessorLearnershipService = new LegacyAssessorLearnershipService();
	private LegacyAssessorQualificationService legacyAssessorQualificationService = new LegacyAssessorQualificationService();
	private LegacyAssessorSkillsProgrammeService legacyAssessorSkillsProgrammeService = new LegacyAssessorSkillsProgrammeService();
	private LegacyAssessorUnitStandardService legacyAssessorUnitStandardService = new LegacyAssessorUnitStandardService();
	private LegacyBursaryService legacyBursaryService = new LegacyBursaryService();
	private LegacyDTTCApprovalService legacyDTTCApprovalService = new LegacyDTTCApprovalService();
	private LegacyDTTCQualificationService legacyDTTCQualificationService = new LegacyDTTCQualificationService();
	private LegacyDTTCSkillsProgrammeService legacyDTTCSkillsProgrammeService = new LegacyDTTCSkillsProgrammeService();
	private LegacyDTTCTradeService legacyDTTCTradeService = new LegacyDTTCTradeService();
	private LegacyDTTCUnitStandardService legacyDTTCUnitStandardService = new LegacyDTTCUnitStandardService();
	private LegacyEmployerWA2LearnershipService legacyEmployerWA2LearnershipService = new LegacyEmployerWA2LearnershipService();
	private LegacyEmployerWA2QualificationService legacyEmployerWA2QualificationService = new LegacyEmployerWA2QualificationService();
	private LegacyEmployerWA2SkillsProgrammeService legacyEmployerWA2SkillsProgrammeService = new LegacyEmployerWA2SkillsProgrammeService();
	private LegacyEmployerWA2TradeService legacyEmployerWA2TradeService = new LegacyEmployerWA2TradeService();
	private LegacyEmployerWA2UnitStandardService legacyEmployerWA2UnitStandardService = new LegacyEmployerWA2UnitStandardService();
	private LegacyEmployerWA2WorkplaceService legacyEmployerWA2WorkplaceService = new LegacyEmployerWA2WorkplaceService();
	private LegacyExperientialService legacyExperientialService = new LegacyExperientialService();
	private LegacyInternshipService legacyInternshipService = new LegacyInternshipService();
	private LegacyLearnershipService legacyLearnershipService = new LegacyLearnershipService();
	private LegacyLearnershipAssessmentService legacyLearnershipAssessmentService = new LegacyLearnershipAssessmentService();
	private LegacyLearnershipAssessment2Service legacyLearnershipAssessment2Service = new LegacyLearnershipAssessment2Service();
	private LegacyLearnershipAssessment3Service legacyLearnershipAssessment3Service = new LegacyLearnershipAssessment3Service();
	private LegacyModeratorAccreditationService legacyModeratorAccreditationService = new LegacyModeratorAccreditationService();
	private LegacyModeratorLearnershipService legacyModeratorLearnershipService = new LegacyModeratorLearnershipService();
	private LegacyModeratorQualificationService legacyModeratorQualificationService = new LegacyModeratorQualificationService();
	private LegacyModeratorSkillsProgrammeService legacyModeratorSkillsProgrammeService = new LegacyModeratorSkillsProgrammeService();
	private LegacyModeratorUnitStandardService legacyModeratorUnitStandardService = new LegacyModeratorUnitStandardService();
	private LegacyOrganisationLevyPayingService legacyOrganisationLevyPayingService = new LegacyOrganisationLevyPayingService();
	private LegacyOrganisationNonLevyPayingService legacyOrganisationNonLevyPayingService = new LegacyOrganisationNonLevyPayingService();
	private LegacyOrganisationSitesService legacyOrganisationSitesService = new LegacyOrganisationSitesService();
	private LegacyPersonService legacyPersonService = new LegacyPersonService();
	private LegacyProviderAccreditationService legacyProviderAccreditationService = new LegacyProviderAccreditationService();
	private LegacyProviderLearnershipService legacyProviderLearnershipService = new LegacyProviderLearnershipService();
	private LegacyProviderQualificationService legacyProviderQualificationService = new LegacyProviderQualificationService();
	private LegacyProviderSkillsProgrammeService legacyProviderSkillsProgrammeService = new LegacyProviderSkillsProgrammeService();
	private LegacyProviderTradeService legacyProviderTradeService = new LegacyProviderTradeService();
	private LegacyProviderUnitStandardService legacyProviderUnitStandardService = new LegacyProviderUnitStandardService();
	private LegacySectionTwentyEightTradeTestService legacySectionTwentyEightTradeTestService = new LegacySectionTwentyEightTradeTestService();
	private LegacySECTTwentyEightService legacySECTTwentyEightService = new LegacySECTTwentyEightService();
	private LegacySkillsProgrammeService legacySkillsProgrammeService = new LegacySkillsProgrammeService();
	private LegacySkillsProgrammeAssessmentsService legacySkillsProgrammeAssessmentsService = new LegacySkillsProgrammeAssessmentsService();
	private LegacyTvetService legacyTvetService = new LegacyTvetService();
	private LegacyUnitStandardService legacyUnitStandardService = new LegacyUnitStandardService();
	private LegacyUnitStandardAssessmentService legacyUnitStandardAssessmentService = new LegacyUnitStandardAssessmentService();
	private HomeAffairsService homeAffairsService = new HomeAffairsService();

	public final static String EXPORT_FOLDER = findExportFolder();

	public void downlaodAllData() {
		try {
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data Extract Process", "Legacy Data Extract Process Has Started on: " + HAJConstants.PL_LINK, null);
			}
//			processLegacyApprenticeshipData();
//			processLegacyApprenticeshipTradeTestData();
//			processLegacyAssessorAccreditationData();
//			processLegacyAssessorLearnershipData();
//			processLegacyAssessorQualificationData();
//			processLegacyAssessorSkillsProgrammeData();
//			processLegacyAssessorUnitStandardData();
//			processLegacyBursaryData();
//			processLegacyDTTCApprovalData();
//			processLegacyDTTCQualificationData();
//			processLegacyDTTCSkillsProgrammeData();
//			processLegacyDTTCTradeData();
//			processLegacyDTTCUnitStandardData();
//			processLegacyEmployerWA2LearnershipData();
//			processLegacyEmployerWA2QualificationData();
//			processLegacyEmployerWA2SkillsProgrammeData();
//			processLegacyEmployerWA2TradeData();
//			processLegacyEmployerWA2UnitStandardData();
//			processLegacyEmployerWA2WorkplaceData();
//			processLegacyExperientialData();
//			processLegacyInternshipData();
//			processLegacyLearnershipData();
//			processLegacyLearnershipAssessmentData(); // manual required
//			processLegacyLearnershipAssessment2Data();
//			processLegacyLearnershipAssessment3Data();
//			processLegacyModeratorAccreditationData();
//			processLegacyModeratorLearnershipData();
//			processLegacyModeratorQualificationData();
//			processLegacyModeratorSkillsProgrammeData();
//			processLegacyModeratorUnitStandardData();
//			processLegacyOrganisationLevyPayingData();
//			processLegacyOrganisationNonLevyPayingData();
//			processLegacyOrganisationSitesData();
//			processLegacyPersonData();
//			processLegacyProviderAccreditationData();
//			processLegacyProviderLearnershipData();
//			processLegacyProviderQualificationData();
//			processLegacyProviderSkillsProgrammeData();
//			processLegacyProviderTradeData();
//			processLegacyProviderUnitStandardData();
//			processLegacySectionTwentyEightTradeTestData();
//			processLegacySECTTwentyEightData();
//			processLegacySkillsProgrammeData();
//			processLegacySkillsProgrammeAssessmentsData();
//			processLegacyTvetData();
//			processLegacyUnitStandardData(); // manual required
//			processLegacyUnitStandardAssessmentData(); // manual required
			processHomeAffairsData();
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data Extract Process", "Legacy Data Extract Process Has Finished With No Errors on: " + HAJConstants.PL_LINK, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data Extract Process Error", "Legacy Data Extract Process Has Finished With an Errors on: " + HAJConstants.PL_LINK + ". Error Message: " + e.getMessage(), null);
			}
		}
	}

	private static String findExportFolder() {
		String folder = findProperty("EXPORT_FOLDER");
		if (folder == null) folder = "/home/merseta/data_extracts/";
		return folder;
	}

	private static String findProperty(String prop) {
		if (System.getProperties().get("DD-PROPERTIES") != null && ((java.util.Properties) System.getProperties().get("DD-PROPERTIES")).getProperty(prop) != null) {
			return ((java.util.Properties) System.getProperties().get("DD-PROPERTIES")).getProperty(prop);
		}
		return null;
	}

	public void processLegacyApprenticeshipData() throws Exception {
		System.out.println("Starting export legacyapprenticeship_06_09_2019.csv");
		List<LegacyApprenticeship> legacyapprenticeship = legacyApprenticeshipService.allLegacyApprenticeship();
		String csv = CSVUtil.writeCSV(legacyapprenticeship, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacyapprenticeship_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacyapprenticeship = null;
		writer.close();
		System.out.println("Done export legacyapprenticeship_06_09_2019.csv");
	}

	public void processLegacyApprenticeshipTradeTestData() throws Exception {
		System.out.println("Starting export legacyapprenticeshiptradetest_06_09_2019.csv");
		List<LegacyApprenticeshipTradeTest> legacyapprenticeshiptradetest = legacyApprenticeshipTradeTestService.allLegacyApprenticeshipTradeTest();
		String csv = CSVUtil.writeCSV(legacyapprenticeshiptradetest, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacyapprenticeshiptradetest_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacyapprenticeshiptradetest = null;
		writer.close();
		System.out.println("Done export legacyapprenticeshiptradetest_06_09_2019.csv");
	}

	public void processLegacyAssessorAccreditationData() throws Exception {
		System.out.println("Starting export legacyassessoraccreditation_06_09_2019.csv");
		List<LegacyAssessorAccreditation> legacyassessoraccreditation = legacyAssessorAccreditationService.allLegacyAssessorAccreditation();
		String csv = CSVUtil.writeCSV(legacyassessoraccreditation, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacyassessoraccreditation_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacyassessoraccreditation = null;
		writer.close();
		System.out.println("Done export legacyassessoraccreditation_06_09_2019.csv");
	}

	public void processLegacyAssessorLearnershipData() throws Exception {
		System.out.println("Starting export legacyassessorlearnership_06_09_2019.csv");
		List<LegacyAssessorLearnership> legacyassessorlearnership = legacyAssessorLearnershipService.allLegacyAssessorLearnership();
		String csv = CSVUtil.writeCSV(legacyassessorlearnership, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacyassessorlearnership_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacyassessorlearnership = null;
		writer.close();
		System.out.println("Done export legacyassessorlearnership_06_09_2019.csv");
	}

	public void processLegacyAssessorQualificationData() throws Exception {
		System.out.println("Starting export legacyassessorqualification_06_09_2019.csv");
		List<LegacyAssessorQualification> legacyassessorqualification = legacyAssessorQualificationService.allLegacyAssessorQualification();
		String csv = CSVUtil.writeCSV(legacyassessorqualification, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacyassessorqualification_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacyassessorqualification = null;
		writer.close();
		System.out.println("Done export legacyassessorqualification_06_09_2019.csv");
	}

	public void processLegacyAssessorSkillsProgrammeData() throws Exception {
		System.out.println("Starting export legacyassessorskillsprogramme_06_09_2019.csv");
		List<LegacyAssessorSkillsProgramme> legacyassessorskillsprogramme = legacyAssessorSkillsProgrammeService.allLegacyAssessorSkillsProgramme();
		String csv = CSVUtil.writeCSV(legacyassessorskillsprogramme, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacyassessorskillsprogramme_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacyassessorskillsprogramme = null;
		writer.close();
		System.out.println("Done export legacyassessorskillsprogramme_06_09_2019.csv");
	}

	public void processLegacyAssessorUnitStandardData() throws Exception {
		System.out.println("Starting export legacyassessorunitstandard_06_09_2019.csv");
		List<LegacyAssessorUnitStandard> legacyassessorunitstandard = legacyAssessorUnitStandardService.allLegacyAssessorUnitStandard();
		String csv = CSVUtil.writeCSV(legacyassessorunitstandard, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacyassessorunitstandard_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacyassessorunitstandard = null;
		writer.close();
		System.out.println("Done export legacyassessorunitstandard_06_09_2019.csv");
	}

	public void processLegacyBursaryData() throws Exception {
		System.out.println("Starting export legacybursary_06_09_2019.csv");
		List<LegacyBursary> legacybursary = legacyBursaryService.allLegacyBursary();
		String csv = CSVUtil.writeCSV(legacybursary, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacybursary_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacybursary = null;
		writer.close();
		System.out.println("Done export legacybursary_06_09_2019.csv");
	}

	public void processLegacyDTTCApprovalData() throws Exception {
		System.out.println("Starting export legacydttcapproval_06_09_2019.csv");
		List<LegacyDTTCApproval> legacydttcapproval = legacyDTTCApprovalService.allLegacyDTTCApproval();
		String csv = CSVUtil.writeCSV(legacydttcapproval, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacydttcapproval_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacydttcapproval = null;
		writer.close();
		System.out.println("Done export legacydttcapproval_06_09_2019.csv");
	}

	public void processLegacyDTTCQualificationData() throws Exception {
		System.out.println("Starting export legacydttcqualification_06_09_2019.csv");
		List<LegacyDTTCQualification> legacydttcqualification = legacyDTTCQualificationService.allLegacyDTTCQualification();
		String csv = CSVUtil.writeCSV(legacydttcqualification, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacydttcqualification_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacydttcqualification = null;
		writer.close();
		System.out.println("Done export legacydttcqualification_06_09_2019.csv");
	}

	public void processLegacyDTTCSkillsProgrammeData() throws Exception {
		System.out.println("Starting export legacydttcskillsprogramme_06_09_2019.csv");
		List<LegacyDTTCSkillsProgramme> legacydttcskillsprogramme = legacyDTTCSkillsProgrammeService.allLegacyDTTCSkillsProgramme();
		String csv = CSVUtil.writeCSV(legacydttcskillsprogramme, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacydttcskillsprogramme_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacydttcskillsprogramme = null;
		writer.close();
		System.out.println("Done export legacydttcskillsprogramme_06_09_2019.csv");
	}

	public void processLegacyDTTCTradeData() throws Exception {
		System.out.println("Starting export legacydttctrade_06_09_2019.csv");
		List<LegacyDTTCTrade> legacydttctrade = legacyDTTCTradeService.allLegacyDTTCTrade();
		String csv = CSVUtil.writeCSV(legacydttctrade, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacydttctrade_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacydttctrade = null;
		writer.close();
		System.out.println("Done export legacydttctrade_06_09_2019.csv");
	}

	public void processLegacyDTTCUnitStandardData() throws Exception {
		System.out.println("Starting export legacydttcunitstandard_06_09_2019.csv");
		List<LegacyDTTCUnitStandard> legacydttcunitstandard = legacyDTTCUnitStandardService.allLegacyDTTCUnitStandard();
		String csv = CSVUtil.writeCSV(legacydttcunitstandard, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacydttcunitstandard_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacydttcunitstandard = null;
		writer.close();
		System.out.println("Done export legacydttcunitstandard_06_09_2019.csv");
	}

	public void processLegacyEmployerWA2LearnershipData() throws Exception {
		System.out.println("Starting export legacyemployerwa2learnership_06_09_2019.csv");
		List<LegacyEmployerWA2Learnership> legacyemployerwa2learnership = legacyEmployerWA2LearnershipService.allLegacyEmployerWA2Learnership();
		String csv = CSVUtil.writeCSV(legacyemployerwa2learnership, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacyemployerwa2learnership_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacyemployerwa2learnership = null;
		writer.close();
		System.out.println("Done export legacyemployerwa2learnership_06_09_2019.csv");
	}

	public void processLegacyEmployerWA2QualificationData() throws Exception {
		System.out.println("Starting export legacyemployerwa2qualification_06_09_2019.csv");
		List<LegacyEmployerWA2Qualification> legacyemployerwa2qualification = legacyEmployerWA2QualificationService.allLegacyEmployerWA2Qualification();
		String csv = CSVUtil.writeCSV(legacyemployerwa2qualification, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacyemployerwa2qualification_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacyemployerwa2qualification = null;
		writer.close();
		System.out.println("Done export legacyemployerwa2qualification_06_09_2019.csv");
	}

	public void processLegacyEmployerWA2SkillsProgrammeData() throws Exception {
		System.out.println("Starting export legacyemployerwa2skillsprogramme_06_09_2019.csv");
		List<LegacyEmployerWA2SkillsProgramme> legacyemployerwa2skillsprogramme = legacyEmployerWA2SkillsProgrammeService.allLegacyEmployerWA2SkillsProgramme();
		String csv = CSVUtil.writeCSV(legacyemployerwa2skillsprogramme, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacyemployerwa2skillsprogramme_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacyemployerwa2skillsprogramme = null;
		writer.close();
		System.out.println("Done export legacyemployerwa2skillsprogramme_06_09_2019.csv");
	}

	public void processLegacyEmployerWA2TradeData() throws Exception {
		System.out.println("Starting export legacyemployerwa2trade_06_09_2019.csv");
		List<LegacyEmployerWA2Trade> legacyemployerwa2trade = legacyEmployerWA2TradeService.allLegacyEmployerWA2Trade();
		String csv = CSVUtil.writeCSV(legacyemployerwa2trade, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacyemployerwa2trade_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacyemployerwa2trade = null;
		writer.close();
		System.out.println("Done export legacyemployerwa2trade_06_09_2019.csv");
	}

	public void processLegacyEmployerWA2UnitStandardData() throws Exception {
		System.out.println("Starting export legacyemployerwa2unitstandard_06_09_2019.csv");
		List<LegacyEmployerWA2UnitStandard> legacyemployerwa2unitstandard = legacyEmployerWA2UnitStandardService.allLegacyEmployerWA2UnitStandard();
		String csv = CSVUtil.writeCSV(legacyemployerwa2unitstandard, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacyemployerwa2unitstandard_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacyemployerwa2unitstandard = null;
		writer.close();
		System.out.println("Done export legacyemployerwa2unitstandard_06_09_2019.csv");
	}

	public void processLegacyEmployerWA2WorkplaceData() throws Exception {
		System.out.println("Starting export legacyemployerwa2workplace_06_09_2019.csv");
		List<LegacyEmployerWA2Workplace> legacyemployerwa2workplace = legacyEmployerWA2WorkplaceService.allLegacyEmployerWA2Workplace();
		String csv = CSVUtil.writeCSV(legacyemployerwa2workplace, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacyemployerwa2workplace_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacyemployerwa2workplace = null;
		writer.close();
		System.out.println("Done export legacyemployerwa2workplace_06_09_2019.csv");
	}

	public void processLegacyExperientialData() throws Exception {
		System.out.println("Starting export legacyexperiential_06_09_2019.csv");
		List<LegacyExperiential> legacyexperiential = legacyExperientialService.allLegacyExperiential();
		String csv = CSVUtil.writeCSV(legacyexperiential, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacyexperiential_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacyexperiential = null;
		writer.close();
		System.out.println("Done export legacyexperiential_06_09_2019.csv");
	}

	public void processLegacyInternshipData() throws Exception {
		System.out.println("Starting export legacyinternship_06_09_2019.csv");
		List<LegacyInternship> legacyinternship = legacyInternshipService.allLegacyInternship();
		String csv = CSVUtil.writeCSV(legacyinternship, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacyinternship_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacyinternship = null;
		writer.close();
		System.out.println("Done export legacyinternship_06_09_2019.csv");
	}

	public void processLegacyLearnershipData() throws Exception {
		System.out.println("Starting export legacylearnership_06_09_2019.csv");
		List<LegacyLearnership> legacylearnership = legacyLearnershipService.allLegacyLearnership();
		String csv = CSVUtil.writeCSV(legacylearnership, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacylearnership_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacylearnership = null;
		writer.close();
		System.out.println("Done export legacylearnership_06_09_2019.csv");
	}

	public void processLegacyLearnershipAssessmentData() throws Exception {
		System.out.println("Starting export legacylearnershipassessment_06_09_2019.csv");
		List<LegacyLearnershipAssessment> legacylearnershipassessment = legacyLearnershipAssessmentService.allLegacyLearnershipAssessment();
		String csv = CSVUtil.writeCSV(legacylearnershipassessment, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacylearnershipassessment_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacylearnershipassessment = null;
		writer.close();
		System.out.println("Done export legacylearnershipassessment_06_09_2019.csv");
	}

	public void processLegacyLearnershipAssessment2Data() throws Exception {
		System.out.println("Starting export legacylearnershipassessment2_06_09_2019.csv");
		List<LegacyLearnershipAssessment2> legacylearnershipassessment2 = legacyLearnershipAssessment2Service.allLegacyLearnershipAssessment2();
		String csv = CSVUtil.writeCSV(legacylearnershipassessment2, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacylearnershipassessment2_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacylearnershipassessment2 = null;
		writer.close();
		System.out.println("Done export legacylearnershipassessment2_06_09_2019.csv");
	}

	public void processLegacyLearnershipAssessment3Data() throws Exception {
		System.out.println("Starting export legacylearnershipassessment3_06_09_2019.csv");
		List<LegacyLearnershipAssessment3> legacylearnershipassessment3 = legacyLearnershipAssessment3Service.allLegacyLearnershipAssessment3();
		String csv = CSVUtil.writeCSV(legacylearnershipassessment3, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacylearnershipassessment3_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacylearnershipassessment3 = null;
		writer.close();
		System.out.println("Done export legacylearnershipassessment3_06_09_2019.csv");
	}

	public void processLegacyModeratorAccreditationData() throws Exception {
		System.out.println("Starting export legacymoderatoraccreditation_06_09_2019.csv");
		List<LegacyModeratorAccreditation> legacymoderatoraccreditation = legacyModeratorAccreditationService.allLegacyModeratorAccreditation();
		String csv = CSVUtil.writeCSV(legacymoderatoraccreditation, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacymoderatoraccreditation_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacymoderatoraccreditation = null;
		writer.close();
		System.out.println("Done export legacymoderatoraccreditation_06_09_2019.csv");
	}

	public void processLegacyModeratorLearnershipData() throws Exception {
		System.out.println("Starting export legacymoderatorlearnership_06_09_2019.csv");
		List<LegacyModeratorLearnership> legacymoderatorlearnership = legacyModeratorLearnershipService.allLegacyModeratorLearnership();
		String csv = CSVUtil.writeCSV(legacymoderatorlearnership, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacymoderatorlearnership_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacymoderatorlearnership = null;
		writer.close();
		System.out.println("Done export legacymoderatorlearnership_06_09_2019.csv");
	}

	public void processLegacyModeratorQualificationData() throws Exception {
		System.out.println("Starting export legacymoderatorqualification_06_09_2019.csv");
		List<LegacyModeratorQualification> legacymoderatorqualification = legacyModeratorQualificationService.allLegacyModeratorQualification();
		String csv = CSVUtil.writeCSV(legacymoderatorqualification, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacymoderatorqualification_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacymoderatorqualification = null;
		writer.close();
		System.out.println("Done export legacymoderatorqualification_06_09_2019.csv");
	}

	public void processLegacyModeratorSkillsProgrammeData() throws Exception {
		System.out.println("Starting export legacymoderatorskillsprogramme_06_09_2019.csv");
		List<LegacyModeratorSkillsProgramme> legacymoderatorskillsprogramme = legacyModeratorSkillsProgrammeService.allLegacyModeratorSkillsProgramme();
		String csv = CSVUtil.writeCSV(legacymoderatorskillsprogramme, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacymoderatorskillsprogramme_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacymoderatorskillsprogramme = null;
		writer.close();
		System.out.println("Done export legacymoderatorskillsprogramme_06_09_2019.csv");
	}

	public void processLegacyModeratorUnitStandardData() throws Exception {
		System.out.println("Starting export legacymoderatorunitstandard_06_09_2019.csv");
		List<LegacyModeratorUnitStandard> legacymoderatorunitstandard = legacyModeratorUnitStandardService.allLegacyModeratorUnitStandard();
		String csv = CSVUtil.writeCSV(legacymoderatorunitstandard, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacymoderatorunitstandard_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacymoderatorunitstandard = null;
		writer.close();
		System.out.println("Done export legacymoderatorunitstandard_06_09_2019.csv");
	}

	public void processLegacyOrganisationLevyPayingData() throws Exception {
		System.out.println("Starting export legacyorganisationlevypaying_06_09_2019.csv");
		List<LegacyOrganisationLevyPaying> legacyorganisationlevypaying = legacyOrganisationLevyPayingService.allLegacyOrganisationLevyPaying();
		String csv = CSVUtil.writeCSV(legacyorganisationlevypaying, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacyorganisationlevypaying_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacyorganisationlevypaying = null;
		writer.close();
		System.out.println("Done export legacyorganisationlevypaying_06_09_2019.csv");
	}

	public void processLegacyOrganisationNonLevyPayingData() throws Exception {
		System.out.println("Starting export legacyorganisationnonlevypaying_06_09_2019.csv");
		List<LegacyOrganisationNonLevyPaying> legacyorganisationnonlevypaying = legacyOrganisationNonLevyPayingService.allLegacyOrganisationNonLevyPaying();
		String csv = CSVUtil.writeCSV(legacyorganisationnonlevypaying, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacyorganisationnonlevypaying_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacyorganisationnonlevypaying = null;
		writer.close();
		System.out.println("Done export legacyorganisationnonlevypaying_06_09_2019.csv");
	}

	public void processLegacyOrganisationSitesData() throws Exception {
		System.out.println("Starting export legacyorganisationsites_06_09_2019.csv");
		List<LegacyOrganisationSites> legacyorganisationsites = legacyOrganisationSitesService.allLegacyOrganisationSites();
		String csv = CSVUtil.writeCSV(legacyorganisationsites, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacyorganisationsites_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacyorganisationsites = null;
		writer.close();
		System.out.println("Done export legacyorganisationsites_06_09_2019.csv");
	}

	public void processLegacyPersonData() throws Exception {
		System.out.println("Starting export legacyperson_06_09_2019.csv");
		List<LegacyPerson> legacyperson = legacyPersonService.allLegacyPerson();
		String csv = CSVUtil.writeCSV(legacyperson, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacyperson_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacyperson = null;
		writer.close();
		System.out.println("Done export legacyperson_06_09_2019.csv");
	}

	public void processLegacyProviderAccreditationData() throws Exception {
		System.out.println("Starting export legacyprovideraccreditation_06_09_2019.csv");
		List<LegacyProviderAccreditation> legacyprovideraccreditation = legacyProviderAccreditationService.allLegacyProviderAccreditation();
		String csv = CSVUtil.writeCSV(legacyprovideraccreditation, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacyprovideraccreditation_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacyprovideraccreditation = null;
		writer.close();
		System.out.println("Done export legacyprovideraccreditation_06_09_2019.csv");
	}

	public void processLegacyProviderLearnershipData() throws Exception {
		System.out.println("Starting export legacyproviderlearnership_06_09_2019.csv");
		List<LegacyProviderLearnership> legacyproviderlearnership = legacyProviderLearnershipService.allLegacyProviderLearnership();
		String csv = CSVUtil.writeCSV(legacyproviderlearnership, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacyproviderlearnership_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacyproviderlearnership = null;
		writer.close();
		System.out.println("Done export legacyproviderlearnership_06_09_2019.csv");
	}

	public void processLegacyProviderQualificationData() throws Exception {
		System.out.println("Starting export legacyproviderqualification_06_09_2019.csv");
		List<LegacyProviderQualification> legacyproviderqualification = legacyProviderQualificationService.allLegacyProviderQualification();
		String csv = CSVUtil.writeCSV(legacyproviderqualification, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacyproviderqualification_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacyproviderqualification = null;
		writer.close();
		System.out.println("Done export legacyproviderqualification_06_09_2019.csv");
	}

	public void processLegacyProviderSkillsProgrammeData() throws Exception {
		System.out.println("Starting export legacyproviderskillsprogramme_06_09_2019.csv");
		List<LegacyProviderSkillsProgramme> legacyproviderskillsprogramme = legacyProviderSkillsProgrammeService.allLegacyProviderSkillsProgramme();
		String csv = CSVUtil.writeCSV(legacyproviderskillsprogramme, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacyproviderskillsprogramme_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacyproviderskillsprogramme = null;
		writer.close();
		System.out.println("Done export legacyproviderskillsprogramme_06_09_2019.csv");
	}

	public void processLegacyProviderTradeData() throws Exception {
		System.out.println("Starting export legacyprovidertrade_06_09_2019.csv");
		List<LegacyProviderTrade> legacyprovidertrade = legacyProviderTradeService.allLegacyProviderTrade();
		String csv = CSVUtil.writeCSV(legacyprovidertrade, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacyprovidertrade_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacyprovidertrade = null;
		writer.close();
		System.out.println("Done export legacyprovidertrade_06_09_2019.csv");
	}

	public void processLegacyProviderUnitStandardData() throws Exception {
		System.out.println("Starting export legacyproviderunitstandard_06_09_2019.csv");
		List<LegacyProviderUnitStandard> legacyproviderunitstandard = legacyProviderUnitStandardService.allLegacyProviderUnitStandard();
		String csv = CSVUtil.writeCSV(legacyproviderunitstandard, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacyproviderunitstandard_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacyproviderunitstandard = null;
		writer.close();
		System.out.println("Done export legacyproviderunitstandard_06_09_2019.csv");
	}

	public void processLegacySectionTwentyEightTradeTestData() throws Exception {
		System.out.println("Starting export legacysectiontwentyeighttradetest_06_09_2019.csv");
		List<LegacySectionTwentyEightTradeTest> legacysectiontwentyeighttradetest = legacySectionTwentyEightTradeTestService.allLegacySectionTwentyEightTradeTest();
		String csv = CSVUtil.writeCSV(legacysectiontwentyeighttradetest, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacysectiontwentyeighttradetest_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacysectiontwentyeighttradetest = null;
		writer.close();
		System.out.println("Done export legacysectiontwentyeighttradetest_06_09_2019.csv");
	}

	public void processLegacySECTTwentyEightData() throws Exception {
		System.out.println("Starting export legacysecttwentyeight_06_09_2019.csv");
		List<LegacySECTTwentyEight> legacysecttwentyeight = legacySECTTwentyEightService.allLegacySECTTwentyEight();
		String csv = CSVUtil.writeCSV(legacysecttwentyeight, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacysecttwentyeight_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacysecttwentyeight = null;
		writer.close();
		System.out.println("Done export legacysecttwentyeight_06_09_2019.csv");
	}

	public void processLegacySkillsProgrammeData() throws Exception {
		System.out.println("Starting export legacyskillsprogramme_06_09_2019.csv");
		List<LegacySkillsProgramme> legacyskillsprogramme = legacySkillsProgrammeService.allLegacySkillsProgramme();
		String csv = CSVUtil.writeCSV(legacyskillsprogramme, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacyskillsprogramme_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacyskillsprogramme = null;
		writer.close();
		System.out.println("Done export legacyskillsprogramme_06_09_2019.csv");
	}

	public void processLegacySkillsProgrammeAssessmentsData() throws Exception {
		System.out.println("Starting export legacyskillsprogrammeassessments_06_09_2019.csv");
		List<LegacySkillsProgrammeAssessments> legacyskillsprogrammeassessments = legacySkillsProgrammeAssessmentsService.allLegacySkillsProgrammeAssessments();
		String csv = CSVUtil.writeCSV(legacyskillsprogrammeassessments, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacyskillsprogrammeassessments_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacyskillsprogrammeassessments = null;
		writer.close();
		System.out.println("Done export legacyskillsprogrammeassessments_06_09_2019.csv");
	}

	public void processLegacyTvetData() throws Exception {
		System.out.println("Starting export legacytvet_06_09_2019.csv");
		List<LegacyTvet> legacytvet = legacyTvetService.allLegacyTvet();
		String csv = CSVUtil.writeCSV(legacytvet, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacytvet_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacytvet = null;
		writer.close();
		System.out.println("Done export legacytvet_06_09_2019.csv");
	}

	public void processLegacyUnitStandardData() throws Exception {
		System.out.println("Starting export legacyunitstandard_06_09_2019.csv");
		List<LegacyUnitStandard> legacyunitstandard = legacyUnitStandardService.allLegacyUnitStandard();
		String csv = CSVUtil.writeCSV(legacyunitstandard, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacyunitstandard_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacyunitstandard = null;
		writer.close();
		System.out.println("Done export legacyunitstandard_06_09_2019.csv");
	}

	public void processLegacyUnitStandardAssessmentData() throws Exception {
		System.out.println("Starting export legacyunitstandardassessment_06_09_2019.csv");
		List<LegacyUnitStandardAssessment> legacyunitstandardassessment = legacyUnitStandardAssessmentService.allLegacyUnitStandardAssessment();
		String csv = CSVUtil.writeCSV(legacyunitstandardassessment, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "legacyunitstandardassessment_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		legacyunitstandardassessment = null;
		writer.close();
		System.out.println("Done export legacyunitstandardassessment_06_09_2019.csv");
	}
	
	public void processHomeAffairsData() throws Exception {
		System.out.println("Starting export homeaffairs_06_09_2019.csv");
		List<HomeAffairs> homeAffairs = homeAffairsService.allHomeAffairs();
		String csv = CSVUtil.writeCSV(homeAffairs, ";", true);
		PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "homeaffairs_06_09_2019.csv"));
		if (csv.length() > 0) writer.write(csv);
		homeAffairs = null;
		writer.close();
		System.out.println("Done export homeaffairs_06_09_2019.csv");
	}
}
