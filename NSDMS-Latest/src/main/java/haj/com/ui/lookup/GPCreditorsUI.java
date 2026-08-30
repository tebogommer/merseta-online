package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.GPCreditors;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.GPCreditorsService;

@ManagedBean(name = "gpcreditorsUI")
@ViewScoped
public class GPCreditorsUI extends AbstractUI {

	private GPCreditorsService service = new GPCreditorsService();
	private List<GPCreditors> gpcreditorsList = null;
	private List<GPCreditors> gpcreditorsfilteredList = null;
	private GPCreditors gpcreditors = null;
	private LazyDataModel<GPCreditors> dataModel; 

    @PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Initialize method to get all GPCreditors and prepare a for a create of a new GPCreditors
 	 * @author TechFinium 
 	 * @see    GPCreditors
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		gpcreditorsInfo();
	}

	/**
	 * Get all GPCreditors for data table
 	 * @author TechFinium 
 	 * @see    GPCreditors
 	 */
	public void gpcreditorsInfo() {
	 
			
			 dataModel = new LazyDataModel<GPCreditors>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<GPCreditors> retorno = new  ArrayList<GPCreditors>();
			   
			   @Override 
			   public List<GPCreditors> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allGPCreditors(GPCreditors.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(GPCreditors.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(GPCreditors obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public GPCreditors getRowData(String rowKey) {
			        for(GPCreditors obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert GPCreditors into database
 	 * @author TechFinium 
 	 * @see    GPCreditors
 	 */
	public void gpcreditorsInsert() {
		try {
				 service.create(this.gpcreditors);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 gpcreditorsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update GPCreditors in database
 	 * @author TechFinium 
 	 * @see    GPCreditors
 	 */
    public void gpcreditorsUpdate() {
		try {
				 service.update(this.gpcreditors);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 gpcreditorsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete GPCreditors from database
 	 * @author TechFinium 
 	 * @see    GPCreditors
 	 */
	public void gpcreditorsDelete() {
		try {
			 service.delete(this.gpcreditors);
			  prepareNew();
			 gpcreditorsInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of GPCreditors 
 	 * @author TechFinium 
 	 * @see    GPCreditors
 	 */
	public void prepareNew() {
		gpcreditors = new GPCreditors();
	}

/*
    public List<SelectItem> getGPCreditorsDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	gpcreditorsInfo();
    	for (GPCreditors ug : gpcreditorsList) {
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
    public List<GPCreditors> complete(String desc) {
		List<GPCreditors> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		}
		catch (Exception e) {
		addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
    
    public List<GPCreditors> getGPCreditorsList() {
		return gpcreditorsList;
	}
	public void setGPCreditorsList(List<GPCreditors> gpcreditorsList) {
		this.gpcreditorsList = gpcreditorsList;
	}
	public GPCreditors getGpcreditors() {
		return gpcreditors;
	}
	public void setGpcreditors(GPCreditors gpcreditors) {
		this.gpcreditors = gpcreditors;
	}

    public List<GPCreditors> getGPCreditorsfilteredList() {
		return gpcreditorsfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param gpcreditorsfilteredList the new gpcreditorsfilteredList list
 	 * @see    GPCreditors
 	 */	
	public void setGPCreditorsfilteredList(List<GPCreditors> gpcreditorsfilteredList) {
		this.gpcreditorsfilteredList = gpcreditorsfilteredList;
	}

	
	public LazyDataModel<GPCreditors> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<GPCreditors> dataModel) {
		this.dataModel = dataModel;
	}
	
}
