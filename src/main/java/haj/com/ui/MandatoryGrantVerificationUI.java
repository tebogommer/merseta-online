package haj.com.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.omnifaces.util.Faces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.entity.Company;
import haj.com.entity.Doc;
import haj.com.entity.MandatoryGrant;
import haj.com.entity.MgVerification;
import haj.com.entity.MgVerificationDetails;
import haj.com.entity.Signoff;
import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.PivotNonPivotEnum;
import haj.com.entity.enums.WspReportEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyService;
import haj.com.service.DocService;
import haj.com.service.MandatoryGrantService;
import haj.com.service.MgVerificationDetailsService;
import haj.com.service.MgVerificationService;
import haj.com.service.SignoffService;
import haj.com.service.TasksService;
import haj.com.service.UsersService;
import haj.com.service.WspService;
import haj.com.service.YesNoLookupService;
import haj.com.service.lookup.EtqaService;
import haj.com.service.lookup.RejectReasonsService;

//TODO: Auto-generated Javadoc
/**
 * The Class MandatoryGrantVerificationUI.
 */
@ManagedBean(name = "mandatoryGrantVerificationUI")
@ViewScoped
public class MandatoryGrantVerificationUI extends AbstractUI {

	/** Entity Layer */
	private MandatoryGrant mandatoryGrant;
	private WspReportEnum wspReport;
	private Company company;
	private Wsp wsp;
	private MgVerification mgVerification;
	private MgVerificationDetails mgVerificationDetails;
	private Signoff signOffSelected = null;
	private Users currentSignOffUser = null;
	private Users userSelectionForMGSignOff = null;
	
	/** The doc. */
	private Doc doc;

	/** Service Layer */
	private EtqaService etqaService = new EtqaService();
	private YesNoLookupService yesNoService = new YesNoLookupService();
	private MandatoryGrantService mandatoryGrantService = new MandatoryGrantService();
	private WspService wspService = new WspService();
	private CompanyService companyService = new CompanyService();
	private MgVerificationService mgVerificationService = new MgVerificationService();
	private MgVerificationDetailsService mgVerificationDetailsService = new MgVerificationDetailsService();
	private SignoffService signoffService = new SignoffService();
	
	/** Lists */
	private List<MandatoryGrant> mandatoryGrants;
	private LazyDataModel<MandatoryGrant> dataModel;
	private LazyDataModel<Company> companyDataModel;
	private LazyDataModel<Wsp> wspDataModel;
	private List<Wsp> wspList;
	private LazyDataModel<MgVerificationDetails> dataModelMgDetails = null;
	private ArrayList<RejectReasons> selectedRejectReason=new ArrayList<>();
	private List<RejectReasons> rejectReason=new ArrayList<>();
	private List<Signoff> signOffLists = new ArrayList<>();
	private List<Users> signOffUserSelectionList = new ArrayList<>();
//	List<MgVerificationDetails> details = new ArrayList<>();

