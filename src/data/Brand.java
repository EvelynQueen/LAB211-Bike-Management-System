package data;

public class Brand {
    private String id;
    private String name;
    private String origin;

    public Brand(String id, String name, String origin) {
        this.id = id;
        this.name = name;
        this.origin = origin;
    }

    // Getters

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOrigin() {
        return origin;
    }

    @Override
    public String toString() {
        return id + ", " + name + ", " + origin;
    }
}
