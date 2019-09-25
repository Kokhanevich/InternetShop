package mate.academy.internetshop.generators;

public class RoleIdGenerator {
    private static Long idGenerator = 0L;

    private RoleIdGenerator() {

    }

    public static Long getGeneratedId() {
        return idGenerator++;
    }
}
