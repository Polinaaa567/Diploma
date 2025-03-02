package local.arch.controllers_admin.classes;

public class lessonsData {
    
    private String headline;
    private String link;
    private Integer numberPointsForLesson;

    public String getHeadline() { 
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getNumberPointsForLesson() {
        return numberPointsForLesson;
    }

    public void setNumberPointsForLess(Integer numberPointsForLesson) {
        this.numberPointsForLesson = numberPointsForLesson;
    }
}
