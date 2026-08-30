package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.Tool;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.ToolService;

@ManagedBean(name = "toolUI")
@ViewScoped
public class ToolUI extends AbstractUI {

	private ToolService service = new ToolService();
	private List<Tool> toolList = null;
	private List<Tool> toolfilteredList = null;
	private Tool tool = null;
	private LazyDataModel<Tool> dataModel; 

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
	 * Initialize method to get all Tool and prepare a for a create of a new Tool
 	 * @author TechFinium 
 	 * @see    Tool
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		toolInfo();
	}

	/**
	 * Get all Tool for data table
 	 * @author TechFinium 
 	 * @see    Tool
 	 */
	public void toolInfo() {
	 
			
			 dataModel = new LazyDataModel<Tool>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<Tool> retorno = new  ArrayList<Tool>();
			   
			   @Override 
			   public List<Tool> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allTool(Tool.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(Tool.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(Tool obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public Tool getRowData(String rowKey) {
			        for(Tool obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert Tool into database
 	 * @author TechFinium 
 	 * @see    Tool
 	 */
	public void toolInsert() {
		try {
				 service.create(this.tool);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 toolInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update Tool in database
 	 * @author TechFinium 
 	 * @see    Tool
 	 */
    public void toolUpdate() {
		try {
				 service.update(this.tool);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 toolInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete Tool from database
 	 * @author TechFinium 
 	 * @see    Tool
 	 */
	public void toolDelete() {
		try {
			 service.delete(this.tool);
			  prepareNew();
			 toolInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of Tool 
 	 * @author TechFinium 
 	 * @see    Tool
 	 */
	public void prepareNew() {
		tool = new Tool();
	}

/*
    public List<SelectItem> getToolDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	toolInfo();
    	for (Tool ug : toolList) {
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
    public List<Tool> complete(String desc) {
		List<Tool> l = null;
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
    
    public List<Tool> getToolList() {
		return toolList;
	}
	public void setToolList(List<Tool> toolList) {
		this.toolList = toolList;
	}
	public Tool getTool() {
		return tool;
	}
	public void setTool(Tool tool) {
		this.tool = tool;
	}

    public List<Tool> getToolfilteredList() {
		return toolfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param toolfilteredList the new toolfilteredList list
 	 * @see    Tool
 	 */	
	public void setToolfilteredList(List<Tool> toolfilteredList) {
		this.toolfilteredList = toolfilteredList;
	}

	
	public LazyDataModel<Tool> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<Tool> dataModel) {
		this.dataModel = dataModel;
	}
	
}
