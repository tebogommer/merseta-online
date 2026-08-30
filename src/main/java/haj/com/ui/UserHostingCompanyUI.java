package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.UserHostingCompany;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.UserHostingCompanyService;

// TODO: Auto-generated Javadoc
/**
 * The Class UserHostingCompanyUI.
 */
@ManagedBean(name = "userhostingcompanyUI")
@ViewScoped
public class UserHostingCompanyUI extends AbstractUI {

	/** The service. */
	private UserHostingCompanyService service = new UserHostingCompanyService();
	
	/** The userhostingcompany list. */
	private List<UserHostingCompany> userhostingcompanyList = null;
	
	/** The userhostingcompanyfiltered list. */
	private List<UserHostingCompany> userhostingcompanyfilteredList = null;
	
	/** The userhostingcompany. */
	private UserHostingCompany userhostingcompany = null;
	
	/** The data model. */
	private LazyDataModel<UserHostingCompany> dataModel; 

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
	 * Initialize method to get all UserHostingCompany and prepare a for a create of a new UserHostingCompany.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    UserHostingCompany
	 */
	private void runInit() throws Exception {
		prepareNew();
		userhostingcompanyInfo();
	}

	/**
	 * Get all UserHostingCompany for data table.
	 *
	 * @author TechFinium
	 * @see    UserHostingCompany
	 */
	public void userhostingcompanyInfo() {
	 
			
			 dataModel = new LazyDataModel<UserHostingCompany>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<UserHostingCompany> retorno = new  ArrayList<UserHostingCompany>();
			   
			   @Override 
			   public List<UserHostingCompany> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allUserHostingCompany(UserHostingCompany.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(UserHostingCompany.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(UserHostingCompany obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public UserHostingCompany getRowData(String rowKey) {
			        for(UserHostingCompany obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert UserHostingCompany into database.
	 *
	 * @author TechFinium
	 * @see    UserHostingCompany
	 */
	public void userhostingcompanyInsert() {
		try {
				 service.create(this.userhostingcompany);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 userhostingcompanyInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update UserHostingCompany in database.
	 *
	 * @author TechFinium
	 * @see    UserHostingCompany
	 */
    public void userhostingcompanyUpdate() {
		try {
				 service.update(this.userhostingcompany);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 userhostingcompanyInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete UserHostingCompany from database.
	 *
	 * @author TechFinium
	 * @see    UserHostingCompany
	 */
	public void userhostingcompanyDelete() {
		try {
			 service.delete(this.userhostingcompany);
			  prepareNew();
			 userhostingcompanyInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of UserHostingCompany .
	 *
	 * @author TechFinium
	 * @see    UserHostingCompany
	 */
	public void prepareNew() {
		userhostingcompany = new UserHostingCompany();
	}

/*
    public List<SelectItem> getUserHostingCompanyDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	userhostingcompanyInfo();
    	for (UserHostingCompany ug : userhostingcompanyList) {
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
    public List<UserHostingCompany> complete(String desc) {
		List<UserHostingCompany> l = null;
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
     * Gets the user hosting company list.
     *
     * @return the user hosting company list
     */
    public List<UserHostingCompany> getUserHostingCompanyList() {
		return userhostingcompanyList;
	}
	
	/**
	 * Sets the user hosting company list.
	 *
	 * @param userhostingcompanyList the new user hosting company list
	 */
	public void setUserHostingCompanyList(List<UserHostingCompany> userhostingcompanyList) {
		this.userhostingcompanyList = userhostingcompanyList;
	}
	
	/**
	 * Gets the userhostingcompany.
	 *
	 * @return the userhostingcompany
	 */
	public UserHostingCompany getUserhostingcompany() {
		return userhostingcompany;
	}
	
	/**
	 * Sets the userhostingcompany.
	 *
	 * @param userhostingcompany the new userhostingcompany
	 */
	public void setUserhostingcompany(UserHostingCompany userhostingcompany) {
		this.userhostingcompany = userhostingcompany;
	}

    /**
     * Gets the user hosting companyfiltered list.
     *
     * @return the user hosting companyfiltered list
     */
    public List<UserHostingCompany> getUserHostingCompanyfilteredList() {
		return userhostingcompanyfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param userhostingcompanyfilteredList the new userhostingcompanyfilteredList list
	 * @see    UserHostingCompany
	 */	
	public void setUserHostingCompanyfilteredList(List<UserHostingCompany> userhostingcompanyfilteredList) {
		this.userhostingcompanyfilteredList = userhostingcompanyfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<UserHostingCompany> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<UserHostingCompany> dataModel) {
		this.dataModel = dataModel;
	}
	
}
