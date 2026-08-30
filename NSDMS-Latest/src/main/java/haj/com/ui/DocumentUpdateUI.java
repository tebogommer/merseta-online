package  haj.com.ui;

import javax.annotation.PostConstruct;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.DocumentUpdate;
import haj.com.service.DocumentUpdateService;
import org.primefaces.model.LazyDataModel;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "documentupdateUI")
@ViewScoped
public class DocumentUpdateUI extends AbstractUI {

	private DocumentUpdateService service = new DocumentUpdateService();
	private List<DocumentUpdate> documentupdateList = null;
	private List<DocumentUpdate> documentupdatefilteredList = null;
	private DocumentUpdate documentupdate = null;
	private LazyDataModel<DocumentUpdate> dataModel; 

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
	 * Initialize method to get all DocumentUpdate and prepare a for a create of a new DocumentUpdate
 	 * @author TechFinium 
 	 * @see    DocumentUpdate
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		documentupdateInfo();
	}

	/**
	 * Get all DocumentUpdate for data table
 	 * @author TechFinium 
 	 * @see    DocumentUpdate
 	 */
	public void documentupdateInfo() {
			//dataModel = new DocumentUpdateDatamodel();
	}

	/**
	 * Insert DocumentUpdate into database
 	 * @author TechFinium 
 	 * @see    DocumentUpdate
 	 */
	public void documentupdateInsert() {
		try {
				 service.create(this.documentupdate);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 documentupdateInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update DocumentUpdate in database
 	 * @author TechFinium 
 	 * @see    DocumentUpdate
 	 */
    public void documentupdateUpdate() {
		try {
				 service.update(this.documentupdate);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 documentupdateInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete DocumentUpdate from database
 	 * @author TechFinium 
 	 * @see    DocumentUpdate
 	 */
	public void documentupdateDelete() {
		try {
			 service.delete(this.documentupdate);
			  prepareNew();
			 documentupdateInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of DocumentUpdate 
 	 * @author TechFinium 
 	 * @see    DocumentUpdate
 	 */
	public void prepareNew() {
		documentupdate = new DocumentUpdate();
	}

/*
    public List<SelectItem> getDocumentUpdateDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	documentupdateInfo();
    	for (DocumentUpdate ug : documentupdateList) {
    		// l.add(new SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc()));
		}
    	return l;
    }
  */
  
    /**
     * Complete.
     *
     * @param desc the desc
     * @return the list
     */  
    public List<DocumentUpdate> complete(String desc) {
		List<DocumentUpdate> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		}
		catch (Exception e) {
		addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
    
    public List<DocumentUpdate> getDocumentUpdateList() {
		return documentupdateList;
	}
	public void setDocumentUpdateList(List<DocumentUpdate> documentupdateList) {
		this.documentupdateList = documentupdateList;
	}
	public DocumentUpdate getDocumentupdate() {
		return documentupdate;
	}
	public void setDocumentupdate(DocumentUpdate documentupdate) {
		this.documentupdate = documentupdate;
	}

    public List<DocumentUpdate> getDocumentUpdatefilteredList() {
		return documentupdatefilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param documentupdatefilteredList the new documentupdatefilteredList list
 	 * @see    DocumentUpdate
 	 */	
	public void setDocumentUpdatefilteredList(List<DocumentUpdate> documentupdatefilteredList) {
		this.documentupdatefilteredList = documentupdatefilteredList;
	}

	
	public LazyDataModel<DocumentUpdate> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<DocumentUpdate> dataModel) {
		this.dataModel = dataModel;
	}
	
}
