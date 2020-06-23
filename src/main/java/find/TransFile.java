package find;

import org.apache.commons.collections4.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 查询单一盘符可执行文件
 * @Author wangzhikai
 * @Date 2019-10-24 15:01:56
 */
public class TransFile {

    private static final String SUFFIX = "exe";

    private static final String ROOTPATH = "E:\\";

    public static void main(String[] args) throws IOException {


        /*File file = new File("../wzk.jpg");
        System.out.println(file.getPath());
        System.out.println(file.getAbsolutePath());
        System.out.println(file.getCanonicalPath());*/


        System.out.println("Searching....");
        Long startTime = System.currentTimeMillis();

        List<String> fileNames = findFiles();
        if (CollectionUtils.isNotEmpty(fileNames)) {
            for (String fileName : fileNames) {
                System.out.println(fileName);
            }
            System.out.println("=======================================");
            Long endTime = System.currentTimeMillis();

            System.out.println("(匹配到 " + fileNames.size() + "条 数据，总共花费 " + (endTime-startTime)/1000 + "秒 的时间)");
        }else {
            System.out.println("没有匹配的文件!");
        }
    }

    private static List<String> findFiles() throws IOException {
        // 1.定义集合保存符合要求的文件路径
        List<String> allFileNames = new ArrayList<String>();

        // 2.定义要查询的盘符
        File root = new File(ROOTPATH);

        File[] files = root.listFiles();
        if (files != null && files.length > 0) {
            // 3.调用递归方法查询出符合要求的文件
            transforward(allFileNames,files);
        }
        return allFileNames;
    }

    private static void transforward(List<String> allFileNames, File[] files) throws IOException {
        for (File file : files) {
            if (!file.isDirectory()) {
                // 3.1如果是文件，直接判断后缀是否符合筛选要求
                String[] split = file.getName().split("\\.");
                String suffStr = split[split.length - 1];
                if (SUFFIX.equals(suffStr)) {
                    allFileNames.add(file.getCanonicalPath());
                }
            }else {
                // 3.2如果是文件夹，继续递归
                File[] innerFiles = file.listFiles();
                if (innerFiles != null && innerFiles.length > 0) {
                    transforward(allFileNames, innerFiles);
                }
            }
        }
    }
}
