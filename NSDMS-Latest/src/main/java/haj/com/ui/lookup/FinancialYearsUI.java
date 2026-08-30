package  haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.lookup.FinancialYears;
import haj.com.service.lookup.FinancialYearsService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "financialyearsUI")
@ViewScoped
public class FinancialYearsUI extends AbstractUI {

	private FinancialYearsService service = new FinancialYearsService();
	private List<FinancialYears> financialyearsList = null;
	private List<FinancialYears> financialyearsfilteredList = null;
	private FinancialYears financialyears = null;
	private LazyDataModel<FinancialYears> dataModel; 

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
	 * Initialize method to get all FinancialYears and prepare a for a create of a new FinancialYears
 	 * @author TechFinium 
 	 * @see    FinancialYears
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		financialyearsInfo();
	}

	/**
	 * Get all FinancialYears for data table
 	 * @author TechFinium 
 	 * @see    FinancialYears
 	 */
	public void financialyearsInfo() {
	 
			
			 dataModel = new LazyDataModel<FinancialYears>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<FinancialYears> retorno = new  ArrayList<FinancialYears>();
			   
			   @Override 
			   public List<FinancialYears> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allFinancialYears(FinancialYears.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(FinancialYears.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(FinancialYears obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public FinancialYears getRowData(String rowKey) {
			        for(FinancialYears obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert FinancialYears into database
 	 * @author TechFinium 
 	 * @see    FinancialYears
 	 */
	public void financialyearsInsert() {
		try {
				 service.create(this.financialyears);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 financialyearsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update FinancialYears in database
 	 * @author TechFinium 
 	 * @see    FinancialYears
 	 */
    public void financialyearsUpdate() {
		try {
				 service.update(this.financialyears);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 financialyearsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete FinancialYears from database
 	 * @author TechFinium 
 	 * @see    FinancialYears
 	 */
	public void financialyearsDelete() {
		try {
			 service.delete(this.financialyears);
			  prepareNew();
			 financialyearsInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of FinancialYears 
 	 * @author TechFinium 
 	 * @see    FinancialYears
 	 */
	public void prepareNew() {
		financialyears = new FinancialYears();
	}

/*
    public List<SelectItem> getFinancialYearsDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	financialyearsInfo();
    	for (FinancialYears ug : financialyearsList) {
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
    public List<FinancialYears> complete(String desc) {
		List<FinancialYears> l = null;
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
    
    public List<FinancialYears> getFinancialYearsList() {
		return financialyearsList;
	}
	public void setFinancialYearsList(List<FinancialYears> financialyearsList) {
		this.financialyearsList = financialyearsList;
	}
	public FinancialYears getFinancialyears() {
		return financialyears;
	}
	public void setFinancialyears(FinancialYears financialyears) {
		this.financialyears = financialyears;
	}

    public List<FinancialYears> getFinancialYearsfilteredList() {
		return financialyearsfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param financialyearsfilteredList the new financialyearsfilteredList list
 	 * @see    FinancialYears
 	 */	
	public void setFinancialYearsfilteredList(List<FinancialYears> financialyearsfilteredList) {
		this.financialyearsfilteredList = financialyearsfilteredList;
	}

	
	public LazyDataModel<FinancialYears> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<FinancialYears> dataModel) {
		this.dataModel = dataModel;
	}
	
}
