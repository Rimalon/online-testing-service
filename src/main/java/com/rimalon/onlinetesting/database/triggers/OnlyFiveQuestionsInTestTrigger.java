package com.rimalon.onlinetesting.database.triggers;

import org.h2.api.Trigger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OnlyFiveQuestionsInTestTrigger implements Trigger {

    @Override
    public void init(Connection conn, String schemaName, String triggerName, String tableName, boolean before, int type) throws SQLException {
    }

    @Override
    public void fire(Connection conn, Object[] oldRow, Object[] newRow) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
                "SELECT COUNT(*) as count\n" +
                        "FROM online_testing.question\n" +
                        "WHERE testId = ?");
        stmt.setObject(1, newRow[1]);
        ResultSet result = stmt.executeQuery();
        result.next();
        if (result.getInt(1) > 5) {
            PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM online_testing.question WHERE id = ?");
            deleteStmt.setObject(1, newRow[0]);
            deleteStmt.execute();
        }
    }

    @Override
    public void close() throws SQLException {
    }

    @Override
    public void remove() throws SQLException {

    }
}
