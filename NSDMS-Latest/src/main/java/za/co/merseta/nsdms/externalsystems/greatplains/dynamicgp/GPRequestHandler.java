package za.co.merseta.nsdms.externalsystems.greatplains.dynamicgp;

import java.net.Authenticator;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.MessageContext;

import org.springframework.core.io.ClassPathResource;

import com.microsoft.schemas.dynamics._2006._01.CompanyKey;
import com.microsoft.schemas.dynamics._2006._01.Context;
import com.microsoft.schemas.dynamics._2006._01.OrganizationKey;
import com.microsoft.schemas.dynamics.gp._2006._01.DynamicsGP;
import com.microsoft.schemas.dynamics.gp._2006._01.DynamicsGP_Service;
import com.microsoft.schemas.dynamics.gp._2006._01.Policy;
import com.microsoft.schemas.dynamics.gp._2006._01.PolicyKey;
import com.microsoft.schemas.dynamics.security._2006._01.NtlmAuthenticator;

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
    private int requestTimeoutInMillis;
    private static DynamicsGP dynamicsGP;
    private static Context context;
    public static int MERSETA_COMPANY_ID = 2;
    private String CONNECT_TIMEOUT = "com.sun.xml.internal.ws.connect.timeout";
    private String REQUEST_TIMEOUT = "com.sun.xml.internal.ws.request.timeout";
    private T request;
    private final static String[] WSDL_PATH_ARRAY = "wsdl/DynamicsGP.wsdl,WEB-INF/classes/wsdl/DynamicsGP.wsdl,/wsdl/DynamicsGP.wsdl,/WEB-INF/classes/wsdl/DynamicsGP.wsdl"
            .split(",");
    private static final String CATALINA_BASE_PROPERTY = "catalina.base";

    NSDMSLogger logger = NSDMSLogger.getLogger(GPRequestHandler.class);

    public GPRequestHandler(int requestTimeoutInMillis) {
        logger.info("GPRequestHandler() - {}", "START");
        logger.debug("Parameters Received \n \t requestTimeoutInMillis ={}", requestTimeoutInMillis);
        this.requestTimeoutInMillis = requestTimeoutInMillis;
    }

    private void init() throws TechnicalException {
        logger.info("init() - {}", "START");
        String username = NSDMSConfiguration.getString("gp.dynamics.username");
        String password = NSDMSConfiguration.getString("gp.dynamics.password");
        String enddpoint = NSDMSConfiguration.getString("gp.dynamics.url");
        int connectionTimOutInMillis = NSDMSConfiguration.getInt("gp.dynamics.connection.timeout.millis");

        logger.debug("username={}", username);
        logger.debug("password starts-with [{}] and ends-with [{}]", () -> password.substring(0, 2),
                () -> password.substring(password.length() - 2));
        logger.debug("url={}", enddpoint);
        logger.debug("connectionTimeout={} milliseconds", connectionTimOutInMillis);
        logger.debug("requestTimeout={} milliseconds", requestTimeoutInMillis);

        
        NtlmAuthenticator authenticator = new NtlmAuthenticator(username, password);
        Authenticator.setDefault(authenticator);
                
        if (dynamicsGP == null) {
            synchronized (GPRequestHandler.class) {
                if (dynamicsGP == null) {
                    DynamicsGP_Service service = new DynamicsGP_Service(getWSDLURL());
                    dynamicsGP = service.getLegacyDynamicsGP();

                    BindingProvider bindProv = (BindingProvider) dynamicsGP;
                    @SuppressWarnings("rawtypes")
                    java.util.List<Handler> handlers = bindProv.getBinding().getHandlerChain();
                    handlers.add(new PayloadLoggerSOAPHandler());
                    bindProv.getBinding().setHandlerChain(handlers);
                }
            }
        }

        Map<String, List<String>> headers = new HashMap<String, List<String>>();
        headers.put("Username", Collections.singletonList(username));
        headers.put("Password", Collections.singletonList(password));

        Map<String, Object> req_ctx = ((BindingProvider) dynamicsGP).getRequestContext();
        req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, enddpoint);
        req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
        req_ctx.put(CONNECT_TIMEOUT, connectionTimOutInMillis);
        req_ctx.put(REQUEST_TIMEOUT, requestTimeoutInMillis);

        if (context == null) {
            synchronized (GPRequestHandler.class) {
                if (context == null) {
                    initContextForCompany();
                }
            }
        }

        logger.info("init() - {}", "END");
    }

    protected DynamicsGP getGPProxy() {
        return dynamicsGP;
    }

    protected Context getContext() {
        return this.context;
    }

    protected T getRequest() {
        return this.request;
    }

    private void initContextForCompany() {
        logger.info("initContextForCompany() - {}", "START");
        context = new Context();
        context.setOrganizationKey(null);
        context.setCultureName("en-US");
        context.setOrganizationKey((OrganizationKey) new CompanyKey(MERSETA_COMPANY_ID));
        logger.trace("Response Returned \n {}", () -> JAXBService.marshallToStringNoException(context));
        logger.info("initContextForCompany() - {}", "END");
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
                    logger.error(
                            String.format("The exception was customised and converted to %s:%s",
                                    customisedException.getClass(), customisedException.getMessage()),
                            customisedException);
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

    protected Policy createPolicy(String policyId) {
        logger.info("createPolicy() - {}", "START");
        logger.debug("Parameters Received \n \t policyId ={}", policyId);
        Policy policy = new Policy();
        PolicyKey policyKey = new PolicyKey();
        policyKey.setId(policyId);
        policy.setKey(policyKey);
        logger.trace("Response Returned \n {}", () -> JAXBService.marshallToStringNoException(policy));
        logger.info("createPolicy() - {}", "END");
        return policy;
    }

    private URL getWSDLURL() throws TechnicalException {
        logger.info("getWSDLURL() - {}", "START");
        URL wsdlURL = null;
        boolean loaded = false;

        for (String path : WSDL_PATH_ARRAY) {
            try {
                wsdlURL = new ClassPathResource(path).getURL();
                logger.debug("WSDL loaded from path = {}", wsdlURL);
                loaded = true;
                break;
            } catch (Exception e) {
                logger.error(String.format("Failed to load wsdl from %s", path), e);
            }
        }

        if (!loaded) {
            String wsdlFilePath = null;
            try {
                wsdlFilePath = String.format("file://%s/webapps/ROOT/WEB-INF/classes/wsdl/DynamicsGP.wsdl",
                        System.getProperty(CATALINA_BASE_PROPERTY));
                wsdlURL = new URL(wsdlFilePath);
                logger.debug("WSDL loaded from path = {}", wsdlFilePath);
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
            throws ValidationException, BusinessException, TechnicalException, RequestTimeOutException {
        logger.info("handleException() - {}", "START");

        if (exception instanceof javax.xml.ws.soap.SOAPFaultException) {
            if (exception.getMessage() != null && exception.getMessage().contains("Business object not found.")) {
                logger.debug("Converting exception to EntityNotFoundException");
                throw new EntityNotFoundException(getSOAPFaultString(exception.getMessage()), exception);
            } else if (exception.getMessage() != null
                    && exception.getMessage().contains("A validation exception has occurred")) {
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