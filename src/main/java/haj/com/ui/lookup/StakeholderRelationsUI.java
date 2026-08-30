package haj.com.ui.lookup;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Faces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;

import haj.com.entity.Doc;
import haj.com.entity.DocumentTracker;
import haj.com.entity.datamodel.lookup.StakeholderRelationsDatamodel;
import haj.com.entity.enums.DocumentTrackerEnum;
import haj.com.entity.lookup.StakeholderRelations;
import haj.com.entity.lookup.StakeholderRelationsSurvey;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.DocService;
import haj.com.service.DocumentTrackerService;
import haj.com.service.lookup.StakeholderRelationsService;
import haj.com.service.lookup.StakeholderRelationsSurveyService;
import haj.com.ui.UploadDocUI;
import haj.com.utils.GenericUtility;

@ManagedBean(name = "stakeholderrelationsUI")
@ViewScoped
public class StakeholderRelationsUI extends AbstractUI {

	private StakeholderRelationsService service = new StakeholderRelationsService();
	private StakeholderRelationsSurveyService stakeholderRelationsSurveyService = new StakeholderRelationsSurveyService();
	private List<StakeholderRelations> stakeholderrelationsList = null;
	private List<StakeholderRelations> stakeholderrelationsfilteredList = null;
	private StakeholderRelations stakeholderrelations = null;
	private StakeholderRelations stakeholderrelationsDocSelection = null;
	private LazyDataModel<StakeholderRelations> dataModel;
	private StakeholderRelationsSurvey stakeholderrelationssurvey = null;
	
	/** doc information */
	private Doc doc;
	private DocService docService = new DocService();
	private boolean showDocForm = false;
	private List<Doc> docs;
	private List<Doc> docsfiltered;
	private List<DocumentTracker> documentTrackers;
	private DocumentTrackerService documentTrackerService = new DocumentTrackerService();


