package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.SummativeAssessmentReportUnitStandards;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.SummativeAssessmentReportUnitStandardsService;

@ManagedBean(name = "summativeassessmentreportunitstandardsUI")
@ViewScoped
public class SummativeAssessmentReportUnitStandardsUI extends AbstractUI {

	private SummativeAssessmentReportUnitStandardsService service = new SummativeAssessmentReportUnitStandardsService();
	private List<SummativeAssessmentReportUnitStandards> summativeassessmentreportunitstandardsList = null;
	private List<SummativeAssessmentReportUnitStandards> summativeassessmentreportunitstandardsfilteredList = null;
	private SummativeAssessmentReportUnitStandards summativeassessmentreportunitstandards = null;
	private LazyDataModel<SummativeAssessmentReportUnitStandards> dataModel; 

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
	 * Initialize method to get all SummativeAssessmentReportUnitStandards and prepare a for a create of a new SummativeAssessmentReportUnitStandards
 	 * @author TechFinium 
 	 * @see    SummativeAssessmentReportUnitStandards
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		summativeassessmentreportunitstandardsInfo();
	}

	/**
	 * Get all SummativeAssessmentReportUnitStandards for data table
 	 * @author TechFinium 
 	 * @see    SummativeAssessmentReportUnitStandards
 	 */
	public void summativeassessmentreportunitstandardsInfo() {
	 
			
			 dataModel = new LazyDataModel<SummativeAssessmentReportUnitStandards>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<SummativeAssessmentReportUnitStandards> retorno = new  ArrayList<SummativeAssessmentReportUnitStandards>();
			   
			   @Override 
			   public List<SummativeAssessmentReportUnitStandards> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allSummativeAssessmentReportUnitStandards(SummativeAssessmentReportUnitStandards.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(SummativeAssessmentReportUnitStandards.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(SummativeAssessmentReportUnitStandards obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public SummativeAssessmentReportUnitStandards getRowData(String rowKey) {
			        for(SummativeAssessmentReportUnitStandards obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert SummativeAssessmentReportUnitStandards into database
 	 * @author TechFinium 
 	 * @see    SummativeAssessmentReportUnitStandards
 	 */
	public void summativeassessmentreportunitstandardsInsert() {
		try {
				 service.create(this.summativeassessmentreportunitstandards);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 summativeassessmentreportunitstandardsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update SummativeAssessmentReportUnitStandards in database
 	 * @author TechFinium 
 	 * @see    SummativeAssessmentReportUnitStandards
 	 */
    public void summativeassessmentreportunitstandardsUpdate() {
		try {
				 service.update(this.summativeassessmentreportunitstandards);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 summativeassessmentreportunitstandardsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete SummativeAssessmentReportUnitStandards from database
 	 * @author TechFinium 
 	 * @see    SummativeAssessmentReportUnitStandards
 	 */
	public void summativeassessmentreportunitstandardsDelete() {
		try {
			 service.delete(this.summativeassessmentreportunitstandards);
			  prepareNew();
			 summativeassessmentreportunitstandardsInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of SummativeAssessmentReportUnitStandards 
 	 * @author TechFinium 
 	 * @see    SummativeAssessmentReportUnitStandards
 	 */
	public void prepareNew() {
		summativeassessmentreportunitstandards = new SummativeAssessmentReportUnitStandards();
	}

/*
    public List<SelectItem> getSummativeAssessmentReportUnitStandardsDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	summativeassessmentreportunitstandardsInfo();
    	for (SummativeAssessmentReportUnitStandards ug : summativeassessmentreportunitstandardsList) {
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
    public List<SummativeAssessmentReportUnitStandards> complete(String desc) {
		List<SummativeAssessmentReportUnitStandards> l = null;
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
    
    public List<SummativeAssessmentReportUnitStandards> getSummativeAssessmentReportUnitStandardsList() {
		return summativeassessmentreportunitstandardsList;
	}
	public void setSummativeAssessmentReportUnitStandardsList(List<SummativeAssessmentReportUnitStandards> summativeassessmentreportunitstandardsList) {
		this.summativeassessmentreportunitstandardsList = summativeassessmentreportunitstandardsList;
	}
	public SummativeAssessmentReportUnitStandards getSummativeassessmentreportunitstandards() {
		return summativeassessmentreportunitstandards;
	}
	public void setSummativeassessmentreportunitstandards(SummativeAssessmentReportUnitStandards summativeassessmentreportunitstandards) {
		this.summativeassessmentreportunitstandards = summativeassessmentreportunitstandards;
	}

    public List<SummativeAssessmentReportUnitStandards> getSummativeAssessmentReportUnitStandardsfilteredList() {
		return summativeassessmentreportunitstandardsfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param summativeassessmentreportunitstandardsfilteredList the new summativeassessmentreportunitstandardsfilteredList list
 	 * @see    SummativeAssessmentReportUnitStandards
 	 */	
	public void setSummativeAssessmentReportUnitStandardsfilteredList(List<SummativeAssessmentReportUnitStandards> summativeassessmentreportunitstandardsfilteredList) {
		this.summativeassessmentreportunitstandardsfilteredList = summativeassessmentreportunitstandardsfilteredList;
	}

	
	public LazyDataModel<SummativeAssessmentReportUnitStandards> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SummativeAssessmentReportUnitStandards> dataModel) {
		this.dataModel = dataModel;
	}
	
}
