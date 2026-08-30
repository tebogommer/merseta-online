package  haj.com.ui;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

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
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.JasperService;
import haj.com.service.NRLDService;
import haj.com.service.QCTOService;
import haj.com.service.SETMISService;
import haj.com.service.setmis.SETMISFile100Service;
import haj.com.service.setmis.SETMISFile200Service;
import haj.com.service.setmis.SETMISFile304Service;
import haj.com.service.setmis.SETMISFile400Service;
import haj.com.service.setmis.SETMISFile401Service;
import haj.com.service.setmis.SETMISFile500Service;
import haj.com.service.setmis.SETMISFile501Service;
import haj.com.service.setmis.SETMISFile502Service;
import haj.com.service.setmis.SETMISFile503Service;
import haj.com.service.setmis.SETMISFile505Service;
import haj.com.service.setmis.SETMISFile506Service;
import haj.com.utils.CSVUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class BlankUI.
 */
@ManagedBean(name = "setmisNrldQctoExtractsUI")
@ViewScoped
public class SetmisNrldQctoExtractsUI extends AbstractUI {
	
	/* Service levels */
	private NRLDService  nrldService = new NRLDService();
	private SETMISService setmisService = new SETMISService();
	private QCTOService qctoService = new QCTOService();
	
	/* Vars */
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	/* SETMIS Service Levels */
	private SETMISFile100Service setmisFile100Service = new SETMISFile100Service();
	private SETMISFile200Service setmisFile200Service = new SETMISFile200Service();
	private SETMISFile304Service setmisFile304Service = new SETMISFile304Service();
	private SETMISFile400Service setmisFile400Service = new SETMISFile400Service();
	private SETMISFile401Service setmisFile401Service = new SETMISFile401Service();
	private SETMISFile500Service setmisFile500Service = new SETMISFile500Service();
	private SETMISFile501Service setmisFile501Service = new SETMISFile501Service();
	private SETMISFile502Service setmisFile502Service = new SETMISFile502Service();
	private SETMISFile503Service setmisFile503Service = new SETMISFile503Service();
	private SETMISFile505Service setmisFile505Service = new SETMISFile505Service();
	private SETMISFile506Service setmisFile506Service = new SETMISFile506Service();

