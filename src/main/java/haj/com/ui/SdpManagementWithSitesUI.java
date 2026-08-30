package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.AssessorModeratorCompanySites;
import haj.com.entity.Company;
import haj.com.entity.SDPCompany;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.TrainingSite;
import haj.com.entity.lookup.LegacyProviderAccreditation;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AssessorModeratorCompanySitesService;
import haj.com.service.CompanyService;
import haj.com.service.SDPCompanyService;
import haj.com.service.TrainingProviderApplicationService;
import haj.com.service.TrainingSiteService;
import haj.com.service.lookup.LegacyProviderAccreditationService;

/**
 * The Class SdpManagementWithSitesUI.
 */
@ManagedBean(name = "sdpManagementWithSitesUI")
@ViewScoped
public class SdpManagementWithSitesUI extends AbstractUI {

	/* Entity Level */
	private Company holdingCompany = null;
	private SDPCompany sdpCompanyLink = null;
	private TrainingSite trainingSite = null;
	private TrainingProviderApplication selectedTrainingProviderApplication = null;

	/* Service Level */
	private CompanyService companyService = new CompanyService();
	private SDPCompanyService sdpCompanyService = new SDPCompanyService();
	private TrainingSiteService trainingSiteService = new TrainingSiteService();
	private TrainingProviderApplicationService trainingProviderApplicationService = new TrainingProviderApplicationService();
	private LegacyProviderAccreditationService legacyProviderAccreditationService = new LegacyProviderAccreditationService();
	private AssessorModeratorCompanySitesService assessorModeratorCompanySitesService = new AssessorModeratorCompanySitesService();

	/* Levy Data Model */
	private LazyDataModel<SDPCompany> sdpCompanyDataModel;
	private LazyDataModel<LegacyProviderAccreditation> legacyDataModel;
	private LazyDataModel<TrainingProviderApplication> trainingProviderApplicationDataModel;
	private LazyDataModel<AssessorModeratorCompanySites> assessorModeratorCompanySitesDataModel;
	
