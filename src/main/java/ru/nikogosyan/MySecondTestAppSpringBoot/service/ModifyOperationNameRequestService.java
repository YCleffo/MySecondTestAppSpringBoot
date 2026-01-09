package ru.nikogosyan.MySecondTestAppSpringBoot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.nikogosyan.MySecondTestAppSpringBoot.model.Request;

@Slf4j
@Service
//@Qualifier("ModifyOperationNameRequestService")
public class ModifyOperationNameRequestService implements ModifyRequestService{
    @Override
    public void modify(Request request){
//        request.setSystemName("Service 1");

        log.info("Отправка запроса в Сервис 2...");

        HttpEntity<Request> httpEntity = new HttpEntity<>(request);

        new RestTemplate().exchange("http://localhost:8084/feedback",
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<>() {
                });
    }
}
