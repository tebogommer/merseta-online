package haj.com.entity.datamodel.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.HomeAffairs;
import haj.com.service.lookup.HomeAffairsService;

public class HomeAffairsDatamodel extends LazyDataModel<HomeAffairs> {

	private static final long serialVersionUID = 1L;
	private List<HomeAffairs> retorno = new ArrayList<HomeAffairs>();
	private HomeAffairsService service = new HomeAffairsService();

	public HomeAffairsDatamodel() {
		super();
	}

	@Override
	public List<HomeAffairs> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = service.allHomeAffairs(HomeAffairs.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.count(HomeAffairs.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(HomeAffairs obj) {
		return obj.getId();
	}

	@Override
	public HomeAffairs getRowData(String rowKey) {
		for (HomeAffairs obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
