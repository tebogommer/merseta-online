package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.SeeingRating;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.SeeingRatingService;

// TODO: Auto-generated Javadoc
/**
 * The Class SeeingRatingUI.
 */
@ManagedBean(name = "seeingratingUI")
@ViewScoped
public class SeeingRatingUI extends AbstractUI {

	/** The service. */
	private SeeingRatingService service = new SeeingRatingService();
	
	/** The seeingrating list. */
	private List<SeeingRating> seeingratingList = null;
	
	/** The seeingratingfiltered list. */
	private List<SeeingRating> seeingratingfilteredList = null;
	
	/** The seeingrating. */
	private SeeingRating seeingrating = null;
	
	/** The data model. */
	private LazyDataModel<SeeingRating> dataModel;

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
	 * Initialize method to get all SeeingRating and prepare a for a create of a
	 * new SeeingRating.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see SeeingRating
	 */
	private void runInit() throws Exception {
		prepareNew();
		seeingratingInfo();
	}

	/**
	 * Get all SeeingRating for data table.
	 *
	 * @author TechFinium
	 * @see SeeingRating
	 */
	public void seeingratingInfo() {

		dataModel = new LazyDataModel<SeeingRating>() {

			private static final long serialVersionUID = 1L;
			private List<SeeingRating> retorno = new ArrayList<SeeingRating>();

			@Override
			public List<SeeingRating> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allSeeingRating(SeeingRating.class, first, pageSize, sortField, sortOrder,
							filters);
					dataModel.setRowCount(service.count(SeeingRating.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SeeingRating obj) {
				return obj.getId();
			}

			@Override
			public SeeingRating getRowData(String rowKey) {
				for (SeeingRating obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert SeeingRating into database.
	 *
	 * @author TechFinium
	 * @see SeeingRating
	 */
	public void seeingratingInsert() {
		try {
			service.create(this.seeingrating);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			seeingratingInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update SeeingRating in database.
	 *
	 * @author TechFinium
	 * @see SeeingRating
	 */
	public void seeingratingUpdate() {
		try {
			service.update(this.seeingrating);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			seeingratingInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete SeeingRating from database.
	 *
	 * @author TechFinium
	 * @see SeeingRating
	 */
	public void seeingratingDelete() {
		try {
			service.delete(this.seeingrating);
			prepareNew();
			seeingratingInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of SeeingRating.
	 *
	 * @author TechFinium
	 * @see SeeingRating
	 */
	public void prepareNew() {
		seeingrating = new SeeingRating();
	}

	/*
	 * public List<SelectItem> getSeeingRatingDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * seeingratingInfo(); for (SeeingRating ug : seeingratingList) { //
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
	public List<SeeingRating> complete(String desc) {
		List<SeeingRating> l = null;
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
	 * Gets the seeing rating list.
	 *
	 * @return the seeing rating list
	 */
	public List<SeeingRating> getSeeingRatingList() {
		return seeingratingList;
	}

	/**
	 * Sets the seeing rating list.
	 *
	 * @param seeingratingList the new seeing rating list
	 */
	public void setSeeingRatingList(List<SeeingRating> seeingratingList) {
		this.seeingratingList = seeingratingList;
	}

	/**
	 * Gets the seeingrating.
	 *
	 * @return the seeingrating
	 */
	public SeeingRating getSeeingrating() {
		return seeingrating;
	}

	/**
	 * Sets the seeingrating.
	 *
	 * @param seeingrating the new seeingrating
	 */
	public void setSeeingrating(SeeingRating seeingrating) {
		this.seeingrating = seeingrating;
	}

	/**
	 * Gets the seeing ratingfiltered list.
	 *
	 * @return the seeing ratingfiltered list
	 */
	public List<SeeingRating> getSeeingRatingfilteredList() {
		return seeingratingfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param seeingratingfilteredList            the new seeingratingfilteredList list
	 * @see SeeingRating
	 */
	public void setSeeingRatingfilteredList(List<SeeingRating> seeingratingfilteredList) {
		this.seeingratingfilteredList = seeingratingfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<SeeingRating> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<SeeingRating> dataModel) {
		this.dataModel = dataModel;
	}

}
