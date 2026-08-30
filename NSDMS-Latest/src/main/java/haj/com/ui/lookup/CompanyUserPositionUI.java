package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.CompanyUserPosition;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.CompanyUserPositionService;

// TODO: Auto-generated Javadoc
/**
 * The Class CompanyUserPositionUI.
 */
@ManagedBean(name = "companyuserpositionUI")
@ViewScoped
public class CompanyUserPositionUI extends AbstractUI {

	/** The service. */
	private CompanyUserPositionService service = new CompanyUserPositionService();
	
	/** The companyuserposition list. */
	private List<CompanyUserPosition> companyuserpositionList = null;
	
	/** The companyuserpositionfiltered list. */
	private List<CompanyUserPosition> companyuserpositionfilteredList = null;
	
	/** The companyuserposition. */
	private CompanyUserPosition companyuserposition = null;
	
	/** The data model. */
	private LazyDataModel<CompanyUserPosition> dataModel; 

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
	 * Initialize method to get all CompanyUserPosition and prepare a for a create of a new CompanyUserPosition.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    CompanyUserPosition
	 */
	private void runInit() throws Exception {
		prepareNew();
		companyuserpositionInfo();
	}

	/**
	 * Get all CompanyUserPosition for data table.
	 *
	 * @author TechFinium
	 * @see    CompanyUserPosition
	 */
	public void companyuserpositionInfo() {
	 
			
			 dataModel = new LazyDataModel<CompanyUserPosition>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<CompanyUserPosition> retorno = new  ArrayList<CompanyUserPosition>();
			   
			   @Override 
			   public List<CompanyUserPosition> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allCompanyUserPosition(CompanyUserPosition.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(CompanyUserPosition.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(CompanyUserPosition obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public CompanyUserPosition getRowData(String rowKey) {
			        for(CompanyUserPosition obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert CompanyUserPosition into database.
	 *
	 * @author TechFinium
	 * @see    CompanyUserPosition
	 */
	public void companyuserpositionInsert() {
		try {
				 service.create(this.companyuserposition);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 companyuserpositionInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update CompanyUserPosition in database.
	 *
	 * @author TechFinium
	 * @see    CompanyUserPosition
	 */
    public void companyuserpositionUpdate() {
		try {
				 service.update(this.companyuserposition);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 companyuserpositionInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete CompanyUserPosition from database.
	 *
	 * @author TechFinium
	 * @see    CompanyUserPosition
	 */
	public void companyuserpositionDelete() {
		try {
			 service.delete(this.companyuserposition);
			  prepareNew();
			 companyuserpositionInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of CompanyUserPosition .
	 *
	 * @author TechFinium
	 * @see    CompanyUserPosition
	 */
	public void prepareNew() {
		companyuserposition = new CompanyUserPosition();
	}

/*
    public List<SelectItem> getCompanyUserPositionDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	companyuserpositionInfo();
    	for (CompanyUserPosition ug : companyuserpositionList) {
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
    public List<CompanyUserPosition> complete(String desc) {
		List<CompanyUserPosition> l = null;
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
     * Gets the company user position list.
     *
     * @return the company user position list
     */
    public List<CompanyUserPosition> getCompanyUserPositionList() {
		return companyuserpositionList;
	}
	
	/**
	 * Sets the company user position list.
	 *
	 * @param companyuserpositionList the new company user position list
	 */
	public void setCompanyUserPositionList(List<CompanyUserPosition> companyuserpositionList) {
		this.companyuserpositionList = companyuserpositionList;
	}
	
	/**
	 * Gets the companyuserposition.
	 *
	 * @return the companyuserposition
	 */
	public CompanyUserPosition getCompanyuserposition() {
		return companyuserposition;
	}
	
	/**
	 * Sets the companyuserposition.
	 *
	 * @param companyuserposition the new companyuserposition
	 */
	public void setCompanyuserposition(CompanyUserPosition companyuserposition) {
		this.companyuserposition = companyuserposition;
	}

    /**
     * Gets the company user positionfiltered list.
     *
     * @return the company user positionfiltered list
     */
    public List<CompanyUserPosition> getCompanyUserPositionfilteredList() {
		return companyuserpositionfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param companyuserpositionfilteredList the new companyuserpositionfilteredList list
	 * @see    CompanyUserPosition
	 */	
	public void setCompanyUserPositionfilteredList(List<CompanyUserPosition> companyuserpositionfilteredList) {
		this.companyuserpositionfilteredList = companyuserpositionfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<CompanyUserPosition> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<CompanyUserPosition> dataModel) {
		this.dataModel = dataModel;
	}
	
}
