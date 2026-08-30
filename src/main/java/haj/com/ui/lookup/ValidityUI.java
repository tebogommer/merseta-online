package  haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.lookup.Validity;
import haj.com.service.lookup.ValidityService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "validityUI")
@ViewScoped
public class ValidityUI extends AbstractUI {

	private ValidityService service = new ValidityService();
	private List<Validity> validityList = null;
	private List<Validity> validityfilteredList = null;
	private Validity validity = null;
	private LazyDataModel<Validity> dataModel; 

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
	 * Initialize method to get all Validity and prepare a for a create of a new Validity
 	 * @author TechFinium 
 	 * @see    Validity
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		validityInfo();
	}

	/**
	 * Get all Validity for data table
 	 * @author TechFinium 
 	 * @see    Validity
 	 */
	public void validityInfo() {
	 
			
			 dataModel = new LazyDataModel<Validity>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<Validity> retorno = new  ArrayList<Validity>();
			   
			   @Override 
			   public List<Validity> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allValidity(Validity.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(Validity.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(Validity obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public Validity getRowData(String rowKey) {
			        for(Validity obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert Validity into database
 	 * @author TechFinium 
 	 * @see    Validity
 	 */
	public void validityInsert() {
		try {
				 service.create(this.validity);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 validityInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update Validity in database
 	 * @author TechFinium 
 	 * @see    Validity
 	 */
    public void validityUpdate() {
		try {
				 service.update(this.validity);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 validityInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete Validity from database
 	 * @author TechFinium 
 	 * @see    Validity
 	 */
	public void validityDelete() {
		try {
			 service.delete(this.validity);
			  prepareNew();
			 validityInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of Validity 
 	 * @author TechFinium 
 	 * @see    Validity
 	 */
	public void prepareNew() {
		validity = new Validity();
	}

/*
    public List<SelectItem> getValidityDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	validityInfo();
    	for (Validity ug : validityList) {
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
    public List<Validity> complete(String desc) {
		List<Validity> l = null;
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
    
    public List<Validity> getValidityList() {
		return validityList;
	}
	public void setValidityList(List<Validity> validityList) {
		this.validityList = validityList;
	}
	public Validity getValidity() {
		return validity;
	}
	public void setValidity(Validity validity) {
		this.validity = validity;
	}

    public List<Validity> getValidityfilteredList() {
		return validityfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param validityfilteredList the new validityfilteredList list
 	 * @see    Validity
 	 */	
	public void setValidityfilteredList(List<Validity> validityfilteredList) {
		this.validityfilteredList = validityfilteredList;
	}

	
	public LazyDataModel<Validity> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<Validity> dataModel) {
		this.dataModel = dataModel;
	}
	
}
