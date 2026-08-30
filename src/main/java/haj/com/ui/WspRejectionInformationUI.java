package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.WspRejectionInformation;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.WspRejectionInformationService;

@ManagedBean(name = "wsprejectioninformationUI")
@ViewScoped
public class WspRejectionInformationUI extends AbstractUI {

	private WspRejectionInformationService service = new WspRejectionInformationService();
	private List<WspRejectionInformation> wsprejectioninformationList = null;
	private List<WspRejectionInformation> wsprejectioninformationfilteredList = null;
	private WspRejectionInformation wsprejectioninformation = null;
	private LazyDataModel<WspRejectionInformation> dataModel; 

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
	 * Initialize method to get all WspRejectionInformation and prepare a for a create of a new WspRejectionInformation
 	 * @author TechFinium 
 	 * @see    WspRejectionInformation
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		wsprejectioninformationInfo();
	}

	/**
	 * Get all WspRejectionInformation for data table
 	 * @author TechFinium 
 	 * @see    WspRejectionInformation
 	 */
	public void wsprejectioninformationInfo() {
//			dataModel = new WspRejectionInformationDatamodel();
	}

	/**
	 * Insert WspRejectionInformation into database
 	 * @author TechFinium 
 	 * @see    WspRejectionInformation
 	 */
	public void wsprejectioninformationInsert() {
		try {
				 service.create(this.wsprejectioninformation);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 wsprejectioninformationInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update WspRejectionInformation in database
 	 * @author TechFinium 
 	 * @see    WspRejectionInformation
 	 */
    public void wsprejectioninformationUpdate() {
		try {
				 service.update(this.wsprejectioninformation);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 wsprejectioninformationInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete WspRejectionInformation from database
 	 * @author TechFinium 
 	 * @see    WspRejectionInformation
 	 */
	public void wsprejectioninformationDelete() {
		try {
			 service.delete(this.wsprejectioninformation);
			  prepareNew();
			 wsprejectioninformationInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of WspRejectionInformation 
 	 * @author TechFinium 
 	 * @see    WspRejectionInformation
 	 */
	public void prepareNew() {
		wsprejectioninformation = new WspRejectionInformation();
	}

/*
    public List<SelectItem> getWspRejectionInformationDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	wsprejectioninformationInfo();
    	for (WspRejectionInformation ug : wsprejectioninformationList) {
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
    public List<WspRejectionInformation> complete(String desc) {
		List<WspRejectionInformation> l = null;
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
    
    public List<WspRejectionInformation> getWspRejectionInformationList() {
		return wsprejectioninformationList;
	}
	public void setWspRejectionInformationList(List<WspRejectionInformation> wsprejectioninformationList) {
		this.wsprejectioninformationList = wsprejectioninformationList;
	}
	public WspRejectionInformation getWsprejectioninformation() {
		return wsprejectioninformation;
	}
	public void setWsprejectioninformation(WspRejectionInformation wsprejectioninformation) {
		this.wsprejectioninformation = wsprejectioninformation;
	}

    public List<WspRejectionInformation> getWspRejectionInformationfilteredList() {
		return wsprejectioninformationfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param wsprejectioninformationfilteredList the new wsprejectioninformationfilteredList list
 	 * @see    WspRejectionInformation
 	 */	
	public void setWspRejectionInformationfilteredList(List<WspRejectionInformation> wsprejectioninformationfilteredList) {
		this.wsprejectioninformationfilteredList = wsprejectioninformationfilteredList;
	}

	
	public LazyDataModel<WspRejectionInformation> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<WspRejectionInformation> dataModel) {
		this.dataModel = dataModel;
	}
	
}
