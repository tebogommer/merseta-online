package haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.SmeQualifications;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.SmeQualificationsService;

@ManagedBean(name = "smequalificationsUI")
@ViewScoped
public class SmeQualificationsUI extends AbstractUI {

	private SmeQualificationsService service = new SmeQualificationsService();
	private List<SmeQualifications> smequalificationsList = null;
	private List<SmeQualifications> smequalificationsfilteredList = null;
	private SmeQualifications smequalifications = null;
	private LazyDataModel<SmeQualifications> dataModel;

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
	 * Initialize method to get all SmeQualifications and prepare a for a create
	 * of a new SmeQualifications
	 * 
	 * @author TechFinium
	 * @see SmeQualifications
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		smequalificationsInfo();
	}

	/**
	 * Get all SmeQualifications for data table
	 * 
	 * @author TechFinium
	 * @see SmeQualifications
	 */
	public void smequalificationsInfo() {
		// dataModel = new SmeQualificationsDatamodel();
	}

	/**
	 * Insert SmeQualifications into database
	 * 
	 * @author TechFinium
	 * @see SmeQualifications
	 */
	public void smequalificationsInsert() {
		try {
			service.create(this.smequalifications);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			smequalificationsInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update SmeQualifications in database
	 * 
	 * @author TechFinium
	 * @see SmeQualifications
	 */
	public void smequalificationsUpdate() {
		try {
			service.update(this.smequalifications);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			smequalificationsInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete SmeQualifications from database
	 * 
	 * @author TechFinium
	 * @see SmeQualifications
	 */
	public void smequalificationsDelete() {
		try {
			service.delete(this.smequalifications);
			prepareNew();
			smequalificationsInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of SmeQualifications
	 * 
	 * @author TechFinium
	 * @see SmeQualifications
	 */
	public void prepareNew() {
		smequalifications = new SmeQualifications();
	}

	/*
	 * public List<SelectItem> getSmeQualificationsDD() { List<SelectItem> l
	 * =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * smequalificationsInfo(); for (SmeQualifications ug :
	 * smequalificationsList) { // l.add(new SelectItem(ug.getUserGroupId(),
	 * ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<SmeQualifications> complete(String desc) {
		List<SmeQualifications> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<SmeQualifications> getSmeQualificationsList() {
		return smequalificationsList;
	}

	public void setSmeQualificationsList(List<SmeQualifications> smequalificationsList) {
		this.smequalificationsList = smequalificationsList;
	}

	public SmeQualifications getSmequalifications() {
		return smequalifications;
	}

	public void setSmequalifications(SmeQualifications smequalifications) {
		this.smequalifications = smequalifications;
	}

	public List<SmeQualifications> getSmeQualificationsfilteredList() {
		return smequalificationsfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param smequalificationsfilteredList
	 *            the new smequalificationsfilteredList list
	 * @see SmeQualifications
	 */
	public void setSmeQualificationsfilteredList(List<SmeQualifications> smequalificationsfilteredList) {
		this.smequalificationsfilteredList = smequalificationsfilteredList;
	}

	public LazyDataModel<SmeQualifications> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SmeQualifications> dataModel) {
		this.dataModel = dataModel;
	}

}
