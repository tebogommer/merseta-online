package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.StatementOfResults;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.StatementOfResultsService;

// TODO: Auto-generated Javadoc
/**
 * The Class StatementOfResultsUI.
 */
@ManagedBean(name = "statementofresultsUI")
@ViewScoped
public class StatementOfResultsUI extends AbstractUI {

	/** The service. */
	private StatementOfResultsService service = new StatementOfResultsService();
	
	/** The statementofresults list. */
	private List<StatementOfResults> statementofresultsList = null;
	
	/** The statementofresultsfiltered list. */
	private List<StatementOfResults> statementofresultsfilteredList = null;
	
	/** The statementofresults. */
	private StatementOfResults statementofresults = null;
	
	/** The data model. */
	private LazyDataModel<StatementOfResults> dataModel; 

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
	 * Initialize method to get all StatementOfResults and prepare a for a create of a new StatementOfResults.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    StatementOfResults
	 */
	private void runInit() throws Exception {
		prepareNew();
		statementofresultsInfo();
	}

	/**
	 * Get all StatementOfResults for data table.
	 *
	 * @author TechFinium
	 * @see    StatementOfResults
	 */
	public void statementofresultsInfo() {
	 
			
			 dataModel = new LazyDataModel<StatementOfResults>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<StatementOfResults> retorno = new  ArrayList<StatementOfResults>();
			   
			   @Override 
			   public List<StatementOfResults> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allStatementOfResults(StatementOfResults.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(StatementOfResults.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(StatementOfResults obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public StatementOfResults getRowData(String rowKey) {
			        for(StatementOfResults obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert StatementOfResults into database.
	 *
	 * @author TechFinium
	 * @see    StatementOfResults
	 */
	public void statementofresultsInsert() {
		try {
				 service.create(this.statementofresults);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 statementofresultsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update StatementOfResults in database.
	 *
	 * @author TechFinium
	 * @see    StatementOfResults
	 */
    public void statementofresultsUpdate() {
		try {
				 service.update(this.statementofresults);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 statementofresultsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete StatementOfResults from database.
	 *
	 * @author TechFinium
	 * @see    StatementOfResults
	 */
	public void statementofresultsDelete() {
		try {
			 service.delete(this.statementofresults);
			  prepareNew();
			 statementofresultsInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted "));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of StatementOfResults .
	 *
	 * @author TechFinium
	 * @see    StatementOfResults
	 */
	public void prepareNew() {
		statementofresults = new StatementOfResults();
	}

/*
    public List<SelectItem> getStatementOfResultsDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	statementofresultsInfo();
    	for (StatementOfResults ug : statementofresultsList) {
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
    public List<StatementOfResults> complete(String desc) {
		List<StatementOfResults> l = null;
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
     * Gets the statement of results list.
     *
     * @return the statement of results list
     */
    public List<StatementOfResults> getStatementOfResultsList() {
		return statementofresultsList;
	}
	
	/**
	 * Sets the statement of results list.
	 *
	 * @param statementofresultsList the new statement of results list
	 */
	public void setStatementOfResultsList(List<StatementOfResults> statementofresultsList) {
		this.statementofresultsList = statementofresultsList;
	}
	
	/**
	 * Gets the statementofresults.
	 *
	 * @return the statementofresults
	 */
	public StatementOfResults getStatementofresults() {
		return statementofresults;
	}
	
	/**
	 * Sets the statementofresults.
	 *
	 * @param statementofresults the new statementofresults
	 */
	public void setStatementofresults(StatementOfResults statementofresults) {
		this.statementofresults = statementofresults;
	}

    /**
     * Gets the statement of resultsfiltered list.
     *
     * @return the statement of resultsfiltered list
     */
    public List<StatementOfResults> getStatementOfResultsfilteredList() {
		return statementofresultsfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param statementofresultsfilteredList the new statementofresultsfilteredList list
	 * @see    StatementOfResults
	 */	
	public void setStatementOfResultsfilteredList(List<StatementOfResults> statementofresultsfilteredList) {
		this.statementofresultsfilteredList = statementofresultsfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<StatementOfResults> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<StatementOfResults> dataModel) {
		this.dataModel = dataModel;
	}
	
}
