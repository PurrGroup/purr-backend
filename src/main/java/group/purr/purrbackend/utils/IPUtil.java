package group.purr.purrbackend.utils;

import group.purr.purrbackend.constant.HttpConstants;

import javax.servlet.http.HttpServletRequest;

public class IPUtil {
    public static String getIP(HttpServletRequest request){
        String IP = request.getHeader(HttpConstants.X_FORWARDED_FOR);

        if(IP == null || IP.length() == 0 || IP.equalsIgnoreCase(HttpConstants.UNKNOWN))
            IP = request.getHeader(HttpConstants.PROXY_CLIENT_IP);
        if(IP == null || IP.length() == 0 || IP.equalsIgnoreCase(HttpConstants.UNKNOWN))
            IP = request.getHeader(HttpConstants.WL_PROXY_CLIENT_IP);
        if(IP == null || IP.length() == 0 || IP.equalsIgnoreCase(HttpConstants.UNKNOWN))
            IP = request.getHeader(HttpConstants.HTTP_CLIENT_IP);
        if(IP == null || IP.length() == 0 || IP.equalsIgnoreCase(HttpConstants.UNKNOWN))
            IP = request.getHeader(HttpConstants.HTTP_X_FORWARDED_FOR);
        if(IP == null || IP.length() == 0 || IP.equalsIgnoreCase(HttpConstants.UNKNOWN))
            IP = request.getRemoteAddr();

        if(IP != null && IP.contains(","))
            IP = IP.substring(0, IP.indexOf(",")).trim();

        return IP;
    }
}
