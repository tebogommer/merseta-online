package  haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.lookup.TradeTestCentersHistoric;
import haj.com.service.lookup.TradeTestCentersHistoricService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "tradetestcentershistoricUI")
@ViewScoped
public class TradeTestCentersHistoricUI extends AbstractUI {

	private TradeTestCentersHistoricService service = new TradeTestCentersHistoricService();
	private List<TradeTestCentersHistoric> tradetestcentershistoricList = null;
	private List<TradeTestCentersHistoric> tradetestcentershistoricfilteredList = null;
	private TradeTestCentersHistoric tradetestcentershistoric = null;
	private LazyDataModel<TradeTestCentersHistoric> dataModel; 

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
	 * Initialize method to get all TradeTestCentersHistoric and prepare a for a create of a new TradeTestCentersHistoric
 	 * @author TechFinium 
 	 * @see    TradeTestCentersHistoric
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		tradetestcentershistoricInfo();
	}

	/**
	 * Get all TradeTestCentersHistoric for data table
 	 * @author TechFinium 
 	 * @see    TradeTestCentersHistoric
 	 */
	public void tradetestcentershistoricInfo() {
	 
			
			 dataModel = new LazyDataModel<TradeTestCentersHistoric>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<TradeTestCentersHistoric> retorno = new  ArrayList<TradeTestCentersHistoric>();
			   
			   @Override 
			   public List<TradeTestCentersHistoric> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allTradeTestCentersHistoric(TradeTestCentersHistoric.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(TradeTestCentersHistoric.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(TradeTestCentersHistoric obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public TradeTestCentersHistoric getRowData(String rowKey) {
			        for(TradeTestCentersHistoric obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert TradeTestCentersHistoric into database
 	 * @author TechFinium 
 	 * @see    TradeTestCentersHistoric
 	 */
	public void tradetestcentershistoricInsert() {
		try {
				 service.create(this.tradetestcentershistoric);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 tradetestcentershistoricInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update TradeTestCentersHistoric in database
 	 * @author TechFinium 
 	 * @see    TradeTestCentersHistoric
 	 */
    public void tradetestcentershistoricUpdate() {
		try {
				 service.update(this.tradetestcentershistoric);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 tradetestcentershistoricInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete TradeTestCentersHistoric from database
 	 * @author TechFinium 
 	 * @see    TradeTestCentersHistoric
 	 */
	public void tradetestcentershistoricDelete() {
		try {
			 service.delete(this.tradetestcentershistoric);
			  prepareNew();
			 tradetestcentershistoricInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of TradeTestCentersHistoric 
 	 * @author TechFinium 
 	 * @see    TradeTestCentersHistoric
 	 */
	public void prepareNew() {
		tradetestcentershistoric = new TradeTestCentersHistoric();
	}

/*
    public List<SelectItem> getTradeTestCentersHistoricDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	tradetestcentershistoricInfo();
    	for (TradeTestCentersHistoric ug : tradetestcentershistoricList) {
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
    public List<TradeTestCentersHistoric> complete(String desc) {
		List<TradeTestCentersHistoric> l = null;
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
    
    public List<TradeTestCentersHistoric> getTradeTestCentersHistoricList() {
		return tradetestcentershistoricList;
	}
	public void setTradeTestCentersHistoricList(List<TradeTestCentersHistoric> tradetestcentershistoricList) {
		this.tradetestcentershistoricList = tradetestcentershistoricList;
	}
	public TradeTestCentersHistoric getTradetestcentershistoric() {
		return tradetestcentershistoric;
	}
	public void setTradetestcentershistoric(TradeTestCentersHistoric tradetestcentershistoric) {
		this.tradetestcentershistoric = tradetestcentershistoric;
	}

    public List<TradeTestCentersHistoric> getTradeTestCentersHistoricfilteredList() {
		return tradetestcentershistoricfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param tradetestcentershistoricfilteredList the new tradetestcentershistoricfilteredList list
 	 * @see    TradeTestCentersHistoric
 	 */	
	public void setTradeTestCentersHistoricfilteredList(List<TradeTestCentersHistoric> tradetestcentershistoricfilteredList) {
		this.tradetestcentershistoricfilteredList = tradetestcentershistoricfilteredList;
	}

	
	public LazyDataModel<TradeTestCentersHistoric> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<TradeTestCentersHistoric> dataModel) {
		this.dataModel = dataModel;
	}
	
}
