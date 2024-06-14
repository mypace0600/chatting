package com.just.chatting.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Slf4j
@Component
public class CommonUtil {
    private String serverIP;

    public String getServerIP(){
        if(StringUtils.isBlank(serverIP) || StringUtils.equals(serverIP, Constant.UNKNOWN)){
            try {
                List<String> ips = new ArrayList<>();
                for(Enumeration<NetworkInterface> eni = NetworkInterface.getNetworkInterfaces(); eni.hasMoreElements();){
                    NetworkInterface nif = eni.nextElement();
                    for(Enumeration<InetAddress> eia = nif.getInetAddresses(); eia.hasMoreElements();){
                        InetAddress ia = eia.nextElement();
                        if(!ia.isLoopbackAddress() && !ia.isLinkLocalAddress() && ia.isSiteLocalAddress()){
                            ips.add(ia.getHostAddress().toString());
                        }
                    }
                }
                if(ips.size()>0){
                    if(ips.size()==1){
                        serverIP = ips.get(0);
                    } else {
                        int size = ips.size();
                        StringBuilder sb = new StringBuilder();
                        for(String ip : ips){
                            size--;
                            sb.append(ip);
                            if(size>0){
                                sb.append(" / ");
                            }
                        }
                        serverIP = sb.toString();
                    }
                }
                if(StringUtils.isBlank(serverIP)){
                    serverIP = Constant.UNKNOWN;
                }
            } catch (Exception e) {
                log.debug("서버 IP를 확인하지 못함");
                serverIP = Constant.UNKNOWN;
            }
        }
        return serverIP;
    }

    public String getExceptionTrace(Exception e){
        String trace = ExceptionUtils.getStackTrace(e);
        return trace.length() > 2000 ? trace.substring(0,2000) : trace;
    }
}
