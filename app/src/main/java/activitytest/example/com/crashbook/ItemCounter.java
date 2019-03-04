package activitytest.example.com.crashbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ItemCounter extends AppCompatActivity {

    private Toolbar toolbar;

    private List<Item> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.counter);

        toolbar = (Toolbar) findViewById(R.id.toolbar_counter);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Calendar cal = Calendar.getInstance();
        day_count(cal.get(Calendar.DATE));
        month_count(cal.get(Calendar.MONTH));
        year_count(cal.get(Calendar.YEAR));
    }

    void day_count(int day){
        itemList = DataSupport.where("day == ?", day+"")
                .find(Item.class);
        float paycount = 0;
        float savecount = 0;
        for (Item i:itemList){
            switch(i.getCategory()) {
                case 0: savecount+=i.getMoney();
                    break;
                default: paycount+=i.getMoney();
            }
        }
        TextView daypay = (TextView) findViewById(R.id.daypay);
        TextView daysave = (TextView) findViewById(R.id.daysave);
        daypay.setText((paycount+""));
        daysave.setText((savecount+""));
    }

    void month_count(int month){
        itemList = DataSupport.where("month == ?", month+"")
                .find(Item.class);
        float paycount = 0;
        float savecount = 0;
        for (Item i:itemList){
            switch(i.getCategory()) {
                case 0: savecount+=i.getMoney();
                    break;
                default: paycount+=i.getMoney();
            }
        }
        TextView monthpay = (TextView) findViewById(R.id.monthpay);
        TextView monthsave = (TextView) findViewById(R.id.monthsave);
        monthpay.setText((paycount+""));
        monthsave.setText((savecount+""));
    }

    void year_count(int year){
        itemList = DataSupport.where("year == ?", year+"")
                .find(Item.class);
        float paycount = 0;
        float savecount = 0;
        for (Item i:itemList){
            switch(i.getCategory()) {
                case 0: savecount+=i.getMoney();
                    break;
                default: paycount+=i.getMoney();
            }
        }
        TextView yearpay = (TextView) findViewById(R.id.yearpay);
        TextView yearsave = (TextView) findViewById(R.id.yearsave);
        yearpay.setText((paycount+""));
        yearsave.setText((savecount+""));
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar_counter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
        }
        return true;
    }

    public static void counterstart(Context context){
        Intent intent = new Intent(context, ItemCounter.class);
        context.startActivity(intent);
    }
}
