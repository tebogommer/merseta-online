package  haj.com.ui;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.SiteVisitReportDispute;
import haj.com.service.SiteVisitReportDisputeService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "sitevisitreportdisputeUI")
@ViewScoped
public class SiteVisitReportDisputeUI extends AbstractUI {

	private SiteVisitReportDisputeService service = new SiteVisitReportDisputeService();
	private List<SiteVisitReportDispute> sitevisitreportdisputeList = null;
	private List<SiteVisitReportDispute> sitevisitreportdisputefilteredList = null;
	private SiteVisitReportDispute sitevisitreportdispute = null;
	private LazyDataModel<SiteVisitReportDispute> dataModel; 

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
	 * Initialize method to get all SiteVisitReportDispute and prepare a for a create of a new SiteVisitReportDispute
 	 * @author TechFinium 
 	 * @see    SiteVisitReportDispute
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		sitevisitreportdisputeInfo();
	}

	/**
	 * Get all SiteVisitReportDispute for data table
 	 * @author TechFinium 
 	 * @see    SiteVisitReportDispute
 	 */
	public void sitevisitreportdisputeInfo() {
	 
			
			 dataModel = new LazyDataModel<SiteVisitReportDispute>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<SiteVisitReportDispute> retorno = new  ArrayList<SiteVisitReportDispute>();
			   
			   @Override 
			   public List<SiteVisitReportDispute> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allSiteVisitReportDispute(SiteVisitReportDispute.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(SiteVisitReportDispute.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(SiteVisitReportDispute obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public SiteVisitReportDispute getRowData(String rowKey) {
			        for(SiteVisitReportDispute obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert SiteVisitReportDispute into database
 	 * @author TechFinium 
 	 * @see    SiteVisitReportDispute
 	 */
	public void sitevisitreportdisputeInsert() {
		try {
				 service.create(this.sitevisitreportdispute);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 sitevisitreportdisputeInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update SiteVisitReportDispute in database
 	 * @author TechFinium 
 	 * @see    SiteVisitReportDispute
 	 */
    public void sitevisitreportdisputeUpdate() {
		try {
				 service.update(this.sitevisitreportdispute);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 sitevisitreportdisputeInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete SiteVisitReportDispute from database
 	 * @author TechFinium 
 	 * @see    SiteVisitReportDispute
 	 */
	public void sitevisitreportdisputeDelete() {
		try {
			 service.delete(this.sitevisitreportdispute);
			  prepareNew();
			 sitevisitreportdisputeInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of SiteVisitReportDispute 
 	 * @author TechFinium 
 	 * @see    SiteVisitReportDispute
 	 */
	public void prepareNew() {
		sitevisitreportdispute = new SiteVisitReportDispute();
	}

/*
    public List<SelectItem> getSiteVisitReportDisputeDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	sitevisitreportdisputeInfo();
    	for (SiteVisitReportDispute ug : sitevisitreportdisputeList) {
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
    public List<SiteVisitReportDispute> complete(String desc) {
		List<SiteVisitReportDispute> l = null;
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
    
    public List<SiteVisitReportDispute> getSiteVisitReportDisputeList() {
		return sitevisitreportdisputeList;
	}
	public void setSiteVisitReportDisputeList(List<SiteVisitReportDispute> sitevisitreportdisputeList) {
		this.sitevisitreportdisputeList = sitevisitreportdisputeList;
	}
	public SiteVisitReportDispute getSitevisitreportdispute() {
		return sitevisitreportdispute;
	}
	public void setSitevisitreportdispute(SiteVisitReportDispute sitevisitreportdispute) {
		this.sitevisitreportdispute = sitevisitreportdispute;
	}

    public List<SiteVisitReportDispute> getSiteVisitReportDisputefilteredList() {
		return sitevisitreportdisputefilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param sitevisitreportdisputefilteredList the new sitevisitreportdisputefilteredList list
 	 * @see    SiteVisitReportDispute
 	 */	
	public void setSiteVisitReportDisputefilteredList(List<SiteVisitReportDispute> sitevisitreportdisputefilteredList) {
		this.sitevisitreportdisputefilteredList = sitevisitreportdisputefilteredList;
	}

	
	public LazyDataModel<SiteVisitReportDispute> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SiteVisitReportDispute> dataModel) {
		this.dataModel = dataModel;
	}
	
}
