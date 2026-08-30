package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.ProjectType;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.ProjectTypeService;

@ManagedBean(name = "projectTypeUI")
@ViewScoped
public class ProjectTypeUI extends AbstractUI {

	private ProjectTypeService service = new ProjectTypeService();
	private List<ProjectType> projectTypeList = null;
	private List<ProjectType> projectTypefilteredList = null;
	private ProjectType projectType= null;
	private LazyDataModel<ProjectType> dataModel; 

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
	 * Initialize method to get all projectType and prepare a for a create of a new projectType
 	 * @author TechFinium 
 	 * @see    ProjectType
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		ProjectTypeInfo();
	}

	/**
	 * Get all projectType for data table
 	 * @author TechFinium 
 	 * @see    ProjectType
 	 */
	public void ProjectTypeInfo() {
	 
			
			 dataModel = new LazyDataModel<ProjectType>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<ProjectType> retorno = new  ArrayList<ProjectType>();
			   
			   @Override 
			   public List<ProjectType> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allProjectType(ProjectType.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(ProjectType.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(ProjectType obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public ProjectType getRowData(String rowKey) {
			        for(ProjectType obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	public void projectTypeInsert()
	{
		try {
			 service.create(this.projectType);
			 prepareNew();
			 addInfoMessage(super.getEntryLanguage("update.successful"));
			 ProjectTypeInfo();
		 } catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
	 	   super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void applyDGYearData()
	{
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Update projectType in database
 	 * @author TechFinium 
 	 * @see    ProjectType
 	 */
    public void projectTypeUpdate() {
		try {
				 service.update(this.projectType);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 ProjectTypeInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete projectType from database
 	 * @author TechFinium 
 	 * @see    ProjectType
 	 */
	public void projectTypeDelete() {
		try {
			 service.delete(this.projectType);
			  prepareNew();
			 ProjectTypeInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of projectType 
 	 * @author TechFinium 
 	 * @see    ProjectType
 	 */
	public void prepareNew() {
		projectType = new ProjectType();
	}

/*
    public List<SelectItem> getprojectTypeDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	projectTypeInfo();
    	for (projectType ug : projectTypeList) {
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
    public List<ProjectType> complete(String desc) {
		List<ProjectType> l = null;
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
    
    public List<ProjectType> getprojectTypeList() {
		return projectTypeList;
	}
	public void setprojectTypeList(List<ProjectType> projectTypeList) {
		this.projectTypeList = projectTypeList;
	}


    public List<ProjectType> getprojectTypefilteredList() {
		return projectTypefilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param projectTypefilteredList the new projectTypefilteredList list
 	 * @see    ProjectType
 	 */	
	public void setprojectTypefilteredList(List<ProjectType> projectTypefilteredList) {
		this.projectTypefilteredList = projectTypefilteredList;
	}

	
	public LazyDataModel<ProjectType> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<ProjectType> dataModel) {
		this.dataModel = dataModel;
	}

	public ProjectType getProjectType() {
		return projectType;
	}

	public void setProjectType(ProjectType projectType) {
		this.projectType = projectType;
	}


}
