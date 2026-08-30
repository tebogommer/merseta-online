package haj.com.entity.datamodel.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.StakeholderRelationsSurvey;
import haj.com.service.lookup.StakeholderRelationsSurveyService;

public class StakeholderRelationsSurveyDatamodel extends LazyDataModel<StakeholderRelationsSurvey> {

	private static final long serialVersionUID = 1L;
	private List<StakeholderRelationsSurvey> retorno = new ArrayList<StakeholderRelationsSurvey>();
	private StakeholderRelationsSurveyService service = new StakeholderRelationsSurveyService();

	public StakeholderRelationsSurveyDatamodel() {
		super();
	}

	@Override
	public List<StakeholderRelationsSurvey> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = service.allStakeholderRelationsSurvey(StakeholderRelationsSurvey.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.count(StakeholderRelationsSurvey.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(StakeholderRelationsSurvey obj) {
		return obj.getId();
	}

	@Override
	public StakeholderRelationsSurvey getRowData(String rowKey) {
		for (StakeholderRelationsSurvey obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
