package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.Etqa;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.EtqaService;

// TODO: Auto-generated Javadoc
/**
 * The Class EtqaUI.
 */
@ManagedBean(name = "etqaUI")
@ViewScoped
public class EtqaUI extends AbstractUI {

	/** The service. */
	private EtqaService service = new EtqaService();
	
	/** The etqa list. */
	private List<Etqa> etqaList = null;
	
	/** The etqafiltered list. */
	private List<Etqa> etqafilteredList = null;
	
	/** The etqa. */
	private Etqa etqa = null;
	
	/** The data model. */
	private LazyDataModel<Etqa> dataModel; 

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
	 * Initialize method to get all Etqa and prepare a for a create of a new Etqa.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    Etqa
	 */
	private void runInit() throws Exception {
		prepareNew();
		etqaInfo();
	}

	/**
	 * Get all Etqa for data table.
	 *
	 * @author TechFinium
	 * @see    Etqa
	 */
	public void etqaInfo() {
	 
			
			 dataModel = new LazyDataModel<Etqa>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<Etqa> retorno = new  ArrayList<Etqa>();
			   
			   @Override 
			   public List<Etqa> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allEtqa(Etqa.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(Etqa.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(Etqa obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public Etqa getRowData(String rowKey) {
			        for(Etqa obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert Etqa into database.
	 *
	 * @author TechFinium
	 * @see    Etqa
	 */
	public void etqaInsert() {
		try {
				 service.create(this.etqa);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 etqaInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update Etqa in database.
	 *
	 * @author TechFinium
	 * @see    Etqa
	 */
    public void etqaUpdate() {
		try {
				 service.update(this.etqa);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 etqaInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete Etqa from database.
	 *
	 * @author TechFinium
	 * @see    Etqa
	 */
	public void etqaDelete() {
		try {
			 service.delete(this.etqa);
			  prepareNew();
			 etqaInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted "));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of Etqa .
	 *
	 * @author TechFinium
	 * @see    Etqa
	 */
	public void prepareNew() {
		etqa = new Etqa();
	}

/*
    public List<SelectItem> getEtqaDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	etqaInfo();
    	for (Etqa ug : etqaList) {
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
    public List<Etqa> complete(String desc) {
		List<Etqa> l = null;
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
     * Gets the etqa list.
     *
     * @return the etqa list
     */
    public List<Etqa> getEtqaList() {
		return etqaList;
	}
	
	/**
	 * Sets the etqa list.
	 *
	 * @param etqaList the new etqa list
	 */
	public void setEtqaList(List<Etqa> etqaList) {
		this.etqaList = etqaList;
	}
	
	/**
	 * Gets the etqa.
	 *
	 * @return the etqa
	 */
	public Etqa getEtqa() {
		return etqa;
	}
	
	/**
	 * Sets the etqa.
	 *
	 * @param etqa the new etqa
	 */
	public void setEtqa(Etqa etqa) {
		this.etqa = etqa;
	}

    /**
     * Gets the etqafiltered list.
     *
     * @return the etqafiltered list
     */
    public List<Etqa> getEtqafilteredList() {
		return etqafilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param etqafilteredList the new etqafilteredList list
	 * @see    Etqa
	 */	
	public void setEtqafilteredList(List<Etqa> etqafilteredList) {
		this.etqafilteredList = etqafilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<Etqa> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<Etqa> dataModel) {
		this.dataModel = dataModel;
	}
	
}
