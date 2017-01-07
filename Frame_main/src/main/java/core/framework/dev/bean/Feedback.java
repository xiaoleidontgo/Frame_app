package core.framework.dev.bean;

import java.util.List;

/**
 * Created by lyh on 2016/11/19.
 * 视频评论
 */

public class Feedback {

    private int results;
    private int page;
    private int pages;
    private int isAdmin;
    private boolean needCode;
    private int owner;
    private List<HotListBean> hotList;
    private List<ListBean> list;

    public int getResults() {
        return results;
    }

    public void setResults(int results) {
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isNeedCode() {
        return needCode;
    }

    public void setNeedCode(boolean needCode) {
        this.needCode = needCode;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public List<HotListBean> getHotList() {
        return hotList;
    }

    public void setHotList(List<HotListBean> hotList) {
        this.hotList = hotList;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class HotListBean {

        private int mid;
        private int lv;
        private String fbid;
        private int ad_check;
        private int good;
        private int isgood;
        private String msg;
        private String device;
        private int create;
        private String create_at;
        private int reply_count;
        private String face;
        private int rank;
        private String nick;
        private LevelInfoBean level_info;
        private String sex;

        public int getMid() {
            return mid;
        }

        public void setMid(int mid) {
            this.mid = mid;
        }

        public int getLv() {
            return lv;
        }

        public void setLv(int lv) {
            this.lv = lv;
        }

        public String getFbid() {
            return fbid;
        }

        public void setFbid(String fbid) {
            this.fbid = fbid;
        }

        public int getAd_check() {
            return ad_check;
        }

        public void setAd_check(int ad_check) {
            this.ad_check = ad_check;
        }

        public int getGood() {
            return good;
        }

        public void setGood(int good) {
            this.good = good;
        }

        public int getIsgood() {
            return isgood;
        }

        public void setIsgood(int isgood) {
            this.isgood = isgood;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getDevice() {
            return device;
        }

        public void setDevice(String device) {
            this.device = device;
        }

        public int getCreate() {
            return create;
        }

        public void setCreate(int create) {
            this.create = create;
        }

        public String getCreate_at() {
            return create_at;
        }

        public void setCreate_at(String create_at) {
            this.create_at = create_at;
        }

        public int getReply_count() {
            return reply_count;
        }

        public void setReply_count(int reply_count) {
            this.reply_count = reply_count;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public LevelInfoBean getLevel_info() {
            return level_info;
        }

        public void setLevel_info(LevelInfoBean level_info) {
            this.level_info = level_info;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public static class LevelInfoBean {

            private int current_exp;
            private int current_level;
            private int current_min;
            private int next_exp;

            public int getCurrent_exp() {
                return current_exp;
            }

            public void setCurrent_exp(int current_exp) {
                this.current_exp = current_exp;
            }

            public int getCurrent_level() {
                return current_level;
            }

            public void setCurrent_level(int current_level) {
                this.current_level = current_level;
            }

            public int getCurrent_min() {
                return current_min;
            }

            public void setCurrent_min(int current_min) {
                this.current_min = current_min;
            }

            public int getNext_exp() {
                return next_exp;
            }

            public void setNext_exp(int next_exp) {
                this.next_exp = next_exp;
            }
        }
    }

    public static class ListBean {

        private int mid;
        private int lv;
        private String fbid;
        private int ad_check;
        private int good;
        private int isgood;
        private String msg;
        private String device;
        private int create;
        private String create_at;
        private int reply_count;
        private String face;
        private int rank;
        private String nick;
        private HotListBean.LevelInfoBean level_info;
        private String sex;
        private Object reply;

        public int getMid() {
            return mid;
        }

        public void setMid(int mid) {
            this.mid = mid;
        }

        public int getLv() {
            return lv;
        }

        public void setLv(int lv) {
            this.lv = lv;
        }

        public String getFbid() {
            return fbid;
        }

        public void setFbid(String fbid) {
            this.fbid = fbid;
        }

        public int getAd_check() {
            return ad_check;
        }

        public void setAd_check(int ad_check) {
            this.ad_check = ad_check;
        }

        public int getGood() {
            return good;
        }

        public void setGood(int good) {
            this.good = good;
        }

        public int getIsgood() {
            return isgood;
        }

        public void setIsgood(int isgood) {
            this.isgood = isgood;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getDevice() {
            return device;
        }

        public void setDevice(String device) {
            this.device = device;
        }

        public int getCreate() {
            return create;
        }

        public void setCreate(int create) {
            this.create = create;
        }

        public String getCreate_at() {
            return create_at;
        }

        public void setCreate_at(String create_at) {
            this.create_at = create_at;
        }

        public int getReply_count() {
            return reply_count;
        }

        public void setReply_count(int reply_count) {
            this.reply_count = reply_count;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public HotListBean.LevelInfoBean getLevel_info() {
            return level_info;
        }

        public void setLevel_info(HotListBean.LevelInfoBean level_info) {
            this.level_info = level_info;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public Object getReply() {
            return reply;
        }

        public void setReply(Object reply) {
            this.reply = reply;
        }

        public static class LevelInfoBeanX {

            private int current_exp;
            private int current_level;
            private int current_min;
            private int next_exp;

            public int getCurrent_exp() {
                return current_exp;
            }

            public void setCurrent_exp(int current_exp) {
                this.current_exp = current_exp;
            }

            public int getCurrent_level() {
                return current_level;
            }

            public void setCurrent_level(int current_level) {
                this.current_level = current_level;
            }

            public int getCurrent_min() {
                return current_min;
            }

            public void setCurrent_min(int current_min) {
                this.current_min = current_min;
            }

            public int getNext_exp() {
                return next_exp;
            }

            public void setNext_exp(int next_exp) {
                this.next_exp = next_exp;
            }
        }
    }
}
