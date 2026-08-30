package  haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.lookup.ReportGenerationSchedule;
import haj.com.service.lookup.ReportGenerationScheduleService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "reportgenerationscheduleUI")
@ViewScoped
public class ReportGenerationScheduleUI extends AbstractUI {

	private ReportGenerationScheduleService service = new ReportGenerationScheduleService();
	private List<ReportGenerationSchedule> reportgenerationscheduleList = null;
	private List<ReportGenerationSchedule> reportgenerationschedulefilteredList = null;
	private ReportGenerationSchedule reportgenerationschedule = null;
	private LazyDataModel<ReportGenerationSchedule> dataModel; 

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
	 * Initialize method to get all ReportGenerationSchedule and prepare a for a create of a new ReportGenerationSchedule
 	 * @author TechFinium 
 	 * @see    ReportGenerationSchedule
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		reportgenerationscheduleInfo();
	}

	/**
	 * Get all ReportGenerationSchedule for data table
 	 * @author TechFinium 
 	 * @see    ReportGenerationSchedule
 	 */
	public void reportgenerationscheduleInfo() {
	 
			
			 dataModel = new LazyDataModel<ReportGenerationSchedule>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<ReportGenerationSchedule> retorno = new  ArrayList<ReportGenerationSchedule>();
			   
			   @Override 
			   public List<ReportGenerationSchedule> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allReportGenerationSchedule(ReportGenerationSchedule.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(ReportGenerationSchedule.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(ReportGenerationSchedule obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public ReportGenerationSchedule getRowData(String rowKey) {
			        for(ReportGenerationSchedule obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert ReportGenerationSchedule into database
 	 * @author TechFinium 
 	 * @see    ReportGenerationSchedule
 	 */
	public void reportgenerationscheduleInsert() {
		try {
				 service.create(this.reportgenerationschedule);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 reportgenerationscheduleInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update ReportGenerationSchedule in database
 	 * @author TechFinium 
 	 * @see    ReportGenerationSchedule
 	 */
    public void reportgenerationscheduleUpdate() {
		try {
				 service.update(this.reportgenerationschedule);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 reportgenerationscheduleInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete ReportGenerationSchedule from database
 	 * @author TechFinium 
 	 * @see    ReportGenerationSchedule
 	 */
	public void reportgenerationscheduleDelete() {
		try {
			 service.delete(this.reportgenerationschedule);
			  prepareNew();
			 reportgenerationscheduleInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of ReportGenerationSchedule 
 	 * @author TechFinium 
 	 * @see    ReportGenerationSchedule
 	 */
	public void prepareNew() {
		reportgenerationschedule = new ReportGenerationSchedule();
	}

/*
    public List<SelectItem> getReportGenerationScheduleDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	reportgenerationscheduleInfo();
    	for (ReportGenerationSchedule ug : reportgenerationscheduleList) {
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
    public List<ReportGenerationSchedule> complete(String desc) {
		List<ReportGenerationSchedule> l = null;
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
    
    public List<ReportGenerationSchedule> getReportGenerationScheduleList() {
		return reportgenerationscheduleList;
	}
	public void setReportGenerationScheduleList(List<ReportGenerationSchedule> reportgenerationscheduleList) {
		this.reportgenerationscheduleList = reportgenerationscheduleList;
	}
	public ReportGenerationSchedule getReportgenerationschedule() {
		return reportgenerationschedule;
	}
	public void setReportgenerationschedule(ReportGenerationSchedule reportgenerationschedule) {
		this.reportgenerationschedule = reportgenerationschedule;
	}

    public List<ReportGenerationSchedule> getReportGenerationSchedulefilteredList() {
		return reportgenerationschedulefilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param reportgenerationschedulefilteredList the new reportgenerationschedulefilteredList list
 	 * @see    ReportGenerationSchedule
 	 */	
	public void setReportGenerationSchedulefilteredList(List<ReportGenerationSchedule> reportgenerationschedulefilteredList) {
		this.reportgenerationschedulefilteredList = reportgenerationschedulefilteredList;
	}

	
	public LazyDataModel<ReportGenerationSchedule> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<ReportGenerationSchedule> dataModel) {
		this.dataModel = dataModel;
	}
	
}
