package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.Country;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.CountryService;

// TODO: Auto-generated Javadoc
/**
 * The Class CountryUI.
 */
@ManagedBean(name = "countryUI")
@ViewScoped
public class CountryUI extends AbstractUI {

	/** The service. */
	private CountryService service = new CountryService();
	
	/** The country list. */
	private List<Country> countryList = null;
	
	/** The countryfiltered list. */
	private List<Country> countryfilteredList = null;
	
	/** The country. */
	private Country country = null;
	
	/** The data model. */
	private LazyDataModel<Country> dataModel; 

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
	 * Initialize method to get all Country and prepare a for a create of a new Country.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    Country
	 */
	private void runInit() throws Exception {
		prepareNew();
		countryInfo();
	}

	/**
	 * Get all Country for data table.
	 *
	 * @author TechFinium
	 * @see    Country
	 */
	public void countryInfo() {
	 
			
			 dataModel = new LazyDataModel<Country>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<Country> retorno = new  ArrayList<Country>();
			   
			   @Override 
			   public List<Country> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allCountry(Country.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(Country.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(Country obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public Country getRowData(String rowKey) {
			        for(Country obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert Country into database.
	 *
	 * @author TechFinium
	 * @see    Country
	 */
	public void countryInsert() {
		try {
				 service.create(this.country);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 countryInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update Country in database.
	 *
	 * @author TechFinium
	 * @see    Country
	 */
    public void countryUpdate() {
		try {
				 service.update(this.country);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 countryInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete Country from database.
	 *
	 * @author TechFinium
	 * @see    Country
	 */
	public void countryDelete() {
		try {
			 service.delete(this.country);
			  prepareNew();
			 countryInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted "));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of Country .
	 *
	 * @author TechFinium
	 * @see    Country
	 */
	public void prepareNew() {
		country = new Country();
	}

/*
    public List<SelectItem> getCountryDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	countryInfo();
    	for (Country ug : countryList) {
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
    public List<Country> complete(String desc) {
		List<Country> l = null;
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
     * Gets the country list.
     *
     * @return the country list
     */
    public List<Country> getCountryList() {
		return countryList;
	}
	
	/**
	 * Sets the country list.
	 *
	 * @param countryList the new country list
	 */
	public void setCountryList(List<Country> countryList) {
		this.countryList = countryList;
	}
	
	/**
	 * Gets the country.
	 *
	 * @return the country
	 */
	public Country getCountry() {
		return country;
	}
	
	/**
	 * Sets the country.
	 *
	 * @param country the new country
	 */
	public void setCountry(Country country) {
		this.country = country;
	}

    /**
     * Gets the countryfiltered list.
     *
     * @return the countryfiltered list
     */
    public List<Country> getCountryfilteredList() {
		return countryfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param countryfilteredList the new countryfilteredList list
	 * @see    Country
	 */	
	public void setCountryfilteredList(List<Country> countryfilteredList) {
		this.countryfilteredList = countryfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<Country> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<Country> dataModel) {
		this.dataModel = dataModel;
	}
	
}
