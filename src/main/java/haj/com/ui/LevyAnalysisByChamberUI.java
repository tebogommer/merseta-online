package haj.com.ui;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.bean.ReconSchemeYears;
import haj.com.bean.SchemeYearIdentifierBean;
import haj.com.constants.HAJConstants;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.report.bean.ByChamberReportBean;
import haj.com.sars.SarsFiles;
import haj.com.service.ReportService;
import haj.com.service.SarsFilesService;

@ManagedBean(name = "levyanalysisbychamberUI")
@ViewScoped
public class LevyAnalysisByChamberUI extends AbstractUI {

	/* Entity */
	private SarsFiles sarsFiles = null;
	private SchemeYearIdentifierBean schemeYearInfo;
	
	/* Beans */
	private ByChamberReportBean percentageBean1 = new ByChamberReportBean();
	private ByChamberReportBean percentageBean2 = new ByChamberReportBean();
	
	/* Service Level */
	private ReportService reportService = new ReportService();
	private SarsFilesService sarsFilesService = new SarsFilesService();
	
	/* Lazy Data Models */
	private LazyDataModel<SarsFiles> sarsFilesDataModel;
	
	/* Array Lists */
	private List<ByChamberReportBean> levyStatusBean;
	private List<ByChamberReportBean> levyData1Bean;
	private List<ByChamberReportBean> levyData2Bean;
	private List<ReconSchemeYears> sarsYears;
	
	/* Vars */
	private Date dateGenerated = null;
	private String data;
	private String forMonthToString = "";
	private boolean showStatus = true;
	private boolean showData1 = true;
	private boolean showData2 = true;

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
		sarsfilesInfo();
		sarsYears = sarsFilesService.schemeYears();
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
	
	public void populateBean() {
		try {
			dateGenerated = new Date();
			schemeYearInfo = reportService.findSchemeYearInfo(sarsFiles.getForMonth());
			forMonthToString = HAJConstants.sddfMMMMyyyy.format(sarsFiles.getForMonth());
			populateStatusBean();
			populateData1Bean();
			populateData2Bean();
			addInfoMessage("Reports Generated For " + forMonthToString);
//			populateStatusBeanThread();
//			populateData1BeanThread();
//			populateData2BeanThread();
			runClientSideUpdate("bean2PanelId");
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
		}
	}

