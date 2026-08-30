package haj.com.entity.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.ActiveContracts;
import haj.com.entity.DgAllocationParent;
import haj.com.entity.ProjectImplementationPlan;
import haj.com.entity.SDFCompany;
import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.lookup.RegionTown;
import haj.com.service.ActiveContractsService;
import haj.com.service.DgAllocationParentService;
import haj.com.service.ProjectImplementationPlanService;
import haj.com.service.SDFCompanyService;
import haj.com.service.lookup.RegionTownService;

public class ProjectImplementationPlanDatamodel extends LazyDataModel<ProjectImplementationPlan> {

	private static final long serialVersionUID = 1L;
	private List<ProjectImplementationPlan> retorno = new ArrayList<ProjectImplementationPlan>();
	private ProjectImplementationPlanService service = new ProjectImplementationPlanService();
	private DgAllocationParentService dgAllocationParentService = new DgAllocationParentService();
	private ActiveContractsService activeContractsService = new ActiveContractsService();
	private RegionTownService regionTownService = new RegionTownService();
	private SDFCompanyService sdfCompanyService = new SDFCompanyService();
	
	public ProjectImplementationPlanDatamodel() {
		super();
	}

	@Override
	public List<ProjectImplementationPlan> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = service.sortAndFilterSearch(ProjectImplementationPlan.class, first, pageSize, sortField, sortOrder, filters);
			for (ProjectImplementationPlan projectImplementationPlan : retorno) {
				DgAllocationParent dgAllocationParent = dgAllocationParentService.findByWSP(projectImplementationPlan.getWsp().getId());
				projectImplementationPlan.setDgAllocationParent(dgAllocationParent);
				projectImplementationPlan.setCloUser(getCLO(projectImplementationPlan.getWsp()));
				projectImplementationPlan.setSdfCompany(sdfCompanyService.locateFirstPrimarySDF(projectImplementationPlan.getWsp().getCompany()));
				List<SDFCompany>list = sdfCompanyService.findByCompanyAndSdfType(projectImplementationPlan.getWsp().getCompany(), (long) 5);
				if(list.size()>0) {
					projectImplementationPlan.setSecondarySdfCompany(list.get(0));
				}
				if(dgAllocationParent != null) {
					projectImplementationPlan.setActiveContracts(activeContractsService.findByDgAllocationParent(dgAllocationParent.getId()));
					RegionTown rt = regionTownService.findByTown(dgAllocationParent.getWsp().getCompany().getResidentialAddress().getTown());
					projectImplementationPlan.getDgAllocationParent().getWsp().getCompany().setRegionTown(rt);
				}
			}
			setRowCount(service.countSearch(ProjectImplementationPlan.class, filters));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(ProjectImplementationPlan obj) {
		return obj.getId();
	}

	@Override
	public ProjectImplementationPlan getRowData(String rowKey) {
		for (ProjectImplementationPlan obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}
	
	public Users getCLO(Wsp wsp) throws Exception {
		RegionTown rt = RegionTownService.instance().findByTown(wsp.getCompany().getResidentialAddress().getTown());
		Users cloUser = rt.getClo().getUsers();
		return cloUser;
	}

}
