package uni.hcmut.wcmanager.utils;

import uni.hcmut.wcmanager.enums.MatchType;
import uni.hcmut.wcmanager.enums.RoundName;

public class MatchUtils {
    static int getMatchType(int roundName) {
        if (roundName > RoundName.GROUP_STAGE) {
            return MatchType.KNOCKOUT;
        }

        return MatchType.DRAWABLE;
    }
}
