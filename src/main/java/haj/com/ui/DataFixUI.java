package haj.com.ui;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.constants.HAJConstants;
import haj.com.entity.Wsp;
import haj.com.entity.enums.WspStatusEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.DataFixService;
import haj.com.service.UploadTrackerAtrWspService;
import haj.com.service.WspService;
import haj.com.utils.GenericUtility;

@ManagedBean(name = "dataFixUI")
@ViewScoped
public class DataFixUI extends AbstractUI {

	private DataFixService dataFixService = new DataFixService();
	
	private List<String> companyLNumbers = new ArrayList<>();
	private List<Wsp> wspEffectedList = new ArrayList<>();
	private List<Integer> distinctFinYears = new ArrayList<>();
	private Integer selectedYear;
	private String lNumber;
	private Long previousTaskId;
	private Long createUserId;
	private Long wspId;
	private Long wspSignOffId;
	private Long extensionRequestId;
	private Integer batchSize;

	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void runInit() throws Exception {
		populateDistinctFinYears();
	}
	
	private void populateDistinctFinYears() throws Exception {
		WspService service = new WspService();
		distinctFinYears = service.findDictinctFinYears();
		if (distinctFinYears.size() != 0) {
			selectedYear = distinctFinYears.get(0);
		}
		service = null;
	}

