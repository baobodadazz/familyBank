package src.com.main.familybank.bill.bills;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class Bill {

    private final SimpleStringProperty userId;
    private final SimpleLongProperty time;
    private final SimpleDoubleProperty money;
    private final SimpleStringProperty category;
    private final SimpleStringProperty details;

    public String getUserId() {
        return userId.get();
    }

    public void setUserId(String userId) {
        this.userId.set(userId);
    }

    public long getTime() {
        return time.get();
    }

    public void setTime(long time) {
        this.time.set(time);
    }

    public double getMoney() {
        return money.get();
    }

    public void setMoney(double money) {
        this.money.set(money);
    }

    public String getCategory() {
        return category.get();
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public String getDetails() {
        return details.get();
    }

    public void setDetails(String details) {
        this.details.set(details);
    }

    public Bill(String userId, long time, double money, String category, String details) {
        this.userId = new SimpleStringProperty(userId);
        this.time = new SimpleLongProperty(time);
        this.money = new SimpleDoubleProperty(money);
        this.category = new SimpleStringProperty(category);
        this.details = new SimpleStringProperty(details);
    }

    public SimpleStringProperty getUserIdProperty(){
        return userId;
    }

    public SimpleLongProperty getTimeProperty(){
        return time;
    }

    public SimpleDoubleProperty getMoneyProperty(){
        return money;
    }

    public SimpleStringProperty getCategoryProperty(){
        return category;
    }

    public SimpleStringProperty getDetailsProperty(){
        return details;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "userId='" + getUserId() + '\'' +
                ", time=" + getTime() +
                ", money=" + getMoney() +
                ", category='" + getCategory() + '\'' +
                ", details='" + getDetails() + '\'' +
                '}';
    }
}
