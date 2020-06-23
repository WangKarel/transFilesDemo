package find;

import org.apache.commons.collections4.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 查询所有盘符所有图片文件/视频文件
 * @Author wangzhikai
 * @Date 2019-10-24 15:02:07
 */
public class TransFiles {

    // 图片后缀
    private static final String[] PICTURE_SUFFIX = {"jpg","png","gif","jpeg"};
    // 视频后缀
    private static final String[] VIDEO_SUFFIX = {"flv","avi","mov","mp4","wmv","rmvb"};
    // 要查找的盘符
    private static final String[] ROOTPATH = {"C:\\","D:\\","E:\\","F:\\","G:\\","H:\\"};

    public static void main(String[] args) throws IOException {
        System.out.println("Searching....");
        Long startTime = System.currentTimeMillis();
        List<String> fileNames = findFiles();
        if (CollectionUtils.isNotEmpty(fileNames)) {
            for (String fileName : fileNames) {
                System.out.println(fileName);
            }
            Long endTime = System.currentTimeMillis();
            System.out.println("================================");
            System.out.println("Match " + fileNames.size() + " pieces of data in total,it takes "
                    + (endTime-startTime)/1000 + " seconds");
            System.out.println("(匹配到 " + fileNames.size() + "条 数据，总共花费 " + (endTime-startTime)/1000 + "秒 的时间)");
        }else {
            System.out.println("No match data!");
        }
    }

    private static List<String> findFiles() throws IOException {
        List<String> fileNames = new ArrayList<String>();
        List<String> paths = Arrays.asList(ROOTPATH);
        if (CollectionUtils.isNotEmpty(paths)) {
            for (String path : paths) {
                File root = new File(path);
                File[] files = root.listFiles();
                if (files != null && files.length > 0) {
                    transForward(fileNames,files);
                }
            }
        }
        return fileNames;
    }

    private static void transForward(List<String> fileNames, File[] files) throws IOException {
        List<String> suffixes = Arrays.asList(PICTURE_SUFFIX);
        for (File file : files) {
            if (!file.isDirectory()) {
                String[] split = file.getName().split("\\.");
                String suffixStr = split[split.length - 1].toLowerCase();
                if (suffixes.contains(suffixStr)) {
                    fileNames.add(file.getCanonicalPath());
                }
            }else {
                File[] innerFiles = file.listFiles();
                if (innerFiles != null && innerFiles.length > 0) {
                    transForward(fileNames,innerFiles);
                }
            }
        }
    }
}
