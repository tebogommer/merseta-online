package  haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.lookup.QualificationTasks;
import haj.com.service.lookup.QualificationTasksService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "qualificationtasksUI")
@ViewScoped
public class QualificationTasksUI extends AbstractUI {

	private QualificationTasksService service = new QualificationTasksService();
	private List<QualificationTasks> qualificationtasksList = null;
	private List<QualificationTasks> qualificationtasksfilteredList = null;
	private QualificationTasks qualificationtasks = null;
	private LazyDataModel<QualificationTasks> dataModel; 

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
	 * Initialize method to get all QualificationTasks and prepare a for a create of a new QualificationTasks
 	 * @author TechFinium 
 	 * @see    QualificationTasks
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		qualificationtasksInfo();
	}

	/**
	 * Get all QualificationTasks for data table
 	 * @author TechFinium 
 	 * @see    QualificationTasks
 	 */
	public void qualificationtasksInfo() {
	 
			
			 dataModel = new LazyDataModel<QualificationTasks>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<QualificationTasks> retorno = new  ArrayList<QualificationTasks>();
			   
			   @Override 
			   public List<QualificationTasks> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allQualificationTasks(QualificationTasks.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(QualificationTasks.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(QualificationTasks obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public QualificationTasks getRowData(String rowKey) {
			        for(QualificationTasks obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert QualificationTasks into database
 	 * @author TechFinium 
 	 * @see    QualificationTasks
 	 */
	public void qualificationtasksInsert() {
		try {
				 service.create(this.qualificationtasks);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 qualificationtasksInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update QualificationTasks in database
 	 * @author TechFinium 
 	 * @see    QualificationTasks
 	 */
    public void qualificationtasksUpdate() {
		try {
				 service.update(this.qualificationtasks);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 qualificationtasksInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete QualificationTasks from database
 	 * @author TechFinium 
 	 * @see    QualificationTasks
 	 */
	public void qualificationtasksDelete() {
		try {
			 service.delete(this.qualificationtasks);
			  prepareNew();
			 qualificationtasksInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of QualificationTasks 
 	 * @author TechFinium 
 	 * @see    QualificationTasks
 	 */
	public void prepareNew() {
		qualificationtasks = new QualificationTasks();
	}

/*
    public List<SelectItem> getQualificationTasksDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	qualificationtasksInfo();
    	for (QualificationTasks ug : qualificationtasksList) {
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
    public List<QualificationTasks> complete(String desc) {
		List<QualificationTasks> l = null;
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
    
    public List<QualificationTasks> getQualificationTasksList() {
		return qualificationtasksList;
	}
	public void setQualificationTasksList(List<QualificationTasks> qualificationtasksList) {
		this.qualificationtasksList = qualificationtasksList;
	}
	public QualificationTasks getQualificationtasks() {
		return qualificationtasks;
	}
	public void setQualificationtasks(QualificationTasks qualificationtasks) {
		this.qualificationtasks = qualificationtasks;
	}

    public List<QualificationTasks> getQualificationTasksfilteredList() {
		return qualificationtasksfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param qualificationtasksfilteredList the new qualificationtasksfilteredList list
 	 * @see    QualificationTasks
 	 */	
	public void setQualificationTasksfilteredList(List<QualificationTasks> qualificationtasksfilteredList) {
		this.qualificationtasksfilteredList = qualificationtasksfilteredList;
	}

	
	public LazyDataModel<QualificationTasks> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<QualificationTasks> dataModel) {
		this.dataModel = dataModel;
	}
	
}
