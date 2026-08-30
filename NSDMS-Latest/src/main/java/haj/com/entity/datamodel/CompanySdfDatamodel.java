package haj.com.entity.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.RejectReasonsChild;
import haj.com.entity.SDFCompany;
import haj.com.entity.TrainingProviderVerfication;
import haj.com.entity.enums.CompanyStatusEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.service.RejectReasonsChildService;
import haj.com.service.SDFCompanyService;
import haj.com.service.lookup.RegionTownService;
import haj.com.service.lookup.RejectReasonsService;

public class CompanySdfDatamodel extends LazyDataModel<SDFCompany> {

	private static final long serialVersionUID = 1L;
	private List<SDFCompany> retorno = new ArrayList<SDFCompany>();
	private SDFCompanyService service = new SDFCompanyService();

	public CompanySdfDatamodel() {
		super();
	}

	@Override
	public List<SDFCompany> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			
			retorno = service.allSDFCompanySearch(SDFCompany.class, first, pageSize, sortField, sortOrder, filters);
			service.resolveSizeOfCompanyWithRegion(retorno);
			service.resolveSDFs(retorno);
			for(SDFCompany sDFCompany : retorno) {
				if(sDFCompany.getCompany().getCompanyStatus() == CompanyStatusEnum.Rejected || sDFCompany.getCompany().getCompanyStatus() == CompanyStatusEnum.Pending) {
					populateRejectReasons(sDFCompany);
				}else {
					sDFCompany.setRejectReason("N/A");
				}
			}
			setRowCount(service.countSearch(SDFCompany.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	private void populateRejectReasons(SDFCompany sDFCompany) throws Exception {
		//RejectReasonsService rs= new RejectReasonsService();
		RejectReasonsChildService rejectReasonsService = new RejectReasonsChildService();
		List<RejectReasonsChild> list=new ArrayList<>();
		//list = rs.locateReasonsSelectedByTargetKeyClassAndProcess(sDFCompany.getId(), SDFCompany.class.getName(), ConfigDocProcessEnum.SDF);
		//list = rejectReasonsService.findBySDF(sDFCompany.getSdf());
		list = rejectReasonsService.findByCompany(sDFCompany.getCompany());
		String results = "";
		int count = 1;
		for (RejectReasonsChild rejectReasons : list) {
			if (count == list.size()) {
				if (rejectReasons != null && rejectReasons.getRejectReasons() != null) {
					results += rejectReasons.getRejectReasons().getDescription();
				}
			} else {
				if (rejectReasons != null && rejectReasons.getRejectReasons() != null) {
					results += rejectReasons.getRejectReasons().getDescription() + ", ";
				}
			}
			count++;
		}
		if (results != "") {
			sDFCompany.setRejectReason(results);
		} else {
			sDFCompany.setRejectReason("None Found");
		}
	}

	@Override
	public Object getRowKey(SDFCompany obj) {
		return obj.getId();
	}

	@Override
	public SDFCompany getRowData(String rowKey) {
		for (SDFCompany obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
