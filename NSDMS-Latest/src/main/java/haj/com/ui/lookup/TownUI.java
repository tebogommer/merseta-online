package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.Town;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.TownService;

@ManagedBean(name = "townUI")
@ViewScoped
public class TownUI extends AbstractUI {

	private TownService service = new TownService();
	private List<Town> townList = null;
	private List<Town> townfilteredList = null;
	private Town town = null;
	private LazyDataModel<Town> dataModel; 

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
	 * Initialize method to get all Town and prepare a for a create of a new Town
 	 * @author TechFinium 
 	 * @see    Town
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		townInfo();
	}

	/**
	 * Get all Town for data table
 	 * @author TechFinium 
 	 * @see    Town
 	 */
	public void townInfo() {
	 
			
			 dataModel = new LazyDataModel<Town>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<Town> retorno = new  ArrayList<Town>();
			   
			   @Override 
			   public List<Town> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allTown(Town.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(Town.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(Town obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public Town getRowData(String rowKey) {
			        for(Town obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert Town into database
 	 * @author TechFinium 
 	 * @see    Town
 	 */
	public void townInsert() {
		try {
				 service.create(this.town);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 townInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update Town in database
 	 * @author TechFinium 
 	 * @see    Town
 	 */
    public void townUpdate() {
		try {
				 service.update(this.town);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 townInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete Town from database
 	 * @author TechFinium 
 	 * @see    Town
 	 */
	public void townDelete() {
		try {
			 service.delete(this.town);
			  prepareNew();
			 townInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of Town 
 	 * @author TechFinium 
 	 * @see    Town
 	 */
	public void prepareNew() {
		town = new Town();
	}

/*
    public List<SelectItem> getTownDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	townInfo();
    	for (Town ug : townList) {
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
    public List<Town> complete(String desc) {
		List<Town> l = null;
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
    
    public List<Town> getTownList() {
		return townList;
	}
	public void setTownList(List<Town> townList) {
		this.townList = townList;
	}
	public Town getTown() {
		return town;
	}
	public void setTown(Town town) {
		this.town = town;
	}

    public List<Town> getTownfilteredList() {
		return townfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param townfilteredList the new townfilteredList list
 	 * @see    Town
 	 */	
	public void setTownfilteredList(List<Town> townfilteredList) {
		this.townfilteredList = townfilteredList;
	}

	
	public LazyDataModel<Town> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<Town> dataModel) {
		this.dataModel = dataModel;
	}
	
}
