package haj.com.entity.datamodel.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.LegacyUnitStandard;
import haj.com.service.lookup.LegacyUnitStandardService;

public class LegacyUnitStandardDatamodel extends LazyDataModel<LegacyUnitStandard> {

	private static final long serialVersionUID = 1L;
	private List<LegacyUnitStandard> retorno = new ArrayList<LegacyUnitStandard>();
	private LegacyUnitStandardService service = new LegacyUnitStandardService();

	public LegacyUnitStandardDatamodel() {
		super();
	}

	@Override
	public List<LegacyUnitStandard> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = service.allLegacyUnitStandard(LegacyUnitStandard.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.count(LegacyUnitStandard.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(LegacyUnitStandard obj) {
		return obj.getId();
	}

	@Override
	public LegacyUnitStandard getRowData(String rowKey) {
		for (LegacyUnitStandard obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
