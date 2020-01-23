package com.sempra.sdgesi.amq;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class OrderRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("activemq:queue:orders")
            .to("log:?level=WARN&showBody=false");

        restConfiguration()
            .component("spark-rest")
            .bindingMode(RestBindingMode.json)
            .port(9091);

        rest("/order")
            .post()
                .to("activemq:queue:orders")
            .get("/{id}")
                .to("log:?level=WARN&showBody=false");

    }

}
