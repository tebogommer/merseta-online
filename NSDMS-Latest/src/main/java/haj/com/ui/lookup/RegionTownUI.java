package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.RegionTown;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.RegionTownService;

@ManagedBean(name = "regiontownUI")
@ViewScoped
public class RegionTownUI extends AbstractUI {

	private RegionTownService service = new RegionTownService();
	private List<RegionTown> regiontownList = null;
	private List<RegionTown> regiontownfilteredList = null;
	private RegionTown regiontown = null;
	private LazyDataModel<RegionTown> dataModel; 

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
	 * Initialize method to get all RegionTown and prepare a for a create of a new RegionTown
 	 * @author TechFinium 
 	 * @see    RegionTown
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		regiontownInfo();
	}

	/**
	 * Get all RegionTown for data table
 	 * @author TechFinium 
 	 * @see    RegionTown
 	 */
	public void regiontownInfo() {
	 
			
			 dataModel = new LazyDataModel<RegionTown>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<RegionTown> retorno = new  ArrayList<RegionTown>();
			   
			   @Override 
			   public List<RegionTown> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allRegionTown(RegionTown.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(RegionTown.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(RegionTown obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public RegionTown getRowData(String rowKey) {
			        for(RegionTown obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert RegionTown into database
 	 * @author TechFinium 
 	 * @see    RegionTown
 	 */
	public void regiontownInsert() {
		try {
				 service.create(this.regiontown);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 regiontownInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update RegionTown in database
 	 * @author TechFinium 
 	 * @see    RegionTown
 	 */
    public void regiontownUpdate() {
		try {
				 service.update(this.regiontown);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 regiontownInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete RegionTown from database
 	 * @author TechFinium 
 	 * @see    RegionTown
 	 */
	public void regiontownDelete() {
		try {
			 service.delete(this.regiontown);
			  prepareNew();
			 regiontownInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of RegionTown 
 	 * @author TechFinium 
 	 * @see    RegionTown
 	 */
	public void prepareNew() {
		regiontown = new RegionTown();
	}

/*
    public List<SelectItem> getRegionTownDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	regiontownInfo();
    	for (RegionTown ug : regiontownList) {
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
    public List<RegionTown> complete(String desc) {
		List<RegionTown> l = null;
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
    
    public List<RegionTown> getRegionTownList() {
		return regiontownList;
	}
	public void setRegionTownList(List<RegionTown> regiontownList) {
		this.regiontownList = regiontownList;
	}
	public RegionTown getRegiontown() {
		return regiontown;
	}
	public void setRegiontown(RegionTown regiontown) {
		this.regiontown = regiontown;
	}

    public List<RegionTown> getRegionTownfilteredList() {
		return regiontownfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param regiontownfilteredList the new regiontownfilteredList list
 	 * @see    RegionTown
 	 */	
	public void setRegionTownfilteredList(List<RegionTown> regiontownfilteredList) {
		this.regiontownfilteredList = regiontownfilteredList;
	}

	
	public LazyDataModel<RegionTown> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<RegionTown> dataModel) {
		this.dataModel = dataModel;
	}
	
}
