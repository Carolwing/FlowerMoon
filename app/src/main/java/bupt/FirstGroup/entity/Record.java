package bupt.FirstGroup.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Record {
    private String time;
    private int score;
    private int grade;
    private String name;

    public Record(int score, int grade) {
        //当本地结束游戏后创建Record对象的构造函数。
        //直接获取当地登录用户的用户名。
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        this.time = df.format(new Date());
        this.score = score;
        this.grade = grade;
        this.name = CurrentUser.getName();
    }

    public Record() {

    }

    public Record(String time, int score, int grade, String name) {
        //从数据库获取排行榜数据后创建Record对象
        this.time = time;
        this.score = score;
        this.grade = grade;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Record{" +
                "time='" + time + '\'' +
                ", score=" + score +
                ", grade=" + grade +
                ", name='" + name + '\'' +
                '}';
    }

    public String[] getParam() {
        return new String[]{time, String.valueOf(score), String.valueOf(grade), name};
    }
}
