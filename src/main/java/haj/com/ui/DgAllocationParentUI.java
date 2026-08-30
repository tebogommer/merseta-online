package haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.DgAllocationParent;
import haj.com.entity.datamodel.DgAllocationParentReportDatamodel;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.DgAllocationParentService;

@ManagedBean(name = "dgallocationparentUI")
@ViewScoped
public class DgAllocationParentUI extends AbstractUI {

	private DgAllocationParentService service = new DgAllocationParentService();
	private List<DgAllocationParent> dgallocationparentList = null;
	private List<DgAllocationParent> dgallocationparentfilteredList = null;
	private DgAllocationParent dgallocationparent = null;
	private LazyDataModel<DgAllocationParent> dataModel;

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
	 * Initialize method to get all DgAllocationParent and prepare a for a
	 * create of a new DgAllocationParent
	 * 
	 * @author TechFinium
	 * @see DgAllocationParent
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		dgallocationparentInfo();
	}

	/**
	 * Get all DgAllocationParent for data table
	 * 
	 * @author TechFinium
	 * @see DgAllocationParent
	 */
	public void dgallocationparentInfo() {
		dataModel = new DgAllocationParentReportDatamodel();
	}

	/**
	 * Insert DgAllocationParent into database
	 * 
	 * @author TechFinium
	 * @see DgAllocationParent
	 */
	public void dgallocationparentInsert() {
		try {
			service.create(this.dgallocationparent);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			dgallocationparentInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update DgAllocationParent in database
	 * 
	 * @author TechFinium
	 * @see DgAllocationParent
	 */
	public void dgallocationparentUpdate() {
		try {
			service.update(this.dgallocationparent);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			dgallocationparentInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete DgAllocationParent from database
	 * 
	 * @author TechFinium
	 * @see DgAllocationParent
	 */
	public void dgallocationparentDelete() {
		try {
			service.delete(this.dgallocationparent);
			prepareNew();
			dgallocationparentInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of DgAllocationParent
	 * 
	 * @author TechFinium
	 * @see DgAllocationParent
	 */
	public void prepareNew() {
		dgallocationparent = new DgAllocationParent();
	}

	/*
	 * public List<SelectItem> getDgAllocationParentDD() { List<SelectItem> l
	 * =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * dgallocationparentInfo(); for (DgAllocationParent ug :
	 * dgallocationparentList) { // l.add(new SelectItem(ug.getUserGroupId(),
	 * ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<DgAllocationParent> complete(String desc) {
		List<DgAllocationParent> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<DgAllocationParent> getDgAllocationParentList() {
		return dgallocationparentList;
	}

	public void setDgAllocationParentList(List<DgAllocationParent> dgallocationparentList) {
		this.dgallocationparentList = dgallocationparentList;
	}

	public DgAllocationParent getDgallocationparent() {
		return dgallocationparent;
	}

	public void setDgallocationparent(DgAllocationParent dgallocationparent) {
		this.dgallocationparent = dgallocationparent;
	}

	public List<DgAllocationParent> getDgAllocationParentfilteredList() {
		return dgallocationparentfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param dgallocationparentfilteredList
	 *            the new dgallocationparentfilteredList list
	 * @see DgAllocationParent
	 */
	public void setDgAllocationParentfilteredList(List<DgAllocationParent> dgallocationparentfilteredList) {
		this.dgallocationparentfilteredList = dgallocationparentfilteredList;
	}

	public LazyDataModel<DgAllocationParent> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<DgAllocationParent> dataModel) {
		this.dataModel = dataModel;
	}

	public List<DgAllocationParent> getDgallocationparentfilteredList() {
		return dgallocationparentfilteredList;
	}

	public void setDgallocationparentfilteredList(List<DgAllocationParent> dgallocationparentfilteredList) {
		this.dgallocationparentfilteredList = dgallocationparentfilteredList;
	}

}
