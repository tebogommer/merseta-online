package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.MandatoryGrantDetailArchive;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.MandatoryGrantDetailArchiveService;

@ManagedBean(name = "mandatorygrantdetailarchiveUI")
@ViewScoped
public class MandatoryGrantDetailArchiveUI extends AbstractUI {

	private MandatoryGrantDetailArchiveService service = new MandatoryGrantDetailArchiveService();
	private List<MandatoryGrantDetailArchive> mandatorygrantdetailarchiveList = null;
	private List<MandatoryGrantDetailArchive> mandatorygrantdetailarchivefilteredList = null;
	private MandatoryGrantDetailArchive mandatorygrantdetailarchive = null;
	private LazyDataModel<MandatoryGrantDetailArchive> dataModel; 

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
	 * Initialize method to get all MandatoryGrantDetailArchive and prepare a for a create of a new MandatoryGrantDetailArchive
 	 * @author TechFinium 
 	 * @see    MandatoryGrantDetailArchive
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		mandatorygrantdetailarchiveInfo();
	}

	/**
	 * Get all MandatoryGrantDetailArchive for data table
 	 * @author TechFinium 
 	 * @see    MandatoryGrantDetailArchive
 	 */
	public void mandatorygrantdetailarchiveInfo() {
//			dataModel = new MandatoryGrantDetailArchiveDatamodel();
	}

	/**
	 * Insert MandatoryGrantDetailArchive into database
 	 * @author TechFinium 
 	 * @see    MandatoryGrantDetailArchive
 	 */
	public void mandatorygrantdetailarchiveInsert() {
		try {
				 service.create(this.mandatorygrantdetailarchive);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 mandatorygrantdetailarchiveInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update MandatoryGrantDetailArchive in database
 	 * @author TechFinium 
 	 * @see    MandatoryGrantDetailArchive
 	 */
    public void mandatorygrantdetailarchiveUpdate() {
		try {
				 service.update(this.mandatorygrantdetailarchive);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 mandatorygrantdetailarchiveInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete MandatoryGrantDetailArchive from database
 	 * @author TechFinium 
 	 * @see    MandatoryGrantDetailArchive
 	 */
	public void mandatorygrantdetailarchiveDelete() {
		try {
			 service.delete(this.mandatorygrantdetailarchive);
			  prepareNew();
			 mandatorygrantdetailarchiveInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of MandatoryGrantDetailArchive 
 	 * @author TechFinium 
 	 * @see    MandatoryGrantDetailArchive
 	 */
	public void prepareNew() {
		mandatorygrantdetailarchive = new MandatoryGrantDetailArchive();
	}

/*
    public List<SelectItem> getMandatoryGrantDetailArchiveDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	mandatorygrantdetailarchiveInfo();
    	for (MandatoryGrantDetailArchive ug : mandatorygrantdetailarchiveList) {
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
    public List<MandatoryGrantDetailArchive> complete(String desc) {
		List<MandatoryGrantDetailArchive> l = null;
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
    
    public List<MandatoryGrantDetailArchive> getMandatoryGrantDetailArchiveList() {
		return mandatorygrantdetailarchiveList;
	}
	public void setMandatoryGrantDetailArchiveList(List<MandatoryGrantDetailArchive> mandatorygrantdetailarchiveList) {
		this.mandatorygrantdetailarchiveList = mandatorygrantdetailarchiveList;
	}
	public MandatoryGrantDetailArchive getMandatorygrantdetailarchive() {
		return mandatorygrantdetailarchive;
	}
	public void setMandatorygrantdetailarchive(MandatoryGrantDetailArchive mandatorygrantdetailarchive) {
		this.mandatorygrantdetailarchive = mandatorygrantdetailarchive;
	}

    public List<MandatoryGrantDetailArchive> getMandatoryGrantDetailArchivefilteredList() {
		return mandatorygrantdetailarchivefilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param mandatorygrantdetailarchivefilteredList the new mandatorygrantdetailarchivefilteredList list
 	 * @see    MandatoryGrantDetailArchive
 	 */	
	public void setMandatoryGrantDetailArchivefilteredList(List<MandatoryGrantDetailArchive> mandatorygrantdetailarchivefilteredList) {
		this.mandatorygrantdetailarchivefilteredList = mandatorygrantdetailarchivefilteredList;
	}

	
	public LazyDataModel<MandatoryGrantDetailArchive> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<MandatoryGrantDetailArchive> dataModel) {
		this.dataModel = dataModel;
	}
	
}
