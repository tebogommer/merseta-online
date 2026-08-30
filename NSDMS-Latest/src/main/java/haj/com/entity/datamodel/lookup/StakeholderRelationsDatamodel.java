package haj.com.entity.datamodel.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.StakeholderRelations;
import haj.com.service.lookup.StakeholderRelationsService;

public class StakeholderRelationsDatamodel extends LazyDataModel<StakeholderRelations> {

	private static final long serialVersionUID = 1L;
	private List<StakeholderRelations> retorno = new ArrayList<StakeholderRelations>();
	private StakeholderRelationsService service = new StakeholderRelationsService();

	public StakeholderRelationsDatamodel() {
		super();
	}

	@Override
	public List<StakeholderRelations> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		try {
			retorno = service.allStakeholderRelationsWhereUserNull(StakeholderRelations.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.countAllStakeholderRelationsWhereUserNull(StakeholderRelations.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(StakeholderRelations obj) {
		return obj.getId();
	}

	@Override
	public StakeholderRelations getRowData(String rowKey) {
		for (StakeholderRelations obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
