package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.CompanyQualifications;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyQualificationsService;

// TODO: Auto-generated Javadoc
/**
 * The Class CompanyQualificationsUI.
 */
@ManagedBean(name = "companyqualificationsUI")
@ViewScoped
public class CompanyQualificationsUI extends AbstractUI {

	/** The service. */
	private CompanyQualificationsService service = new CompanyQualificationsService();
	
	/** The companyqualifications list. */
	private List<CompanyQualifications> companyqualificationsList = null;
	
	/** The companyqualificationsfiltered list. */
	private List<CompanyQualifications> companyqualificationsfilteredList = null;
	
	/** The companyqualifications. */
	private CompanyQualifications companyqualifications = null;
	
	/** The data model. */
	private LazyDataModel<CompanyQualifications> dataModel; 

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
	 * Initialize method to get all CompanyQualifications and prepare a for a create of a new CompanyQualifications.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    CompanyQualifications
	 */
	private void runInit() throws Exception {
		prepareNew();
		companyqualificationsInfo();
	}

	/**
	 * Get all CompanyQualifications for data table.
	 *
	 * @author TechFinium
	 * @see    CompanyQualifications
	 */
	public void companyqualificationsInfo() {
	 
			
			 dataModel = new LazyDataModel<CompanyQualifications>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<CompanyQualifications> retorno = new  ArrayList<CompanyQualifications>();
			   
			   @Override 
			   public List<CompanyQualifications> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allCompanyQualifications(CompanyQualifications.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(CompanyQualifications.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(CompanyQualifications obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public CompanyQualifications getRowData(String rowKey) {
			        for(CompanyQualifications obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert CompanyQualifications into database.
	 *
	 * @author TechFinium
	 * @see    CompanyQualifications
	 */
	public void companyqualificationsInsert() {
		try {
				 service.create(this.companyqualifications);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 companyqualificationsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update CompanyQualifications in database.
	 *
	 * @author TechFinium
	 * @see    CompanyQualifications
	 */
    public void companyqualificationsUpdate() {
		try {
				 service.update(this.companyqualifications);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 companyqualificationsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete CompanyQualifications from database.
	 *
	 * @author TechFinium
	 * @see    CompanyQualifications
	 */
	public void companyqualificationsDelete() {
		try {
			 service.delete(this.companyqualifications);
			  prepareNew();
			 companyqualificationsInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted "));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of CompanyQualifications .
	 *
	 * @author TechFinium
	 * @see    CompanyQualifications
	 */
	public void prepareNew() {
		companyqualifications = new CompanyQualifications();
	}

/*
    public List<SelectItem> getCompanyQualificationsDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	companyqualificationsInfo();
    	for (CompanyQualifications ug : companyqualificationsList) {
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
    public List<CompanyQualifications> complete(String desc) {
		List<CompanyQualifications> l = null;
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
    
    /**
     * Gets the company qualifications list.
     *
     * @return the company qualifications list
     */
    public List<CompanyQualifications> getCompanyQualificationsList() {
		return companyqualificationsList;
	}
	
	/**
	 * Sets the company qualifications list.
	 *
	 * @param companyqualificationsList the new company qualifications list
	 */
	public void setCompanyQualificationsList(List<CompanyQualifications> companyqualificationsList) {
		this.companyqualificationsList = companyqualificationsList;
	}
	
	/**
	 * Gets the companyqualifications.
	 *
	 * @return the companyqualifications
	 */
	public CompanyQualifications getCompanyqualifications() {
		return companyqualifications;
	}
	
	/**
	 * Sets the companyqualifications.
	 *
	 * @param companyqualifications the new companyqualifications
	 */
	public void setCompanyqualifications(CompanyQualifications companyqualifications) {
		this.companyqualifications = companyqualifications;
	}

    /**
     * Gets the company qualificationsfiltered list.
     *
     * @return the company qualificationsfiltered list
     */
    public List<CompanyQualifications> getCompanyQualificationsfilteredList() {
		return companyqualificationsfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param companyqualificationsfilteredList the new companyqualificationsfilteredList list
	 * @see    CompanyQualifications
	 */	
	public void setCompanyQualificationsfilteredList(List<CompanyQualifications> companyqualificationsfilteredList) {
		this.companyqualificationsfilteredList = companyqualificationsfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<CompanyQualifications> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<CompanyQualifications> dataModel) {
		this.dataModel = dataModel;
	}
	
}
