package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.RememberingRating;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.RememberingRatingService;

// TODO: Auto-generated Javadoc
/**
 * The Class RememberingRatingUI.
 */
@ManagedBean(name = "rememberingratingUI")
@ViewScoped
public class RememberingRatingUI extends AbstractUI {

	/** The service. */
	private RememberingRatingService service = new RememberingRatingService();
	
	/** The rememberingrating list. */
	private List<RememberingRating> rememberingratingList = null;
	
	/** The rememberingratingfiltered list. */
	private List<RememberingRating> rememberingratingfilteredList = null;
	
	/** The rememberingrating. */
	private RememberingRating rememberingrating = null;
	
	/** The data model. */
	private LazyDataModel<RememberingRating> dataModel;

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
	 * Initialize method to get all RememberingRating and prepare a for a create
	 * of a new RememberingRating.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see RememberingRating
	 */
	private void runInit() throws Exception {
		prepareNew();
		rememberingratingInfo();
	}

	/**
	 * Get all RememberingRating for data table.
	 *
	 * @author TechFinium
	 * @see RememberingRating
	 */
	public void rememberingratingInfo() {

		dataModel = new LazyDataModel<RememberingRating>() {

			private static final long serialVersionUID = 1L;
			private List<RememberingRating> retorno = new ArrayList<RememberingRating>();

			@Override
			public List<RememberingRating> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allRememberingRating(RememberingRating.class, first, pageSize, sortField,
							sortOrder, filters);
					dataModel.setRowCount(service.count(RememberingRating.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(RememberingRating obj) {
				return obj.getId();
			}

			@Override
			public RememberingRating getRowData(String rowKey) {
				for (RememberingRating obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert RememberingRating into database.
	 *
	 * @author TechFinium
	 * @see RememberingRating
	 */
	public void rememberingratingInsert() {
		try {
			service.create(this.rememberingrating);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			rememberingratingInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update RememberingRating in database.
	 *
	 * @author TechFinium
	 * @see RememberingRating
	 */
	public void rememberingratingUpdate() {
		try {
			service.update(this.rememberingrating);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			rememberingratingInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete RememberingRating from database.
	 *
	 * @author TechFinium
	 * @see RememberingRating
	 */
	public void rememberingratingDelete() {
		try {
			service.delete(this.rememberingrating);
			prepareNew();
			rememberingratingInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of RememberingRating.
	 *
	 * @author TechFinium
	 * @see RememberingRating
	 */
	public void prepareNew() {
		rememberingrating = new RememberingRating();
	}

	/*
	 * public List<SelectItem> getRememberingRatingDD() { List<SelectItem> l
	 * =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * rememberingratingInfo(); for (RememberingRating ug :
	 * rememberingratingList) { // l.add(new SelectItem(ug.getUserGroupId(),
	 * ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<RememberingRating> complete(String desc) {
		List<RememberingRating> l = null;
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
	 * Gets the remembering rating list.
	 *
	 * @return the remembering rating list
	 */
	public List<RememberingRating> getRememberingRatingList() {
		return rememberingratingList;
	}

	/**
	 * Sets the remembering rating list.
	 *
	 * @param rememberingratingList the new remembering rating list
	 */
	public void setRememberingRatingList(List<RememberingRating> rememberingratingList) {
		this.rememberingratingList = rememberingratingList;
	}

	/**
	 * Gets the rememberingrating.
	 *
	 * @return the rememberingrating
	 */
	public RememberingRating getRememberingrating() {
		return rememberingrating;
	}

	/**
	 * Sets the rememberingrating.
	 *
	 * @param rememberingrating the new rememberingrating
	 */
	public void setRememberingrating(RememberingRating rememberingrating) {
		this.rememberingrating = rememberingrating;
	}

	/**
	 * Gets the remembering ratingfiltered list.
	 *
	 * @return the remembering ratingfiltered list
	 */
	public List<RememberingRating> getRememberingRatingfilteredList() {
		return rememberingratingfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param rememberingratingfilteredList            the new rememberingratingfilteredList list
	 * @see RememberingRating
	 */
	public void setRememberingRatingfilteredList(List<RememberingRating> rememberingratingfilteredList) {
		this.rememberingratingfilteredList = rememberingratingfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<RememberingRating> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<RememberingRating> dataModel) {
		this.dataModel = dataModel;
	}

}
