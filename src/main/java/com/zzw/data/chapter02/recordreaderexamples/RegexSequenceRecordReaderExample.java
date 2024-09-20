package com.zzw.data.chapter02.recordreaderexamples;

import java.io.IOException;
import org.datavec.api.records.reader.SequenceRecordReader;
import org.datavec.api.records.reader.impl.regex.RegexSequenceRecordReader;
import org.datavec.api.split.NumberedFileInputSplit;

/**
 * <Description> <br>
 *
 * @author zhang.zhiwei<br>
 * @version 1.0<br>
 * @CreateDate 2024-09-19 <br>
 * @see com.zzw.data.chapter02.recordreaderexamples <br>
 */
public class RegexSequenceRecordReaderExample {
    public static void main(String[] args) {
        try {
            ClassLoader classLoader = RegexSequenceRecordReaderExample.class.getClassLoader();

            NumberedFileInputSplit fileSplit = new NumberedFileInputSplit(classLoader.getResource("logdata").getPath(),
                    1,
                    20);
            String regex = "(\\d{2}/\\d{2}/\\d{2}) (\\d{2}:\\d{2}:\\d{2}) ([A-Z]) (.*)";

            SequenceRecordReader recordReader = new RegexSequenceRecordReader(regex, 0);
            recordReader.initialize(fileSplit);
            //There are 10 sequences of files.  We are printing one of the sample sequence here
            System.out.println(recordReader.next().get(0).toString());
        }
        catch (IllegalArgumentException e) {
            System.out.println("Please provide proper directory path to logdata in place of: Path/to/logdata");
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
