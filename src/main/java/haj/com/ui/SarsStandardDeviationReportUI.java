package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.bean.SarsLevyStandardDeviationDataBean;
import haj.com.bean.SarsLevyStandardDeviationMonthBean;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.sars.SarsFiles;
import haj.com.service.SarsFilesService;
import haj.com.service.SarsLevyDetailsService;

@ManagedBean(name = "sarsStandardDeviationReportUI")
@ViewScoped
public class SarsStandardDeviationReportUI extends AbstractUI {

	/* Entity */
	private SarsFiles sarsFiles = null;

	/* Service levels */
	private SarsLevyDetailsService sarsLevyDetailsService;
	private SarsFilesService sarsFilesService;

	/* Array Lists */
	private List<SarsLevyStandardDeviationMonthBean> sarsLevyStandardDeviationMonthList;

	/* Data Models */
	private LazyDataModel<SarsFiles> sarsDataModel;
	private LazyDataModel<SarsLevyStandardDeviationDataBean> levyDeviationReportDataModel;

	/* Booleans */
	private boolean reportGeneratedWithoutError = false;

	/**
	 * Inits the.
	 */
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

	private void runInit() throws Exception {
		populateServiceLevels();
		populateDefaultInformation();
		sarsfilesInfo();
	}

	private void populateServiceLevels() throws Exception {
		if (sarsLevyDetailsService == null) {
			sarsLevyDetailsService = new SarsLevyDetailsService();
		}
		if (sarsFilesService == null) {
			sarsFilesService = new SarsFilesService();
		}
	}

	private void populateDefaultInformation() throws Exception {
		// set to default for result section not to display
		reportGeneratedWithoutError = false;
	}

	public void sarsfilesInfo() {
		sarsDataModel = new LazyDataModel<SarsFiles>() {
			private static final long serialVersionUID = 1L;
			private List<SarsFiles> retorno = new ArrayList<SarsFiles>();

			@Override
			public List<SarsFiles> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					if (sortField == null) {
						sortField = "forMonth";
						sortOrder = sortOrder.DESCENDING;
					}
					retorno = sarsFilesService.allSarsFiles(SarsFiles.class, first, pageSize, sortField, sortOrder,
							filters);
					sarsDataModel.setRowCount(sarsFilesService.count(SarsFiles.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
					e.printStackTrace();
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SarsFiles obj) {
				return obj.getId();
			}

			@Override
			public SarsFiles getRowData(String rowKey) {
				for (SarsFiles obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	public void generateDeviationReport() {
		try {
			addInfoMessage("Generation Complete");
			sarsLevyStandardDeviationMonthList = sarsLevyDetailsService.populateMonthsListBySarsFileId(sarsFiles);
			levyDeviationReportDataModelInfo();
			reportGeneratedWithoutError = true;
		} catch (Exception e) {
			reportGeneratedWithoutError = false;
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void levyDeviationReportDataModelInfo() {
		levyDeviationReportDataModel = new LazyDataModel<SarsLevyStandardDeviationDataBean>() {
			private static final long serialVersionUID = 1L;
			private List<SarsLevyStandardDeviationDataBean> retorno = new ArrayList<SarsLevyStandardDeviationDataBean>();
			@Override
			public List<SarsLevyStandardDeviationDataBean> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = sarsLevyDetailsService.findStandardDeviationReportByMonthList(SarsLevyStandardDeviationDataBean.class, first, pageSize, sarsLevyStandardDeviationMonthList);
					levyDeviationReportDataModel.setRowCount(sarsLevyDetailsService.countAllCompanyStatements(sarsLevyStandardDeviationMonthList));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			// no ability to select it
			@Override
			public Object getRowKey(SarsLevyStandardDeviationDataBean obj) {
				return null;
			}

			// no ability to select it
			@SuppressWarnings("unlikely-arg-type")
			@Override
			public SarsLevyStandardDeviationDataBean getRowData(String rowKey) {
				return null;
			}
		};
	}

	public boolean isReportGeneratedWithoutError() {
		return reportGeneratedWithoutError;
	}

	public void setReportGeneratedWithoutError(boolean reportGeneratedWithoutError) {
		this.reportGeneratedWithoutError = reportGeneratedWithoutError;
	}

	public SarsFiles getSarsFiles() {
		return sarsFiles;
	}

	public void setSarsFiles(SarsFiles sarsFiles) {
		this.sarsFiles = sarsFiles;
	}

	public LazyDataModel<SarsFiles> getSarsDataModel() {
		return sarsDataModel;
	}

	public void setSarsDataModel(LazyDataModel<SarsFiles> sarsDataModel) {
		this.sarsDataModel = sarsDataModel;
	}

	public LazyDataModel<SarsLevyStandardDeviationDataBean> getLevyDeviationReportDataModel() {
		return levyDeviationReportDataModel;
	}

	public void setLevyDeviationReportDataModel(
			LazyDataModel<SarsLevyStandardDeviationDataBean> levyDeviationReportDataModel) {
		this.levyDeviationReportDataModel = levyDeviationReportDataModel;
	}

	public List<SarsLevyStandardDeviationMonthBean> getSarsLevyStandardDeviationMonthList() {
		return sarsLevyStandardDeviationMonthList;
	}

	public void setSarsLevyStandardDeviationMonthList(
			List<SarsLevyStandardDeviationMonthBean> sarsLevyStandardDeviationMonthList) {
		this.sarsLevyStandardDeviationMonthList = sarsLevyStandardDeviationMonthList;
	}

}
