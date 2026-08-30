package  haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.lookup.ReportGenerationProperties;
import haj.com.service.lookup.ReportGenerationPropertiesService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "reportgenerationpropertiesUI")
@ViewScoped
public class ReportGenerationPropertiesUI extends AbstractUI {

	private ReportGenerationPropertiesService service = new ReportGenerationPropertiesService();
	private List<ReportGenerationProperties> reportgenerationpropertiesList = null;
	private List<ReportGenerationProperties> reportgenerationpropertiesfilteredList = null;
	private ReportGenerationProperties reportgenerationproperties = null;
	private LazyDataModel<ReportGenerationProperties> dataModel; 

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
	 * Initialize method to get all ReportGenerationProperties and prepare a for a create of a new ReportGenerationProperties
 	 * @author TechFinium 
 	 * @see    ReportGenerationProperties
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		reportgenerationpropertiesInfo();
	}

	/**
	 * Get all ReportGenerationProperties for data table
 	 * @author TechFinium 
 	 * @see    ReportGenerationProperties
 	 */
	public void reportgenerationpropertiesInfo() {
	 
			
			 dataModel = new LazyDataModel<ReportGenerationProperties>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<ReportGenerationProperties> retorno = new  ArrayList<ReportGenerationProperties>();
			   
			   @Override 
			   public List<ReportGenerationProperties> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allReportGenerationProperties(ReportGenerationProperties.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(ReportGenerationProperties.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(ReportGenerationProperties obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public ReportGenerationProperties getRowData(String rowKey) {
			        for(ReportGenerationProperties obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert ReportGenerationProperties into database
 	 * @author TechFinium 
 	 * @see    ReportGenerationProperties
 	 */
	public void reportgenerationpropertiesInsert() {
		try {
				 service.create(this.reportgenerationproperties);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 reportgenerationpropertiesInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update ReportGenerationProperties in database
 	 * @author TechFinium 
 	 * @see    ReportGenerationProperties
 	 */
    public void reportgenerationpropertiesUpdate() {
		try {
				 service.update(this.reportgenerationproperties);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 reportgenerationpropertiesInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete ReportGenerationProperties from database
 	 * @author TechFinium 
 	 * @see    ReportGenerationProperties
 	 */
	public void reportgenerationpropertiesDelete() {
		try {
			 service.delete(this.reportgenerationproperties);
			  prepareNew();
			 reportgenerationpropertiesInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of ReportGenerationProperties 
 	 * @author TechFinium 
 	 * @see    ReportGenerationProperties
 	 */
	public void prepareNew() {
		reportgenerationproperties = new ReportGenerationProperties();
		reportgenerationproperties.setGenerationUnderway(false);
	}

/*
    public List<SelectItem> getReportGenerationPropertiesDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	reportgenerationpropertiesInfo();
    	for (ReportGenerationProperties ug : reportgenerationpropertiesList) {
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
    public List<ReportGenerationProperties> complete(String desc) {
		List<ReportGenerationProperties> l = null;
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
    
    public List<ReportGenerationProperties> getReportGenerationPropertiesList() {
		return reportgenerationpropertiesList;
	}
	public void setReportGenerationPropertiesList(List<ReportGenerationProperties> reportgenerationpropertiesList) {
		this.reportgenerationpropertiesList = reportgenerationpropertiesList;
	}
	public ReportGenerationProperties getReportgenerationproperties() {
		return reportgenerationproperties;
	}
	public void setReportgenerationproperties(ReportGenerationProperties reportgenerationproperties) {
		this.reportgenerationproperties = reportgenerationproperties;
	}

    public List<ReportGenerationProperties> getReportGenerationPropertiesfilteredList() {
		return reportgenerationpropertiesfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param reportgenerationpropertiesfilteredList the new reportgenerationpropertiesfilteredList list
 	 * @see    ReportGenerationProperties
 	 */	
	public void setReportGenerationPropertiesfilteredList(List<ReportGenerationProperties> reportgenerationpropertiesfilteredList) {
		this.reportgenerationpropertiesfilteredList = reportgenerationpropertiesfilteredList;
	}

	
	public LazyDataModel<ReportGenerationProperties> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<ReportGenerationProperties> dataModel) {
		this.dataModel = dataModel;
	}
	
}
