package synergy.rottco.bullet.black.rottcosynergy;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by boghi on 12/9/2017.
 */

public class DrawerAdapter extends BaseAdapter{

    private Context mContext;
    private List<ModelDrawerItems> dataSource;
    public DrawerAdapter(Context context, List<ModelDrawerItems> dataSource)
    {
        this.mContext=context;
        this.dataSource=dataSource;
    }
    @Override
    public int getCount() {
        return dataSource.size();
    }

    @Override
    public Object getItem(int i) {
        return dataSource.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.tvDrawerListItem);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        ModelDrawerItems drawerItem = (ModelDrawerItems) getItem(position);

        if(drawerItem.getItemType()==ModelDrawerItems.HEADER)
        {
            holder.text.setTextColor(Color.parseColor("#E1989D"));
        }
        else if(drawerItem.getItemType()==ModelDrawerItems.LIST_ITEM)
        {
            holder.text.setTextColor(Color.WHITE);
        }
        holder.text.setText(drawerItem.getText());
        //holder.surnameTextView.setText(person.getSurname());
        //holder.personImageView.setImageBitmap(person.getImage());

        return convertView;
    }
    static class ViewHolder {
        private TextView text;

    }
}
