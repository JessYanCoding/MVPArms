package me.jessyan.mvparms.demo.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ASUS on 2016/2/29.
 */
public class HomePicEntity {

    /**
     * date : 1456675200000
     * publishTime : 1456675200000
     * type : normal
     * count : 6
     * itemList : [{"type":"video","data":{"id":5754,"date":1456675200000,"idx":1,"title":"奥斯卡号外丨恭喜小李子终于申奥成功","description":"恭喜小李子结束了长达 20 年的陪跑~最新采访小李时，谈到为何投身环保，同时还坚持演戏。他说作为明星很多时候说话不受重视，但至少站出来说话能被听到，如果不演戏就没人听他说这些环保的事了。From Burger Fiction","category":"综合","duration":454,"playUrl":"http://baobab.wdjcdn.com/1456717752764486381173.mp4","playInfo":[{"height":480,"width":854,"name":"标清","type":"normal","url":"http://baobab.wdjcdn.com/1456718039738_5754_854x480.mp4"},{"height":720,"width":1280,"name":"高清","type":"high","url":"http://baobab.wdjcdn.com/1456717752764486381173.mp4"}],"consumption":{"collectionCount":602,"shareCount":2628,"playCount":0,"replyCount":51},"promotion":null,"waterMarks":null,"provider":{"name":"Vimeo","alias":"vimeo","icon":"http://img.wdjimg.com/image/video/c3ad630be461cbb081649c9e21d6cbe3_256_256.png"},"author":null,"adTrack":null,"shareAdTrack":null,"favoriteAdTrack":null,"webAdTrack":null,"cover":{"feed":"http://img.wdjimg.com/image/video/3141c1e1a154d17a5742eb9ab4a8723b_0_0.jpeg","detail":"http://img.wdjimg.com/image/video/3141c1e1a154d17a5742eb9ab4a8723b_0_0.jpeg","blurred":"http://img.wdjimg.com/image/video/e7686329d482d4bfa939acbcd53060ea_0_0.jpeg","sharing":"http://img.wdjimg.com/image/video/3141c1e1a154d17a5742eb9ab4a8723b_0_0.jpeg"},"webUrl":{"raw":"http://www.wandoujia.com/eyepetizer/detail.html?vid=5754","forWeibo":"http://wandou.im/1l1ibm"},"campaign":null}}]
     */

    private String nextPageUrl;

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    private List<IssueListEntity> issueList;

    public void setIssueList(List<IssueListEntity> issueList) {
        this.issueList = issueList;
    }

    public List<IssueListEntity> getIssueList() {
        return issueList;
    }

    public static class IssueListEntity {
        private long date;
        private long publishTime;
        private String type;
        private int count;
        /**
         * type : video
         * data : {"id":5754,"date":1456675200000,"idx":1,"title":"奥斯卡号外丨恭喜小李子终于申奥成功","description":"恭喜小李子结束了长达 20 年的陪跑~最新采访小李时，谈到为何投身环保，同时还坚持演戏。他说作为明星很多时候说话不受重视，但至少站出来说话能被听到，如果不演戏就没人听他说这些环保的事了。From Burger Fiction","category":"综合","duration":454,"playUrl":"http://baobab.wdjcdn.com/1456717752764486381173.mp4","playInfo":[{"height":480,"width":854,"name":"标清","type":"normal","url":"http://baobab.wdjcdn.com/1456718039738_5754_854x480.mp4"},{"height":720,"width":1280,"name":"高清","type":"high","url":"http://baobab.wdjcdn.com/1456717752764486381173.mp4"}],"consumption":{"collectionCount":602,"shareCount":2628,"playCount":0,"replyCount":51},"promotion":null,"waterMarks":null,"provider":{"name":"Vimeo","alias":"vimeo","icon":"http://img.wdjimg.com/image/video/c3ad630be461cbb081649c9e21d6cbe3_256_256.png"},"author":null,"adTrack":null,"shareAdTrack":null,"favoriteAdTrack":null,"webAdTrack":null,"cover":{"feed":"http://img.wdjimg.com/image/video/3141c1e1a154d17a5742eb9ab4a8723b_0_0.jpeg","detail":"http://img.wdjimg.com/image/video/3141c1e1a154d17a5742eb9ab4a8723b_0_0.jpeg","blurred":"http://img.wdjimg.com/image/video/e7686329d482d4bfa939acbcd53060ea_0_0.jpeg","sharing":"http://img.wdjimg.com/image/video/3141c1e1a154d17a5742eb9ab4a8723b_0_0.jpeg"},"webUrl":{"raw":"http://www.wandoujia.com/eyepetizer/detail.html?vid=5754","forWeibo":"http://wandou.im/1l1ibm"},"campaign":null}
         */

        private List<ItemListEntity> itemList;

