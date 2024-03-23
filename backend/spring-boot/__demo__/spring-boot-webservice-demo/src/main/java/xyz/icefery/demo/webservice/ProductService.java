package xyz.icefery.demo.webservice;

import javax.jws.WebService;

@WebService(targetNamespace = "http://service.webservice.demo.icefery.xyz")
public interface ProductService {
    String hello(String name);
}