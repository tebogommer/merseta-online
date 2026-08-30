package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.TradeTestResult;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.TradeTestResultService;

// TODO: Auto-generated Javadoc
/**
 * The Class TradeTestResultUI.
 */
@ManagedBean(name = "tradetestresultUI")
@ViewScoped
public class TradeTestResultUI extends AbstractUI {

	/** The service. */
	private TradeTestResultService service = new TradeTestResultService();
	
	/** The tradetestresult list. */
	private List<TradeTestResult> tradetestresultList = null;
	
	/** The tradetestresultfiltered list. */
	private List<TradeTestResult> tradetestresultfilteredList = null;
	
	/** The tradetestresult. */
	private TradeTestResult tradetestresult = null;
	
	/** The data model. */
	private LazyDataModel<TradeTestResult> dataModel;

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
	 * Initialize method to get all TradeTestResult and prepare a for a create
	 * of a new TradeTestResult.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see TradeTestResult
	 */
	private void runInit() throws Exception {
		prepareNew();
		tradetestresultInfo();
	}

	/**
	 * Get all TradeTestResult for data table.
	 *
	 * @author TechFinium
	 * @see TradeTestResult
	 */
	public void tradetestresultInfo() {

		dataModel = new LazyDataModel<TradeTestResult>() {

			private static final long serialVersionUID = 1L;
			private List<TradeTestResult> retorno = new ArrayList<TradeTestResult>();

			@Override
			public List<TradeTestResult> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allTradeTestResult(TradeTestResult.class, first, pageSize, sortField, sortOrder,
							filters);
					dataModel.setRowCount(service.count(TradeTestResult.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(TradeTestResult obj) {
				return obj.getId();
			}

			@Override
			public TradeTestResult getRowData(String rowKey) {
				for (TradeTestResult obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert TradeTestResult into database.
	 *
	 * @author TechFinium
	 * @see TradeTestResult
	 */
	public void tradetestresultInsert() {
		try {
			service.create(this.tradetestresult);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			tradetestresultInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update TradeTestResult in database.
	 *
	 * @author TechFinium
	 * @see TradeTestResult
	 */
	public void tradetestresultUpdate() {
		try {
			service.update(this.tradetestresult);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			tradetestresultInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete TradeTestResult from database.
	 *
	 * @author TechFinium
	 * @see TradeTestResult
	 */
	public void tradetestresultDelete() {
		try {
			service.delete(this.tradetestresult);
			prepareNew();
			tradetestresultInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of TradeTestResult.
	 *
	 * @author TechFinium
	 * @see TradeTestResult
	 */
	public void prepareNew() {
		tradetestresult = new TradeTestResult();
	}

	/*
	 * public List<SelectItem> getTradeTestResultDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * tradetestresultInfo(); for (TradeTestResult ug : tradetestresultList) {
	 * // l.add(new SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); }
	 * return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<TradeTestResult> complete(String desc) {
		List<TradeTestResult> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the trade test result list.
	 *
	 * @return the trade test result list
	 */
	public List<TradeTestResult> getTradeTestResultList() {
		return tradetestresultList;
	}

	/**
	 * Sets the trade test result list.
	 *
	 * @param tradetestresultList the new trade test result list
	 */
	public void setTradeTestResultList(List<TradeTestResult> tradetestresultList) {
		this.tradetestresultList = tradetestresultList;
	}

	/**
	 * Gets the tradetestresult.
	 *
	 * @return the tradetestresult
	 */
	public TradeTestResult getTradetestresult() {
		return tradetestresult;
	}

	/**
	 * Sets the tradetestresult.
	 *
	 * @param tradetestresult the new tradetestresult
	 */
	public void setTradetestresult(TradeTestResult tradetestresult) {
		this.tradetestresult = tradetestresult;
	}

	/**
	 * Gets the trade test resultfiltered list.
	 *
	 * @return the trade test resultfiltered list
	 */
	public List<TradeTestResult> getTradeTestResultfilteredList() {
		return tradetestresultfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param tradetestresultfilteredList            the new tradetestresultfilteredList list
	 * @see TradeTestResult
	 */
	public void setTradeTestResultfilteredList(List<TradeTestResult> tradetestresultfilteredList) {
		this.tradetestresultfilteredList = tradetestresultfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<TradeTestResult> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<TradeTestResult> dataModel) {
		this.dataModel = dataModel;
	}

}
