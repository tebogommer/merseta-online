package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.SelfcaringRating;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.SelfcaringRatingService;

// TODO: Auto-generated Javadoc
/**
 * The Class SelfcaringRatingUI.
 */
@ManagedBean(name = "selfcaringratingUI")
@ViewScoped
public class SelfcaringRatingUI extends AbstractUI {

	/** The service. */
	private SelfcaringRatingService service = new SelfcaringRatingService();
	
	/** The selfcaringrating list. */
	private List<SelfcaringRating> selfcaringratingList = null;
	
	/** The selfcaringratingfiltered list. */
	private List<SelfcaringRating> selfcaringratingfilteredList = null;
	
	/** The selfcaringrating. */
	private SelfcaringRating selfcaringrating = null;
	
	/** The data model. */
	private LazyDataModel<SelfcaringRating> dataModel;

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
	 * Initialize method to get all SelfcaringRating and prepare a for a create
	 * of a new SelfcaringRating.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see SelfcaringRating
	 */
	private void runInit() throws Exception {
		prepareNew();
		selfcaringratingInfo();
	}

	/**
	 * Get all SelfcaringRating for data table.
	 *
	 * @author TechFinium
	 * @see SelfcaringRating
	 */
	public void selfcaringratingInfo() {

		dataModel = new LazyDataModel<SelfcaringRating>() {

			private static final long serialVersionUID = 1L;
			private List<SelfcaringRating> retorno = new ArrayList<SelfcaringRating>();

			@Override
			public List<SelfcaringRating> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allSelfcaringRating(SelfcaringRating.class, first, pageSize, sortField, sortOrder,
							filters);
					dataModel.setRowCount(service.count(SelfcaringRating.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SelfcaringRating obj) {
				return obj.getId();
			}

			@Override
			public SelfcaringRating getRowData(String rowKey) {
				for (SelfcaringRating obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert SelfcaringRating into database.
	 *
	 * @author TechFinium
	 * @see SelfcaringRating
	 */
	public void selfcaringratingInsert() {
		try {
			service.create(this.selfcaringrating);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			selfcaringratingInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update SelfcaringRating in database.
	 *
	 * @author TechFinium
	 * @see SelfcaringRating
	 */
	public void selfcaringratingUpdate() {
		try {
			service.update(this.selfcaringrating);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			selfcaringratingInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete SelfcaringRating from database.
	 *
	 * @author TechFinium
	 * @see SelfcaringRating
	 */
	public void selfcaringratingDelete() {
		try {
			service.delete(this.selfcaringrating);
			prepareNew();
			selfcaringratingInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of SelfcaringRating.
	 *
	 * @author TechFinium
	 * @see SelfcaringRating
	 */
	public void prepareNew() {
		selfcaringrating = new SelfcaringRating();
	}

	/*
	 * public List<SelectItem> getSelfcaringRatingDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * selfcaringratingInfo(); for (SelfcaringRating ug : selfcaringratingList)
	 * { // l.add(new SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); }
	 * return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<SelfcaringRating> complete(String desc) {
		List<SelfcaringRating> l = null;
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
	 * Gets the selfcaring rating list.
	 *
	 * @return the selfcaring rating list
	 */
	public List<SelfcaringRating> getSelfcaringRatingList() {
		return selfcaringratingList;
	}

	/**
	 * Sets the selfcaring rating list.
	 *
	 * @param selfcaringratingList the new selfcaring rating list
	 */
	public void setSelfcaringRatingList(List<SelfcaringRating> selfcaringratingList) {
		this.selfcaringratingList = selfcaringratingList;
	}

	/**
	 * Gets the selfcaringrating.
	 *
	 * @return the selfcaringrating
	 */
	public SelfcaringRating getSelfcaringrating() {
		return selfcaringrating;
	}

	/**
	 * Sets the selfcaringrating.
	 *
	 * @param selfcaringrating the new selfcaringrating
	 */
	public void setSelfcaringrating(SelfcaringRating selfcaringrating) {
		this.selfcaringrating = selfcaringrating;
	}

	/**
	 * Gets the selfcaring ratingfiltered list.
	 *
	 * @return the selfcaring ratingfiltered list
	 */
	public List<SelfcaringRating> getSelfcaringRatingfilteredList() {
		return selfcaringratingfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param selfcaringratingfilteredList            the new selfcaringratingfilteredList list
	 * @see SelfcaringRating
	 */
	public void setSelfcaringRatingfilteredList(List<SelfcaringRating> selfcaringratingfilteredList) {
		this.selfcaringratingfilteredList = selfcaringratingfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<SelfcaringRating> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<SelfcaringRating> dataModel) {
		this.dataModel = dataModel;
	}

}
