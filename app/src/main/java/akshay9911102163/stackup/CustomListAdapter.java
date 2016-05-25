package akshay9911102163.stackup;

/**
 * Created by Akshay Khanna on 17-04-2016.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.text.SimpleDateFormat;
import java.util.List;

public class CustomListAdapter  extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<FetchedItem> fetchedItemList;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListAdapter(Activity activity, List<FetchedItem> fetchedItemList) {
        this.activity = activity;
        this.fetchedItemList = fetchedItemList;
    }

    @Override
    public int getCount() {
        return fetchedItemList.size();
    }

    @Override
    public Object getItem(int location) {
        return fetchedItemList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_single_item, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView score = (TextView) convertView.findViewById(R.id.score);
        TextView tags = (TextView) convertView.findViewById(R.id.tags);
        TextView time = (TextView) convertView.findViewById(R.id.tv_last_actvivty_time);

        // getting movie data for the row
        //Movie m = movieItems.get(position);

       FetchedItem item = fetchedItemList.get(position);

        // thumbnail image
        thumbNail.setImageUrl(item.getProfile_img(), imageLoader);
        //thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

        // title
        String custTitle=item.getTitle();

        if(custTitle.length()>34)
        {
            custTitle+=" ";

            int endIndex=custTitle.substring(33).indexOf(' ')+33;
            custTitle=custTitle.substring(0,endIndex)+" ...";

        }
        title.setText( custTitle);
        //title.setText(m.getTitle());

        // score
        score.setText("score: " + item.getScore());
        //rating.setText("Rating: " + String.valueOf(m.getRating()));

        // tags
        String tagsStr = "";
        for (String str : item.getTag_list()) {
            tagsStr += str + ", ";
        }
        tagsStr =tagsStr.length() > 0 ? tagsStr.substring(0,
                tagsStr.length()-1) :tagsStr;
        tags.setText(tagsStr);
        /*String genreStr = "";
        for (String str : m.getGenre()) {
            genreStr += str + ", ";
        }
        genreStr = genreStr.length() > 0 ? genreStr.substring(0,
                genreStr.length() - 2) : genreStr;
        genre.setText(genreStr);*/

        // release year
        time.setText(String.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(item.getLast_activity_date())));

        return convertView;
    }

}