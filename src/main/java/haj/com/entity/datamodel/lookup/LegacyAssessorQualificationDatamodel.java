package haj.com.entity.datamodel.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.LegacyAssessorQualification;
import haj.com.service.lookup.LegacyAssessorQualificationService;

public class LegacyAssessorQualificationDatamodel extends LazyDataModel<LegacyAssessorQualification> {

	private static final long serialVersionUID = 1L;
	private List<LegacyAssessorQualification> retorno = new ArrayList<LegacyAssessorQualification>();
	private LegacyAssessorQualificationService service = new LegacyAssessorQualificationService();

	public LegacyAssessorQualificationDatamodel() {
		super();
	}

	@Override
	public List<LegacyAssessorQualification> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = service.allLegacyAssessorQualification(LegacyAssessorQualification.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.count(LegacyAssessorQualification.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(LegacyAssessorQualification obj) {
		return obj.getId();
	}

	@Override
	public LegacyAssessorQualification getRowData(String rowKey) {
		for (LegacyAssessorQualification obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
