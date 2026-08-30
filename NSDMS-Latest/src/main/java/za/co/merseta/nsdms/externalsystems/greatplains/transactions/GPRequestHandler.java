package za.co.merseta.nsdms.externalsystems.greatplains.transactions;

import java.net.URL;
import java.util.Map;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.MessageContext;

import org.springframework.core.io.ClassPathResource;
import haj.com.gptransations.TransictionConverter;
import haj.com.gptransations.TransictionConverterSoap;
import haj.com.service.JAXBService;
import za.co.merseta.nsdms.framework.configuration.NSDMSConfiguration;
import za.co.merseta.nsdms.framework.exception.BusinessException;
import za.co.merseta.nsdms.framework.exception.EntityNotFoundException;
import za.co.merseta.nsdms.framework.exception.NSDMSException;
import za.co.merseta.nsdms.framework.exception.RequestTimeOutException;
import za.co.merseta.nsdms.framework.exception.TechnicalException;
import za.co.merseta.nsdms.framework.exception.ValidationException;
import za.co.merseta.nsdms.framework.logging.NSDMSLogger;
import za.co.merseta.nsdms.framework.logging.PayloadLoggerSOAPHandler;

public abstract class GPRequestHandler<T, V> {
    protected String username = NSDMSConfiguration.getString("gp.transactions.username");
    protected String password = NSDMSConfiguration.getString("gp.transactions.password");
    private String enddpoint = NSDMSConfiguration.getString("gp.transactions.url");
    private int connectionTimOutInMillis = NSDMSConfiguration.getInt("gp.transactions.connection.timeout.millis");
    private int requestTimeoutInMillis;
    private String CONNECT_TIMEOUT = "com.sun.xml.internal.ws.connect.timeout";
    private String REQUEST_TIMEOUT = "com.sun.xml.internal.ws.request.timeout";
    private T request;
    private final static String WSDL_PATH = "wsdl/gptransactions.wsdl";
    private static final String CATALINA_BASE_PROPERTY = "catalina.base";
    private static final NSDMSLogger logger = NSDMSLogger.getLogger(GPRequestHandler.class);
	private TransictionConverter transictionConverter;
	private TransictionConverterSoap transictionConverterSoap;    

    public GPRequestHandler(int requestTimeoutInMillis) {
        logger.info("GPRequestHandler() - {}", "START");
        logger.debug("Parameters Received \n \t requestTimeoutInMillis ={}", requestTimeoutInMillis);
        this.requestTimeoutInMillis = requestTimeoutInMillis;
    }

    private void init() throws TechnicalException {
        logger.info("init() - {}", "START");
        logger.debug("username={}", username);
        logger.debug("password starts-with [{}] and ends-with [{}]", () -> password.substring(0, 2),
                () -> password.substring(password.length() - 2));
        logger.debug("url={}", enddpoint);
        logger.debug("connectionTimeout={} milliseconds", connectionTimOutInMillis);
        logger.debug("requestTimeout={} milliseconds", requestTimeoutInMillis);

        transictionConverter = new TransictionConverter(getWSDLURL());
        transictionConverterSoap = transictionConverter.getTransictionConverterSoap();

        Map<String, Object> req_ctx = ((BindingProvider) transictionConverterSoap).getRequestContext();
        req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, enddpoint);
        req_ctx.put(CONNECT_TIMEOUT, this.connectionTimOutInMillis);
        req_ctx.put(REQUEST_TIMEOUT, this.requestTimeoutInMillis);

        BindingProvider bindProv = (BindingProvider) this.transictionConverterSoap;
        @SuppressWarnings("rawtypes")
        java.util.List<Handler> handlers = bindProv.getBinding().getHandlerChain();
        handlers.add(new PayloadLoggerSOAPHandler());
        bindProv.getBinding().setHandlerChain(handlers);

