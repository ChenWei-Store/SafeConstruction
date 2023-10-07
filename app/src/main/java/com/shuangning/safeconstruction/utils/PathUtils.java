package com.shuangning.safeconstruction.utils;

import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import com.shuangning.safeconstruction.base.BaseApplication;

import java.io.File;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/04/15
 *     desc  : utils about path
 * </pre>
 *
 *内部存储 : /data/data/package/ 目录
 *外部存储 ( 私有目录 ) : /storage/emulated/0/Android/data/package/目录,
 * 一般可以从/sdcard/Android/data/<app_package>看
 *手机从/Android/data/<app_package>看
 *外部存储 ( 公开目录 ) : /storage/emulated/0/ 目录
 * 一般可以从/sdcard/Pictures 等看
 * 推荐隐私数据存储到内部存储中
 * 应用数据全部存储到外部存储 ( 私有目录 )
 * 和其他应用共享数据存储到外部存储 ( 公开目录 )
 * SDCard目录推荐使用MediaStore存储常用Image、Video、Document,Files,Audio等, 尽量减少需适配情况
 *
 * 权限情况:
 * 内部存储/外部存储私有目录不需要权限(外部存储私有目录在Android4.4(sdk19)以前需要，版本低不考虑)
 * 外部存储公开目录:
 * 在Android6.0之前,声明权限不需要动态申请
 * Android 6.0-Android10(包含10)需要声明+动态申请(WRITE_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE)
 * Android10已经引入Scoped Storage，可以绕过,Android11必须适配
 * Android11需要声明MANAGE_EXTERNAL_STORAGE并动态申请，使用MediaStore获取数据
 */
public final class PathUtils {

    private static final char SEP = File.separatorChar;

    private PathUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Join the path.
     *
     * @param parent The parent of path.
     * @param child  The child path.
     * @return the path
     */
    public static String join(String parent, String child) {
        if (TextUtils.isEmpty(child)) return parent;
        if (parent == null) {
            parent = "";
        }
        int len = parent.length();
        String legalSegment = getLegalSegment(child);
        String newPath;
        if (len == 0) {
            newPath = SEP + legalSegment;
        } else if (parent.charAt(len - 1) == SEP) {
            newPath = parent + legalSegment;
        } else {
            newPath = parent + SEP + legalSegment;
        }
        return newPath;
    }

