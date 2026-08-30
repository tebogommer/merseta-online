package  haj.com.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.apache.commons.io.FilenameUtils;
import org.hibernate.validator.constraints.Email;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.bean.ATRReportSummayBean;
import haj.com.bean.DGApplicationSummaryBean;
import haj.com.bean.EmpEmploymentStatusBean;
import haj.com.bean.EmployeeEquityProfileBean;
import haj.com.bean.EmployeeProfileBean;
import haj.com.bean.PivotalTrainingReportBean;
import haj.com.bean.WSPReportSummayBean;
import haj.com.constants.HAJConstants;
import haj.com.entity.BankingDetails;
import haj.com.entity.Company;
import haj.com.entity.CompanyUsers;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.ExtensionRequest;
import haj.com.entity.SDFCompany;
import haj.com.entity.Sites;
import haj.com.entity.TrainingComittee;
import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.WspSignoff;
import haj.com.entity.WspSkillsRequirements;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.enums.WspReportEnum;
import haj.com.entity.enums.WspStatusEnum;
import haj.com.entity.enums.WspTypeEnum;
import haj.com.entity.lookup.OrganisedLabourUnion;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationErrorException;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.BankingDetailsService;
import haj.com.service.CompanyService;
import haj.com.service.CompanyUsersService;
import haj.com.service.DgVerificationService;
import haj.com.service.DocService;
import haj.com.service.ExtensionRequestService;
import haj.com.service.JasperService;
import haj.com.service.MailDef;
import haj.com.service.MandatoryGrantDetailService;
import haj.com.service.SDFCompanyService;
import haj.com.service.ScheduleChangesLogService;
import haj.com.service.SitesService;
import haj.com.service.TasksService;
import haj.com.service.TrainingComitteeService;
import haj.com.service.WspService;
import haj.com.service.WspSignoffService;
import haj.com.service.WspSkillsRequirementsService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.utils.GenericUtility;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


@ManagedBean(name = "adminReopenWspUI")
@ViewScoped
public class AdminReopenWspUI extends AbstractUI {

	/* Entity Level */
	private Company selectedCompany = null;
	private Wsp selectedWsp = null;
	private Wsp newWsp = null;
	private ExtensionRequest extensionRequest = null;
	private BankingDetails bankingDetails = null;
	private WspSignoffService signoffService = new WspSignoffService();
	private Doc doc;
	private SDFCompany primarySDFObject = null;
	private Company grantCompany = null;
	
	/* Service Level */
	private CompanyService companyService = new CompanyService();
	private WspService wspService = new WspService();
	private ExtensionRequestService extensionRequestService = new ExtensionRequestService();
	private SDFCompanyService sdfCompanyService = new SDFCompanyService();
	private BankingDetailsService bankingDetailsService = null;
	private SitesService sitesService = null;
	private CompanyUsersService companyUsersService = null;
	private TrainingComitteeService trainingComitteeService = null;
	
	/* Lazy Data Models */
	private LazyDataModel<Company> dataModelComapny;
	
	/* Vars */
	@Email(message = "Please enter a valid Email Address")
	private String email;
	
	/* Array lists */
	private List<ExtensionRequest> extensionRequestsByCompany;
	private List<ExtensionRequest> extensionRequestsByWsp;
	private List<Wsp> companyWspList;
	private List<Integer> intList = new ArrayList<>();
	private List<Company> linkedCompanies = null;
	private List<Sites> sites = null;
	private List<CompanyUsers> companyUsers = null; 
	private List<TrainingComittee> trainingComittees = null;
	private List<RejectReasons> selectedRejectionReasons = null;
	
	/* display booleans */
	private boolean createExtensionRequest = false;
	private boolean reopenWsp = false;
	private boolean belowThreshold = false;
	private boolean sendToSdf = false;
	
	/* Logic booleans */
	private boolean canCreateNewWsp = false;
	private boolean primarySDF = false;
	private boolean canInitiate = false;
	private boolean displayValidiationCheck = false;
	
