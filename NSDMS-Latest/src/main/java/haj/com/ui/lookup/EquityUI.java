package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.Equity;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.EquityService;

// TODO: Auto-generated Javadoc
/**
 * The Class EquityUI.
 */
@ManagedBean(name = "equityUI")
@ViewScoped
public class EquityUI extends AbstractUI {

	/** The service. */
	private EquityService service = new EquityService();
	
	/** The equity list. */
	private List<Equity> equityList = null;
	
	/** The equityfiltered list. */
	private List<Equity> equityfilteredList = null;
	
	/** The equity. */
	private Equity equity = null;
	
	/** The data model. */
	private LazyDataModel<Equity> dataModel;

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
	 * Initialize method to get all Equity and prepare a for a create of a new
	 * Equity.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see Equity
	 */
	private void runInit() throws Exception {
		prepareNew();
		equityInfo();
	}

	/**
	 * Get all Equity for data table.
	 *
	 * @author TechFinium
	 * @see Equity
	 */
	public void equityInfo() {

		dataModel = new LazyDataModel<Equity>() {

			private static final long serialVersionUID = 1L;
			private List<Equity> retorno = new ArrayList<Equity>();

			@Override
			public List<Equity> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allEquity(Equity.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(Equity.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(Equity obj) {
				return obj.getId();
			}

			@Override
			public Equity getRowData(String rowKey) {
				for (Equity obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert Equity into database.
	 *
	 * @author TechFinium
	 * @see Equity
	 */
	public void equityInsert() {
		try {
			service.create(this.equity);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			equityInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update Equity in database.
	 *
	 * @author TechFinium
	 * @see Equity
	 */
	public void equityUpdate() {
		try {
			service.update(this.equity);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			equityInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete Equity from database.
	 *
	 * @author TechFinium
	 * @see Equity
	 */
	public void equityDelete() {
		try {
			service.delete(this.equity);
			prepareNew();
			equityInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of Equity.
	 *
	 * @author TechFinium
	 * @see Equity
	 */
	public void prepareNew() {
		equity = new Equity();
	}

	/*
	 * public List<SelectItem> getEquityDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * equityInfo(); for (Equity ug : equityList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Equity> complete(String desc) {
		List<Equity> l = null;
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
	 * Gets the equity list.
	 *
	 * @return the equity list
	 */
	public List<Equity> getEquityList() {
		return equityList;
	}

	/**
	 * Sets the equity list.
	 *
	 * @param equityList the new equity list
	 */
	public void setEquityList(List<Equity> equityList) {
		this.equityList = equityList;
	}

	/**
	 * Gets the equity.
	 *
	 * @return the equity
	 */
	public Equity getEquity() {
		return equity;
	}

	/**
	 * Sets the equity.
	 *
	 * @param equity the new equity
	 */
	public void setEquity(Equity equity) {
		this.equity = equity;
	}

	/**
	 * Gets the equityfiltered list.
	 *
	 * @return the equityfiltered list
	 */
	public List<Equity> getEquityfilteredList() {
		return equityfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param equityfilteredList            the new equityfilteredList list
	 * @see Equity
	 */
	public void setEquityfilteredList(List<Equity> equityfilteredList) {
		this.equityfilteredList = equityfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<Equity> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<Equity> dataModel) {
		this.dataModel = dataModel;
	}

}
