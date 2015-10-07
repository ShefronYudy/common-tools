package com.shefron.test.utils;

import java.io.File;
import java.util.*;

/**
 * Created by Administrator on 2014/12/12.
 */
public class FileUtils {

    /**
     * 返回排序后的文件列表
     * @param files 需要排序的文件集合
     * @param ascFlag true:升序 false:降序
     * @return
     */
    public final static List<File> getSortedFilesByTime(Collection<File> files, final boolean ascFlag){
        List<File> sortedFiles = new ArrayList<File>();
        for(File file : files){
            sortedFiles.add(file);
        }

        Collections.sort(sortedFiles, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                long diff = o1.lastModified() - o2.lastModified();

                int i = 0;
                if(diff>0){
                    i = ascFlag ? 1:-1;

                }else if(diff == 0){
                    i = 0;
                }else{
                    i = ascFlag ? -1:1;
                }
                return i;
            }
        });

        return sortedFiles;

    }

    public static void main(String[] args){
        Set<File> files = new HashSet<File>();

        for(File file :FileUtils.getSortedFilesByTime(files,false) ){
            System.out.println(file.getName());
        }
    }


}
