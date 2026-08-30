package haj.com.ui;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.entity.LevyIncomeSummary;
import haj.com.entity.enums.ReportPropertiesEnum;
import haj.com.entity.lookup.ReportGenerationProperties;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.report.bean.ByChamberReportBean;
import haj.com.service.LevyIncomeSummaryService;
import haj.com.service.ReportService;
import haj.com.service.SarsFilesService;
import haj.com.service.lookup.ReportGenerationPropertiesService;

/**
 * The Class historicalgrantsandprojectsanalysisUI.
 */
@ManagedBean(name = "historicalgrantsandprojectsanalysisUI")
@ViewScoped
public class HistoricalGrantsAndProjectsAnalysisUI extends AbstractUI {

	/* Entity */
	private ReportGenerationProperties reportGenerationProperties;
	
	/* Service levels */
	private SarsFilesService sarsFilesService = new SarsFilesService();
	private ReportService reportService = new ReportService();
	private LevyIncomeSummaryService levyIncomSummaryService = new LevyIncomeSummaryService();
	private ReportGenerationPropertiesService reportGenerationPropertiesService = new ReportGenerationPropertiesService();
	
	/* Beans */
	private ByChamberReportBean grandTotalHistoricalGrants = new ByChamberReportBean();
	private ByChamberReportBean grandTotalMandatoryGrants = new ByChamberReportBean();
	
	/* Array Lists */
	private List<Integer> schemeYears = null;
	private List<ByChamberReportBean> historicalGrantsAdProjects;
	private List<ByChamberReportBean> mandatoryGrantsByChamber;
	private List<LevyIncomeSummary> displayLevyList;
	
	/* Vars */
	private Date createDate;
	private String grantsCategories = "";
	private String manData = "";
	private String disData = "";
	private String levyCategories = "";
	private String autoData = "";
	private String metalData = "";
	private String motorData = "";
	private String newtyreData = "";
	private String plasticData = "";
	private String unknownData = "";
	private Boolean displayGeneration = false; 

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
	 * Initialize method to get all historicalgrantsandprojectsanalysis and prepare
	 * a for a create of a new historicalgrantsandprojectsanalysis.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see historicalgrantsandprojectsanalysis
	 */
	private void runInit() throws Exception {
		
		// populate properties
		reportGenerationProperties = reportGenerationPropertiesService.findByReportProperty(ReportPropertiesEnum.Grant_Project_Expenses_Inception);
		if (reportGenerationProperties != null && reportGenerationProperties.getGenerationUnderway() != null && !reportGenerationProperties.getGenerationUnderway()) {
			displayGeneration = true;
		} else {
			displayGeneration = false;
		}
		
//		displayLevyList = levyIncomSummaryService.allLevyIncomeSummary();
		displayLevyList = levyIncomSummaryService.allLevyIncomeSummaryByType(ReportPropertiesEnum.Grant_Project_Expenses_Inception);
		if (!displayLevyList.isEmpty()) {
			createDate = displayLevyList.get(0).getCreateDate();
		}
		
		List<LevyIncomeSummary> temp = displayLevyList;
		temp.remove(temp.size()-1);

		int intCount = 0;
		for (LevyIncomeSummary levy : temp) {
			if (temp.size() - 1 == intCount) {
				levyCategories = levyCategories + "'" + levy.getDescription() + "'";
				autoData = autoData + levy.getAuto();
				metalData = metalData + levy.getMetal();
				motorData = motorData + levy.getMotor();
				newtyreData = newtyreData + levy.getNewTyre();
				plasticData = plasticData + levy.getPlastic();
				unknownData = unknownData + levy.getUnknown();
			}else {
				levyCategories = levyCategories + "'" + levy.getDescription() + "',";
				autoData = autoData + levy.getAuto() + ",";
				metalData = metalData + levy.getMetal() + ",";
				motorData = motorData + levy.getMotor() + ",";
				newtyreData = newtyreData + levy.getNewTyre() + ",";
				plasticData = plasticData + levy.getPlastic() + ",";
				unknownData = unknownData + levy.getUnknown() + ",";
				intCount++;
			}

		}

		populatehistoricalGrantsAdProjects();
	}