        logger.info("init() - {}", "END");
    }

    protected TransictionConverterSoap getGPProxy() {
        return transictionConverterSoap;
    }

    protected T getRequest() {
        return this.request;
    }

    protected abstract T createGPRequest();

    protected abstract V callGP() throws Exception;

    protected final V processRequest()
            throws ValidationException, BusinessException, TechnicalException, RequestTimeOutException {
        logger.info("processRequest() - {}", "START");
        V response = null;

        try {
            validate();
            init();
            this.request = createGPRequest();
            logger.trace("Request generated for the GP call is \n {}",
                    () -> JAXBService.marshallToStringNoException(this.request));
            response = callGP();
        } catch (Exception e) {
            logger.error(String.format("Received an exception %s:%s", e.getClass(), e.getMessage()), e);
            try {
                handleException(e);
            } catch (BusinessException | TechnicalException | RequestTimeOutException handledNSDMSException) {
                logger.error(
                        String.format("The exception was handled, and converted into %s:%s",
                                handledNSDMSException.getClass(), handledNSDMSException.getMessage()),
                        handledNSDMSException);
                try {
                    customiseNSDMSException(handledNSDMSException);
                    throw handledNSDMSException;
                } catch (BusinessException | TechnicalException | RequestTimeOutException customisedException) {
                    logger.error(String.format("The exception was customised and converted to %s:%s",customisedException.getClass(),customisedException.getMessage()),customisedException);
                    throw customisedException;
                }
            }
        }

        final V printedResponse = response;
        logger.trace("Response Returned \n {}", () -> JAXBService.marshallToStringNoException(printedResponse));
        logger.info("processRequest() - {}", "END");
        return response;
    }

    private String getSOAPFaultString(String message) {
        logger.info("getSOAPFaultString() - {}", "START");

        logger.debug("Parameters Received \n \t message ={}", message);
        if (message != null && message.length() > 0) {
            message = message.replaceFirst("Client received SOAP Fault from server:", "");
            message = message.replaceAll(
                    "Please see the server log to find more detail regarding exact cause of the failure.", "");
        }

        logger.debug("Response Returned \n {}", message);
        logger.info("getSOAPFaultString() - {}", "END");
        return message;
    }

    protected Exception overrideException(Exception e) {
        logger.info("overrideException()");
        return e;
    }

    private URL getWSDLURL() throws TechnicalException {
        logger.info("getWSDLURL() - {}", "START");
        URL wsdlURL = null;

        try {
            wsdlURL = new ClassPathResource(WSDL_PATH).getURL();
        } catch (Exception exception) {
            String wsdlFilePath = null;
            logger.error("Failed to get WSDL from classpath location {}", WSDL_PATH);
            try {
                wsdlFilePath = String.format("file://%s/webapps/ROOT/wsdl/DynamicsGP.wsdl",
                        System.getProperty(CATALINA_BASE_PROPERTY),
                        WSDL_PATH);
                wsdlURL = new URL(wsdlFilePath);
            } catch (Exception innerException) {
                throw new TechnicalException(
                        String.format("Failed to get the WSDL from [%s]. Original error was [%s:%s]",
                                wsdlFilePath, innerException.getClass(), innerException.getMessage()),
                        innerException);
            }
        }
        URL printedString = wsdlURL;
        logger.debug("Response Returned \n {}", printedString.toString());
        logger.info("getWSDLURL() - {}", "END");
        return wsdlURL;
    }

    private void handleException(Exception exception)
            throws ValidationException,BusinessException, TechnicalException, RequestTimeOutException {
        logger.info("handleException() - {}", "START");

        if (exception instanceof javax.xml.ws.soap.SOAPFaultException) {
            if (exception.getMessage() != null && exception.getMessage().contains("Business object not found.")) {
                logger.debug("Converting exception to EntityNotFoundException");
                throw new EntityNotFoundException(getSOAPFaultString(exception.getMessage()), exception);
            }else if(exception.getMessage() != null && exception.getMessage().contains("A validation exception has occurred")){
                logger.debug("Converting exception to ValidationException");
                throw new ValidationException(getSOAPFaultString(exception.getMessage()), exception);
            } else {
                logger.debug("Converting exception to BusinessException");
                throw new BusinessException(getSOAPFaultString(exception.getMessage()), exception);
            }
        } else if (exception instanceof javax.xml.ws.WebServiceException) {
            if (exception.getMessage() != null
                    && exception.getMessage().contains("java.net.SocketTimeoutException: Read timed out")) {
                logger.debug("Converting exception to RequestTimeOutException");
                throw new RequestTimeOutException(exception.getMessage(), exception);
            } else {
                logger.debug("Converting exception to TechnicalException");
                throw new TechnicalException(
                        String.format("%s:%s", exception.getClass(), exception.getMessage()), exception);
            }
        } else {
            logger.debug("Converting exception to TechnicalException");
            throw new TechnicalException(
                    String.format("%s:%s", exception.getClass(), exception.getMessage()), exception);
        }
    }

    protected void customiseNSDMSException(NSDMSException exception)
            throws BusinessException, TechnicalException, RequestTimeOutException {
    }

    protected void validate() throws ValidationException {
    }

    protected void downCastException(NSDMSException exception)
            throws BusinessException, TechnicalException, RequestTimeOutException {
        logger.info("downCastException() - {}");    

        if (exception instanceof BusinessException) {
            throw (BusinessException) exception;
        } else if (exception instanceof TechnicalException) {
            throw (TechnicalException) exception;
        } else if (exception instanceof RequestTimeOutException) {
            throw (RequestTimeOutException) exception;
        }
    }
}