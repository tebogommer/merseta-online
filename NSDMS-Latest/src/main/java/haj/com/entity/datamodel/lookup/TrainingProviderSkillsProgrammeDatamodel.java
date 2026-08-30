package haj.com.entity.datamodel.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.TrainingProviderSkillsProgramme;
import haj.com.service.TrainingProviderSkillsProgrammeService;

public class TrainingProviderSkillsProgrammeDatamodel extends LazyDataModel<TrainingProviderSkillsProgramme> {

	private static final long serialVersionUID = 1L;
	private List<TrainingProviderSkillsProgramme> retorno = new ArrayList<TrainingProviderSkillsProgramme>();
	private TrainingProviderSkillsProgrammeService service = new TrainingProviderSkillsProgrammeService();

	public TrainingProviderSkillsProgrammeDatamodel() {
		super();
	}

	@Override
	public List<TrainingProviderSkillsProgramme> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = service.allTrainingProviderSkillsProgramme(TrainingProviderSkillsProgramme.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.count(TrainingProviderSkillsProgramme.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(TrainingProviderSkillsProgramme obj) {
		return obj.getId();
	}

	@Override
	public TrainingProviderSkillsProgramme getRowData(String rowKey) {
		for (TrainingProviderSkillsProgramme obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
