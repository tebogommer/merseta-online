package haj.com.entity.datamodel.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.DisabilityRating;
import haj.com.service.lookup.DisabilityRatingService;

public class DisabilityRatingDatamodel extends LazyDataModel<DisabilityRating> {

	private static final long serialVersionUID = 1L;
	private List<DisabilityRating> retorno = new ArrayList<DisabilityRating>();
	private DisabilityRatingService service = new DisabilityRatingService();

	public DisabilityRatingDatamodel() {
		super();
	}

	@Override
	public List<DisabilityRating> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = service.allDisabilityRating(DisabilityRating.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.count(DisabilityRating.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(DisabilityRating obj) {
		return obj.getId();
	}

	@Override
	public DisabilityRating getRowData(String rowKey) {
		for (DisabilityRating obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
