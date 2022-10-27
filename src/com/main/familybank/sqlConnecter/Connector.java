package src.com.main.familybank.sqlConnecter;

import src.com.main.familybank.Datas.Datas;
import src.com.main.familybank.account.accounts.Account;
import src.com.main.familybank.bill.bills.Bill;

import java.io.*;
import java.sql.*;
import java.util.Vector;

public class Connector {
    /**
     * mysql的链接
     */
    private static String url;
    /**
     * 数据库账号
     */
    private static String user;
    /**
     * 数据库密码
     */
    private static String password;
    /**
     * 与数据库的连接对象
     */
    private static Connection conn = null;
    private static boolean is_init = false;

    /**
     * 用于初始化数据库，程序必须先执行此方法
     *
     * @return 状态值，
     * 0 ：正常初始化
     * -1：；类检查失败
     * -2：连接失败
     */
    public static int init() {
        //读取文件
        File sqlAccount = new File("FamilyBank/sqlAccount.txt");

        FileReader fr = null;
        BufferedReader br = null;

        try {
            fr = new FileReader(sqlAccount);
            br = new BufferedReader(fr);

            url = br.readLine();
            user = br.readLine();
            password = br.readLine();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            Class.forName("com.mysql.jdbc.Driver"); //检查类是否可用
            System.out.print("[INFO][SQL CONNECT]:check package successful :");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("[INFO][SQL CONNECT]:unable to check package");
            return -1;
        }
        try {
            conn = DriverManager.getConnection(url, user, password);    //连接数据库
            System.out.println("[INFO][SQL CONNECT]:get conn = " + conn);
        } catch (SQLException throw_ables) {
            throw_ables.printStackTrace();
            System.out.println("[INFO][SQL CONNECT]:unable to connect to " + url);
            return -2;
        }
        is_init = true;
        return 0;
    }

    /**
     * @return true  : 已经初始化
     * false : 未初始化
     */
    public static boolean isInit() {
        return is_init;
    }

