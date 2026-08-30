package haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import haj.com.entity.CompanyUsers;
import haj.com.entity.SDFCompany;
import haj.com.entity.datamodel.CompanyContactsDatamodel;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;

// TODO: Auto-generated Javadoc
/**
 * The Class SDFCompanyUI.
 */
@ManagedBean(name = "companyContactsUI")
@ViewScoped
public class CompanyContactsUI extends AbstractUI {

	/** The data model. */
	private LazyDataModel<CompanyUsers> dataModel;
	private List<CompanyUsers> companyUsersList = null;
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
	 * Initialize method to get all CompanySDF and prepare a for a create of a new
	 * CompanySDF.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see SDFCompany
	 */
	private void runInit() throws Exception {
		populateInformation();
	}

	private void populateInformation() {
		dataModel = new CompanyContactsDatamodel();
	}

	public LazyDataModel<CompanyUsers> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<CompanyUsers> dataModel) {
		this.dataModel = dataModel;
	}

	public List<CompanyUsers> getCompanyUsersList() {
		return companyUsersList;
	}

	public void setCompanyUsersList(List<CompanyUsers> companyUsersList) {
		this.companyUsersList = companyUsersList;
	}

}
