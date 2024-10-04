package com.r3944realms.modernlifepatch.utils.String;

public class StringValidator {
    public static boolean isNotValidMinecraftKey(String key) {
        // 正则表达式，匹配只包含小写字母、数字和下划线的字符串
        String regex = "^[a-z0-9_]+$";
        return !key.matches(regex);
    }
}
