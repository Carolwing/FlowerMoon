package bupt.FirstGroup;


public class WorldRank {
    static String[][] result = new String[3][4];

    public static void setResult(String[][] r) {
        WorldRank.result = r;
    }

    public static String[][] getResult() {
        return result;
    }
}
/*
result[1] = db.findMaxScore(1);
                result[2] = db.findMaxScore(2);
                result[3] = db.findMaxScore(3);
 */