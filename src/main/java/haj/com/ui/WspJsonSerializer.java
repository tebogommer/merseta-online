package haj.com.ui;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import haj.com.entity.Wsp;


public class WspJsonSerializer  extends JsonSerializer<Wsp> {

	private static ThreadLocal<Integer> depth = new ThreadLocal<Integer>() {

		@Override
		protected Integer initialValue() {
			return 0;
		}

	};

	@Override
	public void serialize(Wsp value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {

		depth.set(depth.get() + 1);
		try {
			if (depth.get() > 2) {
				jgen.writeNull();
			} else {

				jgen.writeStartObject();
				jgen.writeStringField("companyGuid", value.getCompany().getCompanyGuid());
				jgen.writeStringField("companyName", value.getCompany().getCompanyName());

/*				Wsp createdByUser = value.getCreatedBy();
				if (null != createdByUser) {
					jgen.writeStringField(
							"createdBy",
							createdByUser.getFirstName() + " "
									+ createdByUser.getLastName());
				}

				User lastModifiedByUser = value.getLastModifiedBy();
				if (null != lastModifiedByUser) {
					jgen.writeStringField("lastModifiedBy",
							lastModifiedByUser.getFirstName() + " "
									+ lastModifiedByUser.getLastName());
				}
*/
				jgen.writeEndObject();

				return;

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
