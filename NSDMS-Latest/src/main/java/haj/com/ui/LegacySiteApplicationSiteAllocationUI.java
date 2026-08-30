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

import haj.com.entity.Address;
import haj.com.entity.Blank;
import haj.com.entity.Doc;
import haj.com.entity.LegacyProviderApplicationSiteAllocation;
import haj.com.entity.SDPCompany;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.TrainingSite;
import haj.com.entity.enums.AccreditationApplicationTypeEnum;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AddressService;
import haj.com.service.DocService;
import haj.com.service.LegacyProviderApplicationSiteAllocationService;
import haj.com.service.SDPCompanyService;
import haj.com.service.TrainingProviderApplicationService;
import haj.com.service.TrainingSiteService;

@ManagedBean(name = "legacySiteApplicationSiteAllocationUI")
@ViewScoped
public class LegacySiteApplicationSiteAllocationUI extends AbstractUI {
	
	/* Entity Levels */
	private LegacyProviderApplicationSiteAllocation legacyProviderApplicationSiteAllocation = null;
	private LegacyProviderApplicationSiteAllocation legacyProviderApplicationSiteAllocationView = null;
	private TrainingSite assignedTrainingSite = null;
	private TrainingProviderApplication selectedTrainingProviderApplication = null;
	private Doc doc;

	/* Service Levels */
	private TrainingProviderApplicationService trainingProviderApplicationService = new TrainingProviderApplicationService();
	private LegacyProviderApplicationSiteAllocationService legacyProviderApplicationSiteAllocationService = new LegacyProviderApplicationSiteAllocationService();
	private TrainingSiteService trainingSiteService = new TrainingSiteService();
	private SDPCompanyService sdpCompanyService = new SDPCompanyService();

	/* Lazy Data Models */
	private LazyDataModel<TrainingProviderApplication> trainingProviderApplicationDataModel;
	private LazyDataModel<LegacyProviderApplicationSiteAllocation> legacyProviderApplicationSiteAllocationDataModel;
	private LazyDataModel<TrainingSite> trainingSiteDataModel;
	private LazyDataModel<SDPCompany> sdpCompanyDataModel;
	
	/* Array Lists */
	private List<Doc> supportingDocs = new ArrayList<>();

	/* Vars */
	private Boolean canAlter = false;
	private Boolean newSite = false;
	private String setmisValidiationSite = "";

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

	/**
	 * Initialize method to get all Blank and prepare a for a create of a new
	 * Blank.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see Blank
	 */
	private void runInit() throws Exception {
		// check if session user has permission to view the page
		if (getSessionUI().getUser() != null && getSessionUI().getUser().getUserPermissions() != null && getSessionUI().getUser().getUserPermissions().getLegacySiteAllocation()) {
			checkIfPermissionToAlter();
			legacyProviderApplicationSiteAllocationDataModelInfo();
			trainingProviderApplicationDataModelInfo();
		} else {
			ajaxRedirectToDashboard();
		}
	}

	private void checkIfPermissionToAlter() throws Exception {
		if (getSessionUI().getUser() != null && getSessionUI().getUser().getUserPermissions() != null
				&& getSessionUI().getUser().getUserPermissions().getLegacySiteAllocationAlter() != null
				&& getSessionUI().getUser().getUserPermissions().getLegacySiteAllocationAlter()) {
			canAlter = true;
		} else {
			canAlter = false;
		}
	}

