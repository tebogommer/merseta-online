package  haj.com.ui;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.Blank;
import haj.com.entity.SDFCompany;
import haj.com.entity.datamodel.CompanySdfDatamodel;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.SDFCompanyService;

/**
 * The Class CompanySdfReportUI
 */
@ManagedBean(name = "companySdfReportUI")
@ViewScoped
public class CompanySdfReportUI extends AbstractUI {

	/* service levels */
	private SDFCompanyService sdfCompanyService = new SDFCompanyService();
	
	/* lazy data models */
	private LazyDataModel<SDFCompany> dataModel;
	
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
		companySdfInfo();
	}
	
	public void downloadCompanySdfReport(){
		try {
			sdfCompanyService.downloadCompanySdfReport();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void companySdfInfo() {
		dataModel = new CompanySdfDatamodel();
	}

	/* getters and setters */
	public LazyDataModel<SDFCompany> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SDFCompany> dataModel) {
		this.dataModel = dataModel;
	}
	
}