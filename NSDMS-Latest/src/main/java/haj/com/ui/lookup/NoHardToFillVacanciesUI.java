package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.NoHardToFillVacancies;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.NoHardToFillVacanciesService;

@ManagedBean(name = "nohardtofillvacanciesUI")
@ViewScoped
public class NoHardToFillVacanciesUI extends AbstractUI {

	private NoHardToFillVacanciesService service = new NoHardToFillVacanciesService();
	private List<NoHardToFillVacancies> nohardtofillvacanciesList = null;
	private List<NoHardToFillVacancies> nohardtofillvacanciesfilteredList = null;
	private NoHardToFillVacancies nohardtofillvacancies = null;
	private LazyDataModel<NoHardToFillVacancies> dataModel; 

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
	 * Initialize method to get all NoHardToFillVacancies and prepare a for a create of a new NoHardToFillVacancies
 	 * @author TechFinium 
 	 * @see    NoHardToFillVacancies
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		nohardtofillvacanciesInfo();
	}

	/**
	 * Get all NoHardToFillVacancies for data table
 	 * @author TechFinium 
 	 * @see    NoHardToFillVacancies
 	 */
	public void nohardtofillvacanciesInfo() {
	 
			
			 dataModel = new LazyDataModel<NoHardToFillVacancies>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<NoHardToFillVacancies> retorno = new  ArrayList<NoHardToFillVacancies>();
			   
			   @Override 
			   public List<NoHardToFillVacancies> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allNoHardToFillVacancies(NoHardToFillVacancies.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(NoHardToFillVacancies.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(NoHardToFillVacancies obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public NoHardToFillVacancies getRowData(String rowKey) {
			        for(NoHardToFillVacancies obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert NoHardToFillVacancies into database
 	 * @author TechFinium 
 	 * @see    NoHardToFillVacancies
 	 */
	public void nohardtofillvacanciesInsert() {
		try {
				 service.create(this.nohardtofillvacancies);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 nohardtofillvacanciesInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update NoHardToFillVacancies in database
 	 * @author TechFinium 
 	 * @see    NoHardToFillVacancies
 	 */
    public void nohardtofillvacanciesUpdate() {
		try {
				 service.update(this.nohardtofillvacancies);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 nohardtofillvacanciesInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete NoHardToFillVacancies from database
 	 * @author TechFinium 
 	 * @see    NoHardToFillVacancies
 	 */
	public void nohardtofillvacanciesDelete() {
		try {
			 service.delete(this.nohardtofillvacancies);
			  prepareNew();
			 nohardtofillvacanciesInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of NoHardToFillVacancies 
 	 * @author TechFinium 
 	 * @see    NoHardToFillVacancies
 	 */
	public void prepareNew() {
		nohardtofillvacancies = new NoHardToFillVacancies();
	}

/*
    public List<SelectItem> getNoHardToFillVacanciesDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	nohardtofillvacanciesInfo();
    	for (NoHardToFillVacancies ug : nohardtofillvacanciesList) {
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
    public List<NoHardToFillVacancies> complete(String desc) {
		List<NoHardToFillVacancies> l = null;
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
    
    public List<NoHardToFillVacancies> getNoHardToFillVacanciesList() {
		return nohardtofillvacanciesList;
	}
	public void setNoHardToFillVacanciesList(List<NoHardToFillVacancies> nohardtofillvacanciesList) {
		this.nohardtofillvacanciesList = nohardtofillvacanciesList;
	}
	public NoHardToFillVacancies getNohardtofillvacancies() {
		return nohardtofillvacancies;
	}
	public void setNohardtofillvacancies(NoHardToFillVacancies nohardtofillvacancies) {
		this.nohardtofillvacancies = nohardtofillvacancies;
	}

    public List<NoHardToFillVacancies> getNoHardToFillVacanciesfilteredList() {
		return nohardtofillvacanciesfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param nohardtofillvacanciesfilteredList the new nohardtofillvacanciesfilteredList list
 	 * @see    NoHardToFillVacancies
 	 */	
	public void setNoHardToFillVacanciesfilteredList(List<NoHardToFillVacancies> nohardtofillvacanciesfilteredList) {
		this.nohardtofillvacanciesfilteredList = nohardtofillvacanciesfilteredList;
	}

	
	public LazyDataModel<NoHardToFillVacancies> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<NoHardToFillVacancies> dataModel) {
		this.dataModel = dataModel;
	}
	
}
