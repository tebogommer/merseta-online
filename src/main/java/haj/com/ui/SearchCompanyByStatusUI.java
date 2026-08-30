package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Company;
import haj.com.entity.MailLog;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyService;
import haj.com.service.MailLogService;

@ManagedBean(name="searchCompanyByStatusUI")
@ViewScoped
public class SearchCompanyByStatusUI  extends AbstractUI
{
	/** The company service. */
	private CompanyService companyService = new CompanyService();
	/** The company */
	private Company company=new Company();
	/**Company lazy data model*/
	private LazyDataModel<Company> dataModel;
	/**The company list**/
	private List<Company> companyFilteredList = null;
	/**the Mail logs**/
	private List<MailLog> mailLogs;
	/**mail log server**/
	private MailLogService mailLogService = new MailLogService();
	
	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		try {
			
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Run init.
	 *
	 * @throws Exception the exception
	 */
	public void runInit() throws Exception {
		 companyInfo();
	}
	
	public void companyInfo()
	{
		
		dataModel=new LazyDataModel<Company>() 
		{
			private static final long serialVersionUID = 1L;
			private List<Company> companyList=new ArrayList<>();
			
			@Override
			public List<Company> load(int first, int pageSize, String sortField, SortOrder sortOrder,Map<String, Object> filters) {
				
				try
				{
					
					companyList=companyService.allCompanyByStatusThaIsNotActive(Company.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(companyService.count(Company.class, filters));
					
				}
				catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return companyList;
			}
			@Override
			public Object getRowKey(Company object) {
				// TODO Auto-generated method stub
				return object.getId();
			}
			@Override
			public Company getRowData(String rowKey) {
				for (Company obj : companyList) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
					{
						return obj;
					}
				}
				return null;
			}
		};
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public LazyDataModel<Company> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<Company> dataModel) {
		this.dataModel = dataModel;
	}

	public List<Company> getCompanyFilteredList() {
		return companyFilteredList;
	}

	public void setCompanyFilteredList(List<Company> companyFilteredList) {
		this.companyFilteredList = companyFilteredList;
	}

	public List<MailLog> getMailLogs() {
		return mailLogs;
	}

	public void setMailLogs(List<MailLog> mailLogs) {
		this.mailLogs = mailLogs;
	}
	
	public void findMailLog() {
		try {
			this.mailLogs = mailLogService.allMailForCompany(company);
			if (this.mailLogs.size() < 1) {
				addInfoMessage("No mails has been send");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

}
