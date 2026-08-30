package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.StructureStatusID;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.StructureStatusIDService;

// TODO: Auto-generated Javadoc
/**
 * The Class StructureStatusIDUI.
 */
@ManagedBean(name = "structurestatusidUI")
@ViewScoped
public class StructureStatusIDUI extends AbstractUI {

	/** The service. */
	private StructureStatusIDService service = new StructureStatusIDService();
	
	/** The structurestatusid list. */
	private List<StructureStatusID> structurestatusidList = null;
	
	/** The structurestatusidfiltered list. */
	private List<StructureStatusID> structurestatusidfilteredList = null;
	
	/** The structurestatusid. */
	private StructureStatusID structurestatusid = null;
	
	/** The data model. */
	private LazyDataModel<StructureStatusID> dataModel; 

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
	 * Initialize method to get all StructureStatusID and prepare a for a create of a new StructureStatusID.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    StructureStatusID
	 */
	private void runInit() throws Exception {
		prepareNew();
		structurestatusidInfo();
	}

	/**
	 * Get all StructureStatusID for data table.
	 *
	 * @author TechFinium
	 * @see    StructureStatusID
	 */
	public void structurestatusidInfo() {
	 
			
			 dataModel = new LazyDataModel<StructureStatusID>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<StructureStatusID> retorno = new  ArrayList<StructureStatusID>();
			   
			   @Override 
			   public List<StructureStatusID> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allStructureStatusID(StructureStatusID.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(StructureStatusID.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(StructureStatusID obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public StructureStatusID getRowData(String rowKey) {
			        for(StructureStatusID obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert StructureStatusID into database.
	 *
	 * @author TechFinium
	 * @see    StructureStatusID
	 */
	public void structurestatusidInsert() {
		try {
				 service.create(this.structurestatusid);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 structurestatusidInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update StructureStatusID in database.
	 *
	 * @author TechFinium
	 * @see    StructureStatusID
	 */
    public void structurestatusidUpdate() {
		try {
				 service.update(this.structurestatusid);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 structurestatusidInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete StructureStatusID from database.
	 *
	 * @author TechFinium
	 * @see    StructureStatusID
	 */
	public void structurestatusidDelete() {
		try {
			 service.delete(this.structurestatusid);
			  prepareNew();
			 structurestatusidInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted "));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of StructureStatusID .
	 *
	 * @author TechFinium
	 * @see    StructureStatusID
	 */
	public void prepareNew() {
		structurestatusid = new StructureStatusID();
	}

/*
    public List<SelectItem> getStructureStatusIDDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	structurestatusidInfo();
    	for (StructureStatusID ug : structurestatusidList) {
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
    public List<StructureStatusID> complete(String desc) {
		List<StructureStatusID> l = null;
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
     * Gets the structure status ID list.
     *
     * @return the structure status ID list
     */
    public List<StructureStatusID> getStructureStatusIDList() {
		return structurestatusidList;
	}
	
	/**
	 * Sets the structure status ID list.
	 *
	 * @param structurestatusidList the new structure status ID list
	 */
	public void setStructureStatusIDList(List<StructureStatusID> structurestatusidList) {
		this.structurestatusidList = structurestatusidList;
	}
	
	/**
	 * Gets the structurestatusid.
	 *
	 * @return the structurestatusid
	 */
	public StructureStatusID getStructurestatusid() {
		return structurestatusid;
	}
	
	/**
	 * Sets the structurestatusid.
	 *
	 * @param structurestatusid the new structurestatusid
	 */
	public void setStructurestatusid(StructureStatusID structurestatusid) {
		this.structurestatusid = structurestatusid;
	}

    /**
     * Gets the structure status I dfiltered list.
     *
     * @return the structure status I dfiltered list
     */
    public List<StructureStatusID> getStructureStatusIDfilteredList() {
		return structurestatusidfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param structurestatusidfilteredList the new structurestatusidfilteredList list
	 * @see    StructureStatusID
	 */	
	public void setStructureStatusIDfilteredList(List<StructureStatusID> structurestatusidfilteredList) {
		this.structurestatusidfilteredList = structurestatusidfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<StructureStatusID> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<StructureStatusID> dataModel) {
		this.dataModel = dataModel;
	}
	
}
