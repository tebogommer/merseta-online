package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.ApplicantType;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.ApplicantTypeService;

// TODO: Auto-generated Javadoc
/**
 * The Class ApplicantTypeUI.
 */
@ManagedBean(name = "applicantTypeUI")
@ViewScoped
public class ApplicantTypeUI extends AbstractUI {

	/** The service. */
	private ApplicantTypeService service = new ApplicantTypeService();
	
	/** The ApplicantType list. */
	private List<ApplicantType> applicantTypeList = null;
	
	/** The ApplicantTypefiltered list. */
	private List<ApplicantType> applicantTypefilteredList = null;
	
	/** The ApplicantType. */
	private ApplicantType applicantType = null;
	
	/** The data model. */
	private LazyDataModel<ApplicantType> dataModel; 

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
	 * Initialize method to get all ApplicantType and prepare a for a create of a new ApplicantType.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    ApplicantType
	 */
	private void runInit() throws Exception {
		prepareNew();
		ApplicantTypeInfo();
	}

	/**
	 * Get all ApplicantType for data table.
	 *
	 * @author TechFinium
	 * @see    ApplicantType
	 */
	public void ApplicantTypeInfo() {
	 
			
			 dataModel = new LazyDataModel<ApplicantType>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<ApplicantType> retorno = new  ArrayList<ApplicantType>();
			   
			   @Override 
			   public List<ApplicantType> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allApplicantType(ApplicantType.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(ApplicantType.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(ApplicantType obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public ApplicantType getRowData(String rowKey) {
			        for(ApplicantType obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert ApplicantType into database.
	 *
	 * @author TechFinium
	 * @see    ApplicantType
	 */
	public void applicantTypeInsert() {
		try {
				 service.create(this.applicantType);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 ApplicantTypeInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update ApplicantType in database.
	 *
	 * @author TechFinium
	 * @see    ApplicantType
	 */
    public void ApplicantTypeUpdate() {
		try {
				 service.update(this.applicantType);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 ApplicantTypeInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete ApplicantType from database.
	 *
	 * @author TechFinium
	 * @see    ApplicantType
	 */
	public void applicantTypeDelete() {
		try {
			 service.delete(this.applicantType);
			  prepareNew();
			 ApplicantTypeInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted "));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of ApplicantType .
	 *
	 * @author TechFinium
	 * @see    ApplicantType
	 */
	public void prepareNew() {
		applicantType = new ApplicantType();
	}

/*
    public List<SelectItem> getApplicantTypeDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	ApplicantTypeInfo();
    	for (ApplicantType ug : ApplicantTypeList) {
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
    public List<ApplicantType> complete(String desc) {
		List<ApplicantType> l = null;
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
     * Gets the ApplicantType list.
     *
     * @return the ApplicantType list
     */
    public List<ApplicantType> getApplicantTypeList() {
		return applicantTypeList;
	}
	
	/**
	 * Sets the ApplicantType list.
	 *
	 * @param ApplicantTypeList the new ApplicantType list
	 */
	public void setApplicantTypeList(List<ApplicantType> ApplicantTypeList) {
		this.applicantTypeList = ApplicantTypeList;
	}
	
	/**
	 * Gets the ApplicantType.
	 *
	 * @return the ApplicantType
	 */
	public ApplicantType getApplicantType() {
		return applicantType;
	}
	
	/**
	 * Sets the ApplicantType.
	 *
	 * @param ApplicantType the new ApplicantType
	 */
	public void setApplicantType(ApplicantType ApplicantType) {
		this.applicantType = ApplicantType;
	}

    /**
     * Gets the ApplicantTypefiltered list.
     *
     * @return the ApplicantTypefiltered list
     */
    public List<ApplicantType> getApplicantTypefilteredList() {
		return applicantTypefilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param ApplicantTypefilteredList the new ApplicantTypefilteredList list
	 * @see    ApplicantType
	 */	
	public void setApplicantTypefilteredList(List<ApplicantType> ApplicantTypefilteredList) {
		this.applicantTypefilteredList = ApplicantTypefilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<ApplicantType> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<ApplicantType> dataModel) {
		this.dataModel = dataModel;
	}
	
}
