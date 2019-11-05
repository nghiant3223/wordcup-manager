package uni.hcmut.wcmanager.utils;

import uni.hcmut.wcmanager.enums.MatchType;
import uni.hcmut.wcmanager.enums.RoundName;

public class MatchUtils {
    public static MatchType getType(RoundName roundName) {
        if (roundName == RoundName.GROUP_STAGE) {
            return MatchType.DRAWABLE;
        }

        return MatchType.KNOCKOUT;
    }
}
