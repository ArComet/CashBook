package activitytest.example.com.crashbook;

import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static TextView paytext;
    private static TextView savetext;
    private static RecyclerView recyclerView;

    private List<Item> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //悬浮添加按钮
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                ItemEditor.actionstart(0,MainActivity.this,
                        cal,0,0);
            }
        });

        //数据库
        LitePal.getDatabase();
        itemList = DataSupport.findAll(Item.class);

        //滚动列表
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ItemAdapter adapter = new ItemAdapter(itemList);
        recyclerView.setAdapter(adapter);

        //支出&收入统计
        paytext = (TextView) findViewById(R.id.paycount);
        savetext = (TextView) findViewById(R.id.savecount);
        SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
        String paycount = pref.getInt("paycount",0)+"";
        String savecount = pref.getInt("savecount",0)+"";
        paytext.setText(paycount);
        savetext.setText(savecount);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //刷新统计
        SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
        String paycount = pref.getInt("paycount",0)+"";
        String savecount = pref.getInt("savecount",0)+"";
        paytext.setText(paycount);
        savetext.setText(savecount);

        //刷新数据库
        itemList = DataSupport.findAll(Item.class);

        //刷新滚动列表
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ItemAdapter adapter = new ItemAdapter(itemList);
        recyclerView.setAdapter(adapter);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.cash_count:
                break;
            case R.id.cash_find:
                break;
            default:
        }
        return true;
    }
}
