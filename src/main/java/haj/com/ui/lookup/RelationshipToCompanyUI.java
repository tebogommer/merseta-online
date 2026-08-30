package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.RelationshipToCompany;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.RelationshipToCompanyService;

@ManagedBean(name = "relationshiptocompanyUI")
@ViewScoped
public class RelationshipToCompanyUI extends AbstractUI {

	private RelationshipToCompanyService service = new RelationshipToCompanyService();
	private List<RelationshipToCompany> relationshiptocompanyList = null;
	private List<RelationshipToCompany> relationshiptocompanyfilteredList = null;
	private RelationshipToCompany relationshiptocompany = null;
	private LazyDataModel<RelationshipToCompany> dataModel; 

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
	 * Initialize method to get all RelationshipToCompany and prepare a for a create of a new RelationshipToCompany
 	 * @author TechFinium 
 	 * @see    RelationshipToCompany
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		relationshiptocompanyInfo();
	}

	/**
	 * Get all RelationshipToCompany for data table
 	 * @author TechFinium 
 	 * @see    RelationshipToCompany
 	 */
	public void relationshiptocompanyInfo() {
	 
			
			 dataModel = new LazyDataModel<RelationshipToCompany>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<RelationshipToCompany> retorno = new  ArrayList<RelationshipToCompany>();
			   
			   @Override 
			   public List<RelationshipToCompany> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allRelationshipToCompany(RelationshipToCompany.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(RelationshipToCompany.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(RelationshipToCompany obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public RelationshipToCompany getRowData(String rowKey) {
			        for(RelationshipToCompany obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert RelationshipToCompany into database
 	 * @author TechFinium 
 	 * @see    RelationshipToCompany
 	 */
	public void relationshiptocompanyInsert() {
		try {
				 service.create(this.relationshiptocompany);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 relationshiptocompanyInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update RelationshipToCompany in database
 	 * @author TechFinium 
 	 * @see    RelationshipToCompany
 	 */
    public void relationshiptocompanyUpdate() {
		try {
				 service.update(this.relationshiptocompany);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 relationshiptocompanyInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete RelationshipToCompany from database
 	 * @author TechFinium 
 	 * @see    RelationshipToCompany
 	 */
	public void relationshiptocompanyDelete() {
		try {
			 service.delete(this.relationshiptocompany);
			  prepareNew();
			 relationshiptocompanyInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of RelationshipToCompany 
 	 * @author TechFinium 
 	 * @see    RelationshipToCompany
 	 */
	public void prepareNew() {
		relationshiptocompany = new RelationshipToCompany();
	}

/*
    public List<SelectItem> getRelationshipToCompanyDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	relationshiptocompanyInfo();
    	for (RelationshipToCompany ug : relationshiptocompanyList) {
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
    public List<RelationshipToCompany> complete(String desc) {
		List<RelationshipToCompany> l = null;
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
    
    public List<RelationshipToCompany> getRelationshipToCompanyList() {
		return relationshiptocompanyList;
	}
	public void setRelationshipToCompanyList(List<RelationshipToCompany> relationshiptocompanyList) {
		this.relationshiptocompanyList = relationshiptocompanyList;
	}
	public RelationshipToCompany getRelationshiptocompany() {
		return relationshiptocompany;
	}
	public void setRelationshiptocompany(RelationshipToCompany relationshiptocompany) {
		this.relationshiptocompany = relationshiptocompany;
	}

    public List<RelationshipToCompany> getRelationshipToCompanyfilteredList() {
		return relationshiptocompanyfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param relationshiptocompanyfilteredList the new relationshiptocompanyfilteredList list
 	 * @see    RelationshipToCompany
 	 */	
	public void setRelationshipToCompanyfilteredList(List<RelationshipToCompany> relationshiptocompanyfilteredList) {
		this.relationshiptocompanyfilteredList = relationshiptocompanyfilteredList;
	}

	
	public LazyDataModel<RelationshipToCompany> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<RelationshipToCompany> dataModel) {
		this.dataModel = dataModel;
	}
	
}