        public void setDate(long date) {
            this.date = date;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public void setItemList(List<ItemListEntity> itemList) {
            this.itemList = itemList;
        }

        public long getDate() {
            return date;
        }

        public long getPublishTime() {
            return publishTime;
        }

        public String getType() {
            return type;
        }

        public int getCount() {
            return count;
        }

        public List<ItemListEntity> getItemList() {
            return itemList;
        }

        public static class ItemListEntity implements Serializable {
            private String type;
            private String image;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            /**
             * id : 5754
             * date : 1456675200000
             * idx : 1
             * title : 奥斯卡号外丨恭喜小李子终于申奥成功
             * description : 恭喜小李子结束了长达 20 年的陪跑~最新采访小李时，谈到为何投身环保，同时还坚持演戏。他说作为明星很多时候说话不受重视，但至少站出来说话能被听到，如果不演戏就没人听他说这些环保的事了。From Burger Fiction
             * category : 综合
             * duration : 454
             * playUrl : http://baobab.wdjcdn.com/1456717752764486381173.mp4
             * playInfo : [{"height":480,"width":854,"name":"标清","type":"normal","url":"http://baobab.wdjcdn.com/1456718039738_5754_854x480.mp4"},{"height":720,"width":1280,"name":"高清","type":"high","url":"http://baobab.wdjcdn.com/1456717752764486381173.mp4"}]
             * consumption : {"collectionCount":602,"shareCount":2628,"playCount":0,"replyCount":51}
             * promotion : null
             * waterMarks : null
             * provider : {"name":"Vimeo","alias":"vimeo","icon":"http://img.wdjimg.com/image/video/c3ad630be461cbb081649c9e21d6cbe3_256_256.png"}
             * author : null
             * adTrack : null
             * shareAdTrack : null
             * favoriteAdTrack : null
             * webAdTrack : null
             * cover : {"feed":"http://img.wdjimg.com/image/video/3141c1e1a154d17a5742eb9ab4a8723b_0_0.jpeg","detail":"http://img.wdjimg.com/image/video/3141c1e1a154d17a5742eb9ab4a8723b_0_0.jpeg","blurred":"http://img.wdjimg.com/image/video/e7686329d482d4bfa939acbcd53060ea_0_0.jpeg","sharing":"http://img.wdjimg.com/image/video/3141c1e1a154d17a5742eb9ab4a8723b_0_0.jpeg"}
             * webUrl : {"raw":"http://www.wandoujia.com/eyepetizer/detail.html?vid=5754","forWeibo":"http://wandou.im/1l1ibm"}
             * campaign : null
             */

            private DataEntity data;

            public void setType(String type) {
                this.type = type;
            }

            public void setData(DataEntity data) {
                this.data = data;
            }

            public String getType() {
                return type;
            }

            public DataEntity getData() {
                return data;
            }

            public static class DataEntity {
                private String image;

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                private int id;
                private long date;
                private int idx;
                private String title;
                private String description;
                private String category;
                private int duration;
                private String playUrl;
                private String text;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                /**
                 * collectionCount : 602
                 * shareCount : 2628
                 * playCount : 0
                 * replyCount : 51
                 */

                private ConsumptionEntity consumption;
                private Object promotion;
                private Object waterMarks;
                /**
                 * name : Vimeo
                 * alias : vimeo
                 * icon : http://img.wdjimg.com/image/video/c3ad630be461cbb081649c9e21d6cbe3_256_256.png
                 */

                private ProviderEntity provider;
                private Object author;
                private Object adTrack;
                private Object shareAdTrack;
                private Object favoriteAdTrack;
                private Object webAdTrack;
                /**
                 * feed : http://img.wdjimg.com/image/video/3141c1e1a154d17a5742eb9ab4a8723b_0_0.jpeg
                 * detail : http://img.wdjimg.com/image/video/3141c1e1a154d17a5742eb9ab4a8723b_0_0.jpeg
                 * blurred : http://img.wdjimg.com/image/video/e7686329d482d4bfa939acbcd53060ea_0_0.jpeg
                 * sharing : http://img.wdjimg.com/image/video/3141c1e1a154d17a5742eb9ab4a8723b_0_0.jpeg
                 */

                private CoverEntity cover;
                /**
                 * raw : http://www.wandoujia.com/eyepetizer/detail.html?vid=5754
                 * forWeibo : http://wandou.im/1l1ibm
                 */

                private WebUrlEntity webUrl;
                private Object campaign;
                /**
                 * height : 480
                 * width : 854
                 * name : 标清
                 * type : normal
                 * url : http://baobab.wdjcdn.com/1456718039738_5754_854x480.mp4
                 */

                private List<PlayInfoEntity> playInfo;

                public void setId(int id) {
                    this.id = id;
                }

                public void setDate(long date) {
                    this.date = date;
                }

                public void setIdx(int idx) {
                    this.idx = idx;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public void setCategory(String category) {
                    this.category = category;
                }

                public void setDuration(int duration) {
                    this.duration = duration;
                }

                public void setPlayUrl(String playUrl) {
                    this.playUrl = playUrl;
                }

                public void setConsumption(ConsumptionEntity consumption) {
                    this.consumption = consumption;
                }

                public void setPromotion(Object promotion) {
                    this.promotion = promotion;
                }

                public void setWaterMarks(Object waterMarks) {
                    this.waterMarks = waterMarks;
                }

                public void setProvider(ProviderEntity provider) {
                    this.provider = provider;
                }

                public void setAuthor(Object author) {
                    this.author = author;
                }

                public void setAdTrack(Object adTrack) {
                    this.adTrack = adTrack;
                }

                public void setShareAdTrack(Object shareAdTrack) {
                    this.shareAdTrack = shareAdTrack;
                }

                public void setFavoriteAdTrack(Object favoriteAdTrack) {
                    this.favoriteAdTrack = favoriteAdTrack;
                }

                public void setWebAdTrack(Object webAdTrack) {
                    this.webAdTrack = webAdTrack;
                }

                public void setCover(CoverEntity cover) {
                    this.cover = cover;
                }

                public void setWebUrl(WebUrlEntity webUrl) {
                    this.webUrl = webUrl;
                }

                public void setCampaign(Object campaign) {
                    this.campaign = campaign;
                }

                public void setPlayInfo(List<PlayInfoEntity> playInfo) {
                    this.playInfo = playInfo;
                }

                public int getId() {
                    return id;
                }

                public long getDate() {
                    return date;
                }

                public int getIdx() {
                    return idx;
                }

                public String getTitle() {
                    return title;
                }

                public String getDescription() {
                    return description;
                }

                public String getCategory() {
                    return category;
                }

                public int getDuration() {
                    return duration;
                }

                public String getPlayUrl() {
                    return playUrl;
                }

                public ConsumptionEntity getConsumption() {
                    return consumption;
                }

                public Object getPromotion() {
                    return promotion;
                }

                public Object getWaterMarks() {
                    return waterMarks;
                }

                public ProviderEntity getProvider() {
                    return provider;
                }

                public Object getAuthor() {
                    return author;
                }

                public Object getAdTrack() {
                    return adTrack;
                }

                public Object getShareAdTrack() {
                    return shareAdTrack;
                }

                public Object getFavoriteAdTrack() {
                    return favoriteAdTrack;
                }

                public Object getWebAdTrack() {
                    return webAdTrack;
                }

                public CoverEntity getCover() {
                    return cover;
                }

                public WebUrlEntity getWebUrl() {
                    return webUrl;
                }

                public Object getCampaign() {
                    return campaign;
                }

                public List<PlayInfoEntity> getPlayInfo() {
                    return playInfo;
                }

                public static class ConsumptionEntity {
                    private int collectionCount;
                    private int shareCount;
                    private int playCount;
                    private int replyCount;

                    public void setCollectionCount(int collectionCount) {
                        this.collectionCount = collectionCount;
                    }

                    public void setShareCount(int shareCount) {
                        this.shareCount = shareCount;
                    }

                    public void setPlayCount(int playCount) {
                        this.playCount = playCount;
                    }

                    public void setReplyCount(int replyCount) {
                        this.replyCount = replyCount;
                    }

                    public int getCollectionCount() {
                        return collectionCount;
                    }

                    public int getShareCount() {
                        return shareCount;
                    }

                    public int getPlayCount() {
                        return playCount;
                    }

                    public int getReplyCount() {
                        return replyCount;
                    }
                }

                public static class ProviderEntity {
                    private String name;
                    private String alias;
                    private String icon;

                    public void setName(String name) {
                        this.name = name;
                    }

                    public void setAlias(String alias) {
                        this.alias = alias;
                    }

                    public void setIcon(String icon) {
                        this.icon = icon;
                    }

                    public String getName() {
                        return name;
                    }

                    public String getAlias() {
                        return alias;
                    }

                    public String getIcon() {
                        return icon;
                    }
                }

                public static class CoverEntity {
                    private String feed;
                    private String detail;
                    private String blurred;
                    private String sharing;

                    public void setFeed(String feed) {
                        this.feed = feed;
                    }

                    public void setDetail(String detail) {
                        this.detail = detail;
                    }

                    public void setBlurred(String blurred) {
                        this.blurred = blurred;
                    }

                    public void setSharing(String sharing) {
                        this.sharing = sharing;
                    }

                    public String getFeed() {
                        return feed;
                    }

                    public String getDetail() {
                        return detail;
                    }

                    public String getBlurred() {
                        return blurred;
                    }

                    public String getSharing() {
                        return sharing;
                    }
                }

                public static class WebUrlEntity {
                    private String raw;
                    private String forWeibo;

                    public void setRaw(String raw) {
                        this.raw = raw;
                    }

                    public void setForWeibo(String forWeibo) {
                        this.forWeibo = forWeibo;
                    }

                    public String getRaw() {
                        return raw;
                    }

                    public String getForWeibo() {
                        return forWeibo;
                    }
                }

                public static class PlayInfoEntity {
                    private int height;
                    private int width;
                    private String name;
                    private String type;
                    private String url;

                    public void setHeight(int height) {
                        this.height = height;
                    }

                    public void setWidth(int width) {
                        this.width = width;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }

                    public int getHeight() {
                        return height;
                    }

                    public int getWidth() {
                        return width;
                    }

                    public String getName() {
                        return name;
                    }

                    public String getType() {
                        return type;
                    }

                    public String getUrl() {
                        return url;
                    }
                }
            }
        }
    }
}
