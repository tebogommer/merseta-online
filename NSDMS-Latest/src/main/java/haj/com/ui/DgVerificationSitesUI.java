package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.DgVerificationSites;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.DgVerificationSitesService;

@ManagedBean(name = "dgverificationsitesUI")
@ViewScoped
public class DgVerificationSitesUI extends AbstractUI {

	private DgVerificationSitesService service = new DgVerificationSitesService();
	private List<DgVerificationSites> dgverificationsitesList = null;
	private List<DgVerificationSites> dgverificationsitesfilteredList = null;
	private DgVerificationSites dgverificationsites = null;
	private LazyDataModel<DgVerificationSites> dataModel; 

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
	 * Initialize method to get all DgVerificationSites and prepare a for a create of a new DgVerificationSites
 	 * @author TechFinium 
 	 * @see    DgVerificationSites
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		dgverificationsitesInfo();
	}

	/**
	 * Get all DgVerificationSites for data table
 	 * @author TechFinium 
 	 * @see    DgVerificationSites
 	 */
	public void dgverificationsitesInfo() {
	 
			
			 dataModel = new LazyDataModel<DgVerificationSites>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<DgVerificationSites> retorno = new  ArrayList<DgVerificationSites>();
			   
			   @Override 
			   public List<DgVerificationSites> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allDgVerificationSites(DgVerificationSites.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(DgVerificationSites.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(DgVerificationSites obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public DgVerificationSites getRowData(String rowKey) {
			        for(DgVerificationSites obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert DgVerificationSites into database
 	 * @author TechFinium 
 	 * @see    DgVerificationSites
 	 */
	public void dgverificationsitesInsert() {
		try {
				 service.create(this.dgverificationsites);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 dgverificationsitesInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update DgVerificationSites in database
 	 * @author TechFinium 
 	 * @see    DgVerificationSites
 	 */
    public void dgverificationsitesUpdate() {
		try {
				 service.update(this.dgverificationsites);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 dgverificationsitesInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete DgVerificationSites from database
 	 * @author TechFinium 
 	 * @see    DgVerificationSites
 	 */
	public void dgverificationsitesDelete() {
		try {
			 service.delete(this.dgverificationsites);
			  prepareNew();
			 dgverificationsitesInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of DgVerificationSites 
 	 * @author TechFinium 
 	 * @see    DgVerificationSites
 	 */
	public void prepareNew() {
		dgverificationsites = new DgVerificationSites();
	}

/*
    public List<SelectItem> getDgVerificationSitesDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	dgverificationsitesInfo();
    	for (DgVerificationSites ug : dgverificationsitesList) {
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
    public List<DgVerificationSites> complete(String desc) {
		List<DgVerificationSites> l = null;
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
    
    public List<DgVerificationSites> getDgVerificationSitesList() {
		return dgverificationsitesList;
	}
	public void setDgVerificationSitesList(List<DgVerificationSites> dgverificationsitesList) {
		this.dgverificationsitesList = dgverificationsitesList;
	}
	public DgVerificationSites getDgverificationsites() {
		return dgverificationsites;
	}
	public void setDgverificationsites(DgVerificationSites dgverificationsites) {
		this.dgverificationsites = dgverificationsites;
	}

    public List<DgVerificationSites> getDgVerificationSitesfilteredList() {
		return dgverificationsitesfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param dgverificationsitesfilteredList the new dgverificationsitesfilteredList list
 	 * @see    DgVerificationSites
 	 */	
	public void setDgVerificationSitesfilteredList(List<DgVerificationSites> dgverificationsitesfilteredList) {
		this.dgverificationsitesfilteredList = dgverificationsitesfilteredList;
	}

	
	public LazyDataModel<DgVerificationSites> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<DgVerificationSites> dataModel) {
		this.dataModel = dataModel;
	}
	
}
