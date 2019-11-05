package uni.hcmut.wcmanager.enums;

public enum GroupName {
    A(1), B(2), C(3), D(4),
    E(5), F(6), G(7), H(8);

    private int id;

    GroupName(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}