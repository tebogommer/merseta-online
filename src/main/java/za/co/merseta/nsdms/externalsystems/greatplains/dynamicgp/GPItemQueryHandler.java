package za.co.merseta.nsdms.externalsystems.greatplains.dynamicgp;
import za.co.merseta.nsdms.framework.configuration.NSDMSConfiguration;

public abstract class GPItemQueryHandler<T,V> extends GPRequestHandler<T,V>{

    public GPItemQueryHandler(){
        super(NSDMSConfiguration.getInt("gp.dynamics.request.timeout.itemquery.millis"));
    }    
}