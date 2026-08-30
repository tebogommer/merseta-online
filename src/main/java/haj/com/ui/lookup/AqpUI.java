package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.Aqp;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.AqpService;

@ManagedBean(name = "aqpUI")
@ViewScoped
public class AqpUI extends AbstractUI {

	private AqpService service = new AqpService();
	private List<Aqp> aqpList = null;
	private List<Aqp> aqpfilteredList = null;
	private Aqp aqp = null;
	private LazyDataModel<Aqp> dataModel; 

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
	 * Initialize method to get all Aqp and prepare a for a create of a new Aqp
 	 * @author TechFinium 
 	 * @see    Aqp
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		aqpInfo();
	}

	/**
	 * Get all Aqp for data table
 	 * @author TechFinium 
 	 * @see    Aqp
 	 */
	public void aqpInfo() {
	 
			
			 dataModel = new LazyDataModel<Aqp>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<Aqp> retorno = new  ArrayList<Aqp>();
			   
			   @Override 
			   public List<Aqp> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allAqp(Aqp.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(Aqp.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(Aqp obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public Aqp getRowData(String rowKey) {
			        for(Aqp obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert Aqp into database
 	 * @author TechFinium 
 	 * @see    Aqp
 	 */
	public void aqpInsert() {
		try {
				 service.create(this.aqp);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 aqpInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update Aqp in database
 	 * @author TechFinium 
 	 * @see    Aqp
 	 */
    public void aqpUpdate() {
		try {
				 service.update(this.aqp);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 aqpInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete Aqp from database
 	 * @author TechFinium 
 	 * @see    Aqp
 	 */
	public void aqpDelete() {
		try {
			 service.delete(this.aqp);
			  prepareNew();
			 aqpInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of Aqp 
 	 * @author TechFinium 
 	 * @see    Aqp
 	 */
	public void prepareNew() {
		aqp = new Aqp();
	}

/*
    public List<SelectItem> getAqpDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	aqpInfo();
    	for (Aqp ug : aqpList) {
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
    public List<Aqp> complete(String desc) {
		List<Aqp> l = null;
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
    
    public List<Aqp> getAqpList() {
		return aqpList;
	}
	public void setAqpList(List<Aqp> aqpList) {
		this.aqpList = aqpList;
	}
	public Aqp getAqp() {
		return aqp;
	}
	public void setAqp(Aqp aqp) {
		this.aqp = aqp;
	}

    public List<Aqp> getAqpfilteredList() {
		return aqpfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param aqpfilteredList the new aqpfilteredList list
 	 * @see    Aqp
 	 */	
	public void setAqpfilteredList(List<Aqp> aqpfilteredList) {
		this.aqpfilteredList = aqpfilteredList;
	}

	
	public LazyDataModel<Aqp> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<Aqp> dataModel) {
		this.dataModel = dataModel;
	}
	
}
