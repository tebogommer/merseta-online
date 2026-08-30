package haj.com.entity.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.LegacyReportingParams;
import haj.com.service.LegacyReportingParamsService;

public class LegacyReportingParamsDatamodel extends LazyDataModel<LegacyReportingParams> {

	private static final long serialVersionUID = 1L;
	private List<LegacyReportingParams> retorno = new ArrayList<LegacyReportingParams>();
	private LegacyReportingParamsService service = new LegacyReportingParamsService();

	public LegacyReportingParamsDatamodel() {
		super();
	}

	@Override
	public List<LegacyReportingParams> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = service.allLegacyReportingParams(LegacyReportingParams.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.count(LegacyReportingParams.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(LegacyReportingParams obj) {
		return obj.getId();
	}

	@Override
	public LegacyReportingParams getRowData(String rowKey) {
		for (LegacyReportingParams obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
