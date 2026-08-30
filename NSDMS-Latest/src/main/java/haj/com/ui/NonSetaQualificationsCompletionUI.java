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

import haj.com.constants.HAJConstants;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.Doc;
import haj.com.entity.NonSetaQualificationsCompletion;
import haj.com.entity.SummativeAssessmentReport;
import haj.com.entity.SummativeAssessmentReportUnitStandards;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompetenceEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.entity.lookup.SkillsSet;
import haj.com.entity.lookup.UnitStandards;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyLearnersService;
import haj.com.service.DocService;
import haj.com.service.NonSetaQualificationsCompletionService;
import haj.com.service.SummativeAssessmentReportService;
import haj.com.service.TasksService;
import haj.com.service.UnitStandardsService;
import haj.com.service.lookup.RejectReasonsService;

import static haj.com.constants.HAJConstants.SKILLS_PROGRAM_LIST;
import static haj.com.constants.HAJConstants.SKILLS_SET_LIST;

@ManagedBean(name = "nonsetaqualificationscompletionUI")
@ViewScoped
public class NonSetaQualificationsCompletionUI extends AbstractUI {

	private NonSetaQualificationsCompletionService service = new NonSetaQualificationsCompletionService();
	private UnitStandardsService unitStandardsService = new UnitStandardsService();
	private List<NonSetaQualificationsCompletion> nonsetaqualificationscompletionList = null;
	private List<NonSetaQualificationsCompletion> nonsetaqualificationscompletionfilteredList = null;
	private NonSetaQualificationsCompletion nonsetaqualificationscompletion = null;
	private SummativeAssessmentReport summativeAssessmentReport;
	private SummativeAssessmentReportService summativeAssessmentReportService = new SummativeAssessmentReportService();
	private LazyDataModel<SummativeAssessmentReport> dataModelSummativeAssessmentReport;
	private LazyDataModel<NonSetaQualificationsCompletion> dataModel;
	private CompanyLearners  companyLearners;
	private Doc doc;
	private boolean skillsSet;
	private boolean skillsProgram;
	private boolean shortCreditBearing;
	private boolean newSkills;
	private List<SummativeAssessmentReportUnitStandards> summativeAssessmentReportUnitStandards = new ArrayList<>();
	private List<UnitStandards> unitStandards = new ArrayList<>();
	private LazyDataModel<UnitStandards> dataModelUnitStandards;
	private UnitStandards unitStandard = null;
	private Company trainingProvider;
	
