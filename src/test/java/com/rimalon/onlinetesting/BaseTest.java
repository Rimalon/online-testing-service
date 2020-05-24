package com.rimalon.onlinetesting;

import com.rimalon.onlinetesting.datamodel.entities.User;
import com.rimalon.onlinetesting.datamodel.ids.UserId;
import com.rimalon.onlinetesting.helpers.QueryHelper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OnlineTestingApplication.class)
public class BaseTest {
    protected final UserId userId1 = new UserId(Integer.MAX_VALUE);
    protected final UserId userId2 = new UserId(Integer.MAX_VALUE - 1);
    protected final UserId userId3 = new UserId(Integer.MAX_VALUE - 2);
    protected final UserId userId4 = new UserId(Integer.MAX_VALUE - 3);

    @Autowired
    QueryHelper queryHelper;

    @Before
    public void setup() {
        queryHelper.save(User.class, "id, username, password", new Object[]{userId1, "testUser1", "$2a$10$bnxQNavPT8cjZh5NFQJlIusyBuofSXdtWMGRIqOyD3IClb2RFcrjq"}); // this password is qwerty
        queryHelper.save(User.class, "id, username, password", new Object[]{userId2, "testUser2", "$2a$10$bnxQNavPT8cjZh5NFQJlIusyBuofSXdtWMGRIqOyD3IClb2RFcrjq"}); // this password is qwerty
        queryHelper.save(User.class, "id, username, password", new Object[]{userId3, "testUser3", "$2a$10$bnxQNavPT8cjZh5NFQJlIusyBuofSXdtWMGRIqOyD3IClb2RFcrjq"}); // this password is qwerty
        queryHelper.save(User.class, "id, username, password", new Object[]{userId4, "testUser4", "$2a$10$bnxQNavPT8cjZh5NFQJlIusyBuofSXdtWMGRIqOyD3IClb2RFcrjq"}); // this password is qwerty
    }

}
