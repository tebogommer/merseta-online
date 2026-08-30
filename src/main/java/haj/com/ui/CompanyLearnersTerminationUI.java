package haj.com.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.CompanyLearnersTermination;
import haj.com.entity.Doc;
import haj.com.entity.ProcessRoles;
import haj.com.entity.ReviewCommitteeMeeting;
import haj.com.entity.Signoff;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.LearnerChangeTypeEnum;
import haj.com.entity.enums.TerminationTypeEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyLearnersService;
import haj.com.service.CompanyLearnersTerminationService;
import haj.com.service.CompanyService;
import haj.com.service.DocService;
import haj.com.service.SignoffService;
import haj.com.service.TasksService;
import haj.com.service.TrainingProviderApplicationService;
import haj.com.service.lookup.RejectReasonsService;

@ManagedBean(name = "companylearnersterminationUI")
@ViewScoped
public class CompanyLearnersTerminationUI extends AbstractUI {

	private CompanyLearnersTerminationService service = new CompanyLearnersTerminationService();
	private CompanyLearnersService companyLearnersService = new CompanyLearnersService();
	private List<CompanyLearnersTermination> companylearnersterminationList = null;
	private List<CompanyLearnersTermination> companylearnersterminationfilteredList = null;
	private CompanyLearnersTermination companylearnerstermination = null;
	private LazyDataModel<CompanyLearnersTermination> dataModel;
	private LazyDataModel<CompanyLearnersTermination> applicationsDataModel;
	private ArrayList<RejectReasons> selectedRejectReason=new ArrayList<>();
	private Doc doc;
	private List<RejectReasons> rejectReasonsList=new ArrayList<>();
	private ReviewCommitteeMeeting reviewCommitteeMeeting;
	private LazyDataModel<CompanyLearnersTermination> expiredDataModel;
	private CompanyLearnersTermination parentCompanylearnerstermination = null;
	
	private LazyDataModel<CompanyLearnersTermination> investigatorDataModel;
	