	/* Vars */
	private Integer selectedNumber = 1;
	private String errors = null;
	private Integer finYear = Integer.parseInt(GenericUtility.sdfYear.format(getNow()));
	private Date closeOffDate;
	private String results = "";
	
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
		populateServiceLevels();
		populateCompanies();
		intList = new ArrayList<>();
		intList.add(1);
		intList.add(2);
		intList.add(3);
		intList.add(4);
	}

	private void populateServiceLevels() throws Exception{
	}
	
	private void populateCompanies() throws Exception{
		companyInfo();
	}
	
	public void companyInfo() {
		dataModelComapny = new LazyDataModel<Company>() {
			private static final long serialVersionUID = 1L;
			private List<Company> companyList = new ArrayList<>();
			@Override
			public List<Company> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (sortField == null) {
						sortField = "companyStatus";
						sortOrder = SortOrder.DESCENDING;
					}
					companyList = companyService.allCompany2(Company.class, first, pageSize, sortField, sortOrder, filters);
					dataModelComapny.setRowCount(companyService.count(Company.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return companyList;
			}
			@Override
			public Object getRowKey(Company object) {
				// TODO Auto-generated method stub
				return object.getId();
			}
			@Override
			public Company getRowData(String rowKey) {
				for (Company obj : companyList) {
					if (obj.getId().equals(Long.valueOf(rowKey))) {
						return obj;
					}
				}
				return null;
			}
		};
	}
	
	public void selectCompany(){
		try {
			displayValidiationCheck = false;
			results = "";
			primarySDFObject = null;
			createExtensionRequest = false;
			reopenWsp = false;
			companyWspList = wspService.findByCompany(selectedCompany);
			extensionRequestsByCompany = extensionRequestService.findByCompany(selectedCompany);
			addInfoMessage("Company Selected");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void cancelExtenionRequest(){
		try {
			createExtensionRequest = false;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepExtensionRequest(){
		try {
			createExtensionRequest = true;
			prepNewExtionRequest();
			extensionRequestsByWsp = extensionRequestService.findByWSP(selectedWsp);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void prepNewExtionRequest() throws Exception{
		extensionRequest = new ExtensionRequest();
		extensionRequest.setUser(getSessionUI().getActiveUser());
		extensionRequest.setStatus(ApprovalEnum.PendingApproval);
		extensionRequest.setWsp(selectedWsp);
		extensionRequest.setCompany(selectedCompany);
		extensionRequestService.prepareNewDocs(extensionRequest);
	}
	
	public void storeNewFile(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			if (doc.getId() == null) {
				doc.setExtensionRequest(extensionRequest);
			}else {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}

	}

	/**
	 * Creates an extension request and logs the action by the super admin
	 */
	public void createExtensionAndLog(){
		try {
			// extension request information
			String companyName = selectedCompany.getCompanyName() + " ( " + selectedCompany.getLevyNumber() + " )";
			String information = "";
			adminCreate(extensionRequest, false, getSessionUI().getActiveUser());
			information = "Extension request created for company: " + companyName + " for grant application fin year: " + selectedWsp.getFinYear() + ". Refer to document uploaded for more information. Extension Request ID: " + extensionRequest.getId() ;
			ScheduleChangesLogService.instance().addNewEntry(selectedCompany, null, extensionRequest.getClass().getName(), extensionRequest.getId(), information, true);
			addInfoMessage("Extension Request Created");
			createExtensionRequest = false;
			reopenWsp = false;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void adminCreate(ExtensionRequest entity, boolean createWorkflow, Users user) throws Exception {
		for (Doc doc : entity.getDocs()) {
			if (doc.getId() != null) {
				DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
				if (docByte != null) {
					doc.setData(docByte.getData());
				}				
			}
			if (doc.getData() == null) throw new Exception("Please ensure all documents are uploaded");
		}
		entity.setStatus(ApprovalEnum.Approved);
//		entity.setApprovedUser(user);
		if (entity.getId() == null) {
			this.extensionRequestService.create(entity);
		} else this.extensionRequestService.update(entity);
		for (Doc doc : entity.getDocs()) {
			if (doc.getId() == null) DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), user);
		}	
	}
	
	/**
	 * Re-opens the grant application
	 */
	public void reopenWspBySelection() {
		try {
			if (selectedWsp != null) {
				
				// validation 
				if (selectedNumber == 1 || selectedNumber == 3  || selectedNumber == 4) {
					validatePrimarySdfAssigned();
				}	
				String companyName = selectedCompany.getCompanyName() + " ( " + selectedCompany.getLevyNumber() + " )";
				String information = "Re-opened grant application for company: " + companyName + " for grant year: " + selectedWsp.getFinYear() + ".";
				
				String informationChangedOnWsp = "";
				SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
				
				if (selectedWsp.getApprovedDate() != null) {
					informationChangedOnWsp += " Approved Date: " +  sdf.format(selectedWsp.getApprovedDate()) + " to " + " null.";
				}
				if (selectedWsp.getSdfAppealedGrantDate() != null) {
					informationChangedOnWsp += " SDF Appealed Grant: " +  selectedWsp.getSdfAppealedGrantDate() + " to " + " null.";
				}
				
				if (selectedWsp.getSdfAppealedGrant() != null) {
					informationChangedOnWsp += " SDF Appealed Grant (Boolean): " +  selectedWsp.getSdfAppealedGrantDate() + " to " + " false.";
				}
				if (selectedWsp.getGrantRejected() != null) {
					informationChangedOnWsp += " Grant Rejected: " +  selectedWsp.getGrantRejected() + " to " + " null.";
				}
				if (selectedWsp.getGrantRejected() != null) {
					informationChangedOnWsp += " Grant Rejected: " +  selectedWsp.getGrantRejected() + " to " + " null.";
				}
				if (selectedWsp.getSystemApprovalRejection() != null) {
					informationChangedOnWsp += " System Automatic Rejection: " +  selectedWsp.getSystemApprovalRejection() + " to " + " null.";
				}
				if (selectedNumber == 1) {
					if (selectedWsp.getWithSdf() != null) {
						informationChangedOnWsp += " Indicator if grant application with sdf: " +  selectedWsp.getWithSdf() + " to " + " true.";
					}
					if (selectedWsp.getWspStatusEnum() != null) {
						informationChangedOnWsp += " Grant Application Status: " +  selectedWsp.getWspStatusEnum().getFriendlyName() + " to " + WspStatusEnum.Draft.getFriendlyName();
					}
				} else if (selectedNumber == 4) {
					if (selectedWsp.getWithSdf() != null) {
						informationChangedOnWsp += " Indicator if grant application with sdf: " +  selectedWsp.getWithSdf() + " to " + " true.";
					}
					if (selectedWsp.getWspStatusEnum() != null) {
						informationChangedOnWsp += " Grant Application Status: " +  selectedWsp.getWspStatusEnum().getFriendlyName() + " to " + WspStatusEnum.PendingSignOff.getFriendlyName();
					}
				} else {
					if (selectedWsp.getWithSdf() != null) {
						informationChangedOnWsp += " Indicator if grant application with sdf: " +  selectedWsp.getWithSdf() + " to " + " false.";
					}
					if (selectedWsp.getWspStatusEnum() != null) {
						informationChangedOnWsp += " Grant Application Status: " +  selectedWsp.getWspStatusEnum().getFriendlyName() + " to " + WspStatusEnum.Pending.getFriendlyName();
					}
				}
				
				// sets information for re-open
				selectedWsp.setApprovedDate(null);
				selectedWsp.setSdfAppealedGrantDate(null);
				selectedWsp.setSdfAppealedGrant(false);
				selectedWsp.setGrantRejected(null);
				selectedWsp.setSystemApprovalRejection(null);
				selectedWsp.setWithSdf(false);
				
				// indicators for reopen
//				selectedWsp.setAdminReopened(true);
//				selectedWsp.setApplicationReopened(true);
//				selectedWsp.setApplicationReopenedDate(getNow());
				
//				switch (selectedWsp.getReopenedToLocation()) {
//				case Draft: 1
				switch (selectedNumber) {
					case 1:
					selectedWsp.setWspStatusEnum(WspStatusEnum.Draft);
					selectedWsp.setWithSdf(true);
					selectedWsp.setSubmissionDate(null);
					information = "Grant application status set to Draft for primary SDF to access and re-upload data. Information changed on a grant application level: " + informationChangedOnWsp;
					wspService.update(selectedWsp);
					ScheduleChangesLogService.instance().addNewEntry(selectedCompany, null, selectedWsp.getClass().getName(), selectedWsp.getId(), information, true);
					WspSignoffService wspSignoffService = new WspSignoffService();
					List<WspSignoff> existingSignOffList = wspSignoffService.findByWsp(selectedWsp);
					for (WspSignoff wspSignoff : existingSignOffList) {
						signoffService.delete(wspSignoff);
					}
					List<WspSignoff> createList = new ArrayList<>();
					List<SDFCompany> sdfCompanies = sdfCompanyService.findByCompanySignOff(selectedWsp.getCompany(), true);
					for (SDFCompany sdfCompany : sdfCompanies) {
						if (sdfCompany.getSdfType().getSignOnRecognition() == null) {
							WspSignoff signOff = new WspSignoff(selectedWsp, sdfCompany.getSdf());
							signOff.setSdfType(sdfCompany.getSdfType());
							createList.add(signOff);
						} else if (sdfCompany.getSdfType().getSignOnRecognition().equals(sdfCompany.getCompany().getRecognitionAgreement())) {
							WspSignoff signOff = new WspSignoff(selectedWsp, sdfCompany.getSdf());
							signOff.setSdfType(sdfCompany.getSdfType());
							createList.add(signOff);
						}
					}
					if (createList.size() != 0) {
						for (WspSignoff wspSignoff : createList) {
							wspSignoffService.create(wspSignoff);
						}
					}
					DgVerificationService.instance().clearDgVerification(selectedWsp);
					TasksService.instance().completeTaskByTargetKey(ConfigDocProcessEnum.WSP, selectedWsp.getId());
					break;
//				case DocumentUpload: 2
					case 2:
					selectedWsp.setWspStatusEnum(WspStatusEnum.Pending);
					information = "Grant application status set to Pending Approval for SDF to upload documnets and then for MerSETA to process staff to process. Task to be sent to first user in workflow process for Grant Doucment Rejection. Information changed on a grant application level: "  + informationChangedOnWsp;
					wspService.update(selectedWsp);
					ScheduleChangesLogService.instance().addNewEntry(selectedCompany, null, selectedWsp.getClass().getName(), selectedWsp.getId(), information, true);
					SDFCompany company = sdfCompanyService.locateFirstPrimarySDF(selectedWsp.getCompany());
					List<Users> sdfUsers = new ArrayList<>();
					if (company != null) sdfUsers.add(company.getSdf());
					TasksService.instance().findFirstInProcessAndCreateTask(null, getSessionUI().getActiveUser(), selectedWsp.getId(), Wsp.class.getName(), true, ConfigDocProcessEnum.WSPDocumentUpload, null, sdfUsers);
					break;
//				case InProgress: 3
					case 3:
					selectedWsp.setWspStatusEnum(WspStatusEnum.Pending);
					information = " Grant application status set to Pending Approval for MerSETA staff to process. Task to be sent to first user in workflow process for Grant submission after sign off. Information changed on a grant application level: "  + informationChangedOnWsp;
					wspService.update(selectedWsp);
					ScheduleChangesLogService.instance().addNewEntry(selectedCompany, null, selectedWsp.getClass().getName(), selectedWsp.getId(), information, true);
					// task creation for wsp
					TasksService.instance().completeTaskByTargetKey(ConfigDocProcessEnum.WSP, selectedWsp.getId());
					String message = "Grant Application for " + selectedWsp.getCompany().getCompanyName() + " has been submitted and with a sign off upload please review.";
					TasksService.instance().findFirstInProcessAndCreateTask(message, getSessionUI().getActiveUser(), selectedWsp.getId(), selectedWsp.getClass().getName(), true, ConfigDocProcessEnum.WSP, MailDef.instance(MailEnum.WSPSubmittedAndSignedReview, MailTagsEnum.CompanyName, selectedWsp.getCompany().getCompanyName()), null);
					break;
//				case SdfSignoff:
					case 4:
						WspSignoffService wspSignoffServiceTwo = new WspSignoffService();
						// set the status required for submission
						selectedWsp.setWspStatusEnum(WspStatusEnum.PendingSignOff);
						information = " Grant application status set to Pending Signoff for Primary SDF to sign off. Task(s) to be sent to users required for sign off. Information changed on a grant application level: "  + informationChangedOnWsp;
						wspService.update(selectedWsp);
						ScheduleChangesLogService.instance().addNewEntry(selectedCompany, null, selectedWsp.getClass().getName(), selectedWsp.getId(), information, true);
						TasksService.instance().completeTaskByTargetKey(ConfigDocProcessEnum.WSP, selectedWsp.getId());
						List<Users> userList = new ArrayList<>();
						selectedWsp.setWspSignoffs(wspSignoffServiceTwo.findByWsp(selectedWsp));
						for (WspSignoff wspSignoff : selectedWsp.getWspSignoffs()) {
							if (wspSignoff.getAccept() == null || !wspSignoff.getAccept()) {
								userList.add(wspSignoff.getUser());
							}
						}
						String taskMessage = "Grant Application for " + selectedWsp.getCompany().getCompanyName() + " has been submitted and requires your sign off.";
						TasksService.instance().createTaskEachUser(selectedWsp.getClass().getName(), selectedWsp.getCompany(), taskMessage, "WSP form requires sign off", taskMessage, getSessionUI().getActiveUser(), selectedWsp.getId(), true, true, null, userList, ConfigDocProcessEnum.WSP, MailDef.instance(MailEnum.WSPRequiresSignOff, MailTagsEnum.CompanyName, selectedWsp.getCompany().getCompanyName()));
						DgVerificationService.instance().clearDgVerification(selectedWsp);
					break;
				default:
					break;
				}
				runClientSideExecute("PF('dlgReopenWsp').hide()");
			} else {
				addErrorMessage("Unable to locate selected wsp contact support!");
			}
			createExtensionRequest = false;
			reopenWsp = false;
			addInfoMessage("Wsp Reopened");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			logger.fatal(e);
		}
	}
	
	public void prepareWSP() {
		try {
			this.newWsp = new Wsp();
			this.newWsp.setCompany(selectedCompany);
			this.newWsp.setCreateUsers(getSessionUI().getActiveUser());
			runClientSideExecute("PF('dialogRangeWsp').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepareInterSetaManWSP() {
		try {
			this.newWsp = new Wsp();
			this.newWsp.setCompany(selectedCompany);
			this.newWsp.setCreateUsers(getSessionUI().getActiveUser());
			this.newWsp.setWspType(WspTypeEnum.IntersetaTransferMandGrant);
			runClientSideExecute("PF('dialogIntersetaRangeWsp').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void validatePrimarySdfAssigned() throws Exception{
		List<SDFCompany> primarySDFList = sdfCompanyService.findAllPrimarySDF(selectedCompany);
		if (primarySDFList.size() == 0) {
			throw new Exception("No Primary SDFs assigned to company: " + selectedCompany.getCompanyName() + ". reopen cancelled.");
		}
	}
	
	/**
	 * Gets the prepare fin year.
	 *
	 * @return the prepare fin year
	 */
	public List<Integer> getPrepareFinYear() {
		List<Integer> l = new ArrayList<>();
		Calendar now = Calendar.getInstance();
		l.add(now.get(Calendar.YEAR));
		l.add(now.get(Calendar.YEAR) + 1);
		return l;
	}
	
	public List<SelectItem> getWspTypeEnumDD() {
		List<SelectItem> l = new ArrayList<SelectItem>();
		if (selectedCompany.getNonLevyPaying() != null && selectedCompany.getNonLevyPaying()) {
			l.add(new SelectItem(WspTypeEnum.Discretionary, super.getEntryLanguage(WspTypeEnum.Discretionary.getRegistrationName())));
		} else {
			if (belowThreshold) l.add(new SelectItem(WspTypeEnum.Both, super.getEntryLanguage(WspTypeEnum.Discretionary.getRegistrationName())));
			else l.add(new SelectItem(WspTypeEnum.Both, super.getEntryLanguage(WspTypeEnum.Both.getRegistrationName())));
		}
		return l;
	}
	

	public List<SelectItem> getWspIntersetaTypeEnumDD() {
		List<SelectItem> l = new ArrayList<SelectItem>();
		l.add(new SelectItem(WspTypeEnum.IntersetaTransferMandGrant, super.getEntryLanguage(WspTypeEnum.IntersetaTransferMandGrant.getRegistrationName())));
		return l;
	}
	
	public void initiateWSP() {
		try {
			wspService.checkWspFinYearUnique(this.newWsp);
			wspService.createWithSignOff(this.newWsp);
			selectCompany();
			addInfoMessage("WSP created for Year: " + newWsp.getFinYear());
//			this.newWsp  = null;
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void runWspValidiationCheck(){
		try {
			errors = null;
			wspService.runValidiationCheckFirstSubmit(selectedWsp);
			addInfoMessage("Passed Validiation");
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (ValidationErrorException e) {
			this.errors = e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepareWspForCompanyVersionTwo() {
		try {
			if (selectedCompany != null) {
				
				results = "";
				if (bankingDetailsService == null) {
					bankingDetailsService = new BankingDetailsService();
				}
				// validation if one can raise a wsp on the current year
				results = "1) Count of grant application on NSDMS: " + wspService.countByCompanyAndFinYear(selectedCompany, (finYear + 1)) + " <br/> ";
				canCreateNewWsp = ((wspService.countByCompanyAndFinYear(selectedCompany, (finYear + 1)) == 0) ? true : false);
				results = results + "2) Can initiate grant application for current year: " + canCreateNewWsp + "<br/>";
				// locates company banking details
				bankingDetails = bankingDetailsService.findByCompanyLates(selectedCompany);
				if (bankingDetails != null) {
					results = results + "3) Banking Details Found: " + bankingDetails + "<br/>";
				} else {
					results = results + "3) No Banking Details on record. <br/>";
				}
				
				// locates primary SDF
				primarySDFObject = sdfCompanyService.locateFirstPrimarySDF(selectedCompany);
				if (primarySDFObject != null) {
					results = results + "4) Primary SDF Found: " + primarySDFObject + ", Primary SDF Type ID: "+primarySDFObject.getSdfType().getId()+" (1 is Primary SDF)<br/>";
				} else {
					results = results + "4) No Primary SDF <br/>";
				}
				// identifies if primary SDF
				if (primarySDFObject != null) {
					primarySDF = primarySDFObject.getSdfType().getId() != HAJConstants.EMP_ID && primarySDFObject.getSdfType().getId() != HAJConstants.ALT_EMP_ID && primarySDFObject.getSdfType().getId() != HAJConstants.LAB_ID && primarySDFObject.getSdfType().getId() != HAJConstants.ALT_LAB_ID;
					results = results + "5) Result check of Primary SDF: "+primarySDF+" <br/>";
				} else {
					primarySDF = false;
					results = results + "5) Result check of Primary SDF: "+primarySDF+" <br/>";
				}
				if (getNow().before(GenericUtility.getEndOfApril(getNow()))) {
					canInitiate = bankingDetails != null && primarySDF;
				} else {
					List<ExtensionRequest> extensionRequests = extensionRequestService.findByCompanyNoWSPAssigned(selectedCompany);
					results = results + "6) Extension Requests Found: "+extensionRequests.size()+" <br/>";
					if (extensionRequests.size() > 0 && extensionRequests.get(0).getStatus() == ApprovalEnum.Approved) {
						results = results + "7) Approved Extension Request Submission Date : "+GenericUtility.sdf6.format(extensionRequests.get(0).getNewSubmissionDate())+" <br/>";
						Date endOfMay = GenericUtility.getEndOfDay(GenericUtility.sdf6.parse("31-05-" + GenericUtility.sdfYYYY.format(getNow())));
						if (extensionRequests.get(0).getNewSubmissionDate() != null) {
							closeOffDate = GenericUtility.getEndOfDay(extensionRequests.get(0).getNewSubmissionDate());
							results = results + "8) Close Off Date : "+GenericUtility.sdf6.format(closeOffDate)+" <br/>";
						} else {
							closeOffDate = endOfMay;
							results = results + "8) Close Off Date : "+GenericUtility.sdf6.format(closeOffDate)+" <br/>";
						}
						results = results + "9) Machine time before close off date : "+getNow().before(closeOffDate)+" <br/>";
						canInitiate = getNow().before(closeOffDate) && bankingDetails != null && primarySDF;
						results = results + "10) canInitiate Result : "+canInitiate+" <br/>";
					} else {
						canInitiate = false;
						results = results + "10) canInitiate Result : "+canInitiate+" <br/>";
					}
				}
				super.runClientSideUpdate(":wspInsForm");
				displayValidiationCheck = true;
			}
		} catch (Exception e) {
			displayValidiationCheck = false;
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void downloadGrantApplication() {
		Map<String, Object> params = new HashMap<String, Object>();
		
		try {
			if (sitesService == null) {
				sitesService = new SitesService();
			}
			if (companyUsersService == null) {
				companyUsersService = new CompanyUsersService();
			}
			if (trainingComitteeService == null) {
				trainingComitteeService = new TrainingComitteeService();
			}
			
			
			this.selectedWsp = wspService.findByGuid(selectedWsp.getWspGuid(), getSessionUI().getActiveUser());
			grantCompany = this.selectedWsp.getCompany();

			linkedCompanies = companyService.findLinkedCompany(grantCompany);
			sites = sitesService.findByCompany(grantCompany);
			companyUsers = companyUsersService.findByCompany(grantCompany);
			trainingComittees = trainingComitteeService.findByCompany(grantCompany);

			if (grantCompany.getMajorityUnion() == null) {
				OrganisedLabourUnion majorityUnion = new OrganisedLabourUnion();
				majorityUnion.setDescription("N/A");
				grantCompany.setMajorityUnion(majorityUnion);
			}

			params.put("company", grantCompany);
			params.put("wsp_id", selectedWsp.getId());
			params.put("wsp", selectedWsp);
			// Getting last sign off date
			Date finalSignOffDate = new Date();
			for (WspSignoff wspSignoff : selectedWsp.getWspSignoffs()) {
				finalSignOffDate = wspSignoff.getCreateDate();
			}
			params.put("finalSignOffDate", finalSignOffDate);
			// Adding logo an backround image
			JasperService.addLogo(params);
			// Adding Grant reports
			JasperService.addGrantSubreport(params);

			MandatoryGrantDetailService mandatoryGrantDetailService = new MandatoryGrantDetailService();

			List<EmployeeProfileBean> employeeProfileBeansList = mandatoryGrantDetailService.allEmployeeProfileBean(selectedWsp.getCompany());
			List<EmployeeEquityProfileBean> employeeEquityProfileBeanList = mandatoryGrantDetailService.allEmployeeEquityProfileBean(selectedWsp.getCompany());
			List<EmpEmploymentStatusBean> empEmploymentStatusBeanList = mandatoryGrantDetailService.allEmpEmploymentStatusBean(selectedWsp.getCompany());
			List<ATRReportSummayBean> atrReportSummayBeanList = mandatoryGrantDetailService.allATRReportSummayBean(selectedWsp,WspReportEnum.ATR, HAJConstants.DISC_FUNDING_ID);
			List<WSPReportSummayBean> wspReportSummayBeanList = mandatoryGrantDetailService.allWSPReportSummayBean(selectedWsp,WspReportEnum.WSP, HAJConstants.DISC_FUNDING_ID);
			List<PivotalTrainingReportBean> pivotalTrainingTrained = mandatoryGrantDetailService.allPivotalTrainingReport(selectedWsp, WspReportEnum.ATR, HAJConstants.YES_ID);
			List<PivotalTrainingReportBean> pivotalTrainingPlaned = mandatoryGrantDetailService.allPivotalTrainingReportWSP(selectedWsp, WspReportEnum.WSP, HAJConstants.YES_ID);
			List<DGApplicationSummaryBean> dgApplicationSummaryBean = mandatoryGrantDetailService.allDGApplicationSummaryBean(selectedWsp, WspReportEnum.WSP, HAJConstants.DISC_FUNDING_ID);
			WspSkillsRequirementsService wspSkillsRequirementsService = new WspSkillsRequirementsService();
			List<WspSkillsRequirements> wspSkillsRequirementsList = wspSkillsRequirementsService.findByWsp(selectedWsp);
			params.put("EmployeeProfileDataSource", new JRBeanCollectionDataSource(employeeProfileBeansList));
			params.put("EmployeeEquityDataSource", new JRBeanCollectionDataSource(employeeEquityProfileBeanList));
			params.put("ATRDataSource", new JRBeanCollectionDataSource(atrReportSummayBeanList));
			for (int x = 0; x < companyUsers.size(); x++) {
				CompanyUsers comUsers = companyUsers.get(x);
				if (comUsers.getCompanyUserType() != null) {
					companyUsers.remove(x);
					String type = super.getEntryLanguage(comUsers.getCompanyUserType().getType());
					if (comUsers.getCompanyUserType() == ConfigDocProcessEnum.SDF) {
						SDFCompany sdfCompany = SDFCompanyService.instance().findByCompanyAndUser(grantCompany,
								comUsers.getUser());
						if (sdfCompany != null) {
							type = sdfCompany.getSdfType().getDescription();
						}

					}
					comUsers.setCompanyUserTypeDes(type);
					companyUsers.add(x, comUsers);
				}
			}

			params.put("CompanyUsersDataSource", new JRBeanCollectionDataSource(companyUsers));
			params.put("TrainingCommDataSource", new JRBeanCollectionDataSource(trainingComittees));
			params.put("LinkedCompDataSource", new JRBeanCollectionDataSource(linkedCompanies));
			params.put("CompSiteDataSource", new JRBeanCollectionDataSource(sites));
			params.put("WspSkillsRequirementsListDataSource", new JRBeanCollectionDataSource(wspSkillsRequirementsList));
			params.put("WspSignoffsDataSource", new JRBeanCollectionDataSource(selectedWsp.getWspSignoffs()));
			params.put("EmpEmploymentStatusListDataSource", new JRBeanCollectionDataSource(empEmploymentStatusBeanList));
			params.put("wspReportSummayBeanListDataSource", new JRBeanCollectionDataSource(wspReportSummayBeanList));

			params.put("PivotalTrainingTrainedDataSource", new JRBeanCollectionDataSource(pivotalTrainingTrained));
			params.put("PivotalTrainingPlanedDataSource", new JRBeanCollectionDataSource(pivotalTrainingPlaned));
			params.put("DGApplicationSummaryBeanDataSource", new JRBeanCollectionDataSource(dgApplicationSummaryBean));

			JasperService.instance().createReportFromDBtoServletOutputStream("GRANT_APPLICATION.jasper", params, grantCompany.getLevyNumber() + "_Grant_Application.pdf");

		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void prepNotificationEmail(){
		try {
			selectedRejectionReasons = new ArrayList<>();
			selectedWsp = wspService.findByKey(selectedWsp.getId());
			selectedNumber = 1;
			sendToSdf = false;
			email = "";
			runClientSideExecute("PF('dlgWspNotification').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public List<RejectReasons> getRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.WSP);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public void sendNotification(){
		try {
			if (!sendToSdf) {
				boolean dateWasNull = false;
				if (email == null || email.isEmpty()) {
					throw new Exception("Provide email before sending");
				}
				if (selectedNumber == 3 || selectedNumber == 4) {
					if (selectedRejectionReasons == null || selectedRejectionReasons.size() == 0) {
						throw new Exception("Provide Rejection Reasons Before Proceeding");
					}
				}
				if (selectedNumber == 4){
					if (selectedWsp.getSdfAppealedGrantDate() == null) {
						dateWasNull = true;
						selectedWsp.setSdfAppealedGrantDate(getNow());
					}
				}
				wspService.sendReleventNotificationToUsersAdmin(selectedWsp, selectedNumber, selectedRejectionReasons, email.trim(), false);
				if (dateWasNull) {
					selectedWsp.setSdfAppealedGrantDate(null);
				}
			} else {
				email = "";
				selectedRejectionReasons = null;
				wspService.sendReleventNotificationToUsersAdmin(selectedWsp, selectedNumber, selectedRejectionReasons, null, true);
			}
			sendToSdf = false;
			selectedNumber = 1;
			selectedWsp = null;
			email = "";
			addInfoMessage("Action Complete");
			runClientSideExecute("PF('dlgWspNotification').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/*
	public void saveWSP() {
		try {
			if (getNow().before(closeOffDate)) {
				wspService.submitWSP(wsp, getSessionUI().getActiveUser(), true);
			} else {
				wspService.saveWSP(wsp, getSessionUI().getActiveUser(), true);
			}
			addInfoMessage(super.getEntryLanguage("update.successful"));

			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (ValidationErrorException e) {
			this.errors = e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	 * 
	 */
	
	

	/* getters and setters */
	public Company getSelectedCompany() {
		return selectedCompany;
	}

	public void setSelectedCompany(Company selectedCompany) {
		this.selectedCompany = selectedCompany;
	}

	public Wsp getSelectedWsp() {
		return selectedWsp;
	}

	public void setSelectedWsp(Wsp selectedWsp) {
		this.selectedWsp = selectedWsp;
	}

	public ExtensionRequest getExtensionRequest() {
		return extensionRequest;
	}

	public void setExtensionRequest(ExtensionRequest extensionRequest) {
		this.extensionRequest = extensionRequest;
	}

	public LazyDataModel<Company> getDataModelComapny() {
		return dataModelComapny;
	}

	public void setDataModelComapny(LazyDataModel<Company> dataModelComapny) {
		this.dataModelComapny = dataModelComapny;
	}

	public List<ExtensionRequest> getExtensionRequestsByWsp() {
		return extensionRequestsByWsp;
	}

	public void setExtensionRequestsByWsp(List<ExtensionRequest> extensionRequestsByWsp) {
		this.extensionRequestsByWsp = extensionRequestsByWsp;
	}

	public List<Wsp> getCompanyWspList() {
		return companyWspList;
	}

	public void setCompanyWspList(List<Wsp> companyWspList) {
		this.companyWspList = companyWspList;
	}

	public List<ExtensionRequest> getExtensionRequestsByCompany() {
		return extensionRequestsByCompany;
	}

	public void setExtensionRequestsByCompany(List<ExtensionRequest> extensionRequestsByCompany) {
		this.extensionRequestsByCompany = extensionRequestsByCompany;
	}

	public boolean isCreateExtensionRequest() {
		return createExtensionRequest;
	}

	public void setCreateExtensionRequest(boolean createExtensionRequest) {
		this.createExtensionRequest = createExtensionRequest;
	}

	public boolean isReopenWsp() {
		return reopenWsp;
	}

	public void setReopenWsp(boolean reopenWsp) {
		this.reopenWsp = reopenWsp;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public boolean isBelowThreshold() {
		return belowThreshold;
	}

	public void setBelowThreshold(boolean belowThreshold) {
		this.belowThreshold = belowThreshold;
	}

	public Wsp getNewWsp() {
		return newWsp;
	}

	public void setNewWsp(Wsp newWsp) {
		this.newWsp = newWsp;
	}

	public Integer getSelectedNumber() {
		return selectedNumber;
	}

	public void setSelectedNumber(Integer selectedNumber) {
		this.selectedNumber = selectedNumber;
	}

	public List<Integer> getIntList() {
		return intList;
	}

	public void setIntList(List<Integer> intList) {
		this.intList = intList;
	}

	public String getErrors() {
		return errors;
	}

	public void setErrors(String errors) {
		this.errors = errors;
	}

	public BankingDetails getBankingDetails() {
		return bankingDetails;
	}

	public void setBankingDetails(BankingDetails bankingDetails) {
		this.bankingDetails = bankingDetails;
	}

	public boolean isCanCreateNewWsp() {
		return canCreateNewWsp;
	}

	public void setCanCreateNewWsp(boolean canCreateNewWsp) {
		this.canCreateNewWsp = canCreateNewWsp;
	}

	public Integer getFinYear() {
		return finYear;
	}

	public void setFinYear(Integer finYear) {
		this.finYear = finYear;
	}

	public boolean isPrimarySDF() {
		return primarySDF;
	}

	public void setPrimarySDF(boolean primarySDF) {
		this.primarySDF = primarySDF;
	}

	public SDFCompany getPrimarySDFObject() {
		return primarySDFObject;
	}

	public void setPrimarySDFObject(SDFCompany primarySDFObject) {
		this.primarySDFObject = primarySDFObject;
	}

	public boolean isCanInitiate() {
		return canInitiate;
	}

	public void setCanInitiate(boolean canInitiate) {
		this.canInitiate = canInitiate;
	}

	public String getResults() {
		return results;
	}

	public void setResults(String results) {
		this.results = results;
	}

	public boolean isDisplayValidiationCheck() {
		return displayValidiationCheck;
	}

	public void setDisplayValidiationCheck(boolean displayValidiationCheck) {
		this.displayValidiationCheck = displayValidiationCheck;
	}

	public Company getGrantCompany() {
		return grantCompany;
	}

	public void setGrantCompany(Company grantCompany) {
		this.grantCompany = grantCompany;
	}

	public boolean isSendToSdf() {
		return sendToSdf;
	}

	public void setSendToSdf(boolean sendToSdf) {
		this.sendToSdf = sendToSdf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<RejectReasons> getSelectedRejectionReasons() {
		return selectedRejectionReasons;
	}

	public void setSelectedRejectionReasons(List<RejectReasons> selectedRejectionReasons) {
		this.selectedRejectionReasons = selectedRejectionReasons;
	}
	
}