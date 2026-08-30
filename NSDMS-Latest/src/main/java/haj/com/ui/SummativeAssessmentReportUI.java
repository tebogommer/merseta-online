package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.SummativeAssessmentReport;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.SummativeAssessmentReportService;

@ManagedBean(name = "summativeassessmentreportUI")
@ViewScoped
public class SummativeAssessmentReportUI extends AbstractUI {

	private SummativeAssessmentReportService service = new SummativeAssessmentReportService();
	private List<SummativeAssessmentReport> summativeassessmentreportList = null;
	private List<SummativeAssessmentReport> summativeassessmentreportfilteredList = null;
	private SummativeAssessmentReport summativeassessmentreport = null;
	private LazyDataModel<SummativeAssessmentReport> dataModel;

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
	 * Initialize method to get all SummativeAssessmentReport and prepare a for a
	 * create of a new SummativeAssessmentReport
	 * 
	 * @author TechFinium
	 * @see SummativeAssessmentReport
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		summativeassessmentreportInfo();
	}

	/**
	 * Get all SummativeAssessmentReport for data table
	 * 
	 * @author TechFinium
	 * @see SummativeAssessmentReport
	 */
	public void summativeassessmentreportInfo() {

		dataModel = new LazyDataModel<SummativeAssessmentReport>() {

			private static final long serialVersionUID = 1L;
			private List<SummativeAssessmentReport> retorno = new ArrayList<SummativeAssessmentReport>();

			@Override
			public List<SummativeAssessmentReport> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allSummativeAssessmentReport(SummativeAssessmentReport.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(SummativeAssessmentReport.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SummativeAssessmentReport obj) {
				return obj.getId();
			}

			@Override
			public SummativeAssessmentReport getRowData(String rowKey) {
				for (SummativeAssessmentReport obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert SummativeAssessmentReport into database
	 * 
	 * @author TechFinium
	 * @see SummativeAssessmentReport
	 */
	public void summativeassessmentreportInsert() {
		try {
			service.create(this.summativeassessmentreport, null);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			summativeassessmentreportInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update SummativeAssessmentReport in database
	 * 
	 * @author TechFinium
	 * @see SummativeAssessmentReport
	 */
	public void summativeassessmentreportUpdate() {
		try {
			service.update(this.summativeassessmentreport);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			summativeassessmentreportInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete SummativeAssessmentReport from database
	 * 
	 * @author TechFinium
	 * @see SummativeAssessmentReport
	 */
	public void summativeassessmentreportDelete() {
		try {
			service.delete(this.summativeassessmentreport);
			prepareNew();
			summativeassessmentreportInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of SummativeAssessmentReport
	 * 
	 * @author TechFinium
	 * @see SummativeAssessmentReport
	 */
	public void prepareNew() {
		summativeassessmentreport = new SummativeAssessmentReport();
	}

	/*
	 * public List<SelectItem> getSummativeAssessmentReportDD() { List<SelectItem> l
	 * =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * summativeassessmentreportInfo(); for (SummativeAssessmentReport ug :
	 * summativeassessmentreportList) { // l.add(new SelectItem(ug.getUserGroupId(),
	 * ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<SummativeAssessmentReport> complete(String desc) {
		List<SummativeAssessmentReport> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<SummativeAssessmentReport> getSummativeAssessmentReportList() {
		return summativeassessmentreportList;
	}

	public void setSummativeAssessmentReportList(List<SummativeAssessmentReport> summativeassessmentreportList) {
		this.summativeassessmentreportList = summativeassessmentreportList;
	}

	public SummativeAssessmentReport getSummativeassessmentreport() {
		return summativeassessmentreport;
	}

	public void setSummativeassessmentreport(SummativeAssessmentReport summativeassessmentreport) {
		this.summativeassessmentreport = summativeassessmentreport;
	}

	public List<SummativeAssessmentReport> getSummativeAssessmentReportfilteredList() {
		return summativeassessmentreportfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param summativeassessmentreportfilteredList
	 *            the new summativeassessmentreportfilteredList list
	 * @see SummativeAssessmentReport
	 */
	public void setSummativeAssessmentReportfilteredList(List<SummativeAssessmentReport> summativeassessmentreportfilteredList) {
		this.summativeassessmentreportfilteredList = summativeassessmentreportfilteredList;
	}

	public LazyDataModel<SummativeAssessmentReport> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SummativeAssessmentReport> dataModel) {
		this.dataModel = dataModel;
	}

}
