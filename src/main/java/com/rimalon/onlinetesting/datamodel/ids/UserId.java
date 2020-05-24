package com.rimalon.onlinetesting.datamodel.ids;

import org.springframework.jdbc.core.SqlParameterValue;

import java.sql.Types;

public class UserId extends SqlParameterValue {
    public UserId(Integer id) {
        super(Types.INTEGER, id);
    }

    @Override
    public String toString() {
        return this.getValue() == null ? "null" : this.getValue().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserId intId = (UserId) o;

        return this.getValue() == intId.getValue();
    }

    @Override
    public int hashCode() {
        return (int) super.getValue();
    }

}
