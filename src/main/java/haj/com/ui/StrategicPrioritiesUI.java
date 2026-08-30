package haj.com.ui;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.StrategicPriorities;
import haj.com.service.StrategicPrioritiesService;
import haj.com.entity.datamodel.StrategicPrioritiesDatamodel;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "strategicprioritiesUI")
@ViewScoped
public class StrategicPrioritiesUI extends AbstractUI {

	private StrategicPrioritiesService         service                         = new StrategicPrioritiesService();
	private List<StrategicPriorities>          strategicprioritiesList         = null;
	private List<StrategicPriorities>          strategicprioritiesfilteredList = null;
	private StrategicPriorities                strategicpriorities             = null;
	private LazyDataModel<StrategicPriorities> dataModel;

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
	 * Initialize method to get all StrategicPriorities and prepare a for a create of a new StrategicPriorities
	 * @author TechFinium
	 * @see StrategicPriorities
	 * @throws Exception
	 * the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		strategicprioritiesInfo();
	}

	/**
	 * Get all StrategicPriorities for data table
	 * @author TechFinium
	 * @see StrategicPriorities
	 */
	public void strategicprioritiesInfo() {
		dataModel = new StrategicPrioritiesDatamodel();
	}

	/**
	 * Insert StrategicPriorities into database
	 * @author TechFinium
	 * @see StrategicPriorities
	 */
	public void strategicprioritiesInsert() {
		try {
			service.create(this.strategicpriorities);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			strategicprioritiesInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update StrategicPriorities in database
	 * @author TechFinium
	 * @see StrategicPriorities
	 */
	public void strategicprioritiesUpdate() {
		try {
			service.update(this.strategicpriorities);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			strategicprioritiesInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete StrategicPriorities from database
	 * @author TechFinium
	 * @see StrategicPriorities
	 */
	public void strategicprioritiesDelete() {
		try {
			service.delete(this.strategicpriorities);
			prepareNew();
			strategicprioritiesInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of StrategicPriorities
	 * @author TechFinium
	 * @see StrategicPriorities
	 */
	public void prepareNew() {
		strategicpriorities = new StrategicPriorities();
		strategicpriorities.setCreateUser(getSessionUI().getActiveUser());
	}

	/*
	 * public List<SelectItem> getStrategicPrioritiesDD() { List<SelectItem> l =new ArrayList<SelectItem>(); l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------")); strategicprioritiesInfo(); for (StrategicPriorities ug : strategicprioritiesList) { // l.add(new SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 * @param desc
	 * the desc
	 * @return the list
	 */
	public List<StrategicPriorities> complete(String desc) {
		List<StrategicPriorities> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<StrategicPriorities> getStrategicPrioritiesList() {
		return strategicprioritiesList;
	}

	public void setStrategicPrioritiesList(List<StrategicPriorities> strategicprioritiesList) {
		this.strategicprioritiesList = strategicprioritiesList;
	}

	public StrategicPriorities getStrategicpriorities() {
		return strategicpriorities;
	}

	public void setStrategicpriorities(StrategicPriorities strategicpriorities) {
		this.strategicpriorities = strategicpriorities;
	}

	public List<StrategicPriorities> getStrategicPrioritiesfilteredList() {
		return strategicprioritiesfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * @author TechFinium
	 * @param strategicprioritiesfilteredList
	 * the new strategicprioritiesfilteredList list
	 * @see StrategicPriorities
	 */
	public void setStrategicPrioritiesfilteredList(List<StrategicPriorities> strategicprioritiesfilteredList) {
		this.strategicprioritiesfilteredList = strategicprioritiesfilteredList;
	}

	public LazyDataModel<StrategicPriorities> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<StrategicPriorities> dataModel) {
		this.dataModel = dataModel;
	}

}
