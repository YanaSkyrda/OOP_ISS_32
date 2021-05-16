package model.user;

public class UserBuilder {

    private Long id;

    private String name;

    private String email;

    private String password;

    private String role;

    public UserBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public UserBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder setRole(String role) {
        this.role = role;
        return this;
    }

    public User build() {
        return new User(id, name, email, password, role);
    }
}
