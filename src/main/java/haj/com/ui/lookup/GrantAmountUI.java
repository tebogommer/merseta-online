package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.GrantAmount;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.GrantAmountService;

@ManagedBean(name = "grantamountUI")
@ViewScoped
public class GrantAmountUI extends AbstractUI {

	private GrantAmountService service = new GrantAmountService();
	private List<GrantAmount> grantamountList = null;
	private List<GrantAmount> grantamountfilteredList = null;
	private GrantAmount grantamount = null;
	private LazyDataModel<GrantAmount> dataModel; 

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
	 * Initialize method to get all GrantAmount and prepare a for a create of a new GrantAmount
 	 * @author TechFinium 
 	 * @see    GrantAmount
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		grantamountInfo();
	}

	/**
	 * Get all GrantAmount for data table
 	 * @author TechFinium 
 	 * @see    GrantAmount
 	 */
	public void grantamountInfo() {
	 
			
			 dataModel = new LazyDataModel<GrantAmount>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<GrantAmount> retorno = new  ArrayList<GrantAmount>();
			   
			   @Override 
			   public List<GrantAmount> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allGrantAmount(GrantAmount.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(GrantAmount.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(GrantAmount obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public GrantAmount getRowData(String rowKey) {
			        for(GrantAmount obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert GrantAmount into database
 	 * @author TechFinium 
 	 * @see    GrantAmount
 	 */
	public void grantamountInsert() {
		try {
				 service.create(this.grantamount);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 grantamountInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update GrantAmount in database
 	 * @author TechFinium 
 	 * @see    GrantAmount
 	 */
    public void grantamountUpdate() {
		try {
				 service.update(this.grantamount);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 grantamountInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete GrantAmount from database
 	 * @author TechFinium 
 	 * @see    GrantAmount
 	 */
	public void grantamountDelete() {
		try {
			 service.delete(this.grantamount);
			  prepareNew();
			 grantamountInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of GrantAmount 
 	 * @author TechFinium 
 	 * @see    GrantAmount
 	 */
	public void prepareNew() {
		grantamount = new GrantAmount();
	}

/*
    public List<SelectItem> getGrantAmountDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	grantamountInfo();
    	for (GrantAmount ug : grantamountList) {
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
    public List<GrantAmount> complete(String desc) {
		List<GrantAmount> l = null;
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
    
    public List<GrantAmount> getGrantAmountList() {
		return grantamountList;
	}
	public void setGrantAmountList(List<GrantAmount> grantamountList) {
		this.grantamountList = grantamountList;
	}
	public GrantAmount getGrantamount() {
		return grantamount;
	}
	public void setGrantamount(GrantAmount grantamount) {
		this.grantamount = grantamount;
	}

    public List<GrantAmount> getGrantAmountfilteredList() {
		return grantamountfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param grantamountfilteredList the new grantamountfilteredList list
 	 * @see    GrantAmount
 	 */	
	public void setGrantAmountfilteredList(List<GrantAmount> grantamountfilteredList) {
		this.grantamountfilteredList = grantamountfilteredList;
	}

	
	public LazyDataModel<GrantAmount> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<GrantAmount> dataModel) {
		this.dataModel = dataModel;
	}
	
}
