package activitytest.example.com.crashbook;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private Context mContext;

    private List<Item> mitemList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView itemDate;
        TextView itemName;
        TextView itemMoney;

        public ViewHolder(View view){
            super(view);
            cardView = (CardView) view;
            itemDate = (TextView) view.findViewById(R.id.item_date);
            itemName = (TextView) view.findViewById(R.id.item_name);
            itemMoney = (TextView) view.findViewById(R.id.item_money);
        }


    }

    public ItemAdapter(List<Item> itemList){
        mitemList=itemList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_layout,
                parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Item item = mitemList.get(position);
                ItemEditor.actionstart(1,mContext,item.getCalendar(),item.getMoney(),item.getCategory());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item item = mitemList.get(position);
        Calendar cal = item.getCalendar();
        String string;
        string=(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DATE);
        holder.itemDate.setText(string);
        holder.itemMoney.setText(item.getMoney());
        switch (item.getCategory()){
            case 0: holder.itemName.setText("收入");
                break;
            case 1: holder.itemName.setText("餐饮");
                break;
            case 2: holder.itemName.setText("购物");
                break;
            case 3: holder.itemName.setText("旅游");
                break;
            default:
        }
        switch (item.getCategory()){
            case 0:
                string="+"+item.getMoney();
                holder.itemMoney.setText(string);
                holder.itemMoney.setTextColor(Color.parseColor("#0080FF"));
                break;
            case 1:
                string="-"+item.getMoney();
                holder.itemMoney.setText(string);
                holder.itemMoney.setTextColor(Color.parseColor("#FF0000"));
                break;
            default:
        }
    }

    @Override
    public int getItemCount() {
        return mitemList.size();
    }
}
