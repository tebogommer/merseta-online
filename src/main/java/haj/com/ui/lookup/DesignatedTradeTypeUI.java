package  haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.lookup.DesignatedTradeType;
import haj.com.service.lookup.DesignatedTradeTypeService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "designatedtradetypeUI")
@ViewScoped
public class DesignatedTradeTypeUI extends AbstractUI {

	private DesignatedTradeTypeService service = new DesignatedTradeTypeService();
	private List<DesignatedTradeType> designatedtradetypeList = null;
	private List<DesignatedTradeType> designatedtradetypefilteredList = null;
	private DesignatedTradeType designatedtradetype = null;
	private LazyDataModel<DesignatedTradeType> dataModel; 

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
	 * Initialize method to get all DesignatedTradeType and prepare a for a create of a new DesignatedTradeType
 	 * @author TechFinium 
 	 * @see    DesignatedTradeType
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		designatedtradetypeInfo();
	}

	/**
	 * Get all DesignatedTradeType for data table
 	 * @author TechFinium 
 	 * @see    DesignatedTradeType
 	 */
	public void designatedtradetypeInfo() {
	 
			
			 dataModel = new LazyDataModel<DesignatedTradeType>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<DesignatedTradeType> retorno = new  ArrayList<DesignatedTradeType>();
			   
			   @Override 
			   public List<DesignatedTradeType> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allDesignatedTradeType(DesignatedTradeType.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(DesignatedTradeType.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(DesignatedTradeType obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public DesignatedTradeType getRowData(String rowKey) {
			        for(DesignatedTradeType obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert DesignatedTradeType into database
 	 * @author TechFinium 
 	 * @see    DesignatedTradeType
 	 */
	public void designatedtradetypeInsert() {
		try {
				 service.create(this.designatedtradetype);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 designatedtradetypeInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update DesignatedTradeType in database
 	 * @author TechFinium 
 	 * @see    DesignatedTradeType
 	 */
    public void designatedtradetypeUpdate() {
		try {
				 service.update(this.designatedtradetype);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 designatedtradetypeInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete DesignatedTradeType from database
 	 * @author TechFinium 
 	 * @see    DesignatedTradeType
 	 */
	public void designatedtradetypeDelete() {
		try {
			 service.delete(this.designatedtradetype);
			  prepareNew();
			 designatedtradetypeInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of DesignatedTradeType 
 	 * @author TechFinium 
 	 * @see    DesignatedTradeType
 	 */
	public void prepareNew() {
		designatedtradetype = new DesignatedTradeType();
	}

/*
    public List<SelectItem> getDesignatedTradeTypeDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	designatedtradetypeInfo();
    	for (DesignatedTradeType ug : designatedtradetypeList) {
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
    public List<DesignatedTradeType> complete(String desc) {
		List<DesignatedTradeType> l = null;
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
    
    public List<DesignatedTradeType> getDesignatedTradeTypeList() {
		return designatedtradetypeList;
	}
	public void setDesignatedTradeTypeList(List<DesignatedTradeType> designatedtradetypeList) {
		this.designatedtradetypeList = designatedtradetypeList;
	}
	public DesignatedTradeType getDesignatedtradetype() {
		return designatedtradetype;
	}
	public void setDesignatedtradetype(DesignatedTradeType designatedtradetype) {
		this.designatedtradetype = designatedtradetype;
	}

    public List<DesignatedTradeType> getDesignatedTradeTypefilteredList() {
		return designatedtradetypefilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param designatedtradetypefilteredList the new designatedtradetypefilteredList list
 	 * @see    DesignatedTradeType
 	 */	
	public void setDesignatedTradeTypefilteredList(List<DesignatedTradeType> designatedtradetypefilteredList) {
		this.designatedtradetypefilteredList = designatedtradetypefilteredList;
	}

	
	public LazyDataModel<DesignatedTradeType> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<DesignatedTradeType> dataModel) {
		this.dataModel = dataModel;
	}
	
}
