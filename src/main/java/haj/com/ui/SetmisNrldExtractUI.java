package  haj.com.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.bean.HeaderBean;
import haj.com.dataextract.bean.NLRDFile21BeanProviderVersionTwo;
import haj.com.dataextract.bean.NLRDFile24BeanProviderAccreditationVersionTwo;
import haj.com.dataextract.bean.NLRDFile25BeanPersonInformationVersionTwo;
import haj.com.dataextract.bean.NLRDFile26BeanPersonDesignationVersionTwo;
import haj.com.dataextract.bean.NLRDFile27BeanNQFDesignationRegistrationVersionTwo;
import haj.com.dataextract.bean.NLRDFile28BeanLearnershipEnrolmentAchievementVersionTwo;
import haj.com.dataextract.bean.NLRDFile29BeanQualificationEnrolmentAchievementVersionTwo;
import haj.com.dataextract.bean.NLRDFile30BeanUnitStandardEnrolmentAchievementVersionTwo;
import haj.com.dataextract.bean.SETMISFile100BeanVersionTwo;
import haj.com.dataextract.bean.SETMISFile200BeanVersionTwo;
import haj.com.dataextract.bean.SETMISFile304Bean;
import haj.com.dataextract.bean.SETMISFile400BeanVersionTwo;
import haj.com.dataextract.bean.SETMISFile401Bean;
import haj.com.dataextract.bean.SETMISFile500Bean;
import haj.com.dataextract.bean.SETMISFile501Bean;
import haj.com.dataextract.bean.SETMISFile502Bean;
import haj.com.dataextract.bean.SETMISFile503BeanVersionTwo;
import haj.com.dataextract.bean.SETMISFile505Bean;
import haj.com.dataextract.bean.SETMISFile506Bean;
import haj.com.entity.Blank;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.JasperService;
import haj.com.service.NRLDService;
import haj.com.service.SETMISService;
import haj.com.service.setmis.SETMISFile100Service;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "setmisNrldExtractUI")
@ViewScoped
public class SetmisNrldExtractUI extends AbstractUI {
	
	/* Service Levels */
	private SETMISService        setmisService        = new SETMISService();
	private SETMISFile100Service setmisFile100Service = new SETMISFile100Service();
	private NRLDService          nrldService          = new NRLDService();
	
	/* Vars */
	private boolean canExtract = false;
	private String startOperationHours = "08:30:00";
	private String endOperationHours = "16:00:00";
	private String           currentDate;
	private String           currentDateNrld;
	private HeaderBean       headerBean;
	private List<HeaderBean> headerBeanList;

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
	 * Initialize method to get all Blank and prepare a for a create of a new Blank.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    Blank
	 */
	private void runInit() throws Exception {
		if ((getSessionUI().isAdmin()) || (getSessionUI().getActiveUser() != null && getSessionUI().getActiveUser().getDataExtracts() != null && getSessionUI().getActiveUser().getDataExtracts())) {
			calculateIfCanExtract();
			populateExtractionInformation();
		}else {
			ajaxRedirectToDashboard();
		}
	}

	private void calculateIfCanExtract() throws Exception {
		if (getSessionUI().isAdmin()) {
			canExtract = true;
		} else {
			String todaysFormat = new SimpleDateFormat("dd:MM:yyyy").format(getNow());
			Date startOperationHour = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss").parse(todaysFormat+" "+startOperationHours);
			Date endOperationHour = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss").parse(todaysFormat+" "+endOperationHours);
		    if (getNow().after(startOperationHour) && getNow().before(endOperationHour)) {
		    	canExtract = false;
		    } else {
		    	canExtract = true;
			}
		}
	}
	
