package  haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.lookup.UnionMembership;
import haj.com.service.lookup.UnionMembershipService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "unionmembershipUI")
@ViewScoped
public class UnionMembershipUI extends AbstractUI {

	private UnionMembershipService service = new UnionMembershipService();
	private List<UnionMembership> unionmembershipList = null;
	private List<UnionMembership> unionmembershipfilteredList = null;
	private UnionMembership unionmembership = null;
	private LazyDataModel<UnionMembership> dataModel; 

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
	 * Initialize method to get all UnionMembership and prepare a for a create of a new UnionMembership
 	 * @author TechFinium 
 	 * @see    UnionMembership
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		unionmembershipInfo();
	}

	/**
	 * Get all UnionMembership for data table
 	 * @author TechFinium 
 	 * @see    UnionMembership
 	 */
	public void unionmembershipInfo() {
	 
			
			 dataModel = new LazyDataModel<UnionMembership>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<UnionMembership> retorno = new  ArrayList<UnionMembership>();
			   
			   @Override 
			   public List<UnionMembership> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allUnionMembership(UnionMembership.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(UnionMembership.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(UnionMembership obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public UnionMembership getRowData(String rowKey) {
			        for(UnionMembership obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert UnionMembership into database
 	 * @author TechFinium 
 	 * @see    UnionMembership
 	 */
	public void unionmembershipInsert() {
		try {
				 service.create(this.unionmembership);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 unionmembershipInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update UnionMembership in database
 	 * @author TechFinium 
 	 * @see    UnionMembership
 	 */
    public void unionmembershipUpdate() {
		try {
				 service.update(this.unionmembership);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 unionmembershipInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete UnionMembership from database
 	 * @author TechFinium 
 	 * @see    UnionMembership
 	 */
	public void unionmembershipDelete() {
		try {
			 service.delete(this.unionmembership);
			  prepareNew();
			 unionmembershipInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of UnionMembership 
 	 * @author TechFinium 
 	 * @see    UnionMembership
 	 */
	public void prepareNew() {
		unionmembership = new UnionMembership();
	}

/*
    public List<SelectItem> getUnionMembershipDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	unionmembershipInfo();
    	for (UnionMembership ug : unionmembershipList) {
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
    public List<UnionMembership> complete(String desc) {
		List<UnionMembership> l = null;
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
    
    public List<UnionMembership> getUnionMembershipList() {
		return unionmembershipList;
	}
	public void setUnionMembershipList(List<UnionMembership> unionmembershipList) {
		this.unionmembershipList = unionmembershipList;
	}
	public UnionMembership getUnionmembership() {
		return unionmembership;
	}
	public void setUnionmembership(UnionMembership unionmembership) {
		this.unionmembership = unionmembership;
	}

    public List<UnionMembership> getUnionMembershipfilteredList() {
		return unionmembershipfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param unionmembershipfilteredList the new unionmembershipfilteredList list
 	 * @see    UnionMembership
 	 */	
	public void setUnionMembershipfilteredList(List<UnionMembership> unionmembershipfilteredList) {
		this.unionmembershipfilteredList = unionmembershipfilteredList;
	}

	
	public LazyDataModel<UnionMembership> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<UnionMembership> dataModel) {
		this.dataModel = dataModel;
	}
	
}
