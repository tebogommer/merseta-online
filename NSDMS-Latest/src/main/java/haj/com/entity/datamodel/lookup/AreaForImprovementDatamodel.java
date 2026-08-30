package haj.com.entity.datamodel.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.AreaForImprovement;
import haj.com.service.lookup.AreaForImprovementService;

public class AreaForImprovementDatamodel extends LazyDataModel<AreaForImprovement> {

	private static final long serialVersionUID = 1L;
	private List<AreaForImprovement> retorno = new ArrayList<AreaForImprovement>();
	private AreaForImprovementService service = new AreaForImprovementService();

	public AreaForImprovementDatamodel() {
		super();
	}

	@Override
	public List<AreaForImprovement> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = service.allAreaForImprovement(AreaForImprovement.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.count(AreaForImprovement.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(AreaForImprovement obj) {
		return obj.getId();
	}

	@Override
	public AreaForImprovement getRowData(String rowKey) {
		for (AreaForImprovement obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
