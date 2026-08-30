package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.Transient;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.lookup.LegacyProviderAccreditation;
import haj.com.entity.lookup.LegacyProviderLearnership;
import haj.com.entity.lookup.LegacyProviderQualification;
import haj.com.entity.lookup.LegacyProviderSkillsProgramme;
import haj.com.entity.lookup.LegacyProviderUnitStandard;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.TrainingProviderApplicationService;
import haj.com.service.TrainingProviderSkillsProgrammeService;
import haj.com.service.lookup.LegacyProviderAccreditationService;
import haj.com.service.lookup.LegacyProviderLearnershipService;
import haj.com.service.lookup.LegacyProviderQualificationService;
import haj.com.service.lookup.LegacyProviderSkillsProgrammeService;
import haj.com.service.lookup.LegacyProviderUnitStandardService;

@ManagedBean(name = "trainingProviderLegacyApplicationUI")
@ViewScoped
public class TrainingProviderLegacyApplicationUI extends AbstractUI {

	private LegacyProviderAccreditationService legacyProviderAccreditationService = new LegacyProviderAccreditationService();
	private TrainingProviderApplicationService trainingProviderApplicationService = new TrainingProviderApplicationService();
	private LazyDataModel<LegacyProviderAccreditation> legacyDataModel;
	private LazyDataModel<TrainingProviderApplication> tpDataModel; 
	
	private TrainingProviderApplication selectedTrainingProviderApplication = new TrainingProviderApplication();
	private LegacyProviderAccreditation selectedLegacyProviderAccreditation = new LegacyProviderAccreditation();
	
	private List<LegacyProviderQualification> legacyProviderQualificationList;
	private List<LegacyProviderSkillsProgramme> legacyProviderSkillsProgrammeList;
	private List<LegacyProviderUnitStandard> legacyProviderUnitStandardList;
	
	private LazyDataModel<LegacyProviderQualification> legacyProviderQualificationLazyDataModel;
	private LazyDataModel<LegacyProviderSkillsProgramme> legacyProviderSkillsProgrammeLazyDataModel;
	private LazyDataModel<LegacyProviderUnitStandard>  legacyProviderUnitStandardLazyDataModel;
	private LazyDataModel<LegacyProviderLearnership>legacyProviderLearnershipLazyDataModel;
	
