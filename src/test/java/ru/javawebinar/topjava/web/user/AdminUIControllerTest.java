package ru.javawebinar.topjava.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.web.AbstractControllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.UserTestData.GUEST_ID;
import static ru.javawebinar.topjava.UserTestData.guest;

class AdminUIControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminUIController.REST_URL + '/';

    @Autowired
    private UserService userService;

    @Test
    void setEnable() throws Exception {
        User user = guest;
        user.setEnabled(!user.isEnabled());

        perform(MockMvcRequestBuilders
                .post(REST_URL + GUEST_ID)
                .param("enable", String.valueOf(user.isEnabled()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        assertEquals(user.isEnabled(), userService.get(GUEST_ID).isEnabled());
    }
}