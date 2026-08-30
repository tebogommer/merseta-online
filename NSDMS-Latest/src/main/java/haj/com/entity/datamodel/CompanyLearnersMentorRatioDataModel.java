package haj.com.entity.datamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.lookup.Region;
import haj.com.entity.lookup.RegionTown;
import haj.com.service.CompanyLearnersService;
import haj.com.service.CompanyService;
import haj.com.service.SitesSmeService;
import haj.com.service.SmeQualificationsService;
import haj.com.service.lookup.RegionService;
import haj.com.service.lookup.RegionTownService;

public class CompanyLearnersMentorRatioDataModel extends LazyDataModel<CompanyLearners> {

	private static final long serialVersionUID = 1L;
	private List<CompanyLearners> retorno = new ArrayList<CompanyLearners>();
	private CompanyLearnersService companyLearnersService = new CompanyLearnersService();
	private SmeQualificationsService smeQualificationsService = new SmeQualificationsService();
	private SitesSmeService sitesSmeService = new SitesSmeService();
	private CompanyService companyService = new CompanyService();
	private Company company;
	
	public CompanyLearnersMentorRatioDataModel(Company company) {
		super();
		this.company = company;
	}

	@Override
	public List<CompanyLearners> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			if(filters == null) {
				filters = new HashMap<>();
			}
			filters.put("companyID", company.getId());			
			retorno = companyLearnersService.sortAndFilterWhereLearnerMentorRatio(first, pageSize, sortField, sortOrder, filters);
			for(CompanyLearners companyLearners: retorno) {
				companyLearners.setEmployer(companyService.findByKey(companyLearners.getEmployer().getId()));
				Region region = getRegion(companyLearners.getCompany());
				companyLearners.getCompany().setRegion(region);
				if(companyLearners.getSitesSme() != null) {
					companyLearners.setSitesSme(sitesSmeService.findByKey(companyLearners.getSitesSme().getId()));
				}
				if(companyLearners.getSmeQualification() != null) {
					companyLearners.setSmeQualification(smeQualificationsService.findByKey(companyLearners.getSmeQualification().getId()));
				}
			}
			setRowCount(companyLearnersService.countWhereLearnerMentorRatio(filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(CompanyLearners obj) {
		return obj.getId();
	}

	@Override
	public CompanyLearners getRowData(String rowKey) {
		for (CompanyLearners obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}
	
	public Region getRegion(Company company) throws Exception {
		RegionService regionService = new RegionService();
		Region r = new Region();
		if(company != null && company.getResidentialAddress() != null && company.getResidentialAddress().getTown() != null) {
			RegionTown rt = RegionTownService.instance().findByTown(company.getResidentialAddress().getTown());
			r = regionService.findByKey(rt.getRegion().getId());
		}
		return r;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}
