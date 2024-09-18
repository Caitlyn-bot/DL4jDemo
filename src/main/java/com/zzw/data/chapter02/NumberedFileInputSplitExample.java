package com.zzw.data.chapter02;

import org.datavec.api.split.NumberedFileInputSplit;

/**
 * <Description> <br>
 *
 * @author zhang.zhiwei<br>
 * @version 1.0<br>
 * @CreateDate 2024-09-18 <br>
 * @see com.zzw.data.chapter02 <br>
 */
public class NumberedFileInputSplitExample {
    public static void main(String[] args) {
        NumberedFileInputSplit numberedFileInputSplit = new NumberedFileInputSplit("numberedfiles/file%d.txt", 1, 4);
        numberedFileInputSplit.locationsIterator().forEachRemaining(System.out::println);
    }
}