	public void addLnumber() {
		try {
			companyLNumbers.add(lNumber.trim());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void generateMissingTasksByLNumber(){
		try {
			boolean canRun = true;
			String errors = "";
			if (companyLNumbers.size() == 0) {
				errors += ", No Lumbers Found";
				canRun = false;
			}
			if (previousTaskId == null) {
				errors += ", No previousTaskId provided";
				canRun = false;
			}
			if (createUserId == null) {
				errors += ", No createUserId provided";
				canRun = false;
			}
			if (canRun) {
				dataFixService.fixAllocationTasks(companyLNumbers, previousTaskId, createUserId);
			} else {
				addWarningMessage("Cant proceed. Add: " + errors );
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			GenericUtility.mailError("Error Generation Of Missing Tasks", "<p>Error of generation of allocation tasks missing.</p><p> Stack Trace:<p/><p> "  +exceptionAsString+ "</p>");
		}
	}
	
	public void viewDocumentUploadTaskToMerSETA(){
		try {
			wspEffectedList = dataFixService.locateDocumentUploadTaskToMerSETA();
			addInfoMessage("WSP FOUND: " + wspEffectedList.size());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void sendDocumentUploadTaskToMerSETA(){
		try {
			dataFixService.sendDocumentUploadTaskToMerSETA();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			GenericUtility.mailError("Error Sending Wsp Back to Merseta", "<p>Error of sending wsp tasks back to merseta from document upload.</p><p> Stack Trace:<p/><p> "  +exceptionAsString+ "</p>");
		}
	}
	
	public void reopenWspForAppeal(){
		try {
			if (wspId != null) {
				dataFixService.reopenWspForAppeal(wspId, getSessionUI().getActiveUser());
				addInfoMessage("WSP Reset");
			}else {
				addWarningMessage("Provide WSP id before Proceeding!");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			GenericUtility.mailError("Error re-setting Wsp to Appeal Status", "<p>Error of re-setting wsp to appeal status.</p><p> Stack Trace:<p/><p> "  +exceptionAsString+ "</p>");
		}
	}
	
	public void reopenWspForApproval(){
		try {
			if (wspId != null) {
				dataFixService.reopenWspForFinalApprovalMerseta(wspId, getSessionUI().getActiveUser());
				addInfoMessage("WSP Reset");
			}else {
				addWarningMessage("Provide WSP id before Proceeding!");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			GenericUtility.mailError("Error re-setting Wsp to Appeal Status", "<p>Error of re-setting wsp to appeal status.</p><p> Stack Trace:<p/><p> "  +exceptionAsString+ "</p>");
		}
	}
	
	public void setAcceptedForWspSignOff(){
		try {
			if (wspSignOffId != null) {
				dataFixService.setAcceptedForWspSignOff(wspSignOffId, getSessionUI().getActiveUser());
				addInfoMessage("Action Complete");
			}else {
				addWarningMessage("Provide wspSignOffId before Proceeding!");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			GenericUtility.mailError("Error Setting Accepted To True On WSP Sign Off", "<p>Error of Setting Accepted To True On WSP Sign Off.</p><p> Stack Trace:<p/><p> "  +exceptionAsString+ "</p>");
		}
	}
	
	public void linkActiveContractsDataToPIP(){
		try {
			dataFixService.linkActiveContractToPIP();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			GenericUtility.mailError("Error Linking Active Contracts to unlinked PIP", "<p>Error Linking Active Contracts to unlinked PIP.</p><p> Stack Trace:<p/><p> "  +exceptionAsString+ "</p>");
		}
	}
	
	public void sendWspToMersetaWorkflow(){
		try {
			if (wspId != null) {
				dataFixService.sendWspToMersetaForApproval(wspId, getSessionUI().getActiveUser());
				addInfoMessage("Action Complete");
			}else {
				addWarningMessage("Provide wspId before Proceeding!");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			GenericUtility.mailError("Error Setting Accepted To True On WSP Sign Off", "<p>Error of Setting Accepted To True On WSP Sign Off.</p><p> Stack Trace:<p/><p> "  +exceptionAsString+ "</p>");
		}
	}
	
	public void archiveRecords(){
		try {
			dataFixService.archiveRecords();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void deleteFailedImportedEntriesByWspID(){
		try {
			if (wspId != null) {
				if (batchSize != null) {
					dataFixService.deleteFailedImportedEntriesByWspID(wspId, batchSize, getSessionUI().getActiveUser().getId());
					addInfoMessage("Action Complete");
					batchSize = null;
					wspId = null;
				}else {
					addErrorMessage("Add Batch Size before Proceeding");
				}
			} else {
				addErrorMessage("Add WSP ID before Proceeding");
			}
		} catch (Exception e) {
			try {
				UploadTrackerAtrWspService.instance().createEntry("SYSTEM GRANT DATA", 0, "SYSTEM DELETION ERROR: " + e.getMessage() , wspId, getSessionUI().getActiveUser().getId());
			} catch (Exception b) {
				logger.fatal(b);
			}
			addErrorMessage(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			GenericUtility.mailError("Error deleteFailedImportedEntriesByWsp", "<p>Error deleteFailedImportedEntriesByWsp.</p><p> Stack Trace:<p/><p> "  +exceptionAsString+ "</p>");
		}
	}
	
	public void createTaskForExtensionRequest(){
		try {
			if (extensionRequestId != null) {
				dataFixService.createTaskForExtensionRequest(extensionRequestId, getSessionUI().getActiveUser());
				addInfoMessage("Action Complete");
			} else {
				addWarningMessage("Provide extensionRequestId before Proceeding!");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			GenericUtility.mailError("Error Creating Task for Extension Request", "<p>Error Creating Task for Extension Request.</p><p> Stack Trace:<p/><p> "  +exceptionAsString+ "</p>");
		}
	}
	
	public void generateDgVerificationByWspId(){
		try {
			if (wspId != null) {
				dataFixService.generateDgVerificationByWspID(wspId, getSessionUI().getActiveUser());
				addInfoMessage("Action Complete");
			}else {
				addWarningMessage("Provide wspId before Proceeding!");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			GenericUtility.mailError("Error Generating Verification By WSP ID", "<p>Error Generating Verification By WSP ID.</p><p> Stack Trace:<p/><p> "  +exceptionAsString+ "</p>");
		}
	}
	
	public void generateDgVerificationByWspIdScript(){
		try {
			
			dataFixService.generateDgVerificationByWspIDScript(getSessionUI().getActiveUser());
			addInfoMessage("Action Complete");
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			GenericUtility.mailError("Error Generating Verification By WSP ID", "<p>Error Generating Verification By WSP ID.</p><p> Stack Trace:<p/><p> "  +exceptionAsString+ "</p>");
		}
	}
	
	public void generateDgVerificationForSmallCompaniesRejectedWspFinYear(){
		try {
			dataFixService.generateDgVerificationForSmallCompaniesRejectedWsp(WspStatusEnum.Rejected, 2020);
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			GenericUtility.mailError("Error Generating DG Verification", "<p>Error Generating DG verification For Small Companies With Grants Rejected.</p><p> Stack Trace:<p/><p> "  +exceptionAsString+ "</p>");
		}
	}
	
	public void generateMgVerificationByFinYear(){
		try {
			if (selectedYear != null) {
				dataFixService.generateMgVerificationByFinYear(Long.valueOf(selectedYear));
				addInfoMessage("Action Complete");
			} else {
				addInfoMessage("Select a year before proceeding");
			}
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			GenericUtility.mailError("Error Generating DG Verification", "<p>Error Generating DG verification For Small Companies With Grants Rejected.</p><p> Stack Trace:<p/><p> "  +exceptionAsString+ "</p>");
		}
	}
	
	
	public void fixSitesSme() {
		try {
			if (wspId != null) {
				dataFixService.fixSitesSme(wspId, getSessionUI().getActiveUser());
				addInfoMessage("Mentor Reset");
			}else {
				addWarningMessage("Provide WSP id before Proceeding!");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			GenericUtility.mailError("Error re-setting Wsp to Appeal Status", "<p>Error of re-setting wsp to appeal status.</p><p> Stack Trace:<p/><p> "  +exceptionAsString+ "</p>");
		}
	}
	
	public void populateWspCompanyHistroy() {
		try {
			dataFixService.populateWspCompanyHistoryForLastestGrantYear();
			addInfoMessage("Action underway notification to be sent");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			GenericUtility.mailError("Error populateing Wsp Company Histroy", "<p>Error populateing Wsp Company Histroy.</p><p> Stack Trace:<p/><p> "  +exceptionAsString+ "</p>");
		}
	}
	
	public void generateTranchOnePayments() {
		try {
			dataFixService.generateTranchOnePaymentForApprovedMoa();
			addInfoMessage("Action underway notification to be sent");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			GenericUtility.mailError("Error generateTranchOnePayments", "<p>Error generateTranchOnePayments.</p><p> Stack Trace:<p/><p> "  +exceptionAsString+ "</p>");
		}
	}
	
	public void fixToActiveContractDetail() {
		try {
			dataFixService.fixToActiveContractDetail();
			addInfoMessage("Action underway notification to be sent");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			GenericUtility.mailError("Error fixToActiveContractDetail", "<p>Error fixToActiveContractDetail.</p><p> Stack Trace:<p/><p> "  +exceptionAsString+ "</p>");
		}
	}
	
	public void fixDataActiveContracts() {
		try {
			dataFixService.fixDataActiveContracts();
			addInfoMessage("Action underway notification to be sent");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			GenericUtility.mailError("Error fixDataActiveContracts", "<p>Error fixDataActiveContracts.</p><p> Stack Trace:<p/><p> "  +exceptionAsString+ "</p>");
		}
	}
	
	public void populateUserCitizenType() {
		try {
			dataFixService.populateUserCitizenType();
			addInfoMessage("Action underway notification to be sent");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			GenericUtility.mailError("Error populateUserCitizenType", "<p>Error fixDataActiveContracts.</p><p> Stack Trace:<p/><p> "  +exceptionAsString+ "</p>");
		}
	}
	
	public void deleteDuplicateApprovedContracts() {
		try {
			dataFixService.deleteDuplicateApprovedContracts();
			addInfoMessage("Action underway notification to be sent");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			GenericUtility.mailError("Error deleteDuplicateApprovedContracts", "<p>Error deleteDuplicateApprovedContracts.</p><p> Stack Trace:<p/><p> "  +exceptionAsString+ "</p>");
		}
	}
	
	public void fixDataActiveContractDetail() {
		try {
			dataFixService.fixDataActiveContractDetail();
			addInfoMessage("Action underway notification to be sent");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			GenericUtility.mailError("Error fixDataActiveContractDetail", "<p>Error fixDataActiveContractDetail.</p><p> Stack Trace:<p/><p> "  +exceptionAsString+ "</p>");
		}
	}
	
	public void fixExtensionOfScopeSelfEvaluation() {
		try {
			dataFixService.fixExtensionOfScopeSelfEvaluation();
			addInfoMessage("Action underway notification to be sent");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			GenericUtility.mailError("Error fixExtensionOfScopeSelfEvaluation", "<p>Error fixExtensionOfScopeSelfEvaluation on site"+HAJConstants.PL_LINK+".</p><p> Stack Trace:<p/><p> "  +exceptionAsString+ "</p>");
		}
	}
	
	public void fixUsersData() {
		try {
			dataFixService.fixUsersData();
			addInfoMessage("Action underway notification to be sent");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			GenericUtility.mailError("Error fixUsersData", "<p>Error fixUsersData on site"+HAJConstants.PL_LINK+".</p><p> Stack Trace:<p/><p> "  +exceptionAsString+ "</p>");
		}
	}
	
	public void runUserNameFix() {
		try {
			dataFixService.runUserNameFix();
			addInfoMessage("Action underway notification to be sent");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			GenericUtility.mailError("Error runUserNameFix", "<p>Error runUserNameFix on site"+HAJConstants.PL_LINK+".</p><p> Stack Trace:<p/><p> "  +exceptionAsString+ "</p>");
		}
	}
	
	public void runUserBirthTownFix() {
		try {
			dataFixService.runUserBirthTownFix();
			addInfoMessage("Action underway notification to be sent");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			GenericUtility.mailError("Error runUserBirthTownFix", "<p>Error runUserBirthTownFix on site"+HAJConstants.PL_LINK+".</p><p> Stack Trace:<p/><p> "  +exceptionAsString+ "</p>");
		}
	}
	
	public void runCompanySiteNumberAllocation() {
		try {
			dataFixService.runCompanySiteNumberAllocation();
			addInfoMessage("Action underway notification to be sent");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			GenericUtility.mailError("Error runCompanySiteNumberAllocation", "<p>Error runCompanySiteNumberAllocation on site"+HAJConstants.PL_LINK+".</p><p> Stack Trace:<p/><p> "  +exceptionAsString+ "</p>");
		}
	}
	
	public void fixComapnyLearnersProviderApplicationData() {
		try {
			dataFixService.fixComapnyLearnersProviderApplicationData();
			addInfoMessage("Action underway notification to be sent");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			GenericUtility.mailError("Error fixComapnyLearnersProviderApplicationData", "<p>Error fixComapnyLearnersProviderApplicationData on site"+HAJConstants.PL_LINK+".</p><p> Stack Trace:<p/><p> "  +exceptionAsString+ "</p>");
		}
	}
	
	public void testQmrGeneration() {
		try {
			dataFixService.testQmrMonthGeneration(HAJConstants.sdf.parse("2020-04-01"));
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			GenericUtility.mailError("Error testQmrGeneration", "<p>Error testQmrGeneration on site"+HAJConstants.PL_LINK+".</p><p> Stack Trace:<p/><p> "  +exceptionAsString+ "</p>");
		}
	}
	
	public void testQmrQuarterGeneration() {
		try {
			dataFixService.testQmrQuarterGeneration();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			GenericUtility.mailError("Error testQmrQuarterGeneration", "<p>Error testQmrQuarterGeneration on site"+HAJConstants.PL_LINK+".</p><p> Stack Trace:<p/><p> "  +exceptionAsString+ "</p>");
		}
	}
	
	public void populateCompanyLearnersDateForQmr() {
		try {
			dataFixService.populateCompanyLearnersDateForQmr();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			GenericUtility.mailError("Error populateCompanyLearnersDateForQmr", "<p>Error populateCompanyLearnersDateForQmr on site"+HAJConstants.PL_LINK+".</p><p> Stack Trace:<p/><p> "  +exceptionAsString+ "</p>");
		}
	}
	
	public void populateCompanyLearnersLegacyDateForQmr() {
		try {
			dataFixService.populateCompanyLearnersLegacyDateForQmr();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			GenericUtility.mailError("Error populateCompanyLearnersLegacyDateForQmr", "<p>Error populateCompanyLearnersLegacyDateForQmr on site"+HAJConstants.PL_LINK+".</p><p> Stack Trace:<p/><p> "  +exceptionAsString+ "</p>");
		}
	}
	
	public void populatePipLearnerLink() {
		try {
			dataFixService.populatePipLearnerLink();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			GenericUtility.mailError("Error populatePipLearnerLink", "<p>Error populatePipLearnerLink on site"+HAJConstants.PL_LINK+".</p><p> Stack Trace:<p/><p> "  +exceptionAsString+ "</p>");
		}
	}
	
	public void populateLearnerLinkToPipLearnerLink() {
		try {
			dataFixService.populateLearnerLinkToPipLearnerLink();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			GenericUtility.mailError("Error populateLearnerLinkToPipLearnerLink", "<p>Error populateLearnerLinkToPipLearnerLink on site"+HAJConstants.PL_LINK+".</p><p> Stack Trace:<p/><p> "  +exceptionAsString+ "</p>");
		}
	}
	
	public void runAssessorModeratorDeAccreditationSchedule() {
		try {
			dataFixService.runAssessorModeratorDeAccreditationSchedule(null);
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			GenericUtility.mailError("Error runAssessorModeratorDeAccreditationSchedule", "<p>Error dataFixService.runAssessorModeratorDeAccreditationSchedule(null) on site"+HAJConstants.PL_LINK+".</p><p> Stack Trace:<p/><p> "  +exceptionAsString+ "</p>");
		}
	}
	
	public void runProviderDeAccreditationSchedule() {
		try {
			dataFixService.runProviderDeAccreditationSchedule(null);
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			GenericUtility.mailError("Error runProviderDeAccreditationSchedule", "<p>Error dataFixService.runProviderDeAccreditationSchedule(null) on site"+HAJConstants.PL_LINK+".</p><p> Stack Trace:<p/><p> "  +exceptionAsString+ "</p>");
		}
	}
	
	public void populateNewSdpStructure() {
		try {
			dataFixService.populateNewSdpStructure();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			GenericUtility.mailError("Error populateNewSdpStructure", "<p>Error dataFixService.populateNewSdpStructure() on site"+HAJConstants.PL_LINK+".</p><p> Stack Trace:<p/><p> "  +exceptionAsString+ "</p>");
		}
	}
	
	public void populateNewAssModStructure() {
		try {
			dataFixService.populateNewAssModStructure();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			GenericUtility.mailError("Error populateNewAssModStructure", "<p>Error dataFixService.populateNewAssModStructure() on site"+HAJConstants.PL_LINK+".</p><p> Stack Trace:<p/><p> "  +exceptionAsString+ "</p>");
		}
	}
	
	/** Getters and setters */
	public List<String> getCompanyLNumbers() {
		return companyLNumbers;
	}

	public void setCompanyLNumbers(List<String> companyLNumbers) {
		this.companyLNumbers = companyLNumbers;
	}

	public String getlNumber() {
		return lNumber;
	}

	public void setlNumber(String lNumber) {
		this.lNumber = lNumber;
	}

	public Long getPreviousTaskId() {
		return previousTaskId;
	}

	public void setPreviousTaskId(Long previousTaskId) {
		this.previousTaskId = previousTaskId;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public List<Wsp> getWspEffectedList() {
		return wspEffectedList;
	}

	public void setWspEffectedList(List<Wsp> wspEffectedList) {
		this.wspEffectedList = wspEffectedList;
	}

	public Long getWspId() {
		return wspId;
	}

	public void setWspId(Long wspId) {
		this.wspId = wspId;
	}

	public Long getWspSignOffId() {
		return wspSignOffId;
	}

	public void setWspSignOffId(Long wspSignOffId) {
		this.wspSignOffId = wspSignOffId;
	}

	public Long getExtensionRequestId() {
		return extensionRequestId;
	}

	public void setExtensionRequestId(Long extensionRequestId) {
		this.extensionRequestId = extensionRequestId;
	}

	public Integer getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(Integer batchSize) {
		this.batchSize = batchSize;
	}

	public Integer getSelectedYear() {
		return selectedYear;
	}

	public void setSelectedYear(Integer selectedYear) {
		this.selectedYear = selectedYear;
	}

	public List<Integer> getDistinctFinYears() {
		return distinctFinYears;
	}

	public void setDistinctFinYears(List<Integer> distinctFinYears) {
		this.distinctFinYears = distinctFinYears;
	}


}
