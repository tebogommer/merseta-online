package haj.com.entity.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.CompanyUsers;
import haj.com.entity.Users;
import haj.com.service.CompanyUsersService;
import haj.com.service.lookup.RegionTownService;

public class CompanyUsersDatamodel extends LazyDataModel<CompanyUsers> {

	private static final long serialVersionUID = 1L;
	private List<CompanyUsers> retorno = new ArrayList<CompanyUsers>();
	private CompanyUsersService service = new CompanyUsersService();
	private Users u;

	public CompanyUsersDatamodel(Users u) {
		super();
		this.u=u;
	}

	@Override
	public List<CompanyUsers> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			filters.put("user", u);
			retorno = (List<CompanyUsers>) service.sortAndFilterSearch(CompanyUsers.class, first, pageSize, sortField, sortOrder, filters);
			service.resolveUsersPosition(retorno);			
			setRowCount(service.countSearch(CompanyUsers.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(CompanyUsers obj) {
		return obj.getId();
	}

	@Override
	public CompanyUsers getRowData(String rowKey) {
		for (CompanyUsers obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
