package haj.com.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.bean.LostTimeReasonBean;
import haj.com.constants.HAJConstants;
import haj.com.entity.AssessorModeratorApplication;
import haj.com.entity.CompanyLearnersLostTime;
import haj.com.entity.Doc;
import haj.com.entity.ProcessRoles;
import haj.com.entity.Signoff;
import haj.com.entity.Users;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.LostTimeReason;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyLearnersLostTimeService;
import haj.com.service.CompanyLearnersService;
import haj.com.service.CompanyService;
import haj.com.service.DocService;
import haj.com.service.JasperService;
import haj.com.service.ProcessRolesService;
import haj.com.service.SignoffService;
import haj.com.service.TasksService;
import haj.com.service.TrainingProviderApplicationService;
import haj.com.service.UsersService;
import haj.com.service.lookup.RejectReasonsService;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@ManagedBean(name = "companylearnerslosttimeUI")
@ViewScoped
public class CompanyLearnersLostTimeUI extends AbstractUI {

	private CompanyLearnersLostTimeService service = new CompanyLearnersLostTimeService();
	private List<CompanyLearnersLostTime> companylearnerslosttimeList = null;
	private ArrayList<RejectReasons> selectedRejectReason;
	private List<CompanyLearnersLostTime> companylearnerslosttimefilteredList = null;
	private CompanyLearnersLostTime companylearnerslosttime = null;
	private LazyDataModel<CompanyLearnersLostTime> dataModel;
	private Doc doc;
	private CompanyLearnersService companyLearnersService = new CompanyLearnersService();
	private ArrayList<RejectReasons>rejectReasonList;
	/** The Selected Reject Reasons */
	private RejectReasonsService rejectReasonsService = new RejectReasonsService();
	private Signoff signOff;
	private SignoffService  signoffService = new SignoffService();
	private List<Signoff>signOffs;

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

