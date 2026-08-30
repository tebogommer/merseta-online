package haj.com.entity.datamodel.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.QualificationToolList;
import haj.com.service.lookup.QualificationToolListService;

public class QualificationToolListDatamodel extends LazyDataModel<QualificationToolList> {

	private static final long serialVersionUID = 1L;
	private List<QualificationToolList> retorno = new ArrayList<QualificationToolList>();
	private QualificationToolListService service = new QualificationToolListService();

	public QualificationToolListDatamodel() {
		super();
	}

	@Override
	public List<QualificationToolList> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = service.allQualificationToolList(QualificationToolList.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.count(QualificationToolList.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(QualificationToolList obj) {
		return obj.getId();
	}

	@Override
	public QualificationToolList getRowData(String rowKey) {
		for (QualificationToolList obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
