package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.DgVerificationRejectionInformation;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.DgVerificationRejectionInformationService;

@ManagedBean(name = "dgverificationrejectioninformationUI")
@ViewScoped
public class DgVerificationRejectionInformationUI extends AbstractUI {

	private DgVerificationRejectionInformationService service = new DgVerificationRejectionInformationService();
	private List<DgVerificationRejectionInformation> dgverificationrejectioninformationList = null;
	private List<DgVerificationRejectionInformation> dgverificationrejectioninformationfilteredList = null;
	private DgVerificationRejectionInformation dgverificationrejectioninformation = null;
	private LazyDataModel<DgVerificationRejectionInformation> dataModel; 

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
	 * Initialize method to get all DgVerificationRejectionInformation and prepare a for a create of a new DgVerificationRejectionInformation
 	 * @author TechFinium 
 	 * @see    DgVerificationRejectionInformation
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		dgverificationrejectioninformationInfo();
	}

	/**
	 * Get all DgVerificationRejectionInformation for data table
 	 * @author TechFinium 
 	 * @see    DgVerificationRejectionInformation
 	 */
	public void dgverificationrejectioninformationInfo() {
//			dataModel = new DgVerificationRejectionInformationDatamodel();
	}

	/**
	 * Insert DgVerificationRejectionInformation into database
 	 * @author TechFinium 
 	 * @see    DgVerificationRejectionInformation
 	 */
	public void dgverificationrejectioninformationInsert() {
		try {
				 service.create(this.dgverificationrejectioninformation);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 dgverificationrejectioninformationInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update DgVerificationRejectionInformation in database
 	 * @author TechFinium 
 	 * @see    DgVerificationRejectionInformation
 	 */
    public void dgverificationrejectioninformationUpdate() {
		try {
				 service.update(this.dgverificationrejectioninformation);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 dgverificationrejectioninformationInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete DgVerificationRejectionInformation from database
 	 * @author TechFinium 
 	 * @see    DgVerificationRejectionInformation
 	 */
	public void dgverificationrejectioninformationDelete() {
		try {
			 service.delete(this.dgverificationrejectioninformation);
			  prepareNew();
			 dgverificationrejectioninformationInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of DgVerificationRejectionInformation 
 	 * @author TechFinium 
 	 * @see    DgVerificationRejectionInformation
 	 */
	public void prepareNew() {
		dgverificationrejectioninformation = new DgVerificationRejectionInformation();
	}

/*
    public List<SelectItem> getDgVerificationRejectionInformationDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	dgverificationrejectioninformationInfo();
    	for (DgVerificationRejectionInformation ug : dgverificationrejectioninformationList) {
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
    public List<DgVerificationRejectionInformation> complete(String desc) {
		List<DgVerificationRejectionInformation> l = null;
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
    
    public List<DgVerificationRejectionInformation> getDgVerificationRejectionInformationList() {
		return dgverificationrejectioninformationList;
	}
	public void setDgVerificationRejectionInformationList(List<DgVerificationRejectionInformation> dgverificationrejectioninformationList) {
		this.dgverificationrejectioninformationList = dgverificationrejectioninformationList;
	}
	public DgVerificationRejectionInformation getDgverificationrejectioninformation() {
		return dgverificationrejectioninformation;
	}
	public void setDgverificationrejectioninformation(DgVerificationRejectionInformation dgverificationrejectioninformation) {
		this.dgverificationrejectioninformation = dgverificationrejectioninformation;
	}

    public List<DgVerificationRejectionInformation> getDgVerificationRejectionInformationfilteredList() {
		return dgverificationrejectioninformationfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param dgverificationrejectioninformationfilteredList the new dgverificationrejectioninformationfilteredList list
 	 * @see    DgVerificationRejectionInformation
 	 */	
	public void setDgVerificationRejectionInformationfilteredList(List<DgVerificationRejectionInformation> dgverificationrejectioninformationfilteredList) {
		this.dgverificationrejectioninformationfilteredList = dgverificationrejectioninformationfilteredList;
	}

	
	public LazyDataModel<DgVerificationRejectionInformation> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<DgVerificationRejectionInformation> dataModel) {
		this.dataModel = dataModel;
	}
	
}
