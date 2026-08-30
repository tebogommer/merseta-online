package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.SkillsGapTrackLookUp;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.SkillsGapTrackLookUpService;

@ManagedBean(name = "skillsgaptracklookupUI")
@ViewScoped
public class SkillsGapTrackLookUpUI extends AbstractUI {

	private SkillsGapTrackLookUpService service = new SkillsGapTrackLookUpService();
	private List<SkillsGapTrackLookUp> skillsgaptracklookupList = null;
	private List<SkillsGapTrackLookUp> skillsgaptracklookupfilteredList = null;
	private SkillsGapTrackLookUp skillsgaptracklookup = null;
	private LazyDataModel<SkillsGapTrackLookUp> dataModel; 

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
	 * Initialize method to get all SkillsGapTrackLookUp and prepare a for a create of a new SkillsGapTrackLookUp
 	 * @author TechFinium 
 	 * @see    SkillsGapTrackLookUp
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		skillsgaptracklookupInfo();
	}

	/**
	 * Get all SkillsGapTrackLookUp for data table
 	 * @author TechFinium 
 	 * @see    SkillsGapTrackLookUp
 	 */
	public void skillsgaptracklookupInfo() {
	 
			
			 dataModel = new LazyDataModel<SkillsGapTrackLookUp>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<SkillsGapTrackLookUp> retorno = new  ArrayList<SkillsGapTrackLookUp>();
			   
			   @Override 
			   public List<SkillsGapTrackLookUp> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allSkillsGapTrackLookUp(SkillsGapTrackLookUp.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(SkillsGapTrackLookUp.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(SkillsGapTrackLookUp obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public SkillsGapTrackLookUp getRowData(String rowKey) {
			        for(SkillsGapTrackLookUp obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert SkillsGapTrackLookUp into database
 	 * @author TechFinium 
 	 * @see    SkillsGapTrackLookUp
 	 */
	public void skillsgaptracklookupInsert() {
		try {
				 service.create(this.skillsgaptracklookup);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 skillsgaptracklookupInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update SkillsGapTrackLookUp in database
 	 * @author TechFinium 
 	 * @see    SkillsGapTrackLookUp
 	 */
    public void skillsgaptracklookupUpdate() {
		try {
				 service.update(this.skillsgaptracklookup);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 skillsgaptracklookupInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete SkillsGapTrackLookUp from database
 	 * @author TechFinium 
 	 * @see    SkillsGapTrackLookUp
 	 */
	public void skillsgaptracklookupDelete() {
		try {
			 service.delete(this.skillsgaptracklookup);
			  prepareNew();
			 skillsgaptracklookupInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of SkillsGapTrackLookUp 
 	 * @author TechFinium 
 	 * @see    SkillsGapTrackLookUp
 	 */
	public void prepareNew() {
		skillsgaptracklookup = new SkillsGapTrackLookUp();
	}

/*
    public List<SelectItem> getSkillsGapTrackLookUpDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	skillsgaptracklookupInfo();
    	for (SkillsGapTrackLookUp ug : skillsgaptracklookupList) {
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
    public List<SkillsGapTrackLookUp> complete(String desc) {
		List<SkillsGapTrackLookUp> l = null;
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
    
    public List<SkillsGapTrackLookUp> getSkillsGapTrackLookUpList() {
		return skillsgaptracklookupList;
	}
	public void setSkillsGapTrackLookUpList(List<SkillsGapTrackLookUp> skillsgaptracklookupList) {
		this.skillsgaptracklookupList = skillsgaptracklookupList;
	}
	public SkillsGapTrackLookUp getSkillsgaptracklookup() {
		return skillsgaptracklookup;
	}
	public void setSkillsgaptracklookup(SkillsGapTrackLookUp skillsgaptracklookup) {
		this.skillsgaptracklookup = skillsgaptracklookup;
	}

    public List<SkillsGapTrackLookUp> getSkillsGapTrackLookUpfilteredList() {
		return skillsgaptracklookupfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param skillsgaptracklookupfilteredList the new skillsgaptracklookupfilteredList list
 	 * @see    SkillsGapTrackLookUp
 	 */	
	public void setSkillsGapTrackLookUpfilteredList(List<SkillsGapTrackLookUp> skillsgaptracklookupfilteredList) {
		this.skillsgaptracklookupfilteredList = skillsgaptracklookupfilteredList;
	}

	
	public LazyDataModel<SkillsGapTrackLookUp> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SkillsGapTrackLookUp> dataModel) {
		this.dataModel = dataModel;
	}
	
}
