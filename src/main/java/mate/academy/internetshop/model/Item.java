package mate.academy.internetshop.model;

public class Item {
    private Long id;
    private String name;
    private Double price;

    public Item() {
        //this.id = ItemIdGenerator.getGeneratedId();
    }

    public Item(Long id) {
        this.id = id;
    }

    public Item(String name, Double price) {
        this();
        this.name = name;
        this.price = price;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Item{"
                + "id="
                + id + ", name='"
                + name + '\'' + ", prise="
                + price + '}';
    }
}
