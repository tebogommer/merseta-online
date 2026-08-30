package haj.com.entity.datamodel.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.LegacyAssessorAccreditation;
import haj.com.service.lookup.LegacyAssessorAccreditationService;

public class LegacyAssessorAccreditationDatamodel extends LazyDataModel<LegacyAssessorAccreditation> {

	private static final long serialVersionUID = 1L;
	private List<LegacyAssessorAccreditation> retorno = new ArrayList<LegacyAssessorAccreditation>();
	private LegacyAssessorAccreditationService service = new LegacyAssessorAccreditationService();

	public LegacyAssessorAccreditationDatamodel() {
		super();
	}

	@Override
	public List<LegacyAssessorAccreditation> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = service.allLegacyAssessorAccreditation(LegacyAssessorAccreditation.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.count(LegacyAssessorAccreditation.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(LegacyAssessorAccreditation obj) {
		return obj.getId();
	}

	@Override
	public LegacyAssessorAccreditation getRowData(String rowKey) {
		for (LegacyAssessorAccreditation obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
