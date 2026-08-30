package haj.com.ui;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.charts.bar.BarChartModel;

import haj.com.bean.ReconSchemeYears;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.report.bean.ByChamberReportBean;
import haj.com.sars.SarsFiles;
import haj.com.service.ReportService;
import haj.com.service.SarsFilesService;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class sizeofemployercontributionanalysisUI.
 */
@ManagedBean(name = "sizeOfEmployerContributionAnalysisUI")
@ViewScoped
public class SizeOfEmployerContributionAnalysisUI extends AbstractUI {

	private List<ReconSchemeYears> sarsYears;
	private SarsFilesService sarsFilesService = new SarsFilesService();

	private ReportService reportService = new ReportService();
	private SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
	private ByChamberReportBean sizeofemployercontributionanalysis;
	private ByChamberReportBean sizeofemployercontributionanalysisPre;
	
	private LazyDataModel<SarsFiles> sarsFilesDataModel;
	private SarsFiles sarsFiles = null;
	private BigInteger sarsFilesPre = null;
	
	private String series = "";

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		try {
			runInit();
			sarsfilesInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Initialize method to get all sizeofemployercontributionanalysis and prepare a
	 * for a create of a new sizeofemployercontributionanalysis.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see sizeofemployercontributionanalysis
	 */
	private void runInit() throws Exception {
		sarsYears = new ArrayList<>();
		this.setSarsYears(sarsFilesService.schemeYears());
	}

	public void populateBean(){
		try {
			sizeofemployercontributionanalysis = reportService.findSizeOfEmpoloyerContribution(sarsFiles.getId());
			sarsFilesPre = reportService.findPreSarsFile(sarsFiles.getId());
			sizeofemployercontributionanalysisPre = reportService.findSizeOfEmpoloyerContribution(sarsFilesPre.longValue());
			if (sizeofemployercontributionanalysis == null ) {
				
				sizeofemployercontributionanalysis = new ByChamberReportBean()  ;
				sizeofemployercontributionanalysis.setSmallCount(new BigInteger("0"));
				sizeofemployercontributionanalysis.setMidCount(new BigInteger("0"));
				sizeofemployercontributionanalysis.setLargeCount(new BigInteger("0"));
			}
			if(sizeofemployercontributionanalysisPre == null) {
							
				sizeofemployercontributionanalysisPre = new ByChamberReportBean();
				sizeofemployercontributionanalysisPre.setSmallCount(new BigInteger("0"));
				sizeofemployercontributionanalysisPre.setMidCount(new BigInteger("0"));
				sizeofemployercontributionanalysisPre.setLargeCount(new BigInteger("0"));
			}
			
			series = "[{ name: '" + sdf.format(sarsFiles.getForMonth()) + "', data: [ " + sizeofemployercontributionanalysis.getSmallCount() 
							+ "," + sizeofemployercontributionanalysis.getMidCount() + "," + sizeofemployercontributionanalysis.getLargeCount() 
							+ "]}, {name: '" + sdf.format(GenericUtility.deductYearsfromDate(sarsFiles.getForMonth(), 1)) + "', data: [ " + sizeofemployercontributionanalysisPre.getSmallCount() 
							+ "," + sizeofemployercontributionanalysisPre.getMidCount() + "," + sizeofemployercontributionanalysisPre.getLargeCount() + "]}]";
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
		}
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

		};}

	public List<ReconSchemeYears> getSarsYears() {
		return sarsYears;
	}

	public void setSarsYears(List<ReconSchemeYears> sarsYears) {
		this.sarsYears = sarsYears;
	}

	public SarsFilesService getSarsFilesService() {
		return sarsFilesService;
	}

	public void setSarsFilesService(SarsFilesService sarsFilesService) {
		this.sarsFilesService = sarsFilesService;
	}

	public ReportService getReportService() {
		return reportService;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public SimpleDateFormat getSdf() {
		return sdf;
	}

	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}

	public ByChamberReportBean getSizeofemployercontributionanalysisPre() {
		return sizeofemployercontributionanalysisPre;
	}

	public void setSizeofemployercontributionanalysisPre(ByChamberReportBean sizeofemployercontributionanalysisPre) {
		this.sizeofemployercontributionanalysisPre = sizeofemployercontributionanalysisPre;
	}

	public void setSizeofemployercontributionanalysis(ByChamberReportBean sizeofemployercontributionanalysis) {
		this.sizeofemployercontributionanalysis = sizeofemployercontributionanalysis;
	}

	public ByChamberReportBean getSizeofemployercontributionanalysis() {
		return sizeofemployercontributionanalysis;
	}

	public LazyDataModel<SarsFiles> getSarsFilesDataModel() {
		return sarsFilesDataModel;
	}

	public void setSarsFilesDataModel(LazyDataModel<SarsFiles> sarsFilesDataModel) {
		this.sarsFilesDataModel = sarsFilesDataModel;
	}

	public SarsFiles getSarsFiles() {
		return sarsFiles;
	}

	public void setSarsFiles(SarsFiles sarsFiles) {
		this.sarsFiles = sarsFiles;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

}