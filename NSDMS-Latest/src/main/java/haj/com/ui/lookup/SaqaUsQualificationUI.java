package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.SaqaUsQualification;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.SaqaUsQualificationService;

@ManagedBean(name = "saqausqualificationUI")
@ViewScoped
public class SaqaUsQualificationUI extends AbstractUI {

	private SaqaUsQualificationService service = new SaqaUsQualificationService();
	private List<SaqaUsQualification> saqausqualificationList = null;
	private List<SaqaUsQualification> saqausqualificationfilteredList = null;
	private SaqaUsQualification saqausqualification = null;
	private LazyDataModel<SaqaUsQualification> dataModel; 

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
	 * Initialize method to get all SaqaUsQualification and prepare a for a create of a new SaqaUsQualification
 	 * @author TechFinium 
 	 * @see    SaqaUsQualification
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		saqausqualificationInfo();
	}

	/**
	 * Get all SaqaUsQualification for data table
 	 * @author TechFinium 
 	 * @see    SaqaUsQualification
 	 */
	public void saqausqualificationInfo() {
	 
			
			 dataModel = new LazyDataModel<SaqaUsQualification>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<SaqaUsQualification> retorno = new  ArrayList<SaqaUsQualification>();
			   
			   @Override 
			   public List<SaqaUsQualification> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allSaqaUsQualification(SaqaUsQualification.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(SaqaUsQualification.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(SaqaUsQualification obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public SaqaUsQualification getRowData(String rowKey) {
			        for(SaqaUsQualification obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert SaqaUsQualification into database
 	 * @author TechFinium 
 	 * @see    SaqaUsQualification
 	 */
	public void saqausqualificationInsert() {
		try {
				 service.create(this.saqausqualification);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 saqausqualificationInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update SaqaUsQualification in database
 	 * @author TechFinium 
 	 * @see    SaqaUsQualification
 	 */
    public void saqausqualificationUpdate() {
		try {
				 service.update(this.saqausqualification);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 saqausqualificationInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete SaqaUsQualification from database
 	 * @author TechFinium 
 	 * @see    SaqaUsQualification
 	 */
	public void saqausqualificationDelete() {
		try {
			 service.delete(this.saqausqualification);
			  prepareNew();
			 saqausqualificationInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of SaqaUsQualification 
 	 * @author TechFinium 
 	 * @see    SaqaUsQualification
 	 */
	public void prepareNew() {
		saqausqualification = new SaqaUsQualification();
	}

/*
    public List<SelectItem> getSaqaUsQualificationDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	saqausqualificationInfo();
    	for (SaqaUsQualification ug : saqausqualificationList) {
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
    public List<SaqaUsQualification> complete(String desc) {
		List<SaqaUsQualification> l = null;
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
    
    public List<SaqaUsQualification> getSaqaUsQualificationList() {
		return saqausqualificationList;
	}
	public void setSaqaUsQualificationList(List<SaqaUsQualification> saqausqualificationList) {
		this.saqausqualificationList = saqausqualificationList;
	}
	public SaqaUsQualification getSaqausqualification() {
		return saqausqualification;
	}
	public void setSaqausqualification(SaqaUsQualification saqausqualification) {
		this.saqausqualification = saqausqualification;
	}

    public List<SaqaUsQualification> getSaqaUsQualificationfilteredList() {
		return saqausqualificationfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param saqausqualificationfilteredList the new saqausqualificationfilteredList list
 	 * @see    SaqaUsQualification
 	 */	
	public void setSaqaUsQualificationfilteredList(List<SaqaUsQualification> saqausqualificationfilteredList) {
		this.saqausqualificationfilteredList = saqausqualificationfilteredList;
	}

	
	public LazyDataModel<SaqaUsQualification> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SaqaUsQualification> dataModel) {
		this.dataModel = dataModel;
	}
	
}
