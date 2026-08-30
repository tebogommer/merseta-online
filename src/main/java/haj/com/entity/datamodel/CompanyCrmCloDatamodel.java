package haj.com.entity.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Company;
import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.Region;
import haj.com.entity.lookup.RegionTown;
import haj.com.service.CompanyService;
import haj.com.service.CompanyUsersService;
import haj.com.service.lookup.RegionService;
import haj.com.service.lookup.RegionTownService;
import haj.com.ui.SessionUI;

public class CompanyCrmCloDatamodel extends LazyDataModel<Company> {

	private static final long serialVersionUID = 1L;
	private List<Company> retorno = new ArrayList<Company>();
	private CompanyService service = new CompanyService();
	private Users user;
	private SessionUI sessionUI;

	public CompanyCrmCloDatamodel(Users user) {
		super();
		this.user = user;
	}

	public CompanyCrmCloDatamodel(SessionUI sessionUI) {
		this.user = sessionUI.getUser();
		this.sessionUI = sessionUI;
	}

	@Override
	public List<Company> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			if(!sessionUI.isEmployee() && !sessionUI.isAdmin()) {
				//filters.put("companyUserType", ConfigDocProcessEnum.TP);
				filters.put("user", user);
			}
			
			retorno = (List<Company>) service.sortAndFilterWhere(Company.class, first, pageSize, sortField, sortOrder, filters);

			
			for(Company company : retorno) {				
				  company.setRegion(getRegion(company)); 
				  company.setClo(getCLO(company));
				  company.setCrm(getCRM(company));	
				  company.setRegionTown(getRegionTown(company));
			}
			
			setRowCount(service.countWhere(Company.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
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
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
	
	public Region getRegion(Company company) throws Exception {
		RegionService regionService = new RegionService();
		Region r = new Region();
		if(company.getResidentialAddress() != null && company.getResidentialAddress().getTown()!=null) {
			RegionTown rt = RegionTownService.instance().findByTown(company.getResidentialAddress().getTown());
			r = regionService.findByKey(rt.getRegion().getId());
		}
		return r;
	}
	
	public Users getCLO(Company company) throws Exception {
		Users cloUser = new Users();
		if(company.getResidentialAddress() != null && company.getResidentialAddress().getTown()!=null) {
			RegionTown rt = RegionTownService.instance().findByTown(company.getResidentialAddress().getTown());
			cloUser = rt.getClo().getUsers();
		}
		return cloUser;
	}
	
	public Users getCRM(Company company) throws Exception {
		Users crmUser = new Users();
		if(company.getResidentialAddress() != null && company.getResidentialAddress().getTown()!=null) {
			RegionTown rt = RegionTownService.instance().findByTown(company.getResidentialAddress().getTown());
			crmUser = rt.getCrm().getUsers();
		}
		return crmUser;
	}
	

	private RegionTown getRegionTown(Company company) throws Exception{
		RegionTown rt = new RegionTown();
		if(company.getResidentialAddress() != null && company.getResidentialAddress().getTown()!=null) {
			rt = RegionTownService.instance().findByTown(company.getResidentialAddress().getTown());
		}		
		return rt;
	}
}
