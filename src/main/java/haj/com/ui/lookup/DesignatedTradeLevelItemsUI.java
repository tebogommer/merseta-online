package  haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.lookup.DesignatedTradeLevelItems;
import haj.com.service.lookup.DesignatedTradeLevelItemsService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "designatedtradelevelitemsUI")
@ViewScoped
public class DesignatedTradeLevelItemsUI extends AbstractUI {

	private DesignatedTradeLevelItemsService service = new DesignatedTradeLevelItemsService();
	private List<DesignatedTradeLevelItems> designatedtradelevelitemsList = null;
	private List<DesignatedTradeLevelItems> designatedtradelevelitemsfilteredList = null;
	private DesignatedTradeLevelItems designatedtradelevelitems = null;
	private LazyDataModel<DesignatedTradeLevelItems> dataModel; 

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
	 * Initialize method to get all DesignatedTradeLevelItems and prepare a for a create of a new DesignatedTradeLevelItems
 	 * @author TechFinium 
 	 * @see    DesignatedTradeLevelItems
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		designatedtradelevelitemsInfo();
	}

	/**
	 * Get all DesignatedTradeLevelItems for data table
 	 * @author TechFinium 
 	 * @see    DesignatedTradeLevelItems
 	 */
	public void designatedtradelevelitemsInfo() {
	 
			
			 dataModel = new LazyDataModel<DesignatedTradeLevelItems>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<DesignatedTradeLevelItems> retorno = new  ArrayList<DesignatedTradeLevelItems>();
			   
			   @Override 
			   public List<DesignatedTradeLevelItems> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allDesignatedTradeLevelItems(DesignatedTradeLevelItems.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(DesignatedTradeLevelItems.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(DesignatedTradeLevelItems obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public DesignatedTradeLevelItems getRowData(String rowKey) {
			        for(DesignatedTradeLevelItems obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert DesignatedTradeLevelItems into database
 	 * @author TechFinium 
 	 * @see    DesignatedTradeLevelItems
 	 */
	public void designatedtradelevelitemsInsert() {
		try {
				 service.create(this.designatedtradelevelitems);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 designatedtradelevelitemsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update DesignatedTradeLevelItems in database
 	 * @author TechFinium 
 	 * @see    DesignatedTradeLevelItems
 	 */
    public void designatedtradelevelitemsUpdate() {
		try {
				 service.update(this.designatedtradelevelitems);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 designatedtradelevelitemsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete DesignatedTradeLevelItems from database
 	 * @author TechFinium 
 	 * @see    DesignatedTradeLevelItems
 	 */
	public void designatedtradelevelitemsDelete() {
		try {
			 service.delete(this.designatedtradelevelitems);
			  prepareNew();
			 designatedtradelevelitemsInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of DesignatedTradeLevelItems 
 	 * @author TechFinium 
 	 * @see    DesignatedTradeLevelItems
 	 */
	public void prepareNew() {
		designatedtradelevelitems = new DesignatedTradeLevelItems();
	}

/*
    public List<SelectItem> getDesignatedTradeLevelItemsDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	designatedtradelevelitemsInfo();
    	for (DesignatedTradeLevelItems ug : designatedtradelevelitemsList) {
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
    public List<DesignatedTradeLevelItems> complete(String desc) {
		List<DesignatedTradeLevelItems> l = null;
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
    
    public List<DesignatedTradeLevelItems> getDesignatedTradeLevelItemsList() {
		return designatedtradelevelitemsList;
	}
	public void setDesignatedTradeLevelItemsList(List<DesignatedTradeLevelItems> designatedtradelevelitemsList) {
		this.designatedtradelevelitemsList = designatedtradelevelitemsList;
	}
	public DesignatedTradeLevelItems getDesignatedtradelevelitems() {
		return designatedtradelevelitems;
	}
	public void setDesignatedtradelevelitems(DesignatedTradeLevelItems designatedtradelevelitems) {
		this.designatedtradelevelitems = designatedtradelevelitems;
	}

    public List<DesignatedTradeLevelItems> getDesignatedTradeLevelItemsfilteredList() {
		return designatedtradelevelitemsfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param designatedtradelevelitemsfilteredList the new designatedtradelevelitemsfilteredList list
 	 * @see    DesignatedTradeLevelItems
 	 */	
	public void setDesignatedTradeLevelItemsfilteredList(List<DesignatedTradeLevelItems> designatedtradelevelitemsfilteredList) {
		this.designatedtradelevelitemsfilteredList = designatedtradelevelitemsfilteredList;
	}

	
	public LazyDataModel<DesignatedTradeLevelItems> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<DesignatedTradeLevelItems> dataModel) {
		this.dataModel = dataModel;
	}
	
}