	/* Setmis Beans */
	private List<SETMISFile100BeanVersionTwo>       setmisFile100BeanVersionTwo;
	private List<SETMISFile200BeanVersionTwo>       setmisFile200BeanVersionTwo;
	private List<SETMISFile304Bean>       			setmisFile304Bean;
	private List<SETMISFile400BeanVersionTwo>       setmisFile400BeanVersionTwo;
	private List<SETMISFile401Bean>       			setmisFile401Bean;
	private List<SETMISFile500Bean>       			setmisFile500Bean;
	private List<SETMISFile501Bean>       			setmisFile501Bean;
	private List<SETMISFile502Bean>       			setmisFile502Bean;
	private List<SETMISFile503BeanVersionTwo>       setmisFile503BeanVersionTwo;
	private List<SETMISFile505Bean>       			setmisFile505Bean;
	private List<SETMISFile506Bean>       			setmisFile506Bean;
	
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
	}

	/* SETMIS Extracts */
	public void extractSETMISFile100BeanVersionTwo() {
		try {
			setmisFile100BeanVersionTwo = setmisFile100Service.extractSETMISFile100BeanVersionTwo();
			String csv = "";
			if (!setmisFile100BeanVersionTwo.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile100BeanVersionTwo);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_100_v001_" + sdf.format(getNow()) + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_100_v001_" + sdf.format(getNow()) + ".dat", "text/plain");
				addWarningMessage("No data to export but file donwloaded");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void extractSETMISFile200BeanVersionTwo() {
		try {
			setmisFile200BeanVersionTwo = setmisService.extractSETMISFile200BeanVersionTwo();
			String csv = "";
			if (!setmisFile200BeanVersionTwo.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile200BeanVersionTwo);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_200_v001_" + sdf.format(getNow()) + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_200_v001_" + sdf.format(getNow()) + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
				addWarningMessage("No data to export");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void extractSETMISFile304Bean() {
		try {
//			setmisFile304Bean = setmisService.extractSETMISFile304Bean();
			setmisFile304Bean = setmisService.extractSETMISFile304BeanVersionTwo();
			String csv = "";
			if (!setmisFile304Bean.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile304Bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_304_v001_" + sdf.format(getNow()) + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_304_v001_" + sdf.format(getNow()) + ".dat", "text/plain");
				addWarningMessage("No data to export");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void extractSETMISFile400BeanVersionTwo() {
		try {
			setmisFile400BeanVersionTwo = setmisService.extractSETMISFile400BeanVersionTwo();
			String csv = "";
			if (!setmisFile400BeanVersionTwo.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile400BeanVersionTwo);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_400_v001_" + sdf.format(getNow()) + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_400_v001_" + sdf.format(getNow()) + ".dat", "text/plain");
				addWarningMessage("No data to export");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void extractSETMISFile401BeanVersionTwo() {
		try {
			setmisFile401Bean = setmisService.extractSETMISFile401BeanVersionTwo();
			String csv = "" ;
			if (!setmisFile401Bean.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile401Bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_401_v001_" + sdf.format(getNow()) + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_401_v001_" + sdf.format(getNow()) + ".dat", "text/plain");
				addWarningMessage("No data to export");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void extractSETMISFile500BeanVersionTwo() {
		try {
			setmisFile500Bean = setmisService.extractSETMISFile500BeanVersionTwo();
			String csv = "";
			if (!setmisFile500Bean.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile500Bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_500_v001_" + sdf.format(getNow()) + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_500_v001_" + sdf.format(getNow()) + ".dat", "text/plain");
				addWarningMessage("No data to export");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void extractSETMISFile501BeanVersionTwo() {
		try {
			setmisFile501Bean = setmisService.extractSETMISFile501BeanVersionTwo();
			String csv = "";
			if (!setmisFile501Bean.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile501Bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_501_v001_" + sdf.format(getNow()) + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_501_v001_" + sdf.format(getNow()) + ".dat", "text/plain");
				addWarningMessage("No data to export");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void extractSETMISFile502BeanVersionTwo() {
		try {
			setmisFile502Bean = setmisService.extractSETMISFile502BeanVersionTwo();
			String csv = "";
			if (!setmisFile502Bean.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile502Bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_502_v001_" + sdf.format(getNow()) + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_502_v001_" + sdf.format(getNow()) + ".dat", "text/plain");
				addWarningMessage("No data to export");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void extractSETMISFile503BeanVersionTwo() {
		try {
			setmisFile503BeanVersionTwo = setmisService.extractSETMISFile503BeanVersionTwo();
			String csv = "";
			if (!setmisFile503BeanVersionTwo.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile503BeanVersionTwo);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_503_v001_" + sdf.format(getNow()) + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_503_v001_" + sdf.format(getNow()) + ".dat", "text/plain");
				addWarningMessage("No data to export");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void extractSETMISFile505BeanVersionTwo() {
		try {
			setmisFile505Bean = setmisService.extractSETMISFile505BeanVersionTwo();
			String csv = "";
			if (!setmisFile505Bean.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile505Bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_505_v001_" + sdf.format(getNow()) + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_505_v001_" + sdf.format(getNow()) + ".dat", "text/plain");
				addWarningMessage("No data to export");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void extractSETMISFile506BeanVersionTwo() {
		try {
			setmisFile506Bean = setmisService.extractSETMISFile506BeanVersionTwo();
			String csv = "";
			if (!setmisFile506Bean.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile506Bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_506_v001_" + sdf.format(getNow()) + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_506_v001_" + sdf.format(getNow()) + ".dat", "text/plain");
				addWarningMessage("No data to export");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/* Getters and setters */
	public List<SETMISFile100BeanVersionTwo> getSetmisFile100BeanVersionTwo() {
		return setmisFile100BeanVersionTwo;
	}

	public void setSetmisFile100BeanVersionTwo(List<SETMISFile100BeanVersionTwo> setmisFile100BeanVersionTwo) {
		this.setmisFile100BeanVersionTwo = setmisFile100BeanVersionTwo;
	}

	public List<SETMISFile200BeanVersionTwo> getSetmisFile200BeanVersionTwo() {
		return setmisFile200BeanVersionTwo;
	}

	public void setSetmisFile200BeanVersionTwo(List<SETMISFile200BeanVersionTwo> setmisFile200BeanVersionTwo) {
		this.setmisFile200BeanVersionTwo = setmisFile200BeanVersionTwo;
	}

	public List<SETMISFile304Bean> getSetmisFile304Bean() {
		return setmisFile304Bean;
	}

	public void setSetmisFile304Bean(List<SETMISFile304Bean> setmisFile304Bean) {
		this.setmisFile304Bean = setmisFile304Bean;
	}

	public List<SETMISFile400BeanVersionTwo> getSetmisFile400BeanVersionTwo() {
		return setmisFile400BeanVersionTwo;
	}

	public void setSetmisFile400BeanVersionTwo(List<SETMISFile400BeanVersionTwo> setmisFile400BeanVersionTwo) {
		this.setmisFile400BeanVersionTwo = setmisFile400BeanVersionTwo;
	}

	public List<SETMISFile401Bean> getSetmisFile401Bean() {
		return setmisFile401Bean;
	}

	public void setSetmisFile401Bean(List<SETMISFile401Bean> setmisFile401Bean) {
		this.setmisFile401Bean = setmisFile401Bean;
	}

	public List<SETMISFile500Bean> getSetmisFile500Bean() {
		return setmisFile500Bean;
	}

	public void setSetmisFile500Bean(List<SETMISFile500Bean> setmisFile500Bean) {
		this.setmisFile500Bean = setmisFile500Bean;
	}

	public List<SETMISFile501Bean> getSetmisFile501Bean() {
		return setmisFile501Bean;
	}

	public void setSetmisFile501Bean(List<SETMISFile501Bean> setmisFile501Bean) {
		this.setmisFile501Bean = setmisFile501Bean;
	}

	public List<SETMISFile502Bean> getSetmisFile502Bean() {
		return setmisFile502Bean;
	}

	public void setSetmisFile502Bean(List<SETMISFile502Bean> setmisFile502Bean) {
		this.setmisFile502Bean = setmisFile502Bean;
	}

	public List<SETMISFile503BeanVersionTwo> getSetmisFile503BeanVersionTwo() {
		return setmisFile503BeanVersionTwo;
	}

	public void setSetmisFile503BeanVersionTwo(List<SETMISFile503BeanVersionTwo> setmisFile503BeanVersionTwo) {
		this.setmisFile503BeanVersionTwo = setmisFile503BeanVersionTwo;
	}

	public List<SETMISFile505Bean> getSetmisFile505Bean() {
		return setmisFile505Bean;
	}

	public void setSetmisFile505Bean(List<SETMISFile505Bean> setmisFile505Bean) {
		this.setmisFile505Bean = setmisFile505Bean;
	}

	public List<SETMISFile506Bean> getSetmisFile506Bean() {
		return setmisFile506Bean;
	}

	public void setSetmisFile506Bean(List<SETMISFile506Bean> setmisFile506Bean) {
		this.setmisFile506Bean = setmisFile506Bean;
	}

	
}
