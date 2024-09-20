package com.zzw.data.chapter02.executorexamples;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.records.writer.RecordWriter;
import org.datavec.api.records.writer.impl.csv.CSVRecordWriter;
import org.datavec.api.split.FileSplit;
import org.datavec.api.split.partition.NumberOfRecordsPartitioner;
import org.datavec.api.split.partition.Partitioner;
import org.datavec.api.transform.TransformProcess;
import org.datavec.api.transform.schema.Schema;
import org.datavec.api.writable.Writable;
import org.datavec.local.transforms.LocalTransformExecutor;

/**
 * <Description> <br>
 *
 * @author zhang.zhiwei<br>
 * @version 1.0<br>
 * @CreateDate 2024-09-20 <br>
 * @see com.zzw.data.chapter02.executorexamples <br>
 */
public class LocalExecuteExample {
    public static void main(String[] args) throws Exception {
        try {
            int numClasses = 2;
            int batchSize = 8;
            ClassLoader classLoader = LocalExecuteExample.class.getClassLoader();

            File file = new File(classLoader.getResource("titanic.csv").getPath());
            RecordReader recordReader = new CSVRecordReader(1, ',');
            recordReader.initialize(new FileSplit(file));
            // WritableConverter writableConverter = new SelfWritableConverter();

            Schema schema = new Schema.Builder()
                    .addColumnInteger("Survived")
                    .addColumnCategorical("Pclass", Arrays.asList("1", "2", "3"))
                    .addColumnString("Name")
                    .addColumnCategorical("Sex", Arrays.asList("male", "female"))
                    .addColumnsInteger("Age", "Siblings/Spouses Aboard", "Parents/Children Aboard")
                    .addColumnDouble("Fare")
                    .build();
            TransformProcess transformProcess = new TransformProcess.Builder(schema)
                    .removeColumns("Name", "Fare")
                    .categoricalToInteger("Sex")
                    .categoricalToOneHot("Pclass")
                    .removeColumns("Pclass[1]")
                    .build();

            List<List<Writable>> outputData = new ArrayList<>();

            RecordWriter recordWriter = new CSVRecordWriter();
            Partitioner partitioner = new NumberOfRecordsPartitioner();
            recordWriter.initialize(new FileSplit(new File(classLoader.getResource("LocalExecuteExample.csv").getPath())), partitioner);

            while (recordReader.hasNext()) {
                outputData.add(recordReader.next());
            }
            List<List<Writable>> transformedOutput = LocalTransformExecutor.execute(outputData, transformProcess);
            // 注意，这里数据是被写入到了target目录下的文件里，而不是resource目录下的文件
            recordWriter.writeBatch(transformedOutput);
            recordWriter.close();
        }
        catch (IllegalArgumentException e) {
            System.out.println("Please provide proper file paths for titanic.csv & fle in place of: Path/to/titanic.csv-file && /Path/To/LocalExecuteExample.csv");
            System.out.println("You need to create an empty CSV file and mention the file path in place of /Path/To/LocalExecuteExample.csv");
        }
    }
}
