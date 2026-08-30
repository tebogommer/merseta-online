package haj.com.ui;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Company;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyService;

// TODO: Auto-generated Javadoc
/**
 * The Class CompanyLookupTableUI.
 */
@ManagedBean(name = "companyLookupTableUI")
@ViewScoped
public class CompanyLookupTableUI extends AbstractUI {

	/** The company data model. */
	private LazyDataModel<Company> companyDataModel;
	
	/** The company filtered list. */
	private List<Company> companyFilteredList;
	
	/** The company service. */
	private CompanyService companyService = new CompanyService();
	
	/** The company. */
	private Company company;

	/** The where hql. */
	private String whereHql = " where o.id is not null ";
	
	/** The company name statement. */
	private String companyNameStatement = " and o.companyName LIKE :companyName ";
	
	/** The company registration number statement. */
	private String companyRegistrationNumberStatement = " and o.companyRegistrationNumber LIKE :companyRegistrationNumber ";
	
	/** The company email statement. */
	private String companyEmailStatement = " and o.email LIKE :email ";
	
	/** The company name criteria. */
	private String companyNameCriteria;
	
	/** The company registration number criteria. */
	private String companyRegistrationNumberCriteria;
	
	/** The company email criteria. */
	private String companyEmailCriteria;
	
	/** The parameters. */
	private Map<String, Object> parameters;

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
	 * Run init.
	 *
	 * @throws Exception the exception
	 */
	private void runInit() throws Exception {
		companyInfo();
		this.company = new Company();
	}

	/**
	 * Clear search.
	 */
	public void clearSearch() {
		parameters = new Hashtable<String, Object>();
		companyNameCriteria = null;
		companyRegistrationNumberCriteria = null;
		companyEmailCriteria = null;
	}

	/**
	 * Go back.
	 */
	public void goBack() {
		clearSearch();
		this.company = new Company();
	}

	/**
	 * Redirect to learner reg for company.
	 */
	public void redirectToLearnerRegForCompany() {
		super.setParameter("companyId", this.company.getId(), true);
		super.ajaxRedirect("/pages/tp/learnerRegistrationForm.jsf");
	}

	/**
	 * Redirect to learner reg for users email GUID.
	 */
	public void redirectToLearnerRegForUsersEmailGUID() {
		super.redirect("/admin/companyUsersLookupTableForLearnersReg.jsf?companyId=" + this.company.getCompanyGuid().trim());
	}

	/**
	 * Parameters for company search.
	 */
	public void parametersForCompanySearch() {
		parameters = new Hashtable<String, Object>();
		if (!this.companyNameCriteria.isEmpty()) {
			whereHql += companyNameStatement;
			parameters.put("companyName", companyNameCriteria + "%");
		}
		if (!this.companyRegistrationNumberCriteria.isEmpty()) {
			whereHql += companyRegistrationNumberStatement;
			parameters.put("companyRegistrationNumber", companyRegistrationNumberCriteria + "%");
		}
		if (!this.companyEmailCriteria.isEmpty()) {
			whereHql += companyEmailStatement;
			parameters.put("email", companyEmailCriteria + "%");
		}
		companyInfo();
	}

