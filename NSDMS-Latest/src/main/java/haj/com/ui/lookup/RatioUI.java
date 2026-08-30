package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.Ratio;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.RatioService;

@ManagedBean(name = "ratioUI")
@ViewScoped
public class RatioUI extends AbstractUI {

	private RatioService service = new RatioService();
	private List<Ratio> ratioList = null;
	private List<Ratio> ratiofilteredList = null;
	private Ratio ratio = null;
	private LazyDataModel<Ratio> dataModel; 

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
	 * Initialize method to get all Ratio and prepare a for a create of a new Ratio
 	 * @author TechFinium 
 	 * @see    Ratio
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		ratioInfo();
	}

	/**
	 * Get all Ratio for data table
 	 * @author TechFinium 
 	 * @see    Ratio
 	 */
	public void ratioInfo() {
	 
			
			 dataModel = new LazyDataModel<Ratio>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<Ratio> retorno = new  ArrayList<Ratio>();
			   
			   @Override 
			   public List<Ratio> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allRatio(Ratio.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(Ratio.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(Ratio obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public Ratio getRowData(String rowKey) {
			        for(Ratio obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert Ratio into database
 	 * @author TechFinium 
 	 * @see    Ratio
 	 */
	public void ratioInsert() {
		try {
				 service.create(this.ratio);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 ratioInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update Ratio in database
 	 * @author TechFinium 
 	 * @see    Ratio
 	 */
    public void ratioUpdate() {
		try {
				 service.update(this.ratio);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 ratioInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete Ratio from database
 	 * @author TechFinium 
 	 * @see    Ratio
 	 */
	public void ratioDelete() {
		try {
			 service.delete(this.ratio);
			  prepareNew();
			 ratioInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of Ratio 
 	 * @author TechFinium 
 	 * @see    Ratio
 	 */
	public void prepareNew() {
		ratio = new Ratio();
	}

/*
    public List<SelectItem> getRatioDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	ratioInfo();
    	for (Ratio ug : ratioList) {
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
    public List<Ratio> complete(String desc) {
		List<Ratio> l = null;
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
    
    public List<Ratio> getRatioList() {
		return ratioList;
	}
	public void setRatioList(List<Ratio> ratioList) {
		this.ratioList = ratioList;
	}
	public Ratio getRatio() {
		return ratio;
	}
	public void setRatio(Ratio ratio) {
		this.ratio = ratio;
	}

    public List<Ratio> getRatiofilteredList() {
		return ratiofilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param ratiofilteredList the new ratiofilteredList list
 	 * @see    Ratio
 	 */	
	public void setRatiofilteredList(List<Ratio> ratiofilteredList) {
		this.ratiofilteredList = ratiofilteredList;
	}

	
	public LazyDataModel<Ratio> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<Ratio> dataModel) {
		this.dataModel = dataModel;
	}
	
}
