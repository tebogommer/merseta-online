package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.OnScreenHelp;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.OnScreenHelpService;

@ManagedBean(name = "onscreenhelpUI")
@ViewScoped
public class OnScreenHelpUI extends AbstractUI {

	private OnScreenHelpService service = new OnScreenHelpService();
	private List<OnScreenHelp> onscreenhelpList = null;
	private List<OnScreenHelp> onscreenhelpfilteredList = null;
	private OnScreenHelp onscreenhelp = null;
	private LazyDataModel<OnScreenHelp> dataModel; 

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
	 * Initialize method to get all OnScreenHelp and prepare a for a create of a new OnScreenHelp
 	 * @author TechFinium 
 	 * @see    OnScreenHelp
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		onscreenhelpInfo();
	}

	/**
	 * Get all OnScreenHelp for data table
 	 * @author TechFinium 
 	 * @see    OnScreenHelp
 	 */
	public void onscreenhelpInfo() {
	 
			
			 dataModel = new LazyDataModel<OnScreenHelp>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<OnScreenHelp> retorno = new  ArrayList<OnScreenHelp>();
			   
			   @Override 
			   public List<OnScreenHelp> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allOnScreenHelp(OnScreenHelp.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(OnScreenHelp.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(OnScreenHelp obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public OnScreenHelp getRowData(String rowKey) {
			        for(OnScreenHelp obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert OnScreenHelp into database
 	 * @author TechFinium 
 	 * @see    OnScreenHelp
 	 */
	public void onscreenhelpInsert() {
		try {
			 if (onscreenhelp.getId() !=null) {
				 service.create(this.onscreenhelp);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 onscreenhelpInfo();
			 }
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update OnScreenHelp in database
 	 * @author TechFinium 
 	 * @see    OnScreenHelp
 	 */
    public void onscreenhelpUpdate() {
		try {
				 service.update(this.onscreenhelp);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 onscreenhelpInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete OnScreenHelp from database
 	 * @author TechFinium 
 	 * @see    OnScreenHelp
 	 */
	public void onscreenhelpDelete() {
		try {
			 service.delete(this.onscreenhelp);
			  prepareNew();
			 onscreenhelpInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of OnScreenHelp 
 	 * @author TechFinium 
 	 * @see    OnScreenHelp
 	 */
	public void prepareNew() {
		onscreenhelp = new OnScreenHelp();
	}

/*
    public List<SelectItem> getOnScreenHelpDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	onscreenhelpInfo();
    	for (OnScreenHelp ug : onscreenhelpList) {
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
    public List<OnScreenHelp> complete(String desc) {
		List<OnScreenHelp> l = null;
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
    
    public List<OnScreenHelp> getOnScreenHelpList() {
		return onscreenhelpList;
	}
	public void setOnScreenHelpList(List<OnScreenHelp> onscreenhelpList) {
		this.onscreenhelpList = onscreenhelpList;
	}
	public OnScreenHelp getOnscreenhelp() {
		return onscreenhelp;
	}
	public void setOnscreenhelp(OnScreenHelp onscreenhelp) {
		this.onscreenhelp = onscreenhelp;
	}

    public List<OnScreenHelp> getOnScreenHelpfilteredList() {
		return onscreenhelpfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param onscreenhelpfilteredList the new onscreenhelpfilteredList list
 	 * @see    OnScreenHelp
 	 */	
	public void setOnScreenHelpfilteredList(List<OnScreenHelp> onscreenhelpfilteredList) {
		this.onscreenhelpfilteredList = onscreenhelpfilteredList;
	}

	
	public LazyDataModel<OnScreenHelp> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<OnScreenHelp> dataModel) {
		this.dataModel = dataModel;
	}
	
}
