package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.MgVerification;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.MgVerificationService;

@ManagedBean(name = "mgverificationUI")
@ViewScoped
public class MgVerificationUI extends AbstractUI {

	private MgVerificationService service = new MgVerificationService();
	private List<MgVerification> mgverificationList = null;
	private List<MgVerification> mgverificationfilteredList = null;
	private MgVerification mgverification = null;
	private LazyDataModel<MgVerification> dataModel;

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
	 * Initialize method to get all MgVerification and prepare a for a create of a
	 * new MgVerification
	 * 
	 * @author TechFinium
	 * @see MgVerification
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		mgverificationInfo();
	}

	/**
	 * Get all MgVerification for data table
	 * 
	 * @author TechFinium
	 * @see MgVerification
	 */
	public void mgverificationInfo() {

		dataModel = new LazyDataModel<MgVerification>() {

			private static final long serialVersionUID = 1L;
			private List<MgVerification> retorno = new ArrayList<MgVerification>();

			@Override
			public List<MgVerification> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allMgVerification(MgVerification.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(MgVerification.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(MgVerification obj) {
				return obj.getId();
			}

			@Override
			public MgVerification getRowData(String rowKey) {
				for (MgVerification obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert MgVerification into database
	 * 
	 * @author TechFinium
	 * @see MgVerification
	 */
	public void mgverificationInsert() {
		try {
			service.create(this.mgverification);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			mgverificationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update MgVerification in database
	 * 
	 * @author TechFinium
	 * @see MgVerification
	 */
	public void mgverificationUpdate() {
		try {
			service.update(this.mgverification);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			mgverificationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete MgVerification from database
	 * 
	 * @author TechFinium
	 * @see MgVerification
	 */
	public void mgverificationDelete() {
		try {
			service.delete(this.mgverification);
			prepareNew();
			mgverificationInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of MgVerification
	 * 
	 * @author TechFinium
	 * @see MgVerification
	 */
	public void prepareNew() {
		mgverification = new MgVerification();
	}

	/*
	 * public List<SelectItem> getMgVerificationDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * mgverificationInfo(); for (MgVerification ug : mgverificationList) { //
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
	public List<MgVerification> complete(String desc) {
		List<MgVerification> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<MgVerification> getMgVerificationList() {
		return mgverificationList;
	}

	public void setMgVerificationList(List<MgVerification> mgverificationList) {
		this.mgverificationList = mgverificationList;
	}

	public MgVerification getMgverification() {
		return mgverification;
	}

	public void setMgverification(MgVerification mgverification) {
		this.mgverification = mgverification;
	}

	public List<MgVerification> getMgVerificationfilteredList() {
		return mgverificationfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param mgverificationfilteredList
	 *            the new mgverificationfilteredList list
	 * @see MgVerification
	 */
	public void setMgVerificationfilteredList(List<MgVerification> mgverificationfilteredList) {
		this.mgverificationfilteredList = mgverificationfilteredList;
	}

	public LazyDataModel<MgVerification> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<MgVerification> dataModel) {
		this.dataModel = dataModel;
	}

}
