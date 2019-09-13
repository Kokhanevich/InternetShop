package mate.academy.internetshop.generators;

public class UserIdGenerator {
    private static Long idGenerator = 0L;

    private UserIdGenerator() {

    }

    public static Long getGeneratedId() {
        return idGenerator++;
    }
}
