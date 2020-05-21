package com.rimalon.onlinetesting.datamodel.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class Test extends BaseEntity {
    Integer id;
    Integer authorId;
}
