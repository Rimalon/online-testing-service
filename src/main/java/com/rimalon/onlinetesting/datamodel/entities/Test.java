package com.rimalon.onlinetesting.datamodel.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class Test extends BaseEntity {
    @JsonProperty
    Integer id;
    @JsonProperty
    Integer authorId;
}
