package src.com.main.familybank.account.accounts;

public class Account {
    private static int idNum = 100000;

    /**
     * a string for family user's personal ID
     * which is only one, is the kew word of this table
     */
    private String userId;
    private String userName;
    private String password;
    private String realName;

    private double budget;

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    {
        budget = 0;
    }

    public Account() {
    }

    public static int getIdNum() {
        idNum++;
        return idNum;
    }

    public static void setIdNum(int idNum) {
        Account.idNum = idNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Account(String userId, String userName, String password, String realName) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.realName = realName;
    }

    @Override
    public String toString() {
        return "Account{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", realName='" + realName + '\'' +
                '}';
    }
}