	/**
	 * Company info.
	 */
	public void companyInfo() {
		companyFilteredList = new ArrayList<>();
		companyDataModel = new LazyDataModel<Company>() {
			private static final long serialVersionUID = 1L;
			private List<Company> retorno = new ArrayList<Company>();

			@Override
			public List<Company> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = companyService.allCompanyForLearnerReg(Company.class, first, pageSize, sortField, sortOrder, parameters, whereHql);
					companyDataModel.setRowCount(companyService.count(Company.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(Company obj) {
				return obj.getId();
			}

			@Override
			public Company getRowData(String rowKey) {
				for (Company obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	/**
	 * Gets the company data model.
	 *
	 * @return the company data model
	 */
	public LazyDataModel<Company> getCompanyDataModel() {
		return companyDataModel;
	}

	/**
	 * Sets the company data model.
	 *
	 * @param companyDataModel the new company data model
	 */
	public void setCompanyDataModel(LazyDataModel<Company> companyDataModel) {
		this.companyDataModel = companyDataModel;
	}

	/**
	 * Gets the company filtered list.
	 *
	 * @return the company filtered list
	 */
	public List<Company> getCompanyFilteredList() {
		return companyFilteredList;
	}

	/**
	 * Sets the company filtered list.
	 *
	 * @param companyFilteredList the new company filtered list
	 */
	public void setCompanyFilteredList(List<Company> companyFilteredList) {
		this.companyFilteredList = companyFilteredList;
	}

	/**
	 * Gets the where hql.
	 *
	 * @return the where hql
	 */
	public String getWhereHql() {
		return whereHql;
	}

	/**
	 * Sets the where hql.
	 *
	 * @param whereHql the new where hql
	 */
	public void setWhereHql(String whereHql) {
		this.whereHql = whereHql;
	}

	/**
	 * Gets the company name statement.
	 *
	 * @return the company name statement
	 */
	public String getCompanyNameStatement() {
		return companyNameStatement;
	}

	/**
	 * Sets the company name statement.
	 *
	 * @param companyNameStatement the new company name statement
	 */
	public void setCompanyNameStatement(String companyNameStatement) {
		this.companyNameStatement = companyNameStatement;
	}

	/**
	 * Gets the company registration number statement.
	 *
	 * @return the company registration number statement
	 */
	public String getCompanyRegistrationNumberStatement() {
		return companyRegistrationNumberStatement;
	}

	/**
	 * Sets the company registration number statement.
	 *
	 * @param companyRegistrationNumberStatement the new company registration number statement
	 */
	public void setCompanyRegistrationNumberStatement(String companyRegistrationNumberStatement) {
		this.companyRegistrationNumberStatement = companyRegistrationNumberStatement;
	}

	/**
	 * Gets the company name criteria.
	 *
	 * @return the company name criteria
	 */
	public String getCompanyNameCriteria() {
		return companyNameCriteria;
	}

	/**
	 * Sets the company name criteria.
	 *
	 * @param companyNameCriteria the new company name criteria
	 */
	public void setCompanyNameCriteria(String companyNameCriteria) {
		this.companyNameCriteria = companyNameCriteria;
	}

	/**
	 * Gets the company registration number criteria.
	 *
	 * @return the company registration number criteria
	 */
	public String getCompanyRegistrationNumberCriteria() {
		return companyRegistrationNumberCriteria;
	}

	/**
	 * Sets the company registration number criteria.
	 *
	 * @param companyRegistrationNumberCriteria the new company registration number criteria
	 */
	public void setCompanyRegistrationNumberCriteria(String companyRegistrationNumberCriteria) {
		this.companyRegistrationNumberCriteria = companyRegistrationNumberCriteria;
	}

	/**
	 * Gets the parameters.
	 *
	 * @return the parameters
	 */
	public Map<String, Object> getParameters() {
		return parameters;
	}

	/**
	 * Sets the parameters.
	 *
	 * @param parameters the parameters
	 */
	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	/**
	 * Gets the company.
	 *
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * Sets the company.
	 *
	 * @param company the new company
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * Gets the company email statement.
	 *
	 * @return the company email statement
	 */
	public String getCompanyEmailStatement() {
		return companyEmailStatement;
	}

	/**
	 * Sets the company email statement.
	 *
	 * @param companyEmailStatement the new company email statement
	 */
	public void setCompanyEmailStatement(String companyEmailStatement) {
		this.companyEmailStatement = companyEmailStatement;
	}

	/**
	 * Gets the company email criteria.
	 *
	 * @return the company email criteria
	 */
	public String getCompanyEmailCriteria() {
		return companyEmailCriteria;
	}

	/**
	 * Sets the company email criteria.
	 *
	 * @param companyEmailCriteria the new company email criteria
	 */
	public void setCompanyEmailCriteria(String companyEmailCriteria) {
		this.companyEmailCriteria = companyEmailCriteria;
	}

}
