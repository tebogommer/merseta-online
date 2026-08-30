package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.CompanyCommunication;
import haj.com.entity.CompanyLearnersTradeTest;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyCommunicationService;

@ManagedBean(name = "companycommunicationUI")
@ViewScoped
public class CompanyCommunicationUI extends AbstractUI {

	private CompanyCommunicationService service = new CompanyCommunicationService();
	private List<CompanyCommunication> companycommunicationList = null;
	private List<CompanyCommunication> companycommunicationfilteredList = null;
	private CompanyCommunication companycommunication = null;
	private LazyDataModel<CompanyCommunication> dataModel; 

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
	 * Initialize method to get all CompanyCommunication and prepare a for a create of a new CompanyCommunication
 	 * @author TechFinium 
 	 * @see    CompanyCommunication
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		companycommunicationInfo();
	}

	/**
	 * Get all CompanyCommunication for data table
 	 * @author TechFinium 
 	 * @see    CompanyCommunication
 	 */
	public void companycommunicationInfo() {
		dataModel = new LazyDataModel<CompanyCommunication>() {
			private static final long serialVersionUID = 1L;
			private List<CompanyCommunication> retorno = new ArrayList<CompanyCommunication>();
			@Override
			public List<CompanyCommunication> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = service.allCompanyCommunication(CompanyCommunication.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(CompanyCommunication.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(CompanyCommunication obj) {
				return obj.getId();
			}
			@Override
			public CompanyCommunication getRowData(String rowKey) {
				for (CompanyCommunication obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	/**
	 * Insert CompanyCommunication into database
 	 * @author TechFinium 
 	 * @see    CompanyCommunication
 	 */
	public void companycommunicationInsert() {
		try {
				 service.create(this.companycommunication);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 companycommunicationInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update CompanyCommunication in database
 	 * @author TechFinium 
 	 * @see    CompanyCommunication
 	 */
    public void companycommunicationUpdate() {
		try {
				 service.update(this.companycommunication);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 companycommunicationInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete CompanyCommunication from database
 	 * @author TechFinium 
 	 * @see    CompanyCommunication
 	 */
	public void companycommunicationDelete() {
		try {
			 service.delete(this.companycommunication);
			  prepareNew();
			 companycommunicationInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of CompanyCommunication 
 	 * @author TechFinium 
 	 * @see    CompanyCommunication
 	 */
	public void prepareNew() {
		companycommunication = new CompanyCommunication();
	}

/*
    public List<SelectItem> getCompanyCommunicationDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	companycommunicationInfo();
    	for (CompanyCommunication ug : companycommunicationList) {
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
    public List<CompanyCommunication> complete(String desc) {
		List<CompanyCommunication> l = null;
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
    
    public List<CompanyCommunication> getCompanyCommunicationList() {
		return companycommunicationList;
	}
	public void setCompanyCommunicationList(List<CompanyCommunication> companycommunicationList) {
		this.companycommunicationList = companycommunicationList;
	}
	public CompanyCommunication getCompanycommunication() {
		return companycommunication;
	}
	public void setCompanycommunication(CompanyCommunication companycommunication) {
		this.companycommunication = companycommunication;
	}

    public List<CompanyCommunication> getCompanyCommunicationfilteredList() {
		return companycommunicationfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param companycommunicationfilteredList the new companycommunicationfilteredList list
 	 * @see    CompanyCommunication
 	 */	
	public void setCompanyCommunicationfilteredList(List<CompanyCommunication> companycommunicationfilteredList) {
		this.companycommunicationfilteredList = companycommunicationfilteredList;
	}

	
	public LazyDataModel<CompanyCommunication> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<CompanyCommunication> dataModel) {
		this.dataModel = dataModel;
	}
	
}
