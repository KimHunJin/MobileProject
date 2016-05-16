package sungkyul.ac.kr.leeform.items;

/**
 * Created by user on 2016-05-16.
 */
public class CommunityItem {
    private int cNumber;
    private String cName;
    private String cCount;
    private String cContent;
    private int cImg;
<<<<<<< HEAD
    public String getcName(){ return cName; }
    public String getcCount() { return cCount; }
=======

    public String getcName() {
        return cName;
    }

    public String getcCount() {
        return cCount;
    }
>>>>>>> 21848864a3767f175b95c88c6b67cac9dabb77f2

    public String getcContent() {
        return cContent;
    }

    public int getcImg() {
        return cImg;
    }

    public CommunityItem(String name, String count, String content, int img) {
        cName = name;
        cCount = count;
        cContent = content;
        cImg = img;
    }
}
