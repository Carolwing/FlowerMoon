package bupt.FirstGroup.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Record {
    private String time;
    private int score;
    private int grade;
    private String name;
    public static final String EASY_TAG = "easy";
    public static final String MED_TAG = "medium";
    public static final String HARD_TAG = "hard";

    public Record(int score, String diff) {
        //当本地结束游戏后创建Record对象的构造函数。
        //直接获取当地登录用户的用户名。
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        this.time = df.format(new Date());
        this.score = score;
        this.name = CurrentUser.getName();
        switch (diff) {
            case EASY_TAG:
                this.grade = 1;
                break;
            case MED_TAG:
                this.grade = 2;
                break;
            case HARD_TAG:
                this.grade = 3;
                break;
        }
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
