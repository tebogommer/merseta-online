package haj.com.ui;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.bean.SarsChamberProvinceReportBean;
import haj.com.bean.SarsProvinceCountBean;
import haj.com.constants.HAJConstants;
import haj.com.entity.lookup.Chamber;
/*import haj.com.entity.sizeofemplyeranalysis;*/
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.report.bean.ByChamberReportBean;
import haj.com.sars.SarsFiles;
import haj.com.service.ReportService;
import haj.com.service.SarsFilesService;
import haj.com.service.lookup.ChamberService;

/**
 * The Class sizeOfEmployerAnalysisByProvinceUI.
 */
@ManagedBean(name = "sizeOfEmployerAnalysisByProvinceUI")
@ViewScoped
public class SizeOfEmployerAnalysisByProvinceUI extends AbstractUI {

	/* Entity */
	private SarsFiles sarsFiles = null;
	private SarsFiles sarsFilesPreviousYear = null;
	
	/* Service */
	private ReportService reportService = new ReportService();
	private SarsFilesService sarsFilesService = new SarsFilesService();
	private ChamberService chamberService = new ChamberService();
	
	/* Array Lists */
	private List<ByChamberReportBean> employerSizeBean = new ArrayList<>();
	private List<Chamber> allChambers = new ArrayList<>();
	private List<SarsChamberProvinceReportBean> chamberProvinceReportList = new ArrayList<>();
	private List<SarsProvinceCountBean> provinceReportList = new ArrayList<>();
	private List<SarsProvinceCountBean> activeContributingEmployersReport = new ArrayList<>();
	
	/* Lazy data models */
	private LazyDataModel<SarsFiles> sarsFilesDataModel;
	
	/* Vars */
	private String forMonthStringFormat = "";
	
