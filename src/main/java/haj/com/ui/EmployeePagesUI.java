package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.EmployeePages;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.EmployeePagesService;

@ManagedBean(name = "employeepagesUI")
@ViewScoped
public class EmployeePagesUI extends AbstractUI {

	private EmployeePagesService service = new EmployeePagesService();
	private List<EmployeePages> employeepagesList = null;
	private List<EmployeePages> employeepagesfilteredList = null;
	private EmployeePages employeepages = null;
	private LazyDataModel<EmployeePages> dataModel; 

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
	 * Initialize method to get all EmployeePages and prepare a for a create of a new EmployeePages
 	 * @author TechFinium 
 	 * @see    EmployeePages
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		employeepagesInfo();
	}

	/**
	 * Get all EmployeePages for data table
 	 * @author TechFinium 
 	 * @see    EmployeePages
 	 */
	public void employeepagesInfo() {
	 
			
			 dataModel = new LazyDataModel<EmployeePages>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<EmployeePages> retorno = new  ArrayList<EmployeePages>();
			   
			   @Override 
			   public List<EmployeePages> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allEmployeePages(EmployeePages.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(EmployeePages.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(EmployeePages obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public EmployeePages getRowData(String rowKey) {
			        for(EmployeePages obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert EmployeePages into database
 	 * @author TechFinium 
 	 * @see    EmployeePages
 	 */
	public void employeepagesInsert() {
		try {
				 service.create(this.employeepages);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 employeepagesInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update EmployeePages in database
 	 * @author TechFinium 
 	 * @see    EmployeePages
 	 */
    public void employeepagesUpdate() {
		try {
				 service.update(this.employeepages);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 employeepagesInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete EmployeePages from database
 	 * @author TechFinium 
 	 * @see    EmployeePages
 	 */
	public void employeepagesDelete() {
		try {
			 service.delete(this.employeepages);
			  prepareNew();
			 employeepagesInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of EmployeePages 
 	 * @author TechFinium 
 	 * @see    EmployeePages
 	 */
	public void prepareNew() {
		employeepages = new EmployeePages();
	}

/*
    public List<SelectItem> getEmployeePagesDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	employeepagesInfo();
    	for (EmployeePages ug : employeepagesList) {
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
    public List<EmployeePages> complete(String desc) {
		List<EmployeePages> l = null;
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
    
    public List<EmployeePages> getEmployeePagesList() {
		return employeepagesList;
	}
	public void setEmployeePagesList(List<EmployeePages> employeepagesList) {
		this.employeepagesList = employeepagesList;
	}
	public EmployeePages getEmployeepages() {
		return employeepages;
	}
	public void setEmployeepages(EmployeePages employeepages) {
		this.employeepages = employeepages;
	}

    public List<EmployeePages> getEmployeePagesfilteredList() {
		return employeepagesfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param employeepagesfilteredList the new employeepagesfilteredList list
 	 * @see    EmployeePages
 	 */	
	public void setEmployeePagesfilteredList(List<EmployeePages> employeepagesfilteredList) {
		this.employeepagesfilteredList = employeepagesfilteredList;
	}

	
	public LazyDataModel<EmployeePages> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<EmployeePages> dataModel) {
		this.dataModel = dataModel;
	}
	
}
