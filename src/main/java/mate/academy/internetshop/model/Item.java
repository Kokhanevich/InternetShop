package mate.academy.internetshop.model;

import mate.academy.internetshop.generators.ItemIdGenerator;

public class Item {
    private Long id;
    private String name;
    private Double prise;

    public Item() {
        this.id = ItemIdGenerator.getGeneratedId();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrise() {
        return prise;
    }

    public void setPrise(Double prise) {
        this.prise = prise;
    }

    @Override
    public String toString() {
        return "Item{"
                + "id="
                + id + ", name='"
                + name + '\'' + ", prise="
                + prise + '}';
    }
}