	/** Boolean */
	private Boolean disableEdit;
	private Boolean disableSignOffButton;
	private boolean alterInfo = false;

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
			mgVerificationDetailsService.prepareNewRegistration(getSessionUI().getTask().getWorkflowProcess(), mgVerification, getSessionUI().getTask().getProcessRole());
			this.wsp = mgVerification.getWsp();
			selcteWsp();
			infoMgVerificationDetails();
			mgVerificationDetailsInfoWsp();
			populateRejectReasons();
			populateSignOffs();
		} else {
			disableEdit = false;
			wspReport = WspReportEnum.WSP;
			companyInfo();
			wspInfo();
		}
	}
	
	private void mgVerificationDetailsInfoWsp() throws Exception {
		company = companyService.findByKey(mgVerification.getWsp().getCompany().getId());
//		details = mgVerificationDetailsService.allMgVerificationDetailsWsp(mgVerification.getWsp());
//		details = mgVerificationDetailsService.mgVerificationDetailsInfoByWsp(mgVerification.getWsp());
	}
	
	private void populateSignOffs() throws Exception{
		if (mgVerification != null && mgVerification.getId() != null) {
			signOffLists = signoffService.findByVerifivcation(mgVerification);
		}
	}

	/**
	 * Lazy load for wsp
	 * 
	 * @throws Exception
	 */
	private void wspInfo() throws Exception {
		// array list
		wspList = wspService.allWsp();

		// lazy load array list
		wspDataModel = new LazyDataModel<Wsp>() {

			private static final long serialVersionUID = 1L;
			private List<Wsp> retorno = new ArrayList<Wsp>();

			@Override
			public List<Wsp> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					Calendar now = Calendar.getInstance();
					Long finYear = Long.valueOf(now.get(Calendar.YEAR));
					retorno = wspService.findCompaniesForVerification(first, pageSize, sortField, sortOrder, filters, finYear.intValue());
					wspDataModel.setRowCount(wspService.countCompaniesForVerification(filters, finYear.intValue()));
				} catch (Exception e) {
					logger.fatal(e);
					e.printStackTrace();
				}
				return retorno;
			}

			@Override
			public Object getRowKey(Wsp obj) {
				return obj.getId();
			}

			@Override
			public Wsp getRowData(String rowKey) {
				for (Wsp obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};
	}
	
	private void infoMgVerificationDetails() throws Exception {
		dataModelMgDetails = new LazyDataModel<MgVerificationDetails>() {

			private static final long serialVersionUID = 1L;
			private List<MgVerificationDetails> retorno = new ArrayList<MgVerificationDetails>();

			@Override
			public List<MgVerificationDetails> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
//					retorno = mgVerificationDetailsService.allMgVerificationDetails(MgVerificationDetails.class, first, pageSize, sortField, sortOrder, filters, mgVerification.getWsp());
//					dataModelMgDetails.setRowCount(mgVerificationDetailsService.count(MgVerificationDetails.class, filters, mgVerification.getWsp()));
					retorno = mgVerificationDetailsService.allMgVerificationDetailsByWspId(MgVerificationDetails.class, first, pageSize, sortField, sortOrder, filters, mgVerification.getWsp());
					dataModelMgDetails.setRowCount(mgVerificationDetailsService.countByWspId(MgVerificationDetails.class, filters, mgVerification.getWsp()));
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

	private void companyInfo() {
		companyDataModel = new LazyDataModel<Company>() {

			private static final long serialVersionUID = 1L;
			private List<Company> retorno = new ArrayList<Company>();

			@Override
			public List<Company> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = mandatoryGrantService.allMandatoryGrantCompany(first, pageSize, sortField, sortOrder, filters, wspReport, HAJConstants.HOSTING_MERSETA_ETQA);
					companyDataModel.setRowCount(mandatoryGrantService.countCompany(filters, wspReport, HAJConstants.HOSTING_MERSETA_ETQA));
				} catch (Exception e) {
					logger.fatal(e);
					e.printStackTrace();
				}
				return retorno;
			}

			@Override
			public Object getRowKey(Company obj) {
				return obj.getId();
			}

			@Override
			public Company getRowData(String rowKey) {
				for (Company obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};
	}
	
	private void populateRejectReasons() {
		RejectReasonsService rs= new RejectReasonsService();
		try {
			rejectReason = rs.locateReasonsSelectedByTargetKeyClassAndProcess(this.mgVerification.getId(), MgVerification.class.getName(), ConfigDocProcessEnum.MG_VERIFICATION);			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}		
	}

	/**
	 * When a wsp is selected locates all mandatory grants by wsp company
	 */
	public void selcteWsp() {
		try {
			pivitolInfoWsp();
			locateWspMgVerification();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * By wsp selection
	 */
	private void pivitolInfoWsp() throws Exception {
		company = companyService.findByKey(wsp.getCompany().getId());
		dataModel = new LazyDataModel<MandatoryGrant>() {

			private static final long serialVersionUID = 1L;
			private List<MandatoryGrant> retorno = new ArrayList<MandatoryGrant>();

			@Override
			public List<MandatoryGrant> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = mandatoryGrantService.allMandatoryGrant(MandatoryGrant.class, first, pageSize, sortField, sortOrder, filters, wspReport, company, HAJConstants.MAN_FUNDING_ID);
					dataModel.setRowCount(mandatoryGrantService.count(MandatoryGrant.class, filters, wspReport, company, HAJConstants.MAN_FUNDING_ID));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(MandatoryGrant obj) {
				return obj.getId();
			}

			@Override
			public MandatoryGrant getRowData(String rowKey) {
				for (MandatoryGrant obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};
	}

	/**
	 * locates MG verification by WSP
	 * 
	 * @throws Exception
	 */
	private void locateWspMgVerification() throws Exception {
		mgVerification = mgVerificationService.findByWspId(wsp);
		if (mgVerification == null || mgVerification.getId() == null) {
			mgVerification = new MgVerification();
			mgVerification.setWsp(wsp);
		}
		this.mgVerification = signoffService.resolveMgSignOff(mgVerification);
		mgVerification.getSignOffs().forEach(sign -> {
			if (disableSignOffButton == null || !disableSignOffButton) {
				disableSignOffButton = sign.getUser().equals(getSessionUI().getUser()) && (sign.getCompleted() == null || !sign.getCompleted());
			}
		});
	}
	
	public void uploadMGVerificationEvidence(){
		try {
			boolean signoffProvided = validiateSignoff();
			if (!signoffProvided) {
				throw new Exception("Please sign off before proceeding");
			}
			if (getSessionUI().getTask().getFirstInProcess()) {
				mgVerificationDetailsService.validateUploadEvidance(mgVerification);
			}
			mgVerificationService.uploadMGVerificationEvidence(getSessionUI().getActiveUser(), mgVerification, getSessionUI().getTask());
			addInfoMessage("Update Succesful");
			prepareNew();
			super.ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}
	
	private boolean validiateSignoff() throws Exception{
		boolean signoffProvided = true;
		for (Signoff signoff : signOffLists) {
			if (signoff.getUser() != null) {
				if (getSessionUI().getActiveUser().getId().equals(signoff.getUser().getId()) && (signoff.getAccept() == null || !signoff.getAccept())) {
					signoffProvided = false;
					break;
				}
			}
		}
		return signoffProvided;
	}

	public void download(Doc doc) {
		try {
			Faces.sendFile(doc.getData(), doc.getOriginalFname(), true);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
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

	public void closeDialog(){
		try {
			mgVerificationDetails = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
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

	/**
	 * By company selection
	 */
	public void pivitolInfo() {
		dataModel = new LazyDataModel<MandatoryGrant>() {
			private static final long serialVersionUID = 1L;
			private List<MandatoryGrant> retorno = new ArrayList<MandatoryGrant>();
			@Override
			public List<MandatoryGrant> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = mandatoryGrantService.allMandatoryGrant(MandatoryGrant.class, first, pageSize, sortField, sortOrder, filters, wspReport, company, HAJConstants.MAN_FUNDING_ID);
					dataModel.setRowCount(mandatoryGrantService.count(MandatoryGrant.class, filters, wspReport, company, HAJConstants.MAN_FUNDING_ID));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(MandatoryGrant obj) {
				return obj.getId();
			}
			@Override
			public MandatoryGrant getRowData(String rowKey) {
				for (MandatoryGrant obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}
	
	public void prepSignOff(){
		try {
			if (getSessionUI().getActiveUser().getId().equals(signOffSelected.getUser().getId())) {
				runClientSideExecute("PF('signOffDlg').show()");
			} else {
				addErrorMessage("You do not have permission to sign off");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void signOff(){
		try {
			if (signOffSelected.getAccept()) {
				signOffSelected.setSignOffDate(getNow());
				signoffService.update(signOffSelected);
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
	
	public void completeMgVerification() {
		try {
			mgVerificationService.completeMgVerification(getSessionUI().getActiveUser(), mgVerification, getSessionUI().getTask());
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void cloCrmReviewMgVerificationEvidence() {
		try {
			boolean signoffProvided = validiateSignoff();
			if (!signoffProvided) {
				throw new Exception("Please sign off before proceeding");
			}
			mgVerificationService.cloCrmReviewMgVerificationEvidence(getSessionUI().getActiveUser(), mgVerification, getSessionUI().getTask());
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void finalApproveMgVerification() {
		try {
			boolean signoffProvided = validiateSignoff();
			if (!signoffProvided) {
				throw new Exception("Please sign off before proceeding");
			}
			mgVerificationService.validiationBeforeMailNotification(3, mgVerification);
			mgVerificationService.finalApproveMgVerification(getSessionUI().getActiveUser(), mgVerification, getSessionUI().getTask());
			addInfoMessage(super.getEntryLanguage("update.successful"));
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void rejectMGVerification() {
		try {
			if (selectedRejectReason.size() == 0) { 
				throw new Exception("Please select a reject reason");	
			}			
			mgVerificationService.rejectTask(mgVerification, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason, signOffLists);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			e.printStackTrace();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	private void prepareNew() {
		mgVerification=null;
		dataModel =null;
	}

	public void finalRejectMGVerification() {
		try {
			if (selectedRejectReason.size() == 0) {
				throw new Exception("Please select a reject reason");	
			}
			mgVerificationService.validiationBeforeMailNotification(4, mgVerification);
			mgVerificationService.finalRejectTask(mgVerification, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
			//service.rejectRegistration(skillsregistration, getSessionUI().getActiveUser(), getSessionUI().getTask(),  selectedRejectReason);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			//skillsregistrationInfo();
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public List<RejectReasons> getRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.MG_VERIFICATION);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public void generateEmployeesEmployed() {
		try {
			mandatoryGrantService.create(mandatoryGrant);

			pivitolInfo();
			addInfoMessage(getEntryLanguage("update.successful"));
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}

	public void deleteMandatory() {
		try {
			mandatoryGrantService.delete(mandatoryGrant);

			pivitolInfo();
			addInfoMessage(getEntryLanguage("row.deleted"));
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}

	public void applySaqaData(SelectEvent event) {
		try {
			mandatoryGrant.setNqfLevels(mandatoryGrant.getQualification().getNqflevel());
			mandatoryGrant.setInterventionLevel(mandatoryGrant.getNqfLevels().getInterventionLevel());
			mandatoryGrant.setEtqa(etqaService.findByCode(mandatoryGrant.getQualification().getEtqaid()));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void applyInterventionData(SelectEvent event) {
		try {
			mandatoryGrant.setPivotNonPivot(mandatoryGrant.getInterventionType().getPivotNonPivot());
			if (mandatoryGrant.getPivotNonPivot() == PivotNonPivotEnum.Pivotal) {
				mandatoryGrant.setNqfAligned(yesNoService.findByKey(HAJConstants.YES_ID));
			} else {
				mandatoryGrant.setNqfAligned(yesNoService.findByKey(HAJConstants.NO_ID));
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}

	public void prepNewDoc() {
		try {
			doc = new Doc();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	/**
	 * Store file.
	 *
	 * @param event
	 *            the event
	 */
	// TODO Doc upload
	/*public void storeFile(FileUploadEvent event) {
		try {
			if(event.getFile()!=null && event.getFile().getContents()!=null) {
				doc.setData(event.getFile().getContents());
				doc.setOriginalFname(event.getFile().getFileName());
				doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
				doc.setUsers(getSessionUI().getActiveUser());
				if (doc.getId() != null) {
					DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
				}
			}else {
				addInfoMessage("Please upload document");
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}*/
	
	public void storeNewFile(FileUploadEvent event) {		
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			if (doc.getId() != null) {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			} else {
				doc.setTargetKey(this.mgVerificationDetails.getId());
				doc.setTargetClass(mgVerificationDetails.getClass().getName());
				if (doc.getId() == null) {
					DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), getSessionUI().getActiveUser());
				}
			}	
			doc = new Doc();
			infoMgVerificationDetails();
			mgVerificationDetailsInfoWsp();
			super.runClientSideExecute("PF('dlgUpload').hide()");				
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void storeFile(FileUploadEvent event) {
		try {
			if (doc != null) {
				doc.setData(event.getFile().getContents());
				doc.setOriginalFname(event.getFile().getFileName());
				doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
				doc.setUsers(getSessionUI().getActiveUser());
				
				if (doc.getId() != null) {
					DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
				}
			} else {
				doc = new Doc();
				doc.setData(event.getFile().getContents());
				doc.setOriginalFname(event.getFile().getFileName());
				doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
				doc.setUsers(getSessionUI().getActiveUser());
				doc.setTargetClass(mgVerification.getClass().getName());
				doc.setTargetKey(mgVerificationDetails.getId());
				if (doc.getId() == null) {
					DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), getSessionUI().getActiveUser());
				}
				//this.auditormonitorreviewInfo();
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	

	/**
	 * Download.
	 */
	public void download() {
		try {
			Faces.sendFile(doc.getData(), doc.getOriginalFname(), true);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	public MandatoryGrant getMandatoryGrant() {
		return mandatoryGrant;
	}

	public void setMandatoryGrant(MandatoryGrant mandatoryGrant) {
		this.mandatoryGrant = mandatoryGrant;
	}

	public List<MandatoryGrant> getMandatoryGrants() {
		return mandatoryGrants;
	}

	public void setMandatoryGrants(List<MandatoryGrant> mandatoryGrants) {
		this.mandatoryGrants = mandatoryGrants;
	}

	public LazyDataModel<MandatoryGrant> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<MandatoryGrant> dataModel) {
		this.dataModel = dataModel;
	}

	public LazyDataModel<Company> getCompanyDataModel() {
		return companyDataModel;
	}

	public void setCompanyDataModel(LazyDataModel<Company> companyDataModel) {
		this.companyDataModel = companyDataModel;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public LazyDataModel<Wsp> getWspDataModel() {
		return wspDataModel;
	}

	public void setWspDataModel(LazyDataModel<Wsp> wspDataModel) {
		this.wspDataModel = wspDataModel;
	}

	public List<Wsp> getWspList() {
		return wspList;
	}

	public void setWspList(List<Wsp> wspList) {
		this.wspList = wspList;
	}

	public Wsp getWsp() {
		return wsp;
	}

	public void setWsp(Wsp wsp) {
		this.wsp = wsp;
	}

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

	public ArrayList<RejectReasons> getSelectedRejectReason() {
		return selectedRejectReason;
	}

	public void setSelectedRejectReason(ArrayList<RejectReasons> selectedRejectReason) {
		this.selectedRejectReason = selectedRejectReason;
	}

	public LazyDataModel<MgVerificationDetails> getDataModelMgDetails() {
		return dataModelMgDetails;
	}

	public void setDataModelMgDetails(LazyDataModel<MgVerificationDetails> dataModelMgDetails) {
		this.dataModelMgDetails = dataModelMgDetails;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public MgVerificationDetails getMgVerificationDetails() {
		return mgVerificationDetails;
	}

	public void setMgVerificationDetails(MgVerificationDetails mgVerificationDetails) {
		this.mgVerificationDetails = mgVerificationDetails;
	}

	public List<RejectReasons> getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(List<RejectReasons> rejectReason) {
		this.rejectReason = rejectReason;
	}

	public Signoff getSignOffSelected() {
		return signOffSelected;
	}

	public void setSignOffSelected(Signoff signOffSelected) {
		this.signOffSelected = signOffSelected;
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

	public List<Users> getSignOffUserSelectionList() {
		return signOffUserSelectionList;
	}

	public void setSignOffUserSelectionList(List<Users> signOffUserSelectionList) {
		this.signOffUserSelectionList = signOffUserSelectionList;
	}

	public boolean isAlterInfo() {
		return alterInfo;
	}

	public void setAlterInfo(boolean alterInfo) {
		this.alterInfo = alterInfo;
	}

//	public List<MgVerificationDetails> getDetails() {
//		return details;
//	}
//
//	public void setDetails(List<MgVerificationDetails> details) {
//		this.details = details;
//	}
}
