package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.SaqaUnitstandard;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.SaqaUnitstandardService;

// TODO: Auto-generated Javadoc
/**
 * The Class SaqaUnitstandardUI.
 */
@ManagedBean(name = "saqaunitstandardUI")
@ViewScoped
public class SaqaUnitstandardUI extends AbstractUI {

	/** The service. */
	private SaqaUnitstandardService service = new SaqaUnitstandardService();
	
	/** The saqaunitstandard list. */
	private List<SaqaUnitstandard> saqaunitstandardList = null;
	
	/** The saqaunitstandardfiltered list. */
	private List<SaqaUnitstandard> saqaunitstandardfilteredList = null;
	
	/** The saqaunitstandard. */
	private SaqaUnitstandard saqaunitstandard = null;
	
	/** The data model. */
	private LazyDataModel<SaqaUnitstandard> dataModel; 

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
	 * Initialize method to get all SaqaUnitstandard and prepare a for a create of a new SaqaUnitstandard.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    SaqaUnitstandard
	 */
	private void runInit() throws Exception {
		prepareNew();
		saqaunitstandardInfo();
	}

	/**
	 * Get all SaqaUnitstandard for data table.
	 *
	 * @author TechFinium
	 * @see    SaqaUnitstandard
	 */
	public void saqaunitstandardInfo() {
	 
			
			 dataModel = new LazyDataModel<SaqaUnitstandard>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<SaqaUnitstandard> retorno = new  ArrayList<SaqaUnitstandard>();
			   
			   @Override 
			   public List<SaqaUnitstandard> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allSaqaUnitstandard(SaqaUnitstandard.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(SaqaUnitstandard.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(SaqaUnitstandard obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public SaqaUnitstandard getRowData(String rowKey) {
			        for(SaqaUnitstandard obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert SaqaUnitstandard into database.
	 *
	 * @author TechFinium
	 * @see    SaqaUnitstandard
	 */
	public void saqaunitstandardInsert() {
		try {
				 service.create(this.saqaunitstandard);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 saqaunitstandardInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update SaqaUnitstandard in database.
	 *
	 * @author TechFinium
	 * @see    SaqaUnitstandard
	 */
    public void saqaunitstandardUpdate() {
		try {
				 service.update(this.saqaunitstandard);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 saqaunitstandardInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete SaqaUnitstandard from database.
	 *
	 * @author TechFinium
	 * @see    SaqaUnitstandard
	 */
	public void saqaunitstandardDelete() {
		try {
			 service.delete(this.saqaunitstandard);
			  prepareNew();
			 saqaunitstandardInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted "));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of SaqaUnitstandard .
	 *
	 * @author TechFinium
	 * @see    SaqaUnitstandard
	 */
	public void prepareNew() {
		saqaunitstandard = new SaqaUnitstandard();
	}

/*
    public List<SelectItem> getSaqaUnitstandardDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	saqaunitstandardInfo();
    	for (SaqaUnitstandard ug : saqaunitstandardList) {
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
    public List<SaqaUnitstandard> complete(String desc) {
		List<SaqaUnitstandard> l = null;
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
     * Gets the saqa unitstandard list.
     *
     * @return the saqa unitstandard list
     */
    public List<SaqaUnitstandard> getSaqaUnitstandardList() {
		return saqaunitstandardList;
	}
	
	/**
	 * Sets the saqa unitstandard list.
	 *
	 * @param saqaunitstandardList the new saqa unitstandard list
	 */
	public void setSaqaUnitstandardList(List<SaqaUnitstandard> saqaunitstandardList) {
		this.saqaunitstandardList = saqaunitstandardList;
	}
	
	/**
	 * Gets the saqaunitstandard.
	 *
	 * @return the saqaunitstandard
	 */
	public SaqaUnitstandard getSaqaunitstandard() {
		return saqaunitstandard;
	}
	
	/**
	 * Sets the saqaunitstandard.
	 *
	 * @param saqaunitstandard the new saqaunitstandard
	 */
	public void setSaqaunitstandard(SaqaUnitstandard saqaunitstandard) {
		this.saqaunitstandard = saqaunitstandard;
	}

    /**
     * Gets the saqa unitstandardfiltered list.
     *
     * @return the saqa unitstandardfiltered list
     */
    public List<SaqaUnitstandard> getSaqaUnitstandardfilteredList() {
		return saqaunitstandardfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param saqaunitstandardfilteredList the new saqaunitstandardfilteredList list
	 * @see    SaqaUnitstandard
	 */	
	public void setSaqaUnitstandardfilteredList(List<SaqaUnitstandard> saqaunitstandardfilteredList) {
		this.saqaunitstandardfilteredList = saqaunitstandardfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<SaqaUnitstandard> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<SaqaUnitstandard> dataModel) {
		this.dataModel = dataModel;
	}
	
}
