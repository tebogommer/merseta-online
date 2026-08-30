package haj.com.ui;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.WspStrategicPriorities;
import haj.com.service.WspStrategicPrioritiesService;
import haj.com.entity.datamodel.WspStrategicPrioritiesDatamodel;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "wspstrategicprioritiesUI")
@ViewScoped
public class WspStrategicPrioritiesUI extends AbstractUI {

	private WspStrategicPrioritiesService         service                            = new WspStrategicPrioritiesService();
	private List<WspStrategicPriorities>          wspstrategicprioritiesList         = null;
	private List<WspStrategicPriorities>          wspstrategicprioritiesfilteredList = null;
	private WspStrategicPriorities                wspstrategicpriorities             = null;
	private LazyDataModel<WspStrategicPriorities> dataModel;

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
	 * Initialize method to get all WspStrategicPriorities and prepare a for a create of a new WspStrategicPriorities
	 * @author TechFinium
	 * @see WspStrategicPriorities
	 * @throws Exception
	 * the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		wspstrategicprioritiesInfo();
	}

	/**
	 * Get all WspStrategicPriorities for data table
	 * @author TechFinium
	 * @see WspStrategicPriorities
	 */
	public void wspstrategicprioritiesInfo() {
		dataModel = new WspStrategicPrioritiesDatamodel(getSessionUI().getWspSession());
	}

	/**
	 * Insert WspStrategicPriorities into database
	 * @author TechFinium
	 * @see WspStrategicPriorities
	 */
	public void wspstrategicprioritiesInsert() {
		try {
			service.create(this.wspstrategicpriorities);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			wspstrategicprioritiesInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update WspStrategicPriorities in database
	 * @author TechFinium
	 * @see WspStrategicPriorities
	 */
	public void wspstrategicprioritiesUpdate() {
		try {
			service.update(this.wspstrategicpriorities);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			wspstrategicprioritiesInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete WspStrategicPriorities from database
	 * @author TechFinium
	 * @see WspStrategicPriorities
	 */
	public void wspstrategicprioritiesDelete() {
		try {
			service.delete(this.wspstrategicpriorities);
			prepareNew();
			wspstrategicprioritiesInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of WspStrategicPriorities
	 * @author TechFinium
	 * @see WspStrategicPriorities
	 */
	public void prepareNew() {
		wspstrategicpriorities = new WspStrategicPriorities();
		wspstrategicpriorities.setWsp(getSessionUI().getWspSession());
	}

	/*
	 * public List<SelectItem> getWspStrategicPrioritiesDD() { List<SelectItem> l =new ArrayList<SelectItem>(); l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------")); wspstrategicprioritiesInfo(); for (WspStrategicPriorities ug : wspstrategicprioritiesList) { // l.add(new SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 * @param desc
	 * the desc
	 * @return the list
	 */
	public List<WspStrategicPriorities> complete(String desc) {
		List<WspStrategicPriorities> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<WspStrategicPriorities> getWspStrategicPrioritiesList() {
		return wspstrategicprioritiesList;
	}

	public void setWspStrategicPrioritiesList(List<WspStrategicPriorities> wspstrategicprioritiesList) {
		this.wspstrategicprioritiesList = wspstrategicprioritiesList;
	}

	public WspStrategicPriorities getWspstrategicpriorities() {
		return wspstrategicpriorities;
	}

	public void setWspstrategicpriorities(WspStrategicPriorities wspstrategicpriorities) {
		this.wspstrategicpriorities = wspstrategicpriorities;
	}

	public List<WspStrategicPriorities> getWspStrategicPrioritiesfilteredList() {
		return wspstrategicprioritiesfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * @author TechFinium
	 * @param wspstrategicprioritiesfilteredList
	 * the new wspstrategicprioritiesfilteredList list
	 * @see WspStrategicPriorities
	 */
	public void setWspStrategicPrioritiesfilteredList(List<WspStrategicPriorities> wspstrategicprioritiesfilteredList) {
		this.wspstrategicprioritiesfilteredList = wspstrategicprioritiesfilteredList;
	}

	public LazyDataModel<WspStrategicPriorities> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<WspStrategicPriorities> dataModel) {
		this.dataModel = dataModel;
	}

}
