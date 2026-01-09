package ru.nikogosyan.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.nikogosyan.MySecondTestAppSpringBoot.model.Response;

@Service
public interface ModifyResponseService {
    Response modify(Response response);
}
