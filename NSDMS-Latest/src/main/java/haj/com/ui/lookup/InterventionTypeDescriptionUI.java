package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.InterventionTypeDescription;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.InterventionTypeDescriptionService;

@ManagedBean(name = "interventiontypedescriptionUI")
@ViewScoped
public class InterventionTypeDescriptionUI extends AbstractUI {

	private InterventionTypeDescriptionService service = new InterventionTypeDescriptionService();
	private List<InterventionTypeDescription> interventiontypedescriptionList = null;
	private List<InterventionTypeDescription> interventiontypedescriptionfilteredList = null;
	private InterventionTypeDescription interventiontypedescription = null;
	private LazyDataModel<InterventionTypeDescription> dataModel; 

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
	 * Initialize method to get all InterventionTypeDescription and prepare a for a create of a new InterventionTypeDescription
 	 * @author TechFinium 
 	 * @see    InterventionTypeDescription
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		interventiontypedescriptionInfo();
	}

	/**
	 * Get all InterventionTypeDescription for data table
 	 * @author TechFinium 
 	 * @see    InterventionTypeDescription
 	 */
	public void interventiontypedescriptionInfo() {
	 
			
			 dataModel = new LazyDataModel<InterventionTypeDescription>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<InterventionTypeDescription> retorno = new  ArrayList<InterventionTypeDescription>();
			   
			   @Override 
			   public List<InterventionTypeDescription> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allInterventionTypeDescription(InterventionTypeDescription.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(InterventionTypeDescription.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(InterventionTypeDescription obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public InterventionTypeDescription getRowData(String rowKey) {
			        for(InterventionTypeDescription obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert InterventionTypeDescription into database
 	 * @author TechFinium 
 	 * @see    InterventionTypeDescription
 	 */
	public void interventiontypedescriptionInsert() {
		try {
				 service.create(this.interventiontypedescription);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 interventiontypedescriptionInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update InterventionTypeDescription in database
 	 * @author TechFinium 
 	 * @see    InterventionTypeDescription
 	 */
    public void interventiontypedescriptionUpdate() {
		try {
				 service.update(this.interventiontypedescription);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 interventiontypedescriptionInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete InterventionTypeDescription from database
 	 * @author TechFinium 
 	 * @see    InterventionTypeDescription
 	 */
	public void interventiontypedescriptionDelete() {
		try {
			 service.delete(this.interventiontypedescription);
			  prepareNew();
			 interventiontypedescriptionInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of InterventionTypeDescription 
 	 * @author TechFinium 
 	 * @see    InterventionTypeDescription
 	 */
	public void prepareNew() {
		interventiontypedescription = new InterventionTypeDescription();
	}

/*
    public List<SelectItem> getInterventionTypeDescriptionDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	interventiontypedescriptionInfo();
    	for (InterventionTypeDescription ug : interventiontypedescriptionList) {
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
    public List<InterventionTypeDescription> complete(String desc) {
		List<InterventionTypeDescription> l = null;
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
    
    public List<InterventionTypeDescription> getInterventionTypeDescriptionList() {
		return interventiontypedescriptionList;
	}
	public void setInterventionTypeDescriptionList(List<InterventionTypeDescription> interventiontypedescriptionList) {
		this.interventiontypedescriptionList = interventiontypedescriptionList;
	}
	public InterventionTypeDescription getInterventiontypedescription() {
		return interventiontypedescription;
	}
	public void setInterventiontypedescription(InterventionTypeDescription interventiontypedescription) {
		this.interventiontypedescription = interventiontypedescription;
	}

    public List<InterventionTypeDescription> getInterventionTypeDescriptionfilteredList() {
		return interventiontypedescriptionfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param interventiontypedescriptionfilteredList the new interventiontypedescriptionfilteredList list
 	 * @see    InterventionTypeDescription
 	 */	
	public void setInterventionTypeDescriptionfilteredList(List<InterventionTypeDescription> interventiontypedescriptionfilteredList) {
		this.interventiontypedescriptionfilteredList = interventiontypedescriptionfilteredList;
	}

	
	public LazyDataModel<InterventionTypeDescription> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<InterventionTypeDescription> dataModel) {
		this.dataModel = dataModel;
	}
	
}
