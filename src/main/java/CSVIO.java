import com.csvreader.CsvWriter;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Description:
 *     对csv进行读写操作
 *
 * @author maserhe
 * @date 2021/10/14 3:39 下午
 **/
public class CSVIO {

    public static void write(String path, String[][] data) {
        if (StringUtils.isBlank(path)) {
            System.out.println("文件保存路径出错");
        }

        // 创建CSV写对象 例如:CsvWriter(文件路径，分隔符，编码格式);
        CsvWriter csvWriter = new CsvWriter(path, ',', Charset.forName("UTF-8"));
        // 写表头
        String[] csvHeaders = data[0];
        try {
            csvWriter.writeRecord(csvHeaders);

            // 写内容
            for (int i = 1; i < data.length; i++) {
                String[] csvContent = data[i];
                csvWriter.writeRecord(csvContent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
