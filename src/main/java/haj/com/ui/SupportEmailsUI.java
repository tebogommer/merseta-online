package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.SupportEmails;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.SupportEmailsService;

@ManagedBean(name = "supportemailsUI")
@ViewScoped
public class SupportEmailsUI extends AbstractUI {

	private SupportEmailsService service = new SupportEmailsService();
	private List<SupportEmails> supportemailsList = null;
	private List<SupportEmails> supportemailsfilteredList = null;
	private SupportEmails supportemails = null;
	private LazyDataModel<SupportEmails> dataModel; 

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
	 * Initialize method to get all SupportEmails and prepare a for a create of a new SupportEmails
 	 * @author TechFinium 
 	 * @see    SupportEmails
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		supportemailsInfo();
	}

	/**
	 * Get all SupportEmails for data table
 	 * @author TechFinium 
 	 * @see    SupportEmails
 	 */
	public void supportemailsInfo() {
	 
			
			 dataModel = new LazyDataModel<SupportEmails>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<SupportEmails> retorno = new  ArrayList<SupportEmails>();
			   
			   @Override 
			   public List<SupportEmails> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allSupportEmails(SupportEmails.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(SupportEmails.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(SupportEmails obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public SupportEmails getRowData(String rowKey) {
			        for(SupportEmails obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert SupportEmails into database
 	 * @author TechFinium 
 	 * @see    SupportEmails
 	 */
	public void supportemailsInsert() {
		try {
				 service.create(this.supportemails);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 supportemailsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update SupportEmails in database
 	 * @author TechFinium 
 	 * @see    SupportEmails
 	 */
    public void supportemailsUpdate() {
		try {
				 service.update(this.supportemails);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 supportemailsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete SupportEmails from database
 	 * @author TechFinium 
 	 * @see    SupportEmails
 	 */
	public void supportemailsDelete() {
		try {
			 service.delete(this.supportemails);
			  prepareNew();
			 supportemailsInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of SupportEmails 
 	 * @author TechFinium 
 	 * @see    SupportEmails
 	 */
	public void prepareNew() {
		supportemails = new SupportEmails();
	}

/*
    public List<SelectItem> getSupportEmailsDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	supportemailsInfo();
    	for (SupportEmails ug : supportemailsList) {
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
    public List<SupportEmails> complete(String desc) {
		List<SupportEmails> l = null;
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
    
    public List<SupportEmails> getSupportEmailsList() {
		return supportemailsList;
	}
	public void setSupportEmailsList(List<SupportEmails> supportemailsList) {
		this.supportemailsList = supportemailsList;
	}
	public SupportEmails getSupportemails() {
		return supportemails;
	}
	public void setSupportemails(SupportEmails supportemails) {
		this.supportemails = supportemails;
	}

    public List<SupportEmails> getSupportEmailsfilteredList() {
		return supportemailsfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param supportemailsfilteredList the new supportemailsfilteredList list
 	 * @see    SupportEmails
 	 */	
	public void setSupportEmailsfilteredList(List<SupportEmails> supportemailsfilteredList) {
		this.supportemailsfilteredList = supportemailsfilteredList;
	}

	
	public LazyDataModel<SupportEmails> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SupportEmails> dataModel) {
		this.dataModel = dataModel;
	}
	
}
