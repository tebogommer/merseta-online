package haj.com.ui;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.bean.ReportDataBean;
import haj.com.entity.Wsp;
import haj.com.entity.enums.EmployedUnEmployedEnum;
import haj.com.entity.enums.PivotNonPivotEnum;
import haj.com.entity.enums.WspReportEnum;
import haj.com.framework.AbstractUI;
import haj.com.service.ReportDataService;
import haj.com.service.WspService;

// TODO: Auto-generated Javadoc
/**
 * The Class ReportUI.
 */
@ManagedBean(name = "reportUI")
@ViewScoped
public class ReportUI extends AbstractUI {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The wsp repot. */
	private WspReportEnum wspRepot;

	/** The wsp. */
	private Wsp wsp;

	/** The report data service. */
	private ReportDataService reportDataService = new ReportDataService();
	
	/** The wsp service. */
	private WspService wspService = new WspService();

	/** The report wsp bean. */
	private ReportDataBean reportWspBean;
	
	/** The report pivotal plan DG bean. */
	private ReportDataBean reportPivotalPlanDGBean;
	
	/** The report atr bean. */
	private ReportDataBean reportAtrBean;
	
	/** The report pivotal training report bean. */
	private ReportDataBean reportPivotalTrainingReportBean;
	
	/** The report training with other seta bean. */
	private ReportDataBean reportTrainingWithOtherSetaBean;

	/** The Constant femaleId. */
	private static final Long femaleId = 2l;
	
	/** The Constant maleId. */
	private static final Long maleId = 1l;
	
	/** The Constant equityBlk. */
	private static final Long equityBlk = 1l;
	
	/** The Constant equityClr. */
	private static final Long equityClr = 2l;
	
	/** The Constant equityInd. */
	private static final Long equityInd = 3l;
	
	/** The Constant equityWht. */
	private static final Long equityWht = 6l;
	
	/** The Constant equityUnknown. */
	private static final Long equityUnknown = 5l;
	
	/** The Constant equityOther. */
	private static final Long equityOther = 6l;

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Run init.
	 *
	 * @throws Exception the exception
	 */
	private void runInit() throws Exception {
		wspRepot = WspReportEnum.WSP;
		this.wsp = getSessionUI().getWspSession();
		runReports();
	}

