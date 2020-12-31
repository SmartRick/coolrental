package cn.kgc.coolrental.util;

import java.util.Arrays;
import java.util.List;

public class StringUtil {
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str.trim()) || "null".equals(str);
    }

    public static boolean notEmpty(String str) {
        return !isEmpty(str);
    }

    public static List<String> splitByLine(String str) {
        return Arrays.asList(str.trim().split(";"));
    }

    public static List<String> splitBySem(String str) {
        return Arrays.asList(str.trim().split("|"));
    }
}
