package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.bean.IngterSetaTransferBean;
import haj.com.entity.HistoricalIntersetaTransfers;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.HistoricalIntersetaTransfersService;

@ManagedBean(name = "historicalintersetatransfersUI")
@ViewScoped
public class HistoricalIntersetaTransfersUI extends AbstractUI {

	private HistoricalIntersetaTransfersService service = new HistoricalIntersetaTransfersService();
	private List<HistoricalIntersetaTransfers> historicalintersetatransfersList = null;
	private List<HistoricalIntersetaTransfers> historicalintersetatransfersfilteredList = null;
	private HistoricalIntersetaTransfers historicalintersetatransfers = null;
	private LazyDataModel<HistoricalIntersetaTransfers> dataModel;
	private  List<IngterSetaTransferBean> summary;
	private  List<IngterSetaTransferBean> breakdown;
	private IngterSetaTransferBean ingterSetaTransferBean;

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
	 * Initialize method to get all HistoricalIntersetaTransfers and prepare a for a create of a new HistoricalIntersetaTransfers
 	 * @author TechFinium
 	 * @see    HistoricalIntersetaTransfers
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		historicalintersetatransfersInfo();
		summary = service.summaryBySchemeYear();
	}

	/**
	 * Get all HistoricalIntersetaTransfers for data table
 	 * @author TechFinium
 	 * @see    HistoricalIntersetaTransfers
 	 */
	public void historicalintersetatransfersInfo() {


			 dataModel = new LazyDataModel<HistoricalIntersetaTransfers>() {

			   private static final long serialVersionUID = 1L;
			   private List<HistoricalIntersetaTransfers> retorno = new  ArrayList<HistoricalIntersetaTransfers>();

			   @Override
			   public List<HistoricalIntersetaTransfers> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  {

				try {
					if (sortField==null) {
						sortField = "mersetaSchemeYear,refNo";
					}
					retorno = service.allHistoricalIntersetaTransfers(HistoricalIntersetaTransfers.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(HistoricalIntersetaTransfers.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
			    return retorno;
			   }

			    @Override
			    public Object getRowKey(HistoricalIntersetaTransfers obj) {
			        return obj.getId();
			    }

			    @Override
			    public HistoricalIntersetaTransfers getRowData(String rowKey) {
			        for(HistoricalIntersetaTransfers obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }

			  };



	}

	/**
	 * Insert HistoricalIntersetaTransfers into database
 	 * @author TechFinium
 	 * @see    HistoricalIntersetaTransfers
 	 */
	public void historicalintersetatransfersInsert() {
		try {
				 service.create(this.historicalintersetatransfers);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 historicalintersetatransfersInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Update HistoricalIntersetaTransfers in database
 	 * @author TechFinium
 	 * @see    HistoricalIntersetaTransfers
 	 */
    public void historicalintersetatransfersUpdate() {
		try {
				 service.update(this.historicalintersetatransfers);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 historicalintersetatransfersInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete HistoricalIntersetaTransfers from database
 	 * @author TechFinium
 	 * @see    HistoricalIntersetaTransfers
 	 */
	public void historicalintersetatransfersDelete() {
		try {
			 service.delete(this.historicalintersetatransfers);
			  prepareNew();
			 historicalintersetatransfersInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of HistoricalIntersetaTransfers
 	 * @author TechFinium
 	 * @see    HistoricalIntersetaTransfers
 	 */
	public void prepareNew() {
		historicalintersetatransfers = new HistoricalIntersetaTransfers();
	}

/*
    public List<SelectItem> getHistoricalIntersetaTransfersDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	historicalintersetatransfersInfo();
    	for (HistoricalIntersetaTransfers ug : historicalintersetatransfersList) {
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
    public List<HistoricalIntersetaTransfers> complete(String desc) {
		List<HistoricalIntersetaTransfers> l = null;
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

    public List<HistoricalIntersetaTransfers> getHistoricalIntersetaTransfersList() {
		return historicalintersetatransfersList;
	}
	public void setHistoricalIntersetaTransfersList(List<HistoricalIntersetaTransfers> historicalintersetatransfersList) {
		this.historicalintersetatransfersList = historicalintersetatransfersList;
	}
	public HistoricalIntersetaTransfers getHistoricalintersetatransfers() {
		return historicalintersetatransfers;
	}
	public void setHistoricalintersetatransfers(HistoricalIntersetaTransfers historicalintersetatransfers) {
		this.historicalintersetatransfers = historicalintersetatransfers;
	}

    public List<HistoricalIntersetaTransfers> getHistoricalIntersetaTransfersfilteredList() {
		return historicalintersetatransfersfilteredList;
	}

	/**
	 * Prepare auto complete data
 	 * @author TechFinium
 	 * @param historicalintersetatransfersfilteredList the new historicalintersetatransfersfilteredList list
 	 * @see    HistoricalIntersetaTransfers
 	 */
	public void setHistoricalIntersetaTransfersfilteredList(List<HistoricalIntersetaTransfers> historicalintersetatransfersfilteredList) {
		this.historicalintersetatransfersfilteredList = historicalintersetatransfersfilteredList;
	}


	public LazyDataModel<HistoricalIntersetaTransfers> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<HistoricalIntersetaTransfers> dataModel) {
		this.dataModel = dataModel;
	}

	public List<IngterSetaTransferBean> getSummary() {
		return summary;
	}

	public void setSummary(List<IngterSetaTransferBean> summary) {
		this.summary = summary;
	}

	public IngterSetaTransferBean getIngterSetaTransferBean() {
		return ingterSetaTransferBean;
	}

	public void setIngterSetaTransferBean(IngterSetaTransferBean ingterSetaTransferBean) {
		this.ingterSetaTransferBean = ingterSetaTransferBean;
	}


	public void doBreakdown() {
		try {
					breakdown = service.breakDownBySchemeYearandTransactionType(ingterSetaTransferBean);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public List<IngterSetaTransferBean> getBreakdown() {
		return breakdown;
	}

	public void setBreakdown(List<IngterSetaTransferBean> breakdown) {
		this.breakdown = breakdown;
	}
}
