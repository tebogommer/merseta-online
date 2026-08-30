package za.co.merseta.nsdms.externalsystems.greatplains.dynamicgp;

import za.co.merseta.nsdms.framework.configuration.NSDMSConfiguration;

public abstract class GPListQueryHandler<T,V> extends GPRequestHandler<T,V>{

    public GPListQueryHandler(){
        super(NSDMSConfiguration.getInt("gp.dynamics.request.timeout.listquery.millis"));
    }
    
}
