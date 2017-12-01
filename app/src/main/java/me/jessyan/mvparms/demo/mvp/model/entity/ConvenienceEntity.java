package me.jessyan.mvparms.demo.mvp.model.entity;

import java.util.List;

/**
 * Created by czw on 2017/11/30.
 */
public class ConvenienceEntity {

    /**
     * id : 1
     * typeName : 便民应用
     * fontColor : #111111
     * fontStyle : font-size:12px;
     * logo : commonFile/M00/00/01/eBjnRFmERNKAO-0RAAEd7JoDNMU961.jpg
     * sort : 1
     * status : 00
     * appMenuVoList : [{"id":3,"type":1,"menuName":"交通缴罚","menuClickType":"01","menuClickTarget":"vehicleviolation","menuModuleName":"交通缴罚","fontColor":"1","fontStyle":"font","logo":"http://www.icoderlab.com/commonFile/M00/00/04/eBjnRFmdOhSAD9ErAAAZfKzCzws770.png","sort":1,"createTime":"2017-08-07 11:52:34","updateTime":"2017-11-16 17:34:42","status":"00","activityImg":"commonFile/M00/00/04/eBjnRFmdOhSAK7mFAAAZfKzCzws222.png","isShow":0,"needLogin":"01"},{"id":6,"type":1,"menuName":"医疗挂号","menuClickType":"01","menuClickTarget":"healthService","menuModuleName":"医疗挂号","fontColor":"#fff","fontStyle":"font-size=12px","logo":"http://www.icoderlab.com/commonFile/M00/00/04/eBjnRFmdOleATEfHAAAVi-GhtyU201.png","sort":2,"createTime":"2017-08-23 16:19:23","updateTime":"2017-10-19 09:53:30","status":"00","activityImg":"commonFile/M00/00/04/eBjnRFmdOleAB1S4AAAVi-GhtyU542.png","isShow":0,"needLogin":"01"},{"id":7,"type":1,"menuName":"电影票","menuClickType":"04","menuClickTarget":"http://www.baidu.com","menuModuleName":"电影票","fontColor":"#fff","fontStyle":"font-size=12px","logo":"http://www.icoderlab.com/group1/M00/00/00/eBjnRFn6tq6AR3PbAAAfwMkDHQc946.png","sort":3,"createTime":"2017-08-23 16:20:15","updateTime":"2017-11-02 14:09:40","status":"00","activityImg":"group1/M00/00/00/eBjnRFn6tq6ANanUAAAfwMkDHQc828.png","isShow":0,"needLogin":"01"},{"id":8,"type":1,"menuName":"手机充值","menuClickType":"01","menuClickTarget":"phoneRecharge","menuModuleName":"手机充值","fontColor":"#aaa","fontStyle":"font-size:12px","logo":"http://www.icoderlab.com/commonFile/M00/00/05/eBjnRFmdOsCAAKY6AAAW9tpf27c314.png","sort":4,"createTime":"2017-08-23 16:21:08","updateTime":"2017-09-27 10:34:49","status":"00","activityImg":"commonFile/M00/00/05/eBjnRFmdOsCANXalAAAW9tpf27c871.png","isShow":0,"needLogin":"01"},{"id":9,"type":1,"menuName":"生活缴费","menuClickType":"04","menuClickTarget":"www.baidu.com","menuModuleName":"生活缴费","fontColor":"#fff","fontStyle":"font-size:12px","logo":"http://www.icoderlab.com/commonFile/M00/00/05/eBjnRFmdOvGASXFLAAAZghGELTQ855.png","sort":5,"createTime":"2017-08-23 16:21:57","updateTime":"2017-09-05 14:29:53","status":"00","activityImg":"commonFile/M00/00/05/eBjnRFmdOvGADbBbAAAZghGELTQ224.png","isShow":0,"needLogin":"00"},{"id":10,"type":1,"menuName":"火车票","menuClickType":"02","menuClickTarget":"http://192.168.1.243:8020/APP/html/index/index.html","menuModuleName":"火车票","fontColor":"#aaa","fontStyle":"font-size:12px","logo":"http://www.icoderlab.com/commonFile/M00/00/05/eBjnRFmdOyCARcnNAAAX1Pq5YcE774.png","sort":6,"createTime":"2017-08-23 16:22:44","updateTime":"2017-10-26 11:34:23","status":"00","activityImg":"commonFile/M00/00/05/eBjnRFmdOyCABfl8AAAX1Pq5YcE934.png","isShow":0,"needLogin":"01"},{"id":11,"type":1,"menuName":"QQ充值","menuClickType":"02","menuClickTarget":"http://rcktest.woyes.com/apiComponent/html/qqRecharge/qqRechargeIndex.html","menuModuleName":"QQ充值","fontColor":"#fff","fontStyle":"font-size:12px","logo":"http://www.icoderlab.com/commonFile/M00/00/10/eBjnRFn6tsuAQ_2sAAAF_xSepWQ315.png","sort":7,"createTime":"2017-08-23 16:27:06","updateTime":"2017-11-03 14:39:15","status":"00","activityImg":"commonFile/M00/00/10/eBjnRFn6tsuAXHhBAAAF_xSepWQ959.png","isShow":0,"needLogin":"01"},{"id":12,"type":1,"menuName":"会员充值","menuClickType":"02","menuClickTarget":"http://rcktest.woyes.com/apiComponent/webapp/memberRechargeUI","menuModuleName":"会员充值","fontColor":"#aaa","fontStyle":"font-size:12px","logo":"http://www.icoderlab.com/commonFile/M00/00/0B/eBjnRFnMjEWAdge6AAAHaeEAy4c489.png","sort":8,"createTime":"2017-08-23 16:28:01","updateTime":"2017-11-01 14:52:09","status":"00","activityImg":"commonFile/M00/00/0B/eBjnRFnMjEWAV_vWAAAJU63La-I230.png","isShow":0,"needLogin":"01"}]
     */

