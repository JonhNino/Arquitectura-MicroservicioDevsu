package com.Devsu.ClientePersonaService.dummy;

import com.Devsu.ClientePersonaService.mq.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DummyService {

    @Autowired
    private Publisher publisher;

    public void sendToRabbit(String message) {
        Data d = new Data(12, message);
        log.info("Message '{}' will be send ... ", d);
        this.publisher.send(d);
    }

}