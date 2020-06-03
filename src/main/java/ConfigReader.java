import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

public class ConfigReader {

    private String table1;
    private String table2;

    public String getTable1() {
        return table1;
    }

    public String getTable2() {
        return table2;
    }

    public void setTable1(String table1) {
        this.table1 = table1;
    }

    public void setTable2(String table2) {
        this.table2 = table2;
    }
}