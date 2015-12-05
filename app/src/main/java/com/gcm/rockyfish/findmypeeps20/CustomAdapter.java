package com.gcm.rockyfish.findmypeeps20;

/**
 * Created by RockyFish on 8/18/15.
 */
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class CustomAdapter extends BaseAdapter {
    ArrayList<String> result;
    Context context;
    ArrayList<Bitmap> imageId = new ArrayList<>();
    ArrayList<ImageView> imageViews = new ArrayList<>();
    ArrayList<String> usernames;
    ArrayList<String> dates;
    ArrayList<String> times;
    ArrayList<Integer> positions = new ArrayList<>();
    ArrayList<add_image> addImages = new ArrayList<>();
    ArrayList<LoadingGif> gifViews = new ArrayList<>();
    LoadingGif loadingGif;
    ImageView imageView;
    int total = 0;
    int position;
    private static LayoutInflater inflater=null;
    public CustomAdapter(FragmentActivity mainActivity, ArrayList<String> prgmNameList, ArrayList<String> prgrmUsernames, ArrayList<String> dates, ArrayList<String> times) {
        // TODO Auto-generated constructor stub
        result =prgmNameList;
        context=mainActivity;
        usernames = prgrmUsernames;
        this.dates = dates;
        this.times = times;
        this.imageViews = null;
        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.size();
    }

    public ArrayList<add_image> getImageId()
    {
        return addImages;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
        TextView username;
        TextView date;
        TextView time;
        LoadingGif loadingGif;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.status_list_items, null);
        holder.tv = (TextView) rowView.findViewById(R.id.notificationTextView);
        holder.img = (ImageView) rowView.findViewById(R.id.profilePic);
        holder.loadingGif = (LoadingGif) rowView.findViewById(R.id.LoadingGif);
        this.position = position;

        if(!positions.contains(position))
        {
            this.position = position;
            loadingGif = holder.loadingGif;
            imageView = holder.img;
            addImages.add(this.position, new add_image());
            addImages.get(this.position).execute();
        } else {
            holder.loadingGif.setVisibility(View.INVISIBLE);
            holder.img.setVisibility(View.VISIBLE);
            holder.img.setImageBitmap(imageId.get(position));
        }
        holder.username = (TextView) rowView.findViewById(R.id.status_usernameTextView);
        holder.date = (TextView) rowView.findViewById(R.id.dateTextView);
        holder.time = (TextView) rowView.findViewById(R.id.timeTextView);
        holder.username.setText(usernames.get(position));
        holder.tv.setText(result.get(position));
       // holder.img.setImageBitmap(imageId.get(position));
        dates.set(position, dates.get(position));
        holder.date.setText(getDate(dates.get(position)));
        holder.time.setText(times.get(position));
        return rowView;
    }

    class add_image extends AsyncTask<Void, Void, Bitmap>
    {
        ImageView imageVieww = null;
        LoadingGif loadingView = null;
        Bitmap userIcon = null;
        int positionss;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            positionss = position;
            this.imageVieww = imageView;
            this.loadingView = loadingGif;
        }

        @Override
        protected Bitmap doInBackground(Void... imageViews) {

                String urldisplay = "http://skyrealmstudio.com/img/" + usernames.get(positionss).toLowerCase() + ".jpg";
                InputStream in = null;
                try {
                    in = new URL(urldisplay).openStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                userIcon = BitmapFactory.decodeStream(in);
                //make the icon a circle,'
                userIcon = userIcon.createScaledBitmap(userIcon, userIcon.getWidth(), userIcon.getHeight(), false);
            return userIcon;
        }

        protected void onPostExecute(Bitmap result)
        {
            imageId.add(positionss, result);
            positions.add(positionss);
            this.loadingView.setVisibility(View.INVISIBLE);
            this.imageVieww.setVisibility(View.VISIBLE);
            this.imageVieww.setImageBitmap(result);
        }
    }

    public String getDate(String date) {

        String month = date.substring(0, 2);
        String day = date.substring(3, 5);
        total = 0;
        for (int i = 1; i < Integer.parseInt(month) + 1; i++) {
            total = total + get_month_days(i);
        }
        total = total + Integer.parseInt(day);
        SimpleDateFormat todaysDateC = new SimpleDateFormat("MM-dd");
        String todaysDate = todaysDateC.format(new Date());
        String todaysMonth = todaysDate.substring(0, 2);
        String todaysDay = todaysDate.substring(3, 5);
        int todaysTotal = 0;
        for (int i = 1; i < Integer.parseInt(todaysMonth) + 1; i++) {
            todaysTotal = todaysTotal + get_month_days(i);
        }
        todaysTotal = todaysTotal + Integer.parseInt(todaysDay);

            if (day.equals(todaysDay)) {
            return "Today";
        } else if (Integer.parseInt(day) == Integer.parseInt(todaysDay) -1) {
                return "1 day ago";
            } else {
            int days = todaysTotal - total;
                if(days != 1)
                {
                    return String.valueOf(days) + " day(s) ago";
                }
        }

        return null;
    }

    public int get_month_days(int month)
    {
        switch (month) {
            case 1:
                return 31;
            case 2:
                return 28;
            case 3:
                return 31;
            case 4:
                return 30;
            case 5:
                return 31;
            case 6:
                return 30;
            case 7:
                return 31;
            case 8:
                return 31;
            case 9:
                return 30;
            case 10:
                return 31;
            case 11:
                return 30;
            case 12:
                return 31;
            default:
                break;
        }
        return 0;
    }

}