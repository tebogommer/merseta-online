package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.FLC;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.FLCService;

// TODO: Auto-generated Javadoc
/**
 * The Class FLCUI.
 */
@ManagedBean(name = "flcUI")
@ViewScoped
public class FLCUI extends AbstractUI {

	/** The service. */
	private FLCService service = new FLCService();
	
	/** The flc list. */
	private List<FLC> flcList = null;
	
	/** The flcfiltered list. */
	private List<FLC> flcfilteredList = null;
	
	/** The flc. */
	private FLC flc = null;
	
	/** The data model. */
	private LazyDataModel<FLC> dataModel; 

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
	 * Initialize method to get all FLC and prepare a for a create of a new FLC.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    FLC
	 */
	private void runInit() throws Exception {
		prepareNew();
		flcInfo();
	}

	/**
	 * Get all FLC for data table.
	 *
	 * @author TechFinium
	 * @see    FLC
	 */
	public void flcInfo() {
	 
			
			 dataModel = new LazyDataModel<FLC>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<FLC> retorno = new  ArrayList<FLC>();
			   
			   @Override 
			   public List<FLC> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allFLC(FLC.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(FLC.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(FLC obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public FLC getRowData(String rowKey) {
			        for(FLC obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert FLC into database.
	 *
	 * @author TechFinium
	 * @see    FLC
	 */
	public void flcInsert() {
		try {
				 service.create(this.flc);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 flcInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update FLC in database.
	 *
	 * @author TechFinium
	 * @see    FLC
	 */
    public void flcUpdate() {
		try {
				 service.update(this.flc);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 flcInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete FLC from database.
	 *
	 * @author TechFinium
	 * @see    FLC
	 */
	public void flcDelete() {
		try {
			 service.delete(this.flc);
			  prepareNew();
			 flcInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted "));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of FLC .
	 *
	 * @author TechFinium
	 * @see    FLC
	 */
	public void prepareNew() {
		flc = new FLC();
	}

/*
    public List<SelectItem> getFLCDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	flcInfo();
    	for (FLC ug : flcList) {
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
    public List<FLC> complete(String desc) {
		List<FLC> l = null;
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
     * Gets the FLC list.
     *
     * @return the FLC list
     */
    public List<FLC> getFLCList() {
		return flcList;
	}
	
	/**
	 * Sets the FLC list.
	 *
	 * @param flcList the new FLC list
	 */
	public void setFLCList(List<FLC> flcList) {
		this.flcList = flcList;
	}
	
	/**
	 * Gets the flc.
	 *
	 * @return the flc
	 */
	public FLC getFlc() {
		return flc;
	}
	
	/**
	 * Sets the flc.
	 *
	 * @param flc the new flc
	 */
	public void setFlc(FLC flc) {
		this.flc = flc;
	}

    /**
     * Gets the FL cfiltered list.
     *
     * @return the FL cfiltered list
     */
    public List<FLC> getFLCfilteredList() {
		return flcfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param flcfilteredList the new flcfilteredList list
	 * @see    FLC
	 */	
	public void setFLCfilteredList(List<FLC> flcfilteredList) {
		this.flcfilteredList = flcfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<FLC> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<FLC> dataModel) {
		this.dataModel = dataModel;
	}
	
}
