package haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.DgContractingBulkItems;
import haj.com.entity.datamodel.DgContractingBulkItemsDatamodel;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.DgContractingBulkItemsService;

@ManagedBean(name = "dgContractingBulkItemsUI")
@ViewScoped
public class DgContractingBulkItemsUI extends AbstractUI {

	private DgContractingBulkItemsService service = new DgContractingBulkItemsService();
	private List<DgContractingBulkItems> dgcontractingbulkitemsList = null;
	private List<DgContractingBulkItems> dgcontractingbulkitemsfilteredList = null;
	private DgContractingBulkItems dgcontractingbulkitems = null;
	private LazyDataModel<DgContractingBulkItems> dataModel;

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
	 * Initialize method to get all DgContractingBulkItems and prepare a for a
	 * create of a new DgContractingBulkItems
	 * 
	 * @author TechFinium
	 * @see DgContractingBulkItems
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		dgcontractingbulkitemsInfo();
	}

	/**
	 * Get all DgContractingBulkItems for data table
	 * 
	 * @author TechFinium
	 * @see DgContractingBulkItems
	 */
	public void dgcontractingbulkitemsInfo() {
		dataModel = new DgContractingBulkItemsDatamodel();
	}

	/**
	 * Insert DgContractingBulkItems into database
	 * 
	 * @author TechFinium
	 * @see DgContractingBulkItems
	 */
	public void dgcontractingbulkitemsInsert() {
		try {
			service.create(this.dgcontractingbulkitems);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			dgcontractingbulkitemsInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update DgContractingBulkItems in database
	 * 
	 * @author TechFinium
	 * @see DgContractingBulkItems
	 */
	public void dgcontractingbulkitemsUpdate() {
		try {
			service.update(this.dgcontractingbulkitems);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			dgcontractingbulkitemsInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete DgContractingBulkItems from database
	 * 
	 * @author TechFinium
	 * @see DgContractingBulkItems
	 */
	public void dgcontractingbulkitemsDelete() {
		try {
			service.delete(this.dgcontractingbulkitems);
			prepareNew();
			dgcontractingbulkitemsInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of DgContractingBulkItems
	 * 
	 * @author TechFinium
	 * @see DgContractingBulkItems
	 */
	public void prepareNew() {
		dgcontractingbulkitems = new DgContractingBulkItems();
	}

	/*
	 * public List<SelectItem> getDgContractingBulkItemsDD() { List<SelectItem>
	 * l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * dgcontractingbulkitemsInfo(); for (DgContractingBulkItems ug :
	 * dgcontractingbulkitemsList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<DgContractingBulkItems> complete(String desc) {
		List<DgContractingBulkItems> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<DgContractingBulkItems> getDgContractingBulkItemsList() {
		return dgcontractingbulkitemsList;
	}

	public void setDgContractingBulkItemsList(List<DgContractingBulkItems> dgcontractingbulkitemsList) {
		this.dgcontractingbulkitemsList = dgcontractingbulkitemsList;
	}

	public DgContractingBulkItems getDgcontractingbulkitems() {
		return dgcontractingbulkitems;
	}

	public void setDgcontractingbulkitems(DgContractingBulkItems dgcontractingbulkitems) {
		this.dgcontractingbulkitems = dgcontractingbulkitems;
	}

	public List<DgContractingBulkItems> getDgContractingBulkItemsfilteredList() {
		return dgcontractingbulkitemsfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param dgcontractingbulkitemsfilteredList
	 *            the new dgcontractingbulkitemsfilteredList list
	 * @see DgContractingBulkItems
	 */
	public void setDgContractingBulkItemsfilteredList(List<DgContractingBulkItems> dgcontractingbulkitemsfilteredList) {
		this.dgcontractingbulkitemsfilteredList = dgcontractingbulkitemsfilteredList;
	}

	public LazyDataModel<DgContractingBulkItems> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<DgContractingBulkItems> dataModel) {
		this.dataModel = dataModel;
	}

}
