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
        String base_Version = FileUtil.readLines(FileUtil.getWebRoot() + File.separator + "tims_base_version.txt", "utf-8").get(0);
        if (StrUtil.hasEmpty(base_Version)) {
            //获取最新版本号和前一次版本号差异
            String diff_cmd = "git diff "+new_version+" "+last_version+" --name-status";
            List<String> changelogs = RuntimeUtil.execForLines(diff_cmd);
            List<String> d = changelogs.stream().filter(s -> s.trim().startsWith("D")).collect(Collectors.toList());
            System.out.println(d);
        }else{
            //获取最新和基线差异
            String diff_cmd = "git diff "+new_version+" "+base_Version+" --name-status";
        }


    }
}
