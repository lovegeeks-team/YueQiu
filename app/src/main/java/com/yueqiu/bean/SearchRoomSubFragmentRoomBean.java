package com.yueqiu.bean;

import java.io.StringReader;

/**
 * Created by scguo on 14/12/25.
 *
 * 用于控制每一个球厅显示的属性的Bean文件
 * 用于在球厅的ListView item当中的bean文件
 */
public class SearchRoomSubFragmentRoomBean
{
    private String mRoomPhotoUrl;
    private String mRoomName;
    private float mLevel;
    private double mPrice;
    private String mDetailedAddress;
    private String mDistance;

    public SearchRoomSubFragmentRoomBean(String roomPhoto, String roomName, float level, double price, String address, String distance)
    {
        this.mDetailedAddress = address;
        this.mDistance = distance;
        this.mLevel = level;
        this.mRoomPhotoUrl = roomPhoto;
        this.mPrice = price;
        this.mRoomName = roomName;
    }


    public String getRoomPhotoUrl()
    {
        return mRoomPhotoUrl;
    }

    public void setRoomPhotoUrl(String roomPhotoUrl)
    {
        this.mRoomPhotoUrl = roomPhotoUrl;
    }

    public String getRoomName()
    {
        return mRoomName;
    }

    public void setRoomName(String roomName)
    {
        this.mRoomName = roomName;
    }

    public float getLevel()
    {
        return mLevel;
    }

    public void setLevel(float level)
    {
        this.mLevel = level;
    }

    public double getPrice()
    {
        return mPrice;
    }

    public void setPrice(double price)
    {
        this.mPrice = price;
    }

    public String getDetailedAddress()
    {
        return mDetailedAddress;
    }

    public void setDetailedAddress(String detailedAddress)
    {
        this.mDetailedAddress = detailedAddress;
    }

    public String getDistance()
    {
        return mDistance;
    }

    public void setDistance(String distance)
    {
        this.mDistance = distance;
    }


    // 从服务器端获得到的JsonArray数据当中的一个Json Object的基本元素
    public static final String JSON_IMG_URL = "img_url"; // 图片地址
    public static final String JSON_NAME = "name"; // 球厅名字
    public static final String JSON_DETAILED_ADDRESS = "detail_address"; // 球厅地址
    public static final String JSON_RANGE = "range"; // 球厅在多少米以内
    public static final String JSON_PRICE = ""; // 价格
    public static final String JSON_STAR_NUMBER = "starNumber"; // 评级的星星的数目
    public static final String JSON_LNG = "lng"; // 经度
    public static final String JSON_LAT = "lat"; // 纬度




}