	/* Vars */
	private boolean linkedToHoldingCompany = false;
	private boolean linkedToSite = false;

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
		trainingProviderApplicationDataModelInfo();
	}

	public void trainingProviderApplicationDataModelInfo() {
		trainingProviderApplicationDataModel = new LazyDataModel<TrainingProviderApplication>() {
			private static final long serialVersionUID = 1L;
			private List<TrainingProviderApplication> retorno = new ArrayList<>();
			@Override
			public List<TrainingProviderApplication> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = trainingProviderApplicationService.allTrainingProviderApplicationByUserLinkedAsSdp(first, pageSize, sortField, sortOrder, filters, getSessionUI().getActiveUser().getId());
					trainingProviderApplicationDataModel.setRowCount(trainingProviderApplicationService.countAllTrainingProviderApplicationByUserLinkedAsSdp(filters));
				} catch (Exception e) {
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
				for (TrainingProviderApplication obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void viewProviderApplicationInformation() {
		try {
			// locate link to Company & resolve company information
			if (selectedTrainingProviderApplication.getCompany() != null && selectedTrainingProviderApplication.getCompany().getId() != null) {
				holdingCompany = companyService.resolveCompanyAddressesReturnCompany(companyService.findByKeyNoResolveData(selectedTrainingProviderApplication.getCompany().getId()));
			} else {
				holdingCompany = null;
			}
			
			// resolve site information if app.
			if (selectedTrainingProviderApplication.getTrainingSite() != null && selectedTrainingProviderApplication.getTrainingSite().getId() != null) {
				trainingSite = trainingSiteService.resolveAddressInformatioAndRegion(trainingSiteService.findByKey(selectedTrainingProviderApplication.getTrainingSite().getId()));
			} else {
				trainingSite = null;
				
			}
			
			// locate user link on SDP level
			if (trainingSite == null || trainingSite.getId() == null) {
				sdpCompanyLink = sdpCompanyService.findBySdpIdAndCompanyIdReturnOneResult(getSessionUI().getActiveUser().getId(), holdingCompany.getId());
			} else {
				sdpCompanyLink = sdpCompanyService.findBySdpIdCompanyIdAndTrainingSiteIdReturnOneResult(getSessionUI().getActiveUser().getId(), holdingCompany.getId(), trainingSite.getId());
			}
			
			// check if user link found
			if (sdpCompanyLink == null) {
				selectedTrainingProviderApplication = null;
				holdingCompany = null;
				trainingSite = null;
				throw new Exception("Unable to locate link. Contact support!");
			} else {
				// Locate SDPs assigned
				sdpCompanyDataModelInfo();
				
				// locate Ass / Mods
				assessorModeratorCompanySitesDataModelInfo();
				
				// load legacy data 
				legacyDataModelInfo();
				
				addInfoMessage("Action Complete");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void sdpCompanyDataModelInfo() {
		sdpCompanyDataModel = new LazyDataModel<SDPCompany>() {
			private static final long serialVersionUID = 1L;
			private List<SDPCompany> retorno = new ArrayList<>();
			@Override
			public List<SDPCompany> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (selectedTrainingProviderApplication != null && selectedTrainingProviderApplication.getTrainingSite() != null) {
						if (selectedTrainingProviderApplication.getTrainingSite().getId() != null) {
							retorno = sdpCompanyService.allSdpLinkedToTrainingSite(first, pageSize, sortField, sortOrder, filters, selectedTrainingProviderApplication.getTrainingSite().getId());
							sdpCompanyDataModel.setRowCount(sdpCompanyService.countAllSdpLinkedToTrainingSite(filters));
						}
					} else if(selectedTrainingProviderApplication.getCompany() != null && selectedTrainingProviderApplication.getCompany().getId() != null) {
						retorno = sdpCompanyService.allSdpLinkedToHoldingCompany(first, pageSize, sortField, sortOrder, filters, selectedTrainingProviderApplication.getCompany().getId());
						sdpCompanyDataModel.setRowCount(sdpCompanyService.countAllSdpLinkedToHoldingCompany(filters));
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SDPCompany obj) {
				return obj.getId();
			}

			@Override
			public SDPCompany getRowData(String rowKey) {
				for (SDPCompany obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void assessorModeratorCompanySitesDataModelInfo() {
		assessorModeratorCompanySitesDataModel = new LazyDataModel<AssessorModeratorCompanySites>() {
			private static final long serialVersionUID = 1L;
			private List<AssessorModeratorCompanySites> retorno = new ArrayList<>();
			@Override
			public List<AssessorModeratorCompanySites> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (selectedTrainingProviderApplication != null && selectedTrainingProviderApplication.getTrainingSite() != null) {
						if (selectedTrainingProviderApplication.getTrainingSite().getId() != null) {
							retorno = assessorModeratorCompanySitesService.allAssessorModeratorLinkedToTrainingSite(first, pageSize, sortField, sortOrder, filters, selectedTrainingProviderApplication.getTrainingSite().getId());
							assessorModeratorCompanySitesDataModel.setRowCount(assessorModeratorCompanySitesService.countAllAssessorModeratorLinkedToTrainingSite(filters));
						}
					} else {
						if (selectedTrainingProviderApplication.getCompany() != null && selectedTrainingProviderApplication.getCompany().getId() != null) {
							retorno = assessorModeratorCompanySitesService.allAssessorModeratorLinkedToHoldingCompany(first, pageSize, sortField, sortOrder, filters, selectedTrainingProviderApplication.getCompany().getId());
							assessorModeratorCompanySitesDataModel.setRowCount(assessorModeratorCompanySitesService.countAllAssessorModeratorLinkedToHoldingCompany(filters));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(AssessorModeratorCompanySites obj) {
				return obj.getId();
			}
			@Override
			public AssessorModeratorCompanySites getRowData(String rowKey) {
				for (AssessorModeratorCompanySites obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void legacyDataModelInfo() {

		legacyDataModel = new LazyDataModel<LegacyProviderAccreditation>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyProviderAccreditation> retorno = new ArrayList<LegacyProviderAccreditation>();

			@Override
			public List<LegacyProviderAccreditation> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					filters.put("linkedSdl", selectedTrainingProviderApplication.getCompany().getLevyNumber());
					retorno = legacyProviderAccreditationService.allLegacyProviderAccreditation(LegacyProviderAccreditation.class, first, pageSize, sortField, sortOrder, filters);
					legacyDataModel.setRowCount(legacyProviderAccreditationService.count(LegacyProviderAccreditation.class, filters));
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

	/* Getters and setters */
	public boolean isLinkedToHoldingCompany() {
		return linkedToHoldingCompany;
	}

	public void setLinkedToHoldingCompany(boolean linkedToHoldingCompany) {
		this.linkedToHoldingCompany = linkedToHoldingCompany;
	}

	public boolean isLinkedToSite() {
		return linkedToSite;
	}

	public void setLinkedToSite(boolean linkedToSite) {
		this.linkedToSite = linkedToSite;
	}

	public TrainingProviderApplication getSelectedTrainingProviderApplication() {
		return selectedTrainingProviderApplication;
	}

	public void setSelectedTrainingProviderApplication(TrainingProviderApplication selectedTrainingProviderApplication) {
		this.selectedTrainingProviderApplication = selectedTrainingProviderApplication;
	}

	public LazyDataModel<TrainingProviderApplication> getTrainingProviderApplicationDataModel() {
		return trainingProviderApplicationDataModel;
	}

	public void setTrainingProviderApplicationDataModel(LazyDataModel<TrainingProviderApplication> trainingProviderApplicationDataModel) {
		this.trainingProviderApplicationDataModel = trainingProviderApplicationDataModel;
	}

	public SDPCompany getSdpCompanyLink() {
		return sdpCompanyLink;
	}

	public void setSdpCompanyLink(SDPCompany sdpCompanyLink) {
		this.sdpCompanyLink = sdpCompanyLink;
	}

	public TrainingSite getTrainingSite() {
		return trainingSite;
	}

	public void setTrainingSite(TrainingSite trainingSite) {
		this.trainingSite = trainingSite;
	}

	public LazyDataModel<LegacyProviderAccreditation> getLegacyDataModel() {
		return legacyDataModel;
	}

	public void setLegacyDataModel(LazyDataModel<LegacyProviderAccreditation> legacyDataModel) {
		this.legacyDataModel = legacyDataModel;
	}

	public LazyDataModel<SDPCompany> getSdpCompanyDataModel() {
		return sdpCompanyDataModel;
	}

	public void setSdpCompanyDataModel(LazyDataModel<SDPCompany> sdpCompanyDataModel) {
		this.sdpCompanyDataModel = sdpCompanyDataModel;
	}

	public LazyDataModel<AssessorModeratorCompanySites> getAssessorModeratorCompanySitesDataModel() {
		return assessorModeratorCompanySitesDataModel;
	}

	public void setAssessorModeratorCompanySitesDataModel(
			LazyDataModel<AssessorModeratorCompanySites> assessorModeratorCompanySitesDataModel) {
		this.assessorModeratorCompanySitesDataModel = assessorModeratorCompanySitesDataModel;
	}

}
