package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.WalkingRating;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.WalkingRatingService;

// TODO: Auto-generated Javadoc
/**
 * The Class WalkingRatingUI.
 */
@ManagedBean(name = "walkingratingUI")
@ViewScoped
public class WalkingRatingUI extends AbstractUI {

	/** The service. */
	private WalkingRatingService service = new WalkingRatingService();
	
	/** The walkingrating list. */
	private List<WalkingRating> walkingratingList = null;
	
	/** The walkingratingfiltered list. */
	private List<WalkingRating> walkingratingfilteredList = null;
	
	/** The walkingrating. */
	private WalkingRating walkingrating = null;
	
	/** The data model. */
	private LazyDataModel<WalkingRating> dataModel;

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
	 * Initialize method to get all WalkingRating and prepare a for a create of
	 * a new WalkingRating.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see WalkingRating
	 */
	private void runInit() throws Exception {
		prepareNew();
		walkingratingInfo();
	}

	/**
	 * Get all WalkingRating for data table.
	 *
	 * @author TechFinium
	 * @see WalkingRating
	 */
	public void walkingratingInfo() {

		dataModel = new LazyDataModel<WalkingRating>() {

			private static final long serialVersionUID = 1L;
			private List<WalkingRating> retorno = new ArrayList<WalkingRating>();

			@Override
			public List<WalkingRating> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allWalkingRating(WalkingRating.class, first, pageSize, sortField, sortOrder,
							filters);
					dataModel.setRowCount(service.count(WalkingRating.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(WalkingRating obj) {
				return obj.getId();
			}

			@Override
			public WalkingRating getRowData(String rowKey) {
				for (WalkingRating obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert WalkingRating into database.
	 *
	 * @author TechFinium
	 * @see WalkingRating
	 */
	public void walkingratingInsert() {
		try {
			service.create(this.walkingrating);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			walkingratingInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update WalkingRating in database.
	 *
	 * @author TechFinium
	 * @see WalkingRating
	 */
	public void walkingratingUpdate() {
		try {
			service.update(this.walkingrating);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			walkingratingInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete WalkingRating from database.
	 *
	 * @author TechFinium
	 * @see WalkingRating
	 */
	public void walkingratingDelete() {
		try {
			service.delete(this.walkingrating);
			prepareNew();
			walkingratingInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of WalkingRating.
	 *
	 * @author TechFinium
	 * @see WalkingRating
	 */
	public void prepareNew() {
		walkingrating = new WalkingRating();
	}

	/*
	 * public List<SelectItem> getWalkingRatingDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * walkingratingInfo(); for (WalkingRating ug : walkingratingList) { //
	 * l.add(new SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); }
	 * return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<WalkingRating> complete(String desc) {
		List<WalkingRating> l = null;
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
	 * Gets the walking rating list.
	 *
	 * @return the walking rating list
	 */
	public List<WalkingRating> getWalkingRatingList() {
		return walkingratingList;
	}

	/**
	 * Sets the walking rating list.
	 *
	 * @param walkingratingList the new walking rating list
	 */
	public void setWalkingRatingList(List<WalkingRating> walkingratingList) {
		this.walkingratingList = walkingratingList;
	}

	/**
	 * Gets the walkingrating.
	 *
	 * @return the walkingrating
	 */
	public WalkingRating getWalkingrating() {
		return walkingrating;
	}

	/**
	 * Sets the walkingrating.
	 *
	 * @param walkingrating the new walkingrating
	 */
	public void setWalkingrating(WalkingRating walkingrating) {
		this.walkingrating = walkingrating;
	}

	/**
	 * Gets the walking ratingfiltered list.
	 *
	 * @return the walking ratingfiltered list
	 */
	public List<WalkingRating> getWalkingRatingfilteredList() {
		return walkingratingfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param walkingratingfilteredList            the new walkingratingfilteredList list
	 * @see WalkingRating
	 */
	public void setWalkingRatingfilteredList(List<WalkingRating> walkingratingfilteredList) {
		this.walkingratingfilteredList = walkingratingfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<WalkingRating> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<WalkingRating> dataModel) {
		this.dataModel = dataModel;
	}

}
