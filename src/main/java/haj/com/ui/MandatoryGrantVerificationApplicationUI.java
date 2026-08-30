package haj.com.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Company;
import haj.com.entity.MgVerification;
import haj.com.entity.MgVerificationDetails;
import haj.com.entity.Signoff;
import haj.com.entity.Sites;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.UsersStatusEnum;
import haj.com.entity.lookup.AuditorMonitorReview;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyService;
import haj.com.service.CompanyUsersService;
import haj.com.service.HostingCompanyEmployeesService;
import haj.com.service.MandatoryGrantDetailService;
import haj.com.service.MandatoryGrantRecommendationService;
import haj.com.service.MandatoryGrantService;
import haj.com.service.MgVerificationDetailsService;
import haj.com.service.MgVerificationService;
import haj.com.service.SignoffService;
import haj.com.service.SitesService;
import haj.com.service.TasksService;
import haj.com.service.UsersService;
import haj.com.service.WspService;
import haj.com.service.YesNoLookupService;
import haj.com.service.lookup.EtqaService;
import haj.com.utils.GenericUtility;

//TODO: Auto-generated Javadoc
/**
 * The Class MandatoryGrantVerificationUI.
 */
@ManagedBean(name = "mandatoryGrantVerificationapplicationUI")
@ViewScoped
public class MandatoryGrantVerificationApplicationUI extends AbstractUI {

	/** Entity Layer */
	private MgVerification mgVerification;
	private MgVerificationDetails mgVerificationDetails;
	private Company company;
	private Date today = GenericUtility.getStartOfDay(new Date());
	private Signoff signOffSelected = null;
	private Users currentSignOffUser = null;
	private Users userSelectionForMGSignOff = null;

	/** Service Layer */
	private MandatoryGrantDetailService mandatoryGrantDetailService = new MandatoryGrantDetailService();
	private CompanyService companyService = new CompanyService();
	private CompanyUsersService companyUsersService = new CompanyUsersService();
	private MgVerificationService mgVerificationService = new MgVerificationService();
	private MgVerificationDetailsService mgVerificationDetailsService = new MgVerificationDetailsService();
	private HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
	private SignoffService signoffService = new SignoffService();
	private UsersService usersService = new UsersService();
	private SitesService sitesService = new SitesService();
	
	/** Lists */	
	private LazyDataModel<MgVerification> mgVerificationDataModel;
	private LazyDataModel<MgVerificationDetails> dataModel = null;
	private List<Signoff> signOffLists = new ArrayList<>();
	private List<Users> signOffUserSelectionList = new ArrayList<>();
	private List<Sites> sites;

	/** Boolean */
	private Boolean disableEdit = false;
	private Boolean disableSignOffButton;
	private Boolean selectedCompanyClo = false;
	private Boolean cloUser = false;
	private Boolean crmUser = false;
	private boolean selected = false;
	private boolean filterByNotStarted = false;
	private boolean readOnly = true;
	private boolean alterInfo = false;
	private boolean displayChangeUser = false;



	
	List<MgVerificationDetails> details = new ArrayList<>();
	