	private ArrayList<RejectReasons> selectedRejectReason=new ArrayList<>();
	private List<RejectReasons> rejectReason=new ArrayList<>();

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
	 * Initialize method to get all NonSetaQualificationsCompletion and prepare a
	 * for a create of a new NonSetaQualificationsCompletion
	 * 
	 * @author TechFinium
	 * @see NonSetaQualificationsCompletion
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.RegisterNonSetaQualification) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			this.nonsetaqualificationscompletion = service.findByKeyAndPopulateLearnerDocs(getSessionUI().getTask().getTargetKey());
			this.companyLearners = CompanyLearnersService.instance().findByKey(this.nonsetaqualificationscompletion.getCompanyLearners().getId());
			this.companyLearners.setDocs(DocService.instance().searchByTargetKeyAndClassNoConfigDoc(CompanyLearners.class.getName(), companyLearners.getId()));
			//service.prepareNewRegistration(nonsetaqualificationscompletion, CompanyUserTypeEnum.Company);
			service.prepareNewLearnershipDevelopmentRegistrationDocs(ConfigDocProcessEnum.RegisterNonSetaQualification, nonsetaqualificationscompletion, nonsetaqualificationscompletion, getSessionUI().getTask().getProcessRole());
			
			summativeAssessmentReportUnitStandards = service.findBySummativeAssessmentRepor(nonsetaqualificationscompletion.getSummativeAssessmentReport());
			for(SummativeAssessmentReportUnitStandards summativeAssessmentReportUnitStandard : summativeAssessmentReportUnitStandards) {
				if(nonsetaqualificationscompletion!=null && nonsetaqualificationscompletion.getModerationDate()!=null) {
					summativeAssessmentReportUnitStandard.setCompetenceEnum(CompetenceEnum.Competent);
					//summativeAssessmentReportUnitStandard.setAssesmentDate(nonsetaqualificationscompletion.getModerationDate());
				}else {
					summativeAssessmentReportUnitStandard.setCompetenceEnum(CompetenceEnum.Competent);
					//summativeAssessmentReportUnitStandard.setAssesmentDate(getNow());
				}				
			}
			//prepSummativeAssesment();
			//applyInterventionDataCompanyLearners();
			if(nonsetaqualificationscompletion.getStatus() ==ApprovalEnum.Rejected) {
				populateRejectReasons();
			}
		} else {
			prepareNew();
			nonsetaqualificationscompletionInfo();
		}
	}

	public void prepSummativeAssesment() {
		
		try {
			service.resolveUnitStandarts(nonsetaqualificationscompletion.getSummativeAssessmentReport());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		/*
		 * summativeAssessmentReport = new SummativeAssessmentReport();
		 * summativeAssessmentReport.setNonSetaQualificationsCompletion(
		 * nonsetaqualificationscompletion); try { //unitStandards =
		 * unitStandardsService.findByQualification(nonsetaqualificationscompletion.
		 * getCompanyLearners().getQualification().getCode()); unitStandards =
		 * unitStandardsService.findByQualification(23114); } catch (Exception e) {
		 * e.printStackTrace(); } summativeassessmentreportInfo();
		 */
	}
	


	public void updateUnitStandards() {
		try {
			summativeAssessmentReportService.updateUnitStandards(summativeAssessmentReport);
			prepSummativeAssesment();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addSummativeAssesment() {
		try {
			summativeAssessmentReportService.create(summativeAssessmentReport, unitStandards);
			prepSummativeAssesment();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void summativeassessmentreportInfo() {

		dataModelSummativeAssessmentReport = new LazyDataModel<SummativeAssessmentReport>() {

			private static final long serialVersionUID = 1L;
			private List<SummativeAssessmentReport> retorno = new ArrayList<SummativeAssessmentReport>();

			@Override
			public List<SummativeAssessmentReport> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					filters.put("nonSetaQualificationsCompletionID", nonsetaqualificationscompletion.getId());
					retorno = summativeAssessmentReportService.allSummativeAssessmentReportForNonSetaQualificationsCompletion(first, pageSize, sortField, sortOrder, filters);
					dataModelSummativeAssessmentReport.setRowCount(summativeAssessmentReportService.countForNonSetaQualificationsCompletion(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SummativeAssessmentReport obj) {
				return obj.getId();
			}

			@Override
			public SummativeAssessmentReport getRowData(String rowKey) {
				for (SummativeAssessmentReport obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	public void completeWorkflow() {
		try {
			service.completeWorkflow(nonsetaqualificationscompletion, getSessionUI().getActiveUser(), getSessionUI().getTask(), summativeAssessmentReportUnitStandards);
			ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void recommendWorkflow() {
		try {
			service.recommendWorkflow(nonsetaqualificationscompletion, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void approveWorkflow() {
		try {
			service.approveWorkflow(nonsetaqualificationscompletion, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void rejectWorkflow() {
		try {
			if (selectedRejectReason.size() == 0) throw new Exception("Please select a reject reason");
			service.rejectWorkflow(nonsetaqualificationscompletion, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
			ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void finalApproveWorkflow() {
		try {
			service.finalApproveWorkflow(nonsetaqualificationscompletion, getSessionUI().getActiveUser(), trainingProvider, getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void finalRejectWorkflow() {
		try {
			if (selectedRejectReason.size() == 0) throw new Exception("Please select a reject reason");
			service.finalRejectWorkflow(nonsetaqualificationscompletion, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
			ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setReviewDateForApproval() {
		try {
			service.update(nonsetaqualificationscompletion);
			addInfoMessage("Date Added");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void addUnitStandardToList() {
		this.unitStandards.add(unitStandard);
	}

	public void removeUnitStandardFromList() {
		this.unitStandards.remove(unitStandard);
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
				doc.setTargetKey(nonsetaqualificationscompletion.getId());
				doc.setTargetClass(NonSetaQualificationsCompletion.class.getName());
				if (doc.getId() == null) DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), getSessionUI().getActiveUser());
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void applyInterventionDataCompanyLearners() {
		try {
			if (nonsetaqualificationscompletion.getCompanyLearners().getInterventionType() != null) {
				if (SKILLS_PROGRAM_LIST.contains(nonsetaqualificationscompletion.getCompanyLearners().getInterventionType().getId())) {
					this.skillsProgram = true;
					this.skillsSet = false;
					this.shortCreditBearing = false;
					summativeAssessmentReport.setSkillsSet(null);
					summativeAssessmentReport.setUnitStandard(null);
					summativeAssessmentReport.setSkillsProgram(new SkillsProgram());
				} else if (SKILLS_SET_LIST.contains(nonsetaqualificationscompletion.getCompanyLearners().getInterventionType().getId())) {
					this.skillsProgram = false;
					this.skillsSet = true;
					this.shortCreditBearing = false;
					summativeAssessmentReport.setSkillsProgram(null);
					summativeAssessmentReport.setUnitStandard(null);
					summativeAssessmentReport.setSkillsSet(new SkillsSet());
				} else if (nonsetaqualificationscompletion.getCompanyLearners().getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
					summativeAssessmentReport.setSkillsProgram(null);
					summativeAssessmentReport.setSkillsSet(null);
					this.skillsProgram = false;
					this.skillsSet = false;
					this.shortCreditBearing = true;
				} else {
					summativeAssessmentReport.setSkillsProgram(null);
					summativeAssessmentReport.setSkillsSet(null);
					summativeAssessmentReport.setUnitStandard(null);
					this.skillsProgram = false;
					this.skillsSet = false;
					this.shortCreditBearing = false;
				}
				summativeAssessmentReportService.applyInterventionData(summativeAssessmentReport);
				// companylearners();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void applyInterventionData() {
		try {
			if (summativeAssessmentReport.getInterventionType() != null) {
				if (SKILLS_PROGRAM_LIST.contains(summativeAssessmentReport.getInterventionType().getId())) {
					this.skillsProgram = true;
					this.skillsSet = false;
					this.shortCreditBearing = false;
					summativeAssessmentReport.setSkillsSet(null);
					summativeAssessmentReport.setUnitStandard(null);
					summativeAssessmentReport.setSkillsProgram(new SkillsProgram());
				} else if (SKILLS_SET_LIST.contains(summativeAssessmentReport.getInterventionType().getId())) {
					this.skillsProgram = false;
					this.skillsSet = true;
					this.shortCreditBearing = false;
					summativeAssessmentReport.setSkillsProgram(null);
					summativeAssessmentReport.setUnitStandard(null);
					summativeAssessmentReport.setSkillsSet(new SkillsSet());
				} else if (summativeAssessmentReport.getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
					summativeAssessmentReport.setSkillsProgram(null);
					summativeAssessmentReport.setSkillsSet(null);
					this.skillsProgram = false;
					this.skillsSet = false;
					this.shortCreditBearing = true;
				} else {
					summativeAssessmentReport.setSkillsProgram(null);
					summativeAssessmentReport.setSkillsSet(null);
					summativeAssessmentReport.setUnitStandard(null);
					this.skillsProgram = false;
					this.skillsSet = false;
					this.shortCreditBearing = false;
				}
				summativeAssessmentReportService.applyInterventionData(summativeAssessmentReport);
				// companylearners();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get all NonSetaQualificationsCompletion for data table
	 * 
	 * @author TechFinium
	 * @see NonSetaQualificationsCompletion
	 */
	public void nonsetaqualificationscompletionInfo() {

		dataModel = new LazyDataModel<NonSetaQualificationsCompletion>() {

			private static final long serialVersionUID = 1L;
			private List<NonSetaQualificationsCompletion> retorno = new ArrayList<NonSetaQualificationsCompletion>();

			@Override
			public List<NonSetaQualificationsCompletion> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allNonSetaQualificationsCompletion(NonSetaQualificationsCompletion.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(NonSetaQualificationsCompletion.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(NonSetaQualificationsCompletion obj) {
				return obj.getId();
			}

			@Override
			public NonSetaQualificationsCompletion getRowData(String rowKey) {
				for (NonSetaQualificationsCompletion obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert NonSetaQualificationsCompletion into database
	 * 
	 * @author TechFinium
	 * @see NonSetaQualificationsCompletion
	 */
	public void nonsetaqualificationscompletionInsert() {
		try {
			service.create(this.nonsetaqualificationscompletion);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			nonsetaqualificationscompletionInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update NonSetaQualificationsCompletion in database
	 * 
	 * @author TechFinium
	 * @see NonSetaQualificationsCompletion
	 */
	public void nonsetaqualificationscompletionUpdate() {
		try {
			service.update(this.nonsetaqualificationscompletion);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			nonsetaqualificationscompletionInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete NonSetaQualificationsCompletion from database
	 * 
	 * @author TechFinium
	 * @see NonSetaQualificationsCompletion
	 */
	public void nonsetaqualificationscompletionDelete() {
		try {
			service.delete(this.nonsetaqualificationscompletion);
			prepareNew();
			nonsetaqualificationscompletionInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of NonSetaQualificationsCompletion
	 * 
	 * @author TechFinium
	 * @see NonSetaQualificationsCompletion
	 */
	public void prepareNew() {
		nonsetaqualificationscompletion = new NonSetaQualificationsCompletion();
	}

	/*
	 * public List<SelectItem> getNonSetaQualificationsCompletionDD() {
	 * List<SelectItem> l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * nonsetaqualificationscompletionInfo(); for (NonSetaQualificationsCompletion
	 * ug : nonsetaqualificationscompletionList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<NonSetaQualificationsCompletion> complete(String desc) {
		List<NonSetaQualificationsCompletion> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<RejectReasons> getRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.RegisterNonSetaQualification);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	private void populateRejectReasons() {
		RejectReasonsService rs= new RejectReasonsService();
		try {
			rejectReason = rs.locateReasonsSelectedByTargetKeyClassAndProcess(nonsetaqualificationscompletion.getId(), NonSetaQualificationsCompletion.class.getName(), ConfigDocProcessEnum.RegisterNonSetaQualification);			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	public List<NonSetaQualificationsCompletion> getNonSetaQualificationsCompletionList() {
		return nonsetaqualificationscompletionList;
	}

	public void setNonSetaQualificationsCompletionList(List<NonSetaQualificationsCompletion> nonsetaqualificationscompletionList) {
		this.nonsetaqualificationscompletionList = nonsetaqualificationscompletionList;
	}

	public NonSetaQualificationsCompletion getNonsetaqualificationscompletion() {
		return nonsetaqualificationscompletion;
	}

	public void setNonsetaqualificationscompletion(NonSetaQualificationsCompletion nonsetaqualificationscompletion) {
		this.nonsetaqualificationscompletion = nonsetaqualificationscompletion;
	}

	public List<NonSetaQualificationsCompletion> getNonSetaQualificationsCompletionfilteredList() {
		return nonsetaqualificationscompletionfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param nonsetaqualificationscompletionfilteredList
	 *            the new nonsetaqualificationscompletionfilteredList list
	 * @see NonSetaQualificationsCompletion
	 */
	public void setNonSetaQualificationsCompletionfilteredList(List<NonSetaQualificationsCompletion> nonsetaqualificationscompletionfilteredList) {
		this.nonsetaqualificationscompletionfilteredList = nonsetaqualificationscompletionfilteredList;
	}

	public LazyDataModel<NonSetaQualificationsCompletion> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<NonSetaQualificationsCompletion> dataModel) {
		this.dataModel = dataModel;
	}

	public SummativeAssessmentReport getSummativeAssessmentReport() {
		return summativeAssessmentReport;
	}

	public void setSummativeAssessmentReport(SummativeAssessmentReport summativeAssessmentReport) {
		this.summativeAssessmentReport = summativeAssessmentReport;
	}

	public LazyDataModel<SummativeAssessmentReport> getDataModelSummativeAssessmentReport() {
		return dataModelSummativeAssessmentReport;
	}

	public void setDataModelSummativeAssessmentReport(LazyDataModel<SummativeAssessmentReport> dataModelSummativeAssessmentReport) {
		this.dataModelSummativeAssessmentReport = dataModelSummativeAssessmentReport;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public boolean isSkillsSet() {
		return skillsSet;
	}

	public void setSkillsSet(boolean skillsSet) {
		this.skillsSet = skillsSet;
	}

	public boolean isSkillsProgram() {
		return skillsProgram;
	}

	public void setSkillsProgram(boolean skillsProgram) {
		this.skillsProgram = skillsProgram;
	}

	public boolean isShortCreditBearing() {
		return shortCreditBearing;
	}

	public void setShortCreditBearing(boolean shortCreditBearing) {
		this.shortCreditBearing = shortCreditBearing;
	}

	public List<UnitStandards> getUnitStandards() {
		return unitStandards;
	}

	public void setUnitStandards(List<UnitStandards> unitStandards) {
		this.unitStandards = unitStandards;
	}

	public UnitStandards getUnitStandard() {
		return unitStandard;
	}

	public void setUnitStandard(UnitStandards unitStandard) {
		this.unitStandard = unitStandard;
	}

	public boolean isNewSkills() {
		return newSkills;
	}

	public void setNewSkills(boolean newSkills) {
		this.newSkills = newSkills;
	}

	public ArrayList<RejectReasons> getSelectedRejectReason() {
		return selectedRejectReason;
	}

	public void setSelectedRejectReason(ArrayList<RejectReasons> selectedRejectReason) {
		this.selectedRejectReason = selectedRejectReason;
	}

	public Company getTrainingProvider() {
		return trainingProvider;
	}

	public void setTrainingProvider(Company trainingProvider) {
		this.trainingProvider = trainingProvider;
	}

	public LazyDataModel<UnitStandards> getDataModelUnitStandards() {
		return dataModelUnitStandards;
	}

	public void setDataModelUnitStandards(LazyDataModel<UnitStandards> dataModelUnitStandards) {
		this.dataModelUnitStandards = dataModelUnitStandards;
	}

	public List<SummativeAssessmentReportUnitStandards> getSummativeAssessmentReportUnitStandards() {
		return summativeAssessmentReportUnitStandards;
	}

	public void setSummativeAssessmentReportUnitStandards(
			List<SummativeAssessmentReportUnitStandards> summativeAssessmentReportUnitStandards) {
		this.summativeAssessmentReportUnitStandards = summativeAssessmentReportUnitStandards;
	}

	public List<RejectReasons> getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(List<RejectReasons> rejectReason) {
		this.rejectReason = rejectReason;
	}

	public CompanyLearners getCompanyLearners() {
		return companyLearners;
	}

	public void setCompanyLearners(CompanyLearners companyLearners) {
		this.companyLearners = companyLearners;
	}
}