	public void populatehistoricalGrantsAdProjects() throws Exception {
		historicalGrantsAdProjects = reportService.findGrantsProjectsByYear();
		
		int count = 0;

		for (ByChamberReportBean grant : historicalGrantsAdProjects) {
			if (historicalGrantsAdProjects.size() - 1 == count) {
				grantsCategories = grantsCategories + "'" + grant.getDescription() + "'";
				manData = manData + grant.getMan();
				disData = disData + grant.getDis();
			}else {
				grantsCategories = grantsCategories + "'" + grant.getDescription() + "',";
				manData = manData + grant.getMan() + ",";
				disData = disData + grant.getDis() + ",";
				count++;
			}
		}

		if (!historicalGrantsAdProjects.isEmpty()) {
			grandTotalHistoricalGrants.setDescription("Total");
			for (ByChamberReportBean byChamberReportBean : historicalGrantsAdProjects) {

				if (grandTotalHistoricalGrants.getMan() == null) {
					grandTotalHistoricalGrants.setMan(byChamberReportBean.getMan());
					grandTotalHistoricalGrants.setDis(byChamberReportBean.getDis());
				}
				else {
					grandTotalHistoricalGrants.setMan(grandTotalHistoricalGrants.getMan() + byChamberReportBean.getMan());
					grandTotalHistoricalGrants.setDis(grandTotalHistoricalGrants.getDis() + byChamberReportBean.getDis());
				}
			}
			historicalGrantsAdProjects.add(grandTotalHistoricalGrants);
		}
	}

