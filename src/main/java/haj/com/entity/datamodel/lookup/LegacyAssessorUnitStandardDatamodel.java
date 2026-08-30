package haj.com.entity.datamodel.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.LegacyAssessorUnitStandard;
import haj.com.service.lookup.LegacyAssessorUnitStandardService;

public class LegacyAssessorUnitStandardDatamodel extends LazyDataModel<LegacyAssessorUnitStandard> {

	private static final long serialVersionUID = 1L;
	private List<LegacyAssessorUnitStandard> retorno = new ArrayList<LegacyAssessorUnitStandard>();
	private LegacyAssessorUnitStandardService service = new LegacyAssessorUnitStandardService();

	public LegacyAssessorUnitStandardDatamodel() {
		super();
	}

	@Override
	public List<LegacyAssessorUnitStandard> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = service.allLegacyAssessorUnitStandard(LegacyAssessorUnitStandard.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.count(LegacyAssessorUnitStandard.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(LegacyAssessorUnitStandard obj) {
		return obj.getId();
	}

	@Override
	public LegacyAssessorUnitStandard getRowData(String rowKey) {
		for (LegacyAssessorUnitStandard obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
