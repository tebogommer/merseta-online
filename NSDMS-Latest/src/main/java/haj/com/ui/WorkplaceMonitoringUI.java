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

import haj.com.constants.HAJConstants;
import haj.com.entity.CompanyLearners;
import haj.com.entity.Doc;
import haj.com.entity.LearnerInduction;
import haj.com.entity.MandatoryGrant;
import haj.com.entity.WorkplaceMonitoring;
import haj.com.entity.WorkplaceMonitoringMitigation;
import haj.com.entity.datamodel.CompanyLearnersLearnershipDataModel;
import haj.com.entity.datamodel.CompanyLearnersOther;
import haj.com.entity.datamodel.CompanyLearnersTradeDataModel;
import haj.com.entity.datamodel.MandatoryGrantDataModel;
import haj.com.entity.datamodel.WorkplaceMonitoringMitigationDataModel;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.InterventionTypeEnum;
import haj.com.entity.enums.TradeTypeEnum;
import haj.com.entity.enums.WorkplaceSurveyType;
import haj.com.entity.enums.WspReportEnum;
import haj.com.entity.lookup.LearnerMonitoringSurvey;
import haj.com.entity.lookup.PurposeOfSiteVisit;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyLearnersService;
import haj.com.service.DocService;
import haj.com.service.LearnerInductionService;
import haj.com.service.MandatoryGrantService;
import haj.com.service.TasksService;
import haj.com.service.WorkplaceMonitoringService;
import haj.com.service.lookup.LearnerMonitoringSurveyService;
import haj.com.service.lookup.PurposeOfSiteVisitService;

@ManagedBean(name = "workplacemonitoringUI")
@ViewScoped
public class WorkplaceMonitoringUI extends AbstractUI {

	private WorkplaceMonitoringService service = new WorkplaceMonitoringService();
	private List<WorkplaceMonitoring> workplacemonitoringList = null;
	private List<WorkplaceMonitoring> workplacemonitoringfilteredList = null;
	private WorkplaceMonitoring workplacemonitoring = null;
	private LazyDataModel<WorkplaceMonitoring> dataModel;
	private LearnerInduction learnerInduction;
	private List<LearnerInduction> learnerInductions;
	private Doc doc;

	private LearnerInductionService learnerInductionService = new LearnerInductionService();
	private List<PurposeOfSiteVisit> purposeOfSiteVisits;
	private List<PurposeOfSiteVisit> selectedPurposeOfSiteVisits;
	private PurposeOfSiteVisitService purposeOfSiteVisitsService = new PurposeOfSiteVisitService();
	private List<LearnerMonitoringSurvey> learnerMonitoringSurveys;
	private List<LearnerMonitoringSurvey> dgMonitoringSurveys;
	private List<LearnerMonitoringSurvey> otherSurveys;
	private LearnerMonitoringSurveyService learnerMonitoringSurveyService = new LearnerMonitoringSurveyService();

	private LazyDataModel<CompanyLearners> dataModelCompanyLearners;
	private int companyLearnerCount;
	private LazyDataModel<CompanyLearners> dataModelCompanyLearnersCBMT;
	private int companyLearnerCBMTCount;
	private LazyDataModel<CompanyLearners> dataModelCompanyLearnersMotor;
	private int companyLearnerMotorCount;
	private LazyDataModel<CompanyLearners> dataModelCompanyLearnersMetal;
	private int companyLearnerMetalCount;
	private LazyDataModel<CompanyLearners> dataModelCompanyLearnersAuto;
	private int companyLearnerAutoCount;
	private LazyDataModel<CompanyLearners> dataModelCompanyLearnersOther;
	private int companyLearnerOtherCount;
	private LazyDataModel<MandatoryGrant> dataModelMandatoryGrant;
	private int mandatoryGrantCount;

