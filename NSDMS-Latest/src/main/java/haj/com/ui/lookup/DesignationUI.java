package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.Designation;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.DesignationService;

// TODO: Auto-generated Javadoc
/**
 * The Class DesignationUI.
 */
@ManagedBean(name = "designationUI")
@ViewScoped
public class DesignationUI extends AbstractUI {

	/** The service. */
	private DesignationService service = new DesignationService();
	
	/** The designation list. */
	private List<Designation> designationList = null;
	
	/** The designationfiltered list. */
	private List<Designation> designationfilteredList = null;
	
	/** The designation. */
	private Designation designation = null;
	
	/** The data model. */
	private LazyDataModel<Designation> dataModel; 

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
	 * Initialize method to get all Designation and prepare a for a create of a new Designation.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    Designation
	 */
	private void runInit() throws Exception {
		prepareNew();
		designationInfo();
	}

	/**
	 * Get all Designation for data table.
	 *
	 * @author TechFinium
	 * @see    Designation
	 */
	public void designationInfo() {
	 
			
			 dataModel = new LazyDataModel<Designation>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<Designation> retorno = new  ArrayList<Designation>();
			   
			   @Override 
			   public List<Designation> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allDesignation(Designation.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(Designation.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(Designation obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public Designation getRowData(String rowKey) {
			        for(Designation obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert Designation into database.
	 *
	 * @author TechFinium
	 * @see    Designation
	 */
	public void designationInsert() {
		try {
				 service.create(this.designation);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 designationInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update Designation in database.
	 *
	 * @author TechFinium
	 * @see    Designation
	 */
    public void designationUpdate() {
		try {
				 service.update(this.designation);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 designationInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete Designation from database.
	 *
	 * @author TechFinium
	 * @see    Designation
	 */
	public void designationDelete() {
		try {
			 service.delete(this.designation);
			  prepareNew();
			 designationInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of Designation .
	 *
	 * @author TechFinium
	 * @see    Designation
	 */
	public void prepareNew() {
		designation = new Designation();
	}

/*
    public List<SelectItem> getDesignationDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	designationInfo();
    	for (Designation ug : designationList) {
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
    public List<Designation> complete(String desc) {
		List<Designation> l = null;
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
     * Gets the designation list.
     *
     * @return the designation list
     */
    public List<Designation> getDesignationList() {
		return designationList;
	}
	
	/**
	 * Sets the designation list.
	 *
	 * @param designationList the new designation list
	 */
	public void setDesignationList(List<Designation> designationList) {
		this.designationList = designationList;
	}
	
	/**
	 * Gets the designation.
	 *
	 * @return the designation
	 */
	public Designation getDesignation() {
		return designation;
	}
	
	/**
	 * Sets the designation.
	 *
	 * @param designation the new designation
	 */
	public void setDesignation(Designation designation) {
		this.designation = designation;
	}

    /**
     * Gets the designationfiltered list.
     *
     * @return the designationfiltered list
     */
    public List<Designation> getDesignationfilteredList() {
		return designationfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param designationfilteredList the new designationfilteredList list
	 * @see    Designation
	 */	
	public void setDesignationfilteredList(List<Designation> designationfilteredList) {
		this.designationfilteredList = designationfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<Designation> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<Designation> dataModel) {
		this.dataModel = dataModel;
	}
	
}
