package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.OrganisationType;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.OrganisationTypeService;

@ManagedBean(name = "organisationtypeUI")
@ViewScoped
public class OrganisationTypeUI extends AbstractUI {

	private OrganisationTypeService service = new OrganisationTypeService();
	private List<OrganisationType> organisationtypeList = null;
	private List<OrganisationType> organisationtypefilteredList = null;
	private OrganisationType organisationtype = null;
	private LazyDataModel<OrganisationType> dataModel; 

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
	 * Initialize method to get all OrganisationType and prepare a for a create of a new OrganisationType
 	 * @author TechFinium 
 	 * @see    OrganisationType
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		organisationtypeInfo();
	}

	/**
	 * Get all OrganisationType for data table
 	 * @author TechFinium 
 	 * @see    OrganisationType
 	 */
	public void organisationtypeInfo() {
	 
			
			 dataModel = new LazyDataModel<OrganisationType>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<OrganisationType> retorno = new  ArrayList<OrganisationType>();
			   
			   @Override 
			   public List<OrganisationType> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allOrganisationType(OrganisationType.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(OrganisationType.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(OrganisationType obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public OrganisationType getRowData(String rowKey) {
			        for(OrganisationType obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert OrganisationType into database
 	 * @author TechFinium 
 	 * @see    OrganisationType
 	 */
	public void organisationtypeInsert() {
		try {
				 service.create(this.organisationtype);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 organisationtypeInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update OrganisationType in database
 	 * @author TechFinium 
 	 * @see    OrganisationType
 	 */
    public void organisationtypeUpdate() {
		try {
				 service.update(this.organisationtype);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 organisationtypeInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete OrganisationType from database
 	 * @author TechFinium 
 	 * @see    OrganisationType
 	 */
	public void organisationtypeDelete() {
		try {
			 service.delete(this.organisationtype);
			  prepareNew();
			 organisationtypeInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of OrganisationType 
 	 * @author TechFinium 
 	 * @see    OrganisationType
 	 */
	public void prepareNew() {
		organisationtype = new OrganisationType();
	}

/*
    public List<SelectItem> getOrganisationTypeDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	organisationtypeInfo();
    	for (OrganisationType ug : organisationtypeList) {
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
    public List<OrganisationType> complete(String desc) {
		List<OrganisationType> l = null;
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
    
    public List<OrganisationType> getOrganisationTypeList() {
		return organisationtypeList;
	}
	public void setOrganisationTypeList(List<OrganisationType> organisationtypeList) {
		this.organisationtypeList = organisationtypeList;
	}
	public OrganisationType getOrganisationtype() {
		return organisationtype;
	}
	public void setOrganisationtype(OrganisationType organisationtype) {
		this.organisationtype = organisationtype;
	}

    public List<OrganisationType> getOrganisationTypefilteredList() {
		return organisationtypefilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param organisationtypefilteredList the new organisationtypefilteredList list
 	 * @see    OrganisationType
 	 */	
	public void setOrganisationTypefilteredList(List<OrganisationType> organisationtypefilteredList) {
		this.organisationtypefilteredList = organisationtypefilteredList;
	}

	
	public LazyDataModel<OrganisationType> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<OrganisationType> dataModel) {
		this.dataModel = dataModel;
	}
	
}
