package  haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.lookup.AreaForImprovement;
import haj.com.service.lookup.AreaForImprovementService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "areaforimprovementUI")
@ViewScoped
public class AreaForImprovementUI extends AbstractUI {

	private AreaForImprovementService service = new AreaForImprovementService();
	private List<AreaForImprovement> areaforimprovementList = null;
	private List<AreaForImprovement> areaforimprovementfilteredList = null;
	private AreaForImprovement areaforimprovement = null;
	private LazyDataModel<AreaForImprovement> dataModel; 

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
	 * Initialize method to get all AreaForImprovement and prepare a for a create of a new AreaForImprovement
 	 * @author TechFinium 
 	 * @see    AreaForImprovement
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		areaforimprovementInfo();
	}

	/**
	 * Get all AreaForImprovement for data table
 	 * @author TechFinium 
 	 * @see    AreaForImprovement
 	 */
	public void areaforimprovementInfo() {
	 
			
			 dataModel = new LazyDataModel<AreaForImprovement>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<AreaForImprovement> retorno = new  ArrayList<AreaForImprovement>();
			   
			   @Override 
			   public List<AreaForImprovement> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allAreaForImprovement(AreaForImprovement.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(AreaForImprovement.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(AreaForImprovement obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public AreaForImprovement getRowData(String rowKey) {
			        for(AreaForImprovement obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert AreaForImprovement into database
 	 * @author TechFinium 
 	 * @see    AreaForImprovement
 	 */
	public void areaforimprovementInsert() {
		try {
				 service.create(this.areaforimprovement);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 areaforimprovementInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update AreaForImprovement in database
 	 * @author TechFinium 
 	 * @see    AreaForImprovement
 	 */
    public void areaforimprovementUpdate() {
		try {
				 service.update(this.areaforimprovement);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 areaforimprovementInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete AreaForImprovement from database
 	 * @author TechFinium 
 	 * @see    AreaForImprovement
 	 */
	public void areaforimprovementDelete() {
		try {
			 service.delete(this.areaforimprovement);
			  prepareNew();
			 areaforimprovementInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of AreaForImprovement 
 	 * @author TechFinium 
 	 * @see    AreaForImprovement
 	 */
	public void prepareNew() {
		areaforimprovement = new AreaForImprovement();
	}

/*
    public List<SelectItem> getAreaForImprovementDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	areaforimprovementInfo();
    	for (AreaForImprovement ug : areaforimprovementList) {
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
    public List<AreaForImprovement> complete(String desc) {
		List<AreaForImprovement> l = null;
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
    
    public List<AreaForImprovement> getAreaForImprovementList() {
		return areaforimprovementList;
	}
	public void setAreaForImprovementList(List<AreaForImprovement> areaforimprovementList) {
		this.areaforimprovementList = areaforimprovementList;
	}
	public AreaForImprovement getAreaforimprovement() {
		return areaforimprovement;
	}
	public void setAreaforimprovement(AreaForImprovement areaforimprovement) {
		this.areaforimprovement = areaforimprovement;
	}

    public List<AreaForImprovement> getAreaForImprovementfilteredList() {
		return areaforimprovementfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param areaforimprovementfilteredList the new areaforimprovementfilteredList list
 	 * @see    AreaForImprovement
 	 */	
	public void setAreaForImprovementfilteredList(List<AreaForImprovement> areaforimprovementfilteredList) {
		this.areaforimprovementfilteredList = areaforimprovementfilteredList;
	}

	
	public LazyDataModel<AreaForImprovement> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<AreaForImprovement> dataModel) {
		this.dataModel = dataModel;
	}
	
}
