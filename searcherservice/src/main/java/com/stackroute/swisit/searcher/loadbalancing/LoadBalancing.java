package com.stackroute.swisit.searcher.loadbalancing;
/*---- Import Libraries -----*/
import org.springframework.stereotype.Service;

/*------ Interface for loadbalancing -------*/
@Service
public interface LoadBalancing {
  public void loadProducer();
  public void loadConsumer();
}
