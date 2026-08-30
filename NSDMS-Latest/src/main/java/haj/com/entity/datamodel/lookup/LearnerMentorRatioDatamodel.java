package haj.com.entity.datamodel.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.LearnerMentorRatio;
import haj.com.service.lookup.LearnerMentorRatioService;

public class LearnerMentorRatioDatamodel extends LazyDataModel<LearnerMentorRatio> {

	private static final long serialVersionUID = 1L;
	private List<LearnerMentorRatio> retorno = new ArrayList<LearnerMentorRatio>();
	private LearnerMentorRatioService service = new LearnerMentorRatioService();

	public LearnerMentorRatioDatamodel() {
		super();
	}

	@Override
	public List<LearnerMentorRatio> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = service.allLearnerMentorRatio(LearnerMentorRatio.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.count(LearnerMentorRatio.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(LearnerMentorRatio obj) {
		return obj.getId();
	}

	@Override
	public LearnerMentorRatio getRowData(String rowKey) {
		for (LearnerMentorRatio obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
