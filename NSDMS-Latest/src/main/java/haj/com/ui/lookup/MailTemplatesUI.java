package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.MailTemplates;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.MailTemplatesService;

@ManagedBean(name = "mailtemplatesUI")
@ViewScoped
public class MailTemplatesUI extends AbstractUI {

	private MailTemplatesService service = new MailTemplatesService();
	private List<MailTemplates> mailtemplatesList = null;
	private List<MailTemplates> mailtemplatesfilteredList = null;
	private MailTemplates mailtemplates = null;
	private LazyDataModel<MailTemplates> dataModel; 

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
	 * Initialize method to get all MailTemplates and prepare a for a create of a new MailTemplates
 	 * @author TechFinium 
 	 * @see    MailTemplates
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		mailtemplatesInfo();
	}

	/**
	 * Get all MailTemplates for data table
 	 * @author TechFinium 
 	 * @see    MailTemplates
 	 */
	public void mailtemplatesInfo() {
	 
			
			 dataModel = new LazyDataModel<MailTemplates>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<MailTemplates> retorno = new  ArrayList<MailTemplates>();
			   
			   @Override 
			   public List<MailTemplates> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allMailTemplates(MailTemplates.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(MailTemplates.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(MailTemplates obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public MailTemplates getRowData(String rowKey) {
			        for(MailTemplates obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert MailTemplates into database
 	 * @author TechFinium 
 	 * @see    MailTemplates
 	 */
	public void mailtemplatesInsert() {
		try {
				 service.create(this.mailtemplates);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 mailtemplatesInfo();
		    } catch (org.hibernate.exception.ConstraintViolationException cv) {
		      	addErrorMessage("Code has already been used");
		    
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update MailTemplates in database
 	 * @author TechFinium 
 	 * @see    MailTemplates
 	 */
    public void mailtemplatesUpdate() {
		try {
				 service.update(this.mailtemplates);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 mailtemplatesInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete MailTemplates from database
 	 * @author TechFinium 
 	 * @see    MailTemplates
 	 */
	public void mailtemplatesDelete() {
		try {
			 service.delete(this.mailtemplates);
			  prepareNew();
			 mailtemplatesInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of MailTemplates 
 	 * @author TechFinium 
 	 * @see    MailTemplates
 	 */
	public void prepareNew() {
		mailtemplates = new MailTemplates();
	}

/*
    public List<SelectItem> getMailTemplatesDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	mailtemplatesInfo();
    	for (MailTemplates ug : mailtemplatesList) {
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
    public List<MailTemplates> complete(String desc) {
		List<MailTemplates> l = null;
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
    
    public List<MailTemplates> getMailTemplatesList() {
		return mailtemplatesList;
	}
	public void setMailTemplatesList(List<MailTemplates> mailtemplatesList) {
		this.mailtemplatesList = mailtemplatesList;
	}
	public MailTemplates getMailtemplates() {
		return mailtemplates;
	}
	public void setMailtemplates(MailTemplates mailtemplates) {
		this.mailtemplates = mailtemplates;
	}

    public List<MailTemplates> getMailTemplatesfilteredList() {
		return mailtemplatesfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param mailtemplatesfilteredList the new mailtemplatesfilteredList list
 	 * @see    MailTemplates
 	 */	
	public void setMailTemplatesfilteredList(List<MailTemplates> mailtemplatesfilteredList) {
		this.mailtemplatesfilteredList = mailtemplatesfilteredList;
	}

	
	public LazyDataModel<MailTemplates> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<MailTemplates> dataModel) {
		this.dataModel = dataModel;
	}
	
}
