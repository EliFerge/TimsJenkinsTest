package com.icbc.tims;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class BuildPatch {
    public static void main(String[] args) {

        String versino_cmd = "git log -2 --pretty=format:\"%H\"";
        List<String> versions = RuntimeUtil.execForLines(versino_cmd);
        String new_version = versions.get(0).trim();
        String last_version = versions.get(1).trim();
        System.out.println("最新版本号:"+new_version);
        System.out.println("最新的前一次版本号:"+last_version);
        String base_Version = FileUtil.readLines(FileUtil.getWebRoot() + File.separator + "tims_base_version.txt", "utf-8").get(0);
        String diff_cmd = "git diff " + new_version + " " + last_version + " --name-status";
        if (!StrUtil.hasEmpty(base_Version)) {
            //获取最新版本号和前一次版本号差异
             diff_cmd = "git diff " + new_version + " " + base_Version + " --name-status";

        }
        List<String> changelogs = RuntimeUtil.execForLines(diff_cmd);
        List<String> d = changelogs.stream().filter(s -> s.trim().startsWith("A") || s.trim().startsWith("M"))
                .map(s -> s.substring(1).trim()).collect(Collectors.toList());
        System.out.println("所有AM文件列表:"+d);
        for (int i = 0; i < d.size(); i++) {
            String pattern = "^.+/(?:src)/(.+)\\.java\\s*$";

//                if (d.get(i).endsWith(".java") && d.get(i).startsWith("TimsJenkinsTest/src")) {
//                    d.set(i,d.get(i).replace(".java", "*.class").replace("TimsJenkinsTest/src", "TimsJenkinsTest/WebContent/WEB-INF/classes"));
//                }

            if (d.get(i).matches(pattern)) {
                d.set(i, d.get(i).replace(".java", "*.class").replace("TimsJenkinsTest/src", "TimsJenkinsTest/WebContent/WEB-INF/classes"));

            }
            d.set(i, d.get(i) + "*");
            d.set(i, d.get(i).substring(15));
        }
        System.out.println("patch文件列表:"+d);
        FileUtil.writeLines(d, FileUtil.getWebRoot() + File.separator + "tims_patch.txt", "utf-8");


    }
}
