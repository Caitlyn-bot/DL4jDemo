package com.zzw.data.chapter02.recordreaderexamples;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.records.reader.impl.transform.TransformProcessRecordReader;
import org.datavec.api.split.FileSplit;
import org.datavec.api.transform.TransformProcess;
import org.datavec.api.transform.schema.Schema;
import org.datavec.api.transform.transform.doubletransform.ConvertToDouble;

/**
 * <Description> <br>
 *
 * @author zhang.zhiwei<br>
 * @version 1.0<br>
 * @CreateDate 2024-09-19 <br>
 * @see com.zzw.data.chapter02.recordreaderexamples <br>
 */
public class TransformProcessRecordReaderExample {
    public static void main(String[] args) {
        try {
            Schema schema = new Schema.Builder()
                    .addColumnsString("Name", "Subject")
                    .addColumnInteger("Score")
                    .addColumnCategorical("Grade", Arrays.asList("A", "B", "C", "D"))
                    .addColumnInteger("Passed").build();

            TransformProcess transformProcess = new TransformProcess.Builder(schema)
                    .removeColumns("Name")
                    .transform(new ConvertToDouble("Score"))
                    .categoricalToInteger("Grade").build();
            RecordReader recordReader = new CSVRecordReader(1, ',');
            ClassLoader classLoader = TransformProcessRecordReaderExample.class.getClassLoader();
            recordReader.initialize(new FileSplit(new File(classLoader.getResource("transform-data.csv").getPath())));
            RecordReader transformRecordReader = new TransformProcessRecordReader(recordReader, transformProcess);
            System.out.println(transformRecordReader.next().get(0).toString());
        }
        catch (IllegalArgumentException e) {
            System.out.println("Please provide proper directory path to transform-data.csv in place of: Path/to/transform-data.csv");
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
