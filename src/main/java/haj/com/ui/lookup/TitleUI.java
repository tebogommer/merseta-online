package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.Title;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.TitleService;

// TODO: Auto-generated Javadoc
/**
 * The Class TitleUI.
 */
@ManagedBean(name = "titleUI")
@ViewScoped
public class TitleUI extends AbstractUI {

	/** The service. */
	private TitleService service = new TitleService();
	
	/** The title list. */
	private List<Title> titleList = null;
	
	/** The titlefiltered list. */
	private List<Title> titlefilteredList = null;
	
	/** The title. */
	private Title title = null;
	
	/** The data model. */
	private LazyDataModel<Title> dataModel; 

    /**
     * Inits the.
     */
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
	 * Initialize method to get all Title and prepare a for a create of a new Title.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    Title
	 */
	private void runInit() throws Exception {
		prepareNew();
		titleInfo();
	}

	/**
	 * Get all Title for data table.
	 *
	 * @author TechFinium
	 * @see    Title
	 */
	public void titleInfo() {
	 
			
			 dataModel = new LazyDataModel<Title>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<Title> retorno = new  ArrayList<Title>();
			   
			   @Override 
			   public List<Title> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allTitle(Title.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(Title.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(Title obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public Title getRowData(String rowKey) {
			        for(Title obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert Title into database.
	 *
	 * @author TechFinium
	 * @see    Title
	 */
	public void titleInsert() {
		try {
				 service.create(this.title);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 titleInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update Title in database.
	 *
	 * @author TechFinium
	 * @see    Title
	 */
    public void titleUpdate() {
		try {
				 service.update(this.title);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 titleInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete Title from database.
	 *
	 * @author TechFinium
	 * @see    Title
	 */
	public void titleDelete() {
		try {
			 service.delete(this.title);
			  prepareNew();
			 titleInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted "));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of Title .
	 *
	 * @author TechFinium
	 * @see    Title
	 */
	public void prepareNew() {
		title = new Title();
	}

/*
    public List<SelectItem> getTitleDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	titleInfo();
    	for (Title ug : titleList) {
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
    public List<Title> complete(String desc) {
		List<Title> l = null;
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
    
    /**
     * Gets the title list.
     *
     * @return the title list
     */
    public List<Title> getTitleList() {
		return titleList;
	}
	
	/**
	 * Sets the title list.
	 *
	 * @param titleList the new title list
	 */
	public void setTitleList(List<Title> titleList) {
		this.titleList = titleList;
	}
	
	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public Title getTitle() {
		return title;
	}
	
	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(Title title) {
		this.title = title;
	}

    /**
     * Gets the titlefiltered list.
     *
     * @return the titlefiltered list
     */
    public List<Title> getTitlefilteredList() {
		return titlefilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param titlefilteredList the new titlefilteredList list
	 * @see    Title
	 */	
	public void setTitlefilteredList(List<Title> titlefilteredList) {
		this.titlefilteredList = titlefilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<Title> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<Title> dataModel) {
		this.dataModel = dataModel;
	}
	
}
