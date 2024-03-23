package xyz.icefery.demo.mvc.util;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MvcUtil {
    // 小写单词首字母
    public static String lowerFirstCase(String str) {
        char[] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    // 扫描包中的类的全限定名到集合中
    public static List<String> getClassNamesFromPackage(String packageName) {
        List<String> classNames = new ArrayList<>();
        getClassNamesFromPackage(classNames, packageName);
        return classNames;
    }

    private static void getClassNamesFromPackage(List<String> classNames, String packageName) {
        URL url = MvcUtil.class.getClassLoader().getResource("/" + packageName.replaceAll("\\.", "/"));
        for (File file : new File(url.getFile()).listFiles()) {
            if (file.isDirectory()) {
                getClassNamesFromPackage(classNames, packageName);
            } else {
                classNames.add(packageName + "." + file.getName().replaceAll(".class", "").trim());
            }
        }
    }
}