    private int id;
    private String typeName;
    private String fontColor;
    private String fontStyle;
    private String logo;
    private int sort;
    private String status;
    private List<AppMenuVoListBean> appMenuVoList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public String getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(String fontStyle) {
        this.fontStyle = fontStyle;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<AppMenuVoListBean> getAppMenuVoList() {
        return appMenuVoList;
    }

    public void setAppMenuVoList(List<AppMenuVoListBean> appMenuVoList) {
        this.appMenuVoList = appMenuVoList;
    }

    public static class AppMenuVoListBean {
        /**
         * id : 3
         * type : 1
         * menuName : 交通缴罚
         * menuClickType : 01
         * menuClickTarget : vehicleviolation
         * menuModuleName : 交通缴罚
         * fontColor : 1
         * fontStyle : font
         * logo : http://www.icoderlab.com/commonFile/M00/00/04/eBjnRFmdOhSAD9ErAAAZfKzCzws770.png
         * sort : 1
         * createTime : 2017-08-07 11:52:34
         * updateTime : 2017-11-16 17:34:42
         * status : 00
         * activityImg : commonFile/M00/00/04/eBjnRFmdOhSAK7mFAAAZfKzCzws222.png
         * isShow : 0
         * needLogin : 01
         */

        private int id;
        private int type;
        private String menuName;
        private String menuClickType;
        private String menuClickTarget;
        private String menuModuleName;
        private String fontColor;
        private String fontStyle;
        private String logo;
        private int sort;
        private String createTime;
        private String updateTime;
        private String status;
        private String activityImg;
        private int isShow;
        private String needLogin;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getMenuName() {
            return menuName;
        }

        public void setMenuName(String menuName) {
            this.menuName = menuName;
        }

        public String getMenuClickType() {
            return menuClickType;
        }

        public void setMenuClickType(String menuClickType) {
            this.menuClickType = menuClickType;
        }

        public String getMenuClickTarget() {
            return menuClickTarget;
        }

        public void setMenuClickTarget(String menuClickTarget) {
            this.menuClickTarget = menuClickTarget;
        }

        public String getMenuModuleName() {
            return menuModuleName;
        }

        public void setMenuModuleName(String menuModuleName) {
            this.menuModuleName = menuModuleName;
        }

        public String getFontColor() {
            return fontColor;
        }

        public void setFontColor(String fontColor) {
            this.fontColor = fontColor;
        }

        public String getFontStyle() {
            return fontStyle;
        }

        public void setFontStyle(String fontStyle) {
            this.fontStyle = fontStyle;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getActivityImg() {
            return activityImg;
        }

        public void setActivityImg(String activityImg) {
            this.activityImg = activityImg;
        }

        public int getIsShow() {
            return isShow;
        }

        public void setIsShow(int isShow) {
            this.isShow = isShow;
        }

        public String getNeedLogin() {
            return needLogin;
        }

        public void setNeedLogin(String needLogin) {
            this.needLogin = needLogin;
        }
    }
}
