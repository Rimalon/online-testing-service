package com.rimalon.onlinetesting.datamodel.entities;

import com.rimalon.onlinetesting.datamodel.ids.UserId;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class Test extends BaseEntity {
    Integer id;
    UserId authorId;
}
