package org.example.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemConfig {

    @Value("CamLinh Branch")
    private String branchName;

    @Value("09:00 AM")
    private String openingHour;

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getOpeningHour() {
        return openingHour;
    }

    public void setOpeningHour(String openingHour) {
        this.openingHour = openingHour;
    }

    @Override
    public String toString() {
        return "SystemConfig{" +
                "branchName='" + branchName + '\'' +
                ", openingHour='" + openingHour + '\'' +
                '}';
    }
}
