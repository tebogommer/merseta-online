package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.sars.SarsLevyDetails;
import haj.com.service.SarsLevyDetailsService;

// TODO: Auto-generated Javadoc
/**
 * The Class SarsLevyDetailsUI.
 */
@ManagedBean(name = "sarslevydetailsUI")
@ViewScoped
public class SarsLevyDetailsUI extends AbstractUI {

	/** The service. */
	private SarsLevyDetailsService service = new SarsLevyDetailsService();
	
	/** The sarslevydetails list. */
	private List<SarsLevyDetails> sarslevydetailsList = null;
	
	/** The sarslevydetailsfiltered list. */
	private List<SarsLevyDetails> sarslevydetailsfilteredList = null;
	
	/** The sarslevydetails. */
	private SarsLevyDetails sarslevydetails = null;
	
	/** The data model. */
	private LazyDataModel<SarsLevyDetails> dataModel; 

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
	 * Initialize method to get all SarsLevyDetails and prepare a for a create of a new SarsLevyDetails.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    SarsLevyDetails
	 */
	private void runInit() throws Exception {
		prepareNew();
		sarslevydetailsInfo();
	}

	/**
	 * Get all SarsLevyDetails for data table.
	 *
	 * @author TechFinium
	 * @see    SarsLevyDetails
	 */
	public void sarslevydetailsInfo() {
	 
			
			 dataModel = new LazyDataModel<SarsLevyDetails>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<SarsLevyDetails> retorno = new  ArrayList<SarsLevyDetails>();
			   
			   @Override 
			   public List<SarsLevyDetails> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allSarsLevyDetails(SarsLevyDetails.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(SarsLevyDetails.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(SarsLevyDetails obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public SarsLevyDetails getRowData(String rowKey) {
			        for(SarsLevyDetails obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert SarsLevyDetails into database.
	 *
	 * @author TechFinium
	 * @see    SarsLevyDetails
	 */
	public void sarslevydetailsInsert() {
		try {
				 service.create(this.sarslevydetails);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 sarslevydetailsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update SarsLevyDetails in database.
	 *
	 * @author TechFinium
	 * @see    SarsLevyDetails
	 */
    public void sarslevydetailsUpdate() {
		try {
				 service.update(this.sarslevydetails);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 sarslevydetailsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete SarsLevyDetails from database.
	 *
	 * @author TechFinium
	 * @see    SarsLevyDetails
	 */
	public void sarslevydetailsDelete() {
		try {
			 service.delete(this.sarslevydetails);
			  prepareNew();
			 sarslevydetailsInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of SarsLevyDetails .
	 *
	 * @author TechFinium
	 * @see    SarsLevyDetails
	 */
	public void prepareNew() {
		sarslevydetails = new SarsLevyDetails();
	}

/*
    public List<SelectItem> getSarsLevyDetailsDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	sarslevydetailsInfo();
    	for (SarsLevyDetails ug : sarslevydetailsList) {
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
    public List<SarsLevyDetails> complete(String desc) {
		List<SarsLevyDetails> l = null;
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
     * Gets the sars levy details list.
     *
     * @return the sars levy details list
     */
    public List<SarsLevyDetails> getSarsLevyDetailsList() {
		return sarslevydetailsList;
	}
	
	/**
	 * Sets the sars levy details list.
	 *
	 * @param sarslevydetailsList the new sars levy details list
	 */
	public void setSarsLevyDetailsList(List<SarsLevyDetails> sarslevydetailsList) {
		this.sarslevydetailsList = sarslevydetailsList;
	}
	
	/**
	 * Gets the sarslevydetails.
	 *
	 * @return the sarslevydetails
	 */
	public SarsLevyDetails getSarslevydetails() {
		return sarslevydetails;
	}
	
	/**
	 * Sets the sarslevydetails.
	 *
	 * @param sarslevydetails the new sarslevydetails
	 */
	public void setSarslevydetails(SarsLevyDetails sarslevydetails) {
		this.sarslevydetails = sarslevydetails;
	}

    /**
     * Gets the sars levy detailsfiltered list.
     *
     * @return the sars levy detailsfiltered list
     */
    public List<SarsLevyDetails> getSarsLevyDetailsfilteredList() {
		return sarslevydetailsfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param sarslevydetailsfilteredList the new sarslevydetailsfilteredList list
	 * @see    SarsLevyDetails
	 */	
	public void setSarsLevyDetailsfilteredList(List<SarsLevyDetails> sarslevydetailsfilteredList) {
		this.sarslevydetailsfilteredList = sarslevydetailsfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<SarsLevyDetails> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<SarsLevyDetails> dataModel) {
		this.dataModel = dataModel;
	}
	
}
