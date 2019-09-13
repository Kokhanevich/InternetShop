package mate.academy.internetshop.generators;

public class ItemIdGenerator {
    private static Long idGenerator = 0L;

    private ItemIdGenerator() {
    }

    public static Long getGeneratedId() {
        return idGenerator++;
    }
}
