package haj.com.entity.datamodel.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.LegacyAssessorLearnership;
import haj.com.service.lookup.LegacyAssessorLearnershipService;

public class LegacyAssessorLearnershipDatamodel extends LazyDataModel<LegacyAssessorLearnership> {

	private static final long serialVersionUID = 1L;
	private List<LegacyAssessorLearnership> retorno = new ArrayList<LegacyAssessorLearnership>();
	private LegacyAssessorLearnershipService service = new LegacyAssessorLearnershipService();

	public LegacyAssessorLearnershipDatamodel() {
		super();
	}

	@Override
	public List<LegacyAssessorLearnership> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = service.allLegacyAssessorLearnership(LegacyAssessorLearnership.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.count(LegacyAssessorLearnership.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(LegacyAssessorLearnership obj) {
		return obj.getId();
	}

	@Override
	public LegacyAssessorLearnership getRowData(String rowKey) {
		for (LegacyAssessorLearnership obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
