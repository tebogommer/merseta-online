package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.AssessorModeratorCompany;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AssessorModeratorCompanyService;

// TODO: Auto-generated Javadoc
/**
 * The Class AssessorModeratorCompanyUI.
 */
@ManagedBean(name = "assessormoderatorcompanyUI")
@ViewScoped
public class AssessorModeratorCompanyUI extends AbstractUI {

	/** The service. */
	private AssessorModeratorCompanyService service = new AssessorModeratorCompanyService();
	
	/** The assessormoderatorcompany list. */
	private List<AssessorModeratorCompany> assessormoderatorcompanyList = null;
	
	/** The assessormoderatorcompanyfiltered list. */
	private List<AssessorModeratorCompany> assessormoderatorcompanyfilteredList = null;
	
	/** The assessormoderatorcompany. */
	private AssessorModeratorCompany assessormoderatorcompany = null;
	
	/** The data model. */
	private LazyDataModel<AssessorModeratorCompany> dataModel; 

    /**
     * Inits the.
     */
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
	 * Initialize method to get all AssessorModeratorCompany and prepare a for a create of a new AssessorModeratorCompany.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    AssessorModeratorCompany
	 */
	private void runInit() throws Exception {
		prepareNew();
		assessormoderatorcompanyInfo();
	}

	/**
	 * Get all AssessorModeratorCompany for data table.
	 *
	 * @author TechFinium
	 * @see    AssessorModeratorCompany
	 */
	public void assessormoderatorcompanyInfo() {
	 
			
			 dataModel = new LazyDataModel<AssessorModeratorCompany>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<AssessorModeratorCompany> retorno = new  ArrayList<AssessorModeratorCompany>();
			   
			   @Override 
			   public List<AssessorModeratorCompany> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allAssessorModeratorCompany(AssessorModeratorCompany.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(AssessorModeratorCompany.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(AssessorModeratorCompany obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public AssessorModeratorCompany getRowData(String rowKey) {
			        for(AssessorModeratorCompany obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert AssessorModeratorCompany into database.
	 *
	 * @author TechFinium
	 * @see    AssessorModeratorCompany
	 */
	public void assessormoderatorcompanyInsert() {
		try {
				 service.create(this.assessormoderatorcompany);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 assessormoderatorcompanyInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update AssessorModeratorCompany in database.
	 *
	 * @author TechFinium
	 * @see    AssessorModeratorCompany
	 */
    public void assessormoderatorcompanyUpdate() {
		try {
				 service.update(this.assessormoderatorcompany);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 assessormoderatorcompanyInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete AssessorModeratorCompany from database.
	 *
	 * @author TechFinium
	 * @see    AssessorModeratorCompany
	 */
	public void assessormoderatorcompanyDelete() {
		try {
			 service.delete(this.assessormoderatorcompany);
			  prepareNew();
			 assessormoderatorcompanyInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of AssessorModeratorCompany .
	 *
	 * @author TechFinium
	 * @see    AssessorModeratorCompany
	 */
	public void prepareNew() {
		assessormoderatorcompany = new AssessorModeratorCompany();
	}

/*
    public List<SelectItem> getAssessorModeratorCompanyDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	assessormoderatorcompanyInfo();
    	for (AssessorModeratorCompany ug : assessormoderatorcompanyList) {
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
    public List<AssessorModeratorCompany> complete(String desc) {
		List<AssessorModeratorCompany> l = null;
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
    
    /**
     * Gets the assessor moderator company list.
     *
     * @return the assessor moderator company list
     */
    public List<AssessorModeratorCompany> getAssessorModeratorCompanyList() {
		return assessormoderatorcompanyList;
	}
	
	/**
	 * Sets the assessor moderator company list.
	 *
	 * @param assessormoderatorcompanyList the new assessor moderator company list
	 */
	public void setAssessorModeratorCompanyList(List<AssessorModeratorCompany> assessormoderatorcompanyList) {
		this.assessormoderatorcompanyList = assessormoderatorcompanyList;
	}
	
	/**
	 * Gets the assessormoderatorcompany.
	 *
	 * @return the assessormoderatorcompany
	 */
	public AssessorModeratorCompany getAssessormoderatorcompany() {
		return assessormoderatorcompany;
	}
	
	/**
	 * Sets the assessormoderatorcompany.
	 *
	 * @param assessormoderatorcompany the new assessormoderatorcompany
	 */
	public void setAssessormoderatorcompany(AssessorModeratorCompany assessormoderatorcompany) {
		this.assessormoderatorcompany = assessormoderatorcompany;
	}

    /**
     * Gets the assessor moderator companyfiltered list.
     *
     * @return the assessor moderator companyfiltered list
     */
    public List<AssessorModeratorCompany> getAssessorModeratorCompanyfilteredList() {
		return assessormoderatorcompanyfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param assessormoderatorcompanyfilteredList the new assessormoderatorcompanyfilteredList list
	 * @see    AssessorModeratorCompany
	 */	
	public void setAssessorModeratorCompanyfilteredList(List<AssessorModeratorCompany> assessormoderatorcompanyfilteredList) {
		this.assessormoderatorcompanyfilteredList = assessormoderatorcompanyfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<AssessorModeratorCompany> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<AssessorModeratorCompany> dataModel) {
		this.dataModel = dataModel;
	}
	
}
