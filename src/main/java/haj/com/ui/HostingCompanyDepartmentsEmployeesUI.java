package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.HostingCompanyDepartmentsEmployees;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.HostingCompanyDepartmentsEmployeesService;

// TODO: Auto-generated Javadoc
/**
 * The Class HostingCompanyDepartmentsEmployeesUI.
 */
@ManagedBean(name = "hostingcompanydepartmentsemployeesUI")
@ViewScoped
public class HostingCompanyDepartmentsEmployeesUI extends AbstractUI {

	/** The service. */
	private HostingCompanyDepartmentsEmployeesService service = new HostingCompanyDepartmentsEmployeesService();
	
	/** The hostingcompanydepartmentsemployees list. */
	private List<HostingCompanyDepartmentsEmployees> hostingcompanydepartmentsemployeesList = null;
	
	/** The hostingcompanydepartmentsemployeesfiltered list. */
	private List<HostingCompanyDepartmentsEmployees> hostingcompanydepartmentsemployeesfilteredList = null;
	
	/** The hostingcompanydepartmentsemployees. */
	private HostingCompanyDepartmentsEmployees hostingcompanydepartmentsemployees = null;
	
	/** The data model. */
	private LazyDataModel<HostingCompanyDepartmentsEmployees> dataModel; 

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
	 * Initialize method to get all HostingCompanyDepartmentsEmployees and prepare a for a create of a new HostingCompanyDepartmentsEmployees.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    HostingCompanyDepartmentsEmployees
	 */
	private void runInit() throws Exception {
		prepareNew();
		hostingcompanydepartmentsemployeesInfo();
	}

	/**
	 * Get all HostingCompanyDepartmentsEmployees for data table.
	 *
	 * @author TechFinium
	 * @see    HostingCompanyDepartmentsEmployees
	 */
	public void hostingcompanydepartmentsemployeesInfo() {
	 
			
			 dataModel = new LazyDataModel<HostingCompanyDepartmentsEmployees>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<HostingCompanyDepartmentsEmployees> retorno = new  ArrayList<HostingCompanyDepartmentsEmployees>();
			   
			   @Override 
			   public List<HostingCompanyDepartmentsEmployees> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allHostingCompanyDepartmentsEmployees(HostingCompanyDepartmentsEmployees.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(HostingCompanyDepartmentsEmployees.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(HostingCompanyDepartmentsEmployees obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public HostingCompanyDepartmentsEmployees getRowData(String rowKey) {
			        for(HostingCompanyDepartmentsEmployees obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert HostingCompanyDepartmentsEmployees into database.
	 *
	 * @author TechFinium
	 * @see    HostingCompanyDepartmentsEmployees
	 */
	public void hostingcompanydepartmentsemployeesInsert() {
		try {
				 service.create(this.hostingcompanydepartmentsemployees);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 hostingcompanydepartmentsemployeesInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update HostingCompanyDepartmentsEmployees in database.
	 *
	 * @author TechFinium
	 * @see    HostingCompanyDepartmentsEmployees
	 */
    public void hostingcompanydepartmentsemployeesUpdate() {
		try {
				 service.update(this.hostingcompanydepartmentsemployees);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 hostingcompanydepartmentsemployeesInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete HostingCompanyDepartmentsEmployees from database.
	 *
	 * @author TechFinium
	 * @see    HostingCompanyDepartmentsEmployees
	 */
	public void hostingcompanydepartmentsemployeesDelete() {
		try {
			 service.delete(this.hostingcompanydepartmentsemployees);
			  prepareNew();
			 hostingcompanydepartmentsemployeesInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted "));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of HostingCompanyDepartmentsEmployees .
	 *
	 * @author TechFinium
	 * @see    HostingCompanyDepartmentsEmployees
	 */
	public void prepareNew() {
		hostingcompanydepartmentsemployees = new HostingCompanyDepartmentsEmployees();
	}

/*
    public List<SelectItem> getHostingCompanyDepartmentsEmployeesDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	hostingcompanydepartmentsemployeesInfo();
    	for (HostingCompanyDepartmentsEmployees ug : hostingcompanydepartmentsemployeesList) {
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
    public List<HostingCompanyDepartmentsEmployees> complete(String desc) {
		List<HostingCompanyDepartmentsEmployees> l = null;
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
     * Gets the hosting company departments employees list.
     *
     * @return the hosting company departments employees list
     */
    public List<HostingCompanyDepartmentsEmployees> getHostingCompanyDepartmentsEmployeesList() {
		return hostingcompanydepartmentsemployeesList;
	}
	
	/**
	 * Sets the hosting company departments employees list.
	 *
	 * @param hostingcompanydepartmentsemployeesList the new hosting company departments employees list
	 */
	public void setHostingCompanyDepartmentsEmployeesList(List<HostingCompanyDepartmentsEmployees> hostingcompanydepartmentsemployeesList) {
		this.hostingcompanydepartmentsemployeesList = hostingcompanydepartmentsemployeesList;
	}
	
	/**
	 * Gets the hostingcompanydepartmentsemployees.
	 *
	 * @return the hostingcompanydepartmentsemployees
	 */
	public HostingCompanyDepartmentsEmployees getHostingcompanydepartmentsemployees() {
		return hostingcompanydepartmentsemployees;
	}
	
	/**
	 * Sets the hostingcompanydepartmentsemployees.
	 *
	 * @param hostingcompanydepartmentsemployees the new hostingcompanydepartmentsemployees
	 */
	public void setHostingcompanydepartmentsemployees(HostingCompanyDepartmentsEmployees hostingcompanydepartmentsemployees) {
		this.hostingcompanydepartmentsemployees = hostingcompanydepartmentsemployees;
	}

    /**
     * Gets the hosting company departments employeesfiltered list.
     *
     * @return the hosting company departments employeesfiltered list
     */
    public List<HostingCompanyDepartmentsEmployees> getHostingCompanyDepartmentsEmployeesfilteredList() {
		return hostingcompanydepartmentsemployeesfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param hostingcompanydepartmentsemployeesfilteredList the new hostingcompanydepartmentsemployeesfilteredList list
	 * @see    HostingCompanyDepartmentsEmployees
	 */	
	public void setHostingCompanyDepartmentsEmployeesfilteredList(List<HostingCompanyDepartmentsEmployees> hostingcompanydepartmentsemployeesfilteredList) {
		this.hostingcompanydepartmentsemployeesfilteredList = hostingcompanydepartmentsemployeesfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<HostingCompanyDepartmentsEmployees> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<HostingCompanyDepartmentsEmployees> dataModel) {
		this.dataModel = dataModel;
	}
	
}