    /**
     * @param UID  :账号
     * @param PASS :密码
     * @return true  : 密码正确并登陆
     * false : 密码错误
     */
    public static boolean login(String UID, String PASS) {
        String sql = "SELECT * FROM account WHERE userName = '" + UID + "'";
        Statement stat = null;
        ResultSet rs = null;

        try {
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);

            if (rs.next()) {
                String getId = rs.getString("userId");
                String getUserName = rs.getString("userName");
                String getPass = rs.getString("password");
                String getRealName = rs.getString("realName");
                if (getPass.equals(PASS)) {
                    System.out.println("[INFO][CONNECTOR]:PASSWORD MATCH SUCCESSFUL");
                    Datas.account = new Account(getId, getUserName, getPass, getRealName);
                    System.out.println("[INFO][CONNECTOR]: GET ACCOUNT DATA :" + Datas.account);
                    return true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                stat.close();
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        System.out.println("[INFO][CONNECTOR]:PASSWORD MATCH UNSUCCESSFUL");
        return false;
    }

    /**
     * @param date 日期参数，如20220509代表2022年5月9号
     * @param args 1 全部收入账单 0全部支出账单
     * @param str  用于返回状态字符串
     * @return 总和的绝对值
     */
    public static double getPayByDay(long date, int args, String[] str) {
        return getPayByDay(date, args, str, new Vector<String>(), new Vector<Double>());
    }


    public static double getPayByDay(long date, int args, String[] str, Vector<String> s, Vector<Double> d) {
        String sql = "select money, category from bill WHERE bill.userId = '" + Datas.account.getUserId() + "' AND (time >= " + (date * 1000000) + " AND time < " + (date * 1000000 + 240000) + ")";
        Statement stat = null;
        ResultSet rs = null;
        double num = 0;
        double temp;
        String tempCate;
        str[0] = "";

        int counts = 0;

        try {
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);

            if (args == 1) {
                while (rs.next()) {
                    if ((temp = Double.parseDouble(rs.getString("money"))) > 0) {
                        num += temp;
                        counts++;
                        tempCate = rs.getString("category");
                        if (!s.contains(tempCate)) {
                            s.add(tempCate);
                            d.add(temp);
                        } else {
                            d.set(s.indexOf(tempCate), d.elementAt(s.indexOf(tempCate)) + temp);
                        }
                    }
                }
            }
            if (args == 0) {
                while (rs.next()) {
                    if ((temp = Double.parseDouble(rs.getString("money"))) < 0) {
                        num += temp;
                        counts++;
                        tempCate = rs.getString("category");
                        if (!s.contains(tempCate)) {
                            s.add(tempCate);
                            d.add(Math.abs(temp));
                        } else {
                            d.set(s.indexOf(tempCate), d.elementAt(s.indexOf(tempCate)) + Math.abs(temp));
                        }
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                stat.close();
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        if (s.size() == 0) {
            str[0] += "今日无记录,";
        } else if (s.size() < 5) {
            for (int i = 0; i < s.size(); i++) {
                str[0] += s.elementAt(i) + ",";
            }
        } else {
            for (int i = 0; i < 5; i++) {
                str[0] += s.elementAt(i) + ",";
            }
            str[0] += "等";
        }

        str[0] += "共 " + counts + " 笔！";

        System.out.println("[INFO][CONNECTOR]:GET ALL SUM OF BILL");

        return ((double) Math.round(Math.abs(num) * 100)) / 100;
    }

    /**
     * 用于向数据库提交注册信息
     *
     * @param userName 要注册的用户名（不重复）
     * @param password 要注册的密码
     * @param realName 要注册者的真名
     * @return boolean 类型，用于判断是否注册成功
     */
    public static boolean register(String userName, String password, String realName) {

        if (isExist(userName)) {
            System.out.println("[INFO][CONNECTOR][WRING]:USERNAME HAS BEEN USED");
            return false;
        }
        String sql = "INSERT INTO `account` ( `userName`, `password`, `realName`) VALUES ( '" + userName + "', '" + password + "', '" + realName + "')";

        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }


        return true;
    }

    /**
     * 用于判断用户名是否已经存在
     *
     * @param userName 待判断的用户名
     * @return boolean 类型，是否已经存在
     */
    private static boolean isExist(String userName) {

        String sql = "SELECT userId FROM account WHERE userName = '" + userName + "'";
        Statement stat = null;
        ResultSet rs = null;

        try {
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            if (rs.next()) {
                System.out.println("[INFO][CONNECTOR]:USERNAME IS ALREADY EXIST");
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                stat.close();
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        System.out.println("[INFO][CONNECTOR]:USERNAME IS NOT EXIST");
        return false;
    }

    /**
     * 用于向数据库内添加bill
     *
     * @param bill 传入待添加bill
     */
    public static void addBills(Bill bill) {
        String sql = "INSERT INTO `bill` (`userId`, `time`, `money`, `category`, `details`) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, Datas.account.getUserId());
            ps.setLong(2, bill.getTime());
            ps.setDouble(3, bill.getMoney());
            ps.setString(4, bill.getCategory());
            ps.setString(5, bill.getDetails());

            ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        System.out.println("[INFO][CONNECTOR]:USERNAME IS NOT EXIST");
    }

    /**
     * 加载用户的全部账单数据到data的Bill中
     */
    public static void loadBills() {
        String getCountSql = "SELECT count(userId) FROM bill WHERE userId = '" + Datas.account.getUserId() + "'";
        Statement getCountStat = null;
        ResultSet getCountRs = null;
        int count = 0;

        String sql = "SELECT time, money, category, details FROM bill WHERE userId = '" + Datas.account.getUserId() + "'";
        Statement stat = null;
        ResultSet rs = null;

        try {
            getCountStat = conn.createStatement();
            getCountRs = getCountStat.executeQuery(getCountSql);
            if (getCountRs.next()) {
                count = Integer.parseInt(getCountRs.getString("count(userId)"));
                System.out.println("[INFO][CONNECTOR]:GET NUMBER OF BILLS IS: " + count);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                getCountStat.close();
                getCountRs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        if (count == 0) {
            return;
        }

        Bill[] bills = new Bill[count];
        int i = 0;

        try {
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            while (rs.next()) {
                bills[i] = new Bill(Datas.account.getUserId(), rs.getLong("time"), rs.getDouble("money"), rs.getString("category"), rs.getString("details"));

                i++;
            }
            Datas.bill = bills;
            System.out.println("[INFO][CONNECTOR]:BILL DATA IS LOAD SUCCESSFUL");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                stat.close();
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }


    }
}
