package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.FinYearQuartersLookUp;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.FinYearQuartersLookUpService;

@ManagedBean(name = "finyearquarterslookupUI")
@ViewScoped
public class FinYearQuartersLookUpUI extends AbstractUI {

	private FinYearQuartersLookUpService service = new FinYearQuartersLookUpService();
	private List<FinYearQuartersLookUp> finyearquarterslookupList = null;
	private List<FinYearQuartersLookUp> finyearquarterslookupfilteredList = null;
	private FinYearQuartersLookUp finyearquarterslookup = null;
	private LazyDataModel<FinYearQuartersLookUp> dataModel;

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
	 * Initialize method to get all FinYearQuartersLookUp and prepare a for a
	 * create of a new FinYearQuartersLookUp
	 * 
	 * @author TechFinium
	 * @see FinYearQuartersLookUp
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		finyearquarterslookupInfo();
	}

	/**
	 * Get all FinYearQuartersLookUp for data table
	 * 
	 * @author TechFinium
	 * @see FinYearQuartersLookUp
	 */
	public void finyearquarterslookupInfo() {

		dataModel = new LazyDataModel<FinYearQuartersLookUp>() {

			private static final long serialVersionUID = 1L;
			private List<FinYearQuartersLookUp> retorno = new ArrayList<FinYearQuartersLookUp>();

			@Override
			public List<FinYearQuartersLookUp> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allFinYearQuartersLookUp(FinYearQuartersLookUp.class, first, pageSize, sortField,
							sortOrder, filters);
					dataModel.setRowCount(service.count(FinYearQuartersLookUp.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(FinYearQuartersLookUp obj) {
				return obj.getId();
			}

			@Override
			public FinYearQuartersLookUp getRowData(String rowKey) {
				for (FinYearQuartersLookUp obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert FinYearQuartersLookUp into database
	 * 
	 * @author TechFinium
	 * @see FinYearQuartersLookUp
	 */
	public void finyearquarterslookupInsert() {
		try {
			service.create(this.finyearquarterslookup);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			finyearquarterslookupInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update FinYearQuartersLookUp in database
	 * 
	 * @author TechFinium
	 * @see FinYearQuartersLookUp
	 */
	public void finyearquarterslookupUpdate() {
		try {
			service.update(this.finyearquarterslookup);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			finyearquarterslookupInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete FinYearQuartersLookUp from database
	 * 
	 * @author TechFinium
	 * @see FinYearQuartersLookUp
	 */
	public void finyearquarterslookupDelete() {
		try {
			service.delete(this.finyearquarterslookup);
			prepareNew();
			finyearquarterslookupInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of FinYearQuartersLookUp
	 * 
	 * @author TechFinium
	 * @see FinYearQuartersLookUp
	 */
	public void prepareNew() {
		finyearquarterslookup = new FinYearQuartersLookUp();
		finyearquarterslookup.setUseNextYear(false);
	}

	/*
	 * public List<SelectItem> getFinYearQuartersLookUpDD() { List<SelectItem> l
	 * =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * finyearquarterslookupInfo(); for (FinYearQuartersLookUp ug :
	 * finyearquarterslookupList) { // l.add(new SelectItem(ug.getUserGroupId(),
	 * ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<FinYearQuartersLookUp> complete(String desc) {
		List<FinYearQuartersLookUp> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<FinYearQuartersLookUp> getFinYearQuartersLookUpList() {
		return finyearquarterslookupList;
	}

	public void setFinYearQuartersLookUpList(List<FinYearQuartersLookUp> finyearquarterslookupList) {
		this.finyearquarterslookupList = finyearquarterslookupList;
	}

	public FinYearQuartersLookUp getFinyearquarterslookup() {
		return finyearquarterslookup;
	}

	public void setFinyearquarterslookup(FinYearQuartersLookUp finyearquarterslookup) {
		this.finyearquarterslookup = finyearquarterslookup;
	}

	public List<FinYearQuartersLookUp> getFinYearQuartersLookUpfilteredList() {
		return finyearquarterslookupfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param finyearquarterslookupfilteredList
	 *            the new finyearquarterslookupfilteredList list
	 * @see FinYearQuartersLookUp
	 */
	public void setFinYearQuartersLookUpfilteredList(List<FinYearQuartersLookUp> finyearquarterslookupfilteredList) {
		this.finyearquarterslookupfilteredList = finyearquarterslookupfilteredList;
	}

	public LazyDataModel<FinYearQuartersLookUp> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<FinYearQuartersLookUp> dataModel) {
		this.dataModel = dataModel;
	}

}
