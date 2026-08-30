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

import haj.com.entity.Blank;
import haj.com.entity.Doc;
import haj.com.entity.ProviderApplicationSuspensionDeActivated;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ProviderSusActionsEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.DocService;
import haj.com.service.ProviderApplicationSuspensionDeActivatedService;
import haj.com.service.TrainingProviderApplicationService;

@ManagedBean(name = "providerSuspensionDeAcredUI")
@ViewScoped
public class ProviderSuspensionDeAcredUI extends AbstractUI {

	/* Entity Levels */
	private ProviderApplicationSuspensionDeActivated providerApplicationSuspensionDeActivated = null;
	private ProviderApplicationSuspensionDeActivated providerApplicationSuspensionDeActivatedView = null;
	private TrainingProviderApplication selectedTrainingProviderApplication = null;
	private Doc doc;

	/* Service Levels */
	private TrainingProviderApplicationService trainingProviderApplicationService = new TrainingProviderApplicationService();
	private ProviderApplicationSuspensionDeActivatedService providerApplicationSuspensionDeActivatedService = new ProviderApplicationSuspensionDeActivatedService();

	/* Lazy Data Models */
	private LazyDataModel<TrainingProviderApplication> trainingProviderApplicationDataModel;
	private LazyDataModel<ProviderApplicationSuspensionDeActivated> providerApplicationSuspensionDeActivatedDataModel;
	
	/* Array Lists */
	private List<Doc> supportingDocs = new ArrayList<>();
	
	/* Enums */
	private ProviderSusActionsEnum selectedAction = null;

