package  haj.com.wsp.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.MandatoryGrant;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.MandatoryGrantService;

@ManagedBean(name = "mandatorygrantUI")
@ViewScoped
public class MandatoryGrantUI extends AbstractUI {

	private MandatoryGrantService service = new MandatoryGrantService();
	private List<MandatoryGrant> mandatorygrantList = null;
	private List<MandatoryGrant> mandatorygrantfilteredList = null;
	private MandatoryGrant mandatorygrant = null;
	private LazyDataModel<MandatoryGrant> dataModel; 

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
	 * Initialize method to get all MandatoryGrant and prepare a for a create of a new MandatoryGrant
 	 * @author TechFinium 
 	 * @see    MandatoryGrant
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		mandatorygrantInfo();
	}

	/**
	 * Get all MandatoryGrant for data table
 	 * @author TechFinium 
 	 * @see    MandatoryGrant
 	 */
	public void mandatorygrantInfo() {
	 
			
			 dataModel = new LazyDataModel<MandatoryGrant>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<MandatoryGrant> retorno = new  ArrayList<MandatoryGrant>();
			   
			   @Override 
			   public List<MandatoryGrant> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allMandatoryGrant(MandatoryGrant.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(MandatoryGrant.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(MandatoryGrant obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public MandatoryGrant getRowData(String rowKey) {
			        for(MandatoryGrant obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert MandatoryGrant into database
 	 * @author TechFinium 
 	 * @see    MandatoryGrant
 	 */
	public void mandatorygrantInsert() {
		try {
				 service.create(this.mandatorygrant);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 mandatorygrantInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update MandatoryGrant in database
 	 * @author TechFinium 
 	 * @see    MandatoryGrant
 	 */
    public void mandatorygrantUpdate() {
		try {
				 service.update(this.mandatorygrant);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 mandatorygrantInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete MandatoryGrant from database
 	 * @author TechFinium 
 	 * @see    MandatoryGrant
 	 */
	public void mandatorygrantDelete() {
		try {
			 service.delete(this.mandatorygrant);
			  prepareNew();
			 mandatorygrantInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of MandatoryGrant 
 	 * @author TechFinium 
 	 * @see    MandatoryGrant
 	 */
	public void prepareNew() {
		mandatorygrant = new MandatoryGrant();
	}

/*
    public List<SelectItem> getMandatoryGrantDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	mandatorygrantInfo();
    	for (MandatoryGrant ug : mandatorygrantList) {
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
    public List<MandatoryGrant> complete(String desc) {
		List<MandatoryGrant> l = null;
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
    
    public List<MandatoryGrant> getMandatoryGrantList() {
		return mandatorygrantList;
	}
	public void setMandatoryGrantList(List<MandatoryGrant> mandatorygrantList) {
		this.mandatorygrantList = mandatorygrantList;
	}
	public MandatoryGrant getMandatorygrant() {
		return mandatorygrant;
	}
	public void setMandatorygrant(MandatoryGrant mandatorygrant) {
		this.mandatorygrant = mandatorygrant;
	}

    public List<MandatoryGrant> getMandatoryGrantfilteredList() {
		return mandatorygrantfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param mandatorygrantfilteredList the new mandatorygrantfilteredList list
 	 * @see    MandatoryGrant
 	 */	
	public void setMandatoryGrantfilteredList(List<MandatoryGrant> mandatorygrantfilteredList) {
		this.mandatorygrantfilteredList = mandatorygrantfilteredList;
	}

	
	public LazyDataModel<MandatoryGrant> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<MandatoryGrant> dataModel) {
		this.dataModel = dataModel;
	}
	
}
