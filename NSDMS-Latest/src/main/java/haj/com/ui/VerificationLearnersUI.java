package haj.com.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.omnifaces.util.Faces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.entity.AssessorModeratorApplication;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.Doc;
import haj.com.entity.ScheduledEvent;
import haj.com.entity.TrainingProviderVerfication;
import haj.com.entity.SummativeAssessmentReport;
import haj.com.entity.SummativeAssessmentReportUnitStandards;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.AssessorModeratorAppTypeEnum;
import haj.com.entity.enums.CompetenceEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.InterventionTypeEnum;
import haj.com.entity.enums.NewOrLegacyEnum;
import haj.com.entity.enums.QualificationTypeSelectionEnum;
import haj.com.entity.lookup.LegacyAssessorAccreditation;
import haj.com.entity.lookup.LegacyModeratorAccreditation;
import haj.com.entity.lookup.Qualification;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AssessorModeratorApplicationService;
import haj.com.service.CompanyLearnersService;
import haj.com.service.DocService;
import haj.com.service.SummativeAssessmentReportService;
import haj.com.service.SummativeAssessmentReportUnitStandardsService;
import haj.com.service.TrainingProviderVerficationService;
import haj.com.service.lookup.LegacyAssessorAccreditationService;
import haj.com.service.lookup.LegacyModeratorAccreditationService;

import static haj.com.constants.HAJConstants.SKILLS_PROGRAM_LIST;
import static haj.com.constants.HAJConstants.SKILLS_SET_LIST;

@ManagedBean(name = "verificationLearnersUI")
@ViewScoped
public class VerificationLearnersUI extends AbstractUI {

	private TrainingProviderVerficationService service = new TrainingProviderVerficationService();
	private List<TrainingProviderVerfication> trainingproviderverficationList = null;
	private List<TrainingProviderVerfication> trainingproviderverficationfilteredList = null;
	private TrainingProviderVerfication trainingproviderverfication = null;
	private LazyDataModel<TrainingProviderVerfication> dataModel;
	private LazyDataModel<Company> tpDataModel; 
	private SummativeAssessmentReportService summativeAssessmentReportService = new SummativeAssessmentReportService();
	private LazyDataModel<SummativeAssessmentReport> dataModelSummativeAssessmentReport;
	private SummativeAssessmentReport assessmentReport;
	private SummativeAssessmentReportUnitStandards summativeAssessmentReportUnitStandards;
	private SummativeAssessmentReportUnitStandardsService summativeAssessmentReportUnitStandardsService = new SummativeAssessmentReportUnitStandardsService();
	private Doc doc;
	boolean provided = true;
	boolean show = false;
	boolean showlearners = false;
	private Company selectedCompany;
	private CompanyLearners companyLearners;
	private ScheduledEvent scheduledEvent;
	boolean assessor ;
	boolean applyAssessor ;
	boolean applyModerator ;
	
	private NewOrLegacyEnum newOrLegacyEnum;
	private CompetenceEnum competenceEnum;
	private Date moderationDate;
	private Date assesmentDate;
	
	private String idnumber;
	private AssessorModeratorApplication assessorModeratorApplication;
	private LegacyAssessorAccreditation legacyAssessorAccreditation;
	private LegacyModeratorAccreditation legacyModeratorAccreditation;
	
	private AssessorModeratorApplicationService assessorModeratorApplicationService = new AssessorModeratorApplicationService();
	private LegacyAssessorAccreditationService legacyAssessorAccreditationService = new LegacyAssessorAccreditationService();
	private LegacyModeratorAccreditationService legacyModeratorAccreditationService = new LegacyModeratorAccreditationService();

	private LazyDataModel<AssessorModeratorApplication> dataModelAssessorModeratorApplication;
	private LazyDataModel<LegacyModeratorAccreditation> dataModelLegacyModeratorAccreditation; 
	private LazyDataModel<LegacyAssessorAccreditation> dataModelLegacyAssessorAccreditation;

	private LazyDataModel<LegacyModeratorAccreditation> dataModelLegacyModeratorAccreditationNew; 
	private LazyDataModel<LegacyAssessorAccreditation> dataModelLegacyAssessorAccreditationNew;
	private LazyDataModel<AssessorModeratorApplication> dataModelAssessorModeratorApplicationNew;
	
	private QualificationTypeSelectionEnum qualificationTypeSelectionEnum;
	
	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			e.printStackTrace();
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void runInit() throws Exception {
		providerInfo() ;
	}
	
