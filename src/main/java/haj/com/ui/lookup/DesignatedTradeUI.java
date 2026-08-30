package  haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.lookup.DesignatedTrade;
import haj.com.service.lookup.DesignatedTradeService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "designatedtradeUI")
@ViewScoped
public class DesignatedTradeUI extends AbstractUI {

	private DesignatedTradeService service = new DesignatedTradeService();
	private List<DesignatedTrade> designatedtradeList = null;
	private List<DesignatedTrade> designatedtradefilteredList = null;
	private DesignatedTrade designatedtrade = null;
	private LazyDataModel<DesignatedTrade> dataModel; 

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
	 * Initialize method to get all DesignatedTrade and prepare a for a create of a new DesignatedTrade
 	 * @author TechFinium 
 	 * @see    DesignatedTrade
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		designatedtradeInfo();
	}

	/**
	 * Get all DesignatedTrade for data table
 	 * @author TechFinium 
 	 * @see    DesignatedTrade
 	 */
	public void designatedtradeInfo() {
	 
			
			 dataModel = new LazyDataModel<DesignatedTrade>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<DesignatedTrade> retorno = new  ArrayList<DesignatedTrade>();
			   
			   @Override 
			   public List<DesignatedTrade> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allDesignatedTrade(DesignatedTrade.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(DesignatedTrade.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(DesignatedTrade obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public DesignatedTrade getRowData(String rowKey) {
			        for(DesignatedTrade obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert DesignatedTrade into database
 	 * @author TechFinium 
 	 * @see    DesignatedTrade
 	 */
	public void designatedtradeInsert() {
		try {
				 service.create(this.designatedtrade);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 designatedtradeInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update DesignatedTrade in database
 	 * @author TechFinium 
 	 * @see    DesignatedTrade
 	 */
    public void designatedtradeUpdate() {
		try {
				 service.update(this.designatedtrade);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 designatedtradeInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete DesignatedTrade from database
 	 * @author TechFinium 
 	 * @see    DesignatedTrade
 	 */
	public void designatedtradeDelete() {
		try {
			 service.delete(this.designatedtrade);
			  prepareNew();
			 designatedtradeInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of DesignatedTrade 
 	 * @author TechFinium 
 	 * @see    DesignatedTrade
 	 */
	public void prepareNew() {
		designatedtrade = new DesignatedTrade();
	}

/*
    public List<SelectItem> getDesignatedTradeDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	designatedtradeInfo();
    	for (DesignatedTrade ug : designatedtradeList) {
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
    public List<DesignatedTrade> complete(String desc) {
		List<DesignatedTrade> l = null;
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
    
    public List<DesignatedTrade> getDesignatedTradeList() {
		return designatedtradeList;
	}
	public void setDesignatedTradeList(List<DesignatedTrade> designatedtradeList) {
		this.designatedtradeList = designatedtradeList;
	}
	public DesignatedTrade getDesignatedtrade() {
		return designatedtrade;
	}
	public void setDesignatedtrade(DesignatedTrade designatedtrade) {
		this.designatedtrade = designatedtrade;
	}

    public List<DesignatedTrade> getDesignatedTradefilteredList() {
		return designatedtradefilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param designatedtradefilteredList the new designatedtradefilteredList list
 	 * @see    DesignatedTrade
 	 */	
	public void setDesignatedTradefilteredList(List<DesignatedTrade> designatedtradefilteredList) {
		this.designatedtradefilteredList = designatedtradefilteredList;
	}

	
	public LazyDataModel<DesignatedTrade> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<DesignatedTrade> dataModel) {
		this.dataModel = dataModel;
	}
	
}
