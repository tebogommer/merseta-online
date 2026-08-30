package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
/*import haj.com.entity.sizeofemplyeranalysis;*/
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.report.bean.ByChamberReportBean;
import haj.com.sars.SarsFiles;
import haj.com.service.ReportService;
import haj.com.service.SarsFilesService;

// TODO: Auto-generated Javadoc
/**
 * The Class sizeofemplyeranalysisUI.
 */
@ManagedBean(name = "sizeofemplyeranalysisUI")
@ViewScoped
public class SizeOfEmployerAnalysisUI extends AbstractUI {

	private ReportService reportService = new ReportService();
	private SarsFilesService sarsFilesService = new SarsFilesService();
	private List<ByChamberReportBean> employerSizeBean;

	/* New Sars Field */
	private SarsFiles sarsFiles = null;
	/* The data model. */
	private LazyDataModel<SarsFiles> sarsFilesDataModel;
	
	private String forMonthStringFormat = "";

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

	/**
	 * Initialize method to get all sizeofemplyeranalysis and prepare a for a
	 * create of a new sizeofemplyeranalysis.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see sizeofemplyeranalysis
	 */
	private void runInit() throws Exception {
		sarsfilesInfo();
	}

	public void sarsfilesInfo() {
		sarsFilesDataModel = new LazyDataModel<SarsFiles>() {
			private static final long serialVersionUID = 1L;
			private List<SarsFiles> retorno = new ArrayList<>();

			@Override
			public List<SarsFiles> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					if (sortField == null) {
						sortField = "forMonth";
						sortOrder = sortOrder.DESCENDING;
					}
					retorno = sarsFilesService.allSarsFiles(SarsFiles.class, first, pageSize, sortField, sortOrder, filters);
					sarsFilesDataModel.setRowCount(sarsFilesService.count(SarsFiles.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
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

	public void populateBean() throws Exception {
		try {
			if (sarsFiles != null && sarsFiles.getId() != null) {
				// version one
//				employerSizeBean = reportService.reportDataEmployerAnalysisBySizeAndSisCode(sarsFiles.getId());
				// version two
				employerSizeBean = reportService.reportDataContributingEmployerAnalysisBySizeAndSisCode(sarsFiles.getId());
				forMonthStringFormat = HAJConstants.sddfMMMMyyyy.format(sarsFiles.getForMonth());
				
				ByChamberReportBean grandTotal = new ByChamberReportBean();
				grandTotal.setDescription("Total");
				for (ByChamberReportBean byChamberReportBean : employerSizeBean) {
					if (grandTotal.getAuto() == null) {
						grandTotal.setAuto(byChamberReportBean.getAuto());
						grandTotal.setMetal(byChamberReportBean.getMetal());
						grandTotal.setMotor(byChamberReportBean.getMotor());
						grandTotal.setNewTyre(byChamberReportBean.getNewTyre());
						grandTotal.setPlastic(byChamberReportBean.getPlastic());
						grandTotal.setUnknown(byChamberReportBean.getUnknown());
						grandTotal.setAcm(byChamberReportBean.getAcm());
					} else {
						grandTotal.setAuto(grandTotal.getAuto().add(byChamberReportBean.getAuto()));
						grandTotal.setMetal(grandTotal.getMetal().add(byChamberReportBean.getMetal()));
						grandTotal.setMotor(grandTotal.getMotor().add(byChamberReportBean.getMotor()));
						grandTotal.setNewTyre(grandTotal.getNewTyre().add(byChamberReportBean.getNewTyre()));
						grandTotal.setPlastic(grandTotal.getPlastic().add(byChamberReportBean.getPlastic()));
						grandTotal.setUnknown(grandTotal.getUnknown().add(byChamberReportBean.getUnknown()));
						grandTotal.setAcm(grandTotal.getAcm().add(byChamberReportBean.getAcm()));
					}
				}
				employerSizeBean.add(grandTotal);
				addInfoMessage("Report Generated");
			} else {
				addErrorMessage("Select File Before Proceeding");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public List<ByChamberReportBean> getEmployerSizeBean() {
		return employerSizeBean;
	}

	public void setEmployerSizeBean(List<ByChamberReportBean> employerSizeBean) {
		this.employerSizeBean = employerSizeBean;
	}

	public SarsFiles getSarsFiles() {
		return sarsFiles;
	}

	public void setSarsFiles(SarsFiles sarsFiles) {
		this.sarsFiles = sarsFiles;
	}

	public LazyDataModel<SarsFiles> getSarsFilesDataModel() {
		return sarsFilesDataModel;
	}

	public void setSarsFilesDataModel(LazyDataModel<SarsFiles> sarsFilesDataModel) {
		this.sarsFilesDataModel = sarsFilesDataModel;
	}

	public String getForMonthStringFormat() {
		return forMonthStringFormat;
	}

	public void setForMonthStringFormat(String forMonthStringFormat) {
		this.forMonthStringFormat = forMonthStringFormat;
	}

}
