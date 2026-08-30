package haj.com.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import haj.com.bean.AttachmentBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.BlankDAO;
import haj.com.entity.DgAllocation;
import haj.com.entity.Wsp;
import haj.com.entity.lookup.ReportGenerationSchedule;
import haj.com.framework.AbstractService;
import haj.com.service.lookup.ReportGenerationScheduleService;
import haj.com.utils.GenericUtility;


public class ReportGenerationService extends AbstractService {
	
	/* The dao. */
	private BlankDAO dao = new BlankDAO();
	
	/* Service levels */
	private ReportGenerationScheduleService reportGenerationScheduleService = new ReportGenerationScheduleService();
	private WspService wspService = new WspService();
	private DgAllocationService dgAllocationService = new DgAllocationService();
	
	public void runReportgeneration() throws Exception{
		Date today = GenericUtility.getStartOfDay(getSynchronizedDate());
		List<ReportGenerationSchedule> reportGenerationList = reportGenerationScheduleService.allReportGenerationSchedule();
		for (ReportGenerationSchedule reportGenerationSchedule : reportGenerationList) {
			if (reportGenerationSchedule.getDateOfGeneration() != null && today.equals(GenericUtility.getStartOfDay(reportGenerationSchedule.getDateOfGeneration()))) {
				switch (reportGenerationSchedule.getReportGeneration()) {
				case AllocationForecast:
					if (reportGenerationSchedule.getFinYear() != null) {
						generateForeCastForAllWspByFinYear(reportGenerationSchedule.getFinYear());
					} else {
						throw new Exception("Unable to locate fin year For DG Allocation Forecast Report. Report Generation Failed.");
					}
					break;
				case AllocationForecastAllVerifications:
					if (reportGenerationSchedule.getFinYear() != null) {
						generateForeCastForAllWspByFinYearAllVerificationStatus(reportGenerationSchedule.getFinYear());
					} else {
						throw new Exception("Unable to locate fin year For DG Allocation Forecast Report. Report Generation Failed.");
					}
					break;
				default:
					break;
				}
			}
		}
	}
	
	
	public void generateForeCastForAllWspByFinYear(Integer finYear) throws Exception {
		Workbook wb = new SXSSFWorkbook(100);
		Sheet sh = wb.createSheet();
		Map<String, Object[]> data = new TreeMap<String, Object[]>();
		data.put("1", new Object[] { "Entity ID", "Company Name", "Chamber", "Region Town", "Company Size", "Organisation Type", "Categorisation", "DG Levy (49.5%)", "Mandatory Grant Status", "Co-Funding", "DG Verification Status", "Requested Intervention", "Recommended Intervention", "Recommended Intervention title", "Ofo Code", "Requested Learners", "Requested learners with disability", "Recommended Learners", "Awarded Learners", "Partial Funding Awarded Learners", "Requested Amount", "Recommended Amount", "Award Amount", "Balance", "Partial Funding Award Amount", "Partial Funding Balance", "Disabled Grant Amount", "Total Awarded" });
		List<Wsp> wspList = wspService.findWspbyFinYearApprovedAndApprovedVerification(finYear);
//		int testCounter = 1;
		Integer counter = 2;
		for (Wsp wsp : wspList) {
			List<DgAllocation> forcastAllocationList = new ArrayList<>();
			forcastAllocationList = dgAllocationService.doAllocationForecastReportingByWsp(wsp);
			for (DgAllocation dgAllocation : forcastAllocationList) {
				populateObjectData(data, dgAllocation, counter);
				counter++;
			}
			forcastAllocationList = null;
//			testCounter ++;
		}
		
		Set<String> keyset = data.keySet();
		int rownum = 0;
		for (String key : keyset) {
			Row row = sh.createRow(rownum++);
			Object[] objArr = data.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof String)
					cell.setCellValue((String) obj);
				else if (obj instanceof Integer)
					cell.setCellValue((Integer) obj);
			}
		}
		
		// generates byte array 
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			wb.write(bos);
			byte[] bytes = bos.toByteArray();
			ArrayList<AttachmentBean> attachmentBeans = new ArrayList<>();
			AttachmentBean letter = new AttachmentBean();
			String fileName = "DG_Allocation_Forecast_Report.xlsx";
			letter.setExt("xlsx");
			letter.setFile(bytes);
			letter.setFilename(fileName);
			attachmentBeans.add(letter);
			List<String> emailNotifications = new ArrayList<>();
			String subject = "DG ALLOCATION FORECAST REPORT APPROVED VERIFICATIONS TEST SITE";
			String body = "DG Allocation Forecast Report For Approved Verifications. Test Site Generated: " + HAJConstants.sdfDDMMYYYYHHmm.format(getSynchronizedDate());
			if (HAJConstants.DEV_TEST_PROD.equals("P")) {
				emailNotifications.addAll(GenericUtility.processEmailNotfications());
				subject = "DG ALLOCATION FORECAST REPORT APPROVED VERIFICATIONS PRODUCTION SITE";
				body = "DG Allocation Forecast Report For Approved Verifications. Production System Generated: " + HAJConstants.sdfDDMMYYYYHHmm.format(getSynchronizedDate());
			} else {
				emailNotifications.add("testEmail@a.com");
			}
			for (String string : emailNotifications) {
				GenericUtility.sendMailWithAttachementTempWithLog(string, subject, body, attachmentBeans, null);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// null objects after use
		wspList = null;
	}
	
	public void generateForeCastForAllWspByFinYearAllVerificationStatus(Integer finYear) throws Exception {
		Workbook wb = new SXSSFWorkbook(100);
		Sheet sh = wb.createSheet();
		Map<String, Object[]> data = new TreeMap<String, Object[]>();
		data.put("1", new Object[] { "Entity ID", "Company Name", "Chamber", "Region Town", "Company Size", "Organisation Type", "Categorisation", "DG Levy (49.5%)", "Mandatory Grant Status", "Co-Funding", "DG Verification Status", "Requested Intervention", "Recommended Intervention", "Recommended Intervention title", "Ofo Code", "Requested Learners", "Requested learners with disability", "Recommended Learners", "Awarded Learners", "Partial Funding Awarded Learners", "Requested Amount", "Recommended Amount", "Award Amount", "Balance", "Partial Funding Award Amount", "Partial Funding Balance", "Disabled Grant Amount", "Total Awarded" });
		List<Wsp> wspList = wspService.findWspbyFinYearAndInVerification(finYear);
//		int testCounter = 1;
		Integer counter = 2;
		for (Wsp wsp : wspList) {
			List<DgAllocation> forcastAllocationList = new ArrayList<>();
			forcastAllocationList = dgAllocationService.doAllocationForecastReportingByWsp(wsp);
			for (DgAllocation dgAllocation : forcastAllocationList) {
				populateObjectData(data, dgAllocation, counter);
				counter++;
			}
			forcastAllocationList = null;
//			testCounter ++;
		}
		
		Set<String> keyset = data.keySet();
		int rownum = 0;
		for (String key : keyset) {
			Row row = sh.createRow(rownum++);
			Object[] objArr = data.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof String)
					cell.setCellValue((String) obj);
				else if (obj instanceof Integer)
					cell.setCellValue((Integer) obj);
			}
		}
		
		// generates byte array 
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			wb.write(bos);
			byte[] bytes = bos.toByteArray();
			ArrayList<AttachmentBean> attachmentBeans = new ArrayList<>();
			AttachmentBean letter = new AttachmentBean();
			String fileName = "DG_Allocation_Forecast_Report_All_Verifications.xlsx";
			letter.setExt("xlsx");
			letter.setFile(bytes);
			letter.setFilename(fileName);
			attachmentBeans.add(letter);
			List<String> emailNotifications = new ArrayList<>();
			String subject = "DG ALLOCATION FORECAST REPORT ALL VERIFICATIONS TEST SITE";
			String body = "DG Allocation Forecast Report For All Verifications. Test Site Generated: " + HAJConstants.sdfDDMMYYYYHHmm.format(getSynchronizedDate());
			if (HAJConstants.DEV_TEST_PROD.equals("P")) {
				emailNotifications.addAll(GenericUtility.processEmailNotfications());
				subject = "DG ALLOCATION FORECAST REPORT ALL VERIFICATIONS PRODUCTION SITE";
				body = "DG Allocation Forecast Report For All Verifications. Production System Generated: " + HAJConstants.sdfDDMMYYYYHHmm.format(getSynchronizedDate());
			} else {
				emailNotifications.add("testEmail@a.com");
			}
			for (String string : emailNotifications) {
				GenericUtility.sendMailWithAttachementTempWithLog(string, subject, body, attachmentBeans, null);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// null objects after use
		wspList = null;
	}


	private void populateObjectData(Map<String, Object[]> data, DgAllocation dgAllocation, Integer counter) {
		try {
			String entityId = "";
			if (dgAllocation.getMandatoryGrant().getWsp().getCompany() != null && dgAllocation.getMandatoryGrant().getWsp().getCompany().getLevyNumber() != null) {
				entityId = dgAllocation.getMandatoryGrant().getWsp().getCompany().getLevyNumber().trim();
			}
			
			String companyName = "";
			if (dgAllocation.getMandatoryGrant().getWsp().getCompany() != null && dgAllocation.getMandatoryGrant().getWsp().getCompany().getCompanyName() != null) {
				companyName = dgAllocation.getMandatoryGrant().getWsp().getCompany().getCompanyName();
			}
			
			String chamber = "";
			if (dgAllocation.getMandatoryGrant().getWsp().getCompany() != null && dgAllocation.getMandatoryGrant().getWsp().getCompany().getSicCode() != null) {
				chamber = dgAllocation.getMandatoryGrant().getWsp().getCompany().getSicCode().getDescription();
			}
			
			String regionTown = "";
			if (dgAllocation.getMandatoryGrant().getWsp().getCompany() != null && dgAllocation.getMandatoryGrant().getWsp().getCompany().getRegionTown() != null && dgAllocation.getMandatoryGrant().getWsp().getCompany().getRegionTown().getRegion() != null) {
				regionTown = dgAllocation.getMandatoryGrant().getWsp().getCompany().getRegionTown().getRegion().getDescription();
			}
			
			String companySize = "";
			if (dgAllocation.getMandatoryGrant().getWsp().getCompany().getNumberOfEmployees() != null) {
				if (dgAllocation.getMandatoryGrant().getWsp().getCompany().getNumberOfEmployees() <= 49) {
					companySize = "Small Company";	
				} else if (dgAllocation.getMandatoryGrant().getWsp().getCompany().getNumberOfEmployees() >= 50 && dgAllocation.getMandatoryGrant().getWsp().getCompany().getNumberOfEmployees() <= 149) {
					companySize = "Medium Company";
				} else if (dgAllocation.getMandatoryGrant().getWsp().getCompany().getNumberOfEmployees() >= 150) {
					companySize = "Large Company";
				}
			} else {
				companySize = "Small Company";
			}
			
			String organisationType = "";
			if (dgAllocation.getMandatoryGrant().getWsp().getCompany() != null && dgAllocation.getMandatoryGrant().getWsp().getCompany().getOrganisationType() != null) {
				organisationType = dgAllocation.getMandatoryGrant().getWsp().getCompany().getOrganisationType().getDescription();
			}
			
			String categorisation = "";
			if (dgAllocation.getDgAllocationParent() != null && dgAllocation.getDgAllocationParent().getCompanyCategorization() != null) {
				categorisation = dgAllocation.getDgAllocationParent().getCompanyCategorization().getFriendlyName();
			}
			
			String dgLevy = "";
			if (dgAllocation.getDgAllocationParent() != null && dgAllocation.getDgAllocationParent().getDgLevyAmount() != null) {
				dgLevy = dgAllocation.getDgAllocationParent().getDgLevyAmount().toString();
			}
			
			String mandatoryGrantStatus = "";
			if (dgAllocation.getMandatoryGrant().getWsp() != null && dgAllocation.getMandatoryGrant().getWsp().getWspStatusEnum() != null) {
				mandatoryGrantStatus = dgAllocation.getMandatoryGrant().getWsp().getWspStatusEnum().getFriendlyName();
			}
			
			String coFunding = "";
			if (dgAllocation.getMandatoryGrant().getWsp() != null && dgAllocation.getMandatoryGrant().getWsp().getCoFundingPartnership() != null) {
				coFunding = dgAllocation.getMandatoryGrant().getWsp().getCoFundingPartnership().getYesNoName();
			}
			
			String dgVerificationStatus = "";
			if (dgAllocation.getMandatoryGrant().getWsp() != null && dgAllocation.getMandatoryGrant().getWsp().getDgVerificationStatus() != null) {
				dgVerificationStatus = dgAllocation.getMandatoryGrant().getWsp().getDgVerificationStatus().getFriendlyName();
			}
			
			String requestedIntervention = "";
			if (dgAllocation.getMandatoryGrant().getInterventionType() != null) {
				requestedIntervention = dgAllocation.getMandatoryGrant().getInterventionType().getDescription();
			}
			
			String recommendedIntervention = "";
			if (dgAllocation.getInterventionType() != null) {
				recommendedIntervention = dgAllocation.getInterventionType().getDescription();
			}
			
			String recommendedInterventionTitle = "";
			if (dgAllocation.getLastestMandatoryGrantRecommendation() != null) {
				if (dgAllocation.getLastestMandatoryGrantRecommendation().getQualification() != null && dgAllocation.getLastestMandatoryGrantRecommendation().getQualification().getId() != null) {
					recommendedInterventionTitle = dgAllocation.getLastestMandatoryGrantRecommendation().getQualification().getDescription().trim();
				} else if (dgAllocation.getLastestMandatoryGrantRecommendation().getUnitStandards() != null && dgAllocation.getLastestMandatoryGrantRecommendation().getUnitStandards().getId() != null) {
					recommendedInterventionTitle = dgAllocation.getLastestMandatoryGrantRecommendation().getUnitStandards().getTitle();
				} else if (dgAllocation.getLastestMandatoryGrantRecommendation().getSkillsProgram() != null && dgAllocation.getLastestMandatoryGrantRecommendation().getSkillsProgram().getId() != null) {
					recommendedInterventionTitle = dgAllocation.getLastestMandatoryGrantRecommendation().getSkillsProgram().getDescription();
				} else if (dgAllocation.getLastestMandatoryGrantRecommendation().getSkillsSet() != null && dgAllocation.getLastestMandatoryGrantRecommendation().getSkillsSet().getId() != null) {
					recommendedInterventionTitle = dgAllocation.getLastestMandatoryGrantRecommendation().getSkillsSet().getDescription();
				} else if (dgAllocation.getLastestMandatoryGrantRecommendation().getNonCreditBearingIntervationTitle() != null && dgAllocation.getLastestMandatoryGrantRecommendation().getNonCreditBearingIntervationTitle().getId() != null) {
					recommendedInterventionTitle = dgAllocation.getLastestMandatoryGrantRecommendation().getNonCreditBearingIntervationTitle().getDescription();
				}
			} else {
				if (dgAllocation.getMandatoryGrant().getQualification() != null && dgAllocation.getMandatoryGrant().getQualification().getId() != null) {
					recommendedInterventionTitle = dgAllocation.getMandatoryGrant().getQualification().getDescription().trim();
				} else if (dgAllocation.getMandatoryGrant().getUnitStandard() != null && dgAllocation.getMandatoryGrant().getUnitStandard().getId() != null) {
					recommendedInterventionTitle = dgAllocation.getMandatoryGrant().getUnitStandard().getTitle();
				} else if (dgAllocation.getMandatoryGrant().getSkillsProgram() != null && dgAllocation.getMandatoryGrant().getSkillsProgram().getId() != null) {
					recommendedInterventionTitle = dgAllocation.getMandatoryGrant().getSkillsProgram().getDescription();
				} else if (dgAllocation.getMandatoryGrant().getSkillsSet() != null && dgAllocation.getMandatoryGrant().getSkillsSet().getId() != null) {
					recommendedInterventionTitle = dgAllocation.getMandatoryGrant().getSkillsSet().getDescription();
				} else if (dgAllocation.getMandatoryGrant().getNonCreditBearingIntervationTitle() != null && dgAllocation.getMandatoryGrant().getNonCreditBearingIntervationTitle().getId() != null) {
					recommendedInterventionTitle = dgAllocation.getMandatoryGrant().getNonCreditBearingIntervationTitle().getDescription();
				}
			}
			
			String ofoCode = "";
			if (dgAllocation.getMandatoryGrant().getOfoCodes() != null) {
				ofoCode = dgAllocation.getMandatoryGrant().getOfoCodes().getDescription().trim();
			} 
			
			String requestedLearners = "";
			if (dgAllocation.getTotalLearners() != null) {
				requestedLearners = dgAllocation.getTotalLearners().toString();
			}
			
			String requestedLearnersWithDisability = "";
			if (dgAllocation.getDisabledTotalLearners() != null) {
				requestedLearnersWithDisability = dgAllocation.getDisabledTotalLearners().toString();
			}
			
			String recommendedLearners = "";
			if (dgAllocation.getNumberOfLearners() != null) {
				recommendedLearners = dgAllocation.getNumberOfLearners().toString();
			}
			
			String awardedLearners = "";
			if (dgAllocation.getMaxPossibleLearners() != null) {
				awardedLearners = dgAllocation.getMaxPossibleLearners().toString();
			}
			
			String partialFundingAwardedLearners = "";
			if (dgAllocation.getCoFundingLearnersAwarded() != null) {
				partialFundingAwardedLearners = dgAllocation.getCoFundingLearnersAwarded().toString();
			}
			
			String requestedAmount = "";
			if (dgAllocation.getRequestedAmount() != null) {
				requestedAmount = dgAllocation.getRequestedAmount().toString();
			}
			
			String recommendedAmount = "";
			if (dgAllocation.getRecommendedAmount() != null) {
				recommendedAmount = dgAllocation.getRecommendedAmount().toString();
			}
			
			String awardAmount = "";
			if (dgAllocation.getAwardAmount() != null) {
				awardAmount = dgAllocation.getAwardAmount().toString();
			}
			
			String balance = "";
			if (dgAllocation.getBalanceRemaining() != null) {
				balance = dgAllocation.getBalanceRemaining().toString();
			}
			
			String partialFundingAwardAmount = "";
			if (dgAllocation.getCoFundingAwardAmount() != null) {
				partialFundingAwardAmount = dgAllocation.getCoFundingAwardAmount().toString();
			}
			
			String partialFundingBalance = "";
			if (dgAllocation.getRemainingCoFundingGrantAmount() != null) {
				partialFundingBalance = dgAllocation.getRemainingCoFundingGrantAmount().toString();
			}
			
			String disabledGrantAmount = "";
			if (dgAllocation.getDisabledGrantAmount() != null) {
				disabledGrantAmount = dgAllocation.getDisabledGrantAmount().toString();
			}
			
			String totalAwarded = "";
			if (dgAllocation.getTotalAwardAmount() != null) {
				totalAwarded = dgAllocation.getTotalAwardAmount().toString();
			}
			
			// map to object
			data.put(counter.toString(), 
					new Object[] { entityId, companyName, chamber, regionTown, companySize, 
							organisationType, categorisation, dgLevy, mandatoryGrantStatus, coFunding, 
							dgVerificationStatus, requestedIntervention, recommendedIntervention, recommendedInterventionTitle, ofoCode, 
							requestedLearners, requestedLearnersWithDisability, recommendedLearners, awardedLearners, partialFundingAwardedLearners, 
							requestedAmount, recommendedAmount, awardAmount, balance, partialFundingAwardAmount, 
							partialFundingBalance, disabledGrantAmount, totalAwarded});
		} catch (Exception e) {
			logger.fatal(e);
		}
	}
}
