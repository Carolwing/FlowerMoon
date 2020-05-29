package bupt.FirstGroup.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Record {
    private String time;
    private int score;
    private int grade;
    private String name;

    public Record(int score, int grade) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy--MM-dd");
        this.time=df.format(new Date());
        this.score = score;
        this.grade = grade;
        this.name = CurrentUser.getName();
    }
}
