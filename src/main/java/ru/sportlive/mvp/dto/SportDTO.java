package ru.sportlive.mvp.dto;

public class SportDTO {
    private String sport_name;
    private String description;
    private String instruction;
    private String equipment;

    public SportDTO(String sport_name, String description, String instruction, String equipment) {
        this.sport_name = sport_name;
        this.description = description;
        this.instruction = instruction;
        this.equipment = equipment;
    }

    public String getSport_name() {
        return sport_name;
    }

    public void setSport_name(String sport_name) {
        this.sport_name = sport_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }
}
