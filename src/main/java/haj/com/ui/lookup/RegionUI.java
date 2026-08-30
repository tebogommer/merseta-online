package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.Region;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.RegionService;

// TODO: Auto-generated Javadoc
/**
 * The Class RegionUI.
 */
@ManagedBean(name = "regionUI")
@ViewScoped
public class RegionUI extends AbstractUI {

	/** The service. */
	private RegionService service = new RegionService();
	
	/** The region list. */
	private List<Region> regionList = null;
	
	/** The regionfiltered list. */
	private List<Region> regionfilteredList = null;
	
	/** The region. */
	private Region region = null;
	
	/** The data model. */
	private LazyDataModel<Region> dataModel; 

    /**
     * Inits the.
     */
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
	 * Initialize method to get all Region and prepare a for a create of a new Region.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    Region
	 */
	private void runInit() throws Exception {
		prepareNew();
		regionInfo();
	}

	/**
	 * Get all Region for data table.
	 *
	 * @author TechFinium
	 * @see    Region
	 */
	public void regionInfo() {
	 
			
			 dataModel = new LazyDataModel<Region>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<Region> retorno = new  ArrayList<Region>();
			   
			   @Override 
			   public List<Region> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allRegion(Region.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(Region.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(Region obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public Region getRowData(String rowKey) {
			        for(Region obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert Region into database.
	 *
	 * @author TechFinium
	 * @see    Region
	 */
	public void regionInsert() {
		try {
				 service.create(this.region);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 regionInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update Region in database.
	 *
	 * @author TechFinium
	 * @see    Region
	 */
    public void regionUpdate() {
		try {
				 service.update(this.region);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 regionInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete Region from database.
	 *
	 * @author TechFinium
	 * @see    Region
	 */
	public void regionDelete() {
		try {
			 service.delete(this.region);
			  prepareNew();
			 regionInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted "));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of Region .
	 *
	 * @author TechFinium
	 * @see    Region
	 */
	public void prepareNew() {
		region = new Region();
	}

/*
    public List<SelectItem> getRegionDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	regionInfo();
    	for (Region ug : regionList) {
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
    public List<Region> complete(String desc) {
		List<Region> l = null;
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
    
    /**
     * Gets the region list.
     *
     * @return the region list
     */
    public List<Region> getRegionList() {
		return regionList;
	}
	
	/**
	 * Sets the region list.
	 *
	 * @param regionList the new region list
	 */
	public void setRegionList(List<Region> regionList) {
		this.regionList = regionList;
	}
	
	/**
	 * Gets the region.
	 *
	 * @return the region
	 */
	public Region getRegion() {
		return region;
	}
	
	/**
	 * Sets the region.
	 *
	 * @param region the new region
	 */
	public void setRegion(Region region) {
		this.region = region;
	}

    /**
     * Gets the regionfiltered list.
     *
     * @return the regionfiltered list
     */
    public List<Region> getRegionfilteredList() {
		return regionfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param regionfilteredList the new regionfilteredList list
	 * @see    Region
	 */	
	public void setRegionfilteredList(List<Region> regionfilteredList) {
		this.regionfilteredList = regionfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<Region> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<Region> dataModel) {
		this.dataModel = dataModel;
	}
	
}