	/**  Manage Property. */
	@ManagedProperty(value = "#{uploadDocUI}")
	private UploadDocUI uploadDocUI;

	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Initialize method to get all StakeholderRelations and prepare a for a
	 * create of a new StakeholderRelations
	 * 
	 * @author TechFinium
	 * @see StakeholderRelations
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		stakeholderrelationsInfo();
	}

	/**
	 * Get all StakeholderRelations for data table
	 * 
	 * @author TechFinium
	 * @see StakeholderRelations
	 */
	public void stakeholderrelationsInfo() {
		showDocForm = false;
		stakeholderrelationsDocSelection = null;
		dataModel = new StakeholderRelationsDatamodel();
	}

	/**
	 * Insert StakeholderRelations into database
	 * 
	 * @author TechFinium
	 * @see StakeholderRelations
	 */
	public void stakeholderrelationsInsert() {
		try {
			service.create(this.stakeholderrelations);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			stakeholderrelationsInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update StakeholderRelations in database
	 * 
	 * @author TechFinium
	 * @see StakeholderRelations
	 */
	public void stakeholderrelationsUpdate() {
		try {
			service.update(this.stakeholderrelations);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			stakeholderrelationsInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete StakeholderRelations from database
	 * 
	 * @author TechFinium
	 * @see StakeholderRelations
	 */
	public void stakeholderrelationsDelete() {
		try {
			showDocForm = false;
			stakeholderrelationsDocSelection = null;
			service.delete(this.stakeholderrelations);
			prepareNew();
			stakeholderrelationsInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Preps new instance of stakeholderrelationssurvey
	 */
	public void prepNewStakeholderRelationsSurvey(){
		try {
			showDocForm = false;
			stakeholderrelationsDocSelection = null;
			stakeholderrelationssurvey = new StakeholderRelationsSurvey();
			if (stakeholderrelations == null || stakeholderrelations.getId() == null) {
				throw new Exception("Unable To Assign Stakeholder Relations, contact support!");
			}
			stakeholderrelationssurvey.setStakeholderRelations(stakeholderrelations);
			runClientSideExecute("PF('dlgQuestion').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}

	/**
	 * Insert StakeholderRelationsSurvey into database
	 * 
	 * @author TechFinium
	 * @see StakeholderRelationsSurvey
	 */
	public void stakeholderrelationssurveyInsert() {
		try {
			if (stakeholderrelations == null || stakeholderrelations.getId() == null) {
				throw new Exception("Unable To Assign Stakeholder Relations, contact support!");
			}
			stakeholderRelationsSurveyService.create(this.stakeholderrelationssurvey);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			runClientSideExecute("PF('dlgQuestion').hide()");
			stakeholderrelationsInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Updates StakeholderRelationsSurvey into database
	 * 
	 * @author TechFinium
	 * @see StakeholderRelationsSurvey
	 */
	public void stakeholderrelationssurveyUpdate() {
		try {
			stakeholderRelationsSurveyService.create(this.stakeholderrelationssurvey);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			runClientSideExecute("PF('dlgQuestion').hide()");
			stakeholderrelationsInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete StakeholderRelationsSurvey from database
	 * 
	 * @author TechFinium
	 * @see StakeholderRelationsSurvey
	 */
	public void stakeholderrelationssurveyDelete() {
		try {
			stakeholderRelationsSurveyService.delete(this.stakeholderrelationssurvey);
			prepareNew();
			stakeholderrelationsInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of StakeholderRelations
	 * 
	 * @author TechFinium
	 * @see StakeholderRelations
	 */
	public void prepareNew() {
		showDocForm = false;
		stakeholderrelationsDocSelection = null;
		stakeholderrelations = new StakeholderRelations();
	}

	/*
	 * public List<SelectItem> getStakeholderRelationsDD() { List<SelectItem> l
	 * =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * stakeholderrelationsInfo(); for (StakeholderRelations ug :
	 * stakeholderrelationsList) { // l.add(new SelectItem(ug.getUserGroupId(),
	 * ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<StakeholderRelations> complete(String desc) {
		List<StakeholderRelations> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	/**
	 * Sends out a notification
	 */
	public void sendNotification(){
		try {
			if (stakeholderrelations != null) {
				service.notifyusers(stakeholderrelations);
				addInfoMessage(super.getEntryLanguage("notification.sent"));
			} else {
				throw new Exception("Unable to locate notice, contact support!");
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}
	
	/**
	 * Documents Section 
	 * Start - 
	 */
	
	/**
	 * Preps upload of new documents and locates documents assigned
	 */
	public void viewDoucments(){
		try {
			showDocForm = true;
			initDoc();
			findDocs();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}
	
	/**
	 * Find docs.
	 */
	private void findDocs() {
		try {
			this.docs = docService.searchByTargetKeyAndClass(stakeholderrelationsDocSelection.getClass().getName(), stakeholderrelationsDocSelection.getId());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Inits the doc.
	 */
	private void initDoc() {
		this.doc = new Doc();
		this.doc.setTargetClass(stakeholderrelationsDocSelection.getClass().getName());
		this.doc.setTargetKey(stakeholderrelationsDocSelection.getId());
		this.doc.setUsers(getSessionUI().getActiveUser());
	}

	/**
	 * Safe file.
	 *
	 * @param event the event
	 */
	public void safeFile(FileUploadEvent event) {
		try {
			docService.save(doc, event.getFile().getContents(), event.getFile().getFileName(), getSessionUI().getUser(),
					this.docs);
			addInfoMessage("Your file has been uploaded successfully!");
			initDoc();
			findDocs();
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Download.
	 */
	public void download() {
		try {
			DocumentTrackerService.create(getSessionUI().getUser(), doc, DocumentTrackerEnum.Downloaded);
			Faces.sendFile(doc.getData(), GenericUtility.buidFileName(doc), true);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Show history.
	 */
	public void showHistory() {
		try {
			this.documentTrackers = documentTrackerService.byRoot(doc);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Builds the stream content.
	 */
	public void buildStreamContent() {
		getSessionUI().setSelDoc(doc);
		try {
			DocumentTrackerService.create(getSessionUI().getUser(), doc, DocumentTrackerEnum.Viewed);
		} catch (Exception e) {
			logger.fatal(e);
		}
	}
	
	/**
	 * Documents Section 
	 * - End
	 */
	

	public List<StakeholderRelations> getStakeholderRelationsList() {
		return stakeholderrelationsList;
	}

	public void setStakeholderRelationsList(List<StakeholderRelations> stakeholderrelationsList) {
		this.stakeholderrelationsList = stakeholderrelationsList;
	}

	public StakeholderRelations getStakeholderrelations() {
		return stakeholderrelations;
	}

	public void setStakeholderrelations(StakeholderRelations stakeholderrelations) {
		this.stakeholderrelations = stakeholderrelations;
	}

	public List<StakeholderRelations> getStakeholderRelationsfilteredList() {
		return stakeholderrelationsfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param stakeholderrelationsfilteredList
	 *            the new stakeholderrelationsfilteredList list
	 * @see StakeholderRelations
	 */
	public void setStakeholderRelationsfilteredList(List<StakeholderRelations> stakeholderrelationsfilteredList) {
		this.stakeholderrelationsfilteredList = stakeholderrelationsfilteredList;
	}

	public LazyDataModel<StakeholderRelations> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<StakeholderRelations> dataModel) {
		this.dataModel = dataModel;
	}

	public StakeholderRelationsSurvey getStakeholderrelationssurvey() {
		return stakeholderrelationssurvey;
	}

	public void setStakeholderrelationssurvey(StakeholderRelationsSurvey stakeholderrelationssurvey) {
		this.stakeholderrelationssurvey = stakeholderrelationssurvey;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public boolean isShowDocForm() {
		return showDocForm;
	}

	public void setShowDocForm(boolean showDocForm) {
		this.showDocForm = showDocForm;
	}

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	public List<Doc> getDocsfiltered() {
		return docsfiltered;
	}

	public void setDocsfiltered(List<Doc> docsfiltered) {
		this.docsfiltered = docsfiltered;
	}

	public List<DocumentTracker> getDocumentTrackers() {
		return documentTrackers;
	}

	public void setDocumentTrackers(List<DocumentTracker> documentTrackers) {
		this.documentTrackers = documentTrackers;
	}

	public UploadDocUI getUploadDocUI() {
		return uploadDocUI;
	}

	public void setUploadDocUI(UploadDocUI uploadDocUI) {
		this.uploadDocUI = uploadDocUI;
	}

	public StakeholderRelations getStakeholderrelationsDocSelection() {
		return stakeholderrelationsDocSelection;
	}

	public void setStakeholderrelationsDocSelection(StakeholderRelations stakeholderrelationsDocSelection) {
		this.stakeholderrelationsDocSelection = stakeholderrelationsDocSelection;
	}

}
