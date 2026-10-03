package com.prashanth.urlshortener.modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "counters")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Counter {

    @Id
    private String id;

    private long seq;
}