	private boolean showLegacyProviderAccreditation = false;
	private boolean showLegacyLists = false;
	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Initialize method to get all TrainingProviderApplication and prepare a for a create of a new TrainingProviderApplication
 	 * @author TechFinium 
 	 * @see    TrainingProviderApplication
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		loadUserTPInfo();		
	}
	
	public void loadUserTPInfo() {
	     tpDataModel = new LazyDataModel<TrainingProviderApplication>() { 
		 
		   private static final long serialVersionUID = 1L; 
		   private List<TrainingProviderApplication> retorno = new  ArrayList<TrainingProviderApplication>();
		   
		   @Override 
		   public List<TrainingProviderApplication> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
		   
			try {
				filters.put("userID",getSessionUI().getActiveUser().getId());
				retorno = trainingProviderApplicationService.findUserTP(first, pageSize, sortField, sortOrder, filters);
				tpDataModel.setRowCount(trainingProviderApplicationService.countUuserTP(filters));
			} catch (Exception e) {
				e.printStackTrace();
				logger.fatal(e);
			} 
		    return retorno; 
		   }
		   
		    @Override
		    public Object getRowKey(TrainingProviderApplication obj) {
		        return obj.getId();
		    }
		    
		    @Override
		    public TrainingProviderApplication getRowData(String rowKey) {
		        for(TrainingProviderApplication obj : retorno) {
		            if(obj.getId().equals(Long.valueOf(rowKey)))
		                return obj;
		        }
		        return null;
		    }			    
		    
		  }; 
	}

	public void legacyprovideraccreditationInfo() {
		showLegacyProviderAccreditation = true;
		legacyDataModel = new LazyDataModel<LegacyProviderAccreditation>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyProviderAccreditation> retorno = new ArrayList<LegacyProviderAccreditation>();

			@Override
			public List<LegacyProviderAccreditation> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					//filters.put("linkedSdl", selectedTrainingProviderApplication.getCompany().getLevyNumber());
//					retorno = legacyProviderAccreditationService.allLegacyProviderAccreditation(LegacyProviderAccreditation.class, first, pageSize, sortField, sortOrder, filters);
//					legacyDataModel.setRowCount(legacyProviderAccreditationService.count(LegacyProviderAccreditation.class, filters));
					retorno = legacyProviderAccreditationService.allLegacyProviderAccreditationlegacyOrganisationSitesLinkedSdlOrMainSdl(first, pageSize, sortField, sortOrder, filters, selectedTrainingProviderApplication.getCompany().getLevyNumber());
					legacyDataModel.setRowCount(legacyProviderAccreditationService.countAllLegacyProviderAccreditationlegacyOrganisationSitesLinkedSdlOrMainSdl(filters));
				} catch (Exception e) {					
					e.printStackTrace();
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyProviderAccreditation obj) {
				return obj.getId();
			}

			@Override
			public LegacyProviderAccreditation getRowData(String rowKey) {
				for (LegacyProviderAccreditation obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}
	
	public void legacyproviderqualificationInfo() {

		legacyProviderQualificationLazyDataModel = new LazyDataModel<LegacyProviderQualification>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyProviderQualification> retorno = new ArrayList<LegacyProviderQualification>();

			@Override
			public List<LegacyProviderQualification> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = LegacyProviderQualificationService.instance().allLegacyProviderQualification(LegacyProviderQualification.class, first, pageSize, sortField, sortOrder, filters);
					legacyProviderQualificationLazyDataModel.setRowCount(LegacyProviderQualificationService.instance().count(LegacyProviderQualification.class, filters));
				} catch (Exception e) {
					e.printStackTrace();
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyProviderQualification obj) {
				return obj.getId();
			}

			@Override
			public LegacyProviderQualification getRowData(String rowKey) {
				for (LegacyProviderQualification obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}
	
	public void legacyproviderskillsprogrammeInfo() {

		legacyProviderSkillsProgrammeLazyDataModel = new LazyDataModel<LegacyProviderSkillsProgramme>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyProviderSkillsProgramme> retorno = new ArrayList<LegacyProviderSkillsProgramme>();

			@Override
			public List<LegacyProviderSkillsProgramme> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = LegacyProviderSkillsProgrammeService.instance().allLegacyProviderSkillsProgramme(LegacyProviderSkillsProgramme.class, first, pageSize, sortField, sortOrder, filters);
					legacyProviderSkillsProgrammeLazyDataModel.setRowCount(LegacyProviderSkillsProgrammeService.instance().count(LegacyProviderSkillsProgramme.class, filters));
				} catch (Exception e) {
					e.printStackTrace();
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyProviderSkillsProgramme obj) {
				return obj.getId();
			}

			@Override
			public LegacyProviderSkillsProgramme getRowData(String rowKey) {
				for (LegacyProviderSkillsProgramme obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}
	
	public void legacyproviderunitstandardInfo() {
		legacyProviderUnitStandardLazyDataModel = new LazyDataModel<LegacyProviderUnitStandard>() {
			private static final long serialVersionUID = 1L;
			private List<LegacyProviderUnitStandard> retorno = new ArrayList<LegacyProviderUnitStandard>();
			@Override
			public List<LegacyProviderUnitStandard> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = LegacyProviderUnitStandardService.instance().allLegacyProviderUnitStandard(LegacyProviderUnitStandard.class, first, pageSize, sortField, sortOrder, filters);
					legacyProviderUnitStandardLazyDataModel.setRowCount(LegacyProviderUnitStandardService.instance().count(LegacyProviderUnitStandard.class, filters));
				} catch (Exception e) {
					e.printStackTrace();
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyProviderUnitStandard obj) {
				return obj.getId();
			}

			@Override
			public LegacyProviderUnitStandard getRowData(String rowKey) {
				for (LegacyProviderUnitStandard obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}
	
	public void legacyproviderlearnershipInfo() {

		legacyProviderLearnershipLazyDataModel = new LazyDataModel<LegacyProviderLearnership>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyProviderLearnership> retorno = new ArrayList<LegacyProviderLearnership>();

			@Override
			public List<LegacyProviderLearnership> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = LegacyProviderLearnershipService.instance().allLegacyProviderLearnership(LegacyProviderLearnership.class, first, pageSize, sortField, sortOrder, filters);
					legacyProviderLearnershipLazyDataModel.setRowCount(LegacyProviderLearnershipService.instance().count(LegacyProviderLearnership.class, filters));
				} catch (Exception e) {
					e.printStackTrace();
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyProviderLearnership obj) {
				return obj.getId();
			}

			@Override
			public LegacyProviderLearnership getRowData(String rowKey) {
				for (LegacyProviderLearnership obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}
	
	/*public void populateData(LegacyProviderAccreditation legacyProviderAccreditation) throws Exception {
		legacyProviderAccreditation.setLegacyProviderQualificationList(LegacyProviderQualificationService.instance().findBySldNoAndQualNotNull(legacyProviderAccreditation.getSdlNumber()));
		legacyProviderAccreditation.setLegacyProviderSkillsProgrammeList(LegacyProviderSkillsProgrammeService.instance().findBySldNoAndSkillProgrameIsNotNull(legacyProviderAccreditation.getSdlNumber()));;
		legacyProviderAccreditation.setLegacyProviderUnitStandardList(LegacyProviderUnitStandardService.instance().findBySldNoAndUnitStandardIsNotNull(legacyProviderAccreditation.getSdlNumber()));
	}*/
	
	public void populateData() throws Exception {
		showLegacyLists =true;
		legacyproviderlearnershipInfo();
		//legacyproviderqualificationInfo();
		legacyproviderskillsprogrammeInfo();
		legacyproviderunitstandardInfo();		
	}

	public LazyDataModel<LegacyProviderAccreditation> getLegacyDataModel() {
		return legacyDataModel;
	}

	public void setLegacyDataModel(LazyDataModel<LegacyProviderAccreditation> legacyDataModel) {
		this.legacyDataModel = legacyDataModel;
	}

	public LazyDataModel<TrainingProviderApplication> getTpDataModel() {
		return tpDataModel;
	}

	public void setTpDataModel(LazyDataModel<TrainingProviderApplication> tpDataModel) {
		this.tpDataModel = tpDataModel;
	}

	public TrainingProviderApplication getSelectedTrainingProviderApplication() {
		return selectedTrainingProviderApplication;
	}

	public void setSelectedTrainingProviderApplication(TrainingProviderApplication selectedTrainingProviderApplication) {
		this.selectedTrainingProviderApplication = selectedTrainingProviderApplication;
	}

	public LegacyProviderAccreditation getSelectedLegacyProviderAccreditation() {
		return selectedLegacyProviderAccreditation;
	}

	public void setSelectedLegacyProviderAccreditation(LegacyProviderAccreditation selectedLegacyProviderAccreditation) {
		this.selectedLegacyProviderAccreditation = selectedLegacyProviderAccreditation;
	}

	public List<LegacyProviderQualification> getLegacyProviderQualificationList() {
		return legacyProviderQualificationList;
	}

	public void setLegacyProviderQualificationList(List<LegacyProviderQualification> legacyProviderQualificationList) {
		this.legacyProviderQualificationList = legacyProviderQualificationList;
	}

	public List<LegacyProviderSkillsProgramme> getLegacyProviderSkillsProgrammeList() {
		return legacyProviderSkillsProgrammeList;
	}

	public void setLegacyProviderSkillsProgrammeList(
			List<LegacyProviderSkillsProgramme> legacyProviderSkillsProgrammeList) {
		this.legacyProviderSkillsProgrammeList = legacyProviderSkillsProgrammeList;
	}

	public List<LegacyProviderUnitStandard> getLegacyProviderUnitStandardList() {
		return legacyProviderUnitStandardList;
	}

	public void setLegacyProviderUnitStandardList(List<LegacyProviderUnitStandard> legacyProviderUnitStandardList) {
		this.legacyProviderUnitStandardList = legacyProviderUnitStandardList;
	}

	public LazyDataModel<LegacyProviderQualification> getLegacyProviderQualificationLazyDataModel() {
		return legacyProviderQualificationLazyDataModel;
	}

	public void setLegacyProviderQualificationLazyDataModel(
			LazyDataModel<LegacyProviderQualification> legacyProviderQualificationLazyDataModel) {
		this.legacyProviderQualificationLazyDataModel = legacyProviderQualificationLazyDataModel;
	}

	public LazyDataModel<LegacyProviderSkillsProgramme> getLegacyProviderSkillsProgrammeLazyDataModel() {
		return legacyProviderSkillsProgrammeLazyDataModel;
	}

	public void setLegacyProviderSkillsProgrammeLazyDataModel(
			LazyDataModel<LegacyProviderSkillsProgramme> legacyProviderSkillsProgrammeLazyDataModel) {
		this.legacyProviderSkillsProgrammeLazyDataModel = legacyProviderSkillsProgrammeLazyDataModel;
	}

	public LazyDataModel<LegacyProviderUnitStandard> getLegacyProviderUnitStandardLazyDataModel() {
		return legacyProviderUnitStandardLazyDataModel;
	}

	public void setLegacyProviderUnitStandardLazyDataModel(
			LazyDataModel<LegacyProviderUnitStandard> legacyProviderUnitStandardLazyDataModel) {
		this.legacyProviderUnitStandardLazyDataModel = legacyProviderUnitStandardLazyDataModel;
	}

	public LazyDataModel<LegacyProviderLearnership> getLegacyProviderLearnershipLazyDataModel() {
		return legacyProviderLearnershipLazyDataModel;
	}

	public void setLegacyProviderLearnershipLazyDataModel(
			LazyDataModel<LegacyProviderLearnership> legacyProviderLearnershipLazyDataModel) {
		this.legacyProviderLearnershipLazyDataModel = legacyProviderLearnershipLazyDataModel;
	}

	public boolean isShowLegacyProviderAccreditation() {
		return showLegacyProviderAccreditation;
	}

	public void setShowLegacyProviderAccreditation(boolean showLegacyProviderAccreditation) {
		this.showLegacyProviderAccreditation = showLegacyProviderAccreditation;
	}

	public boolean isShowLegacyLists() {
		return showLegacyLists;
	}

	public void setShowLegacyLists(boolean showLegacyLists) {
		this.showLegacyLists = showLegacyLists;
	}
}
