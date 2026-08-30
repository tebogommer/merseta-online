package haj.com.entity.datamodel.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.LegacySkillsProgrammeAssessments;
import haj.com.service.lookup.LegacySkillsProgrammeAssessmentsService;

public class LegacySkillsProgrammeAssessmentsDatamodel extends LazyDataModel<LegacySkillsProgrammeAssessments> {

	private static final long serialVersionUID = 1L;
	private List<LegacySkillsProgrammeAssessments> retorno = new ArrayList<LegacySkillsProgrammeAssessments>();
	private LegacySkillsProgrammeAssessmentsService service = new LegacySkillsProgrammeAssessmentsService();

	public LegacySkillsProgrammeAssessmentsDatamodel() {
		super();
	}

	@Override
	public List<LegacySkillsProgrammeAssessments> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = service.allLegacySkillsProgrammeAssessments(LegacySkillsProgrammeAssessments.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.count(LegacySkillsProgrammeAssessments.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(LegacySkillsProgrammeAssessments obj) {
		return obj.getId();
	}

	@Override
	public LegacySkillsProgrammeAssessments getRowData(String rowKey) {
		for (LegacySkillsProgrammeAssessments obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
