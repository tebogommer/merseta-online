package haj.com.entity.datamodel.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.LegacyTvet;
import haj.com.service.lookup.LegacyTvetService;

public class LegacyTvetDatamodel extends LazyDataModel<LegacyTvet> {

	private static final long serialVersionUID = 1L;
	private List<LegacyTvet> retorno = new ArrayList<LegacyTvet>();
	private LegacyTvetService service = new LegacyTvetService();

	public LegacyTvetDatamodel() {
		super();
	}

	@Override
	public List<LegacyTvet> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = service.allLegacyTvet(LegacyTvet.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.count(LegacyTvet.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(LegacyTvet obj) {
		return obj.getId();
	}

	@Override
	public LegacyTvet getRowData(String rowKey) {
		for (LegacyTvet obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
