package com.rimalon.onlinetesting.datamodel.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class User extends BaseEntity{
    Integer id;
    String username;
    String password;


}
