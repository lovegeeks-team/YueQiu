package com.yueqiu.constant;


/**
 * Created by yinfeng on 14/12/22.
 */
public class HttpConstants {

    public static final String URL = "http://hxu0480201.my3w.com/index.php/v1";

    /**
     * 请求方式
     */
    public static final class RequestMethod
    {
        public static final String GET = "GET";

        public static final String POST = "POST";
    }

    /*
    请求公共参数
     */
    public static final class PublicConstant
    {
        /*
         * 应用类型
         * 1、WebApp
         * 2、Android
         * 3、IOS
         */
        public static final String APP_TYPE = "app_type";

        /*
         *
         */
        public static final String TOKEN = "token";
    }

    /**
     * 用户注册请求参数
     * 发送Post请求
     * @author yinfeng
     *
     */
    public static final class RegisterConstant
    {
        /*
         * 接口
         */
        public static final String URL = "/user/register";
        /*
         * 账号
         */
        public static final String ACCOUNT = "username";

        /*
         * 手机号
         */
        public static final String PHONE = "phone";

        /*
         * 性别 1男2女
         */
        public static final String SEX = "sex";

        /*
         * 密码
         */
        public static final String PASSWORD = "password";
    }

    /**
     * 登录请求参数
     * 发送Post请求
     * @author yinfeng
     *
     */
    public static final class LoginConstant
    {

        public static final String URL = "/user/login";
        /*
         * 手机号或账号
         */
        public static final String USERNAME = "username";

        /*
         * 密码
         */
        public static final String PASSWORD = "password";
    }

    /**
     * 登出
     */
    public static final class LogoutConstant{

        public static final String URL = "/user/logout";
    }

    /**
     * 我的发布
     */
    public static final class Published{

        public static final String URL      = "/center/getPublishList";

        public static final String TYPE     = "type";

        public static final String STAR_NO  = "start_no";

        public static final String END_NO   = "end_no";
    }

    /*
     * 我的资料请求参数
     * 发送POST请求
     */
    public static final class GetMyInfo
    {
        public static final String URL = "/center/getInfo";

        /*
         * 用户ID
         */
        public static final String USER_ID = "user_id";
    }

    /*
     * 球友、约球、助教、和教练的请求参数
     * 发送Get请求
     */
    public static final class SearchPerson
    {
        public static final String URL = "/home/searchPerson";

        /*
         * 用户ID
         */
        public static final String USER_ID = "user_id";

        /*
         * 距离
         */
        public static final String RANGE = "range";

        /*
         * 性别 男1 女2
         */
        public static final String SEX = "sex";

        /*
         * 发布时间
         */
        public static final String DATE = "date";

        /*
         * 费用
         */
        public static final String MONEY = "money";

        /*
         * 球种
         */
        public static final String CLASS = "class";

        /*
         * 水平
         */
        public static final String LEVEL = "level";


        /*
         * 开始条数
         * 默认0
         */
        public static final String START_NO = "start_no";

        /*
         * 结束条数
         */
        public static final String END_NO = "end_no";
    }

    /**
     * 球厅请求参数
     * 发送POST请求
     * @author yinfeng
     *
     */
    public static final class SearchHallConstant
    {
        public static final String URL = "/home/ searchHall";

        /*
         * 区域
         */
        public static final String DISTRICT = "district";

        /*
         * 距离
         */
        public static final String RANGE = "range";

        /*
         * 价格
         */
        public static final String PRICE = "price";

        /*
         * 好评
         */
        public static final String COMMENT = "comment";


        /*
         * 开始条数
         * 默认0
         */
        public static final String START_NO = "start_no";

        /*
         * 结束条数
         */
        public static final String END_NO = "end_no";
    }

    /*
     * 球厅、约球详情、发布约球
     * 发送POST请求
     */
    public static final class AppointBll
    {
        public static final String URL = "center/publish";

        public static final String USER_ID = "user_id";

        public static final String TITLE = "title";

        public static final String ADDRESS = "address";

        public static final String BEGIN_TIME = "begin_time";

        public static final String END_TIME = "end_time";

        public static final String MODULE = "module";

        public static final String CONTENT = "content";

        public static final String CONTACT = "contact";

        public static final String PHONE  = "phone";

    }

    /*
     * 我的收藏(包含：约球，活动，台球圈)
     */
    public static final class GetStore
    {
        public static final String URL = "/user/getStore";

        /*
         * 用户ID
         */
        public static final String USER_ID = "user_id";

        /*
         * 发布类型
         * 1、约球
         * 2、活动
         * 3、台球圈
         */
        public static final String TYPE_ID = "type_id";

        /*
         * 搜索关键字
         */
        public static final String KEYWORDS = "keywords";

        /*
         * 开始条数（默认为0）
         */
        public static final String START_NO = "start_no";

        /*
         * 结束条数
         */
        public static final String END_NO = "end_no";
    }

    /*
     * 聊吧中添加好友页面通过附近的人获取好友列表
     */
    public static final class SearchPeopleByNearby
    {
        public static final String URL = "/friend/nearby";

        //请求参数：位置坐标
        public static final String LAT = "lat";
        public static final String LNG = "lng";
    }

    /*
     * 聊吧中添加好友页面通过查询手机号或账号获取好友
     */
    public static final class SearchPeopleByKeyword
    {
        public static final String URL = "/friend/word";

        //请求参数：位置坐标
        public static final String USER_ID = "user_id";
        public static final String KEYWORDS = "keywords";
    }

    /*
     * 聊吧中获取好友列表s
     */
    public static final class ContactsList
    {
        public static final String URL = "/friend/getList";

        //请求参数：位置坐标
        public static final String USER_ID = "user_id";
        public static final String GROUP_ID = "group_id";
    }

    /**
     * Json 状态码
     * @author yinfeng
     *
     */
    public static final class ResponseCode
    {
        /*
         *  正常
         */
        public static final int NORMAL = 1001;

        /*
         * 缺少参数
         */
        public static final int a1 = 1002;

        /*
         * 数据格式错误
         */
        public static final int a2 = 1003;

        /*
         * 无权限
         */
        public static final int a3 = 1004;

        /*
         * 找不到记录
         */
        public static final int a4 = 1005;

        /*
         * 服务器内部错误
         */
        public static final int a5 = 1006;

        /*
         * Token错误
         */
        public static final int a6 = 1007;

        /*
         * 请求接口错误
         */
        public static final int a7 = 1008;

        /*
         * 数据库错误
         */
        public static final int a8 = 1009;
    }


}