	/* Display booleans */
	private boolean displayReport = false;;
	private boolean displayEmployerSizeWithProvinceAssigned = false;
	private boolean displayEmployerProvince = false;
	private boolean displayActiveContributing = false;
	private boolean displayEmployerSize = false;

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
		clearDisplays();
		populateChamberList();
		sarsfilesInfo();
	}

	private void populateChamberList() throws Exception{
		if (chamberService == null) {
			chamberService = new ChamberService();
		}
		allChambers = chamberService.allChamber();
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
	
	public void clearDisplays(){
		displayReport = false;
		displayEmployerSizeWithProvinceAssigned = false;
		displayEmployerProvince = false;
		displayActiveContributing = false;
		displayEmployerSize = false;
		
		provinceReportList.clear();
		activeContributingEmployersReport.clear();
		chamberProvinceReportList.clear();
		employerSizeBean.clear();
		
		forMonthStringFormat = "";
	}
	
	public void generateEmployerSizeWithProvinceAssignedReport(){
		try {
			clearDisplays();
			populateEmployerSizeWithProvinceAssigned();
			displayEmployerSizeWithProvinceAssigned = true;
			reportGenInfo();
			addInfoMessage("Report Generated");
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			clearDisplays();
		}
	}
	
	public void generateEmployerProvince(){
		try {
			clearDisplays();
			populateEmployerProvinceReport();
			displayEmployerProvince = true;
			reportGenInfo();
			addInfoMessage("Report Generated");
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			clearDisplays();
		}
	}
	
	public void generateActiveContributingByProvince(){
		try {
			clearDisplays();
			populateActiveContributingByProvince();
			displayActiveContributing = true;
			reportGenInfo();
			addInfoMessage("Report Generated");
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			clearDisplays();
		}
	}
	
	public void generateEmployerSizeWithProvinceAssignedByChamber(){
		try {
			clearDisplays();
			populateEmployerSizeWithProvinceAssignedByChamber();
			displayEmployerSize = true;
			reportGenInfo();
			addInfoMessage("Report Generated");
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			clearDisplays();
		}
	}

	public void reportGenInfo() {
		forMonthStringFormat = HAJConstants.sddfMMMMyyyy.format(sarsFiles.getForMonth());
		displayReport = true;
	}
	
	private void populateActiveContributingByProvince() throws Exception{
		activeContributingEmployersReport = reportService.generateActiveContributingEmployersCountBySarsFileGroupedByProvince(sarsFiles.getId());
	}

	private void populateEmployerProvinceReport()  throws Exception{
		provinceReportList = reportService.generateEmployerAnayalisisByProvinceAndSarsFileId(sarsFiles.getId());
	}

	private void populateEmployerSizeWithProvinceAssignedByChamber() throws Exception {
		populateChamberList();
		chamberProvinceReportList = reportService.generateEmployerAnayalisisByChamberProvinceAndSarsFileId(allChambers, sarsFiles.getId());
	}

	public void populateEmployerSizeWithProvinceAssigned() throws Exception {
		employerSizeBean = reportService.reportDataContributingEmployerAnalysisBySizeAndSisCodeWithProvinceAssigned(sarsFiles.getId());
		forMonthStringFormat = HAJConstants.sddfMMMMyyyy.format(sarsFiles.getForMonth());
		
		ByChamberReportBean grandTotal = new ByChamberReportBean();
		grandTotal.setDescription("Total");
		grandTotal.setAuto(BigInteger.ZERO);
		grandTotal.setMetal(BigInteger.ZERO);
		grandTotal.setMotor(BigInteger.ZERO);
		grandTotal.setNewTyre(BigInteger.ZERO);
		grandTotal.setPlastic(BigInteger.ZERO);
		grandTotal.setUnknown(BigInteger.ZERO);
		grandTotal.setAcm(BigInteger.ZERO);
		
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
				grandTotal.setAcm(grandTotal.getUnknown().add(byChamberReportBean.getAcm()));
			}
		}
		employerSizeBean.add(grandTotal);
	}
	
	public void populateBeanOldMethod() throws Exception {
		try {
			if (sarsFiles != null && sarsFiles.getId() != null) {
				
				// populate employer size with province assigned
				populateEmployerSizeWithProvinceAssigned();
				
				// populate employer province report
				populateEmployerProvinceReport();

				// populate active / contributing
				populateActiveContributingByProvince();
				
				// populate employer size with province assigned by chamber
				populateEmployerSizeWithProvinceAssignedByChamber();
				
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

	public List<SarsChamberProvinceReportBean> getChamberProvinceReportList() {
		return chamberProvinceReportList;
	}

	public void setChamberProvinceReportList(List<SarsChamberProvinceReportBean> chamberProvinceReportList) {
		this.chamberProvinceReportList = chamberProvinceReportList;
	}

	public List<SarsProvinceCountBean> getProvinceReportList() {
		return provinceReportList;
	}

	public void setProvinceReportList(List<SarsProvinceCountBean> provinceReportList) {
		this.provinceReportList = provinceReportList;
	}

	public SarsFiles getSarsFilesPreviousYear() {
		return sarsFilesPreviousYear;
	}

	public void setSarsFilesPreviousYear(SarsFiles sarsFilesPreviousYear) {
		this.sarsFilesPreviousYear = sarsFilesPreviousYear;
	}

	public List<SarsProvinceCountBean> getActiveContributingEmployersReport() {
		return activeContributingEmployersReport;
	}

	public void setActiveContributingEmployersReport(List<SarsProvinceCountBean> activeContributingEmployersReport) {
		this.activeContributingEmployersReport = activeContributingEmployersReport;
	}

	public boolean isDisplayEmployerSizeWithProvinceAssigned() {
		return displayEmployerSizeWithProvinceAssigned;
	}

	public void setDisplayEmployerSizeWithProvinceAssigned(boolean displayEmployerSizeWithProvinceAssigned) {
		this.displayEmployerSizeWithProvinceAssigned = displayEmployerSizeWithProvinceAssigned;
	}

	public boolean isDisplayEmployerProvince() {
		return displayEmployerProvince;
	}

	public void setDisplayEmployerProvince(boolean displayEmployerProvince) {
		this.displayEmployerProvince = displayEmployerProvince;
	}

	public boolean isDisplayActiveContributing() {
		return displayActiveContributing;
	}

	public void setDisplayActiveContributing(boolean displayActiveContributing) {
		this.displayActiveContributing = displayActiveContributing;
	}

	public boolean isDisplayEmployerSize() {
		return displayEmployerSize;
	}

	public void setDisplayEmployerSize(boolean displayEmployerSize) {
		this.displayEmployerSize = displayEmployerSize;
	}

	public boolean isDisplayReport() {
		return displayReport;
	}

	public void setDisplayReport(boolean displayReport) {
		this.displayReport = displayReport;
	}

}
