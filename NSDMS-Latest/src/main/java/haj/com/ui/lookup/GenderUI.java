package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.Gender;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.GenderService;

// TODO: Auto-generated Javadoc
/**
 * The Class GenderUI.
 */
@ManagedBean(name = "genderUI")
@ViewScoped
public class GenderUI extends AbstractUI {

	/** The service. */
	private GenderService service = new GenderService();
	
	/** The gender list. */
	private List<Gender> genderList = null;
	
	/** The genderfiltered list. */
	private List<Gender> genderfilteredList = null;
	
	/** The gender. */
	private Gender gender = null;
	
	/** The data model. */
	private LazyDataModel<Gender> dataModel; 

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
	 * Initialize method to get all Gender and prepare a for a create of a new Gender.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    Gender
	 */
	private void runInit() throws Exception {
		prepareNew();
		genderInfo();
		genderList = service.allGender();
	}

	/**
	 * Get all Gender for data table.
	 *
	 * @author TechFinium
	 * @see    Gender
	 */
	public void genderInfo() {
	 
			
			 dataModel = new LazyDataModel<Gender>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<Gender> retorno = new  ArrayList<Gender>();
			   
			   @Override 
			   public List<Gender> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allGender(Gender.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(Gender.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(Gender obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public Gender getRowData(String rowKey) {
			        for(Gender obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert Gender into database.
	 *
	 * @author TechFinium
	 * @see    Gender
	 */
	public void genderInsert() {
		try {
				 service.create(this.gender);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 genderInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update Gender in database.
	 *
	 * @author TechFinium
	 * @see    Gender
	 */
    public void genderUpdate() {
		try {
				 service.update(this.gender);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 genderInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete Gender from database.
	 *
	 * @author TechFinium
	 * @see    Gender
	 */
	public void genderDelete() {
		try {
			 service.delete(this.gender);
			  prepareNew();
			 genderInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of Gender .
	 *
	 * @author TechFinium
	 * @see    Gender
	 */
	public void prepareNew() {
		gender = new Gender();
	}

/*
    public List<SelectItem> getGenderDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	genderInfo();
    	for (Gender ug : genderList) {
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
    public List<Gender> complete(String desc) {
		List<Gender> l = null;
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
	 * Gets the gender list.
	 *
	 * @return the gender list
	 */
	public List<Gender> getGenderList() {
		return genderList;
	}

	/**
	 * Sets the gender list.
	 *
	 * @param genderList the new gender list
	 */
	public void setGenderList(List<Gender> genderList) {
		this.genderList = genderList;
	}

	/**
	 * Gets the genderfiltered list.
	 *
	 * @return the genderfiltered list
	 */
	public List<Gender> getGenderfilteredList() {
		return genderfilteredList;
	}

	/**
	 * Sets the genderfiltered list.
	 *
	 * @param genderfilteredList the new genderfiltered list
	 */
	public void setGenderfilteredList(List<Gender> genderfilteredList) {
		this.genderfilteredList = genderfilteredList;
	}

	/**
	 * Gets the gender.
	 *
	 * @return the gender
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * Sets the gender.
	 *
	 * @param gender the new gender
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<Gender> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<Gender> dataModel) {
		this.dataModel = dataModel;
	}
 
	
}
