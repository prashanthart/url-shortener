package com.prashanth.urlshortener.service;

import com.prashanth.urlshortener.modal.Counter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CounterService {
    private final MongoTemplate mongoTemplate;

    public long getNextSequence(String name){
        Counter counter = mongoTemplate.findAndModify(
                Query.query(Criteria.where("_id").is(name)),
                new Update().inc("seq",1),
                FindAndModifyOptions.options().returnNew(true).upsert(true),
                Counter.class
        );
        return counter != null ? counter.getSeq() : 1L;
    }
}
