package synergy.rottco.bullet.black.rottcosynergy;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
//        ViewHolderHeader holder=null;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        ModelDrawerItems drawerItem = (ModelDrawerItems) getItem(position);
        if(drawerItem.getItemType()==ModelDrawerItems.TITLE)
        {
            convertView = mInflater.inflate(R.layout.drawer_title, null);
            convertView.setOnClickListener(null);
            ViewHolderTitle holder = new ViewHolderTitle(convertView);
            holder.title.setOnClickListener(null);
            holder.title.setEnabled(false);
        }
        else if(drawerItem.getItemType()==ModelDrawerItems.IMAGE)
        {
            convertView = mInflater.inflate(R.layout.drawer_image_item, null);
            convertView.setOnClickListener(null);
            ViewHolderImage holder = new ViewHolderImage(convertView);
            holder.image.setOnClickListener(null);
            holder.image.setEnabled(false);
        }
        else if(drawerItem.getItemType()==ModelDrawerItems.HEADER)
        {
            convertView = mInflater.inflate(R.layout.drawer_header, null);
            convertView.setOnClickListener(null);
            ViewHolderHeader holder = new ViewHolderHeader(convertView);
            holder.tvHeader.setText(drawerItem.getText());
            holder.tvHeader.setOnClickListener(null);
            holder.tvHeader.setEnabled(false);
        }
        else if(drawerItem.getItemType()==ModelDrawerItems.LIST_ITEM)
        {
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
            ViewHolderListItem holder = new ViewHolderListItem(convertView);
            holder.tvListItem.setText(drawerItem.getText());
        }
        else
        {
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
            ViewHolderListItem holder = new ViewHolderListItem(convertView);
            holder.tvListItem.setText(drawerItem.getText());
        }

//        if(drawerItem.getItemType()==ModelDrawerItems.HEADER)
//        {
//            holder.text.setTextColor(Color.parseColor("#E2A59F"));
//            holder.text.setTypeface(null, Typeface.BOLD);
//            holder.text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//        }
//        else if(drawerItem.getItemType()==ModelDrawerItems.LIST_ITEM)
//        {
//            int paddingPixel = 5;
//            float density = mContext.getResources().getDisplayMetrics().density;
//            int paddingDp = (int)(paddingPixel * density);
//
//
//            holder.text.setTextColor(Color.WHITE);
//            holder.text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
//            holder.text.setPadding(paddingDp,0,0,0);
//        }
      //  holder.text.setText(drawerItem.getText());
        //holder.surnameTextView.setText(person.getSurname());
        //holder.personImageView.setImageBitmap(person.getImage());

        return convertView;
    }
    static class ViewHolderHeader {
        private TextView tvHeader;

        ViewHolderHeader(View view) {
            tvHeader = view.findViewById(R.id.tvDrawerHeader);
        }

    }
    static class ViewHolderListItem {
        private TextView tvListItem;
        ViewHolderListItem(View view) {
            tvListItem = view.findViewById(R.id.tvDrawerListItem);
        }
    }

    static class ViewHolderImage {
        private ImageView image;
        ViewHolderImage(View view) {
            image = view.findViewById(R.id.ivRottcoBy);
        }
    }
    static class ViewHolderTitle {
        private TextView title;
        ViewHolderTitle(View view) {
            title = view.findViewById(R.id.tvDrawerTitle);
        }
    }
}
