package pl.edu.agh.controllers;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ModelMap;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class HomeControllerTest {

    private HomeController homeController;
    private ModelMap modelMap;

    @Before
    public void setUp() throws Exception {
        homeController = new HomeController();
        modelMap = mock(ModelMap.class);
    }

    @Test
    public void testGetAbout() throws Exception {
        String nextView = homeController.getAbout(modelMap);
        assertEquals("home/about",nextView);
    }

}