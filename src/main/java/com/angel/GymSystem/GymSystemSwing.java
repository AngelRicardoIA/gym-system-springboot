package com.angel.GymSystem;

import com.angel.GymSystem.gui.GymSystemForm;
import org.hibernate.id.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

@SpringBootApplication
public class GymSystemSwing {
    public static void main(String[] args) {
        ConfigurableApplicationContext springContext =
                new SpringApplicationBuilder(GymSystemSwing.class)
                        .headless(false)
                        .web(WebApplicationType.NONE)
                        .run(args);
        SwingUtilities.invokeLater(() -> {
            GymSystemForm gymSystemForm = springContext.getBean(GymSystemForm.class);
            gymSystemForm.setVisible(true);
        });
    }
}
