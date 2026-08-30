package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.datatakeon.TS1;
import haj.com.entity.datatakeon.TS2;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.TS1Service;
import haj.com.service.TS2Service;

// TODO: Auto-generated Javadoc
/**
 * The Class TS1UI.
 */
@ManagedBean(name = "ts1UI")
@ViewScoped
public class TS1UI extends AbstractUI {

	/** The service. */
	private TS1Service service = new TS1Service();
	
	/** The ts 1 list. */
	private List<TS1> ts1List = null;
	
	/** The ts 1 filtered list. */
	private List<TS1> ts1filteredList = null;
	
	/** The ts 1. */
	private TS1 ts1 = null;
	
	/** The ts 2 service. */
	private TS2Service ts2service = new TS2Service();
	
	/** The ts 2 list. */
	private List<TS2> ts2List = null;
	
	/** The ts 2 filtered list. */
	private List<TS2> ts2filteredList = null;
	
	/** The data model. */
	private LazyDataModel<TS1> dataModel; 
	
	/** The total. */
	private Double total;
	
	/** The totalctx. */
	private Double totalctx;
    
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
	 * Initialize method to get all TS1 and prepare a for a create of a new TS1.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    TS1
	 */
	private void runInit() throws Exception {
		prepareNew();
		ts1Info();
	}

	/**
	 * Get all TS1 for data table.
	 *
	 * @author TechFinium
	 * @see    TS1
	 */
	public void ts1Info() {
	 
			
			 dataModel = new LazyDataModel<TS1>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<TS1> retorno = new  ArrayList<TS1>();
			   
			   @Override 
			   public List<TS1> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					
					retorno = service.allTS1(TS1.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(TS1.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
					e.printStackTrace();
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(TS1 obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public TS1 getRowData(String rowKey) {
			        for(TS1 obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert TS1 into database.
	 *
	 * @author TechFinium
	 * @see    TS1
	 */
	public void ts1Insert() {
		try {
				 service.create(this.ts1);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 ts1Info();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update TS1 in database.
	 *
	 * @author TechFinium
	 * @see    TS1
	 */
    public void ts1Update() {
		try {
				 service.update(this.ts1);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 ts1Info();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete TS1 from database.
	 *
	 * @author TechFinium
	 * @see    TS1
	 */
	public void ts1Delete() {
		try {
			 service.delete(this.ts1);
			  prepareNew();
			 ts1Info();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of TS1 .
	 *
	 * @author TechFinium
	 * @see    TS1
	 */
	public void prepareNew() {
		ts1 = new TS1();
	}

/*
    public List<SelectItem> getTS1DD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	ts1Info();
    	for (TS1 ug : ts1List) {
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
    public List<TS1> complete(String desc) {
		List<TS1> l = null;
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
     * Gets the TS 1 list.
     *
     * @return the TS 1 list
     */
    public List<TS1> getTS1List() {
		return ts1List;
	}
	
	/**
	 * Sets the TS 1 list.
	 *
	 * @param ts1List the new TS 1 list
	 */
	public void setTS1List(List<TS1> ts1List) {
		this.ts1List = ts1List;
	}
	
	/**
	 * Gets the ts 1.
	 *
	 * @return the ts 1
	 */
	public TS1 getTs1() {
		return ts1;
	}
	
	/**
	 * Sets the ts 1.
	 *
	 * @param ts1 the new ts 1
	 */
	public void setTs1(TS1 ts1) {
		this.ts1 = ts1;
	}

    /**
     * Gets the TS 1 filtered list.
     *
     * @return the TS 1 filtered list
     */
    public List<TS1> getTS1filteredList() {
		return ts1filteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param ts1filteredList the new ts1filteredList list
	 * @see    TS1
	 */	
	public void setTS1filteredList(List<TS1> ts1filteredList) {
		this.ts1filteredList = ts1filteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<TS1> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<TS1> dataModel) {
		this.dataModel = dataModel;
	}

	/**
	 * Gets the ts 1 list.
	 *
	 * @return the ts 1 list
	 */
	public List<TS1> getTs1List() {
		return ts1List;
	}

	/**
	 * Sets the ts 1 list.
	 *
	 * @param ts1List the new ts 1 list
	 */
	public void setTs1List(List<TS1> ts1List) {
		this.ts1List = ts1List;
	}

	/**
	 * Gets the ts 1 filtered list.
	 *
	 * @return the ts 1 filtered list
	 */
	public List<TS1> getTs1filteredList() {
		return ts1filteredList;
	}

	/**
	 * Sets the ts 1 filtered list.
	 *
	 * @param ts1filteredList the new ts 1 filtered list
	 */
	public void setTs1filteredList(List<TS1> ts1filteredList) {
		this.ts1filteredList = ts1filteredList;
	}

	/**
	 * Gets the ts 2 list.
	 *
	 * @return the ts 2 list
	 */
	public List<TS2> getTs2List() {
		return ts2List;
	}

	/**
	 * Sets the ts 2 list.
	 *
	 * @param ts2List the new ts 2 list
	 */
	public void setTs2List(List<TS2> ts2List) {
		this.ts2List = ts2List;
	}

	/**
	 * Gets the ts 2 filtered list.
	 *
	 * @return the ts 2 filtered list
	 */
	public List<TS2> getTs2filteredList() {
		return ts2filteredList;
	}

	/**
	 * Sets the ts 2 filtered list.
	 *
	 * @param ts2filteredList the new ts 2 filtered list
	 */
	public void setTs2filteredList(List<TS2> ts2filteredList) {
		this.ts2filteredList = ts2filteredList;
	}
	
	
	
    /**
     * Ts 2 detail.
     */
    public void ts2Detail() {
		try {
			total = 0.0;	totalctx = 0.0;
			this.ts2List = ts2service.findByTS1(ts1);
			this.ts2List.forEach(a-> {
				if (a.getDocumentAmountD()!=null) total += a.getDocumentAmountD();
				if (a.getCurrentTrxAmountD()!=null) totalctx += a.getCurrentTrxAmountD();
				
			});
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Gets the total.
	 *
	 * @return the total
	 */
	public Double getTotal() {
		return total;
	}

	/**
	 * Sets the total.
	 *
	 * @param total the new total
	 */
	public void setTotal(Double total) {
		this.total = total;
	}

	/**
	 * Gets the totalctx.
	 *
	 * @return the totalctx
	 */
	public Double getTotalctx() {
		return totalctx;
	}

	/**
	 * Sets the totalctx.
	 *
	 * @param totalctx the new totalctx
	 */
	public void setTotalctx(Double totalctx) {
		this.totalctx = totalctx;
	}
}