	private void populateStatusBeanThread() throws Exception {
		try {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						populateStatusBean();
					} catch (Exception e) {
						addErrorMessage(e.getMessage(), e);
					}
				}
			}).start();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void populateStatusBean() throws Exception {
		showStatus = false;
		levyStatusBean = reportService.findleviesBetweenDates(sarsFiles.getId());
		if (!levyStatusBean.isEmpty()) {
			ByChamberReportBean grandTotal = new ByChamberReportBean();
			grandTotal.setDescription("Total");
			for (ByChamberReportBean byChamberReportBean : levyStatusBean) {
				if (grandTotal.getAuto() == null) {
					grandTotal.setAuto(byChamberReportBean.getAuto());
					grandTotal.setMetal(byChamberReportBean.getMetal());
					grandTotal.setMotor(byChamberReportBean.getMotor());
					grandTotal.setNewTyre(byChamberReportBean.getNewTyre());
					grandTotal.setPlastic(byChamberReportBean.getPlastic());
					grandTotal.setUnknown(byChamberReportBean.getUnknown());
					grandTotal.setAcm(byChamberReportBean.getAcm());
				}
				else {
					grandTotal.setAuto(grandTotal.getAuto().add(byChamberReportBean.getAuto()));
					grandTotal.setMetal(grandTotal.getMetal().add(byChamberReportBean.getMetal()));
					grandTotal.setMotor(grandTotal.getMotor().add(byChamberReportBean.getMotor()));
					grandTotal.setNewTyre(grandTotal.getNewTyre().add(byChamberReportBean.getNewTyre()));
					grandTotal.setPlastic(grandTotal.getPlastic().add(byChamberReportBean.getPlastic()));
					grandTotal.setUnknown(grandTotal.getUnknown().add(byChamberReportBean.getUnknown()));
					grandTotal.setAcm(grandTotal.getAcm().add(byChamberReportBean.getAcm()));
				}
			}
			levyStatusBean.add(grandTotal);
		}
		showStatus = true;
	}

	private void populateData1BeanThread() throws Exception {
		try {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						populateData1Bean();
					} catch (Exception e) {
						addErrorMessage(e.getMessage(), e);
					}
				}
			}).start();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void populateData1Bean() throws Exception {
		showData1 = false;
		levyData1Bean = reportService.findContributingEmployersBetweenDates(sarsFiles);
		levyData1Bean.add(reportService.findLevyContributionsBetweenDates(schemeYearInfo.getStartOfYear(), sarsFiles.getForMonth()));
		if (levyData1Bean.size() > 1) {
			percentageBean1.setDescription("Chamber Percentage");
			percentageBean1.setBdAuto(levyData1Bean.get(1).getBdAuto().divide(levyData1Bean.get(1).getBdTotal(), 2, RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(100l)));
			percentageBean1.setBdMetal(levyData1Bean.get(1).getBdMetal().divide(levyData1Bean.get(1).getBdTotal(), 2, RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(100l)));
			percentageBean1.setBdMotor(levyData1Bean.get(1).getBdMotor().divide(levyData1Bean.get(1).getBdTotal(), 2, RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(100l)));
			percentageBean1.setBdNewTyre(levyData1Bean.get(1).getBdNewTyre().divide(levyData1Bean.get(1).getBdTotal(), 2, RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(100l)));
			percentageBean1.setBdPlastic(levyData1Bean.get(1).getBdPlastic().divide(levyData1Bean.get(1).getBdTotal(), 2, RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(100l)));
			percentageBean1.setBdUnknown(levyData1Bean.get(1).getBdUnknown().divide(levyData1Bean.get(1).getBdTotal(), 2, RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(100l)));
			percentageBean1.setBdAcm(levyData1Bean.get(1).getBdAcm().divide(levyData1Bean.get(1).getBdTotal(), 2, RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(100l)));
			percentageBean1.setBdTotal(levyData1Bean.get(1).getBdTotal().divide(levyData1Bean.get(1).getBdTotal(), 2, RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(100l)));
			levyData1Bean.add(percentageBean1);
		}
		showData1 = true;
	}

	private void populateData2BeanThread() throws Exception {
		try {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						populateData2Bean();
					} catch (Exception e) {
						addErrorMessage(e.getMessage(), e);
					}
				}
			}).start();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void populateData2Bean() throws Exception {
		showData2 = false;
		levyData2Bean = reportService.findActiveEmployersBetweenDate(sarsFiles);
		levyData2Bean.addAll(reportService.findContributingEmployersBetweenDates(sarsFiles));
		levyData2Bean.add(reportService.findLevyContributionsBySarsMonth(sarsFiles));
		if (levyData2Bean.size() > 2) {

			percentageBean2.setDescription("Chamber Percentage");

			percentageBean2.setBdAuto(levyData2Bean.get(2).getBdAuto().divide(levyData2Bean.get(2).getBdTotal(), 2, RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(100l)));
			percentageBean2.setBdMetal(levyData2Bean.get(2).getBdMetal().divide(levyData2Bean.get(2).getBdTotal(), 2, RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(100l)));
			percentageBean2.setBdMotor(levyData2Bean.get(2).getBdMotor().divide(levyData2Bean.get(2).getBdTotal(), 2, RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(100l)));
			percentageBean2.setBdNewTyre(levyData2Bean.get(2).getBdNewTyre().divide(levyData2Bean.get(2).getBdTotal(), 2, RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(100l)));
			percentageBean2.setBdPlastic(levyData2Bean.get(2).getBdPlastic().divide(levyData2Bean.get(2).getBdTotal(), 2, RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(100l)));
			percentageBean2.setBdUnknown(levyData2Bean.get(2).getBdUnknown().divide(levyData2Bean.get(2).getBdTotal(), 2, RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(100l)));
			percentageBean2.setBdAcm(levyData2Bean.get(2).getBdAcm().divide(levyData2Bean.get(2).getBdTotal(), 2, RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(100l)));
			percentageBean2.setBdTotal(levyData2Bean.get(2).getBdTotal().divide(levyData2Bean.get(2).getBdTotal(), 2, RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(100l)));

			levyData2Bean.add(percentageBean2);
		}
		
		data = "[{ name: ' " + levyData2Bean.get(0).getDescription() + "', data: [" + levyData2Bean.get(0).getAuto() + "," + levyData2Bean.get(0).getMetal() + "," + levyData2Bean.get(0).getMotor()
				+ "," + levyData2Bean.get(0).getNewTyre() + "," + levyData2Bean.get(0).getPlastic() + "," + levyData2Bean.get(0).getUnknown() + "]}, {name: '" + levyData2Bean.get(1).getDescription()
				+ "', data: [ " + levyData2Bean.get(1).getAuto() + "," + levyData2Bean.get(1).getMetal() + "," + levyData2Bean.get(1).getMotor() + "," + levyData2Bean.get(1).getNewTyre() + ","
				+ levyData2Bean.get(1).getPlastic() + "," + levyData2Bean.get(1).getUnknown() + "," + levyData2Bean.get(1).getAcm() + "]}]";
		
		showData2 = true;
	}
	
	public List<ByChamberReportBean> getLevyStatusBean() {
		return levyStatusBean;
	}

	public void setLevyStatusBean(List<ByChamberReportBean> levyStatusBean) {
		this.levyStatusBean = levyStatusBean;
	}

	public List<ByChamberReportBean> getLevyData1Bean() {
		return levyData1Bean;
	}

	public void setLevyData1Bean(List<ByChamberReportBean> levyData1Bean) {
		this.levyData1Bean = levyData1Bean;
	}

	public List<ByChamberReportBean> getLevyData2Bean() {
		return levyData2Bean;
	}

	public void setLevyData2Bean(List<ByChamberReportBean> levyData2Bean) {
		this.levyData2Bean = levyData2Bean;
	}

	public ByChamberReportBean getPercentageBean1() {
		return percentageBean1;
	}

	public void setPercentageBean1(ByChamberReportBean percentageBean1) {
		this.percentageBean1 = percentageBean1;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public boolean isShowStatus() {
		return showStatus;
	}

	public void setShowStatus(boolean showStatus) {
		this.showStatus = showStatus;
	}

	public boolean isShowData1() {
		return showData1;
	}

	public void setShowData1(boolean showData1) {
		this.showData1 = showData1;
	}

	public boolean isShowData2() {
		return showData2;
	}

	public void setShowData2(boolean showData2) {
		this.showData2 = showData2;
	}

	public LazyDataModel<SarsFiles> getSarsFilesDataModel() {
		return sarsFilesDataModel;
	}

	public void setSarsFilesDataModel(LazyDataModel<SarsFiles> sarsFilesDataModel) {
		this.sarsFilesDataModel = sarsFilesDataModel;
	}

	public List<ReconSchemeYears> getSarsYears() {
		return sarsYears;
	}

	public SarsFiles getSarsFiles() {
		return sarsFiles;
	}

	public void setSarsFiles(SarsFiles sarsFiles) {
		this.sarsFiles = sarsFiles;
	}

	public SchemeYearIdentifierBean getSchemeYearInfo() {
		return schemeYearInfo;
	}

	public void setSchemeYearInfo(SchemeYearIdentifierBean schemeYearInfo) {
		this.schemeYearInfo = schemeYearInfo;
	}

	public void setSarsYears(List<ReconSchemeYears> sarsYears) {
		this.sarsYears = sarsYears;
	}

	public Date getDateGenerated() {
		return dateGenerated;
	}

	public void setDateGenerated(Date dateGenerated) {
		this.dateGenerated = dateGenerated;
	}

	public String getForMonthToString() {
		return forMonthToString;
	}

	public void setForMonthToString(String forMonthToString) {
		this.forMonthToString = forMonthToString;
	}
	
}