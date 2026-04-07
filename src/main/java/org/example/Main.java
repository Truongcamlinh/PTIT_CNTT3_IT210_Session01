package org.example;

import org.example.config.AppConfig;
import org.example.model.SystemConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        SystemConfig config = context.getBean(SystemConfig.class);

        System.out.println("=== System Configuration ===");
        System.out.println("Branch Name: " + config.getBranchName());
        System.out.println("Opening Hour: " + config.getOpeningHour());
        System.out.println("\nFull Config: " + config.toString());

        // Đóng context
        ((AnnotationConfigApplicationContext) context).close();
    }
}
