package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.SDPCompany;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.SDPCompanyService;

@ManagedBean(name = "sdpcompanyUI")
@ViewScoped
public class SDPCompanyUI extends AbstractUI {

	private SDPCompanyService service = new SDPCompanyService();
	private List<SDPCompany> sdpcompanyList = null;
	private List<SDPCompany> sdpcompanyfilteredList = null;
	private SDPCompany sdpcompany = null;
	private LazyDataModel<SDPCompany> dataModel; 

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
	 * Initialize method to get all SDPCompany and prepare a for a create of a new SDPCompany
 	 * @author TechFinium 
 	 * @see    SDPCompany
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		sdpcompanyInfo();
	}

	/**
	 * Get all SDPCompany for data table
 	 * @author TechFinium 
 	 * @see    SDPCompany
 	 */
	public void sdpcompanyInfo() {
//			dataModel = new SDPCompanyDatamodel();
	}

	/**
	 * Insert SDPCompany into database
 	 * @author TechFinium 
 	 * @see    SDPCompany
 	 */
	public void sdpcompanyInsert() {
		try {
				 service.create(this.sdpcompany);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 sdpcompanyInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update SDPCompany in database
 	 * @author TechFinium 
 	 * @see    SDPCompany
 	 */
    public void sdpcompanyUpdate() {
		try {
				 service.update(this.sdpcompany);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 sdpcompanyInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete SDPCompany from database
 	 * @author TechFinium 
 	 * @see    SDPCompany
 	 */
	public void sdpcompanyDelete() {
		try {
			 service.delete(this.sdpcompany);
			  prepareNew();
			 sdpcompanyInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of SDPCompany 
 	 * @author TechFinium 
 	 * @see    SDPCompany
 	 */
	public void prepareNew() {
		sdpcompany = new SDPCompany();
	}

/*
    public List<SelectItem> getSDPCompanyDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	sdpcompanyInfo();
    	for (SDPCompany ug : sdpcompanyList) {
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
    public List<SDPCompany> complete(String desc) {
		List<SDPCompany> l = null;
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
    
    public List<SDPCompany> getSDPCompanyList() {
		return sdpcompanyList;
	}
	public void setSDPCompanyList(List<SDPCompany> sdpcompanyList) {
		this.sdpcompanyList = sdpcompanyList;
	}
	public SDPCompany getSdpcompany() {
		return sdpcompany;
	}
	public void setSdpcompany(SDPCompany sdpcompany) {
		this.sdpcompany = sdpcompany;
	}

    public List<SDPCompany> getSDPCompanyfilteredList() {
		return sdpcompanyfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param sdpcompanyfilteredList the new sdpcompanyfilteredList list
 	 * @see    SDPCompany
 	 */	
	public void setSDPCompanyfilteredList(List<SDPCompany> sdpcompanyfilteredList) {
		this.sdpcompanyfilteredList = sdpcompanyfilteredList;
	}

	
	public LazyDataModel<SDPCompany> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SDPCompany> dataModel) {
		this.dataModel = dataModel;
	}
	
}
