package akshay9911102163.stackup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Akshay Khanna on 17-04-2016.
 */
public class FetchedItem
{
    private String profile_img;
    private String display_name;
    private int score;
    private Date last_activity_date;
    private String link;
    private String title;
    private List<String> tag_list;

    public FetchedItem() {
        this.tag_list = new ArrayList<String>();
    }

    public FetchedItem(String profile_img, String display_name, int score, Date last_activity_date, String link, String title, List<String> tag_list) {
        this.profile_img = profile_img;
        this.display_name = display_name;
        this.score = score;
        this.last_activity_date = last_activity_date;
        this.link = link;
        this.title = title;
        this.tag_list = tag_list;
    }

    public String getProfile_img() {
        return profile_img;
    }

    public void setProfile_img(String profile_img) {
        this.profile_img = profile_img;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getLast_activity_date() {
        return last_activity_date;
    }

    public void setLast_activity_date(Date last_activity_date) {
        this.last_activity_date = last_activity_date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTag_list(ArrayList<String> tag_list) {
        this.tag_list = tag_list;
    }

    public List<String> getTag_list() {
        return tag_list;
    }
}
