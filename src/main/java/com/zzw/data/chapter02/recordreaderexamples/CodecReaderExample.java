package com.zzw.data.chapter02.recordreaderexamples;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.datavec.api.conf.Configuration;
import org.datavec.api.records.reader.SequenceRecordReader;
import org.datavec.api.split.FileSplit;
import org.datavec.api.writable.Writable;
import org.datavec.codec.reader.CodecRecordReader;
import org.datavec.codec.reader.NativeCodecRecordReader;

/**
 * <Description>视频 <br>
 *
 * @author zhang.zhiwei<br>
 * @version 1.0<br>
 * @CreateDate 2024-09-19 <br>
 * @see com.zzw.data.chapter02.recordreaderexamples <br>
 */
public class CodecReaderExample {
    public static void main(String[] args) {
        try {
            SequenceRecordReader codecRecordReader = new NativeCodecRecordReader();
            Configuration conf = new Configuration();
            conf.set(CodecRecordReader.RAVEL, "true");
            conf.set(CodecRecordReader.START_FRAME, "2");
            conf.set(CodecRecordReader.TOTAL_FRAMES, "10");
            conf.set(CodecRecordReader.ROWS, "80");
            conf.set(CodecRecordReader.COLUMNS, "46");
            conf.set(CodecRecordReader.VIDEO_DURATION, "30");
            //Replace with the video file path in your local drive
            ClassLoader classLoader = CodecReaderExample.class.getClassLoader();
            codecRecordReader.initialize(new FileSplit(new File(classLoader.getResource("DL4j.mp4").getPath())));
            codecRecordReader.setConf(conf);
            List<List<Writable>> list =  codecRecordReader.sequenceRecord();
            list.listIterator().forEachRemaining(el -> System.out.println(el.size()));
        }
        catch (IllegalArgumentException e) {
            System.out.println("Please provide proper video file path in place of: /Path/to/video-file ");
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
