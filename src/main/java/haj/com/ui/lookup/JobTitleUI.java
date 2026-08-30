package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.JobTitle;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.JobTitleService;

@ManagedBean(name = "jobtitleUI")
@ViewScoped
public class JobTitleUI extends AbstractUI {

	private JobTitleService service = new JobTitleService();
	private List<JobTitle> jobtitleList = null;
	private List<JobTitle> jobtitlefilteredList = null;
	private JobTitle jobtitle = null;
	private LazyDataModel<JobTitle> dataModel; 

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
	 * Initialize method to get all JobTitle and prepare a for a create of a new JobTitle
 	 * @author TechFinium 
 	 * @see    JobTitle
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		jobtitleInfo();
	}

	/**
	 * Get all JobTitle for data table
 	 * @author TechFinium 
 	 * @see    JobTitle
 	 */
	public void jobtitleInfo() {
	 
			
			 dataModel = new LazyDataModel<JobTitle>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<JobTitle> retorno = new  ArrayList<JobTitle>();
			   
			   @Override 
			   public List<JobTitle> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allJobTitle(JobTitle.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(JobTitle.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(JobTitle obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public JobTitle getRowData(String rowKey) {
			        for(JobTitle obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert JobTitle into database
 	 * @author TechFinium 
 	 * @see    JobTitle
 	 */
	public void jobtitleInsert() {
		try {
				 service.create(this.jobtitle);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 jobtitleInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update JobTitle in database
 	 * @author TechFinium 
 	 * @see    JobTitle
 	 */
    public void jobtitleUpdate() {
		try {
				 service.update(this.jobtitle);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 jobtitleInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete JobTitle from database
 	 * @author TechFinium 
 	 * @see    JobTitle
 	 */
	public void jobtitleDelete() {
		try {
			 service.delete(this.jobtitle);
			  prepareNew();
			 jobtitleInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of JobTitle 
 	 * @author TechFinium 
 	 * @see    JobTitle
 	 */
	public void prepareNew() {
		jobtitle = new JobTitle();
	}

/*
    public List<SelectItem> getJobTitleDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	jobtitleInfo();
    	for (JobTitle ug : jobtitleList) {
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
    public List<JobTitle> complete(String desc) {
		List<JobTitle> l = null;
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
    
    public List<JobTitle> getJobTitleList() {
		return jobtitleList;
	}
	public void setJobTitleList(List<JobTitle> jobtitleList) {
		this.jobtitleList = jobtitleList;
	}
	public JobTitle getJobtitle() {
		return jobtitle;
	}
	public void setJobtitle(JobTitle jobtitle) {
		this.jobtitle = jobtitle;
	}

    public List<JobTitle> getJobTitlefilteredList() {
		return jobtitlefilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param jobtitlefilteredList the new jobtitlefilteredList list
 	 * @see    JobTitle
 	 */	
	public void setJobTitlefilteredList(List<JobTitle> jobtitlefilteredList) {
		this.jobtitlefilteredList = jobtitlefilteredList;
	}

	
	public LazyDataModel<JobTitle> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<JobTitle> dataModel) {
		this.dataModel = dataModel;
	}
	
}
