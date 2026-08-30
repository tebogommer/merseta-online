package haj.com.entity.datamodel.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.LegacySkillsProgramme;
import haj.com.service.lookup.LegacySkillsProgrammeService;

public class LegacySkillsProgrammeDatamodel extends LazyDataModel<LegacySkillsProgramme> {

	private static final long serialVersionUID = 1L;
	private List<LegacySkillsProgramme> retorno = new ArrayList<LegacySkillsProgramme>();
	private LegacySkillsProgrammeService service = new LegacySkillsProgrammeService();

	public LegacySkillsProgrammeDatamodel() {
		super();
	}

	@Override
	public List<LegacySkillsProgramme> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = service.allLegacySkillsProgramme(LegacySkillsProgramme.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.count(LegacySkillsProgramme.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(LegacySkillsProgramme obj) {
		return obj.getId();
	}

	@Override
	public LegacySkillsProgramme getRowData(String rowKey) {
		for (LegacySkillsProgramme obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
