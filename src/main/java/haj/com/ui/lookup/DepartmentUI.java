package  haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.lookup.Department;
import haj.com.service.lookup.DepartmentService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "departmentUI")
@ViewScoped
public class DepartmentUI extends AbstractUI {

	private DepartmentService service = new DepartmentService();
	private List<Department> departmentList = null;
	private List<Department> departmentfilteredList = null;
	private Department department = null;
	private LazyDataModel<Department> dataModel; 

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
	 * Initialize method to get all Department and prepare a for a create of a new Department
 	 * @author TechFinium 
 	 * @see    Department
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		departmentInfo();
	}

	/**
	 * Get all Department for data table
 	 * @author TechFinium 
 	 * @see    Department
 	 */
	public void departmentInfo() {
	 
			
			 dataModel = new LazyDataModel<Department>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<Department> retorno = new  ArrayList<Department>();
			   
			   @Override 
			   public List<Department> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allDepartment(Department.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(Department.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(Department obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public Department getRowData(String rowKey) {
			        for(Department obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert Department into database
 	 * @author TechFinium 
 	 * @see    Department
 	 */
	public void departmentInsert() {
		try {
				 service.create(this.department);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 departmentInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update Department in database
 	 * @author TechFinium 
 	 * @see    Department
 	 */
    public void departmentUpdate() {
		try {
				 service.update(this.department);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 departmentInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete Department from database
 	 * @author TechFinium 
 	 * @see    Department
 	 */
	public void departmentDelete() {
		try {
			 service.delete(this.department);
			  prepareNew();
			 departmentInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of Department 
 	 * @author TechFinium 
 	 * @see    Department
 	 */
	public void prepareNew() {
		department = new Department();
	}

/*
    public List<SelectItem> getDepartmentDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	departmentInfo();
    	for (Department ug : departmentList) {
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
    public List<Department> complete(String desc) {
		List<Department> l = null;
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
    
    public List<Department> getDepartmentList() {
		return departmentList;
	}
	public void setDepartmentList(List<Department> departmentList) {
		this.departmentList = departmentList;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}

    public List<Department> getDepartmentfilteredList() {
		return departmentfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param departmentfilteredList the new departmentfilteredList list
 	 * @see    Department
 	 */	
	public void setDepartmentfilteredList(List<Department> departmentfilteredList) {
		this.departmentfilteredList = departmentfilteredList;
	}

	
	public LazyDataModel<Department> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<Department> dataModel) {
		this.dataModel = dataModel;
	}
	
}
