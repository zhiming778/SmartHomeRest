package com.jimmy.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.jimmy.dao.MessageEntityRepository;
import com.jimmy.entity.MessageEntity;

@Component
public class Messages {

    private Map<String, String> map;

    private MessageEntityRepository repository;

    public Messages(MessageEntityRepository repository) {
        this.repository = repository;
        map = new HashMap<>();
        List<MessageEntity> list = repository.findAll();
        System.out.println(list.size());
        for (MessageEntity entity : list)
            map.put(entity.getName(), entity.getValue());
    }

    public String get(String name) {
        return map.get(name);
    }
}