	public void legacyProviderApplicationSiteAllocationDataModelInfo() {
		legacyProviderApplicationSiteAllocationDataModel = new LazyDataModel<LegacyProviderApplicationSiteAllocation>() {
			private static final long serialVersionUID = 1L;
			private List<LegacyProviderApplicationSiteAllocation> retorno = new ArrayList<>();
			@Override
			public List<LegacyProviderApplicationSiteAllocation> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = legacyProviderApplicationSiteAllocationService.allLegacyProviderApplicationSiteAllocation(LegacyProviderApplicationSiteAllocation.class, first, pageSize, sortField, sortOrder, filters);
					legacyProviderApplicationSiteAllocationDataModel.setRowCount(legacyProviderApplicationSiteAllocationService.count(LegacyProviderApplicationSiteAllocation.class, filters));
				} catch (Exception e) {
					addErrorMessage(e.getMessage(), e);
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(LegacyProviderApplicationSiteAllocation obj) {
				return obj.getId();
			}
			@Override
			public LegacyProviderApplicationSiteAllocation getRowData(String rowKey) {
				for (LegacyProviderApplicationSiteAllocation obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}
	
	
	public void trainingProviderApplicationDataModelInfo() {
		trainingProviderApplicationDataModel = new LazyDataModel<TrainingProviderApplication>() {
			private static final long serialVersionUID = 1L;
			private List<TrainingProviderApplication> retorno = new ArrayList<>();
			@Override
			public List<TrainingProviderApplication> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = trainingProviderApplicationService.allTrainingProviderApplicationByStatusAndLegacyLinkedAndApplicationTypeList(first, pageSize, sortField, sortOrder, filters, ApprovalEnum.getOpenStatusForTrainingProviderApplicationsForLegacy(), AccreditationApplicationTypeEnum.getApplicationSiteAllocation());
					trainingProviderApplicationDataModel.setRowCount(trainingProviderApplicationService.countAllTrainingProviderApplicationByStatusAndLegacyLinkedAndApplicationTypeList(filters, ApprovalEnum.getOpenStatusForTrainingProviderApplicationsForLegacy(), AccreditationApplicationTypeEnum.getApplicationSiteAllocation()));
				} catch (Exception e) {
					addErrorMessage(e.getMessage(), e);
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
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}
	
	public void selectProviderApplication(){
		try {
			assignedTrainingSite = null;
			setmisValidiationSite = "";
			newSite = false;
			if (selectedTrainingProviderApplication.getTrainingSite() != null) {
				selectedTrainingProviderApplication = null;
				doc = null;
				throw new Exception("Site already allocation to legacy application. Please select a different application.");
			} else {
				trainingSiteDataModelInfo();
				prepDocUpload();
				addInfoMessage("Application Selected");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void trainingSiteDataModelInfo() {
		trainingSiteDataModel = new LazyDataModel<TrainingSite>() {
			private static final long serialVersionUID = 1L;
			private List<TrainingSite> retorno = new ArrayList<>();
			@Override
			public List<TrainingSite> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (selectedTrainingProviderApplication.getCompany() != null && selectedTrainingProviderApplication.getCompany().getId() != null) {
						retorno = trainingSiteService.allTrainingSiteLinkedToCompanyResolveRegionData(first, pageSize, sortField, sortOrder, filters, selectedTrainingProviderApplication.getCompany().getId());
						trainingSiteDataModel.setRowCount(trainingSiteService.countAllTrainingSiteLinkedToCompany(filters));
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(TrainingSite obj) {
				return obj.getId();
			}
			@Override
			public TrainingSite getRowData(String rowKey) {
				for (TrainingSite obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void prepNewSite(){
		try {
			supportingDocs.clear();
			newSite = true;
			assignedTrainingSite = new TrainingSite();
			assignedTrainingSite.setCompany(selectedTrainingProviderApplication.getCompany());
			assignedTrainingSite.setPostalAddress(new Address());
			assignedTrainingSite.getPostalAddress().setSameAddress(false);
			assignedTrainingSite.setResidentialAddress(new Address());
			setmisValidiationSite = "";
			sdpCompanyDataModelInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void selectTrainingSite(){
		try {
			supportingDocs.clear();
			trainingSiteService.resolveAddressInformatioAndRegion(assignedTrainingSite);
			newSite = false;
			sdpCompanyDataModelInfo();
			addInfoMessage("Site Selected");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void clearSelectionOfSite(){
		try {
			supportingDocs.clear();
			newSite = false;
			assignedTrainingSite = null;
			setmisValidiationSite = "";
			addWarningMessage("Selection Cleared.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void prepDocUpload() throws Exception {
		supportingDocs = new ArrayList<>();
		prepNewDoc();
	}
	
	public void prepUpload(){
		try {
			prepNewDoc();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepNewDoc() throws Exception{
		doc = new Doc();
	}
	
	public void sdpCompanyDataModelInfo() {
		sdpCompanyDataModel = new LazyDataModel<SDPCompany>() {
			private static final long serialVersionUID = 1L;
			private List<SDPCompany> retorno = new ArrayList<>();
			@Override
			public List<SDPCompany> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (assignedTrainingSite != null) {
						if (assignedTrainingSite.getId() != null) {
							retorno = sdpCompanyService.allSdpLinkedToTrainingSite(first, pageSize, sortField, sortOrder, filters, assignedTrainingSite.getId());
							sdpCompanyDataModel.setRowCount(sdpCompanyService.countAllSdpLinkedToTrainingSite(filters));
						}
					} else {
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
	
	public void clearPostalTrainingSite() {
		if (assignedTrainingSite != null && assignedTrainingSite.getPostalAddress() != null && assignedTrainingSite.getPostalAddress().getSameAddress() != null && assignedTrainingSite.getPostalAddress().getSameAddress()) {
			AddressService.instance().clearAddress(assignedTrainingSite.getPostalAddress());
		}
	}

	public void clearProviderSelection(){
		try {
			legacyProviderApplicationSiteAllocation = null;
			selectedTrainingProviderApplication = null;
			supportingDocs.clear();
			addWarningMessage("Clear Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void storeNewFile(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			if (doc.getId() != null) {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			}
			// add support doc
			if (supportingDocs == null) {
				supportingDocs = new ArrayList<>();
			}
			supportingDocs.add(doc);
			runClientSideExecute("PF('uploadSupportingDocDlg').hide()");
			addInfoMessage("Documents Uploaded, Awaiting Submission");
			prepNewDoc();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void removeDocFromList(){
		try {
			if (doc.getId() == null) {
				supportingDocs.remove(doc);
				doc = null;
				addWarningMessage("Document Removed.");
			} else {
				addWarningMessage("Doc already saved. you can not remove it.");
				doc = null;
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void submitNewEntry(){
		try {
			if (supportingDocs == null || supportingDocs.isEmpty()) {
				addErrorMessage("Provide atleast one supporting document before proceeding.");
			} else {
				setmisValidiationSite = "";
				if (assignedTrainingSite.getId() == null) {
					setmisValidiationSite = trainingSiteService.validiateSiteInformation(assignedTrainingSite).toString();
					if (!setmisValidiationSite.isEmpty()) {
						throw new Exception("SETMIS Validiation Exception. Please attend to errors before proceeding.");
					}
				}
				legacyProviderApplicationSiteAllocationService.submitNewEntry(supportingDocs, getSessionUI().getActiveUser(), selectedTrainingProviderApplication, assignedTrainingSite, "");
				assignedTrainingSite = null;
				selectedTrainingProviderApplication = null;
				legacyProviderApplicationSiteAllocationDataModelInfo();
				trainingProviderApplicationDataModelInfo();
				supportingDocs.clear();
				addInfoMessage("Action Complete");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/* Getters and setters */
	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public Boolean getCanAlter() {
		return canAlter;
	}

	public void setCanAlter(Boolean canAlter) {
		this.canAlter = canAlter;
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

	public List<Doc> getSupportingDocs() {
		return supportingDocs;
	}

	public void setSupportingDocs(List<Doc> supportingDocs) {
		this.supportingDocs = supportingDocs;
	}

	public LazyDataModel<LegacyProviderApplicationSiteAllocation> getLegacyProviderApplicationSiteAllocationDataModel() {
		return legacyProviderApplicationSiteAllocationDataModel;
	}

	public void setLegacyProviderApplicationSiteAllocationDataModel(
			LazyDataModel<LegacyProviderApplicationSiteAllocation> legacyProviderApplicationSiteAllocationDataModel) {
		this.legacyProviderApplicationSiteAllocationDataModel = legacyProviderApplicationSiteAllocationDataModel;
	}

	public LegacyProviderApplicationSiteAllocation getLegacyProviderApplicationSiteAllocation() {
		return legacyProviderApplicationSiteAllocation;
	}

	public void setLegacyProviderApplicationSiteAllocation(
			LegacyProviderApplicationSiteAllocation legacyProviderApplicationSiteAllocation) {
		this.legacyProviderApplicationSiteAllocation = legacyProviderApplicationSiteAllocation;
	}

	public LegacyProviderApplicationSiteAllocation getLegacyProviderApplicationSiteAllocationView() {
		return legacyProviderApplicationSiteAllocationView;
	}

	public void setLegacyProviderApplicationSiteAllocationView(
			LegacyProviderApplicationSiteAllocation legacyProviderApplicationSiteAllocationView) {
		this.legacyProviderApplicationSiteAllocationView = legacyProviderApplicationSiteAllocationView;
	}

	public LazyDataModel<TrainingSite> getTrainingSiteDataModel() {
		return trainingSiteDataModel;
	}

	public void setTrainingSiteDataModel(LazyDataModel<TrainingSite> trainingSiteDataModel) {
		this.trainingSiteDataModel = trainingSiteDataModel;
	}

	public TrainingSite getAssignedTrainingSite() {
		return assignedTrainingSite;
	}

	public void setAssignedTrainingSite(TrainingSite assignedTrainingSite) {
		this.assignedTrainingSite = assignedTrainingSite;
	}

	public Boolean getNewSite() {
		return newSite;
	}

	public void setNewSite(Boolean newSite) {
		this.newSite = newSite;
	}

	public String getSetmisValidiationSite() {
		return setmisValidiationSite;
	}

	public void setSetmisValidiationSite(String setmisValidiationSite) {
		this.setmisValidiationSite = setmisValidiationSite;
	}

	public LazyDataModel<SDPCompany> getSdpCompanyDataModel() {
		return sdpCompanyDataModel;
	}

	public void setSdpCompanyDataModel(LazyDataModel<SDPCompany> sdpCompanyDataModel) {
		this.sdpCompanyDataModel = sdpCompanyDataModel;
	}

}
