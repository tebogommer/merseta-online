package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.Ofo;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.OfoService;

// TODO: Auto-generated Javadoc
/**
 * The Class OfoUI.
 */
@ManagedBean(name = "ofoUI")
@ViewScoped
public class OfoUI extends AbstractUI {

	/** The service. */
	private OfoService service = new OfoService();
	
	/** The ofo list. */
	private List<Ofo> ofoList = null;
	
	/** The ofofiltered list. */
	private List<Ofo> ofofilteredList = null;
	
	/** The ofo. */
	private Ofo ofo = null;
	
	/** The data model. */
	private LazyDataModel<Ofo> dataModel; 

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
	 * Initialize method to get all Ofo and prepare a for a create of a new Ofo.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    Ofo
	 */
	private void runInit() throws Exception {
		prepareNew();
		ofoInfo();
	}

	/**
	 * Get all Ofo for data table.
	 *
	 * @author TechFinium
	 * @see    Ofo
	 */
	public void ofoInfo() {
	 
			
			 dataModel = new LazyDataModel<Ofo>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<Ofo> retorno = new  ArrayList<Ofo>();
			   
			   @Override 
			   public List<Ofo> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allOfo(Ofo.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(Ofo.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(Ofo obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public Ofo getRowData(String rowKey) {
			        for(Ofo obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert Ofo into database.
	 *
	 * @author TechFinium
	 * @see    Ofo
	 */
	public void ofoInsert() {
		try {
				 service.create(this.ofo);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 ofoInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update Ofo in database.
	 *
	 * @author TechFinium
	 * @see    Ofo
	 */
    public void ofoUpdate() {
		try {
				 service.update(this.ofo);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 ofoInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete Ofo from database.
	 *
	 * @author TechFinium
	 * @see    Ofo
	 */
	public void ofoDelete() {
		try {
			 service.delete(this.ofo);
			  prepareNew();
			 ofoInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted "));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of Ofo .
	 *
	 * @author TechFinium
	 * @see    Ofo
	 */
	public void prepareNew() {
		ofo = new Ofo();
	}

/*
    public List<SelectItem> getOfoDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	ofoInfo();
    	for (Ofo ug : ofoList) {
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
    public List<Ofo> complete(String desc) {
		List<Ofo> l = null;
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
     * Gets the ofo list.
     *
     * @return the ofo list
     */
    public List<Ofo> getOfoList() {
		return ofoList;
	}
	
	/**
	 * Sets the ofo list.
	 *
	 * @param ofoList the new ofo list
	 */
	public void setOfoList(List<Ofo> ofoList) {
		this.ofoList = ofoList;
	}
	
	/**
	 * Gets the ofo.
	 *
	 * @return the ofo
	 */
	public Ofo getOfo() {
		return ofo;
	}
	
	/**
	 * Sets the ofo.
	 *
	 * @param ofo the new ofo
	 */
	public void setOfo(Ofo ofo) {
		this.ofo = ofo;
	}

    /**
     * Gets the ofofiltered list.
     *
     * @return the ofofiltered list
     */
    public List<Ofo> getOfofilteredList() {
		return ofofilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param ofofilteredList the new ofofilteredList list
	 * @see    Ofo
	 */	
	public void setOfofilteredList(List<Ofo> ofofilteredList) {
		this.ofofilteredList = ofofilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<Ofo> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<Ofo> dataModel) {
		this.dataModel = dataModel;
	}
	
}
