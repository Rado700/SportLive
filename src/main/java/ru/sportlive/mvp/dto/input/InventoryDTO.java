package ru.sportlive.mvp.dto.input;

public class InventoryDTO {
    private String name;
    private Integer price;
    private String type;
    private String size;
    private Integer couch_id;

    public InventoryDTO(String name, Integer price, String type, String size, Integer couch_id) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.size = size;
        this.couch_id = couch_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getCouch_id() {
        return couch_id;
    }

    public void setCouch_id(Integer couch_id) {
        this.couch_id = couch_id;
    }
}
