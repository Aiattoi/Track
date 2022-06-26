package com.aiattoi.track.app;

import com.aiattoi.track.api.controller.InterestingSiteController;
import com.aiattoi.track.api.controller.ManagerController;
import com.aiattoi.track.api.controller.TrackController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TrackApplicationTests {

    @Autowired
    ManagerController managerController;
    @Autowired
    TrackController trackController;
    @Autowired
    InterestingSiteController siteController;


    @Test
    void contextLoadsTests() {
        Assertions.assertNotNull(managerController);
        Assertions.assertNotNull(trackController);
        Assertions.assertNotNull(siteController);
    }


}
