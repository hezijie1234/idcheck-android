package com.huiyu.tech.zhongxing;

import com.huiyu.tech.zhongxing.api.ApiImpl;
import com.huiyu.tech.zhongxing.utils.FileUtil;

import java.io.File;

/**
 * Created by ml on 2016/11/9.
 */
public class Constants {

    public static final String APP_NAME = "Jingwutong";
//    public static final String IMG_HOST = "http://121.42.178.20:7080";
    public static final String IMG_HOST = ApiImpl.HOST;

    /**项目目录*/
    public static final String PROJECT_FOLDER_PATH = FileUtil
            .getSDCardRootPath() + File.separator + APP_NAME + File.separator;

    /**缓存路径，用于存放临时的图片*/
    public static final String CASH = PROJECT_FOLDER_PATH + "cashes" + File.separator;
    /**用于拍照图片*/
    public static final String PICS = PROJECT_FOLDER_PATH + "pics" + File.separator;
    /**用于语音*/
    public static final String AUDIOS = PROJECT_FOLDER_PATH + "audios" + File.separator;
    /**用于log*/
    public static final String LOGS = PROJECT_FOLDER_PATH + "logs" + File.separator;
    /**用于视频*/
    public static final String VIDEOS = PROJECT_FOLDER_PATH + "videos" + File.separator;

    /**用来登录的用户图片**/
    public static final String USER_HEAD = PICS + "USER_HEAD.jpeg";

    /**缓存用的单张图片**/
    public static final String CASH_IMG = CASH + "cash.png";
    //用于存放获取的模板图片
    public static final String TEMPLATE_IMG = CASH + "module.png";
    /**持续获取到人脸数量后，就使用当前截图**/
    public static final int FACE_SUCCESS_COUNT = 10;
    /**上传到后台对比图片的尺寸**/
    public static final int COMPARE_SIZE = 320;
    /**
     *  request code
     */
    public static final int REQUEST_FILE_SELECTED = 1001;
    public static final int REQUEST_PHOTO_CROP = 1002;
    public static final int REQUEST_PHOTO_SELECTED = 1003;
    public static final int REQUEST_HANDLER_OVER = 1004;
    public static final int REQUEST_REFRESH_LIST = 1005;
    public static final int REQUEST_NEXT = 1006;

    /**
     * share key
     */
    public final static class SHARE_KEY{
        /**用户名*/
        public static final String KEY_ACCOUNT = "account";
        /**sessionid*/
        public static final String KEY_SESSIONID = "sessionid";
        public static final String TYPE = "type";
        public static final String VERSION_CODE = "version_code";
        public static final String USER_PHOTO = "user_photo";
        /**连接的蓝牙地址**/
        public static final String BLUETOOTH_MAC = "bluetooth_mac";
        //  第一次登录
        public static final String FIRST_LOGIN = "first_login";
        public static final String USER_ID = "userId";
    }

    /**
     * intent key
     */
    public final static class INTENT_KEY {
        /**
         * 标题
         */
        public static final String KEY_TITLE = "title";
        /**
         * ID
         */
        public static final String KEY_ID = "id";
        public static final String POSITION = "position";
        public static final String LIST = "list";
    }
}
