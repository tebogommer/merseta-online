import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;

import haj.com.gptransations.ArrayOfInfo;
import haj.com.gptransations.TransictionConverter;
import haj.com.gptransations.TransictionConverterSoap;
import haj.com.utils.MyServiceLogHandler;

public class GPTranTest {

	public static void main(String[] args) {
		try {
			TransictionConverter tc = new TransictionConverter();
			TransictionConverterSoap soap = tc.getTransictionConverterSoap();
			
			// ONLY FOR DEBUGGING
			BindingProvider bindProv = (BindingProvider) soap;
			java.util.List<Handler> handlers = bindProv.getBinding().getHandlerChain();
			handlers.add(new MyServiceLogHandler());
			bindProv.getBinding().setHandlerChain(handlers);
			
//			ArrayOfInfo ai = soap.getInfo("aa", "aa", "vendid");
//			ai.getInfo().forEach(a-> {
//			  System.out.println(a.getDocDescription());
//			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