	private Boolean finalRejection;
	private Boolean disableDateTime=true;
	
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
	 * Initialize method to get all CompanyLearnersTermination and prepare a for a
	 * create of a new CompanyLearnersTermination
	 * 
	 * @author TechFinium
	 * @see CompanyLearnersTermination
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		if (getSessionUI().getTask() != null && (getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.MutualLearnerTermination || getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.OneLearnerTermination)) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			this.companylearnerstermination = service.findByKey(getSessionUI().getTask().getTargetKey());
			boolean removeAgreement=false;
			if(companylearnerstermination.getCompanyLearners().getRegistrationNumber() !=null && companylearnerstermination.getCompanyLearners().getRegistrationNumber().contains("17/")){
				removeAgreement=true;
			}
			if(getSessionUI().getTask().getProcessRole() != null && getSessionUI().getTask().getProcessRole().getRolePermission() != null && getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.Edit) {
				disableDateTime = false;
			}
			service.prepareDocumentByProcess(getSessionUI().getTask().getWorkflowProcess(), companylearnerstermination, getSessionUI().getTask().getProcessRole(),removeAgreement);
			//companyLearnersService.prepareNewRegistration(ConfigDocProcessEnum.Learner, companylearnerstermination.getCompanyLearners());
			companyLearnersService.prepareNewRegistration(companylearnerstermination.getCompanyLearners());
			populateRejectReasons(getSessionUI().getTask().getWorkflowProcess());
			//Populating SDP details
			if(companylearnerstermination.getTrainingProviderApplication() !=null){
				CompanyService companyService=new CompanyService();
				companylearnerstermination.setTrainingProviderApplication(TrainingProviderApplicationService.instance().findByKey(companylearnerstermination.getTrainingProviderApplication().getId()));
				companylearnerstermination.getTrainingProviderApplication().setCompany(companyService.findByKey(companylearnerstermination.getTrainingProviderApplication().getCompany().getId()));
			}
		} else if (getSessionUI().getTask() != null && (getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.SystemLearnerTermination)){
			this.parentCompanylearnerstermination = service.findByKey(getSessionUI().getTask().getTargetKey());
			expiredTerminationInfo();
		}else if (getSessionUI().getTask() != null && (getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.ELearnerMutualTermination || getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.ELearnerOneSidedTermination)) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			this.companylearnerstermination = service.findByKey(getSessionUI().getTask().getTargetKey());
			boolean removeAgreement=false;
			if(companylearnerstermination.getCompanyLearners().getRegistrationNumber() !=null && companylearnerstermination.getCompanyLearners().getRegistrationNumber().contains("17/")){
				removeAgreement=true;
			}
			if(getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.ELearnerOneSidedTermination) {
				service.prepareDocumentByProcess(ConfigDocProcessEnum.ELearnerOneSidedTermination, companylearnerstermination, getSessionUI().getTask().getProcessRole(),removeAgreement);
			}else {
				service.prepareDocumentByProcess(ConfigDocProcessEnum.ELearnerMutualTermination, companylearnerstermination, getSessionUI().getTask().getProcessRole(),removeAgreement);
			}
			
			if(companylearnerstermination.getDocs().size() ==0) {
				service.prepareNewRegistration(companylearnerstermination);
			}
			
			if(getSessionUI().getTask().getProcessRole() != null && getSessionUI().getTask().getProcessRole().getRolePermission() != null && getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.Edit) {
				disableDateTime = false;
			}
			//companyLearnersService.prepareNewRegistration(ConfigDocProcessEnum.Learner, companylearnerstermination.getCompanyLearners());
			
			companyLearnersService.prepareNewRegistration(companylearnerstermination.getCompanyLearners());
			populateRejectReasons(getSessionUI().getTask().getWorkflowProcess());
			//Populating SDP details
			if(companylearnerstermination.getTrainingProviderApplication() !=null){
				CompanyService companyService=new CompanyService();
				companylearnerstermination.setTrainingProviderApplication(TrainingProviderApplicationService.instance().findByKey(companylearnerstermination.getTrainingProviderApplication().getId()));
				companylearnerstermination.getTrainingProviderApplication().setCompany(companyService.findByKey(companylearnerstermination.getTrainingProviderApplication().getCompany().getId()));
			}
			signOffs = signoffService.findByTargetKeyAndClass(companylearnerstermination.getId(), companylearnerstermination.getClass().getName());
		}
		else {
			prepareNew();
			companylearnersterminationInfo();
			learnersterminationApplicationInfo();
			investigatorApplicationInfo();
		}
		
	}
	
	private void populateRejectReasons(ConfigDocProcessEnum configDocProcessEnum) {
		RejectReasonsService rs= new RejectReasonsService();
		try {
			rejectReasonsList = rs.findByTargetKeyClassAndProcessAndResolveRejectDate(companylearnerstermination.getId(), companylearnerstermination.getClass().getName(),configDocProcessEnum);			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			
		}		
	}
	
	public void updateInvestigationDate()
	{
		try {
			service.create(companylearnerstermination);
			addInfoMessage("Investigation date updated");
			companyLearnersService.sendLearnerTerminationInvestigateEmail(companylearnerstermination.getCreateUser(), HAJConstants.sdfDDMMYYYY2.format(companylearnerstermination.getInvestigateDate()), HAJConstants.sdf2.format(companylearnerstermination.getInvestigateDate()),companylearnerstermination.getCompanyLearners().getUser(),getSessionUI().getActiveUser());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * Get all CompanyLearnersTermination for data table
	 * 
	 * @author TechFinium
	 * @see CompanyLearnersTermination
	 */
	public void companylearnersterminationInfo() {

		dataModel = new LazyDataModel<CompanyLearnersTermination>() {

			private static final long serialVersionUID = 1L;
			private List<CompanyLearnersTermination> retorno = new ArrayList<CompanyLearnersTermination>();

			@Override
			public List<CompanyLearnersTermination> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allCompanyLearnersTermination(CompanyLearnersTermination.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(CompanyLearnersTermination.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(CompanyLearnersTermination obj) {
				return obj.getId();
			}

			@Override
			public CompanyLearnersTermination getRowData(String rowKey) {
				for (CompanyLearnersTermination obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}
	
	public void investigatorApplicationInfo() {

		investigatorDataModel = new LazyDataModel<CompanyLearnersTermination>() {

			private static final long serialVersionUID = 1L;
			private List<CompanyLearnersTermination> retorno = new ArrayList<CompanyLearnersTermination>();

			@Override
			public List<CompanyLearnersTermination> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					filters.put("status", ApprovalEnum.PendingInverstigation);
					filters.put("investigatorUser", getSessionUI().getActiveUser());
					retorno = service.allPendingETQAApprovalCompanyLearnersTermination(CompanyLearnersTermination.class, first, pageSize, sortField, sortOrder, filters);
					investigatorDataModel.setRowCount(service.count(CompanyLearnersTermination.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(CompanyLearnersTermination obj) {
				return obj.getId();
			}

			@Override
			public CompanyLearnersTermination getRowData(String rowKey) {
				for (CompanyLearnersTermination obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}
	
	public void learnersterminationApplicationInfo() {

		applicationsDataModel = new LazyDataModel<CompanyLearnersTermination>() {

			private static final long serialVersionUID = 1L;
			private List<CompanyLearnersTermination> retorno = new ArrayList<CompanyLearnersTermination>();

			@Override
			public List<CompanyLearnersTermination> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					filters.put("status", ApprovalEnum.PendingCommitteeApproval);
					retorno = service.allPendingETQAApprovalCompanyLearnersTermination(CompanyLearnersTermination.class, first, pageSize, sortField, sortOrder, filters);
					applicationsDataModel.setRowCount(service.count(CompanyLearnersTermination.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(CompanyLearnersTermination obj) {
				return obj.getId();
			}

			@Override
			public CompanyLearnersTermination getRowData(String rowKey) {
				for (CompanyLearnersTermination obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert CompanyLearnersTermination into database
	 * 
	 * @author TechFinium
	 * @see CompanyLearnersTermination
	 */
	public void companylearnersterminationInsert() {
		try {
			service.create(this.companylearnerstermination);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			companylearnersterminationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update CompanyLearnersTermination in database
	 * 
	 * @author TechFinium
	 * @see CompanyLearnersTermination
	 */
	public void companylearnersterminationUpdate() {
		try {
			service.update(this.companylearnerstermination);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			companylearnersterminationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete CompanyLearnersTermination from database
	 * 
	 * @author TechFinium
	 * @see CompanyLearnersTermination
	 */
	public void companylearnersterminationDelete() {
		try {
			service.delete(this.companylearnerstermination);
			prepareNew();
			companylearnersterminationInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of CompanyLearnersTermination
	 * 
	 * @author TechFinium
	 * @see CompanyLearnersTermination
	 */
	public void prepareNew() {
		companylearnerstermination = new CompanyLearnersTermination();
	}

	public void completeCompanyLearners() {
		try {
			if (getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.ELearnerMutualTermination || getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.ELearnerOneSidedTermination) {
				if(signOff == null) {
					throw new Exception("Signoff error");							
				}
				if(!signOff.getAccept()) {
					throw new Exception("Please signoff before proceeding");
				}
			}

			if (getSessionUI().getTask().getProcessRole().getCompanyUserType() == null) {
				service.completeCompanyLearners(companylearnerstermination, getSessionUI().getActiveUser(), getSessionUI().getTask());
			}else{
				service.completeCompanyLearnersInitial(companylearnerstermination, getSessionUI().getActiveUser(), getSessionUI().getTask(), (companylearnerstermination.getTerminationTypeEnum() == TerminationTypeEnum.OneSidedTermination));
			}
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void completeCompanyELearners() {
		try {
			if(getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.SignOff) {
				if(signOff == null) {
					throw new Exception("Signoff error");							
				}
				if(!signOff.getAccept()) {
					throw new Exception("Please signoff before proceeding");
				}	
			}
			
			if(getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.ELearnerOneSidedTermination) {
				service.completeOneSidedTerminationCompanyELearners(companylearnerstermination, getSessionUI().getActiveUser(), getSessionUI().getTask());
			}else {
				if (getSessionUI().getTask().getProcessRole().getCompanyUserType() == null) {
					service.completeCompanyELearners(companylearnerstermination, getSessionUI().getActiveUser(), getSessionUI().getTask());
				}else{
					service.completeCompanyELearnersInitial(companylearnerstermination, getSessionUI().getActiveUser(), getSessionUI().getTask(), (companylearnerstermination.getTerminationTypeEnum() == TerminationTypeEnum.OneSidedTermination));
				}
			}
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void completeInvestigation()
	{
		try {
			SimpleDateFormat simpleDateFormat = HAJConstants.sdfDDMMYYYY2;
			String fromDate = simpleDateFormat.format(companylearnerstermination.getInvestigateDate());
			String now = simpleDateFormat.format(getNow());
			Date dateFrom =  simpleDateFormat.parse(fromDate);
			Date dateNow =  simpleDateFormat.parse(now);
			if(!dateNow.after(dateFrom) && !dateFrom.equals(dateNow)) {
				throw new Exception("This Application can only be actioned on "+HAJConstants.sdfDDMMYYYY2.format(companylearnerstermination.getInvestigateDate()));		
			}
			service.sendOnesidedTerminationTask(companylearnerstermination, getSessionUI().getActiveUser(), companylearnerstermination.getTask());
			investigatorApplicationInfo();
			getSessionUI().setTask(null);
			super.runClientSideUpdate("mainForm");
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void completeSystemTermination() {
		try {

			service.completeSystemTermination(parentCompanylearnerstermination, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void approveSystemTermination() {
		try {

			service.approveSystemTermination(parentCompanylearnerstermination, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void completeCompanyLearnersNotREGION() {
		try {

			service.completeCompanyLearnersNotREGION(companylearnerstermination, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void approveCompanyLearners() {
		try {
			service.approveCompanyLearners(companylearnerstermination, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void rejectCompanyLearners() {
		try {
			
			if (selectedRejectReason.size() == 0) {
				throw new Exception("Please select a reject reason");				
			}
			else
			{
				if(finalRejection==null || finalRejection==false)
				{
					if(getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.ELearnerMutualTermination) {
						service.rejectECompanyLearnersTermination(companylearnerstermination, getSessionUI().getActiveUser(), getSessionUI().getTask(),selectedRejectReason);
					}else {
						service.rejectCompanyLearners(companylearnerstermination, getSessionUI().getActiveUser(), getSessionUI().getTask(),selectedRejectReason);
					}					
				}
				else{
					service.finalRejectCompanyLearners(companylearnerstermination, getSessionUI().getActiveUser(), getSessionUI().getTask(),selectedRejectReason);
				}
				
				getSessionUI().setTask(null);
				ajaxRedirectToDashboard();
			}
			
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void finalRejectCompanyLearners() {
		try {
			if (selectedRejectReason.size() == 0) {
				throw new Exception("Please select a reject reason");				
			}
			else{
				service.finalRejectCompanyLearners(companylearnerstermination, getSessionUI().getActiveUser(), getSessionUI().getTask(),selectedRejectReason);
				getSessionUI().setTask(null);
				ajaxRedirectToDashboard();
			}
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void rejectCompanyLearnersRegion() {
		try {
			service.rejectCompanyLearnersRegion(companylearnerstermination, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void finalApproveCompanyLearners() {
		try {
			if(reviewCommitteeMeeting!=null){companylearnerstermination.setReviewCommitteeMeeting(reviewCommitteeMeeting);}
			service.finalApproveCompanyLearners(companylearnerstermination, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
			getSessionUI().setTask(null);
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void etqaCompanyLearnersFinalApprove() {
		try {
			service.etqaCompanyLearnersFinalApprove(companylearnerstermination, getSessionUI().getActiveUser());
			learnersterminationApplicationInfo();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void etqaCompanyLearnersFinalRejection() {
		try {
			
			if (selectedRejectReason.size() == 0) 
			{
				throw new Exception("Please select a reject reason");				
			}
			else
			{
				service.etqaCompanyLearnersFinalRejection(companylearnerstermination, getSessionUI().getActiveUser(),selectedRejectReason);
				learnersterminationApplicationInfo();
			}
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void storeFile(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			doc.setUsers(getSessionUI().getActiveUser());
			if (doc.getId() != null) {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			} else {
				doc.setTargetKey(companylearnerstermination.getId());
				doc.setTargetClass(CompanyLearnersTermination.class.getName());
				if (doc.getId() == null) DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), getSessionUI().getActiveUser());
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	
	
	public void expiredTerminationInfo() {

		   expiredDataModel = new LazyDataModel<CompanyLearnersTermination>() {

			private static final long serialVersionUID = 1L;
			private List<CompanyLearnersTermination> retorno = new ArrayList<CompanyLearnersTermination>();

			@Override
			public List<CompanyLearnersTermination> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					//filters.put("status", ApprovalEnum.PendingCommitteeApproval);
					retorno = service.allPendingETQAApprovalCompanyLearnersTermination(CompanyLearnersTermination.class, first, pageSize, sortField, sortOrder, filters);
					expiredDataModel.setRowCount(service.count(CompanyLearnersTermination.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(CompanyLearnersTermination obj) {
				return obj.getId();
			}

			@Override
			public CompanyLearnersTermination getRowData(String rowKey) {
				for (CompanyLearnersTermination obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}
	
	public void actionSignOff() {
		try {
			if (Boolean.TRUE.equals(signOff.getAccept())) {
				signOff.setSignOffDate(getNow());
				signoffService.update(signOff);
				signOffs = signoffService.findByTargetKeyAndClass(companylearnerstermination.getId(), companylearnerstermination.getClass().getName());
			} else {
				signOff.setAccept(false);
			}	
			super.runClientSideExecute("PF('signOffDlg').hide()");
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage());
		}
	}
	
	public void downloadForm() {	
		try {
			if(companylearnerstermination != null) {
				if (companylearnerstermination.getTerminationTypeEnum() == TerminationTypeEnum.MutualSidedTermination) {
					service.downloadLPMFM016(companylearnerstermination);
				} else if (companylearnerstermination.getTerminationTypeEnum() == TerminationTypeEnum.OneSidedTermination || companylearnerstermination.getTerminationTypeEnum() == TerminationTypeEnum.DeceasedLearnerTermination) {
					service.downloadLPMFM017(companylearnerstermination);
				}
			}else {
				throw new Exception("Error when downloading form");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}	
	}
	public void preparLearnerInfo()
	{
		try {
			companyLearnersService.prepareNewRegistration(ConfigDocProcessEnum.Learner, companylearnerstermination.getCompanyLearners());
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}

	public List<CompanyLearnersTermination> getCompanyLearnersTerminationList() {
		return companylearnersterminationList;
	}

	public void setCompanyLearnersTerminationList(List<CompanyLearnersTermination> companylearnersterminationList) {
		this.companylearnersterminationList = companylearnersterminationList;
	}

	public CompanyLearnersTermination getCompanylearnerstermination() {
		return companylearnerstermination;
	}

	public void setCompanylearnerstermination(CompanyLearnersTermination companylearnerstermination) {
		this.companylearnerstermination = companylearnerstermination;
	}

	public List<CompanyLearnersTermination> getCompanyLearnersTerminationfilteredList() {
		return companylearnersterminationfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param companylearnersterminationfilteredList
	 *            the new companylearnersterminationfilteredList list
	 * @see CompanyLearnersTermination
	 */
	public void setCompanyLearnersTerminationfilteredList(List<CompanyLearnersTermination> companylearnersterminationfilteredList) {
		this.companylearnersterminationfilteredList = companylearnersterminationfilteredList;
	}

	public LazyDataModel<CompanyLearnersTermination> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<CompanyLearnersTermination> dataModel) {
		this.dataModel = dataModel;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}
	
	public List<RejectReasons> getRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(getSessionUI().getTask().getWorkflowProcess());
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<RejectReasons> getOneSideRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			if(getSessionUI().getTask() !=null && getSessionUI().getTask().getWorkflowProcess() != null) {
				l = rejectReasonsService.findByProcess(getSessionUI().getTask().getWorkflowProcess());
			}else {
				l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.OneLearnerTermination);
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}


	public ArrayList<RejectReasons> getSelectedRejectReason() {
		return selectedRejectReason;
	}

	public void setSelectedRejectReason(ArrayList<RejectReasons> selectedRejectReason) {
		this.selectedRejectReason = selectedRejectReason;
	}


	public LazyDataModel<CompanyLearnersTermination> getApplicationsDataModel() {
		return applicationsDataModel;
	}

	public void setApplicationsDataModel(LazyDataModel<CompanyLearnersTermination> applicationsDataModel) {
		this.applicationsDataModel = applicationsDataModel;
	}

	/**
	 * @return the rejectReasonsList
	 */
	public List<RejectReasons> getRejectReasonsList() {
		return rejectReasonsList;
	}

	/**
	 * @param rejectReasonsList the rejectReasonsList to set
	 */
	public void setRejectReasonsList(List<RejectReasons> rejectReasonsList) {
		this.rejectReasonsList = rejectReasonsList;
	}

	/**
	 * @return the reviewCommitteeMeeting
	 */
	public ReviewCommitteeMeeting getReviewCommitteeMeeting() {
		return reviewCommitteeMeeting;
	}

	/**
	 * @param reviewCommitteeMeeting the reviewCommitteeMeeting to set
	 */
	public void setReviewCommitteeMeeting(ReviewCommitteeMeeting reviewCommitteeMeeting) {
		this.reviewCommitteeMeeting = reviewCommitteeMeeting;
	}

	

	/**
	 * @return the parentCompanylearnerstermination
	 */
	public CompanyLearnersTermination getParentCompanylearnerstermination() {
		return parentCompanylearnerstermination;
	}

	/**
	 * @param parentCompanylearnerstermination the parentCompanylearnerstermination to set
	 */
	public void setParentCompanylearnerstermination(CompanyLearnersTermination parentCompanylearnerstermination) {
		this.parentCompanylearnerstermination = parentCompanylearnerstermination;
	}

	public LazyDataModel<CompanyLearnersTermination> getInvestigatorDataModel() {
		return investigatorDataModel;
	}

	public void setInvestigatorDataModel(LazyDataModel<CompanyLearnersTermination> investigatorDataModel) {
		this.investigatorDataModel = investigatorDataModel;
	}

	public Boolean getFinalRejection() {
		return finalRejection;
	}

	public void setFinalRejection(Boolean finalRejection) {
		this.finalRejection = finalRejection;
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

	public Boolean getDisableDateTime() {
		return disableDateTime;
	}

	public void setDisableDateTime(Boolean disableDateTime) {
		this.disableDateTime = disableDateTime;
	}
}
