package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.PurposeOfSiteVisit;
import haj.com.entity.lookup.PurposeOfSiteVisitChild;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.PurposeOfSiteVisitChildService;
import haj.com.service.lookup.PurposeOfSiteVisitService;

@ManagedBean(name = "purposeofsitevisitchildUI")
@ViewScoped
public class PurposeOfSiteVisitChildUI extends AbstractUI {

	private PurposeOfSiteVisitChildService service = new PurposeOfSiteVisitChildService();
	private List<PurposeOfSiteVisitChild> purposeofsitevisitchildList = null;
	private List<PurposeOfSiteVisitChild> purposeofsitevisitchildfilteredList = null;
	private PurposeOfSiteVisitChild purposeofsitevisitchild = null;
	private LazyDataModel<PurposeOfSiteVisitChild> dataModel; 
	private List<PurposeOfSiteVisit> PurposeOfSiteVisitList = new ArrayList<>();
	private PurposeOfSiteVisitService purposeOfSiteVisitService = new PurposeOfSiteVisitService();

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
	 * Initialize method to get all PurposeOfSiteVisitChild and prepare a for a create of a new PurposeOfSiteVisitChild
 	 * @author TechFinium 
 	 * @see    PurposeOfSiteVisitChild
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		purposeofsitevisitchildInfo();
		PurposeOfSiteVisitList = purposeOfSiteVisitService.allPurposeOfSiteVisit();
	}

	/**
	 * Get all PurposeOfSiteVisitChild for data table
 	 * @author TechFinium 
 	 * @see    PurposeOfSiteVisitChild
 	 */
	public void purposeofsitevisitchildInfo() {
	 
			
			 dataModel = new LazyDataModel<PurposeOfSiteVisitChild>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<PurposeOfSiteVisitChild> retorno = new  ArrayList<PurposeOfSiteVisitChild>();
			   
			   @Override 
			   public List<PurposeOfSiteVisitChild> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allPurposeOfSiteVisitChild(PurposeOfSiteVisitChild.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(PurposeOfSiteVisitChild.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(PurposeOfSiteVisitChild obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public PurposeOfSiteVisitChild getRowData(String rowKey) {
			        for(PurposeOfSiteVisitChild obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert PurposeOfSiteVisitChild into database
 	 * @author TechFinium 
 	 * @see    PurposeOfSiteVisitChild
 	 */
	public void purposeofsitevisitchildInsert() {
		try {
				 service.create(this.purposeofsitevisitchild);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 purposeofsitevisitchildInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update PurposeOfSiteVisitChild in database
 	 * @author TechFinium 
 	 * @see    PurposeOfSiteVisitChild
 	 */
    public void purposeofsitevisitchildUpdate() {
		try {
				 service.update(this.purposeofsitevisitchild);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 purposeofsitevisitchildInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete PurposeOfSiteVisitChild from database
 	 * @author TechFinium 
 	 * @see    PurposeOfSiteVisitChild
 	 */
	public void purposeofsitevisitchildDelete() {
		try {
			 service.delete(this.purposeofsitevisitchild);
			  prepareNew();
			 purposeofsitevisitchildInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of PurposeOfSiteVisitChild 
 	 * @author TechFinium 
 	 * @see    PurposeOfSiteVisitChild
 	 */
	public void prepareNew() {
		purposeofsitevisitchild = new PurposeOfSiteVisitChild();
	}

/*
    public List<SelectItem> getPurposeOfSiteVisitChildDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	purposeofsitevisitchildInfo();
    	for (PurposeOfSiteVisitChild ug : purposeofsitevisitchildList) {
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
    public List<PurposeOfSiteVisitChild> complete(String desc) {
		List<PurposeOfSiteVisitChild> l = null;
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
    
    public List<PurposeOfSiteVisitChild> getPurposeOfSiteVisitChildList() {
		return purposeofsitevisitchildList;
	}
	public void setPurposeOfSiteVisitChildList(List<PurposeOfSiteVisitChild> purposeofsitevisitchildList) {
		this.purposeofsitevisitchildList = purposeofsitevisitchildList;
	}
	public PurposeOfSiteVisitChild getPurposeofsitevisitchild() {
		return purposeofsitevisitchild;
	}
	public void setPurposeofsitevisitchild(PurposeOfSiteVisitChild purposeofsitevisitchild) {
		this.purposeofsitevisitchild = purposeofsitevisitchild;
	}

    public List<PurposeOfSiteVisitChild> getPurposeOfSiteVisitChildfilteredList() {
		return purposeofsitevisitchildfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param purposeofsitevisitchildfilteredList the new purposeofsitevisitchildfilteredList list
 	 * @see    PurposeOfSiteVisitChild
 	 */	
	public void setPurposeOfSiteVisitChildfilteredList(List<PurposeOfSiteVisitChild> purposeofsitevisitchildfilteredList) {
		this.purposeofsitevisitchildfilteredList = purposeofsitevisitchildfilteredList;
	}

	
	public LazyDataModel<PurposeOfSiteVisitChild> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<PurposeOfSiteVisitChild> dataModel) {
		this.dataModel = dataModel;
	}

	public PurposeOfSiteVisitChildService getService() {
		return service;
	}

	public void setService(PurposeOfSiteVisitChildService service) {
		this.service = service;
	}

	public List<PurposeOfSiteVisitChild> getPurposeofsitevisitchildList() {
		return purposeofsitevisitchildList;
	}

	public void setPurposeofsitevisitchildList(List<PurposeOfSiteVisitChild> purposeofsitevisitchildList) {
		this.purposeofsitevisitchildList = purposeofsitevisitchildList;
	}

	public List<PurposeOfSiteVisitChild> getPurposeofsitevisitchildfilteredList() {
		return purposeofsitevisitchildfilteredList;
	}

	public void setPurposeofsitevisitchildfilteredList(List<PurposeOfSiteVisitChild> purposeofsitevisitchildfilteredList) {
		this.purposeofsitevisitchildfilteredList = purposeofsitevisitchildfilteredList;
	}

	public List<PurposeOfSiteVisit> getPurposeOfSiteVisitList() {
		return PurposeOfSiteVisitList;
	}

	public void setPurposeOfSiteVisitList(List<PurposeOfSiteVisit> purposeOfSiteVisitList) {
		PurposeOfSiteVisitList = purposeOfSiteVisitList;
	}

	public PurposeOfSiteVisitService getPurposeOfSiteVisitService() {
		return purposeOfSiteVisitService;
	}

	public void setPurposeOfSiteVisitService(PurposeOfSiteVisitService purposeOfSiteVisitService) {
		this.purposeOfSiteVisitService = purposeOfSiteVisitService;
	}
	
}