	/**
	 * Inits the.
	 */
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
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.MG_VERIFICATION && super.getParameter("id", false) != null) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			mgVerification = mgVerificationService.findByKey(getSessionUI().getTask().getTargetKey());
			selctemgVerification();
			populateSignOffs();
		} else {
			disableEdit = false;
			if (hostingCompanyEmployeesService.countCloByUserId(getSessionUI().getActiveUser()) == 0) {
				cloUser = false;
				crmUser = false;
			} else {
				cloUser = true;
				crmUser = false;
			}
			
			if (hostingCompanyEmployeesService.countCrmByUserId(getSessionUI().getActiveUser()) == 0) {
				crmUser = false;
			}else {
				cloUser = false;
				crmUser = true;
			}
			mgVerificationInfo();
		}
	}

	/**
	 * Lazy load for wsp
	 * 
	 * @throws Exception
	 */
	private void mgVerificationInfo() throws Exception {
		mgVerificationDataModel = new LazyDataModel<MgVerification>() {
			private static final long serialVersionUID = 1L;
			private List<MgVerification> retorno = new ArrayList<MgVerification>();
			
			@Override
			public List<MgVerification> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if(filters == null) {
						filters = new HashMap<>();
					}
					if (cloUser) {
						filters.put("userID", getSessionUI().getActiveUser().getId());
						if (filterByNotStarted) {
							retorno = mgVerificationService.allMgVerificationByCloNotStarted(first, pageSize, sortField, sortOrder, filters);					
							mgVerificationDataModel.setRowCount(mgVerificationService.countAllMgVerificationByCloNotStarted(filters));
						} else {
							retorno = mgVerificationService.allMgVerificationByClo(first, pageSize, sortField, sortOrder, filters);					
							mgVerificationDataModel.setRowCount(mgVerificationService.countAllMgVerificationByClo(filters));
						}
					} else if (crmUser){
						filters.put("userID", getSessionUI().getActiveUser().getId());
						if (filterByNotStarted) {
							retorno = mgVerificationService.allMgVerificationByCrmNotStarted(first, pageSize, sortField, sortOrder, filters);					
							mgVerificationDataModel.setRowCount(mgVerificationService.countAllMgVerificationByCrmNotStarted(filters));
						} else {
							retorno = mgVerificationService.allMgVerificationByCrm(first, pageSize, sortField, sortOrder, filters);					
							mgVerificationDataModel.setRowCount(mgVerificationService.countAllMgVerificationByCrm(filters));
						}
					} else {
						if (filterByNotStarted) {
							retorno = mgVerificationService.allMgVerificationForManagersNotStarted(first, pageSize, sortField, sortOrder, filters);					
							mgVerificationDataModel.setRowCount(mgVerificationService.countAllMgVerificationForManagersNotStarted(filters));
						} else {
							retorno = mgVerificationService.allMgVerificationForManagers(first, pageSize, sortField, sortOrder, filters);					
							mgVerificationDataModel.setRowCount(mgVerificationService.countForManagers(filters));
						}
					}
				} catch (Exception e) {
					logger.fatal(e);
					e.printStackTrace();
				}
				return retorno;
			}

			@Override
			public Object getRowKey(MgVerification obj) {
				return obj.getId();
			}

			@Override
			public MgVerification getRowData(String rowKey) {
				for (MgVerification obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
			
		};		
	}
	
	public void applyFilter(){
		try {
			mgVerificationInfo();
			mgVerification = null;
			selected = false;
			if (filterByNotStarted) {
				addInfoMessage("Filter Applied");
			} else {
				addInfoMessage("Filter Removed");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}
	
	private void mgVerificationDetailsInfoWsp() throws Exception {
//		company = companyService.findByKey(mgVerification.getWsp().getCompany().getId());
//		sites = sitesService.findByCompany(company);
//		details = mgVerificationDetailsService.allMgVerificationDetailsWsp(mgVerification.getWsp());	
		/* Version Two Display */
//		details = mgVerificationDetailsService.allMgVerificationDetailsWsp(mgVerification.getWsp());		
	}
	
	private void pivitolInfoWsp() throws Exception {
		company = companyService.findByKey(mgVerification.getWsp().getCompany().getId());
//		sites = sitesService.findByCompany(company);
		dataModel = new LazyDataModel<MgVerificationDetails>() {

			private static final long serialVersionUID = 1L;
			private List<MgVerificationDetails> retorno = new ArrayList<MgVerificationDetails>();

			@Override
			public List<MgVerificationDetails> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
//					retorno = mgVerificationDetailsService.allMgVerificationDetails(MgVerificationDetails.class, first, pageSize, sortField, sortOrder, filters, mgVerification.getWsp());
//					dataModel.setRowCount(mgVerificationDetailsService.count(MgVerificationDetails.class, filters, mgVerification.getWsp()));
					
					retorno = mgVerificationDetailsService.allMgVerificationDetailsByWspId(MgVerificationDetails.class, first, pageSize, sortField, sortOrder, filters, mgVerification.getWsp());
					dataModel.setRowCount(mgVerificationDetailsService.countByWspId(MgVerificationDetails.class, filters, mgVerification.getWsp()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				return retorno;
			}

			@Override
			public Object getRowKey(MgVerificationDetails obj) {
				return obj.getId();
			}

			@Override
			public MgVerificationDetails getRowData(String rowKey) {
				for (MgVerificationDetails obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};
	}
	
	public List<Company> completeCompany(String desc) {
		CompanyService equityService = new CompanyService();
		List<Company> l = null;
		try {
			l = equityService.findByNameOrLevyNum(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public void submitMGVerification() {
		try {
			boolean signoffMissing = false;
			for (Signoff signoff : signOffLists) {
				if (signoff.getUser() == null) {
					signoffMissing = true;
					break;
				}
			}
			if (signoffMissing) {
				throw new Exception("Please provide user required for signoff");
			}
			
//			int count = mgVerificationDetailsService.findCountByWspId(mgVerification.getWsp());
			int count = mgVerificationDetailsService.countEntriesNotPopulatedByWspId(mgVerification.getWsp());
			if(count > 0) {
				throw new Exception("Please provide required information before proceeding");
			}
			
			count = mgVerificationDetailsService.countByWspIdWhereEvidanceRequired(mgVerification.getWsp());
			if (count > 0) {
				mgVerificationService.submitMgVerificationToSdf(getSessionUI().getActiveUser(), this.mgVerification, getSessionUI().getTask(), true);
			} else {
				mgVerificationService.submitMgVerificationToSdf(getSessionUI().getActiveUser(), this.mgVerification, getSessionUI().getTask(), false);
			}
			
			prepareNew();
			mgVerificationInfo();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			/*if(checkFieldsprovided(details)) {
		
			}else {
				addErrorMessage("Please provide required information");
			}*/
			//super.ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}
	
	public void updateMGVerification() {
		try {			
			mgVerificationDetailsService.checkValues(mgVerificationDetails);
//			mgVerificationDetails.setEvidanceRequired(mgVerificationDetailsService.determanEvidenceRequired(mgVerificationDetails));
			mgVerificationDetailsService.update(mgVerificationDetails);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			mgVerificationDetails = null;
			selected = true;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}
	
	public void updateMGVerificationDialog() {
		try {			
			mgVerificationDetailsService.checkValues(mgVerificationDetails);
			mgVerificationDetailsService.update(mgVerificationDetails);
			runClientSideExecute("PF('dlgInformation').hide()");
			addInfoMessage(super.getEntryLanguage("update.successful"));
			mgVerificationDetails = null;
			pivitolInfoWsp();
			selected = true;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}
	
	public void closeDialog(){
		try {
			mgVerificationDetails = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void checkValues(MgVerificationDetails mg) throws Exception {
		//String error = "";
		if(mg.getNoLearnersStarted() == null || mg.getNoLearnersWithdrawn()== null || mg.getNoLearnersCompleted()== null || mg.getActionPlan()== null) {
			throw new Exception("Please provide required information");
		}else {	
			if(mg.getNoLearnersStarted() < 0) {
				throw new Exception("Number of Learners Started cannot be less than zero");
			}
			if(mg.getNoLearnersWithdrawn()< 0) {
				throw new Exception("Number of Learners Withdrawn cannot be less than zero");
			}
			if(mg.getNoPlannedLearners()< 0) {
				throw new Exception("Number of Learners Planned cannot be less than zero");
			}
			if(mg.getNoLearnersCompleted()< 0) {
				throw new Exception("Number of Learners Completed cannot be less than zero");
			}
			if(mg.getNoLearnersCompleted() > mg.getNoLearnersStarted()) {
				throw new Exception( "Number of Learners Completed cannot be greater than Number of Learners Started");
			}
			if(mg.getNoLearnersWithdrawn() > mg.getNoLearnersStarted()) {
				throw new Exception("Number of Learners Withdrawn cannot be greater than Number of Learners Started");
			}
			if(mg.getNoLearnersCompleted() + mg.getNoLearnersWithdrawn() >mg.getNoLearnersStarted()) {
				throw new Exception("Number of Learners Completed with Number of Learners Withdrawn  cannot be greater than Number of Learners Started");
			}
		}
	}
	
	
	
	private boolean checkFieldsprovided(MgVerificationDetails mg) {	
		boolean isProvided = false;
		
		if( mg.getNoLearnersStarted() == null || mg.getNoLearnersWithdrawn()== null || mg.getNoLearnersCompleted()== null || mg.getActionPlan()== null) {
			isProvided = false;
		}else {			
			isProvided = true;
		}
				
		return isProvided;
	}
	private boolean checkFieldsprovided(List<MgVerificationDetails> list) {	
		boolean isProvided = true;
		if(list!= null) {
			for(MgVerificationDetails mg : list) {
				if(mg.getNoLearnersStarted() == null || mg.getNoLearnersWithdrawn()== null || mg.getNoLearnersCompleted()== null || mg.getActionPlan()== null) {
					isProvided = false;
					break;
				}
			}
		}		
		return isProvided;
	}
	
	public void uploadMGVerificationEvidence(){
		try {
			mgVerificationService.uploadMGVerificationEvidence(getSessionUI().getActiveUser(), mgVerification, getSessionUI().getTask());
			prepareNew();
			super.ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}
	
	
	public void cancel(){
		try {			
			prepareNew();
			dataModel=null;
			this.company =null;
			selected = false;			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}
	
	public void createMGVerificationInformation() {
		try {
			mandatoryGrantDetailService.createMGVerificationInformation();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void prepareNew() {
		mgVerification = null;
		dataModel= null;
		selected = false;
	}

	public void setVisitDateOnWorkplaceApproval() {
		mgVerification.setDateForVisitProvided(true);
		try {
			
			mgVerificationService.update(mgVerification);
			disableEdit = checkDate(mgVerification);
			mgVerificationService.validiationBeforeMailNotification(1, mgVerification);
			mgVerificationService.sendEmailNotification(1, mgVerification, null);
			//mgVerificationDetailsInfoWsp();
			//selctemgVerification();
			//runInit();
			addInfoMessage("Update Succesful");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepVisitDateOnWorkplaceApproval() {
		
	}

	/**
	 * When a wsp is selected locates all mandatory grants by wsp company
	 */
	public void selctemgVerification() {
		try {
			identifyIfCompanyClo(mgVerification);
//			mandatoryGrantDetailService.generateDetail(mgVerification);
			dataModel = null;
			this.company = companyService.findByKey(mgVerification.getWsp().getCompany().getId());
			selected = true;
			locateWspMgVerification();
			pivitolInfoWsp();
			mgVerificationDetailsInfoWsp();
			populateSignOffs();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}


	private void identifyIfCompanyClo(MgVerification mgVerification2) throws Exception{
		if (mgVerification != null && mgVerification.getCloGeneratedFor() != null && mgVerification.getCloGeneratedFor().getId().equals(getSessionUI().getActiveUser().getId())) {
			selectedCompanyClo = true;
			if (mgVerification.getStatus() == null) {
				readOnly = false;
			} else {
				readOnly = true;
			}
		} else {
			selectedCompanyClo = false;
			readOnly = true;
		}
	}

	/**
	 * locates MG verification by WSP
	 * 
	 * @throws Exception
	 */
	private void locateWspMgVerification() throws Exception {
		mgVerification = mgVerificationService.findByWspId(mgVerification.getWsp());
		
		if (mgVerification == null || mgVerification.getId() == null) {
			mgVerification = new MgVerification();
			mgVerification.setWsp(mgVerification.getWsp());
		}
		disableEdit = checkDate(this.mgVerification);
//		this.mgVerification = signoffService.resolveMgSignOff(mgVerification);
//			mgVerification.getSignOffs().forEach(sign -> {
//			if (disableSignOffButton == null || !disableSignOffButton) {
//				disableSignOffButton = sign.getUser().equals(getSessionUI().getUser()) && (sign.getCompleted() == null || !sign.getCompleted());
//			}
//		});
	}
	
	public boolean checkDate(MgVerification verification) throws ParseException {
		boolean edit = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	
		if(verification != null && verification.getReviewDate() != null) {
			String strDate1=sdf.format(verification.getReviewDate());
			String strDate2=sdf.format(getNow());			
			Date meetingDate=sdf.parse(strDate1);
			Date currentDate=sdf.parse(strDate2);
			if (meetingDate.equals(currentDate)) {
				edit = true;
			}
		}
		return edit;
	}

	/**
	 * Create / Update MG Verification submit button and relocate MG Verification by
	 * WSP
	 */
	public void updateMgVerification() {
		try {
			updateMg();
			locateWspMgVerification();
			addInfoMessage("Update Succesful");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	


	public void signOffVerification() {
		try {
			if (mgVerification.getDateCaptured() == null) {
				mgVerification.setDateCaptured(new Date());
			}
			mgVerificationService.saveSignOff(mgVerification, getSessionUI().getActiveUser(), getSessionUI().getTask());
			locateWspMgVerification();
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create / Update MG Verification
	 * 
	 * @throws Exception
	 */
	private void updateMg() {
		try {
			if (mgVerification.getDateCaptured() == null) {
				mgVerification.setDateCaptured(new Date());
			}
			mgVerificationService.createWithSignOff(mgVerification, getSessionUI().getActiveUser(), getSessionUI().getTask());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void generateEmployeesEmployed() {
		try {
			addInfoMessage(getEntryLanguage("update.successful"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void prepUpdateToInfo(){
		try {
			alterInfo = true;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepViewForInformation(){
		try {
			alterInfo = false;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void populateSignOffs() throws Exception{
		if (mgVerification != null && mgVerification.getId() != null) {
			signOffLists = signoffService.findByVerifivcation(mgVerification);
		}
	}
	
	public void prepChangeSignOffUser(){
		try {
			if (company != null && company.getId() != null) {
				signOffUserSelectionList = companyUsersService.findUsersByCompany(company);
			} else {
				signOffUserSelectionList = companyUsersService.findUsersByCompany(mgVerification.getWsp().getCompany());
			}
			if (signOffSelected.getUser() != null && signOffSelected.getUser().getId() != null) {
				currentSignOffUser = usersService.findByKey(signOffSelected.getUser().getId());
			}
			userSelectionForMGSignOff = null;
			displayChangeUser = true;
			runClientSideExecute("PF('changeSignOffUserDlg').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void changeSignOffUser(){
		try {
			if (userSelectionForMGSignOff != null) {
				// validate sign off
				if (userSelectionForMGSignOff.getStatus() != UsersStatusEnum.Active || userSelectionForMGSignOff.getLastLogin() == null) {
					if (userSelectionForMGSignOff.getStatus() == UsersStatusEnum.InActive) {
						throw new Exception("Selected User Is In-Active, Please Select A Different User For Sign Off");
					} else if (userSelectionForMGSignOff.getStatus() == UsersStatusEnum.EmailNotConfrimed) {
						throw new Exception("Selected User Has Not Confirmed Their Email Address, Please Select A Different User For Sign Off");
					} else {
						throw new Exception("Selected User Has Not Completed First Time Log In, Please Select A Different User For Sign Off");
					}
				}
				signOffSelected.setAccept(false);
				signOffSelected.setSignOffDate(null);
				signOffSelected.setUser(userSelectionForMGSignOff);
				signOffSelected.setDateSignOffUserChanged(getNow());
				signOffSelected.setExpiryDate(null);
				signOffSelected.setOneTimePassword(null);
				signOffSelected.setChangeUser(getSessionUI().getActiveUser());
				signoffService.update(signOffSelected);
				mgVerification.setPrimaryUserSignOff(userSelectionForMGSignOff);
				mgVerificationService.update(mgVerification);
				addInfoMessage("Sign Off User Assigned");
				runClientSideExecute("PF('changeSignOffUserDlg').hide()");
				populateSignOffs();
				displayChangeUser = false;
			} else {
				addErrorMessage("Select A User For MOA Sign Off");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void signOff(){
		try {
			displayChangeUser = false;
			if (signOffSelected.getAccept()) {
				signOffSelected.setSignOffDate(getNow());
				signoffService.update(signOffSelected);
				signOffSelected = null;
				addInfoMessage("Sign Off Complete");
				runClientSideExecute("PF('signOffDlg').hide()");
				populateSignOffs();
			} else {
				addErrorMessage("Please Accept Acknowledgement Before Signing Off");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/* Getters and setters */
	public Boolean getDisableEdit() {
		return disableEdit;
	}

	public void setDisableEdit(Boolean disableEdit) {
		this.disableEdit = disableEdit;
	}

	public MgVerification getMgVerification() {
		return mgVerification;
	}

	public void setMgVerification(MgVerification mgVerification) {
		this.mgVerification = mgVerification;
	}

	public Boolean getDisableSignOffButton() {
		return disableSignOffButton;
	}

	public void setDisableSignOffButton(Boolean disableSignOffButton) {
		this.disableSignOffButton = disableSignOffButton;
	}

	public LazyDataModel<MgVerification> getMgVerificationDataModel() {
		return mgVerificationDataModel;
	}

	public void setMgVerificationDataModel(LazyDataModel<MgVerification> mgVerificationDataModel) {
		this.mgVerificationDataModel = mgVerificationDataModel;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Date getToday() {
		return today;
	}

	public void setToday(Date today) {
		this.today = today;
	}

	public LazyDataModel<MgVerificationDetails> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<MgVerificationDetails> dataModel) {
		this.dataModel = dataModel;
	}

	public MgVerificationDetails getMgVerificationDetails() {
		return mgVerificationDetails;
	}

	public void setMgVerificationDetails(MgVerificationDetails mgVerificationDetails) {
		this.mgVerificationDetails = mgVerificationDetails;
	}

	public List<MgVerificationDetails> getDetails() {
		return details;
	}

	public void setDetails(List<MgVerificationDetails> details) {
		this.details = details;
	}

	public Boolean getSelectedCompanyClo() {
		return selectedCompanyClo;
	}

	public void setSelectedCompanyClo(Boolean selectedCompanyClo) {
		this.selectedCompanyClo = selectedCompanyClo;
	}

	public boolean isFilterByNotStarted() {
		return filterByNotStarted;
	}

	public void setFilterByNotStarted(boolean filterByNotStarted) {
		this.filterByNotStarted = filterByNotStarted;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public boolean isAlterInfo() {
		return alterInfo;
	}

	public void setAlterInfo(boolean alterInfo) {
		this.alterInfo = alterInfo;
	}

	public List<Users> getSignOffUserSelectionList() {
		return signOffUserSelectionList;
	}

	public void setSignOffUserSelectionList(List<Users> signOffUserSelectionList) {
		this.signOffUserSelectionList = signOffUserSelectionList;
	}

	public Users getCurrentSignOffUser() {
		return currentSignOffUser;
	}

	public void setCurrentSignOffUser(Users currentSignOffUser) {
		this.currentSignOffUser = currentSignOffUser;
	}

	public Users getUserSelectionForMGSignOff() {
		return userSelectionForMGSignOff;
	}

	public void setUserSelectionForMGSignOff(Users userSelectionForMGSignOff) {
		this.userSelectionForMGSignOff = userSelectionForMGSignOff;
	}

	public List<Signoff> getSignOffLists() {
		return signOffLists;
	}

	public void setSignOffLists(List<Signoff> signOffLists) {
		this.signOffLists = signOffLists;
	}

	public Signoff getSignOffSelected() {
		return signOffSelected;
	}

	public void setSignOffSelected(Signoff signOffSelected) {
		this.signOffSelected = signOffSelected;
	}

	public boolean isDisplayChangeUser() {
		return displayChangeUser;
	}

	public void setDisplayChangeUser(boolean displayChangeUser) {
		this.displayChangeUser = displayChangeUser;
	}

	public Boolean getCrmUser() {
		return crmUser;
	}

	public void setCrmUser(Boolean crmUser) {
		this.crmUser = crmUser;
	}
}
