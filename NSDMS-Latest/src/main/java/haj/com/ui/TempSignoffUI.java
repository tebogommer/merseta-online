package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.TempSignoff;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.TempSignoffService;

@ManagedBean(name = "tempsignoffUI")
@ViewScoped
public class TempSignoffUI extends AbstractUI {

	private TempSignoffService service = new TempSignoffService();
	private List<TempSignoff> tempsignoffList = null;
	private List<TempSignoff> tempsignofffilteredList = null;
	private TempSignoff tempsignoff = null;
	private LazyDataModel<TempSignoff> dataModel; 

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
	 * Initialize method to get all TempSignoff and prepare a for a create of a new TempSignoff
 	 * @author TechFinium 
 	 * @see    TempSignoff
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		tempsignoffInfo();
	}

	/**
	 * Get all TempSignoff for data table
 	 * @author TechFinium 
 	 * @see    TempSignoff
 	 */
	public void tempsignoffInfo() {
	 
			
			 dataModel = new LazyDataModel<TempSignoff>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<TempSignoff> retorno = new  ArrayList<TempSignoff>();
			   
			   @Override 
			   public List<TempSignoff> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allTempSignoff(TempSignoff.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(TempSignoff.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(TempSignoff obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public TempSignoff getRowData(String rowKey) {
			        for(TempSignoff obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert TempSignoff into database
 	 * @author TechFinium 
 	 * @see    TempSignoff
 	 */
	public void tempsignoffInsert() {
		try {
				 service.create(this.tempsignoff);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 tempsignoffInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update TempSignoff in database
 	 * @author TechFinium 
 	 * @see    TempSignoff
 	 */
    public void tempsignoffUpdate() {
		try {
				 service.update(this.tempsignoff);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 tempsignoffInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete TempSignoff from database
 	 * @author TechFinium 
 	 * @see    TempSignoff
 	 */
	public void tempsignoffDelete() {
		try {
			 service.delete(this.tempsignoff);
			  prepareNew();
			 tempsignoffInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of TempSignoff 
 	 * @author TechFinium 
 	 * @see    TempSignoff
 	 */
	public void prepareNew() {
		tempsignoff = new TempSignoff();
	}

/*
    public List<SelectItem> getTempSignoffDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	tempsignoffInfo();
    	for (TempSignoff ug : tempsignoffList) {
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
    public List<TempSignoff> complete(String desc) {
		List<TempSignoff> l = null;
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
    
    public List<TempSignoff> getTempSignoffList() {
		return tempsignoffList;
	}
	public void setTempSignoffList(List<TempSignoff> tempsignoffList) {
		this.tempsignoffList = tempsignoffList;
	}
	public TempSignoff getTempsignoff() {
		return tempsignoff;
	}
	public void setTempsignoff(TempSignoff tempsignoff) {
		this.tempsignoff = tempsignoff;
	}

    public List<TempSignoff> getTempSignofffilteredList() {
		return tempsignofffilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param tempsignofffilteredList the new tempsignofffilteredList list
 	 * @see    TempSignoff
 	 */	
	public void setTempSignofffilteredList(List<TempSignoff> tempsignofffilteredList) {
		this.tempsignofffilteredList = tempsignofffilteredList;
	}

	
	public LazyDataModel<TempSignoff> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<TempSignoff> dataModel) {
		this.dataModel = dataModel;
	}
	
}
