package haj.com.entity.datamodel.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.NonCreditBearingIntervationTitle;
import haj.com.service.lookup.NonCreditBearingIntervationTitleService;

public class NonCreditBearingIntervationTitleDatamodel extends LazyDataModel<NonCreditBearingIntervationTitle> {

	private static final long serialVersionUID = 1L;
	private List<NonCreditBearingIntervationTitle> retorno = new ArrayList<NonCreditBearingIntervationTitle>();
	private NonCreditBearingIntervationTitleService service = new NonCreditBearingIntervationTitleService();

	public NonCreditBearingIntervationTitleDatamodel() {
		super();
	}

	@Override
	public List<NonCreditBearingIntervationTitle> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = service.allNonCreditBearingIntervationTitle(NonCreditBearingIntervationTitle.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.count(NonCreditBearingIntervationTitle.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(NonCreditBearingIntervationTitle obj) {
		return obj.getId();
	}

	@Override
	public NonCreditBearingIntervationTitle getRowData(String rowKey) {
		for (NonCreditBearingIntervationTitle obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
