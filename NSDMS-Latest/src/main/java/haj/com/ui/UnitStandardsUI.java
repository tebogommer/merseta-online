package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.UnitStandards;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.UnitStandardsService;

// TODO: Auto-generated Javadoc
/**
 * The Class UnitStandardsUI.
 */
@ManagedBean(name = "unitstandardsUI")
@ViewScoped
public class UnitStandardsUI extends AbstractUI {

	/** The service. */
	private UnitStandardsService service = new UnitStandardsService();
	
	/** The unitstandards list. */
	private List<UnitStandards> unitstandardsList = null;
	
	/** The unitstandardsfiltered list. */
	private List<UnitStandards> unitstandardsfilteredList = null;
	
	/** The unitstandards. */
	private UnitStandards unitstandards = null;
	
	/** The data model. */
	private LazyDataModel<UnitStandards> dataModel; 

    /**
     * Inits the.
     */
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
	 * Initialize method to get all UnitStandards and prepare a for a create of a new UnitStandards.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    UnitStandards
	 */
	private void runInit() throws Exception {
		prepareNew();
		unitstandardsInfo();
	}

	/**
	 * Get all UnitStandards for data table.
	 *
	 * @author TechFinium
	 * @see    UnitStandards
	 */
	public void unitstandardsInfo() {
	 
			
			 dataModel = new LazyDataModel<UnitStandards>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<UnitStandards> retorno = new  ArrayList<UnitStandards>();
			   
			   @Override 
			   public List<UnitStandards> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allUnitStandards(UnitStandards.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(UnitStandards.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(UnitStandards obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public UnitStandards getRowData(String rowKey) {
			        for(UnitStandards obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert UnitStandards into database.
	 *
	 * @author TechFinium
	 * @see    UnitStandards
	 */
	public void unitstandardsInsert() {
		try {
				 service.create(this.unitstandards);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 unitstandardsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update UnitStandards in database.
	 *
	 * @author TechFinium
	 * @see    UnitStandards
	 */
    public void unitstandardsUpdate() {
		try {
				 service.update(this.unitstandards);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 unitstandardsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete UnitStandards from database.
	 *
	 * @author TechFinium
	 * @see    UnitStandards
	 */
	public void unitstandardsDelete() {
		try {
			 service.delete(this.unitstandards);
			  prepareNew();
			 unitstandardsInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of UnitStandards .
	 *
	 * @author TechFinium
	 * @see    UnitStandards
	 */
	public void prepareNew() {
		unitstandards = new UnitStandards();
	}

/*
    public List<SelectItem> getUnitStandardsDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	unitstandardsInfo();
    	for (UnitStandards ug : unitstandardsList) {
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
    public List<UnitStandards> complete(String desc) {
		List<UnitStandards> l = null;
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
    
    /**
     * Gets the unit standards list.
     *
     * @return the unit standards list
     */
    public List<UnitStandards> getUnitStandardsList() {
		return unitstandardsList;
	}
	
	/**
	 * Sets the unit standards list.
	 *
	 * @param unitstandardsList the new unit standards list
	 */
	public void setUnitStandardsList(List<UnitStandards> unitstandardsList) {
		this.unitstandardsList = unitstandardsList;
	}
	
	/**
	 * Gets the unitstandards.
	 *
	 * @return the unitstandards
	 */
	public UnitStandards getUnitstandards() {
		return unitstandards;
	}
	
	/**
	 * Sets the unitstandards.
	 *
	 * @param unitstandards the new unitstandards
	 */
	public void setUnitstandards(UnitStandards unitstandards) {
		this.unitstandards = unitstandards;
	}

    /**
     * Gets the unit standardsfiltered list.
     *
     * @return the unit standardsfiltered list
     */
    public List<UnitStandards> getUnitStandardsfilteredList() {
		return unitstandardsfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param unitstandardsfilteredList the new unitstandardsfilteredList list
	 * @see    UnitStandards
	 */	
	public void setUnitStandardsfilteredList(List<UnitStandards> unitstandardsfilteredList) {
		this.unitstandardsfilteredList = unitstandardsfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<UnitStandards> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<UnitStandards> dataModel) {
		this.dataModel = dataModel;
	}
	
}
