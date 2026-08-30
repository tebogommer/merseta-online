package haj.com.entity.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.LegacyReporting;
import haj.com.service.LegacyReportingService;

public class LegacyReportingDatamodel extends LazyDataModel<LegacyReporting> {

	private static final long serialVersionUID = 1L;
	private List<LegacyReporting> retorno = new ArrayList<LegacyReporting>();
	private LegacyReportingService service = new LegacyReportingService();

	public LegacyReportingDatamodel() {
		super();
	}

	@Override
	public List<LegacyReporting> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = service.allLegacyReporting(LegacyReporting.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.count(LegacyReporting.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(LegacyReporting obj) {
		return obj.getId();
	}

	@Override
	public LegacyReporting getRowData(String rowKey) {
		for (LegacyReporting obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
