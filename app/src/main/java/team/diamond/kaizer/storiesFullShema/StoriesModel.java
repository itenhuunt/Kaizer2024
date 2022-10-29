package team.diamond.kaizer.storiesFullShema;


public class StoriesModel {

    String stories, time, name, cost, sell, turl;

    public StoriesModel() {
        //empty
    }



    public StoriesModel(String stories, String time, String name, String cost, String sell) {
        this.stories = stories;
        this.time = time;
        this.name = name;
        this.cost = cost;
        this.sell = sell;
        this.turl = turl;
    }


    public String getStories() {
        return stories;
    }

    public void setStories(String stories) {
        this.stories = stories;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getSell() {
        return sell;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }

    public String getTurl() {
        return turl;
    }

    public void setTurl(String turl) {
        this.turl = turl;
    }
}
