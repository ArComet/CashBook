package activitytest.example.com.crashbook;

import org.litepal.crud.DataSupport;

import java.util.Calendar;

public class Item extends DataSupport {
    private Calendar calendar;
    private int money;
    private int category;

    public Item(Calendar calendar, int money,int category){
        this.money = money;
        this.calendar = calendar;
        this.category = category;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getMoney() {
        return money;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public int getCategory() {
        return category;
    }
}
