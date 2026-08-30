package haj.com.entity.datamodel.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.LearnershipUnitStandards;
import haj.com.service.lookup.LearnershipUnitStandardsService;

public class LearnershipUnitStandardsDatamodel extends LazyDataModel<LearnershipUnitStandards> {

	private static final long serialVersionUID = 1L;
	private List<LearnershipUnitStandards> retorno = new ArrayList<LearnershipUnitStandards>();
	private LearnershipUnitStandardsService service = new LearnershipUnitStandardsService();

	public LearnershipUnitStandardsDatamodel() {
		super();
	}

	@Override
	public List<LearnershipUnitStandards> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = service.allLearnershipUnitStandards(LearnershipUnitStandards.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.count(LearnershipUnitStandards.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(LearnershipUnitStandards obj) {
		return obj.getId();
	}

	@Override
	public LearnershipUnitStandards getRowData(String rowKey) {
		for (LearnershipUnitStandards obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
