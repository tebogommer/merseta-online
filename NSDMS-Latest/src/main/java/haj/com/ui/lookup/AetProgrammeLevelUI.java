package  haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.lookup.AetProgrammeLevel;
import haj.com.service.lookup.AetProgrammeLevelService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "aetprogrammelevelUI")
@ViewScoped
public class AetProgrammeLevelUI extends AbstractUI {

	private AetProgrammeLevelService service = new AetProgrammeLevelService();
	private List<AetProgrammeLevel> aetprogrammelevelList = null;
	private List<AetProgrammeLevel> aetprogrammelevelfilteredList = null;
	private AetProgrammeLevel aetprogrammelevel = null;
	private LazyDataModel<AetProgrammeLevel> dataModel; 

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
	 * Initialize method to get all AetProgrammeLevel and prepare a for a create of a new AetProgrammeLevel
 	 * @author TechFinium 
 	 * @see    AetProgrammeLevel
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		aetprogrammelevelInfo();
	}

	/**
	 * Get all AetProgrammeLevel for data table
 	 * @author TechFinium 
 	 * @see    AetProgrammeLevel
 	 */
	public void aetprogrammelevelInfo() {
	 
			
			 dataModel = new LazyDataModel<AetProgrammeLevel>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<AetProgrammeLevel> retorno = new  ArrayList<AetProgrammeLevel>();
			   
			   @Override 
			   public List<AetProgrammeLevel> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allAetProgrammeLevel(AetProgrammeLevel.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(AetProgrammeLevel.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(AetProgrammeLevel obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public AetProgrammeLevel getRowData(String rowKey) {
			        for(AetProgrammeLevel obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert AetProgrammeLevel into database
 	 * @author TechFinium 
 	 * @see    AetProgrammeLevel
 	 */
	public void aetprogrammelevelInsert() {
		try {
				 service.create(this.aetprogrammelevel);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 aetprogrammelevelInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update AetProgrammeLevel in database
 	 * @author TechFinium 
 	 * @see    AetProgrammeLevel
 	 */
    public void aetprogrammelevelUpdate() {
		try {
				 service.update(this.aetprogrammelevel);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 aetprogrammelevelInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete AetProgrammeLevel from database
 	 * @author TechFinium 
 	 * @see    AetProgrammeLevel
 	 */
	public void aetprogrammelevelDelete() {
		try {
			 service.delete(this.aetprogrammelevel);
			  prepareNew();
			 aetprogrammelevelInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of AetProgrammeLevel 
 	 * @author TechFinium 
 	 * @see    AetProgrammeLevel
 	 */
	public void prepareNew() {
		aetprogrammelevel = new AetProgrammeLevel();
	}

/*
    public List<SelectItem> getAetProgrammeLevelDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	aetprogrammelevelInfo();
    	for (AetProgrammeLevel ug : aetprogrammelevelList) {
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
    public List<AetProgrammeLevel> complete(String desc) {
		List<AetProgrammeLevel> l = null;
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
    
    public List<AetProgrammeLevel> getAetProgrammeLevelList() {
		return aetprogrammelevelList;
	}
	public void setAetProgrammeLevelList(List<AetProgrammeLevel> aetprogrammelevelList) {
		this.aetprogrammelevelList = aetprogrammelevelList;
	}
	public AetProgrammeLevel getAetprogrammelevel() {
		return aetprogrammelevel;
	}
	public void setAetprogrammelevel(AetProgrammeLevel aetprogrammelevel) {
		this.aetprogrammelevel = aetprogrammelevel;
	}

    public List<AetProgrammeLevel> getAetProgrammeLevelfilteredList() {
		return aetprogrammelevelfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param aetprogrammelevelfilteredList the new aetprogrammelevelfilteredList list
 	 * @see    AetProgrammeLevel
 	 */	
	public void setAetProgrammeLevelfilteredList(List<AetProgrammeLevel> aetprogrammelevelfilteredList) {
		this.aetprogrammelevelfilteredList = aetprogrammelevelfilteredList;
	}

	
	public LazyDataModel<AetProgrammeLevel> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<AetProgrammeLevel> dataModel) {
		this.dataModel = dataModel;
	}
	
}
