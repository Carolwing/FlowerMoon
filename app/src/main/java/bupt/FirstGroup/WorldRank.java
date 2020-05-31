package bupt.FirstGroup;


public class WorldRank {

    public static String getString() {
        return "WorldRank.result{ " +
                result[0][0] +
                ", " + result[0][1] +
                ", " + result[0][2] +
                ", " + result[0][3] + " }"
                ;
    }

    public static String[][] result = new String[3][4];

    public static void setResult(String[][] r) {
        WorldRank.result = r;
    }

    public static String[][] getResult() {
        return result;
    }


}
