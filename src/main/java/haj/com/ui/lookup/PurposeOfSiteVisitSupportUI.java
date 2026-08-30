package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.PurposeOfSiteVisitSupport;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.PurposeOfSiteVisitSupportService;

@ManagedBean(name = "purposeofsitevisitsupportUI")
@ViewScoped
public class PurposeOfSiteVisitSupportUI extends AbstractUI {

	private PurposeOfSiteVisitSupportService service = new PurposeOfSiteVisitSupportService();
	private List<PurposeOfSiteVisitSupport> purposeofsitevisitsupportList = null;
	private List<PurposeOfSiteVisitSupport> purposeofsitevisitsupportfilteredList = null;
	private PurposeOfSiteVisitSupport purposeofsitevisitsupport = null;
	private LazyDataModel<PurposeOfSiteVisitSupport> dataModel; 

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
	 * Initialize method to get all PurposeOfSiteVisitSupport and prepare a for a create of a new PurposeOfSiteVisitSupport
 	 * @author TechFinium 
 	 * @see    PurposeOfSiteVisitSupport
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		purposeofsitevisitsupportInfo();
	}

	/**
	 * Get all PurposeOfSiteVisitSupport for data table
 	 * @author TechFinium 
 	 * @see    PurposeOfSiteVisitSupport
 	 */
	public void purposeofsitevisitsupportInfo() {
	 
			
			 dataModel = new LazyDataModel<PurposeOfSiteVisitSupport>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<PurposeOfSiteVisitSupport> retorno = new  ArrayList<PurposeOfSiteVisitSupport>();
			   
			   @Override 
			   public List<PurposeOfSiteVisitSupport> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allPurposeOfSiteVisitSupport(PurposeOfSiteVisitSupport.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(PurposeOfSiteVisitSupport.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(PurposeOfSiteVisitSupport obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public PurposeOfSiteVisitSupport getRowData(String rowKey) {
			        for(PurposeOfSiteVisitSupport obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert PurposeOfSiteVisitSupport into database
 	 * @author TechFinium 
 	 * @see    PurposeOfSiteVisitSupport
 	 */
	public void purposeofsitevisitsupportInsert() {
		try {
				 service.create(this.purposeofsitevisitsupport);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 purposeofsitevisitsupportInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update PurposeOfSiteVisitSupport in database
 	 * @author TechFinium 
 	 * @see    PurposeOfSiteVisitSupport
 	 */
    public void purposeofsitevisitsupportUpdate() {
		try {
				 service.update(this.purposeofsitevisitsupport);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 purposeofsitevisitsupportInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete PurposeOfSiteVisitSupport from database
 	 * @author TechFinium 
 	 * @see    PurposeOfSiteVisitSupport
 	 */
	public void purposeofsitevisitsupportDelete() {
		try {
			 service.delete(this.purposeofsitevisitsupport);
			  prepareNew();
			 purposeofsitevisitsupportInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of PurposeOfSiteVisitSupport 
 	 * @author TechFinium 
 	 * @see    PurposeOfSiteVisitSupport
 	 */
	public void prepareNew() {
		purposeofsitevisitsupport = new PurposeOfSiteVisitSupport();
	}

/*
    public List<SelectItem> getPurposeOfSiteVisitSupportDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	purposeofsitevisitsupportInfo();
    	for (PurposeOfSiteVisitSupport ug : purposeofsitevisitsupportList) {
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
    public List<PurposeOfSiteVisitSupport> complete(String desc) {
		List<PurposeOfSiteVisitSupport> l = null;
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
    
    public List<PurposeOfSiteVisitSupport> getPurposeOfSiteVisitSupportList() {
		return purposeofsitevisitsupportList;
	}
	public void setPurposeOfSiteVisitSupportList(List<PurposeOfSiteVisitSupport> purposeofsitevisitsupportList) {
		this.purposeofsitevisitsupportList = purposeofsitevisitsupportList;
	}
	public PurposeOfSiteVisitSupport getPurposeofsitevisitsupport() {
		return purposeofsitevisitsupport;
	}
	public void setPurposeofsitevisitsupport(PurposeOfSiteVisitSupport purposeofsitevisitsupport) {
		this.purposeofsitevisitsupport = purposeofsitevisitsupport;
	}

    public List<PurposeOfSiteVisitSupport> getPurposeOfSiteVisitSupportfilteredList() {
		return purposeofsitevisitsupportfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param purposeofsitevisitsupportfilteredList the new purposeofsitevisitsupportfilteredList list
 	 * @see    PurposeOfSiteVisitSupport
 	 */	
	public void setPurposeOfSiteVisitSupportfilteredList(List<PurposeOfSiteVisitSupport> purposeofsitevisitsupportfilteredList) {
		this.purposeofsitevisitsupportfilteredList = purposeofsitevisitsupportfilteredList;
	}

	
	public LazyDataModel<PurposeOfSiteVisitSupport> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<PurposeOfSiteVisitSupport> dataModel) {
		this.dataModel = dataModel;
	}
	
}
