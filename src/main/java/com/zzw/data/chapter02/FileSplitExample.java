package com.zzw.data.chapter02;

import java.io.File;

import org.datavec.api.split.FileSplit;

/**
 * <Description> <br>
 *
 * @author zhang.zhiwei<br>
 * @version 1.0<br>
 * @CreateDate 2024-09-18 <br>
 * @see com.zzw.data.chapter02 <br>
 */
public class FileSplitExample {
    public static void main(String[] args) {
        String[] allowedFormats = new String[]{".JPEG"};
        FileSplit fileSplit = new FileSplit(new File("temp"), allowedFormats, true);
        fileSplit.locationsIterator().forEachRemaining(System.out::println);
    }
}
