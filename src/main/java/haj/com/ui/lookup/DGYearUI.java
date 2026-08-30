package haj.com.ui.lookup;

import java.util.*;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.shaded.commons.io.FilenameUtils;

import haj.com.entity.Doc;
import haj.com.entity.enums.DGWindowTypeEnum;
import haj.com.entity.lookup.DGYear;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.DocService;
import haj.com.service.lookup.DGYearService;
import haj.com.utils.GenericUtility;

@ManagedBean(name = "dgYearUI")
@ViewScoped
public class DGYearUI extends AbstractUI {

	private DGYearService         service            = new DGYearService();
	private List<DGYear>          DGYearList         = null;
	private List<DGYear>          DGYearfilteredList = null;
	private DGYear                dgYear             = null;
	private LazyDataModel<DGYear> dataModel;
	private Date                  minDate;
	private Date                  maxDate;
	private Doc                   doc;

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
	 * Initialize method to get all DGYear and prepare a for a create of a new DGYear
	 * @author TechFinium
	 * @see DGYear
	 * @throws Exception
	 * the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		DGYearInfo();
		switchWindowType();
	}

	public void prepDoc() {
		doc = new Doc();
	}

	// store document
	public void storeFile(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			doc.setUsers(getSessionUI().getActiveUser());
			doc.setTargetClass(dgYear.getClass().getName());
			doc.setTargetKey(dgYear.getId());
			if (doc.getId() != null) {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			} else {
				DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), getSessionUI().getActiveUser());
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Get all DGYear for data table
	 * @author TechFinium
	 * @see DGYear
	 */
	public void DGYearInfo() {

		dataModel = new LazyDataModel<DGYear>() {
			private static final long serialVersionUID = 1L;
			private List<DGYear>      retorno          = new ArrayList<DGYear>();

			@Override
			public List<DGYear> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (getSessionUI().isAdmin()) {
						retorno = service.allDGYear(DGYear.class, first, pageSize, sortField, sortOrder, filters);
						dataModel.setRowCount(service.count(DGYear.class, filters));
					} else {
						retorno = service.allDGYear(first, pageSize, sortField, sortOrder, filters);
						dataModel.setRowCount(service.count(filters));
					}
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(DGYear obj) {
				return obj.getId();
			}

			@Override
			public DGYear getRowData(String rowKey) {
				for (DGYear obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}

	public void dgYearInsert() {
		try {
			if (dgYear.getDgWindowType() != DGWindowTypeEnum.Standard) {
				dgYear.setFinYear(null);
			}

			if(dgYear.getEndDate() !=null){
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(dgYear.getEndDate());
				calendar.add(Calendar.HOUR_OF_DAY, 23);
				calendar.add(Calendar.MINUTE, 59);
				calendar.add(Calendar.SECOND, 59);
				dgYear.setEndDate(calendar.getTime());
			}
			service.create(this.dgYear);
			prepareNew();
			switchWindowType();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			DGYearInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update DGYear in database
	 * @author TechFinium
	 * @see DGYear
	 */
	public void dgYearUpdate() {
		try {
			service.update(this.dgYear);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			DGYearInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete DGYear from database
	 * @author TechFinium
	 * @see DGYear
	 */
	public void dgYearDelete() {
		try {
			service.delete(this.dgYear);
			prepareNew();
			DGYearInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of DGYear
	 * @author TechFinium
	 * @see DGYear
	 */
	public void prepareNew() {
		dgYear = new DGYear();
		if (getSessionUI().isAdmin()) {
			dgYear.setDgWindowType(DGWindowTypeEnum.Standard);
		} else {
			dgYear.setDgWindowType(DGWindowTypeEnum.Pivitol);
			dgYear.setFinYear(null);
			dgYear.setDescription(null);
		}
	}

	public void switchWindowType() {
		if (dgYear.getDgWindowType() == DGWindowTypeEnum.Standard) {
			dgYearDefaults();
		} else {
			dgYear.setFinYear(null);
			dgYear.setDescription(null);
			maxDate = GenericUtility.addYearsToDate(getNow(), 20);
		}
	}

	public void dgYearDefaults() {
		try {
			Date endlDate = GenericUtility.sdfA.parse(GenericUtility.getYear(new Date()).toString() + "-03-31 00:00:00");
			if (new Date().before(endlDate)) {
				minDate = GenericUtility.sdfA.parse(GenericUtility.getYear(new Date()) + "-04-01 00:00:00");
				maxDate = GenericUtility.getLasttDayOfMonth(GenericUtility.addYearsToDate(GenericUtility.deductMonthsFromDate(minDate, 1), 1));
				dgYear.setFinYear(GenericUtility.getYear(new Date()));
			} else {
				minDate = GenericUtility.sdfA.parse(GenericUtility.getYear(new Date()) + "-04-01 00:00:00");
				maxDate = GenericUtility.getLasttDayOfMonth(GenericUtility.addYearsToDate(GenericUtility.deductMonthsFromDate(minDate, 1), 1));
				dgYear.setFinYear(GenericUtility.getYear(new Date()) + 1);
			}
			if (service.findTotalDGYearByFinYear(dgYear.getFinYear()) == 0l) {
				dgYear.setDescription("Window " + 1);
			} else {
				dgYear.setDescription("Window " + (service.findTotalDGYearByFinYear(dgYear.getFinYear()) + 1));

			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Complete.
	 * @param desc
	 * the desc
	 * @return the list
	 */
	public List<DGYear> complete(String desc) {
		List<DGYear> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<DGYear> getDGYearList() {
		return DGYearList;
	}

	public void setDGYearList(List<DGYear> DGYearList) {
		this.DGYearList = DGYearList;
	}

	public List<DGYear> getDGYearfilteredList() {
		return DGYearfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * @author TechFinium
	 * @param DGYearfilteredList
	 * the new DGYearfilteredList list
	 * @see DGYear
	 */
	public void setDGYearfilteredList(List<DGYear> DGYearfilteredList) {
		this.DGYearfilteredList = DGYearfilteredList;
	}

	public LazyDataModel<DGYear> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<DGYear> dataModel) {
		this.dataModel = dataModel;
	}

	public DGYear getDgYear() {
		return dgYear;
	}

	public void setDgYear(DGYear dgYear) {
		this.dgYear = dgYear;
	}

	public Date getMinDate() {
		return minDate;
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}

	public Date getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

}
