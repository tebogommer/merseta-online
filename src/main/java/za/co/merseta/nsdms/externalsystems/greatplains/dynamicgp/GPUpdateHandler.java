package za.co.merseta.nsdms.externalsystems.greatplains.dynamicgp;

import za.co.merseta.nsdms.framework.configuration.NSDMSConfiguration;

public abstract class GPUpdateHandler<T,V> extends GPRequestHandler<T,V>{

    public GPUpdateHandler(){
        super(NSDMSConfiguration.getInt("gp.dynamics.request.timeout.update.millis"));
    }   
}