	/**
	 * Initialize method to get all CompanyLearnersLostTime and prepare a for a create of a new CompanyLearnersLostTime
	 * 
	 * @author TechFinium
	 * @see CompanyLearnersLostTime
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.LearnerLostTime) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			this.companylearnerslosttime = service.findByKey(getSessionUI().getTask().getTargetKey());
			//Learner registration Documents
			service.prepareDocuments(ConfigDocProcessEnum.LearnerLostTime, companylearnerslosttime,getSessionUI().getTask().getProcessRole(),getSessionUI().getTask().getFirstInProcess());
			//companyLearnersService.prepareNewRegistration(ConfigDocProcessEnum.Learner, companylearnerslosttime.getCompanyLearners());
			companyLearnersService.prepareNewRegistration(companylearnerslosttime.getCompanyLearners());
			rejectReasonList=(ArrayList<RejectReasons>) rejectReasonsService.locateReasonsSelectedByTargetKeyClassAndProcess(companylearnerslosttime.getId(), companylearnerslosttime.getClass().getName(), ConfigDocProcessEnum.LearnerLostTime);
			//Populating SDP details
			if(companylearnerslosttime.getTrainingProviderApplication() !=null){
				CompanyService companyService=new CompanyService();
				companylearnerslosttime.setTrainingProviderApplication(TrainingProviderApplicationService.instance().findByKey(companylearnerslosttime.getTrainingProviderApplication().getId()));
				companylearnerslosttime.getTrainingProviderApplication().setCompany(companyService.findByKey(companylearnerslosttime.getTrainingProviderApplication().getCompany().getId()));
			}
			
		}else if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.ELearnerLostTime) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			this.companylearnerslosttime = service.findByKey(getSessionUI().getTask().getTargetKey());
			service.prepareDocuments(ConfigDocProcessEnum.ELearnerLostTime, companylearnerslosttime,getSessionUI().getTask().getProcessRole(),getSessionUI().getTask().getFirstInProcess());
			companyLearnersService.prepareNewRegistration(companylearnerslosttime.getCompanyLearners());
			rejectReasonList=(ArrayList<RejectReasons>) rejectReasonsService.locateReasonsSelectedByTargetKeyClassAndProcess(companylearnerslosttime.getId(), companylearnerslosttime.getClass().getName(), ConfigDocProcessEnum.ELearnerLostTime);
			if(companylearnerslosttime.getTrainingProviderApplication() !=null){
				CompanyService companyService=new CompanyService();
				companylearnerslosttime.setTrainingProviderApplication(TrainingProviderApplicationService.instance().findByKey(companylearnerslosttime.getTrainingProviderApplication().getId()));
				companylearnerslosttime.getTrainingProviderApplication().setCompany(companyService.findByKey(companylearnerslosttime.getTrainingProviderApplication().getCompany().getId()));
			}	
			signOffs = signoffService.findByTargetKeyAndClass(companylearnerslosttime.getId(), companylearnerslosttime.getClass().getName());
		}else {
			prepareNew();
			companylearnerslosttimeInfo();
		}
	}

	/**
	 * Get all CompanyLearnersLostTime for data table
	 * 
	 * @author TechFinium
	 * @see CompanyLearnersLostTime
	 */
	public void companylearnerslosttimeInfo() {

		dataModel = new LazyDataModel<CompanyLearnersLostTime>() {

			private static final long serialVersionUID = 1L;
			private List<CompanyLearnersLostTime> retorno = new ArrayList<CompanyLearnersLostTime>();

			@Override
			public List<CompanyLearnersLostTime> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allCompanyLearnersLostTime(CompanyLearnersLostTime.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(CompanyLearnersLostTime.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(CompanyLearnersLostTime obj) {
				return obj.getId();
			}

			@Override
			public CompanyLearnersLostTime getRowData(String rowKey) {
				for (CompanyLearnersLostTime obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert CompanyLearnersLostTime into database
	 * 
	 * @author TechFinium
	 * @see CompanyLearnersLostTime
	 */
	public void companylearnerslosttimeInsert() {
		try {
			service.create(this.companylearnerslosttime);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			companylearnerslosttimeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update CompanyLearnersLostTime in database
	 * 
	 * @author TechFinium
	 * @see CompanyLearnersLostTime
	 */
	public void companylearnerslosttimeUpdate() {
		try {
			service.update(this.companylearnerslosttime);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			companylearnerslosttimeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete CompanyLearnersLostTime from database
	 * 
	 * @author TechFinium
	 * @see CompanyLearnersLostTime
	 */
	public void companylearnerslosttimeDelete() {
		try {
			service.delete(this.companylearnerslosttime);
			prepareNew();
			companylearnerslosttimeInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of CompanyLearnersLostTime
	 * 
	 * @author TechFinium
	 * @see CompanyLearnersLostTime
	 */
	public void prepareNew() {
		companylearnerslosttime = new CompanyLearnersLostTime();
	}

	/**
	 * Store file.
	 *
	 * @param event
	 *            the event
	 */
	// TODO Doc upload
	public void storeFile(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			doc.setUsers(getSessionUI().getActiveUser());
			if (doc.getId() != null) {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			} else {
				doc.setTargetKey(companylearnerslosttime.getId());
				doc.setTargetClass(CompanyLearnersLostTime.class.getName());
				if (doc.getId() == null) DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), getSessionUI().getActiveUser());
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void downloadAddendum() {
		// LPM-AD-001-Addendum-LearnershipAgreement.jrxml
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("company_id", companylearnerslosttime.getCompanyLearners().getCompany().getId());
			params.put("learners_id", companylearnerslosttime.getCompanyLearners().getUser().getId());
			params.put("commencement_date", "");
			params.put("end_date", "");
			JasperService.addLogo(params);
			JasperService.instance().createReportFromDBtoServletOutputStream("LPM-AD-001-Addendum-LearnershipAgreement.jrxml", params, "Application_Form.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void downloadApplicationRequestForExtension() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("user_id",  companylearnerslosttime.getCompanyLearners().getId());
			params.put("company_id",  companylearnerslosttime.getCompanyLearners().getEmployer().getId());
			params.put("call_center_number", "011111111");
			params.put("period", "2019");
			params.put("employer", companylearnerslosttime.getCompanyLearners().getEmployer());
			JasperService.addLogo(params);
			JasperService.instance().createReportFromDBtoServletOutputStream("LPM-FM-011-Request-for-Extension-of-Termination-Date-of-Learnership.jasper", params, "Application_Form.pdf");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);e.printStackTrace();
		}
	}
	
	public void downloadTest() {
		// LPM-AD-001-Addendum-LearnershipAgreement.jrxml
		try {
			
			UsersService usersService = new UsersService();
			Map<String, Object> params = new HashMap<String, Object>();
			
			Users learner = usersService.findByKey(companylearnerslosttime.getCompanyLearners().getUser().getId());

			String contractNum = "";// To be fixed
			int daysExtended = companylearnerslosttime.getDaysExtended();
			String newTerminationDate = HAJConstants.sdfDDMMYYYY2.format(companylearnerslosttime.getNewCompletionDate());
			String expiryDateForLeve = HAJConstants.sdfDDMMYYYY2.format(companylearnerslosttime.getNewCompletionDate());// To be fixed

			params.put("company_id", companylearnerslosttime.getCompanyLearners().getCompany().getId());
			params.put("user_id", companylearnerslosttime.getCompanyLearners().getUser().getId());
			params.put("extension_reason", companylearnerslosttime.getLostTimeReason().getFriendlyName());
			params.put("contract_num", contractNum);
			params.put("daysExtended", daysExtended);
			params.put("newTerminationDate", newTerminationDate);
			params.put("expiryDateForLeve", expiryDateForLeve);

			ArrayList<LostTimeReasonBean> reasonList = new ArrayList<>();
			for (LostTimeReason val : LostTimeReason.values()) {
				boolean selected = false;
				if (companylearnerslosttime.getLostTimeReason().getFriendlyName().equalsIgnoreCase(val.getFriendlyName())) {
					selected = true;
				}
				LostTimeReasonBean reason = new LostTimeReasonBean(val.getFriendlyName(), selected);
				reasonList.add(reason);
			}
			

			params.put("lostTimeReasonBeanDataSource", new JRBeanCollectionDataSource(reasonList));
			Users u= usersService.findByKey(companylearnerslosttime.getCreateUser().getId());
			String toEmail =  u.getEmail();
			
			JasperService.addLogo(params);
			JasperService.addImage(params, "checkbox-marked.png", "checked_image");
			JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");

			JasperService.instance().createReportFromDBtoServletOutputStream("LPM-TP-013-Extension-of-Contract-Template.jasper", params, "test.pdf");
			
			usersService = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void completeCompanyLearners() {
		try {
			service.completeCompanyLearners(companylearnerslosttime, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void downloadLPMFM011() {	
		try {
			if(companylearnerslosttime != null) {
				service.downloadLPMFM011(companylearnerslosttime);
			}else {
				throw new Exception("Error when downloading form");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}	
	}
	
	public void downloadLPMTP013() {	
		try {
			if(companylearnerslosttime != null) {
				service.downloadLPMTP013(companylearnerslosttime);
			}else {
				throw new Exception("Error when downloading form");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}	
	}
	
	public void downloadLPMAD001() {	
		try {
			if(companylearnerslosttime != null) {
				service.downloadLPMAD001(companylearnerslosttime);
			}else {
				throw new Exception("Error when downloading form");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}	
	}
	
	public void completeCompanyLearnersELearner() {
		try {
			if(signOff == null) {
				throw new Exception("Signoff error");							
			}
			if(!signOff.getAccept()) {
				throw new Exception("Please signoff before proceeding");
			}
			service.completeCompanyLearnersELeaner(companylearnerslosttime, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void withdrawLostTime() {
		try {
			service.withdrawLostTime(companylearnerslosttime, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void approveCompanyLearners() {
		try {
			service.approveCompanyLearners(companylearnerslosttime, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void approveCompanyLearnersLostTime() {
		try {
			service.approveCompanyLearnersLostTime(companylearnerslosttime, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void actionSignOff() {
		try {
			if (Boolean.TRUE.equals(signOff.getAccept())) {
				signOff.setSignOffDate(getNow());
				signoffService.update(signOff);
				signOffs = signoffService.findByTargetKeyAndClass(companylearnerslosttime.getId(), companylearnerslosttime.getClass().getName());
			} else {
				signOff.setAccept(false);
			}	
			super.runClientSideExecute("PF('signOffDlg').hide()");
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage());
		}
	}

	public void rejectCompanyLearners() {
		try {
			if (selectedRejectReason.size() == 0) throw new Exception("Please select a reject reason");
			if(getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.ELearnerLostTime) {
				service.rejectCompanyLearnersElostTime(companylearnerslosttime, getSessionUI().getActiveUser(), getSessionUI().getTask(),selectedRejectReason);
			}else {
				service.rejectCompanyLearners(companylearnerslosttime, getSessionUI().getActiveUser(), getSessionUI().getTask(),selectedRejectReason);
			}			
			ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void finalApproveCompanyLearners() {
		try {
			service.finalApproveCompanyLearners(companylearnerslosttime, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void finalRejectCompanyLearners() {
		try {
			if (selectedRejectReason.size() == 0) throw new Exception("Please select a reject reason");
			service.finalRejectCompanyLearners(companylearnerslosttime, getSessionUI().getActiveUser(), getSessionUI().getTask(),selectedRejectReason);
			ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public List<RejectReasons> getRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(getSessionUI().getTask().getWorkflowProcess());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<CompanyLearnersLostTime> getCompanyLearnersLostTimeList() {
		return companylearnerslosttimeList;
	}

	public void setCompanyLearnersLostTimeList(List<CompanyLearnersLostTime> companylearnerslosttimeList) {
		this.companylearnerslosttimeList = companylearnerslosttimeList;
	}

	public CompanyLearnersLostTime getCompanylearnerslosttime() {
		return companylearnerslosttime;
	}

	public void setCompanylearnerslosttime(CompanyLearnersLostTime companylearnerslosttime) {
		this.companylearnerslosttime = companylearnerslosttime;
	}

	public List<CompanyLearnersLostTime> getCompanyLearnersLostTimefilteredList() {
		return companylearnerslosttimefilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param companylearnerslosttimefilteredList
	 *            the new companylearnerslosttimefilteredList list
	 * @see CompanyLearnersLostTime
	 */
	public void setCompanyLearnersLostTimefilteredList(List<CompanyLearnersLostTime> companylearnerslosttimefilteredList) {
		this.companylearnerslosttimefilteredList = companylearnerslosttimefilteredList;
	}

	public LazyDataModel<CompanyLearnersLostTime> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<CompanyLearnersLostTime> dataModel) {
		this.dataModel = dataModel;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public ArrayList<RejectReasons> getSelectedRejectReason() {
		return selectedRejectReason;
	}

	public void setSelectedRejectReason(ArrayList<RejectReasons> selectedRejectReason) {
		this.selectedRejectReason = selectedRejectReason;
	}

	/**
	 * @return the rejectReasonList
	 */
	public ArrayList<RejectReasons> getRejectReasonList() {
		return rejectReasonList;
	}

	/**
	 * @param rejectReasonList the rejectReasonList to set
	 */
	public void setRejectReasonList(ArrayList<RejectReasons> rejectReasonList) {
		this.rejectReasonList = rejectReasonList;
	}

	public Signoff getSignOff() {
		return signOff;
	}

	public List<Signoff> getSignOffs() {
		return signOffs;
	}

	public void setSignOff(Signoff signOff) {
		this.signOff = signOff;
	}

	public void setSignOffs(List<Signoff> signOffs) {
		this.signOffs = signOffs;
	}
}
