package haj.com.entity.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.PaymentRequest;
import haj.com.service.PaymentRequestService;

public class PaymentRequestDatamodel extends LazyDataModel<PaymentRequest> {

	private static final long serialVersionUID = 1L;
	private List<PaymentRequest> retorno = new ArrayList<PaymentRequest>();
	private PaymentRequestService service = new PaymentRequestService();

	public PaymentRequestDatamodel() {
		super();
	}

	@Override
	public List<PaymentRequest> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = service.allPaymentRequest(PaymentRequest.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.count(PaymentRequest.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(PaymentRequest obj) {
		return obj.getId();
	}

	@Override
	public PaymentRequest getRowData(String rowKey) {
		for (PaymentRequest obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