	/**
	 * Capture reports.
	 */
	public void captureReports() {
		try {
			wspService.update(this.wsp);

			// TODO capture reportbean
			switch (this.wspRepot.ordinal()) {
			case 0:// WSP
				break;
			case 1:// PIVITOLPLANDG
				break;
			case 2:// ATR
				break;
			case 3:// PIVITOLTRAININGREPORT
				break;
			case 4:// TRAININGDONEWITHOTHERETAS
				break;
			default:
				break;
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Run reports.
	 */
	public void runReports() {
		prepareWSPReport();
		preparePivotalPlanDGReport();
		preparewithOtherSetaReport();
		prepareATRReport();
		preparePivotalTrainingReport();
	}

	/**
	 * Preparewith other seta report.
	 */
	public void preparewithOtherSetaReport() {
		try {
			this.reportTrainingWithOtherSetaBean = new ReportDataBean();
			this.reportTrainingWithOtherSetaBean.setAges18To24(
					reportDataService.countByAgeRangeWspOtherSETA(17, 25, getSessionUI().getWspSession().getId()));
			this.reportTrainingWithOtherSetaBean.setAges25To34(
					reportDataService.countByAgeRangeWspOtherSETA(24, 35, getSessionUI().getWspSession().getId()));
			this.reportTrainingWithOtherSetaBean.setAges35To44(
					reportDataService.countByAgeRangeWspOtherSETA(34, 45, getSessionUI().getWspSession().getId()));
			this.reportTrainingWithOtherSetaBean.setAges45To54(
					reportDataService.countByAgeRangeWspOtherSETA(44, 55, getSessionUI().getWspSession().getId()));
			this.reportTrainingWithOtherSetaBean.setAges55To64(
					reportDataService.countByAgeRangeWspOtherSETA(54, 65, getSessionUI().getWspSession().getId()));
			this.reportTrainingWithOtherSetaBean.setAges65Up(
					reportDataService.countByAgeRangeWspOtherSETA(64, 150, getSessionUI().getWspSession().getId()));

			this.reportTrainingWithOtherSetaBean.setFemaleBlk(reportDataService
					.countByEquityGenderWspOtherSETA(equityBlk, femaleId, getSessionUI().getWspSession().getId()));
			this.reportTrainingWithOtherSetaBean.setFemaleClr(reportDataService
					.countByEquityGenderWspOtherSETA(equityClr, femaleId, getSessionUI().getWspSession().getId()));
			this.reportTrainingWithOtherSetaBean.setFemaleInd(reportDataService
					.countByEquityGenderWspOtherSETA(equityInd, femaleId, getSessionUI().getWspSession().getId()));
			this.reportTrainingWithOtherSetaBean.setFemaleWht(reportDataService
					.countByEquityGenderWspOtherSETA(equityWht, femaleId, getSessionUI().getWspSession().getId()));

			this.reportTrainingWithOtherSetaBean.setMaleBlk(reportDataService.countByEquityGenderWspOtherSETA(equityBlk,
					maleId, getSessionUI().getWspSession().getId()));
			this.reportTrainingWithOtherSetaBean.setMaleClr(reportDataService.countByEquityGenderWspOtherSETA(equityClr,
					maleId, getSessionUI().getWspSession().getId()));
			this.reportTrainingWithOtherSetaBean.setMaleInd(reportDataService.countByEquityGenderWspOtherSETA(equityInd,
					maleId, getSessionUI().getWspSession().getId()));
			this.reportTrainingWithOtherSetaBean.setMaleWht(reportDataService.countByEquityGenderWspOtherSETA(equityWht,
					maleId, getSessionUI().getWspSession().getId()));

			this.reportTrainingWithOtherSetaBean.setDisabledBlk(
					reportDataService.countByDisabledWSPOtherSETA(equityBlk, getSessionUI().getWspSession().getId()));
			this.reportTrainingWithOtherSetaBean.setDisabledClr(
					reportDataService.countByDisabledWSPOtherSETA(equityClr, getSessionUI().getWspSession().getId()));
			this.reportTrainingWithOtherSetaBean.setDisabledInd(
					reportDataService.countByDisabledWSPOtherSETA(equityInd, getSessionUI().getWspSession().getId()));
			this.reportTrainingWithOtherSetaBean.setDisabledWht(
					reportDataService.countByDisabledWSPOtherSETA(equityWht, getSessionUI().getWspSession().getId()));

			this.reportTrainingWithOtherSetaBean.setEmployed(reportDataService.countByEmployedUnemployedWSPOtherSETA(
					EmployedUnEmployedEnum.Employed, getSessionUI().getWspSession().getId()));
			this.reportTrainingWithOtherSetaBean.setUnemployed(reportDataService.countByEmployedUnemployedWSPOtherSETA(
					EmployedUnEmployedEnum.UnEmployed, getSessionUI().getWspSession().getId()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Prepare ATR report.
	 */
	public void prepareATRReport() {
		try {
			this.reportAtrBean = new ReportDataBean();
			this.reportAtrBean.setAges18To24(
					reportDataService.countByAgeRangeWsp(17, 25, getSessionUI().getWspSession().getId()));
			this.reportAtrBean.setAges25To34(
					reportDataService.countByAgeRangeWsp(24, 35, getSessionUI().getWspSession().getId()));
			this.reportAtrBean.setAges35To44(
					reportDataService.countByAgeRangeWsp(34, 45, getSessionUI().getWspSession().getId()));
			this.reportAtrBean.setAges45To54(
					reportDataService.countByAgeRangeWsp(44, 55, getSessionUI().getWspSession().getId()));
			this.reportAtrBean.setAges55To64(
					reportDataService.countByAgeRangeWsp(54, 65, getSessionUI().getWspSession().getId()));
			this.reportAtrBean
					.setAges65Up(reportDataService.countByAgeRangeWsp(64, 150, getSessionUI().getWspSession().getId()));

			this.reportAtrBean.setFemaleBlk(reportDataService.countByEquityGenderWsp(equityBlk, femaleId,
					getSessionUI().getWspSession().getId()));
			this.reportAtrBean.setFemaleClr(reportDataService.countByEquityGenderWsp(equityClr, femaleId,
					getSessionUI().getWspSession().getId()));
			this.reportAtrBean.setFemaleInd(reportDataService.countByEquityGenderWsp(equityInd, femaleId,
					getSessionUI().getWspSession().getId()));
			this.reportAtrBean.setFemaleWht(reportDataService.countByEquityGenderWsp(equityWht, femaleId,
					getSessionUI().getWspSession().getId()));

			this.reportAtrBean.setMaleBlk(reportDataService.countByEquityGenderWsp(equityBlk, maleId,
					getSessionUI().getWspSession().getId()));
			this.reportAtrBean.setMaleClr(reportDataService.countByEquityGenderWsp(equityClr, maleId,
					getSessionUI().getWspSession().getId()));
			this.reportAtrBean.setMaleInd(reportDataService.countByEquityGenderWsp(equityInd, maleId,
					getSessionUI().getWspSession().getId()));
			this.reportAtrBean.setMaleWht(reportDataService.countByEquityGenderWsp(equityWht, maleId,
					getSessionUI().getWspSession().getId()));

			this.reportAtrBean.setDisabledBlk(
					reportDataService.countByDisabledWSP(equityBlk, getSessionUI().getWspSession().getId()));
			this.reportAtrBean.setDisabledClr(
					reportDataService.countByDisabledWSP(equityClr, getSessionUI().getWspSession().getId()));
			this.reportAtrBean.setDisabledInd(
					reportDataService.countByDisabledWSP(equityInd, getSessionUI().getWspSession().getId()));
			this.reportAtrBean.setDisabledWht(
					reportDataService.countByDisabledWSP(equityWht, getSessionUI().getWspSession().getId()));

			this.reportAtrBean.setEmployed(reportDataService.countByEmployedUnemployedWSP(
					EmployedUnEmployedEnum.Employed, getSessionUI().getWspSession().getId()));
			this.reportAtrBean.setUnemployed(reportDataService.countByEmployedUnemployedWSP(
					EmployedUnEmployedEnum.UnEmployed, getSessionUI().getWspSession().getId()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Prepare pivotal training report.
	 */
	public void preparePivotalTrainingReport() {
		try {
			this.reportPivotalTrainingReportBean = new ReportDataBean();
			this.reportPivotalTrainingReportBean.setAges18To24(reportDataService.countByAgeRangeWspPivotNonPivot(17, 25,
					getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));
			this.reportPivotalTrainingReportBean.setAges25To34(reportDataService.countByAgeRangeWspPivotNonPivot(24, 35,
					getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));
			this.reportPivotalTrainingReportBean.setAges35To44(reportDataService.countByAgeRangeWspPivotNonPivot(34, 45,
					getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));
			this.reportPivotalTrainingReportBean.setAges45To54(reportDataService.countByAgeRangeWspPivotNonPivot(44, 55,
					getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));
			this.reportPivotalTrainingReportBean.setAges55To64(reportDataService.countByAgeRangeWspPivotNonPivot(54, 65,
					getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));
			this.reportPivotalTrainingReportBean.setAges65Up(reportDataService.countByAgeRangeWspPivotNonPivot(64, 150,
					getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));

			this.reportPivotalTrainingReportBean.setFemaleBlk(reportDataService.countByEquityGenderWspPivotNonPivot(
					equityBlk, femaleId, getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));
			this.reportPivotalTrainingReportBean.setFemaleClr(reportDataService.countByEquityGenderWspPivotNonPivot(
					equityClr, femaleId, getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));
			this.reportPivotalTrainingReportBean.setFemaleInd(reportDataService.countByEquityGenderWspPivotNonPivot(
					equityInd, femaleId, getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));
			this.reportPivotalTrainingReportBean.setFemaleWht(reportDataService.countByEquityGenderWspPivotNonPivot(
					equityWht, femaleId, getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));

			this.reportPivotalTrainingReportBean.setMaleBlk(reportDataService.countByEquityGenderWspPivotNonPivot(
					equityBlk, maleId, getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));
			this.reportPivotalTrainingReportBean.setMaleClr(reportDataService.countByEquityGenderWspPivotNonPivot(
					equityClr, maleId, getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));
			this.reportPivotalTrainingReportBean.setMaleInd(reportDataService.countByEquityGenderWspPivotNonPivot(
					equityInd, maleId, getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));
			this.reportPivotalTrainingReportBean.setMaleWht(reportDataService.countByEquityGenderWspPivotNonPivot(
					equityWht, maleId, getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));

			this.reportPivotalTrainingReportBean.setDisabledBlk(reportDataService.countByDisabledWSPPivotNonPivot(
					equityBlk, getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));
			this.reportPivotalTrainingReportBean.setDisabledClr(reportDataService.countByDisabledWSPPivotNonPivot(
					equityClr, getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));
			this.reportPivotalTrainingReportBean.setDisabledInd(reportDataService.countByDisabledWSPPivotNonPivot(
					equityInd, getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));
			this.reportPivotalTrainingReportBean.setDisabledWht(reportDataService.countByDisabledWSPPivotNonPivot(
					equityWht, getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));

			this.reportPivotalTrainingReportBean.setEmployed(
					reportDataService.countByEmployedUnemployedWSPPivotNonPivot(EmployedUnEmployedEnum.Employed,
							getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));
			this.reportPivotalTrainingReportBean.setUnemployed(
					reportDataService.countByEmployedUnemployedWSPPivotNonPivot(EmployedUnEmployedEnum.UnEmployed,
							getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Prepare pivotal plan DG report.
	 */
	public void preparePivotalPlanDGReport() {
		try {
			this.reportPivotalPlanDGBean = new ReportDataBean();
			this.reportPivotalPlanDGBean.setAges18To24(reportDataService.countByAgeRangeWspPivotNonPivot(17, 25,
					getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));
			this.reportPivotalPlanDGBean.setAges25To34(reportDataService.countByAgeRangeWspPivotNonPivot(24, 35,
					getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));
			this.reportPivotalPlanDGBean.setAges35To44(reportDataService.countByAgeRangeWspPivotNonPivot(34, 45,
					getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));
			this.reportPivotalPlanDGBean.setAges45To54(reportDataService.countByAgeRangeWspPivotNonPivot(44, 55,
					getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));
			this.reportPivotalPlanDGBean.setAges55To64(reportDataService.countByAgeRangeWspPivotNonPivot(54, 65,
					getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));
			this.reportPivotalPlanDGBean.setAges65Up(reportDataService.countByAgeRangeWspPivotNonPivot(64, 150,
					getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));

			this.reportPivotalPlanDGBean.setFemaleBlk(reportDataService.countByEquityGenderWspPivotNonPivot(equityBlk,
					femaleId, getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));
			this.reportPivotalPlanDGBean.setFemaleClr(reportDataService.countByEquityGenderWspPivotNonPivot(equityClr,
					femaleId, getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));
			this.reportPivotalPlanDGBean.setFemaleInd(reportDataService.countByEquityGenderWspPivotNonPivot(equityInd,
					femaleId, getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));
			this.reportPivotalPlanDGBean.setFemaleWht(reportDataService.countByEquityGenderWspPivotNonPivot(equityWht,
					femaleId, getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));

			this.reportPivotalPlanDGBean.setMaleBlk(reportDataService.countByEquityGenderWspPivotNonPivot(equityBlk,
					maleId, getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));
			this.reportPivotalPlanDGBean.setMaleClr(reportDataService.countByEquityGenderWspPivotNonPivot(equityClr,
					maleId, getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));
			this.reportPivotalPlanDGBean.setMaleInd(reportDataService.countByEquityGenderWspPivotNonPivot(equityInd,
					maleId, getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));
			this.reportPivotalPlanDGBean.setMaleWht(reportDataService.countByEquityGenderWspPivotNonPivot(equityWht,
					maleId, getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));

			this.reportPivotalPlanDGBean.setDisabledBlk(reportDataService.countByDisabledWSPPivotNonPivot(equityBlk,
					getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));
			this.reportPivotalPlanDGBean.setDisabledClr(reportDataService.countByDisabledWSPPivotNonPivot(equityClr,
					getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));
			this.reportPivotalPlanDGBean.setDisabledInd(reportDataService.countByDisabledWSPPivotNonPivot(equityInd,
					getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));
			this.reportPivotalPlanDGBean.setDisabledWht(reportDataService.countByDisabledWSPPivotNonPivot(equityWht,
					getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));

			this.reportPivotalPlanDGBean.setEmployed(
					reportDataService.countByEmployedUnemployedWSPPivotNonPivot(EmployedUnEmployedEnum.Employed,
							getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));
			this.reportPivotalPlanDGBean.setUnemployed(
					reportDataService.countByEmployedUnemployedWSPPivotNonPivot(EmployedUnEmployedEnum.UnEmployed,
							getSessionUI().getWspSession().getId(), PivotNonPivotEnum.Pivotal));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Prepare WSP report.
	 */
	public void prepareWSPReport() {
		try {
			this.reportWspBean = new ReportDataBean();
			this.reportWspBean.setAges18To24(
					reportDataService.countByAgeRangeWsp(17, 25, getSessionUI().getWspSession().getId()));
			this.reportWspBean.setAges25To34(
					reportDataService.countByAgeRangeWsp(24, 35, getSessionUI().getWspSession().getId()));
			this.reportWspBean.setAges35To44(
					reportDataService.countByAgeRangeWsp(34, 45, getSessionUI().getWspSession().getId()));
			this.reportWspBean.setAges45To54(
					reportDataService.countByAgeRangeWsp(44, 55, getSessionUI().getWspSession().getId()));
			this.reportWspBean.setAges55To64(
					reportDataService.countByAgeRangeWsp(54, 65, getSessionUI().getWspSession().getId()));
			this.reportWspBean
					.setAges65Up(reportDataService.countByAgeRangeWsp(64, 150, getSessionUI().getWspSession().getId()));

			this.reportWspBean.setFemaleBlk(reportDataService.countByEquityGenderWsp(equityBlk, femaleId,
					getSessionUI().getWspSession().getId()));
			this.reportWspBean.setFemaleClr(reportDataService.countByEquityGenderWsp(equityClr, femaleId,
					getSessionUI().getWspSession().getId()));
			this.reportWspBean.setFemaleInd(reportDataService.countByEquityGenderWsp(equityInd, femaleId,
					getSessionUI().getWspSession().getId()));
			this.reportWspBean.setFemaleWht(reportDataService.countByEquityGenderWsp(equityWht, femaleId,
					getSessionUI().getWspSession().getId()));

			this.reportWspBean.setMaleBlk(reportDataService.countByEquityGenderWsp(equityBlk, maleId,
					getSessionUI().getWspSession().getId()));
			this.reportWspBean.setMaleClr(reportDataService.countByEquityGenderWsp(equityClr, maleId,
					getSessionUI().getWspSession().getId()));
			this.reportWspBean.setMaleInd(reportDataService.countByEquityGenderWsp(equityInd, maleId,
					getSessionUI().getWspSession().getId()));
			this.reportWspBean.setMaleWht(reportDataService.countByEquityGenderWsp(equityWht, maleId,
					getSessionUI().getWspSession().getId()));

			this.reportWspBean.setDisabledBlk(
					reportDataService.countByDisabledWSP(equityBlk, getSessionUI().getWspSession().getId()));
			this.reportWspBean.setDisabledClr(
					reportDataService.countByDisabledWSP(equityClr, getSessionUI().getWspSession().getId()));
			this.reportWspBean.setDisabledInd(
					reportDataService.countByDisabledWSP(equityInd, getSessionUI().getWspSession().getId()));
			this.reportWspBean.setDisabledWht(
					reportDataService.countByDisabledWSP(equityWht, getSessionUI().getWspSession().getId()));

			this.reportWspBean.setEmployed(reportDataService.countByEmployedUnemployedWSP(
					EmployedUnEmployedEnum.Employed, getSessionUI().getWspSession().getId()));
			this.reportWspBean.setUnemployed(reportDataService.countByEmployedUnemployedWSP(
					EmployedUnEmployedEnum.UnEmployed, getSessionUI().getWspSession().getId()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Gets the wsp repot.
	 *
	 * @return the wsp repot
	 */
	public WspReportEnum getWspRepot() {
		return wspRepot;
	}

	/**
	 * Sets the wsp repot.
	 *
	 * @param wspRepot the new wsp repot
	 */
	public void setWspRepot(WspReportEnum wspRepot) {
		this.wspRepot = wspRepot;
	}

	/**
	 * Gets the report wsp bean.
	 *
	 * @return the report wsp bean
	 */
	public ReportDataBean getReportWspBean() {
		return reportWspBean;
	}

	/**
	 * Sets the report wsp bean.
	 *
	 * @param reportWspBean the new report wsp bean
	 */
	public void setReportWspBean(ReportDataBean reportWspBean) {
		this.reportWspBean = reportWspBean;
	}

	/**
	 * Gets the report pivotal plan DG bean.
	 *
	 * @return the report pivotal plan DG bean
	 */
	public ReportDataBean getReportPivotalPlanDGBean() {
		return reportPivotalPlanDGBean;
	}

	/**
	 * Sets the report pivotal plan DG bean.
	 *
	 * @param reportPivotalPlanDGBean the new report pivotal plan DG bean
	 */
	public void setReportPivotalPlanDGBean(ReportDataBean reportPivotalPlanDGBean) {
		this.reportPivotalPlanDGBean = reportPivotalPlanDGBean;
	}

	/**
	 * Gets the report atr bean.
	 *
	 * @return the report atr bean
	 */
	public ReportDataBean getReportAtrBean() {
		return reportAtrBean;
	}

	/**
	 * Sets the report atr bean.
	 *
	 * @param reportAtrBean the new report atr bean
	 */
	public void setReportAtrBean(ReportDataBean reportAtrBean) {
		this.reportAtrBean = reportAtrBean;
	}

	/**
	 * Gets the report pivotal training report bean.
	 *
	 * @return the report pivotal training report bean
	 */
	public ReportDataBean getReportPivotalTrainingReportBean() {
		return reportPivotalTrainingReportBean;
	}

	/**
	 * Sets the report pivotal training report bean.
	 *
	 * @param reportPivotalTrainingReportBean the new report pivotal training report bean
	 */
	public void setReportPivotalTrainingReportBean(ReportDataBean reportPivotalTrainingReportBean) {
		this.reportPivotalTrainingReportBean = reportPivotalTrainingReportBean;
	}

	/**
	 * Gets the report training with other seta bean.
	 *
	 * @return the report training with other seta bean
	 */
	public ReportDataBean getReportTrainingWithOtherSetaBean() {
		return reportTrainingWithOtherSetaBean;
	}

	/**
	 * Sets the report training with other seta bean.
	 *
	 * @param reportTrainingWithOtherSetaBean the new report training with other seta bean
	 */
	public void setReportTrainingWithOtherSetaBean(ReportDataBean reportTrainingWithOtherSetaBean) {
		this.reportTrainingWithOtherSetaBean = reportTrainingWithOtherSetaBean;
	}

	/**
	 * Gets the wsp.
	 *
	 * @return the wsp
	 */
	public Wsp getWsp() {
		return wsp;
	}

	/**
	 * Sets the wsp.
	 *
	 * @param wsp the new wsp
	 */
	public void setWsp(Wsp wsp) {
		this.wsp = wsp;
	}

}
