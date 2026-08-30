package haj.com.entity.datamodel.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.PreviousSchools;
import haj.com.service.lookup.PreviousSchoolsService;

public class PreviousSchoolsDatamodel extends LazyDataModel<PreviousSchools> {

	private static final long serialVersionUID = 1L;
	private List<PreviousSchools> retorno = new ArrayList<PreviousSchools>();
	private PreviousSchoolsService service = new PreviousSchoolsService();

	public PreviousSchoolsDatamodel() {
		super();
	}

	@Override
	public List<PreviousSchools> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = service.allPreviousSchools(PreviousSchools.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.count(PreviousSchools.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(PreviousSchools obj) {
		return obj.getId();
	}

	@Override
	public PreviousSchools getRowData(String rowKey) {
		for (PreviousSchools obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
