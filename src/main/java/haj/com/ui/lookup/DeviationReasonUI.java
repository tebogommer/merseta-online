package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.DeviationReason;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.DeviationReasonService;

// TODO: Auto-generated Javadoc
/**
 * The Class DeviationReasonUI.
 */
@ManagedBean(name = "deviationReasonUI")
@ViewScoped
public class DeviationReasonUI extends AbstractUI {

	/** The service. */
	private DeviationReasonService service = new DeviationReasonService();
	
	/** The DeviationReason list. */
	private List<DeviationReason> deviationReasonList = null;
	
	/** The DeviationReasonfiltered list. */
	private List<DeviationReason> deviationReasonfilteredList = null;
	
	/** The DeviationReason. */
	private DeviationReason deviationReason = null;
	
	/** The data model. */
	private LazyDataModel<DeviationReason> dataModel;

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
	 * Initialize method to get all DeviationReason and prepare a for a create of a new
	 * DeviationReason.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see DeviationReason
	 */
	private void runInit() throws Exception {
		prepareNew();
		deviationReasonInfo();
	}

	/**
	 * Get all DeviationReason for data table.
	 *
	 * @author TechFinium
	 * @see DeviationReason
	 */
	public void deviationReasonInfo() {

		dataModel = new LazyDataModel<DeviationReason>() {

			private static final long serialVersionUID = 1L;
			private List<DeviationReason> retorno = new ArrayList<DeviationReason>();

			@Override
			public List<DeviationReason> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allDeviationReason(DeviationReason.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(DeviationReason.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(DeviationReason obj) {
				return obj.getId();
			}

			@Override
			public DeviationReason getRowData(String rowKey) {
				for (DeviationReason obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert DeviationReason into database.
	 *
	 * @author TechFinium
	 * @see DeviationReason
	 */
	public void deviationReasonInsert() {
		try {
			service.create(this.deviationReason);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			deviationReasonInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update DeviationReason in database.
	 *
	 * @author TechFinium
	 * @see DeviationReason
	 */
	public void deviationReasonUpdate() {
		try {
			service.update(this.deviationReason);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			deviationReasonInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete DeviationReason from database.
	 *
	 * @author TechFinium
	 * @see DeviationReason
	 */
	public void deviationReasonDelete() {
		try {
			service.delete(this.deviationReason);
			prepareNew();
			deviationReasonInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of DeviationReason.
	 *
	 * @author TechFinium
	 * @see DeviationReason
	 */
	public void prepareNew() {
		deviationReason = new DeviationReason();
	}

	/*
	 * public List<SelectItem> getDeviationReasonDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * DeviationReasonInfo(); for (DeviationReason ug : DeviationReasonList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<DeviationReason> complete(String desc) {
		List<DeviationReason> l = null;
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
	 * Gets the abet band list.
	 *
	 * @return the abet band list
	 */
	public List<DeviationReason> getDeviationReasonList() {
		return deviationReasonList;
	}

	/**
	 * Sets the abet band list.
	 *
	 * @param DeviationReasonList the new abet band list
	 */
	public void setDeviationReasonList(List<DeviationReason> deviationReasonList) {
		this.deviationReasonList = deviationReasonList;
	}

	/**
	 * Gets the DeviationReason.
	 *
	 * @return the DeviationReason
	 */
	public DeviationReason getDeviationReason() {
		return deviationReason;
	}

	/**
	 * Sets the DeviationReason.
	 *
	 * @param DeviationReason the new DeviationReason
	 */
	public void setDeviationReason(DeviationReason DeviationReason) {
		this.deviationReason = DeviationReason;
	}

	/**
	 * Gets the abet bandfiltered list.
	 *
	 * @return the abet bandfiltered list
	 */
	public List<DeviationReason> getDeviationReasonfilteredList() {
		return deviationReasonfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param DeviationReasonfilteredList            the new DeviationReasonfilteredList list
	 * @see DeviationReason
	 */
	public void setDeviationReasonfilteredList(List<DeviationReason> deviationReasonfilteredList) {
		this.deviationReasonfilteredList = deviationReasonfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<DeviationReason> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<DeviationReason> dataModel) {
		this.dataModel = dataModel;
	}

}
