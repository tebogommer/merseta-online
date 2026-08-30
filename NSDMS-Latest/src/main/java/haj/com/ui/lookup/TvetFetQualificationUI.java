package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.TvetFetQualification;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.TvetFetQualificationService;

// TODO: Auto-generated Javadoc
/**
 * The Class TvetFetQualificationUI.
 */
@ManagedBean(name = "tvetFetQualificationUI")
@ViewScoped
public class TvetFetQualificationUI extends AbstractUI {

	/** The service. */
	private TvetFetQualificationService service = new TvetFetQualificationService();
	
	/** The tvetFetQualificationUI list. */
	private List<TvetFetQualification> tvetFetQualificationUIList = null;
	
	/** The tvetFetQualificationUIfiltered list. */
	private List<TvetFetQualification> tvetFetQualificationUIfilteredList = null;
	
	/** The tvetFetQualificationUI. */
	private TvetFetQualification tvetFetQualification = null;
	
	/** The data model. */
	private LazyDataModel<TvetFetQualification> dataModel; 

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
	 * Initialize method to get all TvetFetQualification and prepare a for a create of a new TvetFetQualification.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    TvetFetQualification
	 */
	private void runInit() throws Exception {
		prepareNew();
		tvetFetQualificationUIInfo();
	}

	/**
	 * Get all TvetFetQualification for data table.
	 *
	 * @author TechFinium
	 * @see    TvetFetQualification
	 */
	public void tvetFetQualificationUIInfo() {
	 
			
			 dataModel = new LazyDataModel<TvetFetQualification>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<TvetFetQualification> retorno = new  ArrayList<TvetFetQualification>();
			   
			   @Override 
			   public List<TvetFetQualification> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allTvetFetQualification(TvetFetQualification.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(TvetFetQualification.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(TvetFetQualification obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public TvetFetQualification getRowData(String rowKey) {
			        for(TvetFetQualification obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert TvetFetQualification into database.
	 *
	 * @author TechFinium
	 * @see    TvetFetQualification
	 */
	public void tvetFetQualificationUIInsert() {
		try {
				 service.create(this.tvetFetQualification);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 tvetFetQualificationUIInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update TvetFetQualification in database.
	 *
	 * @author TechFinium
	 * @see    TvetFetQualification
	 */
    public void tvetFetQualificationUIUpdate() {
		try{
			 service.update(this.tvetFetQualification);
			 addInfoMessage(super.getEntryLanguage("update.successful"));
			 tvetFetQualificationUIInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete TvetFetQualification from database.
	 *
	 * @author TechFinium
	 * @see    TvetFetQualification
	 */
	public void tvetFetQualificationUIDelete() {
		try {
			 service.delete(this.tvetFetQualification);
			  prepareNew();
			 tvetFetQualificationUIInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted "));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of TvetFetQualification .
	 *
	 * @author TechFinium
	 * @see    TvetFetQualification
	 */
	public void prepareNew() {
		tvetFetQualification= new TvetFetQualification();
	}

/*
    public List<SelectItem> getTvetFetQualificationDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	tvetFetQualificationUIInfo();
    	for (TvetFetQualification ug : tvetFetQualificationUIList) {
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
    public List<TvetFetQualification> complete(String desc) {
		List<TvetFetQualification> l = null;
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
     * Gets the tvetFetQualificationUI list.
     *
     * @return the tvetFetQualificationUI list
     */
    public List<TvetFetQualification> getTvetFetQualificationList() {
		return tvetFetQualificationUIList;
	}
	
	/**
	 * Sets the tvetFetQualificationUI list.
	 *
	 * @param tvetFetQualificationUIList the new tvetFetQualificationUI list
	 */
	public void setTvetFetQualificationList(List<TvetFetQualification> tvetFetQualificationUIList) {
		this.tvetFetQualificationUIList = tvetFetQualificationUIList;
	}
	
	/**
	 * Gets the tvetFetQualificationUI.
	 *
	 * @return the tvetFetQualificationUI
	 */
	public TvetFetQualification getTvetFetQualification() {
		return tvetFetQualification;
	}
	
	/**
	 * Sets the tvetFetQualificationUI.
	 *
	 * @param tvetFetQualificationUI the new tvetFetQualificationUI
	 */
	public void setTvetFetQualification(TvetFetQualification tvetFetQualification) {
		this.tvetFetQualification = tvetFetQualification;
	}

    /**
     * Gets the tvetFetQualificationUIfiltered list.
     *
     * @return the tvetFetQualificationUIfiltered list
     */
    public List<TvetFetQualification> getTvetFetQualificationfilteredList() {
		return tvetFetQualificationUIfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param tvetFetQualificationUIfilteredList the new tvetFetQualificationUIfilteredList list
	 * @see    TvetFetQualification
	 */	
	public void setTvetFetQualificationfilteredList(List<TvetFetQualification> tvetFetQualificationUIfilteredList) {
		this.tvetFetQualificationUIfilteredList = tvetFetQualificationUIfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<TvetFetQualification> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<TvetFetQualification> dataModel) {
		this.dataModel = dataModel;
	}
	
}
