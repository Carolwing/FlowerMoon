package bupt.FirstGroup.entity;

public class CurrentUser {
    private static String currentUser;

    public static void setName(String name) {
        currentUser = name;
    }

    public static String getName() {
        return currentUser;
    }
}

