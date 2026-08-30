package haj.com.entity.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.AdministrationOfAQP;
import haj.com.entity.Users;
import haj.com.service.AdministrationOfAQPService;

public class AdministrationOfAQPModel extends LazyDataModel<AdministrationOfAQP> {
	private static final long serialVersionUID = 1L;
	private List<AdministrationOfAQP> retorno = new ArrayList<AdministrationOfAQP>();
	private AdministrationOfAQPService administrationOfAQPService = new AdministrationOfAQPService();
	private Users u;

	public AdministrationOfAQPModel(Users u) {
		super();
		this.u = u;
	}

	@Override
	public List<AdministrationOfAQP> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = administrationOfAQPService.allAdministrationOfAQP(u, first, pageSize, sortField, sortOrder, filters);
			setRowCount(administrationOfAQPService.count(u, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(AdministrationOfAQP obj) {
		return obj.getId();
	}

	@Override
	public AdministrationOfAQP getRowData(String rowKey) {
		for (AdministrationOfAQP obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
