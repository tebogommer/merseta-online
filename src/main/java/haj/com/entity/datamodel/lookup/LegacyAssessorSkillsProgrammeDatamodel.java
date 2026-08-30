package haj.com.entity.datamodel.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.LegacyAssessorSkillsProgramme;
import haj.com.service.lookup.LegacyAssessorSkillsProgrammeService;

public class LegacyAssessorSkillsProgrammeDatamodel extends LazyDataModel<LegacyAssessorSkillsProgramme> {

	private static final long serialVersionUID = 1L;
	private List<LegacyAssessorSkillsProgramme> retorno = new ArrayList<LegacyAssessorSkillsProgramme>();
	private LegacyAssessorSkillsProgrammeService service = new LegacyAssessorSkillsProgrammeService();

	public LegacyAssessorSkillsProgrammeDatamodel() {
		super();
	}

	@Override
	public List<LegacyAssessorSkillsProgramme> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = service.allLegacyAssessorSkillsProgramme(LegacyAssessorSkillsProgramme.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.count(LegacyAssessorSkillsProgramme.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(LegacyAssessorSkillsProgramme obj) {
		return obj.getId();
	}

	@Override
	public LegacyAssessorSkillsProgramme getRowData(String rowKey) {
		for (LegacyAssessorSkillsProgramme obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
