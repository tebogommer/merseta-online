package  haj.com.ui;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.SiteVisitReportSME;
import haj.com.service.SiteVisitReportSMEService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "sitevisitreportsmeUI")
@ViewScoped
public class SiteVisitReportSMEUI extends AbstractUI {

	private SiteVisitReportSMEService service = new SiteVisitReportSMEService();
	private List<SiteVisitReportSME> sitevisitreportsmeList = null;
	private List<SiteVisitReportSME> sitevisitreportsmefilteredList = null;
	private SiteVisitReportSME sitevisitreportsme = null;
	private LazyDataModel<SiteVisitReportSME> dataModel; 

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
	 * Initialize method to get all SiteVisitReportSME and prepare a for a create of a new SiteVisitReportSME
 	 * @author TechFinium 
 	 * @see    SiteVisitReportSME
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		sitevisitreportsmeInfo();
	}

	/**
	 * Get all SiteVisitReportSME for data table
 	 * @author TechFinium 
 	 * @see    SiteVisitReportSME
 	 */
	public void sitevisitreportsmeInfo() {
	 
			
			 dataModel = new LazyDataModel<SiteVisitReportSME>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<SiteVisitReportSME> retorno = new  ArrayList<SiteVisitReportSME>();
			   
			   @Override 
			   public List<SiteVisitReportSME> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allSiteVisitReportSME(SiteVisitReportSME.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(SiteVisitReportSME.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(SiteVisitReportSME obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public SiteVisitReportSME getRowData(String rowKey) {
			        for(SiteVisitReportSME obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert SiteVisitReportSME into database
 	 * @author TechFinium 
 	 * @see    SiteVisitReportSME
 	 */
	public void sitevisitreportsmeInsert() {
		try {
				 service.create(this.sitevisitreportsme);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 sitevisitreportsmeInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update SiteVisitReportSME in database
 	 * @author TechFinium 
 	 * @see    SiteVisitReportSME
 	 */
    public void sitevisitreportsmeUpdate() {
		try {
				 service.update(this.sitevisitreportsme);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 sitevisitreportsmeInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete SiteVisitReportSME from database
 	 * @author TechFinium 
 	 * @see    SiteVisitReportSME
 	 */
	public void sitevisitreportsmeDelete() {
		try {
			 service.delete(this.sitevisitreportsme);
			  prepareNew();
			 sitevisitreportsmeInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of SiteVisitReportSME 
 	 * @author TechFinium 
 	 * @see    SiteVisitReportSME
 	 */
	public void prepareNew() {
		sitevisitreportsme = new SiteVisitReportSME();
	}

/*
    public List<SelectItem> getSiteVisitReportSMEDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	sitevisitreportsmeInfo();
    	for (SiteVisitReportSME ug : sitevisitreportsmeList) {
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
    public List<SiteVisitReportSME> complete(String desc) {
		List<SiteVisitReportSME> l = null;
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
    
    public List<SiteVisitReportSME> getSiteVisitReportSMEList() {
		return sitevisitreportsmeList;
	}
	public void setSiteVisitReportSMEList(List<SiteVisitReportSME> sitevisitreportsmeList) {
		this.sitevisitreportsmeList = sitevisitreportsmeList;
	}
	public SiteVisitReportSME getSitevisitreportsme() {
		return sitevisitreportsme;
	}
	public void setSitevisitreportsme(SiteVisitReportSME sitevisitreportsme) {
		this.sitevisitreportsme = sitevisitreportsme;
	}

    public List<SiteVisitReportSME> getSiteVisitReportSMEfilteredList() {
		return sitevisitreportsmefilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param sitevisitreportsmefilteredList the new sitevisitreportsmefilteredList list
 	 * @see    SiteVisitReportSME
 	 */	
	public void setSiteVisitReportSMEfilteredList(List<SiteVisitReportSME> sitevisitreportsmefilteredList) {
		this.sitevisitreportsmefilteredList = sitevisitreportsmefilteredList;
	}

	
	public LazyDataModel<SiteVisitReportSME> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SiteVisitReportSME> dataModel) {
		this.dataModel = dataModel;
	}
	
}
