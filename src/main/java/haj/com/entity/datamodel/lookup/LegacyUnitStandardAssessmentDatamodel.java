package haj.com.entity.datamodel.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.LegacyUnitStandardAssessment;
import haj.com.service.lookup.LegacyUnitStandardAssessmentService;

public class LegacyUnitStandardAssessmentDatamodel extends LazyDataModel<LegacyUnitStandardAssessment> {

	private static final long serialVersionUID = 1L;
	private List<LegacyUnitStandardAssessment> retorno = new ArrayList<LegacyUnitStandardAssessment>();
	private LegacyUnitStandardAssessmentService service = new LegacyUnitStandardAssessmentService();

	public LegacyUnitStandardAssessmentDatamodel() {
		super();
	}

	@Override
	public List<LegacyUnitStandardAssessment> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = service.allLegacyUnitStandardAssessment(LegacyUnitStandardAssessment.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.count(LegacyUnitStandardAssessment.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(LegacyUnitStandardAssessment obj) {
		return obj.getId();
	}

	@Override
	public LegacyUnitStandardAssessment getRowData(String rowKey) {
		for (LegacyUnitStandardAssessment obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
