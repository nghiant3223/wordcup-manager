package uni.hcmut.wcmanager.enums;

public enum RoundName {
    GROUP_STAGE(1),
    ROUND_OF_SIXTEEN(2),
    QUARTER_FINAL_ROUND(3),
    SEMI_FINAL_ROUND(4),
    FINAL(5);

    private int id;

    RoundName(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
