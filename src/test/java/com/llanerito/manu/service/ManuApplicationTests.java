package com.llanerito.manu.service;

import com.llanerito.manu.intrastructure.cloudinary.CloudinaryAdapter;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.junit.jupiter.api.Test;

@SpringBootTest
@ActiveProfiles("test")
class ManuApplicationTests {

    @MockBean
    private CloudinaryAdapter cloudinaryAdapter; // Con @MockBean, como siempre.

    @Test
    void contextLoads() {
        // Test básico para verificar la carga del ApplicationContext
    }
}
