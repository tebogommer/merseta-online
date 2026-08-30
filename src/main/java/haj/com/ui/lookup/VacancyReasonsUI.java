package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.VacancyReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.VacancyReasonsService;

@ManagedBean(name = "vacancyreasonsUI")
@ViewScoped
public class VacancyReasonsUI extends AbstractUI {

	private VacancyReasonsService service = new VacancyReasonsService();
	private List<VacancyReasons> vacancyreasonsList = null;
	private List<VacancyReasons> vacancyreasonsfilteredList = null;
	private VacancyReasons vacancyreasons = null;
	private LazyDataModel<VacancyReasons> dataModel; 

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
	 * Initialize method to get all VacancyReasons and prepare a for a create of a new VacancyReasons
 	 * @author TechFinium 
 	 * @see    VacancyReasons
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		vacancyreasonsInfo();
	}

	/**
	 * Get all VacancyReasons for data table
 	 * @author TechFinium 
 	 * @see    VacancyReasons
 	 */
	public void vacancyreasonsInfo() {
	 
			
			 dataModel = new LazyDataModel<VacancyReasons>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<VacancyReasons> retorno = new  ArrayList<VacancyReasons>();
			   
			   @Override 
			   public List<VacancyReasons> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allVacancyReasons(VacancyReasons.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(VacancyReasons.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(VacancyReasons obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public VacancyReasons getRowData(String rowKey) {
			        for(VacancyReasons obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert VacancyReasons into database
 	 * @author TechFinium 
 	 * @see    VacancyReasons
 	 */
	public void vacancyreasonsInsert() {
		try {
				 service.create(this.vacancyreasons);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 vacancyreasonsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update VacancyReasons in database
 	 * @author TechFinium 
 	 * @see    VacancyReasons
 	 */
    public void vacancyreasonsUpdate() {
		try {
				 service.update(this.vacancyreasons);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 vacancyreasonsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete VacancyReasons from database
 	 * @author TechFinium 
 	 * @see    VacancyReasons
 	 */
	public void vacancyreasonsDelete() {
		try {
			 service.delete(this.vacancyreasons);
			  prepareNew();
			 vacancyreasonsInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of VacancyReasons 
 	 * @author TechFinium 
 	 * @see    VacancyReasons
 	 */
	public void prepareNew() {
		vacancyreasons = new VacancyReasons();
	}

/*
    public List<SelectItem> getVacancyReasonsDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	vacancyreasonsInfo();
    	for (VacancyReasons ug : vacancyreasonsList) {
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
    public List<VacancyReasons> complete(String desc) {
		List<VacancyReasons> l = null;
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
    
    public List<VacancyReasons> getVacancyReasonsList() {
		return vacancyreasonsList;
	}
	public void setVacancyReasonsList(List<VacancyReasons> vacancyreasonsList) {
		this.vacancyreasonsList = vacancyreasonsList;
	}
	public VacancyReasons getVacancyreasons() {
		return vacancyreasons;
	}
	public void setVacancyreasons(VacancyReasons vacancyreasons) {
		this.vacancyreasons = vacancyreasons;
	}

    public List<VacancyReasons> getVacancyReasonsfilteredList() {
		return vacancyreasonsfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param vacancyreasonsfilteredList the new vacancyreasonsfilteredList list
 	 * @see    VacancyReasons
 	 */	
	public void setVacancyReasonsfilteredList(List<VacancyReasons> vacancyreasonsfilteredList) {
		this.vacancyreasonsfilteredList = vacancyreasonsfilteredList;
	}

	
	public LazyDataModel<VacancyReasons> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<VacancyReasons> dataModel) {
		this.dataModel = dataModel;
	}
	
}
