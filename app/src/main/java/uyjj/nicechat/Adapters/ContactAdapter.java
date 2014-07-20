package uyjj.nicechat.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import uyjj.nicechat.NiceChatProtos;
import uyjj.nicechat.R;

/**
 * Created by juan on 7/19/14.
 */
public class ContactAdapter extends ArrayAdapter<NiceChatProtos.Contact> {

    private final Activity mContext;
    private List<NiceChatProtos.Contact> mContacts;


    static class ViewHolder {
        public TextView text;
        public ImageView image;
    }


    public ContactAdapter(Activity context, int resource, List<NiceChatProtos.Contact> contacts) {
        super(context, resource, contacts);
        this.mContext = context;
        this.mContacts = contacts;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = this.mContext.getLayoutInflater();
            rowView = inflater.inflate(R.layout.contacts_layout, null);
            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) rowView.findViewById(R.id.textContact);
            viewHolder.image = (ImageView) rowView
                    .findViewById(R.id.iconContact);
            rowView.setTag(viewHolder);
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        String s = mContacts.get(position).getName();
        holder.text.setText(s);
        holder.image.setImageResource(R.drawable.ic_launcher);

        return rowView;
    }

    public void setContacts(List<NiceChatProtos.Contact> contacts) {
        this.mContacts = contacts;
    }

}
