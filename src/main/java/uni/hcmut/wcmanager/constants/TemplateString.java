package uni.hcmut.wcmanager.constants;

public class TemplateString {
    public static final String GROUP_NAME_TEMPLATE = "\nGroup %s\n";
    public static final String GROUP_SEPARATOR = "-".repeat(52);

    private static final String HALF_MATCH_SEPARATOR = "-".repeat(20);
    public static final String MATCH_SEPARATOR = String.format("%20s%-20s",
            HALF_MATCH_SEPARATOR, HALF_MATCH_SEPARATOR);

    public static final String MATCH_RESULT_TEMPLATE = "%15s %-2d - %-2d %-15s";
    public static final String PENALTY_RESULT_TEMPLATE = "%18s - %-18s";
    public static final String WINNER_RESULT_TEMPLATE = "%18s : %-18s";
    public static final String TOP_SCORER_TEMPLATE = "%18s : %-18s %d goals";

    public static final String DATA_TEMPLATE = "|%-15s|%3d|%3d|%3d|%3d|%3d|%3d|%3s|%3s|%2s|\n";
    private static final String HEADER_TEMPLATE = "|%-15s|%3s|%3s|%3s|%3s|%3s|%3s|%3s|%3s|%2s|\n";

    public static final String HEADER = String.format(TemplateString.HEADER_TEMPLATE,
            "Team", "MP", "W", "D", "L", "GF", "GA", "GD", "YC", "S");
}
