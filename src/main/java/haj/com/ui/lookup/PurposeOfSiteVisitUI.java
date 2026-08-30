package  haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.lookup.PurposeOfSiteVisit;
import haj.com.service.lookup.PurposeOfSiteVisitService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "purposeofsitevisitUI")
@ViewScoped
public class PurposeOfSiteVisitUI extends AbstractUI {

	private PurposeOfSiteVisitService service = new PurposeOfSiteVisitService();
	private List<PurposeOfSiteVisit> purposeofsitevisitList = null;
	private List<PurposeOfSiteVisit> purposeofsitevisitfilteredList = null;
	private PurposeOfSiteVisit purposeofsitevisit = null;
	private LazyDataModel<PurposeOfSiteVisit> dataModel; 

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
	 * Initialize method to get all PurposeOfSiteVisit and prepare a for a create of a new PurposeOfSiteVisit
 	 * @author TechFinium 
 	 * @see    PurposeOfSiteVisit
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		purposeofsitevisitInfo();
	}

	/**
	 * Get all PurposeOfSiteVisit for data table
 	 * @author TechFinium 
 	 * @see    PurposeOfSiteVisit
 	 */
	public void purposeofsitevisitInfo() {
	 
			
			 dataModel = new LazyDataModel<PurposeOfSiteVisit>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<PurposeOfSiteVisit> retorno = new  ArrayList<PurposeOfSiteVisit>();
			   
			   @Override 
			   public List<PurposeOfSiteVisit> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allPurposeOfSiteVisit(PurposeOfSiteVisit.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(PurposeOfSiteVisit.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(PurposeOfSiteVisit obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public PurposeOfSiteVisit getRowData(String rowKey) {
			        for(PurposeOfSiteVisit obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert PurposeOfSiteVisit into database
 	 * @author TechFinium 
 	 * @see    PurposeOfSiteVisit
 	 */
	public void purposeofsitevisitInsert() {
		try {
				 service.create(this.purposeofsitevisit);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 purposeofsitevisitInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update PurposeOfSiteVisit in database
 	 * @author TechFinium 
 	 * @see    PurposeOfSiteVisit
 	 */
    public void purposeofsitevisitUpdate() {
		try {
				 service.update(this.purposeofsitevisit);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 purposeofsitevisitInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete PurposeOfSiteVisit from database
 	 * @author TechFinium 
 	 * @see    PurposeOfSiteVisit
 	 */
	public void purposeofsitevisitDelete() {
		try {
			 service.delete(this.purposeofsitevisit);
			  prepareNew();
			 purposeofsitevisitInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of PurposeOfSiteVisit 
 	 * @author TechFinium 
 	 * @see    PurposeOfSiteVisit
 	 */
	public void prepareNew() {
		purposeofsitevisit = new PurposeOfSiteVisit();
	}

/*
    public List<SelectItem> getPurposeOfSiteVisitDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	purposeofsitevisitInfo();
    	for (PurposeOfSiteVisit ug : purposeofsitevisitList) {
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
    public List<PurposeOfSiteVisit> complete(String desc) {
		List<PurposeOfSiteVisit> l = null;
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
    
    public List<PurposeOfSiteVisit> getPurposeOfSiteVisitList() {
		return purposeofsitevisitList;
	}
	public void setPurposeOfSiteVisitList(List<PurposeOfSiteVisit> purposeofsitevisitList) {
		this.purposeofsitevisitList = purposeofsitevisitList;
	}
	public PurposeOfSiteVisit getPurposeofsitevisit() {
		return purposeofsitevisit;
	}
	public void setPurposeofsitevisit(PurposeOfSiteVisit purposeofsitevisit) {
		this.purposeofsitevisit = purposeofsitevisit;
	}

    public List<PurposeOfSiteVisit> getPurposeOfSiteVisitfilteredList() {
		return purposeofsitevisitfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param purposeofsitevisitfilteredList the new purposeofsitevisitfilteredList list
 	 * @see    PurposeOfSiteVisit
 	 */	
	public void setPurposeOfSiteVisitfilteredList(List<PurposeOfSiteVisit> purposeofsitevisitfilteredList) {
		this.purposeofsitevisitfilteredList = purposeofsitevisitfilteredList;
	}

	
	public LazyDataModel<PurposeOfSiteVisit> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<PurposeOfSiteVisit> dataModel) {
		this.dataModel = dataModel;
	}
	
}
