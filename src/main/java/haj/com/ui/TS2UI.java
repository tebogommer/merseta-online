package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.datatakeon.TS2;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.TS2Service;

// TODO: Auto-generated Javadoc
/**
 * The Class TS2UI.
 */
@ManagedBean(name = "ts2UI")
@ViewScoped
public class TS2UI extends AbstractUI {

	/** The service. */
	private TS2Service service = new TS2Service();
	
	/** The ts 2 list. */
	private List<TS2> ts2List = null;
	
	/** The ts 2 filtered list. */
	private List<TS2> ts2filteredList = null;
	
	/** The ts 2. */
	private TS2 ts2 = null;
	
	/** The data model. */
	private LazyDataModel<TS2> dataModel; 

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
	 * Initialize method to get all TS2 and prepare a for a create of a new TS2.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    TS2
	 */
	private void runInit() throws Exception {
		prepareNew();
		ts2Info();
	}

	/**
	 * Get all TS2 for data table.
	 *
	 * @author TechFinium
	 * @see    TS2
	 */
	public void ts2Info() {
	 
			
			 dataModel = new LazyDataModel<TS2>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<TS2> retorno = new  ArrayList<TS2>();
			   
			   @Override 
			   public List<TS2> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allTS2(TS2.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(TS2.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(TS2 obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public TS2 getRowData(String rowKey) {
			        for(TS2 obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert TS2 into database.
	 *
	 * @author TechFinium
	 * @see    TS2
	 */
	public void ts2Insert() {
		try {
				 service.create(this.ts2);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 ts2Info();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update TS2 in database.
	 *
	 * @author TechFinium
	 * @see    TS2
	 */
    public void ts2Update() {
		try {
				 service.update(this.ts2);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 ts2Info();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete TS2 from database.
	 *
	 * @author TechFinium
	 * @see    TS2
	 */
	public void ts2Delete() {
		try {
			 service.delete(this.ts2);
			  prepareNew();
			 ts2Info();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of TS2 .
	 *
	 * @author TechFinium
	 * @see    TS2
	 */
	public void prepareNew() {
		ts2 = new TS2();
	}

/*
    public List<SelectItem> getTS2DD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	ts2Info();
    	for (TS2 ug : ts2List) {
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
    public List<TS2> complete(String desc) {
		List<TS2> l = null;
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
     * Gets the TS 2 list.
     *
     * @return the TS 2 list
     */
    public List<TS2> getTS2List() {
		return ts2List;
	}
	
	/**
	 * Sets the TS 2 list.
	 *
	 * @param ts2List the new TS 2 list
	 */
	public void setTS2List(List<TS2> ts2List) {
		this.ts2List = ts2List;
	}
	
	/**
	 * Gets the ts 2.
	 *
	 * @return the ts 2
	 */
	public TS2 getTs2() {
		return ts2;
	}
	
	/**
	 * Sets the ts 2.
	 *
	 * @param ts2 the new ts 2
	 */
	public void setTs2(TS2 ts2) {
		this.ts2 = ts2;
	}

    /**
     * Gets the TS 2 filtered list.
     *
     * @return the TS 2 filtered list
     */
    public List<TS2> getTS2filteredList() {
		return ts2filteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param ts2filteredList the new ts2filteredList list
	 * @see    TS2
	 */	
	public void setTS2filteredList(List<TS2> ts2filteredList) {
		this.ts2filteredList = ts2filteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<TS2> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<TS2> dataModel) {
		this.dataModel = dataModel;
	}
	
}
