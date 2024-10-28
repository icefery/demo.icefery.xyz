package xyz.icefery.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

public class Main {

    public static void main(String[] args) throws Exception {
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Object[] objects;
        try (Client client = dcf.createClient("http://localhost:8080/webservice/product?wsdl")) {
            objects = client.invoke("hello", "icefery");
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(objects);
        System.out.println(json);
    }
}
