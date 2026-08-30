package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.DgVerification;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.DgVerificationService;

@ManagedBean(name = "dgverificationUI")
@ViewScoped
public class DgVerificationUI extends AbstractUI {

	private DgVerificationService service = new DgVerificationService();
	private List<DgVerification> dgverificationList = null;
	private List<DgVerification> dgverificationfilteredList = null;
	private DgVerification dgverification = null;
	private LazyDataModel<DgVerification> dataModel;

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
	 * Initialize method to get all DgVerification and prepare a for a create of a
	 * new DgVerification
	 * 
	 * @author TechFinium
	 * @see DgVerification
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		dgverificationInfo();
	}

	/**
	 * Get all DgVerification for data table
	 * 
	 * @author TechFinium
	 * @see DgVerification
	 */
	public void dgverificationInfo() {

		dataModel = new LazyDataModel<DgVerification>() {

			private static final long serialVersionUID = 1L;
			private List<DgVerification> retorno = new ArrayList<DgVerification>();

			@Override
			public List<DgVerification> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allDgVerification(DgVerification.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(DgVerification.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(DgVerification obj) {
				return obj.getId();
			}

			@Override
			public DgVerification getRowData(String rowKey) {
				for (DgVerification obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert DgVerification into database
	 * 
	 * @author TechFinium
	 * @see DgVerification
	 */
	public void dgverificationInsert() {
		try {
			service.create(this.dgverification);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			dgverificationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update DgVerification in database
	 * 
	 * @author TechFinium
	 * @see DgVerification
	 */
	public void dgverificationUpdate() {
		try {
			service.update(this.dgverification);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			dgverificationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete DgVerification from database
	 * 
	 * @author TechFinium
	 * @see DgVerification
	 */
	public void dgverificationDelete() {
		try {
			service.delete(this.dgverification);
			prepareNew();
			dgverificationInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of DgVerification
	 * 
	 * @author TechFinium
	 * @see DgVerification
	 */
	public void prepareNew() {
		dgverification = new DgVerification();
	}

	/*
	 * public List<SelectItem> getDgVerificationDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * dgverificationInfo(); for (DgVerification ug : dgverificationList) { //
	 * l.add(new SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return
	 * l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<DgVerification> complete(String desc) {
		List<DgVerification> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<DgVerification> getDgVerificationList() {
		return dgverificationList;
	}

	public void setDgVerificationList(List<DgVerification> dgverificationList) {
		this.dgverificationList = dgverificationList;
	}

	public DgVerification getDgverification() {
		return dgverification;
	}

	public void setDgverification(DgVerification dgverification) {
		this.dgverification = dgverification;
	}

	public List<DgVerification> getDgVerificationfilteredList() {
		return dgverificationfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param dgverificationfilteredList
	 *            the new dgverificationfilteredList list
	 * @see DgVerification
	 */
	public void setDgVerificationfilteredList(List<DgVerification> dgverificationfilteredList) {
		this.dgverificationfilteredList = dgverificationfilteredList;
	}

	public LazyDataModel<DgVerification> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<DgVerification> dataModel) {
		this.dataModel = dataModel;
	}

}
