package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.STATSSAAreaCode;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.STATSSAAreaCodeService;

// TODO: Auto-generated Javadoc
/**
 * The Class STATSSAAreaCodeUI.
 */
@ManagedBean(name = "statssaareacodeUI")
@ViewScoped
public class STATSSAAreaCodeUI extends AbstractUI {

	/** The service. */
	private STATSSAAreaCodeService service = new STATSSAAreaCodeService();
	
	/** The statssaareacode list. */
	private List<STATSSAAreaCode> statssaareacodeList = null;
	
	/** The statssaareacodefiltered list. */
	private List<STATSSAAreaCode> statssaareacodefilteredList = null;
	
	/** The statssaareacode. */
	private STATSSAAreaCode statssaareacode = null;
	
	/** The data model. */
	private LazyDataModel<STATSSAAreaCode> dataModel;

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
	 * Initialize method to get all STATSSAAreaCode and prepare a for a create
	 * of a new STATSSAAreaCode.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see STATSSAAreaCode
	 */
	private void runInit() throws Exception {
		prepareNew();
		statssaareacodeInfo();
	}

	/**
	 * Get all STATSSAAreaCode for data table.
	 *
	 * @author TechFinium
	 * @see STATSSAAreaCode
	 */
	public void statssaareacodeInfo() {

		dataModel = new LazyDataModel<STATSSAAreaCode>() {

			private static final long serialVersionUID = 1L;
			private List<STATSSAAreaCode> retorno = new ArrayList<STATSSAAreaCode>();

			@Override
			public List<STATSSAAreaCode> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allSTATSSAAreaCode(STATSSAAreaCode.class, first, pageSize, sortField, sortOrder,
							filters);
					dataModel.setRowCount(service.count(STATSSAAreaCode.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(STATSSAAreaCode obj) {
				return obj.getId();
			}

			@Override
			public STATSSAAreaCode getRowData(String rowKey) {
				for (STATSSAAreaCode obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert STATSSAAreaCode into database.
	 *
	 * @author TechFinium
	 * @see STATSSAAreaCode
	 */
	public void statssaareacodeInsert() {
		try {
			service.create(this.statssaareacode);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			statssaareacodeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update STATSSAAreaCode in database.
	 *
	 * @author TechFinium
	 * @see STATSSAAreaCode
	 */
	public void statssaareacodeUpdate() {
		try {
			service.update(this.statssaareacode);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			statssaareacodeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete STATSSAAreaCode from database.
	 *
	 * @author TechFinium
	 * @see STATSSAAreaCode
	 */
	public void statssaareacodeDelete() {
		try {
			service.delete(this.statssaareacode);
			prepareNew();
			statssaareacodeInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of STATSSAAreaCode.
	 *
	 * @author TechFinium
	 * @see STATSSAAreaCode
	 */
	public void prepareNew() {
		statssaareacode = new STATSSAAreaCode();
	}

	/*
	 * public List<SelectItem> getSTATSSAAreaCodeDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * statssaareacodeInfo(); for (STATSSAAreaCode ug : statssaareacodeList) {
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
	public List<STATSSAAreaCode> complete(String desc) {
		List<STATSSAAreaCode> l = null;
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
	 * Gets the STATSSA area code list.
	 *
	 * @return the STATSSA area code list
	 */
	public List<STATSSAAreaCode> getSTATSSAAreaCodeList() {
		return statssaareacodeList;
	}

	/**
	 * Sets the STATSSA area code list.
	 *
	 * @param statssaareacodeList the new STATSSA area code list
	 */
	public void setSTATSSAAreaCodeList(List<STATSSAAreaCode> statssaareacodeList) {
		this.statssaareacodeList = statssaareacodeList;
	}

	/**
	 * Gets the statssaareacode.
	 *
	 * @return the statssaareacode
	 */
	public STATSSAAreaCode getStatssaareacode() {
		return statssaareacode;
	}

	/**
	 * Sets the statssaareacode.
	 *
	 * @param statssaareacode the new statssaareacode
	 */
	public void setStatssaareacode(STATSSAAreaCode statssaareacode) {
		this.statssaareacode = statssaareacode;
	}

	/**
	 * Gets the STATSSA area codefiltered list.
	 *
	 * @return the STATSSA area codefiltered list
	 */
	public List<STATSSAAreaCode> getSTATSSAAreaCodefilteredList() {
		return statssaareacodefilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param statssaareacodefilteredList            the new statssaareacodefilteredList list
	 * @see STATSSAAreaCode
	 */
	public void setSTATSSAAreaCodefilteredList(List<STATSSAAreaCode> statssaareacodefilteredList) {
		this.statssaareacodefilteredList = statssaareacodefilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<STATSSAAreaCode> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<STATSSAAreaCode> dataModel) {
		this.dataModel = dataModel;
	}

}
