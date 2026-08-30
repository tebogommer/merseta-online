package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.entity.SarsLevySchemeYearReturns;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.SarsLevySchemeYearReturnsService;

@ManagedBean(name = "sarslevyschemeyearreturnsUI")
@ViewScoped
public class SarsLevySchemeYearReturnsUI extends AbstractUI {

	private SarsLevySchemeYearReturnsService service = new SarsLevySchemeYearReturnsService();
	private List<SarsLevySchemeYearReturns> sarslevyschemeyearreturnsList = null;
	private List<SarsLevySchemeYearReturns> sarslevyschemeyearreturnsfilteredList = null;
	private SarsLevySchemeYearReturns sarslevyschemeyearreturns = null;
	private LazyDataModel<SarsLevySchemeYearReturns> dataModel;
	private final String YEAR_FORMAT = HAJConstants.YEAR_FORMAT;

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
	 * Initialize method to get all SarsLevySchemeYearReturns and prepare a for
	 * a create of a new SarsLevySchemeYearReturns
	 * 
	 * @author TechFinium
	 * @see SarsLevySchemeYearReturns
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		sarslevyschemeyearreturnsInfo();
	}

	/**
	 * Get all SarsLevySchemeYearReturns for data table
	 * 
	 * @author TechFinium
	 * @see SarsLevySchemeYearReturns
	 */
	public void sarslevyschemeyearreturnsInfo() {
		dataModel = new LazyDataModel<SarsLevySchemeYearReturns>() {
			private static final long serialVersionUID = 1L;
			private List<SarsLevySchemeYearReturns> retorno = new ArrayList<SarsLevySchemeYearReturns>();

			@Override
			public List<SarsLevySchemeYearReturns> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					retorno = service.allSarsLevySchemeYearReturns(SarsLevySchemeYearReturns.class, first, pageSize,
							sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(SarsLevySchemeYearReturns.class, filters));
				} catch (Exception e) {
					logger.fatal(e.getMessage(), e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SarsLevySchemeYearReturns obj) {
				return obj.getId();
			}

			@Override
			public SarsLevySchemeYearReturns getRowData(String rowKey) {
				for (SarsLevySchemeYearReturns obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	/**
	 * Insert SarsLevySchemeYearReturns into database
	 * 
	 * @author TechFinium
	 * @see SarsLevySchemeYearReturns
	 */
	public void sarslevyschemeyearreturnsInsert() {
		try {
			sarslevyschemeyearreturns.setLastActionDate(getNow());
			sarslevyschemeyearreturns.setLastActionUser(getSessionUI().getActiveUser());
			service.validiateInformation(this.sarslevyschemeyearreturns);
			service.create(this.sarslevyschemeyearreturns);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			sarslevyschemeyearreturnsInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update SarsLevySchemeYearReturns in database
	 * 
	 * @author TechFinium
	 * @see SarsLevySchemeYearReturns
	 */
	public void sarslevyschemeyearreturnsUpdate() {
		try {
			service.update(this.sarslevyschemeyearreturns);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			sarslevyschemeyearreturnsInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete SarsLevySchemeYearReturns from database
	 * 
	 * @author TechFinium
	 * @see SarsLevySchemeYearReturns
	 */
	public void sarslevyschemeyearreturnsDelete() {
		try {
			service.delete(this.sarslevyschemeyearreturns);
			prepareNew();
			sarslevyschemeyearreturnsInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of SarsLevySchemeYearReturns
	 * 
	 * @author TechFinium
	 * @see SarsLevySchemeYearReturns
	 */
	public void prepareNew() {
		sarslevyschemeyearreturns = new SarsLevySchemeYearReturns();
	}

	/*
	 * public List<SelectItem> getSarsLevySchemeYearReturnsDD() {
	 * List<SelectItem> l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * sarslevyschemeyearreturnsInfo(); for (SarsLevySchemeYearReturns ug :
	 * sarslevyschemeyearreturnsList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<SarsLevySchemeYearReturns> complete(String desc) {
		List<SarsLevySchemeYearReturns> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<SarsLevySchemeYearReturns> getSarsLevySchemeYearReturnsList() {
		return sarslevyschemeyearreturnsList;
	}

	public void setSarsLevySchemeYearReturnsList(List<SarsLevySchemeYearReturns> sarslevyschemeyearreturnsList) {
		this.sarslevyschemeyearreturnsList = sarslevyschemeyearreturnsList;
	}

	public SarsLevySchemeYearReturns getSarslevyschemeyearreturns() {
		return sarslevyschemeyearreturns;
	}

	public void setSarslevyschemeyearreturns(SarsLevySchemeYearReturns sarslevyschemeyearreturns) {
		this.sarslevyschemeyearreturns = sarslevyschemeyearreturns;
	}

	public List<SarsLevySchemeYearReturns> getSarsLevySchemeYearReturnsfilteredList() {
		return sarslevyschemeyearreturnsfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param sarslevyschemeyearreturnsfilteredList
	 *            the new sarslevyschemeyearreturnsfilteredList list
	 * @see SarsLevySchemeYearReturns
	 */
	public void setSarsLevySchemeYearReturnsfilteredList(
			List<SarsLevySchemeYearReturns> sarslevyschemeyearreturnsfilteredList) {
		this.sarslevyschemeyearreturnsfilteredList = sarslevyschemeyearreturnsfilteredList;
	}

	public LazyDataModel<SarsLevySchemeYearReturns> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SarsLevySchemeYearReturns> dataModel) {
		this.dataModel = dataModel;
	}

	public String getYEAR_FORMAT() {
		return YEAR_FORMAT;
	}

}
