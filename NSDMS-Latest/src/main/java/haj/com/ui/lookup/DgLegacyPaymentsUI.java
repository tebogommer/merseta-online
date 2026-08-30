package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.DgLegacyPayments;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.DgLegacyPaymentsService;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "dglegacypaymentsUI")
@ViewScoped
public class DgLegacyPaymentsUI extends AbstractUI {

	/* Entity */
	private DgLegacyPayments dglegacypayments = null;

	/* Service Level */
	private DgLegacyPaymentsService service = new DgLegacyPaymentsService();

	/* Array Lists */
	private List<DgLegacyPayments> dglegacypaymentsList = null;
	private List<DgLegacyPayments> dglegacypaymentsfilteredList = null;
	private List<String> csvTypeSelectionList = new ArrayList<>();

	/* Lazy data model */
	private LazyDataModel<DgLegacyPayments> dataModel;

	/* Util */
	private CSVUtil csvUtil = new CSVUtil();

	/* Vars */
	private String csvTypeSelection;
	private boolean displayDownload = false;;

	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Initialize method to get all DgLegacyPayments and prepare a for a create
	 * of a new DgLegacyPayments
	 * 
	 * @author TechFinium
	 * @see DgLegacyPayments
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		dglegacypaymentsInfo();
		countAllEntries();
	}

	/**
	 * Get all DgLegacyPayments for data table
	 * 
	 * @author TechFinium
	 * @see DgLegacyPayments
	 */
	public void dglegacypaymentsInfo() {

		dataModel = new LazyDataModel<DgLegacyPayments>() {

			private static final long serialVersionUID = 1L;
			private List<DgLegacyPayments> retorno = new ArrayList<DgLegacyPayments>();

			@Override
			public List<DgLegacyPayments> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allDgLegacyPayments(DgLegacyPayments.class, first, pageSize, sortField, sortOrder,
							filters);
					dataModel.setRowCount(service.count(DgLegacyPayments.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(DgLegacyPayments obj) {
				return obj.getId();
			}

			@Override
			public DgLegacyPayments getRowData(String rowKey) {
				for (DgLegacyPayments obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert DgLegacyPayments into database
	 * 
	 * @author TechFinium
	 * @see DgLegacyPayments
	 */
	public void dglegacypaymentsInsert() {
		try {
			service.create(this.dglegacypayments);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			dglegacypaymentsInfo();
			countAllEntries();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update DgLegacyPayments in database
	 * 
	 * @author TechFinium
	 * @see DgLegacyPayments
	 */
	public void dglegacypaymentsUpdate() {
		try {
			service.update(this.dglegacypayments);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			dglegacypaymentsInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete DgLegacyPayments from database
	 * 
	 * @author TechFinium
	 * @see DgLegacyPayments
	 */
	public void dglegacypaymentsDelete() {
		try {
			service.delete(this.dglegacypayments);
			prepareNew();
			dglegacypaymentsInfo();
			countAllEntries();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of DgLegacyPayments
	 * 
	 * @author TechFinium
	 * @see DgLegacyPayments
	 */
	public void prepareNew() {
		dglegacypayments = new DgLegacyPayments();
	}

	/*
	 * public List<SelectItem> getDgLegacyPaymentsDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * dglegacypaymentsInfo(); for (DgLegacyPayments ug : dglegacypaymentsList)
	 * { // l.add(new SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); }
	 * return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<DgLegacyPayments> complete(String desc) {
		List<DgLegacyPayments> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public void prepTypeSelection() {
		try {
			csvTypeSelectionList = new ArrayList<>();
			csvTypeSelectionList.add(",");
			csvTypeSelectionList.add(";");
			csvTypeSelectionList.add("|");
			csvTypeSelectionList.add("-");
			csvTypeSelection = ",";
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		try {
			if (csvTypeSelection == null || csvTypeSelection.isEmpty()) {
				csvTypeSelection = ",";
			}
			List<DgLegacyPayments> csvDataList = csvUtil.getObjects(DgLegacyPayments.class, event.getFile().getInputstream(), csvTypeSelection);
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			dglegacypaymentsInfo();
			countAllEntries();
		} catch (ConstraintViolationException e) {
			addErrorMessage("ConstraintViolationException");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		} finally {
			csvUtil = new CSVUtil();
		}
	}

	public void clearUploadedEntries() {
		try {
			service.deleteUploadedEntries();
			addInfoMessage("data cleared");
			dglegacypaymentsInfo();
			countAllEntries();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void runValidiations() {
		try {
			service.runValidiations();
			addInfoMessage("Action Complete");
			dglegacypaymentsInfo();
			countAllEntries();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void runValidiationForEntry() {
		try {
			service.runValidiationForEntry(this.dglegacypayments);
			prepareNew();
			dglegacypaymentsInfo();
			addInfoMessage("Action Complete");
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void countAllEntries() {
		try {
			displayDownload = false;
			int entriesCount = service.countAllResults();
			if (entriesCount < 65000) {
				displayDownload = true;
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public List<DgLegacyPayments> getDgLegacyPaymentsList() {
		return dglegacypaymentsList;
	}

	public void setDgLegacyPaymentsList(List<DgLegacyPayments> dglegacypaymentsList) {
		this.dglegacypaymentsList = dglegacypaymentsList;
	}

	public DgLegacyPayments getDglegacypayments() {
		return dglegacypayments;
	}

	public void setDglegacypayments(DgLegacyPayments dglegacypayments) {
		this.dglegacypayments = dglegacypayments;
	}

	public List<DgLegacyPayments> getDgLegacyPaymentsfilteredList() {
		return dglegacypaymentsfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param dglegacypaymentsfilteredList
	 *            the new dglegacypaymentsfilteredList list
	 * @see DgLegacyPayments
	 */
	public void setDgLegacyPaymentsfilteredList(List<DgLegacyPayments> dglegacypaymentsfilteredList) {
		this.dglegacypaymentsfilteredList = dglegacypaymentsfilteredList;
	}

	public LazyDataModel<DgLegacyPayments> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<DgLegacyPayments> dataModel) {
		this.dataModel = dataModel;
	}

	public String getCsvTypeSelection() {
		return csvTypeSelection;
	}

	public void setCsvTypeSelection(String csvTypeSelection) {
		this.csvTypeSelection = csvTypeSelection;
	}

	public boolean isDisplayDownload() {
		return displayDownload;
	}

	public void setDisplayDownload(boolean displayDownload) {
		this.displayDownload = displayDownload;
	}

	public List<String> getCsvTypeSelectionList() {
		return csvTypeSelectionList;
	}

	public void setCsvTypeSelectionList(List<String> csvTypeSelectionList) {
		this.csvTypeSelectionList = csvTypeSelectionList;
	}

}
