package com.zzw.data.chapter02;

import java.io.File;
import org.datavec.api.split.CollectionInputSplit;
import org.datavec.api.split.FileSplit;

/**
 * <Description> <br>
 *
 * @author zhang.zhiwei<br>
 * @version 1.0<br>
 * @CreateDate 2024-09-18 <br>
 * @see com.zzw.data.chapter02 <br>
 */
public class CollectionInputSplitExample {
    public static void main(String[] args) {
        FileSplit fileSplit = new FileSplit(new File("temp"));
        CollectionInputSplit collectionInputSplit = new CollectionInputSplit(fileSplit.locations());
        collectionInputSplit.locationsIterator().forEachRemaining(System.out::println);
    }
}
