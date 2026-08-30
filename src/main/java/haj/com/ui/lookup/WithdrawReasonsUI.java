package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.WithdrawReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.WithdrawReasonsService;

// TODO: Auto-generated Javadoc
/**
 * The Class WithdrawReasonsUI.
 */
@ManagedBean(name = "withdrawreasonsUI")
@ViewScoped
public class WithdrawReasonsUI extends AbstractUI {

	/** The service. */
	private WithdrawReasonsService service = new WithdrawReasonsService();
	
	/** The WithdrawReasons list. */
	private List<WithdrawReasons> withdrawReasonsList = null;
	
	/** The WithdrawReasonsfiltered list. */
	private List<WithdrawReasons> withdrawReasonsfilteredList = null;
	
	/** The WithdrawReasons. */
	private WithdrawReasons withdrawReasons = null;
	
	/** The data model. */
	private LazyDataModel<WithdrawReasons> dataModel;

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
	 * Initialize method to get all WithdrawReasons and prepare a for a create of a
	 * new WithdrawReasons.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see WithdrawReasons
	 */
	private void runInit() throws Exception {
		prepareNew();
		withdrawReasonsInfo();
	}

	/**
	 * Get all WithdrawReasons for data table.
	 *
	 * @author TechFinium
	 * @see WithdrawReasons
	 */
	public void withdrawReasonsInfo() {

		dataModel = new LazyDataModel<WithdrawReasons>() {

			private static final long serialVersionUID = 1L;
			private List<WithdrawReasons> retorno = new ArrayList<WithdrawReasons>();

			@Override
			public List<WithdrawReasons> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allWithdrawReasons(WithdrawReasons.class, first, pageSize, sortField, sortOrder,
							filters);
					dataModel.setRowCount(service.count(WithdrawReasons.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(WithdrawReasons obj) {
				return obj.getId();
			}

			@Override
			public WithdrawReasons getRowData(String rowKey) {
				for (WithdrawReasons obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert WithdrawReasons into database.
	 *
	 * @author TechFinium
	 * @see WithdrawReasons
	 */
	public void withdrawReasonsInsert() {
		try {
			if (withdrawReasons.getSoftDeleted() == null) {
				withdrawReasons.setSoftDeleted(false);
			}
			service.create(this.withdrawReasons);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			withdrawReasonsInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update WithdrawReasons in database.
	 *
	 * @author TechFinium
	 * @see WithdrawReasons
	 */
	public void withdrawReasonsUpdate() {
		try {
			service.update(this.withdrawReasons);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			withdrawReasonsInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete WithdrawReasons from database.
	 *
	 * @author TechFinium
	 * @see WithdrawReasons
	 */
	public void withdrawReasonsDelete() {
		try {
			service.delete(this.withdrawReasons);
			prepareNew();
			withdrawReasonsInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted "));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void softDeleteEntry(){
		try {
			withdrawReasons.setSoftDeleted(true);
			service.update(this.withdrawReasons);
			prepareNew();
			withdrawReasonsInfo();
			super.addWarningMessage(super.getEntryLanguage("row.soft.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void removeSoftDelete(){
		try {
			withdrawReasons.setSoftDeleted(false);
			service.update(this.withdrawReasons);
			prepareNew();
			withdrawReasonsInfo();
			addInfoMessage(super.getEntryLanguage("update.successful"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of WithdrawReasons.
	 *
	 * @author TechFinium
	 * @see WithdrawReasons
	 */
	public void prepareNew() {
		withdrawReasons = new WithdrawReasons();
		withdrawReasons.setForCrm(false);
		withdrawReasons.setSoftDeleted(false);
	}

	/*
	 * public List<SelectItem> getWithdrawReasonsDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * WithdrawReasonsInfo(); for (WithdrawReasons ug : WithdrawReasonsList) { //
	 * l.add(new SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return
	 * l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<WithdrawReasons> complete(String desc) {
		List<WithdrawReasons> l = null;
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
	 * Gets the reject reasons list.
	 *
	 * @return the reject reasons list
	 */
	public List<WithdrawReasons> getWithdrawReasonsList() {
		return withdrawReasonsList;
	}

	/**
	 * Sets the reject reasons list.
	 *
	 * @param WithdrawReasonsList the new reject reasons list
	 */
	public void setWithdrawReasonsList(List<WithdrawReasons> withdrawReasonsList) {
		this.withdrawReasonsList = withdrawReasonsList;
	}

	/**
	 * Gets the WithdrawReasons.
	 *
	 * @return the WithdrawReasons
	 */
	public WithdrawReasons getWithdrawReasons() {
		return withdrawReasons;
	}

	/**
	 * Sets the WithdrawReasons.
	 *
	 * @param WithdrawReasons the new WithdrawReasons
	 */
	public void setWithdrawReasons(WithdrawReasons withdrawReasons) {
		this.withdrawReasons = withdrawReasons;
	}

	/**
	 * Gets the reject reasonsfiltered list.
	 *
	 * @return the reject reasonsfiltered list
	 */
	public List<WithdrawReasons> getWithdrawReasonsfilteredList() {
		return withdrawReasonsfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param WithdrawReasonsfilteredList            the new WithdrawReasonsfilteredList list
	 * @see WithdrawReasons
	 */
	public void setWithdrawReasonsfilteredList(List<WithdrawReasons> withdrawReasonsfilteredList) {
		this.withdrawReasonsfilteredList = withdrawReasonsfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<WithdrawReasons> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<WithdrawReasons> dataModel) {
		this.dataModel = dataModel;
	}

}
