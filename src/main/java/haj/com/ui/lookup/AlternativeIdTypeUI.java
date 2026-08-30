package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.AlternativeIdType;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.AlternativeIdTypeService;

// TODO: Auto-generated Javadoc
/**
 * The Class AlternativeIdTypeUI.
 */
@ManagedBean(name = "alternativeidtypeUI")
@ViewScoped
public class AlternativeIdTypeUI extends AbstractUI {

	/** The service. */
	private AlternativeIdTypeService service = new AlternativeIdTypeService();
	
	/** The alternativeidtype list. */
	private List<AlternativeIdType> alternativeidtypeList = null;
	
	/** The alternativeidtypefiltered list. */
	private List<AlternativeIdType> alternativeidtypefilteredList = null;
	
	/** The alternativeidtype. */
	private AlternativeIdType alternativeidtype = null;
	
	/** The data model. */
	private LazyDataModel<AlternativeIdType> dataModel; 

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
	 * Initialize method to get all AlternativeIdType and prepare a for a create of a new AlternativeIdType.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    AlternativeIdType
	 */
	private void runInit() throws Exception {
		prepareNew();
		alternativeidtypeInfo();
	}

	/**
	 * Get all AlternativeIdType for data table.
	 *
	 * @author TechFinium
	 * @see    AlternativeIdType
	 */
	public void alternativeidtypeInfo() {
	 
			
			 dataModel = new LazyDataModel<AlternativeIdType>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<AlternativeIdType> retorno = new  ArrayList<AlternativeIdType>();
			   
			   @Override 
			   public List<AlternativeIdType> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allAlternativeIdType(AlternativeIdType.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(AlternativeIdType.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(AlternativeIdType obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public AlternativeIdType getRowData(String rowKey) {
			        for(AlternativeIdType obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert AlternativeIdType into database.
	 *
	 * @author TechFinium
	 * @see    AlternativeIdType
	 */
	public void alternativeidtypeInsert() {
		try {
				 service.create(this.alternativeidtype);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 alternativeidtypeInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update AlternativeIdType in database.
	 *
	 * @author TechFinium
	 * @see    AlternativeIdType
	 */
    public void alternativeidtypeUpdate() {
		try {
				 service.update(this.alternativeidtype);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 alternativeidtypeInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete AlternativeIdType from database.
	 *
	 * @author TechFinium
	 * @see    AlternativeIdType
	 */
	public void alternativeidtypeDelete() {
		try {
			 service.delete(this.alternativeidtype);
			  prepareNew();
			 alternativeidtypeInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of AlternativeIdType .
	 *
	 * @author TechFinium
	 * @see    AlternativeIdType
	 */
	public void prepareNew() {
		alternativeidtype = new AlternativeIdType();
	}

/*
    public List<SelectItem> getAlternativeIdTypeDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	alternativeidtypeInfo();
    	for (AlternativeIdType ug : alternativeidtypeList) {
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
    public List<AlternativeIdType> complete(String desc) {
		List<AlternativeIdType> l = null;
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
     * Gets the alternative id type list.
     *
     * @return the alternative id type list
     */
    public List<AlternativeIdType> getAlternativeIdTypeList() {
		return alternativeidtypeList;
	}
	
	/**
	 * Sets the alternative id type list.
	 *
	 * @param alternativeidtypeList the new alternative id type list
	 */
	public void setAlternativeIdTypeList(List<AlternativeIdType> alternativeidtypeList) {
		this.alternativeidtypeList = alternativeidtypeList;
	}
	
	/**
	 * Gets the alternativeidtype.
	 *
	 * @return the alternativeidtype
	 */
	public AlternativeIdType getAlternativeidtype() {
		return alternativeidtype;
	}
	
	/**
	 * Sets the alternativeidtype.
	 *
	 * @param alternativeidtype the new alternativeidtype
	 */
	public void setAlternativeidtype(AlternativeIdType alternativeidtype) {
		this.alternativeidtype = alternativeidtype;
	}

    /**
     * Gets the alternative id typefiltered list.
     *
     * @return the alternative id typefiltered list
     */
    public List<AlternativeIdType> getAlternativeIdTypefilteredList() {
		return alternativeidtypefilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param alternativeidtypefilteredList the new alternativeidtypefilteredList list
	 * @see    AlternativeIdType
	 */	
	public void setAlternativeIdTypefilteredList(List<AlternativeIdType> alternativeidtypefilteredList) {
		this.alternativeidtypefilteredList = alternativeidtypefilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<AlternativeIdType> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<AlternativeIdType> dataModel) {
		this.dataModel = dataModel;
	}
	
}
