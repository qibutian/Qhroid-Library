package net.duohuo.dhroid.util;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

public class UserLocation implements AMapLocationListener {

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;


    private String city = "南京", provice;

    private double latitude, longitude;

    private boolean islocation;

    static UserLocation instance;

    private String district = "";

    private String street = "";
    OnLocationChanged onLocationChanged;
    private static double EARTH_RADIUS = 6378.137;

    AMapLocation aMapLocation;


    public static UserLocation getInstance() {
        if (instance == null) {
            instance = new UserLocation();
        }
        return instance;
    }

    public void init(Context context) {
        //初始化定位
        mLocationClient = new AMapLocationClient(context);
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
//        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        //获取一次定位结果：
        //该方法默认为false。
//        mLocationOption.setOnceLocation(true);

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);

        if (null != mLocationClient) {
            mLocationClient.setLocationOption(mLocationOption);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }

    }


    public void init(Context context, int locationTime) {
        //初始化定位
        mLocationClient = new AMapLocationClient(context);
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
//        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(locationTime);

        if (null != mLocationClient) {
            mLocationClient.setLocationOption(mLocationOption);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }

    }

    public void cancleLocation() {
        mLocationClient.stopLocation();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvice() {
        return provice;
    }

    public void setProvice(String provice) {
        this.provice = provice;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isIslocation() {
        return islocation;
    }

    public void setIslocation(boolean islocation) {
        this.islocation = islocation;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public AMapLocation getAmapLocation() {
        return aMapLocation;
    }


    public OnLocationChanged getOnLocationChanged() {
        return onLocationChanged;
    }

    public void setOnLocationChanged(OnLocationChanged onLocationChanged) {
        this.onLocationChanged = onLocationChanged;
    }

    public interface OnLocationChanged {
        void change(double latitude, double longitude);
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null
                && amapLocation.getErrorCode() == 0) {
            aMapLocation = amapLocation;
            islocation = true;
            // 定位成功回调信息，设置相关消息
            city = amapLocation.getCity();
            if (city != null && city.contains("市")) {
                city = city.replace("市", "");
            }
            provice = amapLocation.getProvince();

            if (provice != null && provice.contains("省")) {
                provice = provice.replace("省", "");
            }

            district = amapLocation.getDistrict();

            street = amapLocation.getStreet();

            if (longitude != 0 && latitude != 0) {
                double distance = distance(longitude, latitude, amapLocation.getLongitude(), amapLocation.getLatitude());
                Log.d("msg", "distance:" + distance);
                if (distance > 1 && onLocationChanged != null) {
                    onLocationChanged.change(amapLocation.getLatitude(), amapLocation.getLongitude());
                }
            }
            latitude = amapLocation.getLatitude();
            longitude = amapLocation.getLongitude();
        } else {
            //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
            Log.e("AmapError", "location Error, ErrCode:"
                    + amapLocation.getErrorCode() + ", errInfo:"
                    + amapLocation.getErrorInfo());
            islocation = false;
        }
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }


    public double distance(double long1, double lat1, double long2,
                           double lat2) {
        double a, b, R;
        R = 6378137; // 地球半径
        lat1 = lat1 * Math.PI / 180.0;
        lat2 = lat2 * Math.PI / 180.0;
        a = lat1 - lat2;
        b = (long1 - long2) * Math.PI / 180.0;
        double d;
        double sa2, sb2;
        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2 * R * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1) * Math.cos(lat2) * sb2 * sb2));
        return d;
    }


}