	public void populatemandatoryGrantsByChamberThread() throws Exception {

		try {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						//GenericUtility.sendMail(getSessionUI().getActiveUser().getEmail(), "Report Generation", "Please be advised: Generation for Report Grants And Project Expenses From Inception has started.",null);
						System.out.println("Threat Started");
						for (LevyIncomeSummary levyIncomeSummary : displayLevyList) {
							levyIncomSummaryService.delete(levyIncomeSummary);
						}
				 		mandatoryGrantsByChamber = reportService.findLevyIncomeByYear();
						if (!mandatoryGrantsByChamber.isEmpty()) {
							ByChamberReportBean grandTotal = new ByChamberReportBean();
							grandTotal.setDescription("Total");
							for (ByChamberReportBean byChamberReportBean : mandatoryGrantsByChamber) {
								if (grandTotal.getAuto() == null) {
									grandTotal.setAuto(byChamberReportBean.getAuto());
									grandTotal.setMetal(byChamberReportBean.getMetal());
									grandTotal.setMotor(byChamberReportBean.getMotor());
									grandTotal.setNewTyre(byChamberReportBean.getNewTyre());
									grandTotal.setPlastic(byChamberReportBean.getPlastic());
									grandTotal.setUnknown(byChamberReportBean.getUnknown());
								}
								else {
									grandTotal.setAuto(grandTotal.getAuto().add(byChamberReportBean.getAuto()));
									grandTotal.setMetal(grandTotal.getMetal().add(byChamberReportBean.getMetal()));
									grandTotal.setMotor(grandTotal.getMotor().add(byChamberReportBean.getMotor()));
									grandTotal.setNewTyre(grandTotal.getNewTyre().add(byChamberReportBean.getNewTyre()));
									grandTotal.setPlastic(grandTotal.getPlastic().add(byChamberReportBean.getPlastic()));
									grandTotal.setUnknown(grandTotal.getUnknown().add(byChamberReportBean.getUnknown()));
								}
							}
							mandatoryGrantsByChamber.add(grandTotal);
						}
						for (ByChamberReportBean byChamberReportBean : mandatoryGrantsByChamber) {
							LevyIncomeSummary levyIncome = new LevyIncomeSummary(byChamberReportBean.getDescription(), byChamberReportBean.getBdAuto(), byChamberReportBean.getBdMetal(),
									byChamberReportBean.getBdMotor(), byChamberReportBean.getBdNewTyre(), byChamberReportBean.getBdPlastic(), byChamberReportBean.getBdUnknown(), byChamberReportBean.getBdTotal());
							levyIncomSummaryService.create(levyIncome);
						}
						// GenericUtility.sendMail(getSessionUI().getActiveUser().getEmail(), "Report Generation", "Please be advised : Generation for report Grants And Project Expenses From Inception has completed.",null);
						System.out.println("Threat Ended");
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
				}
			}).start();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

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

	public List<ByChamberReportBean> getHistoricalGrantsAdProjects() {
		return historicalGrantsAdProjects;
	}

	public void setHistoricalGrantsAdProjects(List<ByChamberReportBean> historicalGrantsAdProjects) {
		this.historicalGrantsAdProjects = historicalGrantsAdProjects;
	}

	public List<ByChamberReportBean> getMandatoryGrantsByChamber() {
		return mandatoryGrantsByChamber;
	}

	public void setMandatoryGrantsByChamber(List<ByChamberReportBean> mandatoryGrantsByChamber) {
		this.mandatoryGrantsByChamber = mandatoryGrantsByChamber;
	}

	public ByChamberReportBean getGrandTotalHistoricalGrants() {
		return grandTotalHistoricalGrants;
	}

	public void setGrandTotalHistoricalGrants(ByChamberReportBean grandTotalHistoricalGrants) {
		this.grandTotalHistoricalGrants = grandTotalHistoricalGrants;
	}

	public ByChamberReportBean getGrandTotalMandatoryGrants() {
		return grandTotalMandatoryGrants;
	}

	public void setGrandTotalMandatoryGrants(ByChamberReportBean grandTotalMandatoryGrants) {
		this.grandTotalMandatoryGrants = grandTotalMandatoryGrants;
	}

	public List<Integer> getSchemeYears() {
		return schemeYears;
	}

	public void setSchemeYears(List<Integer> schemeYears) {
		this.schemeYears = schemeYears;
	}

	public LevyIncomeSummaryService getLevyIncomSummaryService() {
		return levyIncomSummaryService;
	}

	public void setLevyIncomSummaryService(LevyIncomeSummaryService levyIncomSummaryService) {
		this.levyIncomSummaryService = levyIncomSummaryService;
	}

	public List<LevyIncomeSummary> getDisplayLevyList() {
		return displayLevyList;
	}

	public void setDisplayLevyList(List<LevyIncomeSummary> displayLevyList) {
		this.displayLevyList = displayLevyList;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getGrantsCategories() {
		return grantsCategories;
	}

	public void setGrantsCategories(String grantsCategories) {
		this.grantsCategories = grantsCategories;
	}

	public String getManData() {
		return manData;
	}

	public void setManData(String manData) {
		this.manData = manData;
	}

	public String getDisData() {
		return disData;
	}

	public void setDisData(String disData) {
		this.disData = disData;
	}

	public String getLevyCategories() {
		return levyCategories;
	}

	public void setLevyCategories(String levyCategories) {
		this.levyCategories = levyCategories;
	}

	public String getAutoData() {
		return autoData;
	}

	public void setAutoData(String autoData) {
		this.autoData = autoData;
	}

	public String getMetalData() {
		return metalData;
	}

	public void setMetalData(String metalData) {
		this.metalData = metalData;
	}

	public String getMotorData() {
		return motorData;
	}

	public void setMotorData(String motorData) {
		this.motorData = motorData;
	}

	public String getNewtyreData() {
		return newtyreData;
	}

	public void setNewtyreData(String newtyreData) {
		this.newtyreData = newtyreData;
	}

	public String getPlasticData() {
		return plasticData;
	}

	public void setPlasticData(String plasticData) {
		this.plasticData = plasticData;
	}

	public String getUnknownData() {
		return unknownData;
	}

	public void setUnknownData(String unknownData) {
		this.unknownData = unknownData;
	}

	public Boolean getDisplayGeneration() {
		return displayGeneration;
	}

	public void setDisplayGeneration(Boolean displayGeneration) {
		this.displayGeneration = displayGeneration;
	}

	public ReportGenerationProperties getReportGenerationProperties() {
		return reportGenerationProperties;
	}

	public void setReportGenerationProperties(ReportGenerationProperties reportGenerationProperties) {
		this.reportGenerationProperties = reportGenerationProperties;
	}

}
