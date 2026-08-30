package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.HearingRating;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.HearingRatingService;

// TODO: Auto-generated Javadoc
/**
 * The Class HearingRatingUI.
 */
@ManagedBean(name = "hearingratingUI")
@ViewScoped
public class HearingRatingUI extends AbstractUI {

	/** The service. */
	private HearingRatingService service = new HearingRatingService();
	
	/** The hearingrating list. */
	private List<HearingRating> hearingratingList = null;
	
	/** The hearingratingfiltered list. */
	private List<HearingRating> hearingratingfilteredList = null;
	
	/** The hearingrating. */
	private HearingRating hearingrating = null;
	
	/** The data model. */
	private LazyDataModel<HearingRating> dataModel;

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
	 * Initialize method to get all HearingRating and prepare a for a create of
	 * a new HearingRating.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see HearingRating
	 */
	private void runInit() throws Exception {
		prepareNew();
		hearingratingInfo();
	}

	/**
	 * Get all HearingRating for data table.
	 *
	 * @author TechFinium
	 * @see HearingRating
	 */
	public void hearingratingInfo() {

		dataModel = new LazyDataModel<HearingRating>() {

			private static final long serialVersionUID = 1L;
			private List<HearingRating> retorno = new ArrayList<HearingRating>();

			@Override
			public List<HearingRating> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allHearingRating(HearingRating.class, first, pageSize, sortField, sortOrder,
							filters);
					dataModel.setRowCount(service.count(HearingRating.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(HearingRating obj) {
				return obj.getId();
			}

			@Override
			public HearingRating getRowData(String rowKey) {
				for (HearingRating obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert HearingRating into database.
	 *
	 * @author TechFinium
	 * @see HearingRating
	 */
	public void hearingratingInsert() {
		try {
			service.create(this.hearingrating);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			hearingratingInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update HearingRating in database.
	 *
	 * @author TechFinium
	 * @see HearingRating
	 */
	public void hearingratingUpdate() {
		try {
			service.update(this.hearingrating);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			hearingratingInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete HearingRating from database.
	 *
	 * @author TechFinium
	 * @see HearingRating
	 */
	public void hearingratingDelete() {
		try {
			service.delete(this.hearingrating);
			prepareNew();
			hearingratingInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of HearingRating.
	 *
	 * @author TechFinium
	 * @see HearingRating
	 */
	public void prepareNew() {
		hearingrating = new HearingRating();
	}

	/*
	 * public List<SelectItem> getHearingRatingDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * hearingratingInfo(); for (HearingRating ug : hearingratingList) { //
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
	public List<HearingRating> complete(String desc) {
		List<HearingRating> l = null;
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
	 * Gets the hearing rating list.
	 *
	 * @return the hearing rating list
	 */
	public List<HearingRating> getHearingRatingList() {
		return hearingratingList;
	}

	/**
	 * Sets the hearing rating list.
	 *
	 * @param hearingratingList the new hearing rating list
	 */
	public void setHearingRatingList(List<HearingRating> hearingratingList) {
		this.hearingratingList = hearingratingList;
	}

	/**
	 * Gets the hearingrating.
	 *
	 * @return the hearingrating
	 */
	public HearingRating getHearingrating() {
		return hearingrating;
	}

	/**
	 * Sets the hearingrating.
	 *
	 * @param hearingrating the new hearingrating
	 */
	public void setHearingrating(HearingRating hearingrating) {
		this.hearingrating = hearingrating;
	}

	/**
	 * Gets the hearing ratingfiltered list.
	 *
	 * @return the hearing ratingfiltered list
	 */
	public List<HearingRating> getHearingRatingfilteredList() {
		return hearingratingfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param hearingratingfilteredList            the new hearingratingfilteredList list
	 * @see HearingRating
	 */
	public void setHearingRatingfilteredList(List<HearingRating> hearingratingfilteredList) {
		this.hearingratingfilteredList = hearingratingfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<HearingRating> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<HearingRating> dataModel) {
		this.dataModel = dataModel;
	}

}
