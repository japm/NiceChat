/**
 * Copyright (C) 2014  Juan Pascual
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package uyjj.nicechat.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import uyjj.nicechat.NiceChatProtos;
import uyjj.nicechat.R;

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
