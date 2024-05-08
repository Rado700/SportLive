package ru.sportlive.mvp.dto.input;

public class SportDTO {
    private String name_sport;
    private String description;
    private String instruction;
    private String equipment;

    public SportDTO(String sport_name, String description, String instruction, String equipment) {
        this.name_sport = sport_name;
        this.description = description;
        this.instruction = instruction;
        this.equipment = equipment;
    }

    public String getName_sport() {
        return name_sport;
    }

    public void setName_sport(String name_sport) {
        this.name_sport = name_sport;
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