	public void providerInfo() {
		tpDataModel = new LazyDataModel<Company>() { 		 
			   private static final long serialVersionUID = 1L; 
			   private List<Company> retorno = new  ArrayList<Company>();		   
			   @Override 
			   public List<Company> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
					try {
						filters.put("createUserID", getSessionUI().getActiveUser().getId());
						filters.put("status", ApprovalEnum.NA);
						retorno = (List<Company>) service.allCompany(first, pageSize, sortField, sortOrder, filters);
						tpDataModel.setRowCount(service.countCompany(Company.class, filters));
					} catch (Exception e) {
						logger.fatal(e);
					} 
				    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(Company obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public Company getRowData(String rowKey) {
			        for(Company obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
	}
	
	public void verificationInfo() {	 
		 dataModel = new LazyDataModel<TrainingProviderVerfication>() { 		 
		   private static final long serialVersionUID = 1L; 
		   private List<TrainingProviderVerfication> retorno = new  ArrayList<TrainingProviderVerfication>();		   
		   @Override 
		   public List<TrainingProviderVerfication> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
				try {
					filters.put("createUserID", getSessionUI().getActiveUser().getId());
					filters.put("status", ApprovalEnum.NA);
					retorno = service.allTrainingProviderVerfication(first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.countTrainingProviderVerfication(TrainingProviderVerfication.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
		   }
		   
		    @Override
		    public Object getRowKey(TrainingProviderVerfication obj) {
		        return obj.getId();
		    }
		    
		    @Override
		    public TrainingProviderVerfication getRowData(String rowKey) {
		        for(TrainingProviderVerfication obj : retorno) {
		            if(obj.getId().equals(Long.valueOf(rowKey)))
		                return obj;
		        }
		        return null;
		    }			    
		    
		  }; 		 
	}

	public void trainingproviderverficationInfo() {
		dataModel = new LazyDataModel<TrainingProviderVerfication>() {
			private static final long serialVersionUID = 1L;
			private List<TrainingProviderVerfication> retorno = new ArrayList<TrainingProviderVerfication>();
			@Override
			public List<TrainingProviderVerfication> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					//filters.put("status", ApprovalEnum.PendingApproval);
					//filters.put("createUser", getSessionUI().getActiveUser());
					retorno = service.allTrainingProviderVerfication(TrainingProviderVerfication.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(TrainingProviderVerfication.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(TrainingProviderVerfication obj) {
				return obj.getId();
			}

			@Override
			public TrainingProviderVerfication getRowData(String rowKey) {
				for (TrainingProviderVerfication obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}
	
	public void legacymoderatoraccreditationInfo() {

		dataModelLegacyModeratorAccreditation = new LazyDataModel<LegacyModeratorAccreditation>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyModeratorAccreditation> retorno = new ArrayList<LegacyModeratorAccreditation>();

			@Override
			public List<LegacyModeratorAccreditation> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {					
					filters.put("unitStandardId", summativeAssessmentReportUnitStandards.getUnitStandards().getId());
					retorno = legacyModeratorAccreditationService.allLegacyModeratorAccreditationUnitStandards(first, pageSize, sortField, sortOrder, filters, true);					
					dataModelLegacyModeratorAccreditation.setRowCount(legacyModeratorAccreditationService.countAllLegacyModeratorAccreditationUnitStandards(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyModeratorAccreditation obj) {
				return obj.getId();
			}

			@Override
			public LegacyModeratorAccreditation getRowData(String rowKey) {
				for (LegacyModeratorAccreditation obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}
	

	public void legacyassessoraccreditationInfoNew() {
		dataModelLegacyAssessorAccreditationNew = new LazyDataModel<LegacyAssessorAccreditation>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyAssessorAccreditation> retorno = new ArrayList<LegacyAssessorAccreditation>();

			@Override
			public List<LegacyAssessorAccreditation> load(int first, int pageSize, String sortField,
					SortOrder sortOrder, Map<String, Object> filters) {
				
				try {
					CompanyLearners cl = CompanyLearnersService.instance().findByKey(trainingproviderverfication.getCompanyLearners().getId());
					if(cl.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Learnership) {
						filters.put("qualificationID", cl.getLearnership().getQualification().getId());
						retorno = legacyAssessorAccreditationService.allLegacyAssessorAccreditationQualification(first, pageSize, sortField, sortOrder, filters, true);					
						dataModelLegacyAssessorAccreditationNew.setRowCount(legacyAssessorAccreditationService.countallLegacyAssessorAccreditationQualification(filters));
					}else if(SKILLS_PROGRAM_LIST.contains(cl.getInterventionType().getId())) {
						filters.put("skillsProgrammeID", cl.getSkillsProgram().getId());
						retorno = legacyAssessorAccreditationService.allLegacyAssessorAccreditationSkillsProgram(first, pageSize, sortField, sortOrder, filters, true);					
						dataModelLegacyAssessorAccreditationNew.setRowCount(legacyAssessorAccreditationService.countallLegacyAssessorAccreditationSkillsProgram(filters));
					}else {
						Qualification qualification =CompanyLearnersService.instance().getCompanyLearnerQualification(cl);
						filters.put("qualificationID", qualification.getId());
						retorno = legacyAssessorAccreditationService.allLegacyAssessorAccreditationQualification(first, pageSize, sortField, sortOrder, filters, true);					
						dataModelLegacyAssessorAccreditationNew.setRowCount(legacyAssessorAccreditationService.countallLegacyAssessorAccreditationQualification(filters));
					}					
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyAssessorAccreditation obj) {
				return obj.getId();
			}

			@Override
			public LegacyAssessorAccreditation getRowData(String rowKey) {
				for (LegacyAssessorAccreditation obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}
	
	public void legacymoderatoraccreditationInfoNew() {

		dataModelLegacyModeratorAccreditationNew = new LazyDataModel<LegacyModeratorAccreditation>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyModeratorAccreditation> retorno = new ArrayList<LegacyModeratorAccreditation>();

			@Override
			public List<LegacyModeratorAccreditation> load(int first, int pageSize, String sortField,
					SortOrder sortOrder, Map<String, Object> filters) {

				try {
					CompanyLearners cl = CompanyLearnersService.instance().findByKey(trainingproviderverfication.getCompanyLearners().getId());					
					if(cl.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Learnership) {
						filters.put("qualificationID", cl.getLearnership().getQualification().getId());
						retorno = legacyModeratorAccreditationService.allLegacyModeratorAccreditationQualification(first, pageSize, sortField, sortOrder, filters, true);					
						dataModelLegacyModeratorAccreditationNew.setRowCount(legacyModeratorAccreditationService.countAllLegacyModeratorAccreditationQualification(filters));
					}else if(SKILLS_PROGRAM_LIST.contains(cl.getInterventionType().getId())) {
						filters.put("skillsProgrammeID", cl.getSkillsProgram().getId());
						retorno = legacyModeratorAccreditationService.allLegacyModeratorAccreditationSkillsProgram(first, pageSize, sortField, sortOrder, filters, true);					
						dataModelLegacyModeratorAccreditationNew.setRowCount(legacyModeratorAccreditationService.countAllLegacyModeratorAccreditationSkillsProgram(filters));
					}else {
						Qualification qualification =CompanyLearnersService.instance().getCompanyLearnerQualification(cl);
						filters.put("qualificationID", qualification.getId());
						retorno = legacyModeratorAccreditationService.allLegacyModeratorAccreditationQualification(first, pageSize, sortField, sortOrder, filters, true);					
						dataModelLegacyModeratorAccreditationNew.setRowCount(legacyModeratorAccreditationService.countAllLegacyModeratorAccreditationQualification(filters));
					}
					
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyModeratorAccreditation obj) {
				return obj.getId();
			}

			@Override
			public LegacyModeratorAccreditation getRowData(String rowKey) {
				for (LegacyModeratorAccreditation obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	public void updateUnitStandards() {
		try {
			checkFieldsprovided();
			if(provided) {
				summativeAssessmentReportService.updateUnitStandards(assessmentReport);
				this.trainingproviderverfication.setAssessmentDone(true);
				service.create(this.trainingproviderverfication);
			}else {
				addErrorMessage("Please provide required information");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveUnitStandards() {
		try {
			if(summativeAssessmentReportUnitStandards.getAssesmentDate() != null && summativeAssessmentReportUnitStandards.getModerationDate() != null){
				if(summativeAssessmentReportUnitStandards.getAssesmentDate().after(summativeAssessmentReportUnitStandards.getModerationDate())) {
					throw new Exception("Moderation date cannot be after assessment date");
				}
				if(summativeAssessmentReportUnitStandards.getCompetenceEnum() != null){
					if(summativeAssessmentReportUnitStandards.getAssessorApplication() == null) {
						throw new Exception("Please add an assessor");
					}
					if(summativeAssessmentReportUnitStandards.getModeratorApplication()== null) {
						throw new Exception("Please add a moderator");
					}					
					summativeAssessmentReportUnitStandardsService.update(summativeAssessmentReportUnitStandards);
				}else {
					addErrorMessage("Please provide competence");
				}
				
			}else {
				addErrorMessage("Please provide assesment/moderation date");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage());
		}		
	}
	
	public void removeAssesorDate() {
		try {
			summativeAssessmentReportUnitStandards.setAssesmentDate(null);
			summativeAssessmentReportUnitStandardsService.update(summativeAssessmentReportUnitStandards);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void removeModerationDate() {
		try {
			summativeAssessmentReportUnitStandards.setModerationDate(null);
			summativeAssessmentReportUnitStandardsService.update(summativeAssessmentReportUnitStandards);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void removeCompetence() {
		try {
			summativeAssessmentReportUnitStandards.setCompetenceEnum(null);
			summativeAssessmentReportUnitStandardsService.update(summativeAssessmentReportUnitStandards);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void removeModerator() {
		try {
			summativeAssessmentReportUnitStandards.setLegacyModeratorAccreditation(null);
			summativeAssessmentReportUnitStandards.setModeratorApplication(null);
			summativeAssessmentReportUnitStandards.setModeratorUser(null);
			summativeAssessmentReportUnitStandardsService.update(summativeAssessmentReportUnitStandards);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void removeAssesor() {
		try {
			summativeAssessmentReportUnitStandards.setLegacyAssessorAccreditation(null);
			summativeAssessmentReportUnitStandards.setAssessorApplication(null);
			summativeAssessmentReportUnitStandards.setAssessorUser(null);
			summativeAssessmentReportUnitStandardsService.update(summativeAssessmentReportUnitStandards);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepareDataModelAssesor() {
		try {
			assessor = true;
			applyAssessor = false;
			newOrLegacyEnum = NewOrLegacyEnum.NewApplication;
			assessorModeratorApplicationInfo();
			assessorModeratorApplicationInfoNew();
			legacyassessoraccreditationInfo();
			legacymoderatoraccreditationInfo();
			legacyassessoraccreditationInfoNew();
			legacymoderatoraccreditationInfoNew();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepareDataModelModerator() {
		try {
			assessor = false;
			applyModerator = false;
			newOrLegacyEnum = NewOrLegacyEnum.NewApplication;
			assessorModeratorApplicationInfo();
			assessorModeratorApplicationInfoNew();
			legacyassessoraccreditationInfo();
			legacymoderatoraccreditationInfo();
			legacyassessoraccreditationInfoNew();
			legacymoderatoraccreditationInfoNew();
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepareAssessmentDate() {
		try {
			assessor = true;
			applyAssessor = false;			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepareModerationDate() {
		try {
			assessor = false;
			applyAssessor = false;			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepareCompetence() {
		try {
			summativeAssessmentReportUnitStandards.setCompetenceEnum(CompetenceEnum.NotYetCompetent);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void downloadReport() {
		try {
			summativeAssessmentReportService.downloadReport(assessmentReport, getSessionUI().getActiveUser());
			/*
			 * checkFieldsprovided(); if(provided) {
			 * summativeAssessmentReportService.downloadReport(assessmentReport,
			 * getSessionUI().getActiveUser()); }else {
			 * addErrorMessage("Please provide required information"); }
			 */
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void checkFieldsprovided() {	
		if(assessmentReport!= null) {
			for(SummativeAssessmentReportUnitStandards sr : assessmentReport.getUnitStandards()) {
				if(sr.getCompetenceEnum() == null || sr.getAssesmentDate() == null) {
					provided = false;
					break;
				}else {
					provided = true;
				}
			}
		}
	}
	
	public void setVisitDate() {
		try {
			service.setFirstVisitDate(trainingproviderverfication, getSessionUI().getActiveUser());
			addInfoMessage("Date Added");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepareReviewDate() {
		//scheduledEvent = new ScheduledEvent();
		//scheduledEvent.setC
	}
	
	public void requestModeration() {	
		try {
			scheduledEvent.setQualificationTypeSelectionEnum(qualificationTypeSelectionEnum);
			service.createWorkflowToQualityAssuror(selectedCompany, getSessionUI().getActiveUser(), null, trainingproviderverficationfilteredList, true, scheduledEvent);
			runInit();
			show=false;
			showlearners=false;	
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void requestModerationOld() {		
		String error = "";
		try {
			List<TrainingProviderVerfication>list =service.findByCompanyAndUserAndStatus(selectedCompany, getSessionUI().getActiveUser(), ApprovalEnum.NA);
			error = preCheckSummativeAssessmentReport(list);			
			if (error.length() > 0 || !error.matches("")) {
				addErrorMessage(error);
			}else {
				service.createWorkflowToQualityAssuror(selectedCompany, getSessionUI().getActiveUser(), null, list, true,scheduledEvent);
				providerInfo();
				verificationInfo();
				show=false;
				showlearners=false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void preCheckSummativeAssessmentReport() {
		try {
			service.checkCredits(trainingproviderverfication);
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private String preCheckSummativeAssessmentReport(List<TrainingProviderVerfication>list) throws Exception{
		String error = "";
		boolean check = true;
		boolean checkassessments = true;
		for(TrainingProviderVerfication tpv:list) {
			check=service.checkCredits(tpv);
			if(!check) {
				break;
			}
			checkassessments = service.checkAssessments(tpv);
			if(!checkassessments) {
				break;
			}
		}
		
		if(!check) {
			error= "Please check that all learner meet the minimum requirements";
		}
		if(!checkassessments) {
			error = "Please check that all learner assessments have been loaded";
		}
		return error;
	}

	public void summativeassessmentreportInfo() {

		dataModelSummativeAssessmentReport = new LazyDataModel<SummativeAssessmentReport>() {
			private static final long serialVersionUID = 1L;
			private List<SummativeAssessmentReport> retorno = new ArrayList<SummativeAssessmentReport>();

			@Override
			public List<SummativeAssessmentReport> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					filters.put("trainingProviderVerficationID", trainingproviderverfication.getId());
					retorno = summativeAssessmentReportService.allSummativeAssessmentReportForVerification(first, pageSize, sortField, sortOrder, filters);
					dataModelSummativeAssessmentReport.setRowCount(summativeAssessmentReportService.countForVerification(filters));
				} catch (Exception e) {
					logger.fatal(e);
					e.printStackTrace();
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
		show=true;
	}

	public void trainingproviderverficationInsert() {
		try {
			service.create(this.trainingproviderverfication);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			trainingproviderverficationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void trainingproviderverficationUpdate() {
		try {
			service.update(this.trainingproviderverfication);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			trainingproviderverficationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void trainingproviderverficationDelete() {
		try {
			service.delete(this.trainingproviderverfication);
			prepareNew();
			trainingproviderverficationInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void prepareNew() {
		trainingproviderverfication = new TrainingProviderVerfication();
	}
	
	public void viewCompanyLearnerData() {
		verificationInfo();
		 showlearners=true;
		  populateInformation();
	}
	
	public void populateInformation() {
		try {
			if(scheduledEvent == null) {
				scheduledEvent = new ScheduledEvent();
			}
			service.prepareNewLearnershipDevelopmentRegistrationDocs(ConfigDocProcessEnum.LEARNER_ASSESSMENT_VERIFICATION, scheduledEvent);
			//summativeassessmentreportInfo();
		} catch (Exception e) {
			logger.fatal(e);
			e.printStackTrace();
		}
	}
	
	public List<TrainingProviderVerfication> complete(String desc) {
		List<TrainingProviderVerfication> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public void completeWorkflow() {
		try {
			service.completeWorkflow(trainingproviderverfication, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void completeWorkflowForModeration() {
		try {
			service.completeWorkflowForModeration(trainingproviderverfication, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void completeWorkflowToQualityAssuror() {
		try {
			service.completeWorkflowToQualityAssuror(trainingproviderverfication, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
			/*if(this.trainingproviderverfication.getAssessmentDone() != null &&  this.trainingproviderverfication.getAssessmentDone() == true) {
				service.completeWorkflowToQualityAssuror(trainingproviderverfication, getSessionUI().getActiveUser(), getSessionUI().getTask());
				ajaxRedirectToDashboard();
			}else {
				addErrorMessage("Please complete assessment before proceeding");
			}*/
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void completeWorkflowFinal() {
		try {
			service.completeWorkflowFinal(trainingproviderverfication, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void approveWorkflow() {
		try {
			//(Company company, Users user, Tasks tasks, ScheduledEvent scheduledEvent) 
			//service.approveWorkflow(trainingproviderverfication, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}


	public void finalApproveWorkflow() {
		try {
			service.finalApproveWorkflow(trainingproviderverfication, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	
	public void download(Doc doc) {
		try {
			Faces.sendFile(doc.getData(), doc.getOriginalFname(), true);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void storeNewFile(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			doc.setUsers(getSessionUI().getActiveUser());
			if (doc.getId() != null) {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
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
				doc.setTargetKey(trainingproviderverfication.getId());
				doc.setTargetClass(TrainingProviderVerfication.class.getName());
				if (doc.getId() == null) DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), getSessionUI().getActiveUser());
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public List<AssessorModeratorApplication> completeAssessorModeratorApplication(String desc) {
		AssessorModeratorApplicationService assessorModeratorApplicationService = new AssessorModeratorApplicationService();
		List<AssessorModeratorApplication> l = null;
		try {
			l = assessorModeratorApplicationService.findByCerticateNumber(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public void assessorModeratorApplicationInfo() {
		try{
			System.out.println("44444444444444444444:: trainingproviderverfication.getCompanyLearners().getId():: "+trainingproviderverfication.getCompanyLearners().getId());
			CompanyLearners cl = CompanyLearnersService.instance().findByKey(trainingproviderverfication.getCompanyLearners().getId());
			if (cl.getSkillsProgram().getId() != null){
				System.out.println("55555555555555555555:: summativeAssessmentReportUnitStandards.getUnitStandards().getId():: "+summativeAssessmentReportUnitStandards.getUnitStandards().getId());
			}
		}
		catch (Exception e) {
			logger.fatal(e);
		}
		dataModelAssessorModeratorApplication = new LazyDataModel<AssessorModeratorApplication>() {
			private static final long serialVersionUID = 1L;
			private List<AssessorModeratorApplication> retorno = new ArrayList<AssessorModeratorApplication>();
			@Override
			public List<AssessorModeratorApplication> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if(assessor) {
						filters.put("applicationType", AssessorModeratorAppTypeEnum.NewAssessorRegistration);
						filters.put("applicationType2", AssessorModeratorAppTypeEnum.AssessorReRegistration);
					}else {
						filters.put("applicationType", AssessorModeratorAppTypeEnum.NewModeratorRegistration);
						filters.put("applicationType2", AssessorModeratorAppTypeEnum.ModeratorReRegistration);
					}
					filters.put("unitStandardId", summativeAssessmentReportUnitStandards.getUnitStandards().getId());
					retorno = assessorModeratorApplicationService.sortAndFilterUserUnitStandard(first,pageSize,sortField,sortOrder,filters);
					dataModelAssessorModeratorApplication.setRowCount(assessorModeratorApplicationService.countUserUnitStandard(AssessorModeratorApplication.class,filters));
					
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(AssessorModeratorApplication obj) {
				return obj.getId();
			}

			@Override
			public AssessorModeratorApplication getRowData(String rowKey) {
				for (AssessorModeratorApplication obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}
	
	public void assessorModeratorApplicationInfoNew() {
		try{
			System.out.println("1111111111111111:: trainingproviderverfication.getCompanyLearners().getId():: "+trainingproviderverfication.getCompanyLearners().getId());
			CompanyLearners cl = CompanyLearnersService.instance().findByKey(trainingproviderverfication.getCompanyLearners().getId());
			if (cl.getSkillsProgram().getId() != null){
				System.out.println("2222222222222222:: cl.getSkillsProgram().getId():: "+cl.getSkillsProgram().getId());
			}else {
				System.out.println("333333333333333333:: cl.getLearnership().getQualification().getId():: "+cl.getLearnership().getQualification().getId());
			}
		}
		catch (Exception e) {
			logger.fatal(e);
		}
		dataModelAssessorModeratorApplicationNew = new LazyDataModel<AssessorModeratorApplication>() {
			private static final long serialVersionUID = 1L;
			private List<AssessorModeratorApplication> retorno = new ArrayList<AssessorModeratorApplication>();
			@Override
			public List<AssessorModeratorApplication> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					CompanyLearners cl = CompanyLearnersService.instance().findByKey(trainingproviderverfication.getCompanyLearners().getId());		
					if(assessor) {
						filters.put("applicationType", AssessorModeratorAppTypeEnum.NewAssessorRegistration);
						filters.put("applicationType2", AssessorModeratorAppTypeEnum.AssessorReRegistration);
					}else {
						filters.put("applicationType", AssessorModeratorAppTypeEnum.NewModeratorRegistration);
						filters.put("applicationType2", AssessorModeratorAppTypeEnum.ModeratorReRegistration);
					}
					if(cl.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Learnership) {
						filters.put("qualificationID", cl.getLearnership().getQualification().getId());
						retorno = assessorModeratorApplicationService.sortAndFilterUserQualification(first,pageSize,sortField,sortOrder,filters);
						dataModelAssessorModeratorApplicationNew.setRowCount(assessorModeratorApplicationService.countUserQualification(AssessorModeratorApplication.class,filters));
					}else if(SKILLS_PROGRAM_LIST.contains(cl.getInterventionType().getId())) {
						filters.put("skillsProgramID", cl.getSkillsProgram().getId());
						retorno = assessorModeratorApplicationService.sortAndFilterUserSkillsProgramme(first,pageSize,sortField,sortOrder,filters);
						dataModelAssessorModeratorApplicationNew.setRowCount(assessorModeratorApplicationService.countUserSkillsProgramme(AssessorModeratorApplication.class,filters));
					}else {
						Qualification qualification =CompanyLearnersService.instance().getCompanyLearnerQualification(cl);
						filters.put("qualificationID", qualification.getId());
						retorno = assessorModeratorApplicationService.sortAndFilterUserQualification(first,pageSize,sortField,sortOrder,filters);
						dataModelAssessorModeratorApplicationNew.setRowCount(assessorModeratorApplicationService.countUserQualification(AssessorModeratorApplication.class,filters));
					}
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(AssessorModeratorApplication obj) {
				return obj.getId();
			}

			@Override
			public AssessorModeratorApplication getRowData(String rowKey) {
				for (AssessorModeratorApplication obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}
	
	
	public void legacyassessoraccreditationInfo() {
		dataModelLegacyAssessorAccreditation = new LazyDataModel<LegacyAssessorAccreditation>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyAssessorAccreditation> retorno = new ArrayList<LegacyAssessorAccreditation>();

			@Override
			public List<LegacyAssessorAccreditation> load(int first, int pageSize, String sortField,
					SortOrder sortOrder, Map<String, Object> filters) {

				try {
					filters.put("unitStandardId", summativeAssessmentReportUnitStandards.getUnitStandards().getId());
					retorno = legacyAssessorAccreditationService.allLegacyAssessorAccreditationUnitStandards(first, pageSize, sortField, sortOrder, filters, true);					
					dataModelLegacyAssessorAccreditation.setRowCount(legacyAssessorAccreditationService.countallLegacyAssessorAccreditationUnitStandards(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyAssessorAccreditation obj) {
				return obj.getId();
			}

			@Override
			public LegacyAssessorAccreditation getRowData(String rowKey) {
				for (LegacyAssessorAccreditation obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	
	public void addAssessor() {
		try {
			if(summativeAssessmentReportUnitStandards.getModeratorApplication() != null && summativeAssessmentReportUnitStandards.getModeratorApplication().getId() == assessorModeratorApplication.getId()) {
				throw new Exception("Assessor and moderator must not be the same for the assessed unit standard");
			}
			if(summativeAssessmentReportUnitStandards.getModeratorUser() != null && summativeAssessmentReportUnitStandards.getAssessorUser().getId() == assessorModeratorApplication.getUser().getId()) {
				throw new Exception("Assessor and moderator must not be the same for the assessed unit standard");
			}
			summativeAssessmentReportUnitStandards.setLegacyAssessorAccreditation(null);
			summativeAssessmentReportUnitStandards.setAssessorApplication(assessorModeratorApplication);
			summativeAssessmentReportUnitStandards.setAssessorUser(assessorModeratorApplication.getUser());
			summativeAssessmentReportUnitStandardsService.update(summativeAssessmentReportUnitStandards);
		addInfoMessage("Assessor Added");
			clearInfor();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void addModerator() {
		try {
			if(summativeAssessmentReportUnitStandards.getAssessorApplication() != null && summativeAssessmentReportUnitStandards.getAssessorApplication().getId() == assessorModeratorApplication.getId()) {
				throw new Exception("Assessor and moderator must not be the same for the assessed unit standard");
			}
			if(summativeAssessmentReportUnitStandards.getAssessorUser() != null && summativeAssessmentReportUnitStandards.getAssessorUser().getId() == assessorModeratorApplication.getUser().getId()) {
				throw new Exception("Assessor and moderator must not be the same for the assessed unit standard");
			}
			summativeAssessmentReportUnitStandards.setLegacyModeratorAccreditation(null);
			summativeAssessmentReportUnitStandards.setModeratorApplication(assessorModeratorApplication);
			summativeAssessmentReportUnitStandards.setModeratorUser(assessorModeratorApplication.getUser());
			summativeAssessmentReportUnitStandardsService.update(summativeAssessmentReportUnitStandards);
			addInfoMessage("Moderator Added");
			clearInfor();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void applyToAllAssessors() {
		try {
			SummativeAssessmentReport summativeAssessmentReport = summativeAssessmentReportService.findByTrainingProviderVerficationKey(trainingproviderverfication.getId());
			List<SummativeAssessmentReportUnitStandards>list = summativeAssessmentReportService.findUnitStandards(summativeAssessmentReport.getId());
			for(SummativeAssessmentReportUnitStandards sarus:list) {
				if(sarus.getModeratorApplication() != null && assessorModeratorApplication !=null && sarus.getModeratorApplication().getId() == assessorModeratorApplication.getId()) {
					throw new Exception("Assessor and moderator must not be the same user");
				}else {
					sarus.setLegacyAssessorAccreditation(null);
					sarus.setAssessorApplication(assessorModeratorApplication);
					sarus.setAssessorUser(assessorModeratorApplication.getUser());
					summativeAssessmentReportUnitStandardsService.update(sarus);	
				}
			}
			addInfoMessage("Applied to all");
			clearInfor();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void applyToAllModerator() {
		try {
			SummativeAssessmentReport summativeAssessmentReport = summativeAssessmentReportService.findByTrainingProviderVerficationKey(trainingproviderverfication.getId());
			List<SummativeAssessmentReportUnitStandards>list = summativeAssessmentReportService.findUnitStandards(summativeAssessmentReport.getId());
			for(SummativeAssessmentReportUnitStandards sarus:list) {
				if(sarus.getAssessorApplication() != null && assessorModeratorApplication !=null && sarus.getAssessorApplication().getId() == assessorModeratorApplication.getId()) {
					throw new Exception("Assessor and moderator must not be the same user");
				}else {
					sarus.setLegacyModeratorAccreditation(null);
					sarus.setModeratorApplication(assessorModeratorApplication);
					sarus.setModeratorUser(assessorModeratorApplication.getUser());
					summativeAssessmentReportUnitStandardsService.update(sarus);	
				}
			}
			addInfoMessage("Applied to all");
			clearInfor();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void addAssessorSingle() {
		try {
			if(summativeAssessmentReportUnitStandards.getModeratorApplication() != null && summativeAssessmentReportUnitStandards.getModeratorApplication().getId() == assessorModeratorApplication.getId()) {
				throw new Exception("Assessor and moderator must not be the same for the assessed unit standard");
			}
			if(summativeAssessmentReportUnitStandards.getModeratorUser() != null && summativeAssessmentReportUnitStandards.getModeratorUser().getId() == assessorModeratorApplication.getUser().getId()) {
				throw new Exception("Assessor and moderator must not be the same for the assessed unit standard");
			}		
			summativeAssessmentReportUnitStandards.setLegacyAssessorAccreditation(null);
			summativeAssessmentReportUnitStandards.setAssessorApplication(assessorModeratorApplication);
			summativeAssessmentReportUnitStandards.setAssessorUser(assessorModeratorApplication.getUser());
			summativeAssessmentReportUnitStandardsService.update(summativeAssessmentReportUnitStandards);
			addInfoMessage("Assessor Added");			
			clearInfor();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void addModeratorSingle() {
		try {	
			if(summativeAssessmentReportUnitStandards.getAssessorApplication() != null && summativeAssessmentReportUnitStandards.getAssessorApplication().getId() == assessorModeratorApplication.getId()) {
				throw new Exception("Assessor and moderator must not be the same for the assessed unit standard");
			}
			if(summativeAssessmentReportUnitStandards.getAssessorUser() != null && summativeAssessmentReportUnitStandards.getAssessorUser().getId() == assessorModeratorApplication.getUser().getId()) {
				throw new Exception("Assessor and moderator must not be the same for the assessed unit standard");
			}
			
			summativeAssessmentReportUnitStandards.setLegacyModeratorAccreditation(null);
			summativeAssessmentReportUnitStandards.setModeratorApplication(assessorModeratorApplication);
			summativeAssessmentReportUnitStandards.setModeratorUser(assessorModeratorApplication.getUser());
			summativeAssessmentReportUnitStandardsService.update(summativeAssessmentReportUnitStandards);
			addInfoMessage("Moderator Added");			
			clearInfor();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void addAssessorSingleOld() {
		try {			 
			if(assessor) {
				if(summativeAssessmentReportUnitStandards.getModeratorUser() != null && summativeAssessmentReportUnitStandards.getModeratorUser().getId() == assessorModeratorApplication.getUser().getId()) {
					throw new Exception("Assessor and moderator must not be the same for the assessed unit standard");
				}
				summativeAssessmentReportUnitStandards.setLegacyAssessorAccreditation(null);
				summativeAssessmentReportUnitStandards.setAssessorApplication(assessorModeratorApplication);
				summativeAssessmentReportUnitStandards.setAssessorUser(assessorModeratorApplication.getUser());
				summativeAssessmentReportUnitStandardsService.update(summativeAssessmentReportUnitStandards);
			addInfoMessage("Assessor Added");
			}else {
				if(summativeAssessmentReportUnitStandards.getAssessorUser() != null && summativeAssessmentReportUnitStandards.getAssessorUser().getId() == assessorModeratorApplication.getUser().getId()) {
					throw new Exception("Assessor and moderator must not be the same for the assessed unit standard");
				}
				summativeAssessmentReportUnitStandards.setLegacyModeratorAccreditation(null);
				summativeAssessmentReportUnitStandards.setModeratorApplication(assessorModeratorApplication);
				summativeAssessmentReportUnitStandards.setModeratorUser(assessorModeratorApplication.getUser());
				summativeAssessmentReportUnitStandardsService.update(summativeAssessmentReportUnitStandards);
				addInfoMessage("Moderator Added");
			}			
			clearInfor();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void addLegacyAssessorSingle() {
		try {
			summativeAssessmentReportUnitStandards.setAssessorApplication(null);
			summativeAssessmentReportUnitStandards.setAssessorUser(null);
			summativeAssessmentReportUnitStandards.setLegacyAssessorAccreditation(legacyAssessorAccreditation);
			//summativeAssessmentReportUnitStandards.setAssessorUser(assessorModeratorApplication.getUser());
			summativeAssessmentReportUnitStandardsService.update(summativeAssessmentReportUnitStandards);
			addInfoMessage("Assessor Added");			
			clearInfor();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void addLegacyAssessor() {
		try {
			summativeAssessmentReportUnitStandards.setAssessorApplication(null);
			summativeAssessmentReportUnitStandards.setAssessorUser(null);
			summativeAssessmentReportUnitStandards.setLegacyAssessorAccreditation(legacyAssessorAccreditation);
			//summativeAssessmentReportUnitStandards.setAssessorUser(assessorModeratorApplication.getUser());
			summativeAssessmentReportUnitStandardsService.update(summativeAssessmentReportUnitStandards);
			addInfoMessage("Assessor Added");
			clearInfor();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void addLegacyModerator() {
		try {
			summativeAssessmentReportUnitStandards.setModeratorApplication(null);
			summativeAssessmentReportUnitStandards.setModeratorUser(null);
			summativeAssessmentReportUnitStandards.setLegacyModeratorAccreditation(legacyModeratorAccreditation);
			//summativeAssessmentReportUnitStandards.setModeratorUser(assessorModeratorApplication.getUser());
			summativeAssessmentReportUnitStandardsService.update(summativeAssessmentReportUnitStandards);
			addInfoMessage("Legacy Moderator Added");			
			clearInfor();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void applyToAllLegacy() {
		try {
			SummativeAssessmentReport summativeAssessmentReport = summativeAssessmentReportService.findByTrainingProviderVerficationKey(trainingproviderverfication.getId());
			List<SummativeAssessmentReportUnitStandards>list = summativeAssessmentReportService.findUnitStandards(summativeAssessmentReport.getId());
			for(SummativeAssessmentReportUnitStandards sarus:list) {
				if(assessor && applyAssessor) {
					sarus.setAssessorApplication(null);
					sarus.setAssessorUser(null);
					sarus.setLegacyAssessorAccreditation(legacyAssessorAccreditation);
					summativeAssessmentReportUnitStandardsService.update(sarus);		
				}else {
					if(sarus.getLegacyAssessorAccreditation() != null && sarus.getLegacyAssessorAccreditation().getIdNo() != null &&  sarus.getLegacyAssessorAccreditation().getIdNo() .matches(legacyModeratorAccreditation.getIdNo())) {
						sarus.setModeratorApplication(null);
						sarus.setModeratorUser(null);
						sarus.setLegacyModeratorAccreditation(null);
					}else {
						sarus.setModeratorApplication(null);
						sarus.setModeratorUser(null);
						sarus.setLegacyModeratorAccreditation(legacyModeratorAccreditation);
						summativeAssessmentReportUnitStandardsService.update(sarus);
					}
				}
			}
			addInfoMessage("Applied to all");
			clearInfor();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void addAssessmentDate() {
		try {
			if(summativeAssessmentReportUnitStandards.getAssesmentDate() != null && summativeAssessmentReportUnitStandards.getModerationDate() != null) {
				if(summativeAssessmentReportUnitStandards.getModerationDate().before(summativeAssessmentReportUnitStandards.getAssesmentDate())) {
					throw new Exception("Date of moderation cannot be before assessment date");
				}
			}
			summativeAssessmentReportUnitStandardsService.update(summativeAssessmentReportUnitStandards);
			addInfoMessage("Assessor Added");
			clearInfor();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void addModerationDate() {
		try {
			if(summativeAssessmentReportUnitStandards.getAssesmentDate() != null && summativeAssessmentReportUnitStandards.getModerationDate() != null) {
				if(summativeAssessmentReportUnitStandards.getModerationDate().before(summativeAssessmentReportUnitStandards.getAssesmentDate())) {
					throw new Exception("Date of moderation cannot be before assessment date");
				}
			}
			summativeAssessmentReportUnitStandardsService.update(summativeAssessmentReportUnitStandards);
			addInfoMessage("Moderator Added");
			clearInfor();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void applyDateToAll() {
		try {
			SummativeAssessmentReport summativeAssessmentReport = summativeAssessmentReportService.findByTrainingProviderVerficationKey(trainingproviderverfication.getId());
			List<SummativeAssessmentReportUnitStandards>list = summativeAssessmentReportService.findUnitStandards(summativeAssessmentReport.getId());
			for(SummativeAssessmentReportUnitStandards sarus:list) {
				if(assessor) {
					sarus.setAssesmentDate(summativeAssessmentReportUnitStandards.getAssesmentDate());
					summativeAssessmentReportUnitStandardsService.update(sarus);
				}else {					
					sarus.setModerationDate(summativeAssessmentReportUnitStandards.getModerationDate());
					if(sarus.getAssesmentDate() != null) {
						if(sarus.getModerationDate().before(sarus.getAssesmentDate())) {
							sarus.setModerationDate(null);
						}
					}
					summativeAssessmentReportUnitStandardsService.update(sarus);
				}
			}
			addInfoMessage("Applied to all");
			clearInfor();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void addAssessments() {
		try {
			if(applyAssessor) {
				SummativeAssessmentReport summativeAssessmentReport = summativeAssessmentReportService.findByTrainingProviderVerficationKey(trainingproviderverfication.getId());
				List<SummativeAssessmentReportUnitStandards>list = summativeAssessmentReportService.findUnitStandards(summativeAssessmentReport.getId());
				for(SummativeAssessmentReportUnitStandards sarus:list) {					
					//sarus.setCompetenceEnum(summativeAssessmentReportUnitStandards.getCompetenceEnum());
					sarus.setCompetenceEnum(competenceEnum);
					summativeAssessmentReportUnitStandardsService.update(sarus);					
				}
				clearInfor();
				addInfoMessage("Moderator Added");
			}else {
				summativeAssessmentReportUnitStandards.setCompetenceEnum(competenceEnum);
				summativeAssessmentReportUnitStandardsService.update(summativeAssessmentReportUnitStandards);
				addInfoMessage("Moderator Added");				
			}
			clearInfor();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void precheckLegacyAssMod() throws Exception {
		if(summativeAssessmentReportUnitStandards.getLegacyAssessorAccreditation()!= null && summativeAssessmentReportUnitStandards.getLegacyModeratorAccreditation() != null) {
			if(summativeAssessmentReportUnitStandards.getLegacyAssessorAccreditation().getId() == summativeAssessmentReportUnitStandards.getLegacyModeratorAccreditation() .getId()) {
				throw new Exception("Assessor and Moderator must not be the same");
			}
		}
	}
	
	private void clearInfor() {
		assessor = false;
		applyAssessor = false;
		applyModerator =false;
		dataModelAssessorModeratorApplication=null;
		dataModelAssessorModeratorApplicationNew=null;
		dataModelLegacyAssessorAccreditation=null;
		dataModelLegacyModeratorAccreditation=null;
		dataModelLegacyAssessorAccreditationNew=null;
		dataModelLegacyModeratorAccreditationNew=null;
	}
	
	public void prepNewDoc() {
		try {
			doc = new Doc();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void storeAssessmentFile(FileUploadEvent event) {		
		try {			
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			if (doc.getId() != null) {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			} else {
				doc.setTargetKey(this.summativeAssessmentReportUnitStandards.getId());
				doc.setTargetClass(SummativeAssessmentReportUnitStandards.class.getName());
				if (doc.getId() == null) {
					DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), getSessionUI().getActiveUser());
				}
			}
			summativeassessmentreportInfo();
			super.runClientSideExecute("PF('dlgUpload1').hide()");			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	
	//new methods for verification
	public void addToList() {
		try {
			show = false;
			if(trainingproviderverficationfilteredList == null) {
				trainingproviderverficationfilteredList = new ArrayList<>();
			}
			
			if(!service.checkCredits(trainingproviderverfication)) {
				throw new Exception("Minimum credits not met");
			}
			
			if(!service.checkAssessments(trainingproviderverfication)) {
				throw new Exception("Please make sure all assessment information is available");
			}
			
			if(trainingproviderverficationfilteredList.size() == 0) {
				companyLearners = CompanyLearnersService.instance().findByKey(trainingproviderverfication.getCompanyLearners().getId());
				trainingproviderverficationfilteredList.add(trainingproviderverfication);
				if(trainingproviderverfication.getCompanyLearners().getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Learnership) {
					qualificationTypeSelectionEnum = QualificationTypeSelectionEnum.Learnership;					
				}else if(SKILLS_PROGRAM_LIST.contains(trainingproviderverfication.getCompanyLearners().getInterventionType().getId())) {
					qualificationTypeSelectionEnum = QualificationTypeSelectionEnum.SkillsProgram;
				}else if(SKILLS_SET_LIST.contains(trainingproviderverfication.getCompanyLearners().getInterventionType().getId())) {
					qualificationTypeSelectionEnum = QualificationTypeSelectionEnum.SkillsSet;
				}else if(trainingproviderverfication.getCompanyLearners().getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
					qualificationTypeSelectionEnum = QualificationTypeSelectionEnum.UnitStandards;
				}else {
					qualificationTypeSelectionEnum = QualificationTypeSelectionEnum.Qualification;
				}
				 
			}else {
				
				if(checkIfAlreadyadded()) {
					throw new Exception("Learner already added to the list");
				}else {
					if(!checkIfSameIntervention()) {
						throw new Exception("Learners do not have same intervention types");
					}
					
					if(!checkIfSameQualification()) {
						throw new Exception("Learners do not have same qualification");
					}
					trainingproviderverficationfilteredList.add(trainingproviderverfication);
				}
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	private boolean checkIfSameIntervention() {
		boolean sameintervention = false;
		if(trainingproviderverfication.getCompanyLearners().getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Learnership && qualificationTypeSelectionEnum == QualificationTypeSelectionEnum.Learnership) {
			sameintervention = true;
		}else if(SKILLS_PROGRAM_LIST.contains(trainingproviderverfication.getCompanyLearners().getInterventionType().getId()) && qualificationTypeSelectionEnum == QualificationTypeSelectionEnum.SkillsProgram) {
			sameintervention = true;
		}else if(SKILLS_SET_LIST.contains(trainingproviderverfication.getCompanyLearners().getInterventionType().getId()) && qualificationTypeSelectionEnum == QualificationTypeSelectionEnum.SkillsSet) {
			sameintervention = true;
		}else if(trainingproviderverfication.getCompanyLearners().getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE && qualificationTypeSelectionEnum == QualificationTypeSelectionEnum.UnitStandards) {
			sameintervention = true;
		}
		return sameintervention;
	}
	
	private boolean checkIfSameQualification() throws Exception {
		boolean samequalification = false;
		
		if(qualificationTypeSelectionEnum == QualificationTypeSelectionEnum.Learnership && trainingproviderverfication.getCompanyLearners().getLearnership() != null) {
			if(trainingproviderverfication.getCompanyLearners().getLearnership().getCode().matches(companyLearners.getLearnership().getCode())) {
				samequalification = true;
			}else {
				throw new Exception("Learnership not the same");
			}
		}else if(qualificationTypeSelectionEnum == QualificationTypeSelectionEnum.SkillsProgram && trainingproviderverfication.getCompanyLearners().getSkillsProgram() != null) {
			if(trainingproviderverfication.getCompanyLearners().getSkillsProgram().getProgrammeID().matches(companyLearners.getSkillsProgram().getProgrammeID())) {
				samequalification = true;
			}else {
				throw new Exception("Skills Program not the same");
			}
		}else if(qualificationTypeSelectionEnum == QualificationTypeSelectionEnum.SkillsSet && trainingproviderverfication.getCompanyLearners().getSkillsSet() != null) {
			if(trainingproviderverfication.getCompanyLearners().getSkillsSet().getProgrammeID().matches(companyLearners.getSkillsSet().getProgrammeID())) {
				samequalification = true;
			}else {
				throw new Exception("Skills Set not the same");
			}
		}else if(qualificationTypeSelectionEnum == QualificationTypeSelectionEnum.UnitStandards && trainingproviderverfication.getCompanyLearners().getUnitStandard() != null) {
			System.out.println("111111111111:: trainingproviderverfication.getCompanyLearners().getUnitStandard().getCode():: "+trainingproviderverfication.getCompanyLearners().getUnitStandard().getCode());
			System.out.println("222222222222:: companyLearners.getUnitStandard().getCode():: "+companyLearners.getUnitStandard().getCode());
			if(trainingproviderverfication.getCompanyLearners().getUnitStandard().getCode().intValue() == companyLearners.getUnitStandard().getCode().intValue()) {
				samequalification = true;
			}else {
				throw new Exception("Unit Standards not the same");
			}
		}else {
			throw new Exception("Verification Qualification Error!!! Please contact your administrator");
		}
		
		
		/*if(trainingproviderverfication.getCompanyLearners().getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Learnership) {
			if(trainingproviderverfication.getCompanyLearners().getLearnership().getId() == companyLearners.getLearnership().getId()) {
				samequalification = true;
			}
		}else if(trainingproviderverfication.getCompanyLearners().getInterventionType().getId() == HAJConstants.SKILLS_PROGRAM) {
			if(trainingproviderverfication.getCompanyLearners().getSkillsProgram().getId() == companyLearners.getSkillsProgram().getId()) {
				samequalification = true;
			}
		}else if(trainingproviderverfication.getCompanyLearners().getInterventionType().getId() == HAJConstants.SKILLS_SET ) {
			if(trainingproviderverfication.getCompanyLearners().getSkillsSet().getId() == companyLearners.getSkillsSet().getId()) {
				samequalification = true;
			}
		}else if(trainingproviderverfication.getCompanyLearners().getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
			if(trainingproviderverfication.getCompanyLearners().getUnitStandard().getId() == companyLearners.getUnitStandard().getId()) {
				samequalification = true;
			}
		}else if(trainingproviderverfication.getCompanyLearners().getQualification() != null) {
			if(trainingproviderverfication.getCompanyLearners().getQualification().getId() == companyLearners.getQualification().getId()) {
				samequalification = true;
			}
		}*/
		return samequalification;
	}

	public void removeFromList() {
		try {
			trainingproviderverficationfilteredList.remove(trainingproviderverfication);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	private boolean checkIfAlreadyadded() {
		boolean exists = false;
		for(TrainingProviderVerfication t:trainingproviderverficationfilteredList ) {
			if(t.getId() == trainingproviderverfication.getId()) {
				exists = true;
				break;
			}
		}
		return exists;
	}

	public void prepareDataModelModeratorApplyAll() {
		try {
			clearInfor();
			assessor=false;
			newOrLegacyEnum = NewOrLegacyEnum.NewApplication;
			//assessorModeratorApplicationInfo();
			assessorModeratorApplicationInfoNew();
			//legacyassessoraccreditationInfo();
			//legacymoderatoraccreditationInfo();
			//legacyassessoraccreditationInfoNew();
			legacymoderatoraccreditationInfoNew();
			
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepareDataModelAssessorApplyAll() {
		try {
			clearInfor();
			assessor=true;
			newOrLegacyEnum = NewOrLegacyEnum.NewApplication;
			//assessorModeratorApplicationInfo();
			assessorModeratorApplicationInfoNew();
			//legacyassessoraccreditationInfo();
			//legacymoderatoraccreditationInfo();
			legacyassessoraccreditationInfoNew();
			//legacymoderatoraccreditationInfoNew();
			
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void applyAssessmentDateToAll() {
		try {			
			SummativeAssessmentReport summativeAssessmentReport = summativeAssessmentReportService.findByTrainingProviderVerficationKey(trainingproviderverfication.getId());
			List<SummativeAssessmentReportUnitStandards>list = summativeAssessmentReportService.findUnitStandards(summativeAssessmentReport.getId());
			for(SummativeAssessmentReportUnitStandards sarus:list) {				
				sarus.setAssesmentDate(assesmentDate);
				summativeAssessmentReportUnitStandardsService.update(sarus);				
			}
			summativeassessmentreportInfo();		
			clearInfor();
			addInfoMessage("Applied to all");
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void applyModerationDateToAll() {
		try {			
			SummativeAssessmentReport summativeAssessmentReport = summativeAssessmentReportService.findByTrainingProviderVerficationKey(trainingproviderverfication.getId());
			List<SummativeAssessmentReportUnitStandards>list = summativeAssessmentReportService.findUnitStandards(summativeAssessmentReport.getId());
			for(SummativeAssessmentReportUnitStandards sarus:list) {				
				sarus.setModerationDate(moderationDate);
				if(sarus.getAssesmentDate() != null) {
					if(sarus.getModerationDate().before(sarus.getAssesmentDate())) {
						sarus.setModerationDate(null);
					}
				}
				summativeAssessmentReportUnitStandardsService.update(sarus);				
			}
			summativeassessmentreportInfo();			
			clearInfor();
			addInfoMessage("Applied to all");
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void applyToAllLegacyAssessor() {
		try {
			SummativeAssessmentReport summativeAssessmentReport = summativeAssessmentReportService.findByTrainingProviderVerficationKey(trainingproviderverfication.getId());
			List<SummativeAssessmentReportUnitStandards>list = summativeAssessmentReportService.findUnitStandards(summativeAssessmentReport.getId());
			for(SummativeAssessmentReportUnitStandards sarus:list) {
				sarus.setAssessorApplication(null);
				sarus.setAssessorUser(null);
				sarus.setLegacyAssessorAccreditation(legacyAssessorAccreditation);
				summativeAssessmentReportUnitStandardsService.update(sarus);
			}
			summativeassessmentreportInfo();			
			clearInfor();
			addInfoMessage("Applied to all");
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void applyToAllLegacyModerators() {
		try {
			SummativeAssessmentReport summativeAssessmentReport = summativeAssessmentReportService.findByTrainingProviderVerficationKey(trainingproviderverfication.getId());
			List<SummativeAssessmentReportUnitStandards>list = summativeAssessmentReportService.findUnitStandards(summativeAssessmentReport.getId());
			for(SummativeAssessmentReportUnitStandards sarus:list) {
				if(sarus.getLegacyAssessorAccreditation() != null && sarus.getLegacyAssessorAccreditation().getIdNo() != null &&  sarus.getLegacyAssessorAccreditation().getIdNo() .matches(legacyModeratorAccreditation.getIdNo())) {
					sarus.setModeratorApplication(null);
					sarus.setModeratorUser(null);
					sarus.setLegacyModeratorAccreditation(null);
				}else {
					sarus.setModeratorApplication(null);
					sarus.setModeratorUser(null);
					sarus.setLegacyModeratorAccreditation(legacyModeratorAccreditation);
					summativeAssessmentReportUnitStandardsService.update(sarus);
				}
			}
			summativeassessmentreportInfo();		
			clearInfor();
			addInfoMessage("Applied to all");
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void addAssessmentsApplyAll() {
		try {			
			SummativeAssessmentReport summativeAssessmentReport = summativeAssessmentReportService.findByTrainingProviderVerficationKey(trainingproviderverfication.getId());
			List<SummativeAssessmentReportUnitStandards>list = summativeAssessmentReportService.findUnitStandards(summativeAssessmentReport.getId());
			for(SummativeAssessmentReportUnitStandards sarus:list) {					
				sarus.setCompetenceEnum(competenceEnum);
				summativeAssessmentReportUnitStandardsService.update(sarus);					
			}
			summativeassessmentreportInfo();
			addInfoMessage("Moderator Added");			
			clearInfor();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
		
	public void setApplyAssesorToTrue() {
		applyAssessor = true;
	}
	
	public List<TrainingProviderVerfication> getTrainingProviderVerficationList() {
		return trainingproviderverficationList;
	}

	public void setTrainingProviderVerficationList(List<TrainingProviderVerfication> trainingproviderverficationList) {
		this.trainingproviderverficationList = trainingproviderverficationList;
	}

	public TrainingProviderVerfication getTrainingproviderverfication() {
		return trainingproviderverfication;
	}

	public void setTrainingproviderverfication(TrainingProviderVerfication trainingproviderverfication) {
		this.trainingproviderverfication = trainingproviderverfication;
	}

	public List<TrainingProviderVerfication> getTrainingProviderVerficationfilteredList() {
		return trainingproviderverficationfilteredList;
	}

	public void setTrainingProviderVerficationfilteredList(List<TrainingProviderVerfication> trainingproviderverficationfilteredList) {
		this.trainingproviderverficationfilteredList = trainingproviderverficationfilteredList;
	}

	public LazyDataModel<TrainingProviderVerfication> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<TrainingProviderVerfication> dataModel) {
		this.dataModel = dataModel;
	}

	public LazyDataModel<SummativeAssessmentReport> getDataModelSummativeAssessmentReport() {
		return dataModelSummativeAssessmentReport;
	}

	public void setDataModelSummativeAssessmentReport(LazyDataModel<SummativeAssessmentReport> dataModelSummativeAssessmentReport) {
		this.dataModelSummativeAssessmentReport = dataModelSummativeAssessmentReport;
	}

	public SummativeAssessmentReport getAssessmentReport() {
		return assessmentReport;
	}

	public void setAssessmentReport(SummativeAssessmentReport assessmentReport) {
		this.assessmentReport = assessmentReport;
	}

	public List<TrainingProviderVerfication> getTrainingproviderverficationList() {
		return trainingproviderverficationList;
	}

	public void setTrainingproviderverficationList(List<TrainingProviderVerfication> trainingproviderverficationList) {
		this.trainingproviderverficationList = trainingproviderverficationList;
	}

	public List<TrainingProviderVerfication> getTrainingproviderverficationfilteredList() {
		return trainingproviderverficationfilteredList;
	}

	public void setTrainingproviderverficationfilteredList(List<TrainingProviderVerfication> trainingproviderverficationfilteredList) {
		this.trainingproviderverficationfilteredList = trainingproviderverficationfilteredList;
	}

	public SummativeAssessmentReportUnitStandards getSummativeAssessmentReportUnitStandards() {
		return summativeAssessmentReportUnitStandards;
	}

	public void setSummativeAssessmentReportUnitStandards(SummativeAssessmentReportUnitStandards summativeAssessmentReportUnitStandards) {
		this.summativeAssessmentReportUnitStandards = summativeAssessmentReportUnitStandards;
	}

	public boolean isProvided() {
		return provided;
	}

	public void setProvided(boolean provided) {
		this.provided = provided;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public LazyDataModel<Company> getTpDataModel() {
		return tpDataModel;
	}

	public void setTpDataModel(LazyDataModel<Company> tpDataModel) {
		this.tpDataModel = tpDataModel;
	}

	public boolean isShowlearners() {
		return showlearners;
	}

	public void setShowlearners(boolean showlearners) {
		this.showlearners = showlearners;
	}

	public Company getSelectedCompany() {
		return selectedCompany;
	}

	public void setSelectedCompany(Company selectedCompany) {
		this.selectedCompany = selectedCompany;
	}

	public ScheduledEvent getScheduledEvent() {
		return scheduledEvent;
	}

	public void setScheduledEvent(ScheduledEvent scheduledEvent) {
		this.scheduledEvent = scheduledEvent;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public NewOrLegacyEnum getNewOrLegacyEnum() {
		return newOrLegacyEnum;
	}

	public void setNewOrLegacyEnum(NewOrLegacyEnum newOrLegacyEnum) {
		this.newOrLegacyEnum = newOrLegacyEnum;
	}

	public String getIdnumber() {
		return idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}

	public AssessorModeratorApplication getAssessorModeratorApplication() {
		return assessorModeratorApplication;
	}

	public void setAssessorModeratorApplication(AssessorModeratorApplication assessorModeratorApplication) {
		this.assessorModeratorApplication = assessorModeratorApplication;
	}

	public LazyDataModel<AssessorModeratorApplication> getDataModelAssessorModeratorApplication() {
		return dataModelAssessorModeratorApplication;
	}

	public void setDataModelAssessorModeratorApplication(LazyDataModel<AssessorModeratorApplication> dataModelAssessorModeratorApplication) {
		this.dataModelAssessorModeratorApplication = dataModelAssessorModeratorApplication;
	}

	public LazyDataModel<LegacyModeratorAccreditation> getDataModelLegacyModeratorAccreditation() {
		return dataModelLegacyModeratorAccreditation;
	}

	public LazyDataModel<LegacyAssessorAccreditation> getDataModelLegacyAssessorAccreditation() {
		return dataModelLegacyAssessorAccreditation;
	}

	public void setDataModelLegacyModeratorAccreditation(LazyDataModel<LegacyModeratorAccreditation> dataModelLegacyModeratorAccreditation) {
		this.dataModelLegacyModeratorAccreditation = dataModelLegacyModeratorAccreditation;
	}

	public void setDataModelLegacyAssessorAccreditation(LazyDataModel<LegacyAssessorAccreditation> dataModelLegacyAssessorAccreditation) {
		this.dataModelLegacyAssessorAccreditation = dataModelLegacyAssessorAccreditation;
	}

	public LegacyAssessorAccreditation getLegacyAssessorAccreditation() {
		return legacyAssessorAccreditation;
	}

	public LegacyModeratorAccreditation getLegacyModeratorAccreditation() {
		return legacyModeratorAccreditation;
	}

	public void setLegacyAssessorAccreditation(LegacyAssessorAccreditation legacyAssessorAccreditation) {
		this.legacyAssessorAccreditation = legacyAssessorAccreditation;
	}

	public void setLegacyModeratorAccreditation(LegacyModeratorAccreditation legacyModeratorAccreditation) {
		this.legacyModeratorAccreditation = legacyModeratorAccreditation;
	}

	public boolean isAssessor() {
		return assessor;
	}

	public void setAssessor(boolean assessor) {
		this.assessor = assessor;
	}

	public boolean isApplyAssessor() {
		return applyAssessor;
	}

	public void setApplyAssessor(boolean applyAssessor) {
		this.applyAssessor = applyAssessor;
	}

	public LazyDataModel<LegacyModeratorAccreditation> getDataModelLegacyModeratorAccreditationNew() {
		return dataModelLegacyModeratorAccreditationNew;
	}

	public LazyDataModel<LegacyAssessorAccreditation> getDataModelLegacyAssessorAccreditationNew() {
		return dataModelLegacyAssessorAccreditationNew;
	}

	public LazyDataModel<AssessorModeratorApplication> getDataModelAssessorModeratorApplicationNew() {
		return dataModelAssessorModeratorApplicationNew;
	}

	public void setDataModelLegacyModeratorAccreditationNew(LazyDataModel<LegacyModeratorAccreditation> dataModelLegacyModeratorAccreditationNew) {
		this.dataModelLegacyModeratorAccreditationNew = dataModelLegacyModeratorAccreditationNew;
	}

	public void setDataModelLegacyAssessorAccreditationNew(LazyDataModel<LegacyAssessorAccreditation> dataModelLegacyAssessorAccreditationNew) {
		this.dataModelLegacyAssessorAccreditationNew = dataModelLegacyAssessorAccreditationNew;
	}

	public void setDataModelAssessorModeratorApplicationNew(LazyDataModel<AssessorModeratorApplication> dataModelAssessorModeratorApplicationNew) {
		this.dataModelAssessorModeratorApplicationNew = dataModelAssessorModeratorApplicationNew;
	}

	public boolean isApplyModerator() {
		return applyModerator;
	}

	public void setApplyModerator(boolean applyModerator) {
		this.applyModerator = applyModerator;
	}

	public CompetenceEnum getCompetenceEnum() {
		return competenceEnum;
	}

	public void setCompetenceEnum(CompetenceEnum competenceEnum) {
		this.competenceEnum = competenceEnum;
	}

	public Date getModerationDate() {
		return moderationDate;
	}

	public Date getAssesmentDate() {
		return assesmentDate;
	}

	public void setModerationDate(Date moderationDate) {
		this.moderationDate = moderationDate;
	}

	public void setAssesmentDate(Date assesmentDate) {
		this.assesmentDate = assesmentDate;
	}
}
