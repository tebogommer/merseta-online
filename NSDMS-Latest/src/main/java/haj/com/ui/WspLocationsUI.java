package haj.com.ui;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.WspLocations;
import haj.com.service.WspLocationsService;
import haj.com.entity.datamodel.WspLocationsDatamodel;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "wsplocationsUI")
@ViewScoped
public class WspLocationsUI extends AbstractUI {

	private WspLocationsService         service                  = new WspLocationsService();
	private List<WspLocations>          wsplocationsList         = null;
	private List<WspLocations>          wsplocationsfilteredList = null;
	private WspLocations                wsplocations             = null;
	private LazyDataModel<WspLocations> dataModel;

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
	 * Initialize method to get all WspLocations and prepare a for a create of a new WspLocations
	 * @author TechFinium
	 * @see WspLocations
	 * @throws Exception
	 * the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		wsplocationsInfo();
	}

	/**
	 * Get all WspLocations for data table
	 * @author TechFinium
	 * @see WspLocations
	 */
	public void wsplocationsInfo() {
		dataModel = new WspLocationsDatamodel(getSessionUI().getWspSession());
	}

	/**
	 * Insert WspLocations into database
	 * @author TechFinium
	 * @see WspLocations
	 */
	public void wsplocationsInsert() {
		try {
			service.create(this.wsplocations);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			wsplocationsInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update WspLocations in database
	 * @author TechFinium
	 * @see WspLocations
	 */
	public void wsplocationsUpdate() {
		try {
			service.update(this.wsplocations);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			wsplocationsInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete WspLocations from database
	 * @author TechFinium
	 * @see WspLocations
	 */
	public void wsplocationsDelete() {
		try {
			service.delete(this.wsplocations);
			prepareNew();
			wsplocationsInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of WspLocations
	 * @author TechFinium
	 * @see WspLocations
	 */
	public void prepareNew() {
		wsplocations = new WspLocations();
		wsplocations.setWsp(getSessionUI().getWspSession());
	}

	/*
	 * public List<SelectItem> getWspLocationsDD() { List<SelectItem> l =new ArrayList<SelectItem>(); l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------")); wsplocationsInfo(); for (WspLocations ug : wsplocationsList) { // l.add(new SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 * @param desc
	 * the desc
	 * @return the list
	 */
	public List<WspLocations> complete(String desc) {
		List<WspLocations> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<WspLocations> getWspLocationsList() {
		return wsplocationsList;
	}

	public void setWspLocationsList(List<WspLocations> wsplocationsList) {
		this.wsplocationsList = wsplocationsList;
	}

	public WspLocations getWsplocations() {
		return wsplocations;
	}

	public void setWsplocations(WspLocations wsplocations) {
		this.wsplocations = wsplocations;
	}

	public List<WspLocations> getWspLocationsfilteredList() {
		return wsplocationsfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * @author TechFinium
	 * @param wsplocationsfilteredList
	 * the new wsplocationsfilteredList list
	 * @see WspLocations
	 */
	public void setWspLocationsfilteredList(List<WspLocations> wsplocationsfilteredList) {
		this.wsplocationsfilteredList = wsplocationsfilteredList;
	}

	public LazyDataModel<WspLocations> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<WspLocations> dataModel) {
		this.dataModel = dataModel;
	}

}
