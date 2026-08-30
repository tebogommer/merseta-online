package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Company;
import haj.com.entity.Wsp;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyService;
import haj.com.service.WspService;


@ManagedBean(name = "monitorContractsUI")
@ViewScoped
public class MonitorContractsUI extends AbstractUI {

	/* Entity Level */
	private Company selectedCompany = null;
	private Wsp selectedWsp = null;
	
	/* Service Level */
	private CompanyService companyService = null;
	private WspService wspService = null;
	
	/* Lazy Data Models */
	private LazyDataModel<Company> dataModelComapny;
	private LazyDataModel<Wsp> dataModelWsp;
	
	
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
		populateServiceLevels();
		populateCompanies();
	}

	private void populateServiceLevels() throws Exception{
		if (companyService == null) {
			companyService = new CompanyService();
		}
		if (wspService == null) {
			wspService = new WspService();
		}
	}
	
	private void populateCompanies() throws Exception{
		companyInfo();
	}
	
	public void companyInfo() {
		dataModelComapny = new LazyDataModel<Company>() {
			private static final long serialVersionUID = 1L;
			private List<Company> companyList = new ArrayList<>();
			@Override
			public List<Company> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (sortField == null) {
						sortField = "companyStatus";
						sortOrder = SortOrder.DESCENDING;
					}
					companyList = companyService.allCompany2(Company.class, first, pageSize, sortField, sortOrder, filters);
					dataModelComapny.setRowCount(companyService.count(Company.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return companyList;
			}
			@Override
			public Object getRowKey(Company object) {
				return object.getId();
			}
			@Override
			public Company getRowData(String rowKey) {
				for (Company obj : companyList) {
					if (obj.getId().equals(Long.valueOf(rowKey))) {
						return obj;
					}
				}
				return null;
			}
		};
	}
	
	public void pageFocusRun(String divName) {
		String js = "		setTimeout(function() { " + "			$('html,body').animate({ "
				+ "				scrollTop : $('#" + divName + "').offset().top " + "			}, 1000); "
				+ "		}, 0);";
		super.runClientSideExecute(js);
		divName = null;
	}
	
	public void selectCompany(){
		try {
			wspInfo();
			pageFocusRun("wspForm");
			addInfoMessage("Company Selected");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void wspInfo() {
		dataModelWsp = new LazyDataModel<Wsp>() {
			private static final long serialVersionUID = 1L;
			private List<Wsp> list = new ArrayList<>();
			@Override
			public List<Wsp> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					list = wspService.allWspByCompany(first, pageSize, sortField, sortOrder, filters, selectedCompany);
					dataModelWsp.setRowCount(wspService.countAllWspByCompany(filters));
				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return list;
			}
			@Override
			public Object getRowKey(Wsp object) {
				return object.getId();
			}
			@Override
			public Wsp getRowData(String rowKey) {
				for (Wsp obj : list) {
					if (obj.getId().equals(Long.valueOf(rowKey))) {
						return obj;
					}
				}
				return null;
			}
		};
	}

	/* Getters and Setters */
	public LazyDataModel<Company> getDataModelComapny() {
		return dataModelComapny;
	}

	public void setDataModelComapny(LazyDataModel<Company> dataModelComapny) {
		this.dataModelComapny = dataModelComapny;
	}

	public Company getSelectedCompany() {
		return selectedCompany;
	}

	public void setSelectedCompany(Company selectedCompany) {
		this.selectedCompany = selectedCompany;
	}

	public Wsp getSelectedWsp() {
		return selectedWsp;
	}

	public void setSelectedWsp(Wsp selectedWsp) {
		this.selectedWsp = selectedWsp;
	}

	public LazyDataModel<Wsp> getDataModelWsp() {
		return dataModelWsp;
	}

	public void setDataModelWsp(LazyDataModel<Wsp> dataModelWsp) {
		this.dataModelWsp = dataModelWsp;
	}
	
}