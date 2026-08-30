package haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.DgContractingBulkEntry;
import haj.com.entity.datamodel.DgContractingBulkEntryDatamodel;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.DgContractingBulkEntryService;

@ManagedBean(name = "dgContractingBulkEntryUI")
@ViewScoped
public class DgContractingBulkEntryUI extends AbstractUI {

	private DgContractingBulkEntryService service = new DgContractingBulkEntryService();
	private List<DgContractingBulkEntry> dgcontractingbulkentryList = null;
	private List<DgContractingBulkEntry> dgcontractingbulkentryfilteredList = null;
	private DgContractingBulkEntry dgcontractingbulkentry = null;
	private LazyDataModel<DgContractingBulkEntry> dataModel;

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
	 * Initialize method to get all DgContractingBulkEntry and prepare a for a
	 * create of a new DgContractingBulkEntry
	 * 
	 * @author TechFinium
	 * @see DgContractingBulkEntry
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		dgcontractingbulkentryInfo();
	}

	/**
	 * Get all DgContractingBulkEntry for data table
	 * 
	 * @author TechFinium
	 * @see DgContractingBulkEntry
	 */
	public void dgcontractingbulkentryInfo() {
		dataModel = new DgContractingBulkEntryDatamodel();
	}

	/**
	 * Insert DgContractingBulkEntry into database
	 * 
	 * @author TechFinium
	 * @see DgContractingBulkEntry
	 */
	public void dgcontractingbulkentryInsert() {
		try {
			service.create(this.dgcontractingbulkentry);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			dgcontractingbulkentryInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update DgContractingBulkEntry in database
	 * 
	 * @author TechFinium
	 * @see DgContractingBulkEntry
	 */
	public void dgcontractingbulkentryUpdate() {
		try {
			service.update(this.dgcontractingbulkentry);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			dgcontractingbulkentryInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete DgContractingBulkEntry from database
	 * 
	 * @author TechFinium
	 * @see DgContractingBulkEntry
	 */
	public void dgcontractingbulkentryDelete() {
		try {
			service.delete(this.dgcontractingbulkentry);
			prepareNew();
			dgcontractingbulkentryInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of DgContractingBulkEntry
	 * 
	 * @author TechFinium
	 * @see DgContractingBulkEntry
	 */
	public void prepareNew() {
		dgcontractingbulkentry = new DgContractingBulkEntry();
	}

	/*
	 * public List<SelectItem> getDgContractingBulkEntryDD() { List<SelectItem>
	 * l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * dgcontractingbulkentryInfo(); for (DgContractingBulkEntry ug :
	 * dgcontractingbulkentryList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<DgContractingBulkEntry> complete(String desc) {
		List<DgContractingBulkEntry> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<DgContractingBulkEntry> getDgContractingBulkEntryList() {
		return dgcontractingbulkentryList;
	}

	public void setDgContractingBulkEntryList(List<DgContractingBulkEntry> dgcontractingbulkentryList) {
		this.dgcontractingbulkentryList = dgcontractingbulkentryList;
	}

	public DgContractingBulkEntry getDgcontractingbulkentry() {
		return dgcontractingbulkentry;
	}

	public void setDgcontractingbulkentry(DgContractingBulkEntry dgcontractingbulkentry) {
		this.dgcontractingbulkentry = dgcontractingbulkentry;
	}

	public List<DgContractingBulkEntry> getDgContractingBulkEntryfilteredList() {
		return dgcontractingbulkentryfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param dgcontractingbulkentryfilteredList
	 *            the new dgcontractingbulkentryfilteredList list
	 * @see DgContractingBulkEntry
	 */
	public void setDgContractingBulkEntryfilteredList(List<DgContractingBulkEntry> dgcontractingbulkentryfilteredList) {
		this.dgcontractingbulkentryfilteredList = dgcontractingbulkentryfilteredList;
	}

	public LazyDataModel<DgContractingBulkEntry> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<DgContractingBulkEntry> dataModel) {
		this.dataModel = dataModel;
	}

}
