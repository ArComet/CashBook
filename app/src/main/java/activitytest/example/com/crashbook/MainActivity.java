package activitytest.example.com.crashbook;

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

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView hinttext;

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
                ItemEditor.editorstart(0,MainActivity.this,
                        0,0,0,0,0,0);
            }
        });

        //创建数据库&滚动列表
        itemList = DataSupport.findAll(Item.class);
        TextView hinttext = (TextView) findViewById(R.id.hinttext);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        initdata();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initdata();
    }

    void initdata(){
        //刷新数据库
        itemList = DataSupport.findAll(Item.class);
        //空列表提示
        hinttext = (TextView) findViewById(R.id.hinttext);
        if (!itemList.isEmpty())
            hinttext.setVisibility(View.INVISIBLE);
        else
            hinttext.setVisibility(View.VISIBLE);
        //刷新列表
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
            case R.id.crash_count:
                ItemCounter.counterstart(this);
                break;
            case R.id.crash_find:
                break;
            default:
        }
        return true;
    }
}