	private void populateExtractionInformation() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		currentDate = sdf.format(getNow());
		SimpleDateFormat sdfNrld = new SimpleDateFormat("yyMMdd");
		currentDateNrld = sdfNrld.format(getNow());
	}
	
	/* SETMIS Extracts Start */
	public void extractSETMISFile100BeanVersionTwo() {
		try {
			List<SETMISFile100BeanVersionTwo> setmisFile100BeanVersionTwo = setmisFile100Service.extractSETMISFile100BeanVersionTwoSqlPassed();
			String csv = "";
			if (!setmisFile100BeanVersionTwo.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile100BeanVersionTwo);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_100_v001_" + currentDate + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_100_v001_" + currentDate + ".dat", "text/plain");
				addWarningMessage("No data to export but file donwloaded");
			}
			setmisFile100BeanVersionTwo = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void extractSETMISFile200BeanVersionTwo() {
		try {
			List<SETMISFile200BeanVersionTwo> setmisFile200BeanVersionTwo = setmisService.extractSETMISFile200BeanVersionTwoSqlPassed();
			String csv = "";
			if (!setmisFile200BeanVersionTwo.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile200BeanVersionTwo);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_200_v001_" + currentDate + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_200_v001_" + currentDate + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
				addWarningMessage("No data to export");
			}
			setmisFile200BeanVersionTwo = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void extractSETMISFile304Bean() {
		try {
			List<SETMISFile304Bean> setmisFile304Bean = setmisService.extractSETMISFile304BeanVersionTwoSqlPassed();
			String csv = "";
			if (!setmisFile304Bean.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile304Bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_304_v001_" + currentDate + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_304_v001_" + currentDate + ".dat", "text/plain");
				addWarningMessage("No data to export");
			}
			setmisFile304Bean = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractSETMISFile400BeanVersionTwo() {
		try {
			List<SETMISFile400BeanVersionTwo> setmisFile400BeanVersionTwo = setmisService.extractSETMISFile400BeanVersionTwoSqlPassed();
			String csv = "";
			if (!setmisFile400BeanVersionTwo.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile400BeanVersionTwo);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_400_v001_" + currentDate + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_400_v001_" + currentDate + ".dat", "text/plain");
				addWarningMessage("No data to export");
			}
			setmisFile400BeanVersionTwo = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void extractSETMISFile401BeanVersionTwo() {
		try {
			List<SETMISFile401Bean> setmisFile401Bean = setmisService.extractSETMISFile401BeanVersionTwoSqlPassed();
			String csv = "" ;
			if (!setmisFile401Bean.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile401Bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_401_v001_" + currentDate + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_401_v001_" + currentDate + ".dat", "text/plain");
				addWarningMessage("No data to export");
			}
			setmisFile401Bean = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void extractSETMISFile500BeanVersionTwo() {
		try {
			List<SETMISFile500Bean> setmisFile500Bean = setmisService.extractSETMISFile500BeanVersionTwoSqlPassed();
			String csv = "";
			if (!setmisFile500Bean.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile500Bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_500_v001_" + currentDate + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_500_v001_" + currentDate + ".dat", "text/plain");
				addWarningMessage("No data to export");
			}
			setmisFile500Bean = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void extractSETMISFile501BeanVersionTwo() {
		try {
			List<SETMISFile501Bean> setmisFile501Bean = setmisService.extractSETMISFile501BeanVersionTwoSqlPassed();
			String csv = "";
			if (!setmisFile501Bean.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile501Bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_501_v001_" + currentDate + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_501_v001_" + currentDate + ".dat", "text/plain");
				addWarningMessage("No data to export");
			}
			setmisFile501Bean = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void extractSETMISFile502BeanVersionTwo() {
		try {
			List<SETMISFile502Bean> setmisFile502Bean = setmisService.extractSETMISFile502BeanVersionTwoSqlPassed();
			String csv = "";
			if (!setmisFile502Bean.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile502Bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_502_v001_" + currentDate + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_502_v001_" + currentDate + ".dat", "text/plain");
				addWarningMessage("No data to export");
			}
			setmisFile502Bean = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void extractSETMISFile503BeanVersionTwo() {
		try {
			List<SETMISFile503BeanVersionTwo> setmisFile503BeanVersionTwo = setmisService.extractSETMISFile503BeanVersionTwoSqlPassed();
			String csv = "";
			if (!setmisFile503BeanVersionTwo.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile503BeanVersionTwo);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_503_v001_" + currentDate + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_503_v001_" + currentDate + ".dat", "text/plain");
				addWarningMessage("No data to export");
			}
			setmisFile503BeanVersionTwo = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void extractSETMISFile505BeanVersionTwo() {
		try {
			List<SETMISFile505Bean> setmisFile505Bean = new ArrayList<>();
			String csv = "";
			if (!setmisFile505Bean.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile505Bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_505_v001_" + currentDate + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_505_v001_" + currentDate + ".dat", "text/plain");
				addWarningMessage("No data to export");
			}
			setmisFile505Bean = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void extractSETMISFile506BeanVersionTwo() {
		try {
			List<SETMISFile506Bean> setmisFile506Bean = setmisService.extractSETMISFile506BeanVersionTwoSqlPassed();
			String csv = "";
			if (!setmisFile506Bean.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile506Bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_506_v001_" + currentDate + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_506_v001_" + currentDate + ".dat", "text/plain");
				addWarningMessage("No data to export");
			}
			setmisFile506Bean = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	/* SETMIS Extracts End */
	
	/* NRLD Extracts Start */
	
	public void extractNLRDFile21Bean() {
		try {
			List<NLRDFile21BeanProviderVersionTwo> bean = nrldService.extractNLRDFile21BeanVersionTwoSqlPassed();
			if (!bean.isEmpty()) {
				headerBeanList = new ArrayList<>();
				headerBean = new HeaderBean();
				headerBean.setNumberOfRecords(bean.size());
				headerBean.setFileDescription("Provider File (21)");
				headerBean.setFiller("");
				headerBeanList.add(headerBean);
				String heading = CSVUtil.writeFixedLength(headerBeanList);
				String csv     = heading + "" + CSVUtil.writeFixedLength(bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS21" + currentDateNrld + ".dat", "text/plain");
			} else {
				headerBeanList = new ArrayList<>();
				headerBean = new HeaderBean();
				headerBean.setNumberOfRecords(0);
				headerBean.setFileDescription("Provider File (21)");
				headerBean.setFiller("");
				headerBeanList.add(headerBean);
				String heading = CSVUtil.writeFixedLength(headerBeanList);
				String csv     = heading;
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS21" + currentDateNrld + ".dat", "text/plain");
			}
			bean = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void extractNLRDFile24Bean() {
		try {
			List<NLRDFile24BeanProviderAccreditationVersionTwo> bean = nrldService.extractNLRDFile24BeanVersionTwoSqlPassed();
			if (!bean.isEmpty()) {
				headerBeanList = new ArrayList<>();
				headerBean = new HeaderBean();
				headerBean.setNumberOfRecords(bean.size());
				headerBean.setFileDescription("Provider Accreditation (File 24)");
				headerBean.setFiller("");
				headerBeanList.add(headerBean);
				String heading = CSVUtil.writeFixedLength(headerBeanList);
				String csv     = heading + "" + CSVUtil.writeFixedLength(bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS24" + currentDateNrld + ".dat", "text/plain");
			} else {
				headerBeanList = new ArrayList<>();
				headerBean = new HeaderBean();
				headerBean.setNumberOfRecords(0);
				headerBean.setFileDescription("Provider Accreditation (File 24)");
				headerBean.setFiller("");
				headerBeanList.add(headerBean);
				String heading = CSVUtil.writeFixedLength(headerBeanList);
				String csv     = heading;
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS24" + currentDateNrld + ".dat", "text/plain");
			}
			bean = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void extractNLRDFile25Bean() {
		try {
			List<NLRDFile25BeanPersonInformationVersionTwo> bean = nrldService.extractNLRDFile25BeanVersionTwoSqlPassed();
			if (!bean.isEmpty()) {
				headerBeanList = new ArrayList<>();
				headerBean = new HeaderBean();
				headerBean.setNumberOfRecords(bean.size());
				headerBean.setFileDescription("Person Information (File 25)");
				headerBean.setFiller("");
				headerBeanList.add(headerBean);
				String heading = CSVUtil.writeFixedLength(headerBeanList);
				String csv     = heading + "" + CSVUtil.writeFixedLength(bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS25" + currentDateNrld + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				headerBeanList = new ArrayList<>();
				headerBean = new HeaderBean();
				headerBean.setNumberOfRecords(bean.size());
				headerBean.setFileDescription("Person Information (File 25)");
				headerBean.setFiller("");
				headerBeanList.add(headerBean);
				String heading = CSVUtil.writeFixedLength(headerBeanList);
				String csv     = heading + "";
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS25" + currentDateNrld + ".dat", "text/plain");
				addWarningMessage("No data to export");
			}
			bean = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void extractNLRDFile26Bean() {
		try {
			List<NLRDFile26BeanPersonDesignationVersionTwo> bean = nrldService.extractNLRDFile26BeanVersionTwoSqlPassed();
			if (!bean.isEmpty()) {
				headerBeanList = new ArrayList<>();
				headerBean = new HeaderBean();
				headerBean.setNumberOfRecords(bean.size());
				headerBean.setFileDescription("File Name (File 26)");
				headerBean.setFiller("");
				headerBeanList.add(headerBean);
				String heading = CSVUtil.writeFixedLength(headerBeanList);
				String csv     = heading + "" + CSVUtil.writeFixedLength(bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS26" + currentDateNrld + ".dat", "text/plain");
			} else {
				headerBeanList = new ArrayList<>();
				headerBean = new HeaderBean();
				headerBean.setNumberOfRecords(0);
				headerBean.setFileDescription("File Name (File 26)");
				headerBean.setFiller("");
				headerBeanList.add(headerBean);
				String heading = CSVUtil.writeFixedLength(headerBeanList);
				String csv     = heading + "";
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS26" + currentDateNrld + ".dat", "text/plain");
			}
			bean = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void extractNLRDFile27Bean() {
		try {
			List<NLRDFile27BeanNQFDesignationRegistrationVersionTwo> bean = nrldService.extractNLRDFile27BeanVersionTwoSqlPassed();
			if (!bean.isEmpty()) {
				headerBeanList = new ArrayList<>();
				headerBean = new HeaderBean();
				headerBean.setNumberOfRecords(bean.size());
				headerBean.setFileDescription("NQF Designation registration (File 27)");
				headerBean.setFiller("");
				headerBeanList.add(headerBean);
				String heading = CSVUtil.writeFixedLength(headerBeanList);
				String csv     = heading + "" + CSVUtil.writeFixedLength(bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS27" + currentDateNrld + ".dat", "text/plain");
			} else {
				headerBeanList = new ArrayList<>();
				headerBean = new HeaderBean();
				headerBean.setNumberOfRecords(bean.size());
				headerBean.setFileDescription("NQF Designation registration (File 27)");
				headerBean.setFiller("");
				headerBeanList.add(headerBean);
				String heading = CSVUtil.writeFixedLength(headerBeanList);
				String csv     = heading + "";
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS27" + currentDateNrld + ".dat", "text/plain");
			}
			bean = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void extractNLRDFile28Bean() {
		try {
			List<NLRDFile28BeanLearnershipEnrolmentAchievementVersionTwo> bean = nrldService.extractNLRDFile28BeanVersionTwoSqlPassed();
			if (!bean.isEmpty()) {
				headerBeanList = new ArrayList<>();
				headerBean = new HeaderBean();
				headerBean.setNumberOfRecords(bean.size());
				headerBean.setFileDescription("Learnership Enrolment/Achievement (File 28)");
				headerBean.setFiller("");
				headerBeanList.add(headerBean);
				String heading = CSVUtil.writeFixedLength(headerBeanList);
				String csv     = heading + "" + CSVUtil.writeFixedLength(bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS28" + currentDateNrld + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				headerBeanList = new ArrayList<>();
				headerBean = new HeaderBean();
				headerBean.setNumberOfRecords(bean.size());
				headerBean.setFileDescription("Learnership Enrolment/Achievement (File 28)");
				headerBean.setFiller("");
				headerBeanList.add(headerBean);
				String heading = CSVUtil.writeFixedLength(headerBeanList);
				String csv     = heading + " ";
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS28" + currentDateNrld + ".dat", "text/plain");
			}
			bean = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void extractNLRDFile29Bean() {
		try {
			List<NLRDFile29BeanQualificationEnrolmentAchievementVersionTwo> bean = nrldService.extractNLRDFile29BeanVersionTwoSqlPassed();
			if (!bean.isEmpty()) {
				headerBeanList = new ArrayList<>();
				headerBean = new HeaderBean();
				headerBean.setNumberOfRecords(bean.size());
				headerBean.setFileDescription("Qualification Enrolment/Achievement (File 29)");
				headerBean.setFiller("");
				headerBeanList.add(headerBean);
				String heading = CSVUtil.writeFixedLength(headerBeanList);
				String csv     = heading + "" + CSVUtil.writeFixedLength(bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS29" + currentDateNrld + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				headerBeanList = new ArrayList<>();
				headerBean = new HeaderBean();
				headerBean.setNumberOfRecords(0);
				headerBean.setFileDescription("Qualification Enrolment/Achievement (File 29)");
				headerBean.setFiller("");
				headerBeanList.add(headerBean);
				String heading = CSVUtil.writeFixedLength(headerBeanList);
				String csv     = heading ;
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS29" + currentDateNrld + ".dat", "text/plain");
			}
			bean = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void extractNLRDFile30Bean() {
		try {
			List<NLRDFile30BeanUnitStandardEnrolmentAchievementVersionTwo> bean = nrldService.extractNLRDFile30BeanVersionTwoSqlPassed();
			if (!bean.isEmpty()) {
				headerBeanList = new ArrayList<>();
				headerBean = new HeaderBean();
				headerBean.setNumberOfRecords(bean.size());
				headerBean.setFileDescription("Unit Standard Enrolment/Achievement (File 30)");
				headerBean.setFiller("");
				headerBeanList.add(headerBean);
				String heading = CSVUtil.writeFixedLength(headerBeanList);
				String csv     = heading + "" + CSVUtil.writeFixedLength(bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS30" + currentDateNrld + ".dat", "text/plain");
			} else {
				headerBeanList = new ArrayList<>();
				headerBean = new HeaderBean();
				headerBean.setNumberOfRecords(0);
				headerBean.setFileDescription("Unit Standard Enrolment/Achievement (File 30)");
				headerBean.setFiller("");
				headerBeanList.add(headerBean);
				String heading = CSVUtil.writeFixedLength(headerBeanList);
				String csv     = heading + "";
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS30" + currentDateNrld + ".dat", "text/plain");
			}
			bean = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	
	/* NRLD Extracts End */

	/* Getters and setters */
	public boolean isCanExtract() {
		return canExtract;
	}

	public void setCanExtract(boolean canExtract) {
		this.canExtract = canExtract;
	}

	public String getStartOperationHours() {
		return startOperationHours;
	}

	public void setStartOperationHours(String startOperationHours) {
		this.startOperationHours = startOperationHours;
	}

	public String getEndOperationHours() {
		return endOperationHours;
	}

	public void setEndOperationHours(String endOperationHours) {
		this.endOperationHours = endOperationHours;
	}

	
}
