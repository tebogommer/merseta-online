package haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.DateChangeMultipleSelection;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.DateChangeMultipleSelectionService;

@ManagedBean(name = "datechangemultipleselectionUI")
@ViewScoped
public class DateChangeMultipleSelectionUI extends AbstractUI {

	private DateChangeMultipleSelectionService service = new DateChangeMultipleSelectionService();
	private List<DateChangeMultipleSelection> datechangemultipleselectionList = null;
	private List<DateChangeMultipleSelection> datechangemultipleselectionfilteredList = null;
	private DateChangeMultipleSelection datechangemultipleselection = null;
	private LazyDataModel<DateChangeMultipleSelection> dataModel;

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
	 * Initialize method to get all DateChangeMultipleSelection and prepare a
	 * for a create of a new DateChangeMultipleSelection
	 * 
	 * @author TechFinium
	 * @see DateChangeMultipleSelection
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		datechangemultipleselectionInfo();
	}

	/**
	 * Get all DateChangeMultipleSelection for data table
	 * 
	 * @author TechFinium
	 * @see DateChangeMultipleSelection
	 */
	public void datechangemultipleselectionInfo() {
		// dataModel = new DateChangeMultipleSelectionDatamodel();
	}

	/**
	 * Insert DateChangeMultipleSelection into database
	 * 
	 * @author TechFinium
	 * @see DateChangeMultipleSelection
	 */
	public void datechangemultipleselectionInsert() {
		try {
			service.create(this.datechangemultipleselection);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			datechangemultipleselectionInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update DateChangeMultipleSelection in database
	 * 
	 * @author TechFinium
	 * @see DateChangeMultipleSelection
	 */
	public void datechangemultipleselectionUpdate() {
		try {
			service.update(this.datechangemultipleselection);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			datechangemultipleselectionInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete DateChangeMultipleSelection from database
	 * 
	 * @author TechFinium
	 * @see DateChangeMultipleSelection
	 */
	public void datechangemultipleselectionDelete() {
		try {
			service.delete(this.datechangemultipleselection);
			prepareNew();
			datechangemultipleselectionInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of DateChangeMultipleSelection
	 * 
	 * @author TechFinium
	 * @see DateChangeMultipleSelection
	 */
	public void prepareNew() {
		datechangemultipleselection = new DateChangeMultipleSelection();
	}

	/*
	 * public List<SelectItem> getDateChangeMultipleSelectionDD() {
	 * List<SelectItem> l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * datechangemultipleselectionInfo(); for (DateChangeMultipleSelection ug :
	 * datechangemultipleselectionList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<DateChangeMultipleSelection> complete(String desc) {
		List<DateChangeMultipleSelection> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<DateChangeMultipleSelection> getDateChangeMultipleSelectionList() {
		return datechangemultipleselectionList;
	}

	public void setDateChangeMultipleSelectionList(List<DateChangeMultipleSelection> datechangemultipleselectionList) {
		this.datechangemultipleselectionList = datechangemultipleselectionList;
	}

	public DateChangeMultipleSelection getDatechangemultipleselection() {
		return datechangemultipleselection;
	}

	public void setDatechangemultipleselection(DateChangeMultipleSelection datechangemultipleselection) {
		this.datechangemultipleselection = datechangemultipleselection;
	}

	public List<DateChangeMultipleSelection> getDateChangeMultipleSelectionfilteredList() {
		return datechangemultipleselectionfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param datechangemultipleselectionfilteredList
	 *            the new datechangemultipleselectionfilteredList list
	 * @see DateChangeMultipleSelection
	 */
	public void setDateChangeMultipleSelectionfilteredList(
			List<DateChangeMultipleSelection> datechangemultipleselectionfilteredList) {
		this.datechangemultipleselectionfilteredList = datechangemultipleselectionfilteredList;
	}

	public LazyDataModel<DateChangeMultipleSelection> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<DateChangeMultipleSelection> dataModel) {
		this.dataModel = dataModel;
	}

}
