package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.EmploymentType;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.EmploymentTypeService;

// TODO: Auto-generated Javadoc
/**
 * The Class EmploymentTypeUI.
 */
@ManagedBean(name = "employmenttypeUI")
@ViewScoped
public class EmploymentTypeUI extends AbstractUI {

	/** The service. */
	private EmploymentTypeService service = new EmploymentTypeService();
	
	/** The employmenttype list. */
	private List<EmploymentType> employmenttypeList = null;
	
	/** The employmenttypefiltered list. */
	private List<EmploymentType> employmenttypefilteredList = null;
	
	/** The employmenttype. */
	private EmploymentType employmenttype = null;
	
	/** The data model. */
	private LazyDataModel<EmploymentType> dataModel; 

    /**
     * Inits the.
     */
    @PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Initialize method to get all EmploymentType and prepare a for a create of a new EmploymentType.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    EmploymentType
	 */
	private void runInit() throws Exception {
		prepareNew();
		employmenttypeInfo();
	}

	/**
	 * Get all EmploymentType for data table.
	 *
	 * @author TechFinium
	 * @see    EmploymentType
	 */
	public void employmenttypeInfo() {
	 
			
			 dataModel = new LazyDataModel<EmploymentType>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<EmploymentType> retorno = new  ArrayList<EmploymentType>();
			   
			   @Override 
			   public List<EmploymentType> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allEmploymentType(EmploymentType.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(EmploymentType.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(EmploymentType obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public EmploymentType getRowData(String rowKey) {
			        for(EmploymentType obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert EmploymentType into database.
	 *
	 * @author TechFinium
	 * @see    EmploymentType
	 */
	public void employmenttypeInsert() {
		try {
				 service.create(this.employmenttype);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 employmenttypeInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update EmploymentType in database.
	 *
	 * @author TechFinium
	 * @see    EmploymentType
	 */
    public void employmenttypeUpdate() {
		try {
				 service.update(this.employmenttype);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 employmenttypeInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete EmploymentType from database.
	 *
	 * @author TechFinium
	 * @see    EmploymentType
	 */
	public void employmenttypeDelete() {
		try {
			 service.delete(this.employmenttype);
			  prepareNew();
			 employmenttypeInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted "));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of EmploymentType .
	 *
	 * @author TechFinium
	 * @see    EmploymentType
	 */
	public void prepareNew() {
		employmenttype = new EmploymentType();
	}

/*
    public List<SelectItem> getEmploymentTypeDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	employmenttypeInfo();
    	for (EmploymentType ug : employmenttypeList) {
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
    public List<EmploymentType> complete(String desc) {
		List<EmploymentType> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		}
		catch (Exception e) {
		addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
    
    /**
     * Gets the employment type list.
     *
     * @return the employment type list
     */
    public List<EmploymentType> getEmploymentTypeList() {
		return employmenttypeList;
	}
	
	/**
	 * Sets the employment type list.
	 *
	 * @param employmenttypeList the new employment type list
	 */
	public void setEmploymentTypeList(List<EmploymentType> employmenttypeList) {
		this.employmenttypeList = employmenttypeList;
	}
	
	/**
	 * Gets the employmenttype.
	 *
	 * @return the employmenttype
	 */
	public EmploymentType getEmploymenttype() {
		return employmenttype;
	}
	
	/**
	 * Sets the employmenttype.
	 *
	 * @param employmenttype the new employmenttype
	 */
	public void setEmploymenttype(EmploymentType employmenttype) {
		this.employmenttype = employmenttype;
	}

    /**
     * Gets the employment typefiltered list.
     *
     * @return the employment typefiltered list
     */
    public List<EmploymentType> getEmploymentTypefilteredList() {
		return employmenttypefilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param employmenttypefilteredList the new employmenttypefilteredList list
	 * @see    EmploymentType
	 */	
	public void setEmploymentTypefilteredList(List<EmploymentType> employmenttypefilteredList) {
		this.employmenttypefilteredList = employmenttypefilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<EmploymentType> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<EmploymentType> dataModel) {
		this.dataModel = dataModel;
	}
	
}
