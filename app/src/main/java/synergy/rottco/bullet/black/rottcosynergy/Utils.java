package synergy.rottco.bullet.black.rottcosynergy;

import java.util.UUID;

public class Utils {
    static String BaseUrlTest = "http://backend.rottco.ro/apiv1/";
    static String BaseUrlLive = "TODO:";
    static String RegistrationUrl = "registeruser";
    static String LoginUrl ="login";
    static String LogoutUrl = "logout";
    static String SendFeedbackUrl ="senduserfeedback";
    static String UserUrl = "getuserdata";
    static String GetGasStationsUrl ="getgasstations";
    static String GetGasStationInfoUrl ="getgasstationinfo";
    static String UpdateUser = "updateuser";

    static String authKey =null;
    public static String getBaseUrlTest() {
        return BaseUrlTest;
    }
    public static String getBaseUrlLive() {
        return BaseUrlLive;
    }

    public static String getRegistrationUrl() {
        return BaseUrlTest+RegistrationUrl;
    }

    public static String getRandomUUID()
    {
        return UUID.randomUUID().toString();
    }

    public static String getLoginUrl() {
        return BaseUrlTest+LoginUrl;
    }

    public static String getSendFeedbackUrl() {
        return BaseUrlTest+SendFeedbackUrl;
    }

    public static String getUserUrl() {
        return BaseUrlTest+UserUrl;
    }

    public static String getGetGasStationsUrl() {
        return BaseUrlTest+GetGasStationsUrl;
    }

    public static String getGetGasStationInfoUrl() {
        return BaseUrlTest+GetGasStationInfoUrl;
    }

    public static String getUpdateUserUrl() {
        return BaseUrlTest+UpdateUser;
    }
    public static String getAuthKey() {
        return authKey;
    }


    //temporary change TODO:

    public static void setAuthKey(String authKey) {
        Utils.authKey = authKey;
    }
    public static void invalidateAuthKey(String authKey) {
        Utils.authKey = null;
    }

    public static String getLogoutUrl() {
        return BaseUrlTest+LogoutUrl;
    }

}
