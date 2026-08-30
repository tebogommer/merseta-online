package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.HostingCompany;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.HostingCompanyService;

// TODO: Auto-generated Javadoc
/**
 * The Class HostingCompanyUI.
 */
@ManagedBean(name = "hostingcompanyUI")
@ViewScoped
public class HostingCompanyUI extends AbstractUI {

	/** The service. */
	private HostingCompanyService service = new HostingCompanyService();
	
	/** The hostingcompany list. */
	private List<HostingCompany> hostingcompanyList = null;
	
	/** The hostingcompanyfiltered list. */
	private List<HostingCompany> hostingcompanyfilteredList = null;
	
	/** The hostingcompany. */
	private HostingCompany hostingcompany = null;
	
	/** The data model. */
	private LazyDataModel<HostingCompany> dataModel; 

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
	 * Initialize method to get all HostingCompany and prepare a for a create of a new HostingCompany.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    HostingCompany
	 */
	private void runInit() throws Exception {
		prepareNew();
		hostingcompanyInfo();
	}

	/**
	 * Get all HostingCompany for data table.
	 *
	 * @author TechFinium
	 * @see    HostingCompany
	 */
	public void hostingcompanyInfo() {
	 
			
			 dataModel = new LazyDataModel<HostingCompany>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<HostingCompany> retorno = new  ArrayList<HostingCompany>();
			   
			   @Override 
			   public List<HostingCompany> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allHostingCompany(HostingCompany.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(HostingCompany.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(HostingCompany obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public HostingCompany getRowData(String rowKey) {
			        for(HostingCompany obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert HostingCompany into database.
	 *
	 * @author TechFinium
	 * @see    HostingCompany
	 */
	public void hostingcompanyInsert() {
		try {
				 service.create(this.hostingcompany);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 hostingcompanyInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update HostingCompany in database.
	 *
	 * @author TechFinium
	 * @see    HostingCompany
	 */
    public void hostingcompanyUpdate() {
		try {
				 service.update(this.hostingcompany);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 hostingcompanyInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete HostingCompany from database.
	 *
	 * @author TechFinium
	 * @see    HostingCompany
	 */
	public void hostingcompanyDelete() {
		try {
			 service.delete(this.hostingcompany);
			  prepareNew();
			 hostingcompanyInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of HostingCompany .
	 *
	 * @author TechFinium
	 * @see    HostingCompany
	 */
	public void prepareNew() {
		hostingcompany = new HostingCompany();
	}

/*
    public List<SelectItem> getHostingCompanyDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	hostingcompanyInfo();
    	for (HostingCompany ug : hostingcompanyList) {
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
    public List<HostingCompany> complete(String desc) {
		List<HostingCompany> l = null;
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
     * Gets the hosting company list.
     *
     * @return the hosting company list
     */
    public List<HostingCompany> getHostingCompanyList() {
		return hostingcompanyList;
	}
	
	/**
	 * Sets the hosting company list.
	 *
	 * @param hostingcompanyList the new hosting company list
	 */
	public void setHostingCompanyList(List<HostingCompany> hostingcompanyList) {
		this.hostingcompanyList = hostingcompanyList;
	}
	
	/**
	 * Gets the hostingcompany.
	 *
	 * @return the hostingcompany
	 */
	public HostingCompany getHostingcompany() {
		return hostingcompany;
	}
	
	/**
	 * Sets the hostingcompany.
	 *
	 * @param hostingcompany the new hostingcompany
	 */
	public void setHostingcompany(HostingCompany hostingcompany) {
		this.hostingcompany = hostingcompany;
	}

    /**
     * Gets the hosting companyfiltered list.
     *
     * @return the hosting companyfiltered list
     */
    public List<HostingCompany> getHostingCompanyfilteredList() {
		return hostingcompanyfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param hostingcompanyfilteredList the new hostingcompanyfilteredList list
	 * @see    HostingCompany
	 */	
	public void setHostingCompanyfilteredList(List<HostingCompany> hostingcompanyfilteredList) {
		this.hostingcompanyfilteredList = hostingcompanyfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<HostingCompany> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<HostingCompany> dataModel) {
		this.dataModel = dataModel;
	}
	
}
