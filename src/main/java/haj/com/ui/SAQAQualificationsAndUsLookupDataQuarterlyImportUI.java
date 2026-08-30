package haj.com.ui;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import haj.com.entity.lookup.Qualification;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.saqa.qualifications.LoadQualification;
import haj.com.service.lookup.QualificationService;

@ManagedBean(name = "sAQAQualificationsAndUsLookupDataQuarterlyImportUI")
@ViewScoped
public class SAQAQualificationsAndUsLookupDataQuarterlyImportUI extends AbstractUI {

	private static final long serialVersionUID = 1L;
	private UploadedFile uploadedfileQualification = null;
	private UploadedFile uploadedfileUnitStandards = null;
	
	private QualificationService qualificationService = new QualificationService();

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
	 * @throws Exception
	 */
	private void runInit() throws Exception {		
		prepareNewQualifications();
		prepareNewUnitStandards();
	}	

	public void prepareNewQualifications() {
		uploadedfileQualification = null;
	}
	
	public void prepareNewUnitStandards() {
		uploadedfileUnitStandards = null;
	}
	
	public void prepareNewLinkages() {
		uploadedfileQualification = null;
		uploadedfileUnitStandards = null;
	}
	
	/**
	 * @param event
	 */
	public void handleFileUploadQualification(FileUploadEvent event) {
		
		try {
			
			uploadedfileQualification = event.getFile();
			
			super.addInfoMessage("Upload Qualifications XML File was Succesful");
			
		} catch (Exception e) {
			
			addErrorMessage("Saqa Qualification and Unit standards XML data import was unsuccessful");
			e.printStackTrace();
		}
	}
	
	/**
	 * @param event
	 */
	public void handleFileUploadUnitStandards(FileUploadEvent event) {
		
		try {
			
			uploadedfileUnitStandards = event.getFile();
			
			super.addInfoMessage("Upload Unit Standards XML File was Succesful");
			
		} catch (Exception exception) {
			
			addErrorMessage("Unit standards XML data import was unsuccessful : " + exception.getMessage());
			
			exception.printStackTrace();
		}
	}
	
	public void processUploadedXmlDataQualifications() {
		
		try {
			
			LoadQualification.loadAndProcessQualifications(uploadedfileQualification.getInputstream());
			
		} catch(Exception exception){
			
			if(exception.getMessage().contains("Processing successful !")){
				
				super.runClientSideExecute("PF('dlgWaitNonAjaxQualifications').hide()");
				
				this.addInfoMessage("Qualification processing successful !");
			}
			
			addErrorMessage("SAQA Qualifications XML data import was unsuccessful : " + exception.getMessage());
			
			super.runClientSideExecute("PF('dlgWaitNonAjaxQualifications').hide()");
			
			exception.printStackTrace();
		}
	}
	
	public void processUploadedXmlDataUnitStandards() {
		
		try {
			
			LoadQualification.loadAndProcessUnitStandards(uploadedfileUnitStandards.getInputstream());
			
		} catch(Exception exception){
			
			if(exception.getMessage().contains("Processing successful !")){
				
				super.runClientSideExecute("PF('dlgWaitNonAjaxUnitStandards').hide()");
				
				this.addInfoMessage("Unit Standards processing successful !");
			}
			
			addErrorMessage("Unit Standards XML data import was unsuccessful : " + exception.getMessage());
			
			super.runClientSideExecute("PF('dlgWaitNonAjaxUnitStandards').hide()");
			
			exception.printStackTrace();
		}
	}
	
	public void updateLearneringProgram(){
		try {
			qualificationService.updateLearneringProgrammesWithInformation();
			addInfoMessage("Action complete, mail confirmation will be sent after completion");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void updateSubFiled(){
		try {
			qualificationService.updateQualificationSubSet();
			addInfoMessage("Action complete, mail confirmation will be sent after completion");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
//	public void processUploadedXmlDataLinkages() {
//		
//		try {
//			
//			LoadQualification.loadQualificationAndUnitStandardsLinkages();
//			
//		} catch(Exception exception){
//			
//			if(exception.getMessage().contains("Processing successful !")){
//				
//				super.runClientSideExecute("PF('dlgWaitNonAjaxLinkages').hide()");
//				
//				this.addInfoMessage("Qualification and Unit Standards linkage processing successful !");
//			}
//			
//			addErrorMessage("Qualifications and Unit Standards Linkages processing was unsuccessful : " + exception.getMessage());
//			
//			super.runClientSideExecute("PF('dlgWaitNonAjaxLinkages').hide()");
//			
//			exception.printStackTrace();
//		}
//	}
	
	
	public UploadedFile getUploadedfileQualification() {
		return uploadedfileQualification;
	}

	public void setUploadedfileQualification(UploadedFile uploadedfileQualification) {
		this.uploadedfileQualification = uploadedfileQualification;
	}

	public UploadedFile getUploadedfileUnitStandards() {
		return uploadedfileUnitStandards;
	}

	public void setUploadedfileUnitStandards(UploadedFile uploadedfileUnitStandards) {
		this.uploadedfileUnitStandards = uploadedfileUnitStandards;
	}
}
