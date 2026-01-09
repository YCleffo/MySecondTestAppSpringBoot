package ru.nikogosyan.MySecondTestAppSpringBoot.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.nikogosyan.MySecondTestAppSpringBoot.model.Request;

@Service
@Qualifier("ModifySystemSourceService")
public class ModifyRequestSystemSourceService implements ModifyRequestService {

    @Override
    public void modify(Request request) {
        request.setSource("Service 1 Source");
    }
}