package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.ScarcityReason;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.ScarcityReasonService;

// TODO: Auto-generated Javadoc
/**
 * The Class ScarcityReasonUI.
 */
@ManagedBean(name = "scarcityreasonUI")
@ViewScoped
public class ScarcityReasonUI extends AbstractUI {

	/** The service. */
	private ScarcityReasonService service = new ScarcityReasonService();
	
	/** The scarcityreason list. */
	private List<ScarcityReason> scarcityreasonList = null;
	
	/** The scarcityreasonfiltered list. */
	private List<ScarcityReason> scarcityreasonfilteredList = null;
	
	/** The scarcityreason. */
	private ScarcityReason scarcityreason = null;
	
	/** The data model. */
	private LazyDataModel<ScarcityReason> dataModel; 

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
	 * Initialize method to get all ScarcityReason and prepare a for a create of a new ScarcityReason.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    ScarcityReason
	 */
	private void runInit() throws Exception {
		prepareNew();
		scarcityreasonInfo();
	}

	/**
	 * Get all ScarcityReason for data table.
	 *
	 * @author TechFinium
	 * @see    ScarcityReason
	 */
	public void scarcityreasonInfo() {
	 
			
			 dataModel = new LazyDataModel<ScarcityReason>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<ScarcityReason> retorno = new  ArrayList<ScarcityReason>();
			   
			   @Override 
			   public List<ScarcityReason> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allScarcityReason(ScarcityReason.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(ScarcityReason.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(ScarcityReason obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public ScarcityReason getRowData(String rowKey) {
			        for(ScarcityReason obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert ScarcityReason into database.
	 *
	 * @author TechFinium
	 * @see    ScarcityReason
	 */
	public void scarcityreasonInsert() {
		try {
				 service.create(this.scarcityreason);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 scarcityreasonInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update ScarcityReason in database.
	 *
	 * @author TechFinium
	 * @see    ScarcityReason
	 */
    public void scarcityreasonUpdate() {
		try {
				 service.update(this.scarcityreason);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 scarcityreasonInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete ScarcityReason from database.
	 *
	 * @author TechFinium
	 * @see    ScarcityReason
	 */
	public void scarcityreasonDelete() {
		try {
			 service.delete(this.scarcityreason);
			  prepareNew();
			 scarcityreasonInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of ScarcityReason .
	 *
	 * @author TechFinium
	 * @see    ScarcityReason
	 */
	public void prepareNew() {
		scarcityreason = new ScarcityReason();
	}

/*
    public List<SelectItem> getScarcityReasonDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	scarcityreasonInfo();
    	for (ScarcityReason ug : scarcityreasonList) {
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
    public List<ScarcityReason> complete(String desc) {
		List<ScarcityReason> l = null;
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
     * Gets the scarcity reason list.
     *
     * @return the scarcity reason list
     */
    public List<ScarcityReason> getScarcityReasonList() {
		return scarcityreasonList;
	}
	
	/**
	 * Sets the scarcity reason list.
	 *
	 * @param scarcityreasonList the new scarcity reason list
	 */
	public void setScarcityReasonList(List<ScarcityReason> scarcityreasonList) {
		this.scarcityreasonList = scarcityreasonList;
	}
	
	/**
	 * Gets the scarcityreason.
	 *
	 * @return the scarcityreason
	 */
	public ScarcityReason getScarcityreason() {
		return scarcityreason;
	}
	
	/**
	 * Sets the scarcityreason.
	 *
	 * @param scarcityreason the new scarcityreason
	 */
	public void setScarcityreason(ScarcityReason scarcityreason) {
		this.scarcityreason = scarcityreason;
	}

    /**
     * Gets the scarcity reasonfiltered list.
     *
     * @return the scarcity reasonfiltered list
     */
    public List<ScarcityReason> getScarcityReasonfilteredList() {
		return scarcityreasonfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param scarcityreasonfilteredList the new scarcityreasonfilteredList list
	 * @see    ScarcityReason
	 */	
	public void setScarcityReasonfilteredList(List<ScarcityReason> scarcityreasonfilteredList) {
		this.scarcityreasonfilteredList = scarcityreasonfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<ScarcityReason> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<ScarcityReason> dataModel) {
		this.dataModel = dataModel;
	}
	
}