	/* Vars */
	private Boolean canAlter = false;

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
		if (getSessionUI().getUser() != null && getSessionUI().getUser().getUserPermissions() != null && getSessionUI().getUser().getUserPermissions().getProviderSuspension()) {
			checkIfPermissionToAlter();
			providerApplicationSuspensionDeActivatedDataModelInfo();
		} else {
			ajaxRedirectToDashboard();
		}
	}

	private void checkIfPermissionToAlter() throws Exception {
		if (getSessionUI().getUser() != null && getSessionUI().getUser().getUserPermissions() != null
				&& getSessionUI().getUser().getUserPermissions().getProviderSuspensionAlter() != null
				&& getSessionUI().getUser().getUserPermissions().getProviderSuspensionAlter()) {
			canAlter = true;
		} else {
			canAlter = false;
		}
	}
	
	public void providerApplicationSuspensionDeActivatedDataModelInfo() {
		providerApplicationSuspensionDeActivatedDataModel = new LazyDataModel<ProviderApplicationSuspensionDeActivated>() {
			private static final long serialVersionUID = 1L;
			private List<ProviderApplicationSuspensionDeActivated> retorno = new ArrayList<>();
			@Override
			public List<ProviderApplicationSuspensionDeActivated> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = providerApplicationSuspensionDeActivatedService.allProviderApplicationSuspensionDeActivated(ProviderApplicationSuspensionDeActivated.class, first, pageSize, sortField, sortOrder, filters);
					providerApplicationSuspensionDeActivatedDataModel.setRowCount(providerApplicationSuspensionDeActivatedService.count(ProviderApplicationSuspensionDeActivated.class, filters));
				} catch (Exception e) {
					addErrorMessage(e.getMessage(), e);
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(ProviderApplicationSuspensionDeActivated obj) {
				return obj.getId();
			}

			@Override
			public ProviderApplicationSuspensionDeActivated getRowData(String rowKey) {
				for (ProviderApplicationSuspensionDeActivated obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}
	
	public void selectAction(){
		try {
			if (selectedAction != null) {
				if (selectedAction == ProviderSusActionsEnum.RemoveSuspension || selectedAction == ProviderSusActionsEnum.SuspendDeactivate) {
					trainingProviderApplicationDataModelInfo();
					addInfoMessage("Action Selected");
				} else {
					selectedAction = null;
					addWarningMessage("Unable to locate action. Contact Support!");
				}
			} else {
				addErrorMessage("Unable to locate action. Contact Support!");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void clearAction(){
		try {
			clearAllInfo();
			addWarningMessage("Clear Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void clearAllInfo(){
		selectedAction = null;
		providerApplicationSuspensionDeActivated = null;
		selectedTrainingProviderApplication = null;
		supportingDocs.clear();
	}
	
	public void trainingProviderApplicationDataModelInfo() {
		trainingProviderApplicationDataModel = new LazyDataModel<TrainingProviderApplication>() {
			private static final long serialVersionUID = 1L;
			private List<TrainingProviderApplication> retorno = new ArrayList<>();
			@Override
			public List<TrainingProviderApplication> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (selectedAction == ProviderSusActionsEnum.RemoveSuspension) {
						retorno = trainingProviderApplicationService.allTrainingProviderApplicationsByStatus(first, pageSize, sortField, sortOrder, filters, ApprovalEnum.Suspended);
						trainingProviderApplicationDataModel.setRowCount(trainingProviderApplicationService.countAllTrainingProviderApplicationsByStatus(filters));
					}else {
						retorno = trainingProviderApplicationService.allTrainingProviderApplicationsByStatus(first, pageSize, sortField, sortOrder, filters, ApprovalEnum.Approved);
						trainingProviderApplicationDataModel.setRowCount(trainingProviderApplicationService.countAllTrainingProviderApplicationsByStatus(filters));
					}
					
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
			providerApplicationSuspensionDeActivated = providerApplicationSuspensionDeActivatedService.prepNewEntryByProviderApplication(selectedTrainingProviderApplication, getSessionUI().getActiveUser(), selectedAction);
			prepDocUpload();
			addInfoMessage("Application Selected");
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

	public void clearProviderSelection(){
		try {
			providerApplicationSuspensionDeActivated = null;
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
				providerApplicationSuspensionDeActivatedService.submitNewEntry(providerApplicationSuspensionDeActivated, supportingDocs, getSessionUI().getActiveUser(), selectedTrainingProviderApplication);
				clearAllInfo();
				providerApplicationSuspensionDeActivatedDataModelInfo();
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

	public ProviderApplicationSuspensionDeActivated getProviderApplicationSuspensionDeActivated() {
		return providerApplicationSuspensionDeActivated;
	}

	public void setProviderApplicationSuspensionDeActivated(ProviderApplicationSuspensionDeActivated providerApplicationSuspensionDeActivated) {
		this.providerApplicationSuspensionDeActivated = providerApplicationSuspensionDeActivated;
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

	public void setTrainingProviderApplicationDataModel(
			LazyDataModel<TrainingProviderApplication> trainingProviderApplicationDataModel) {
		this.trainingProviderApplicationDataModel = trainingProviderApplicationDataModel;
	}

	public LazyDataModel<ProviderApplicationSuspensionDeActivated> getProviderApplicationSuspensionDeActivatedDataModel() {
		return providerApplicationSuspensionDeActivatedDataModel;
	}

	public void setProviderApplicationSuspensionDeActivatedDataModel(
			LazyDataModel<ProviderApplicationSuspensionDeActivated> providerApplicationSuspensionDeActivatedDataModel) {
		this.providerApplicationSuspensionDeActivatedDataModel = providerApplicationSuspensionDeActivatedDataModel;
	}

	public ProviderSusActionsEnum getSelectedAction() {
		return selectedAction;
	}

	public void setSelectedAction(ProviderSusActionsEnum selectedAction) {
		this.selectedAction = selectedAction;
	}

	public ProviderApplicationSuspensionDeActivated getProviderApplicationSuspensionDeActivatedView() {
		return providerApplicationSuspensionDeActivatedView;
	}

	public void setProviderApplicationSuspensionDeActivatedView(
			ProviderApplicationSuspensionDeActivated providerApplicationSuspensionDeActivatedView) {
		this.providerApplicationSuspensionDeActivatedView = providerApplicationSuspensionDeActivatedView;
	}

	public List<Doc> getSupportingDocs() {
		return supportingDocs;
	}

	public void setSupportingDocs(List<Doc> supportingDocs) {
		this.supportingDocs = supportingDocs;
	}

}
