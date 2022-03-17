package xyz.icefery.demo.webservice;

import org.springframework.stereotype.Service;
import javax.jws.WebMethod;
import javax.jws.WebService;

@Service
@WebService(
    serviceName = "product-service",
    targetNamespace = "http://service.webservice.demo.icefery.xyz",
    endpointInterface = "xyz.icefery.demo.webservice.ProductService"
)
public class ProductServiceImpl implements ProductService {
    @WebMethod
    @Override
    public String hello(String name) {
        return "Hello " + name;
    }
}