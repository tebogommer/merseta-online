package haj.com.entity.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import haj.com.entity.MgVerification;
import haj.com.entity.SDFCompany;
import haj.com.entity.Users;
import haj.com.entity.lookup.RegionTown;
import haj.com.service.MgVerificationService;
import haj.com.service.SDFCompanyService;
import haj.com.service.lookup.RegionTownService;

public class MGVerificationDataModel extends LazyDataModel<MgVerification> {

	private static final long serialVersionUID = 1L;
	private List<MgVerification> retorno = new ArrayList<MgVerification>();
	private MgVerificationService service = new MgVerificationService();
	private SDFCompanyService sdfCompanyService = new SDFCompanyService();

	public MGVerificationDataModel() {
		super();
	}

	@Override
	public List<MgVerification> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		try {
			//(Class<?> entity, int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters)
			retorno = service.sortAndFilterSearchMgVerification(MgVerification.class, first, pageSize, sortField, sortOrder, filters);
			for(MgVerification mgVerification: retorno) {
				populateCRM(mgVerification);
				populateSDF(mgVerification);
			}
			setRowCount(service.countSearchMgVerification(MgVerification.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(MgVerification obj) {
		return obj.getId();
	}

	@Override
	public MgVerification getRowData(String rowKey) {
		for (MgVerification obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

	private void populateSDF(MgVerification mgVerification) throws Exception {
		if(mgVerification != null && mgVerification.getWsp() != null && mgVerification.getWsp().getCompany() != null) {
			SDFCompany sdf = sdfCompanyService.findPrimarySDF(mgVerification.getWsp().getCompany());
			if (sdf != null) {
				mgVerification.setSdfUser(sdf.getSdf());
			}	
		}			
	}

	private void populateCRM(MgVerification mgVerification) throws Exception {
		if(mgVerification != null && mgVerification.getWsp() != null && mgVerification.getWsp().getCompany() != null && mgVerification.getWsp().getCompany().getResidentialAddress()!= null && mgVerification.getWsp().getCompany().getResidentialAddress().getTown()!=null) {
			RegionTown rt = RegionTownService.instance().findByTown(mgVerification.getWsp().getCompany().getResidentialAddress().getTown());
			Users crmUser = rt.getCrm().getUsers();
			if(crmUser !=null) {
				mgVerification.setCrmUser(crmUser);
			}else {
				mgVerification.setCrmUser(new Users());
			}
		}
	}
}
