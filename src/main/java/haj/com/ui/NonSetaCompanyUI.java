package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.NonSetaCompany;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.NonSetaCompanyService;

@ManagedBean(name = "nonsetacompanyUI")
@ViewScoped
public class NonSetaCompanyUI extends AbstractUI {

	private NonSetaCompanyService service = new NonSetaCompanyService();
	private List<NonSetaCompany> nonsetacompanyList = null;
	private List<NonSetaCompany> nonsetacompanyfilteredList = null;
	private NonSetaCompany nonsetacompany = null;
	private LazyDataModel<NonSetaCompany> dataModel; 

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
	 * Initialize method to get all NonSetaCompany and prepare a for a create of a new NonSetaCompany
 	 * @author TechFinium 
 	 * @see    NonSetaCompany
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		nonsetacompanyInfo();
	}

	/**
	 * Get all NonSetaCompany for data table
 	 * @author TechFinium 
 	 * @see    NonSetaCompany
 	 */
	public void nonsetacompanyInfo() {
//			dataModel = new NonSetaCompanyDatamodel();
	}

	/**
	 * Insert NonSetaCompany into database
 	 * @author TechFinium 
 	 * @see    NonSetaCompany
 	 */
	public void nonsetacompanyInsert() {
		try {
				 service.create(this.nonsetacompany);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 nonsetacompanyInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update NonSetaCompany in database
 	 * @author TechFinium 
 	 * @see    NonSetaCompany
 	 */
    public void nonsetacompanyUpdate() {
		try {
				 service.update(this.nonsetacompany);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 nonsetacompanyInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete NonSetaCompany from database
 	 * @author TechFinium 
 	 * @see    NonSetaCompany
 	 */
	public void nonsetacompanyDelete() {
		try {
			 service.delete(this.nonsetacompany);
			  prepareNew();
			 nonsetacompanyInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of NonSetaCompany 
 	 * @author TechFinium 
 	 * @see    NonSetaCompany
 	 */
	public void prepareNew() {
		nonsetacompany = new NonSetaCompany();
	}

/*
    public List<SelectItem> getNonSetaCompanyDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	nonsetacompanyInfo();
    	for (NonSetaCompany ug : nonsetacompanyList) {
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
    public List<NonSetaCompany> complete(String desc) {
		List<NonSetaCompany> l = null;
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
    
    public List<NonSetaCompany> getNonSetaCompanyList() {
		return nonsetacompanyList;
	}
	public void setNonSetaCompanyList(List<NonSetaCompany> nonsetacompanyList) {
		this.nonsetacompanyList = nonsetacompanyList;
	}
	public NonSetaCompany getNonsetacompany() {
		return nonsetacompany;
	}
	public void setNonsetacompany(NonSetaCompany nonsetacompany) {
		this.nonsetacompany = nonsetacompany;
	}

    public List<NonSetaCompany> getNonSetaCompanyfilteredList() {
		return nonsetacompanyfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param nonsetacompanyfilteredList the new nonsetacompanyfilteredList list
 	 * @see    NonSetaCompany
 	 */	
	public void setNonSetaCompanyfilteredList(List<NonSetaCompany> nonsetacompanyfilteredList) {
		this.nonsetacompanyfilteredList = nonsetacompanyfilteredList;
	}

	
	public LazyDataModel<NonSetaCompany> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<NonSetaCompany> dataModel) {
		this.dataModel = dataModel;
	}
	
}
