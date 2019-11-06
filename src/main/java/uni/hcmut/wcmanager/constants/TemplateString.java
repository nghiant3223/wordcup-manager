package uni.hcmut.wcmanager.constants;

public class TemplateString {
    public static final String GROUP_NAME = "\nGroup %s\n";
    public static final String SEPARATOR = "-".repeat(52);
    public static final String MATCH_RESULT = "%15s %-2d - %-2d %-15s";
    public static final String DATA_TEMPLATE = "|%-15s|%3d|%3d|%3d|%3d|%3d|%3d|%3s|%3s|%2s|\n";
    private static final String HEADER_TEMPLATE = "|%-15s|%3s|%3s|%3s|%3s|%3s|%3s|%3s|%3s|%2s|\n";
    public static final String HEADER = String.format(TemplateString.HEADER_TEMPLATE,
            "Team", "MP", "W", "D", "L", "GF", "GA", "GD", "YC", "S");
}
