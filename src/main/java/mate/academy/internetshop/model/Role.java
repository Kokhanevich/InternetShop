package mate.academy.internetshop.model;

import mate.academy.internetshop.generators.RoleIdGenerator;

public class Role {
    private final Long id;
    private RoleName roleName;

    public Role() {
        this.id = RoleIdGenerator.getGeneratedId();
    }

    public Role(RoleName roleName) {
        this();
        this.roleName = roleName;
    }

    public enum RoleName {
        USER, ADMIN;
    }

    public Long getId() {
        return id;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public static Role of(String roleName) {
        return new Role(RoleName.valueOf(roleName));
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }
}
