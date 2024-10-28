package xyz.icefery.demo.config;

import javax.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.icefery.demo.webservice.ProductService;

@Configuration
public class WebServiceConfig {

    @Autowired
    private ProductService productService;

    @Bean
    public ServletRegistrationBean<?> cxfServlet() {
        return new ServletRegistrationBean<>(new CXFServlet(), "/webservice/*");
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    @Bean
    public Endpoint endpoint() {
        Endpoint endpoint = new EndpointImpl(springBus(), productService);
        endpoint.publish("/product");
        return endpoint;
    }
}