    private static String getLegalSegment(String segment) {
        int st = -1, end = -1;
        char[] charArray = segment.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            if (c != SEP) {
                if (st == -1) {
                    st = i;
                }
                end = i;
            }
        }
        if (st >= 0 && end >= st) {
            return segment.substring(st, end + 1);
        }
        throw new IllegalArgumentException("segment of <" + segment + "> is illegal");
    }

    /**
     * Return the path of /system.
     *
     * @return the path of /system
     */
    public static String getRootPath() {
        return getAbsolutePath(Environment.getRootDirectory());
    }

    /**
     * Return the path of /data.
     *
     * @return the path of /data
     */
    public static String getDataPath() {
        return getAbsolutePath(Environment.getDataDirectory());
    }

    /**
     * Return the path of /cache.
     *
     * @return the path of /cache
     */
    public static String getDownloadCachePath() {
        return getAbsolutePath(Environment.getDownloadCacheDirectory());
    }

    /**
     * Return the path of /data/data/package.
     *
     * @return the path of /data/data/package
     */
    public static String getInternalAppDataPath() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            return BaseApplication.appContext.getApplicationInfo().dataDir;
        }
        return getAbsolutePath(BaseApplication.appContext.getDataDir());
    }

    /**
     * Return the path of /data/data/package/code_cache.
     *
     * @return the path of /data/data/package/code_cache
     */
    public static String getInternalAppCodeCacheDir() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return BaseApplication.appContext.getApplicationInfo().dataDir + "/code_cache";
        }
        return getAbsolutePath(BaseApplication.appContext.getCodeCacheDir());
    }

    /**
     * Return the path of /data/data/package/cache.
     * 获取内部存储缓存目录
     * @return the path of /data/data/package/cache
     */
    public static String getInternalAppCachePath() {
        return getAbsolutePath(BaseApplication.appContext.getCacheDir());
    }

    /**
     * Return the path of /data/data/package/databases.
     *
     * @return the path of /data/data/package/databases
     */
    public static String getInternalAppDbsPath() {
        return BaseApplication.appContext.getApplicationInfo().dataDir + "/databases";
    }

    /**
     * Return the path of /data/data/package/databases/name.
     *
     * @param name The name of database.
     * @return the path of /data/data/package/databases/name
     */
    public static String getInternalAppDbPath(String name) {
        return getAbsolutePath(BaseApplication.appContext.getDatabasePath(name));
    }

    /**
     * Return the path of /data/data/package/files.
     * 获取内部存储文件目录
     * @return the path of /data/data/package/files
     */
    public static String getInternalAppFilesPath() {
        return getAbsolutePath(BaseApplication.appContext.getFilesDir());
    }

    /**
     * Return the path of /data/data/package/shared_prefs.
     *
     * @return the path of /data/data/package/shared_prefs
     */
    public static String getInternalAppSpPath() {
        return BaseApplication.appContext.getApplicationInfo().dataDir + "/shared_prefs";
    }

    /**
     * Return the path of /data/data/package/no_backup.
     *
     * @return the path of /data/data/package/no_backup
     */
    public static String getInternalAppNoBackupFilesPath() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return BaseApplication.appContext.getApplicationInfo().dataDir + "/no_backup";
        }
        return getAbsolutePath(BaseApplication.appContext.getNoBackupFilesDir());
    }

    /**
     * Return the path of /storage/emulated/0.
     *
     * @return the path of /storage/emulated/0
     */
    public static String getExternalStoragePath() {
        if (!isSDCardEnableByEnvironment()) return "";
        return getAbsolutePath(Environment.getExternalStorageDirectory());
    }

    /**
     * Return the path of /storage/emulated/0/Music.
     *
     * @return the path of /storage/emulated/0/Music
     */
    public static String getExternalMusicPath() {
        if (!isSDCardEnableByEnvironment()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC));
    }

    /**
     * Return the path of /storage/emulated/0/Podcasts.
     *
     * @return the path of /storage/emulated/0/Podcasts
     */
    public static String getExternalPodcastsPath() {
        if (!isSDCardEnableByEnvironment()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS));
    }

    /**
     * Return the path of /storage/emulated/0/Ringtones.
     *
     * @return the path of /storage/emulated/0/Ringtones
     */
    public static String getExternalRingtonesPath() {
        if (!isSDCardEnableByEnvironment()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES));
    }

    /**
     * Return the path of /storage/emulated/0/Alarms.
     *
     * @return the path of /storage/emulated/0/Alarms
     */
    public static String getExternalAlarmsPath() {
        if (!isSDCardEnableByEnvironment()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS));
    }

    /**
     * Return the path of /storage/emulated/0/Notifications.
     *
     * @return the path of /storage/emulated/0/Notifications
     */
    public static String getExternalNotificationsPath() {
        if (!isSDCardEnableByEnvironment()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS));
    }

    /**
     * Return the path of /storage/emulated/0/Pictures.
     *
     * @return the path of /storage/emulated/0/Pictures
     */
    public static String getExternalPicturesPath() {
        if (!isSDCardEnableByEnvironment()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
    }

    /**
     * Return the path of /storage/emulated/0/Movies.
     *
     * @return the path of /storage/emulated/0/Movies
     */
    public static String getExternalMoviesPath() {
        if (!isSDCardEnableByEnvironment()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES));
    }

    /**
     * Return the path of /storage/emulated/0/Download.
     *
     * @return the path of /storage/emulated/0/Download
     */
    public static String getExternalDownloadsPath() {
        if (!isSDCardEnableByEnvironment()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
    }

    /**
     * Return the path of /storage/emulated/0/DCIM.
     *
     * @return the path of /storage/emulated/0/DCIM
     */
    public static String getExternalDcimPath() {
        if (!isSDCardEnableByEnvironment()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM));
    }

    /**
     * Return the path of /storage/emulated/0/Documents.
     *
     * @return the path of /storage/emulated/0/Documents
     */
    public static String getExternalDocumentsPath() {
        if (!isSDCardEnableByEnvironment()) return "";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return getAbsolutePath(Environment.getExternalStorageDirectory()) + "/Documents";
        }
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS));
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package.
     *
     * @return the path of /storage/emulated/0/Android/data/package
     */
    public static String getExternalAppDataPath() {
        if (!isSDCardEnableByEnvironment()) return "";
        File externalCacheDir = BaseApplication.appContext.getExternalCacheDir();
        if (externalCacheDir == null) return "";
        return getAbsolutePath(externalCacheDir.getParentFile());
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/cache.
     *
     * @return the path of /storage/emulated/0/Android/data/package/cache
     */
    public static String getExternalAppCachePath() {
        if (!isSDCardEnableByEnvironment()) return "";
        return getAbsolutePath(BaseApplication.appContext.getExternalCacheDir());
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files
     */
    public static String getExternalAppFilesPath() {
        if (!isSDCardEnableByEnvironment()) return "";
        return getAbsolutePath(BaseApplication.appContext.getExternalFilesDir(null));
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Music.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Music
     */
    public static String getExternalAppMusicPath() {
        if (!isSDCardEnableByEnvironment()) return "";
        return getAbsolutePath(BaseApplication.appContext.getExternalFilesDir(Environment.DIRECTORY_MUSIC));
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Podcasts.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Podcasts
     */
    public static String getExternalAppPodcastsPath() {
        if (!isSDCardEnableByEnvironment()) return "";
        return getAbsolutePath(BaseApplication.appContext.getExternalFilesDir(Environment.DIRECTORY_PODCASTS));
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Ringtones.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Ringtones
     */
    public static String getExternalAppRingtonesPath() {
        if (!isSDCardEnableByEnvironment()) return "";
        return getAbsolutePath(BaseApplication.appContext.getExternalFilesDir(Environment.DIRECTORY_RINGTONES));
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Alarms.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Alarms
     */
    public static String getExternalAppAlarmsPath() {
        if (!isSDCardEnableByEnvironment()) return "";
        return getAbsolutePath(BaseApplication.appContext.getExternalFilesDir(Environment.DIRECTORY_ALARMS));
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Notifications.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Notifications
     */
    public static String getExternalAppNotificationsPath() {
        if (!isSDCardEnableByEnvironment()) return "";
        return getAbsolutePath(BaseApplication.appContext.getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS));
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Pictures.
     *
     * @return path of /storage/emulated/0/Android/data/package/files/Pictures
     */
    public static String getExternalAppPicturesPath() {
        if (!isSDCardEnableByEnvironment()) return "";
        return getAbsolutePath(BaseApplication.appContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES));
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Movies.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Movies
     */
    public static String getExternalAppMoviesPath() {
        if (!isSDCardEnableByEnvironment()) return "";
        return getAbsolutePath(BaseApplication.appContext.getExternalFilesDir(Environment.DIRECTORY_MOVIES));
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Download.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Download
     */
    public static String getExternalAppDownloadPath() {
        if (!isSDCardEnableByEnvironment()) return "";
        return getAbsolutePath(BaseApplication.appContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/DCIM.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/DCIM
     */
    public static String getExternalAppDcimPath() {
        if (!isSDCardEnableByEnvironment()) return "";
        return getAbsolutePath(BaseApplication.appContext.getExternalFilesDir(Environment.DIRECTORY_DCIM));
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Documents.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Documents
     */
    public static String getExternalAppDocumentsPath() {
        if (!isSDCardEnableByEnvironment()) return "";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return getAbsolutePath(BaseApplication.appContext.getExternalFilesDir(null)) + "/Documents";
        }
        return getAbsolutePath(BaseApplication.appContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS));
    }

    /**
     * Return the path of /storage/emulated/0/Android/obb/package.
     *
     * @return the path of /storage/emulated/0/Android/obb/package
     */
    public static String getExternalAppObbPath() {
        if (!isSDCardEnableByEnvironment()) return "";
        return getAbsolutePath(BaseApplication.appContext.getObbDir());
    }

    public static String getRootPathExternalFirst() {
        String rootPath = getExternalStoragePath();
        if (TextUtils.isEmpty(rootPath)) {
            rootPath = getRootPath();
        }
        return rootPath;
    }

    public static String getAppDataPathExternalFirst() {
        String appDataPath = getExternalAppDataPath();
        if (TextUtils.isEmpty(appDataPath)) {
            appDataPath = getInternalAppDataPath();
        }
        return appDataPath;
    }

    public static String getFilesPathExternalFirst() {
        String filePath = getExternalAppFilesPath();
        if (TextUtils.isEmpty(filePath)) {
            filePath = getInternalAppFilesPath();
        }
        return filePath;
    }

    public static String getCachePathExternalFirst() {
        String appPath = getExternalAppCachePath();
        if (TextUtils.isEmpty(appPath)) {
            appPath = getInternalAppCachePath();
        }
        return appPath;
    }

    private static String getAbsolutePath(final File file) {
        if (file == null) return "";
        return file.getAbsolutePath();
    }

    private static boolean isSDCardEnableByEnvironment() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }
}
