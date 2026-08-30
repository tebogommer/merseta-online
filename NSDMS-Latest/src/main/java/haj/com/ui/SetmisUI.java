package haj.com.ui;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.constants.HAJConstants;
import haj.com.entity.Blank;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.JasperService;
import haj.com.service.SETMISService;
import haj.com.utils.CSVUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class BlankUI.
 */
@ManagedBean(name = "setmisUI")
@ViewScoped
public class SetmisUI extends AbstractUI {

	private SETMISService setmisService = new SETMISService();
	private String currentDate;

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
	 * Initialize method to get all Blank and prepare a for a create of a new
	 * Blank.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see Blank
	 */
	private void runInit() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		this.currentDate = sdf.format(new Date());
	}

	public void extractSETMISFile401Bean() {
		try {

			String fileName = HAJConstants.DOC_PATH + "MERS_0006_401_v001_" + currentDate + ".dat";
			setmisService.extractSETMISFile401BeanScrollableResult(fileName);
			Path filePath = Paths.get(fileName);
			String csv = new String(Files.readAllBytes(filePath));
			JasperService.convertByteArrayToServletOutputStream(csv.getBytes(),
					"MERS_0006_401_v001_" + currentDate + ".dat", "text/plain");
			Files.delete(filePath);
			addInfoMessage("Extract Done");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractSETMISFile400Bean() {
		try {
			String fileName = HAJConstants.DOC_PATH + "MERS_0006_400_v001_" + currentDate + ".dat";
			setmisService.extractSETMISFile400BeanScrollableResult(fileName);
			Path filePath = Paths.get(fileName);
			String csv = new String(Files.readAllBytes(filePath));
			JasperService.convertByteArrayToServletOutputStream(csv.getBytes(),
					"MERS_0006_400_v001_" + currentDate + ".dat", "text/plain");
			Files.delete(filePath);
			addInfoMessage("Extract Done");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}

	}

}
