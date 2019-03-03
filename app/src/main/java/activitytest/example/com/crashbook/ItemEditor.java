package activitytest.example.com.crashbook;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.Calendar;

public class ItemEditor extends AppCompatActivity {
    private static Toolbar toolbar;
    private static DatePicker datePicker;
    private static ImageView imageView;
    private static EditText editText;

    private int id;
    private int mode;//0-新建条目 1-编辑条目
    private int year;
    private int month;
    private int day;
    private float money;
    private int category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editor);

        //数据初始化
        Intent intent = this.getIntent();
        mode = intent.getIntExtra("mode",0);
        category = intent.getIntExtra("category",0);
        money = intent.getFloatExtra("money",0);
        switch (mode){
            case 0:
                Calendar cal = Calendar.getInstance();
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DATE);
                break;
            case 1:
                id = intent.getIntExtra("id",0);
                year = intent.getIntExtra("year",0);
                month = intent.getIntExtra("month",0);
                day = intent.getIntExtra("day",0);
                break;
            default:
        }

        //toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_editor);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //日期选择控件
        datePicker = (DatePicker) findViewById(R.id.date_picker);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Toast.makeText(ItemEditor.this, year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日", Toast.LENGTH_SHORT).show();
            }
        });

        //通过image切换category
        imageView =(ImageView) findViewById(R.id.item_image);
        showimage(category);
        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                category = (category+1)%4;
                showimage(category);
            }
        });

        editText = (EditText) findViewById(R.id.item_money);
        editText.setText((money+""));

        //保存按钮
        Button savebutton = (Button) findViewById(R.id.save_button);
        savebutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                money = Float.parseFloat(editText.getText().toString());
                //数据库保存
                switch (mode){
                    case 0:
                        Item item = new Item();
                        item.setYear(year);
                        item.setMonth(month);
                        item.setDay(day);
                        item.setMoney(money);
                        item.setCategory(category);
                        item.save();
                        break;
                    case 1:
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("year",year);
                        contentValues.put("month",month);
                        contentValues.put("day",day);
                        contentValues.put("money",money);
                        contentValues.put("category",category);
                        DataSupport.update(Item.class,contentValues,id);
                        break;
                    default:
                }
                //提示信息
                switch (mode){
                    case 0:
                        Toast.makeText(ItemEditor.this, "添加成功", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(ItemEditor.this, "修改成功", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                }
                finish();
            }
        });
    }

    void showimage(int category){
        TextView title = (TextView) findViewById(R.id.item_title);
        TextView name = (TextView) findViewById(R.id.item_name);
        switch (category){
            case 0:
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_money));
                title.setText("  收入  ¥");
                name.setText("");
                break;
            case 1:
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_diet));
                title.setText("  支出  ¥");
                name.setText("餐饮");
                break;
            case 2:
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_shop));
                title.setText("  支出  ¥");
                name.setText("购物");
                break;
            case 3:
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_travel));
                title.setText("  支出  ¥");
                name.setText("旅游");
                break;
            default:
        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //设置编辑器标题
        if (toolbar != null) {
            switch (mode){
                case 0:toolbar.setTitle("新建条目");break;
                case 1:toolbar.setTitle("编辑条目");break;
                default:
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar_editor, menu);
        MenuItem menuItem = menu.findItem(R.id.delete);
        switch (mode){
            case 0: menuItem.setVisible(false);
                break;
            case 1:
                break;
            default:
        }
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

    public static void actionstart(int mode,Context context, int year, int month,
                                    int day ,float money, int category,int id){
        Intent intent = new Intent(context, ItemEditor.class);
        intent.putExtra("mode",mode);
        intent.putExtra("year",year);
        intent.putExtra("month",month);
        intent.putExtra("day",day);
        intent.putExtra("money",money);
        intent.putExtra("category",category);
        if (mode == 1) intent.putExtra("id",id);
        context.startActivity(intent);
    }
}