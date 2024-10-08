package com.zzw.data.chapter02.recordreaderexamples;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.datavec.api.conf.Configuration;
import org.datavec.api.records.reader.impl.jackson.FieldSelection;
import org.datavec.api.records.reader.impl.jackson.JacksonLineRecordReader;
import org.datavec.api.records.reader.impl.transform.TransformProcessRecordReader;
import org.datavec.api.split.FileSplit;
import org.datavec.api.transform.TransformProcess;
import org.datavec.api.transform.schema.Schema;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.shade.jackson.core.JsonFactory;
import org.nd4j.shade.jackson.databind.ObjectMapper;

/**
 * <Description> <br>
 *
 * @author zhang.zhiwei<br>
 * @version 1.0<br>
 * @CreateDate 2024-09-20 <br>
 * @see com.zzw.data.chapter02.recordreaderexamples <br>
 */
public class JacksonLineRecordReaderExample {
    public static void main(String[] args) {
        try {
            Schema schema = new Schema.Builder()
                    .addColumnsDouble("sepalLength", "sepalWidth", "petalLength", "petalWidth")
                    .addColumnCategorical("species", Arrays.asList("setosa", "versicolor", "virginica"))
                    .build();
            TransformProcess transformProcess = new TransformProcess.Builder(schema)
                    .categoricalToInteger("species")
                    .build();

            FieldSelection fieldSelection = new FieldSelection.Builder()
                    .addField("sepalLength")
                    .addField("sepalWidth")
                    .addField("petalLength")
                    .addField("petalWidth")
                    .addField("species")
                    .build();

            JacksonLineRecordReader jacksonLineRecordReader = new JacksonLineRecordReader(fieldSelection, new ObjectMapper(new JsonFactory()));
            Configuration configuration = new Configuration();
            configuration.set(JacksonLineRecordReader.LABELS, "species");
            ClassLoader classLoader = JacksonLineRecordReaderExample.class.getClassLoader();

            jacksonLineRecordReader.initialize(new FileSplit(new File(classLoader.getResource("irisdata.txt").getPath())));
            TransformProcessRecordReader recordReader = new TransformProcessRecordReader(jacksonLineRecordReader, transformProcess);
            System.out.println(jacksonLineRecordReader.next());
            DataSetIterator dataSetIterator = new RecordReaderDataSetIterator(recordReader, 5, -1, 3);
            System.out.println(dataSetIterator.totalOutcomes());
        }
        catch (IllegalArgumentException e) {
            System.out.println("Please provide proper file path for the IRIS data in place of: Path/to/Iris-data ");
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
