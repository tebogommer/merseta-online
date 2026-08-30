package  haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.lookup.SdpType;
import haj.com.service.lookup.SdpTypeService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "sdptypeUI")
@ViewScoped
public class SdpTypeUI extends AbstractUI {

	private SdpTypeService service = new SdpTypeService();
	private List<SdpType> sdptypeList = null;
	private List<SdpType> sdptypefilteredList = null;
	private SdpType sdptype = null;
	private LazyDataModel<SdpType> dataModel; 

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
	 * Initialize method to get all SdpType and prepare a for a create of a new SdpType
 	 * @author TechFinium 
 	 * @see    SdpType
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		sdptypeInfo();
	}

	/**
	 * Get all SdpType for data table
 	 * @author TechFinium 
 	 * @see    SdpType
 	 */
	public void sdptypeInfo() {
	 
			
			 dataModel = new LazyDataModel<SdpType>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<SdpType> retorno = new  ArrayList<SdpType>();
			   
			   @Override 
			   public List<SdpType> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allSdpType(SdpType.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(SdpType.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(SdpType obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public SdpType getRowData(String rowKey) {
			        for(SdpType obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert SdpType into database
 	 * @author TechFinium 
 	 * @see    SdpType
 	 */
	public void sdptypeInsert() {
		try {
				service.validiateDesignationUsed(this.sdptype);
				 service.create(this.sdptype);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 sdptypeInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update SdpType in database
 	 * @author TechFinium 
 	 * @see    SdpType
 	 */
    public void sdptypeUpdate() {
		try {
				 service.update(this.sdptype);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 sdptypeInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete SdpType from database
 	 * @author TechFinium 
 	 * @see    SdpType
 	 */
	public void sdptypeDelete() {
		try {
			 service.delete(this.sdptype);
			  prepareNew();
			 sdptypeInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of SdpType 
 	 * @author TechFinium 
 	 * @see    SdpType
 	 */
	public void prepareNew() {
		sdptype = new SdpType();
	}

/*
    public List<SelectItem> getSdpTypeDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	sdptypeInfo();
    	for (SdpType ug : sdptypeList) {
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
    public List<SdpType> complete(String desc) {
		List<SdpType> l = null;
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
    
    public List<SdpType> getSdpTypeList() {
		return sdptypeList;
	}
	public void setSdpTypeList(List<SdpType> sdptypeList) {
		this.sdptypeList = sdptypeList;
	}
	public SdpType getSdptype() {
		return sdptype;
	}
	public void setSdptype(SdpType sdptype) {
		this.sdptype = sdptype;
	}

    public List<SdpType> getSdpTypefilteredList() {
		return sdptypefilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param sdptypefilteredList the new sdptypefilteredList list
 	 * @see    SdpType
 	 */	
	public void setSdpTypefilteredList(List<SdpType> sdptypefilteredList) {
		this.sdptypefilteredList = sdptypefilteredList;
	}

	
	public LazyDataModel<SdpType> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SdpType> dataModel) {
		this.dataModel = dataModel;
	}
	
}
