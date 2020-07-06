package cn.barrywangmeng.test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * 模拟注册中心集群负载，验证负载散列算法
 *
 *  @author wangmeng
 *  @date 2020/6/18 10:02
 */
public class EurekaClusterLoadBalanceTest {

    public static void main(String[] args) {
        testJvMallBalance();
        System.out.println("============================");
        testEurekaClusterBalance();
    }

    /**
     * 注册中心地址：
     * 
     * http://peer1/eureka/
     * http://peer2:8080/eureka/
     * http://peer3:8080/eureka/
     */
    private static void testJvMallBalance() {
        List<String> eurekaClientIpList = Lists.newArrayList();
        eurekaClientIpList.add("192.168.22.38");
        eurekaClientIpList.add("192.168.22.141");
        eurekaClientIpList.add("192.168.22.20");
        eurekaClientIpList.add("192.168.22.68");

        TreeMap<String, Integer> ipMap = Maps.newTreeMap();
        for (String eurekaClientIp : eurekaClientIpList) {
            randomize(eurekaClientIp, ipMap);
        }

        printIpResult(ipMap, eurekaClientIpList.size());
    }

    /**
     * 模拟ip段测试注册中心负载集群
     */
    private static void testEurekaClusterBalance() {
        int ipLoopSize = 65000;
        String ipFormat = "192.168.%s.%s";
        TreeMap<String, Integer> ipMap = Maps.newTreeMap();
        int netIndex = 0;
        int lastIndex = 0;
        for (int i = 0; i < ipLoopSize; i++) {
            if (lastIndex == 256) {
                netIndex += 1;
                lastIndex = 0;
            }

            String ip = String.format(ipFormat, netIndex, lastIndex);
            randomize(ip, ipMap);
            lastIndex += 1;
        }

        printIpResult(ipMap, ipLoopSize);
    }

    /**
     * 模拟指定ip地址获取对应注册中心负载
     */
    private static void randomize(String eurekaClientIp, TreeMap<String, Integer> ipMap) {
        List<String> eurekaServerUrlList = Lists.newArrayList();
        eurekaServerUrlList.add("http://peer1:8080/eureka/");
        eurekaServerUrlList.add("http://peer2:8080/eureka/");
        eurekaServerUrlList.add("http://peer3:8080/eureka/");

        List<String> randomList = new ArrayList<>(eurekaServerUrlList);
        Random random = new Random(eurekaClientIp.hashCode());
        int last = randomList.size() - 1;
        for (int i = 0; i < last; i++) {
            int pos = random.nextInt(randomList.size() - i);
            if (pos != i) {
                Collections.swap(randomList, i, pos);
            }
        }

        for (String eurekaHost : randomList) {
            int ipCount = ipMap.get(eurekaHost) == null ? 0 : ipMap.get(eurekaHost);
            ipMap.put(eurekaHost, ipCount + 1);
            break;
        }
    }

    private static void printIpResult(TreeMap<String, Integer> ipMap, int totalCount) {
        for (Map.Entry<String, Integer> entry : ipMap.entrySet()) {
            Integer count = entry.getValue();
            BigDecimal rate = new BigDecimal(count).divide(new BigDecimal(totalCount), 2, BigDecimal.ROUND_HALF_UP);
            System.out.println(entry.getKey() + ":" + count + ":" + rate.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP) + "%");
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
