package com.urna.app.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url= "${route.validaCPF}" , name = "cpf")
public interface ValidaCPFClient {
    @RequestMapping(method = RequestMethod.GET, value = "/cpf/{cpf}")
    String getValidaCPF(@PathVariable("cpf") String cpf);
}
