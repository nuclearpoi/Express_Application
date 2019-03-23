package com.bs.express.bean;

import java.io.Serializable;
import java.util.List;



public class ExpressInfo implements Serializable{

    /**
     * code : OK
     * no : 780098068058
     * type : ZTO
     * list : [{"content":"【石家庄市】 快件已在 【长安三部】 签收,签收人: 本人, 感谢使用中通快递,期待再次为您服务!","time":"2018-03-09 11:59:26"},{"content":"【石家庄市】 快件已到达 【长安三部】（0311-85344265）,业务员 容晓光（13081105270） 正在第1次派件, 请保持电话畅通,并耐心等待","time":"2018-03-09 09:03:10"},{"content":"【石家庄市】 快件离开 【石家庄】 发往 【长安三部】","time":"2018-03-08 23:43:44"},{"content":"【石家庄市】 快件到达 【石家庄】","time":"2018-03-08 21:00:44"},{"content":"【广州市】 快件离开 【广州中心】 发往 【石家庄】","time":"2018-03-07 01:38:45"},{"content":"【广州市】 快件到达 【广州中心】","time":"2018-03-07 01:36:53"},{"content":"【广州市】 快件离开 【广州花都】 发往 【石家庄中转】","time":"2018-03-07 00:40:57"},{"content":"【广州市】 【广州花都】（020-37738523） 的 马溪 （18998345739） 已揽收","time":"2018-03-07 00:01:55"}]
     * state : 3
     * msg : 查询成功
     * name : 中通快递
     * site : www.zto.com
     * phone : 95311
     * logo : http://img3.fegine.com/express/zto.jpg
     */

    private String code;
    private String no;
    private String type;
    private String state;
    private String msg;
    private String name;
    private String site;
    private String phone;
    private String logo;
    private List<ListEntity> list;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<ListEntity> getList() {
        return list;
    }

    public void setList(List<ListEntity> list) {
        this.list = list;
    }

    public static class ListEntity implements Serializable {
        /**
         * content : 【石家庄市】 快件已在 【长安三部】 签收,签收人: 本人, 感谢使用中通快递,期待再次为您服务!
         * time : 2018-03-09 11:59:26
         */

        private String content;
        private String time;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

    @Override
    public String toString() {
        return "ExpressInfo{" +
                "code='" + code + '\'' +
                ", no='" + no + '\'' +
                ", type='" + type + '\'' +
                ", state='" + state + '\'' +
                ", msg='" + msg + '\'' +
                ", name='" + name + '\'' +
                ", site='" + site + '\'' +
                ", phone='" + phone + '\'' +
                ", logo='" + logo + '\'' +
                ", list=" + list +
                '}';
    }
}
