package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.CompanyLearnersChange;
import haj.com.entity.Doc;
import haj.com.entity.Signoff;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.LearnerChangeTypeEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyLearnersChangeService;
import haj.com.service.CompanyLearnersService;
import haj.com.service.CompanyService;
import haj.com.service.DocService;
import haj.com.service.SignoffService;
import haj.com.service.TasksService;
import haj.com.service.TrainingProviderApplicationService;
import haj.com.service.lookup.RejectReasonsService;

@ManagedBean(name = "companylearnerschangeUI")
@ViewScoped
public class CompanyLearnersChangeUI extends AbstractUI {

	private CompanyLearnersChangeService service = new CompanyLearnersChangeService();
	private CompanyLearnersService companyLearnersService = new CompanyLearnersService();
	private List<CompanyLearnersChange> companylearnerschangeList = null;
	private List<CompanyLearnersChange> companylearnerschangefilteredList = null;
	private CompanyLearnersChange companylearnerschange = null;
	private LazyDataModel<CompanyLearnersChange> dataModel;
	private Doc doc;

	private ArrayList<RejectReasons> selectedRejectReason = new ArrayList<>();
	private List<RejectReasons> rejectReason = new ArrayList<>();
	
	private Signoff signOff;
	private SignoffService  signoffService = new SignoffService();
	private List<Signoff>signOffs;

	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Initialize method to get all CompanyLearnersChange and prepare a for a create
	 * of a new CompanyLearnersChange
	 * 
	 * @author TechFinium
	 * @see CompanyLearnersChange
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.LearnerChange) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			this.companylearnerschange = service.findByKey(getSessionUI().getTask().getTargetKey());
			//service.prepareNewRegistration(companylearnerschange);
			service.prepareNewLearnershipDevelopmentRegistrationDocs(ConfigDocProcessEnum.LearnerChange, companylearnerschange, companylearnerschange, getSessionUI().getTask().getProcessRole());
			companyLearnersService.prepareNewRegistration(companylearnerschange.getCompanyLearners());
			//Populating SDP details
			if(companylearnerschange.getTrainingProviderApplication() !=null){
				CompanyService companyService=new CompanyService();
				companylearnerschange.setTrainingProviderApplication(TrainingProviderApplicationService.instance().findByKey(companylearnerschange.getTrainingProviderApplication().getId()));
				companylearnerschange.getTrainingProviderApplication().setCompany(companyService.findByKey(companylearnerschange.getTrainingProviderApplication().getCompany().getId()));
			}
			// populateExistingLearner();
			populateRejectReasons();
		}else if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.ELearnerChange) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			this.companylearnerschange = service.findByKey(getSessionUI().getTask().getTargetKey());
			//Populating SDP details
			if(companylearnerschange.getTrainingProviderApplication() !=null){
				CompanyService companyService=new CompanyService();
				companylearnerschange.setTrainingProviderApplication(TrainingProviderApplicationService.instance().findByKey(companylearnerschange.getTrainingProviderApplication().getId()));
				companylearnerschange.getTrainingProviderApplication().setCompany(companyService.findByKey(companylearnerschange.getTrainingProviderApplication().getCompany().getId()));
			}
			companylearnerschange.setDocs(companylearnerschange.getCompanyLearners().getDocs());
			// populateExistingLearner();
			populateRejectReasons();
			signOffs = signoffService.findByTargetKeyAndClass(companylearnerschange.getId(), companylearnerschange.getClass().getName());
			service.prepareNewRegistration(companylearnerschange, ConfigDocProcessEnum.ELearnerChange);
			/*if(getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.Upload) {
				service.prepareCompanyLearnersChangeDocs(ConfigDocProcessEnum.ELearnerChange, companylearnerschange, getSessionUI().getTask().getProcessRole());
			}else {
				service.prepareNewRegistration(companylearnerschange, ConfigDocProcessEnum.ELearnerChange);
			}*/
			
			companyLearnersService.prepareNewRegistration(companylearnerschange.getCompanyLearners());
		}else {
			prepareNew();
			companylearnerschangeInfo();
		}
	}

	private void populateRejectReasons() {
		RejectReasonsService rs = new RejectReasonsService();
		try {
			rejectReason = rs.locateReasonsSelectedByTargetKeyClassAndProcess(companylearnerschange.getId(), CompanyLearnersChange.class.getName(), getSessionUI().getTask().getWorkflowProcess());			
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	
	/**
	 * Get all CompanyLearnersChange for data table
	 * 
	 * @author TechFinium
	 * @see CompanyLearnersChange
	 */
	public void companylearnerschangeInfo() {

		dataModel = new LazyDataModel<CompanyLearnersChange>() {

			private static final long serialVersionUID = 1L;
			private List<CompanyLearnersChange> retorno = new ArrayList<CompanyLearnersChange>();

			@Override
			public List<CompanyLearnersChange> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allCompanyLearnersChange(CompanyLearnersChange.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(CompanyLearnersChange.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(CompanyLearnersChange obj) {
				return obj.getId();
			}

			@Override
			public CompanyLearnersChange getRowData(String rowKey) {
				for (CompanyLearnersChange obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

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

	/**
	 * Insert CompanyLearnersChange into database
	 * 
	 * @author TechFinium
	 * @see CompanyLearnersChange
	 */
	public void companylearnerschangeInsert() {
		try {
			service.create(this.companylearnerschange);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			companylearnerschangeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update CompanyLearnersChange in database
	 * 
	 * @author TechFinium
	 * @see CompanyLearnersChange
	 */
	public void companylearnerschangeUpdate() {
		try {
			service.update(this.companylearnerschange);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			companylearnerschangeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete CompanyLearnersChange from database
	 * 
	 * @author TechFinium
	 * @see CompanyLearnersChange
	 */
	public void companylearnerschangeDelete() {
		try {
			service.delete(this.companylearnerschange);
			prepareNew();
			companylearnerschangeInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of CompanyLearnersChange
	 * 
	 * @author TechFinium
	 * @see CompanyLearnersChange
	 */
	public void prepareNew() {
		companylearnerschange = new CompanyLearnersChange();
	}

	public void completeCompanyLearners() {
		try {
			chechDocuments();
			service.completeCompanyLearners(companylearnerschange, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (ValidationException e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			e.printStackTrace();
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void completeCompanyLearnersElearner() {
		try {
			if(signOff == null) {
				throw new Exception("Signoff error");							
			}
			if(!signOff.getAccept()) {
				throw new Exception("Please signoff before proceeding");
			}
			service.completeCompanyLearnersELearner(companylearnerschange, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (ValidationException e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			e.printStackTrace();
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void chechDocuments() throws Exception
	{
		boolean error = false;
		String err = "";
		if (companylearnerschange.getDocs() != null) {
			for (Doc doc : companylearnerschange.getDocs()) {
				if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
					error = true;
					err = err + "Please upload " + doc.getConfigDoc().getName() ;
					break;
				}
			}
		}
		if (error) throw new Exception(err);
	}

	public void approveCompanyLearners() {
		try {
			service.approveCompanyLearners(companylearnerschange, getSessionUI().getActiveUser(), getSessionUI().getTask());
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
			if (selectedRejectReason.size() == 0) throw new Exception("Please select a reject reason");
			if(getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.ELearnerChange) {
				service.rejectCompanyLearnersEchange(companylearnerschange, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
			}else {
				service.rejectCompanyLearners(companylearnerschange, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
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

	public void finalApproveCompanyLearners() {
		try {
			service.finalApproveCompanyLearners(companylearnerschange, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void finalRejectCompanyLearners() {
		try {
			if (selectedRejectReason.size() == 0) throw new Exception("Please select a reject reason");
			service.finalRejectCompanyLearners(companylearnerschange, getSessionUI().getActiveUser(), getSessionUI().getTask(),selectedRejectReason);
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void actionSignOff() {
		try {
			if (Boolean.TRUE.equals(signOff.getAccept())) {
				signOff.setSignOffDate(getNow());
				signoffService.update(signOff);
				signOffs = signoffService.findByTargetKeyAndClass(companylearnerschange.getId(), companylearnerschange.getClass().getName());
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
			if(companylearnerschange != null) {
				if (companylearnerschange.getLearnerChangeTypeEnum() == LearnerChangeTypeEnum.ChangeOfProgramQalification) {
					service.downloadLPMAD005(companylearnerschange);
				} else if (companylearnerschange.getLearnerChangeTypeEnum() == LearnerChangeTypeEnum.ChangeOfProgramTrade) {
					service.downloadLPMAD010(companylearnerschange);
				} else if (companylearnerschange.getLearnerChangeTypeEnum() == LearnerChangeTypeEnum.ChangeCommencementDate) {
					service.downloadLPMAD001(companylearnerschange);
				} else if (companylearnerschange.getLearnerChangeTypeEnum() == LearnerChangeTypeEnum.ChangeNonCreditBearingTitle) {
					service.downloadLPMAD005NonCreditBearingEmail(companylearnerschange);
				}
			}else {
				throw new Exception("Error when downloading form");
			}
		} catch (Exception e) {
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
				doc.setTargetKey(companylearnerschange.getId());
				doc.setTargetClass(CompanyLearnersChange.class.getName());
				if (doc.getId() == null) DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), getSessionUI().getActiveUser());
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public List<CompanyLearnersChange> getCompanyLearnersChangeList() {
		return companylearnerschangeList;
	}

	public void setCompanyLearnersChangeList(List<CompanyLearnersChange> companylearnerschangeList) {
		this.companylearnerschangeList = companylearnerschangeList;
	}

	public CompanyLearnersChange getCompanylearnerschange() {
		return companylearnerschange;
	}

	public void setCompanylearnerschange(CompanyLearnersChange companylearnerschange) {
		this.companylearnerschange = companylearnerschange;
	}

	public List<CompanyLearnersChange> getCompanyLearnersChangefilteredList() {
		return companylearnerschangefilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param companylearnerschangefilteredList
	 *            the new companylearnerschangefilteredList list
	 * @see CompanyLearnersChange
	 */
	public void setCompanyLearnersChangefilteredList(List<CompanyLearnersChange> companylearnerschangefilteredList) {
		this.companylearnerschangefilteredList = companylearnerschangefilteredList;
	}

	public LazyDataModel<CompanyLearnersChange> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<CompanyLearnersChange> dataModel) {
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

	public List<RejectReasons> getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(List<RejectReasons> rejectReason) {
		this.rejectReason = rejectReason;
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
