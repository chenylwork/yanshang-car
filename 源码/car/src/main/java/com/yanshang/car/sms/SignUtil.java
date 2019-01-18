
package com.yanshang.car.sms;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public final class SignUtil {

    private SignUtil() {
    }

    /**
     * 数据为集合M，将集合M内非空参数值的参数按照参数名ASCII码从小到大排序（字典序）， 使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串
     * @param map 生成签名的参数集合
     * @param signKey 签名密钥
     * @return
     */
    public static String treeMapToString(TreeMap<String, Object> map, String signKey) {
        if (map == null || map.size() == 0)
            return null;

        Iterator<Entry<String, Object>> it = map.entrySet().iterator();
        StringBuilder builder = new StringBuilder(2048);
        while (it.hasNext()) {
            Entry<String, Object> entry = it.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            if (null != key && (key = key.trim()).length() > 0 && null != value && (value = value.toString().trim()).toString().length() > 0) {
                builder.append(key).append("=").append(value).append("&");
            } else {
                continue;
            }
        }
        return builder.append("signKey=").append(signKey).toString();
    }

    public static String getSign(String signKey, Map<String, String> headers, Map<String, Object> reqBody) {


        TreeMap<String, Object> treeMap = new TreeMap<String, Object>();
        treeMap.putAll(headers);
        treeMap.putAll(reqBody);

        String keyValues = SignUtil.treeMapToString(treeMap, signKey);
        String sign = MD5Util.md5Crypt(keyValues);
        return sign;
    }
}