	private WorkplaceMonitoringMitigation workplaceMonitoringMitigation;
	private LazyDataModel<WorkplaceMonitoringMitigation> dataModelWorkplaceMonitoringMitigation;
	private CompanyLearnersService companyLearnersService = new CompanyLearnersService();
	private MandatoryGrantService mandatoryGrantService = new MandatoryGrantService();
	private LearnerMonitoringSurvey learnerMonitoringSurvey;

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
	 * Initialize method to get all WorkplaceMonitoring and prepare a for a create
	 * of a new WorkplaceMonitoring
	 * 
	 * @author TechFinium
	 * @see WorkplaceMonitoring
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.WorkplaceMonitoring) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			this.workplacemonitoring = service.findByKey(getSessionUI().getTask().getTargetKey());
			service.prepareNewRegistration(workplacemonitoring);
			prepnewLearnerInduction();
			this.purposeOfSiteVisits = purposeOfSiteVisitsService.allPurposeOfSiteVisit();
			this.selectedPurposeOfSiteVisits = purposeOfSiteVisitsService.findByWorkplaceMonitoring(workplacemonitoring);
			this.learnerMonitoringSurveys = learnerMonitoringSurveyService.findByWorkplaceMonitoring(workplacemonitoring, WorkplaceSurveyType.LEARNERMONITORINGSURVEY);
			this.dgMonitoringSurveys = learnerMonitoringSurveyService.findByWorkplaceMonitoring(workplacemonitoring, WorkplaceSurveyType.DISCRETIONARYGRANTMONITORING);
			this.otherSurveys = learnerMonitoringSurveyService.findByWorkplaceMonitoring(workplacemonitoring, WorkplaceSurveyType.OTHER);
			datamodelInfo();
		} else {
			prepareNew();
			workplacemonitoringInfo();
		}
	}

	private void prepnewLearnerInduction() {
		try {
			learnerInduction = new LearnerInduction();
			learnerInduction.setWorkplaceMonitoring(workplacemonitoring);
			learnerInductions = learnerInductionService.findByWorkplaceMonitoring(workplacemonitoring);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void datamodelInfo() {
		try {
			dataModelWorkplaceMonitoringMitigation = new WorkplaceMonitoringMitigationDataModel(workplacemonitoring);
			dataModelCompanyLearners = new CompanyLearnersLearnershipDataModel(workplacemonitoring);
			companyLearnerCount = companyLearnersService.count(new HashMap<>(), workplacemonitoring.getCompany(), InterventionTypeEnum.Learnership);

			dataModelCompanyLearnersCBMT = new CompanyLearnersTradeDataModel(workplacemonitoring, 1l);
			companyLearnerCBMTCount = companyLearnersService.count(new HashMap<>(), workplacemonitoring.getCompany(), 1l);

			dataModelCompanyLearnersMotor = new CompanyLearnersTradeDataModel(workplacemonitoring, 2l);
			companyLearnerMotorCount = companyLearnersService.count(new HashMap<>(), workplacemonitoring.getCompany(), 2l);

			dataModelCompanyLearnersMetal = new CompanyLearnersTradeDataModel(workplacemonitoring, 3l);
			companyLearnerMetalCount = companyLearnersService.count(new HashMap<>(), workplacemonitoring.getCompany(), 3l);

			dataModelCompanyLearnersAuto = new CompanyLearnersTradeDataModel(workplacemonitoring, 4l);
			companyLearnerAutoCount = companyLearnersService.count(new HashMap<>(), workplacemonitoring.getCompany(), 4l);

			dataModelMandatoryGrant = new MandatoryGrantDataModel(workplacemonitoring.getCompany(), WspReportEnum.WSP, HAJConstants.DISC_FUNDING_ID);
			mandatoryGrantCount = mandatoryGrantService.count(MandatoryGrant.class, new HashMap<>(), workplacemonitoring.getCompany());

			dataModelCompanyLearnersOther = new CompanyLearnersOther(workplacemonitoring);
			companyLearnerOtherCount = companyLearnersService.countNotDesignatedTradeAndLearnership(new HashMap<>(), workplacemonitoring.getCompany(), InterventionTypeEnum.Learnership);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addMitigationPlan() {
		WorkplaceMonitoringMitigation workplaceMonitoringMitigation = new WorkplaceMonitoringMitigation();
		workplaceMonitoringMitigation.setWorkplaceMonitoring(workplacemonitoring);
		workplaceMonitoringMitigation.setNonComplianceIssue(learnerMonitoringSurvey.getQuestion());
//		workplaceMonitoringMitigation
	}

	public void addToList() {
		try {
			learnerInductionService.create(learnerInduction);
			prepnewLearnerInduction();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeFromList() {
		try {
			learnerInductionService.delete(learnerInduction);
			prepnewLearnerInduction();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void selectPurposeOfSiteVisit() {
		try {
			String inStatement = "";
			for (PurposeOfSiteVisit purposeOfSiteVisit : selectedPurposeOfSiteVisits) {
				inStatement = inStatement + "," + purposeOfSiteVisit.getId();
			}
			for (PurposeOfSiteVisit purposeOfSiteVisit : purposeOfSiteVisits) {
				inStatement = inStatement + "," + purposeOfSiteVisit.getId();
			}
			inStatement = "(" + inStatement.substring(1, inStatement.length()) + ")";
			List<PurposeOfSiteVisit> psv = new ArrayList<>();
			for (PurposeOfSiteVisit purposeOfSiteVisit : selectedPurposeOfSiteVisits) {
				psv.addAll(purposeOfSiteVisitsService.allPurposeOfSiteVisitByParent(purposeOfSiteVisit, inStatement));
			}
			purposeOfSiteVisits.addAll(psv);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get all WorkplaceMonitoring for data table
	 * 
	 * @author TechFinium
	 * @see WorkplaceMonitoring
	 */
	public void workplacemonitoringInfo() {
		dataModel = new LazyDataModel<WorkplaceMonitoring>() {

			private static final long serialVersionUID = 1L;
			private List<WorkplaceMonitoring> retorno = new ArrayList<WorkplaceMonitoring>();

			@Override
			public List<WorkplaceMonitoring> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allWorkplaceMonitoring(WorkplaceMonitoring.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(WorkplaceMonitoring.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(WorkplaceMonitoring obj) {
				return obj.getId();
			}

			@Override
			public WorkplaceMonitoring getRowData(String rowKey) {
				for (WorkplaceMonitoring obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};
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
				doc.setTargetKey(workplacemonitoring.getId());
				doc.setTargetClass(WorkplaceMonitoring.class.getName());
				if (doc.getId() == null) DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), getSessionUI().getActiveUser());
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Insert WorkplaceMonitoring into database
	 * 
	 * @author TechFinium
	 * @see WorkplaceMonitoring
	 */
	public void workplacemonitoringInsert() {
		try {
			service.create(this.workplacemonitoring);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			workplacemonitoringInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update WorkplaceMonitoring in database
	 * 
	 * @author TechFinium
	 * @see WorkplaceMonitoring
	 */
	public void workplacemonitoringUpdate() {
		try {
			service.update(this.workplacemonitoring);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			workplacemonitoringInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete WorkplaceMonitoring from database
	 * 
	 * @author TechFinium
	 * @see WorkplaceMonitoring
	 */
	public void workplacemonitoringDelete() {
		try {
			service.delete(this.workplacemonitoring);
			prepareNew();
			workplacemonitoringInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of WorkplaceMonitoring
	 * 
	 * @author TechFinium
	 * @see WorkplaceMonitoring
	 */
	public void prepareNew() {
		workplacemonitoring = new WorkplaceMonitoring();
	}

	/*
	 * public List<SelectItem> getWorkplaceMonitoringDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * workplacemonitoringInfo(); for (WorkplaceMonitoring ug :
	 * workplacemonitoringList) { // l.add(new SelectItem(ug.getUserGroupId(),
	 * ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<WorkplaceMonitoring> complete(String desc) {
		List<WorkplaceMonitoring> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public void requesteWorkflow() {
		try {
			service.requesteWorkflow(workplacemonitoring, getSessionUI().getActiveUser());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void completeWorkflow() {
		try {
			service.completeWorkflow(workplacemonitoring, getSessionUI().getActiveUser(), getSessionUI().getTask(), learnerMonitoringSurveys, dgMonitoringSurveys, selectedPurposeOfSiteVisits);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void approveWorkflow() {
		try {
			service.approveWorkflow(workplacemonitoring, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void rejectWorkflow() {
		try {
			service.rejectWorkflow(workplacemonitoring, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void finalApproveWorkflow() {
		try {
			service.finalApproveWorkflow(workplacemonitoring, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void finalRejectWorkflow() {
		try {
			service.finalRejectWorkflow(workplacemonitoring, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<WorkplaceMonitoring> getWorkplaceMonitoringList() {
		return workplacemonitoringList;
	}

	public void setWorkplaceMonitoringList(List<WorkplaceMonitoring> workplacemonitoringList) {
		this.workplacemonitoringList = workplacemonitoringList;
	}

	public WorkplaceMonitoring getWorkplacemonitoring() {
		return workplacemonitoring;
	}

	public void setWorkplacemonitoring(WorkplaceMonitoring workplacemonitoring) {
		this.workplacemonitoring = workplacemonitoring;
	}

	public List<WorkplaceMonitoring> getWorkplaceMonitoringfilteredList() {
		return workplacemonitoringfilteredList;
	}

	public void setWorkplaceMonitoringfilteredList(List<WorkplaceMonitoring> workplacemonitoringfilteredList) {
		this.workplacemonitoringfilteredList = workplacemonitoringfilteredList;
	}

	public LazyDataModel<WorkplaceMonitoring> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<WorkplaceMonitoring> dataModel) {
		this.dataModel = dataModel;
	}

	public LearnerInduction getLearnerInduction() {
		return learnerInduction;
	}

	public void setLearnerInduction(LearnerInduction learnerInduction) {
		this.learnerInduction = learnerInduction;
	}

	public List<LearnerInduction> getLearnerInductions() {
		return learnerInductions;
	}

	public void setLearnerInductions(List<LearnerInduction> learnerInductions) {
		this.learnerInductions = learnerInductions;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public List<PurposeOfSiteVisit> getPurposeOfSiteVisits() {
		return purposeOfSiteVisits;
	}

	public void setPurposeOfSiteVisits(List<PurposeOfSiteVisit> purposeOfSiteVisits) {
		this.purposeOfSiteVisits = purposeOfSiteVisits;
	}

	public List<PurposeOfSiteVisit> getSelectedPurposeOfSiteVisits() {
		return selectedPurposeOfSiteVisits;
	}

	public void setSelectedPurposeOfSiteVisits(List<PurposeOfSiteVisit> selectedPurposeOfSiteVisits) {
		this.selectedPurposeOfSiteVisits = selectedPurposeOfSiteVisits;
	}

	public List<LearnerMonitoringSurvey> getLearnerMonitoringSurveys() {
		return learnerMonitoringSurveys;
	}

	public void setLearnerMonitoringSurveys(List<LearnerMonitoringSurvey> learnerMonitoringSurveys) {
		this.learnerMonitoringSurveys = learnerMonitoringSurveys;
	}

	public List<LearnerMonitoringSurvey> getDgMonitoringSurveys() {
		return dgMonitoringSurveys;
	}

	public void setDgMonitoringSurveys(List<LearnerMonitoringSurvey> dgMonitoringSurveys) {
		this.dgMonitoringSurveys = dgMonitoringSurveys;
	}

	public LazyDataModel<CompanyLearners> getDataModelCompanyLearners() {
		return dataModelCompanyLearners;
	}

	public void setDataModelCompanyLearners(LazyDataModel<CompanyLearners> dataModelCompanyLearners) {
		this.dataModelCompanyLearners = dataModelCompanyLearners;
	}

	public LazyDataModel<CompanyLearners> getDataModelCompanyLearnersCBMT() {
		return dataModelCompanyLearnersCBMT;
	}

	public void setDataModelCompanyLearnersCBMT(LazyDataModel<CompanyLearners> dataModelCompanyLearnersCBMT) {
		this.dataModelCompanyLearnersCBMT = dataModelCompanyLearnersCBMT;
	}

	public LazyDataModel<CompanyLearners> getDataModelCompanyLearnersMotor() {
		return dataModelCompanyLearnersMotor;
	}

	public void setDataModelCompanyLearnersMotor(LazyDataModel<CompanyLearners> dataModelCompanyLearnersMotor) {
		this.dataModelCompanyLearnersMotor = dataModelCompanyLearnersMotor;
	}

	public LazyDataModel<CompanyLearners> getDataModelCompanyLearnersMetal() {
		return dataModelCompanyLearnersMetal;
	}

	public void setDataModelCompanyLearnersMetal(LazyDataModel<CompanyLearners> dataModelCompanyLearnersMetal) {
		this.dataModelCompanyLearnersMetal = dataModelCompanyLearnersMetal;
	}

	public LazyDataModel<WorkplaceMonitoringMitigation> getDataModelWorkplaceMonitoringMitigation() {
		return dataModelWorkplaceMonitoringMitigation;
	}

	public void setDataModelWorkplaceMonitoringMitigation(LazyDataModel<WorkplaceMonitoringMitigation> dataModelWorkplaceMonitoringMitigation) {
		this.dataModelWorkplaceMonitoringMitigation = dataModelWorkplaceMonitoringMitigation;
	}

	public WorkplaceMonitoringMitigation getWorkplaceMonitoringMitigation() {
		return workplaceMonitoringMitigation;
	}

	public void setWorkplaceMonitoringMitigation(WorkplaceMonitoringMitigation workplaceMonitoringMitigation) {
		this.workplaceMonitoringMitigation = workplaceMonitoringMitigation;
	}

	public LazyDataModel<MandatoryGrant> getDataModelMandatoryGrant() {
		return dataModelMandatoryGrant;
	}

	public void setDataModelMandatoryGrant(LazyDataModel<MandatoryGrant> dataModelMandatoryGrant) {
		this.dataModelMandatoryGrant = dataModelMandatoryGrant;
	}

	public int getCompanyLearnerCount() {
		return companyLearnerCount;
	}

	public void setCompanyLearnerCount(int companyLearnerCount) {
		this.companyLearnerCount = companyLearnerCount;
	}

	public int getCompanyLearnerCBMTCount() {
		return companyLearnerCBMTCount;
	}

	public void setCompanyLearnerCBMTCount(int companyLearnerCBMTCount) {
		this.companyLearnerCBMTCount = companyLearnerCBMTCount;
	}

	public int getCompanyLearnerMotorCount() {
		return companyLearnerMotorCount;
	}

	public void setCompanyLearnerMotorCount(int companyLearnerMotorCount) {
		this.companyLearnerMotorCount = companyLearnerMotorCount;
	}

	public int getCompanyLearnerMetalCount() {
		return companyLearnerMetalCount;
	}

	public void setCompanyLearnerMetalCount(int companyLearnerMetalCount) {
		this.companyLearnerMetalCount = companyLearnerMetalCount;
	}

	public int getMandatoryGrantCount() {
		return mandatoryGrantCount;
	}

	public void setMandatoryGrantCount(int mandatoryGrantCount) {
		this.mandatoryGrantCount = mandatoryGrantCount;
	}

	public CompanyLearnersService getCompanyLearnersService() {
		return companyLearnersService;
	}

	public void setCompanyLearnersService(CompanyLearnersService companyLearnersService) {
		this.companyLearnersService = companyLearnersService;
	}

	public LazyDataModel<CompanyLearners> getDataModelCompanyLearnersOther() {
		return dataModelCompanyLearnersOther;
	}

	public void setDataModelCompanyLearnersOther(LazyDataModel<CompanyLearners> dataModelCompanyLearnersOther) {
		this.dataModelCompanyLearnersOther = dataModelCompanyLearnersOther;
	}

	public int getCompanyLearnerOtherCount() {
		return companyLearnerOtherCount;
	}

	public void setCompanyLearnerOtherCount(int companyLearnerOtherCount) {
		this.companyLearnerOtherCount = companyLearnerOtherCount;
	}

	public List<LearnerMonitoringSurvey> getOtherSurveys() {
		return otherSurveys;
	}

	public void setOtherSurveys(List<LearnerMonitoringSurvey> otherSurveys) {
		this.otherSurveys = otherSurveys;
	}

	public LazyDataModel<CompanyLearners> getDataModelCompanyLearnersAuto() {
		return dataModelCompanyLearnersAuto;
	}

	public void setDataModelCompanyLearnersAuto(LazyDataModel<CompanyLearners> dataModelCompanyLearnersAuto) {
		this.dataModelCompanyLearnersAuto = dataModelCompanyLearnersAuto;
	}

	public int getCompanyLearnerAutoCount() {
		return companyLearnerAutoCount;
	}

	public void setCompanyLearnerAutoCount(int companyLearnerAutoCount) {
		this.companyLearnerAutoCount = companyLearnerAutoCount;
	}

	public LearnerMonitoringSurvey getLearnerMonitoringSurvey() {
		return learnerMonitoringSurvey;
	}

	public void setLearnerMonitoringSurvey(LearnerMonitoringSurvey learnerMonitoringSurvey) {
		this.learnerMonitoringSurvey = learnerMonitoringSurvey;
	}

}
