package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.NextRefreshYear;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.NextRefreshYearService;

@ManagedBean(name = "nextrefreshyearUI")
@ViewScoped
public class NextRefreshYearUI extends AbstractUI {

	private NextRefreshYearService service = new NextRefreshYearService();
	private List<NextRefreshYear> nextrefreshyearList = null;
	private List<NextRefreshYear> nextrefreshyearfilteredList = null;
	private NextRefreshYear nextrefreshyear = null;
	private LazyDataModel<NextRefreshYear> dataModel; 

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
	 * Initialize method to get all NextRefreshYear and prepare a for a create of a new NextRefreshYear
 	 * @author TechFinium 
 	 * @see    NextRefreshYear
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		nextrefreshyearInfo();
	}

	/**
	 * Get all NextRefreshYear for data table
 	 * @author TechFinium 
 	 * @see    NextRefreshYear
 	 */
	public void nextrefreshyearInfo() {
	 
			
			 dataModel = new LazyDataModel<NextRefreshYear>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<NextRefreshYear> retorno = new  ArrayList<NextRefreshYear>();
			   
			   @Override 
			   public List<NextRefreshYear> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allNextRefreshYear(NextRefreshYear.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(NextRefreshYear.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(NextRefreshYear obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public NextRefreshYear getRowData(String rowKey) {
			        for(NextRefreshYear obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert NextRefreshYear into database
 	 * @author TechFinium 
 	 * @see    NextRefreshYear
 	 */
	public void nextrefreshyearInsert() {
		try {
				 service.create(this.nextrefreshyear);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 nextrefreshyearInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update NextRefreshYear in database
 	 * @author TechFinium 
 	 * @see    NextRefreshYear
 	 */
    public void nextrefreshyearUpdate() {
		try {
				 service.update(this.nextrefreshyear);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 nextrefreshyearInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete NextRefreshYear from database
 	 * @author TechFinium 
 	 * @see    NextRefreshYear
 	 */
	public void nextrefreshyearDelete() {
		try {
			 service.delete(this.nextrefreshyear);
			  prepareNew();
			 nextrefreshyearInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of NextRefreshYear 
 	 * @author TechFinium 
 	 * @see    NextRefreshYear
 	 */
	public void prepareNew() {
		nextrefreshyear = new NextRefreshYear();
	}

/*
    public List<SelectItem> getNextRefreshYearDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	nextrefreshyearInfo();
    	for (NextRefreshYear ug : nextrefreshyearList) {
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
    public List<NextRefreshYear> complete(String desc) {
		List<NextRefreshYear> l = null;
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
    
    public List<NextRefreshYear> getNextRefreshYearList() {
		return nextrefreshyearList;
	}
	public void setNextRefreshYearList(List<NextRefreshYear> nextrefreshyearList) {
		this.nextrefreshyearList = nextrefreshyearList;
	}
	public NextRefreshYear getNextrefreshyear() {
		return nextrefreshyear;
	}
	public void setNextrefreshyear(NextRefreshYear nextrefreshyear) {
		this.nextrefreshyear = nextrefreshyear;
	}

    public List<NextRefreshYear> getNextRefreshYearfilteredList() {
		return nextrefreshyearfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param nextrefreshyearfilteredList the new nextrefreshyearfilteredList list
 	 * @see    NextRefreshYear
 	 */	
	public void setNextRefreshYearfilteredList(List<NextRefreshYear> nextrefreshyearfilteredList) {
		this.nextrefreshyearfilteredList = nextrefreshyearfilteredList;
	}

	
	public LazyDataModel<NextRefreshYear> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<NextRefreshYear> dataModel) {
		this.dataModel = dataModel;
	}
	
}
