package me.jessyan.mvparms.demo.mvp.model.entity;

/**
 * Created by xhb on 2016/3/2.
 * 发现更多实体类
 */
public class FindMoreEntity {
    /**
     * id : 2
     * name : 创意
     * alias : null
     * bgPicture : http://img.wdjimg.com/image/video/e8f7e96c58348e4dd6daede64721d02d_0_0.jpeg
     * bgColor :
     */

    private int id;
    private String name;
    private Object alias;
    private String bgPicture;
    private String bgColor;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAlias(Object alias) {
        this.alias = alias;
    }

    public void setBgPicture(String bgPicture) {
        this.bgPicture = bgPicture;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Object getAlias() {
        return alias;
    }

    public String getBgPicture() {
        return bgPicture;
    }

    public String getBgColor() {
        return bgColor;
    }
}
