package cn.barrywangmeng.test;

import com.google.common.collect.Maps;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class EurekaClusterRandomTest {

    public static void main(String[] args) {
        randomize();
    }

    private static void randomize() {
        int ipLoopSize = 100;
        int eurekaListSize = 3;
        String ipFormat = "192.168.22.%s";
        String ipFormat2 = "192.100.22.%s";
        TreeMap<Integer, Integer> ipMap = Maps.newTreeMap();
        for (int j = 0; j < ipLoopSize; j++) {
            String ip = String.format(ipFormat2, j);

            Random random = new Random(ip.hashCode());
            // serverUrlList下标
            int index = random.nextInt(eurekaListSize - 1);
            Integer ipCount = ipMap.get(index);
            if (ipCount != null) {
                ipCount += 1;
                ipMap.put(index, ipCount);
                continue;
            }

            ipMap.put(index, 1);
        }

        for (Map.Entry<Integer, Integer> entry : ipMap.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

    public static String getServerIPv4() {
        String candidateAddress = null;
        try {
            Enumeration<NetworkInterface> nics = NetworkInterface.getNetworkInterfaces();
            while (nics.hasMoreElements()) {
                NetworkInterface nic = nics.nextElement();
                Enumeration<InetAddress> inetAddresses = nic.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    String address = inetAddresses.nextElement().getHostAddress();
                    String nicName = nic.getName();
                    if (nicName.startsWith("eth0") || nicName.startsWith("en0")) {
                        return address;
                    }
                    if (nicName.endsWith("0") || candidateAddress == null) {
                        candidateAddress = address;
                    }
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException("Cannot resolve local network address", e);
        }
        return candidateAddress == null ? "127.0.0.1" : candidateAddress;
    }
}
