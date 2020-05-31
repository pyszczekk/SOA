package pl.edu.agh.soa.rest;

import io.swagger.jaxrs.config.BeanConfig;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class Config extends Application {
    public Config(){
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0");

        beanConfig.setBasePath("/rest-api/api"); // auto-generate a swagger.json here, context-root
        beanConfig.setResourcePackage("pl.edu.agh.soa.rest");
        beanConfig.setTitle("Student service - documentation ");
        beanConfig.setScan(true);
    }

}