package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.CompanyHostingCompany;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyHostingCompanyService;

// TODO: Auto-generated Javadoc
/**
 * The Class CompanyHostingCompanyUI.
 */
@ManagedBean(name = "companyhostingcompanyUI")
@ViewScoped
public class CompanyHostingCompanyUI extends AbstractUI {

	/** The service. */
	private CompanyHostingCompanyService service = new CompanyHostingCompanyService();
	
	/** The companyhostingcompany list. */
	private List<CompanyHostingCompany> companyhostingcompanyList = null;
	
	/** The companyhostingcompanyfiltered list. */
	private List<CompanyHostingCompany> companyhostingcompanyfilteredList = null;
	
	/** The companyhostingcompany. */
	private CompanyHostingCompany companyhostingcompany = null;
	
	/** The data model. */
	private LazyDataModel<CompanyHostingCompany> dataModel; 

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
	 * Initialize method to get all CompanyHostingCompany and prepare a for a create of a new CompanyHostingCompany.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    CompanyHostingCompany
	 */
	private void runInit() throws Exception {
		prepareNew();
		companyhostingcompanyInfo();
	}

	/**
	 * Get all CompanyHostingCompany for data table.
	 *
	 * @author TechFinium
	 * @see    CompanyHostingCompany
	 */
	public void companyhostingcompanyInfo() {
	 
			
			 dataModel = new LazyDataModel<CompanyHostingCompany>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<CompanyHostingCompany> retorno = new  ArrayList<CompanyHostingCompany>();
			   
			   @Override 
			   public List<CompanyHostingCompany> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allCompanyHostingCompany(CompanyHostingCompany.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(CompanyHostingCompany.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(CompanyHostingCompany obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public CompanyHostingCompany getRowData(String rowKey) {
			        for(CompanyHostingCompany obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert CompanyHostingCompany into database.
	 *
	 * @author TechFinium
	 * @see    CompanyHostingCompany
	 */
	public void companyhostingcompanyInsert() {
		try {
				 service.create(this.companyhostingcompany);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 companyhostingcompanyInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update CompanyHostingCompany in database.
	 *
	 * @author TechFinium
	 * @see    CompanyHostingCompany
	 */
    public void companyhostingcompanyUpdate() {
		try {
				 service.update(this.companyhostingcompany);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 companyhostingcompanyInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete CompanyHostingCompany from database.
	 *
	 * @author TechFinium
	 * @see    CompanyHostingCompany
	 */
	public void companyhostingcompanyDelete() {
		try {
			 service.delete(this.companyhostingcompany);
			  prepareNew();
			 companyhostingcompanyInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of CompanyHostingCompany .
	 *
	 * @author TechFinium
	 * @see    CompanyHostingCompany
	 */
	public void prepareNew() {
		companyhostingcompany = new CompanyHostingCompany();
	}

/*
    public List<SelectItem> getCompanyHostingCompanyDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	companyhostingcompanyInfo();
    	for (CompanyHostingCompany ug : companyhostingcompanyList) {
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
    public List<CompanyHostingCompany> complete(String desc) {
		List<CompanyHostingCompany> l = null;
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
     * Gets the company hosting company list.
     *
     * @return the company hosting company list
     */
    public List<CompanyHostingCompany> getCompanyHostingCompanyList() {
		return companyhostingcompanyList;
	}
	
	/**
	 * Sets the company hosting company list.
	 *
	 * @param companyhostingcompanyList the new company hosting company list
	 */
	public void setCompanyHostingCompanyList(List<CompanyHostingCompany> companyhostingcompanyList) {
		this.companyhostingcompanyList = companyhostingcompanyList;
	}
	
	/**
	 * Gets the companyhostingcompany.
	 *
	 * @return the companyhostingcompany
	 */
	public CompanyHostingCompany getCompanyhostingcompany() {
		return companyhostingcompany;
	}
	
	/**
	 * Sets the companyhostingcompany.
	 *
	 * @param companyhostingcompany the new companyhostingcompany
	 */
	public void setCompanyhostingcompany(CompanyHostingCompany companyhostingcompany) {
		this.companyhostingcompany = companyhostingcompany;
	}

    /**
     * Gets the company hosting companyfiltered list.
     *
     * @return the company hosting companyfiltered list
     */
    public List<CompanyHostingCompany> getCompanyHostingCompanyfilteredList() {
		return companyhostingcompanyfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param companyhostingcompanyfilteredList the new companyhostingcompanyfilteredList list
	 * @see    CompanyHostingCompany
	 */	
	public void setCompanyHostingCompanyfilteredList(List<CompanyHostingCompany> companyhostingcompanyfilteredList) {
		this.companyhostingcompanyfilteredList = companyhostingcompanyfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<CompanyHostingCompany> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<CompanyHostingCompany> dataModel) {
		this.dataModel = dataModel;
	}
	
}
