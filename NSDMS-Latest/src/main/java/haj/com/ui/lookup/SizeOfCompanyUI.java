package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.SizeOfCompany;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.SizeOfCompanyService;

@ManagedBean(name = "sizeofcompanyUI")
@ViewScoped
public class SizeOfCompanyUI extends AbstractUI {

	private SizeOfCompanyService service = new SizeOfCompanyService();
	private List<SizeOfCompany> sizeofcompanyList = null;
	private List<SizeOfCompany> sizeofcompanyfilteredList = null;
	private SizeOfCompany sizeofcompany = null;
	private LazyDataModel<SizeOfCompany> dataModel; 

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
	 * Initialize method to get all SizeOfCompany and prepare a for a create of a new SizeOfCompany
 	 * @author TechFinium 
 	 * @see    SizeOfCompany
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		sizeofcompanyInfo();
	}

	/**
	 * Get all SizeOfCompany for data table
 	 * @author TechFinium 
 	 * @see    SizeOfCompany
 	 */
	public void sizeofcompanyInfo() {
	 
			
			 dataModel = new LazyDataModel<SizeOfCompany>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<SizeOfCompany> retorno = new  ArrayList<SizeOfCompany>();
			   
			   @Override 
			   public List<SizeOfCompany> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allSizeOfCompany(SizeOfCompany.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(SizeOfCompany.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(SizeOfCompany obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public SizeOfCompany getRowData(String rowKey) {
			        for(SizeOfCompany obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert SizeOfCompany into database
 	 * @author TechFinium 
 	 * @see    SizeOfCompany
 	 */
	public void sizeofcompanyInsert() {
		try {
				 service.create(this.sizeofcompany);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 sizeofcompanyInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update SizeOfCompany in database
 	 * @author TechFinium 
 	 * @see    SizeOfCompany
 	 */
    public void sizeofcompanyUpdate() {
		try {
				 service.update(this.sizeofcompany);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 sizeofcompanyInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete SizeOfCompany from database
 	 * @author TechFinium 
 	 * @see    SizeOfCompany
 	 */
	public void sizeofcompanyDelete() {
		try {
			 service.delete(this.sizeofcompany);
			  prepareNew();
			 sizeofcompanyInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of SizeOfCompany 
 	 * @author TechFinium 
 	 * @see    SizeOfCompany
 	 */
	public void prepareNew() {
		sizeofcompany = new SizeOfCompany();
	}

/*
    public List<SelectItem> getSizeOfCompanyDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	sizeofcompanyInfo();
    	for (SizeOfCompany ug : sizeofcompanyList) {
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
    public List<SizeOfCompany> complete(String desc) {
		List<SizeOfCompany> l = null;
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
    
    public List<SizeOfCompany> getSizeOfCompanyList() {
		return sizeofcompanyList;
	}
	public void setSizeOfCompanyList(List<SizeOfCompany> sizeofcompanyList) {
		this.sizeofcompanyList = sizeofcompanyList;
	}
	public SizeOfCompany getSizeofcompany() {
		return sizeofcompany;
	}
	public void setSizeofcompany(SizeOfCompany sizeofcompany) {
		this.sizeofcompany = sizeofcompany;
	}

    public List<SizeOfCompany> getSizeOfCompanyfilteredList() {
		return sizeofcompanyfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param sizeofcompanyfilteredList the new sizeofcompanyfilteredList list
 	 * @see    SizeOfCompany
 	 */	
	public void setSizeOfCompanyfilteredList(List<SizeOfCompany> sizeofcompanyfilteredList) {
		this.sizeofcompanyfilteredList = sizeofcompanyfilteredList;
	}

	
	public LazyDataModel<SizeOfCompany> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SizeOfCompany> dataModel) {
		this.dataModel = dataModel;
	}
	
}
