package com.taobao.util;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.taobao.atlas.framework.Atlas;
import android.taobao.atlas.framework.BundleImpl;
import android.taobao.atlas.framework.Framework;
import android.taobao.atlas.runtime.ActivityTaskMgr;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleException;
import org.osgi.framework.BundleListener;
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.FrameworkListener;

import java.io.File;

/**
 * Created by yumodev on 17/7/7.
 */

public class AtlasPluginUtil {

    public static void init(){
        Atlas.getInstance().addBundleListener(new BundleListener() {
            @Override
            public void bundleChanged(BundleEvent event) {
                if (event != null){
                    String bundleInfo = printBundleInfo(event.getBundle(), false);
                    AppUtil.showToast(event.getType() + " "+bundleInfo);
                }
            }
        });

        Framework.addFrameworkListener(new FrameworkListener() {
            @Override
            public void frameworkEvent(FrameworkEvent event) {
                if (event != null){
                    AppUtil.showToast("framewaorkEvent"+event.getType());
                }
            }
        });
    }
    public static void installPlugin(String bundleName) {
        //远程bundle
        Activity activity = ActivityTaskMgr.getInstance().peekTopActivity();
        File remoteBundleFile = new File(activity.getExternalCacheDir(), "lib" + bundleName.replace(".", "_") + ".so");

        String path = "";
        if (remoteBundleFile.exists()) {
            path = remoteBundleFile.getAbsolutePath();
        } else {
            String message = " 远程bundle不存在，请确定 : " + remoteBundleFile.getAbsolutePath();
            AppUtil.showToast(message);
            return;

        }

        PackageInfo info = activity.getPackageManager().getPackageArchiveInfo(path, 0);
        try {
            Atlas.getInstance().installBundle(info.packageName, new File(path));
        } catch (BundleException e) {
            AppUtil.showToast("远程bundle 安装失败" + e.getMessage());
            e.printStackTrace();
        }
    }

    public static boolean existPlugin(String location){
        Bundle bundle = Atlas.getInstance().getBundle(location);
        return bundle != null;
    }

    /**
     * 获取Bandle的信息
     * @param location
     * @return
     */
    public static String printBundleInfo(String location, boolean show){
        Bundle bundle = Atlas.getInstance().getBundle(location);
        if (bundle == null){
            AppUtil.showToast("bundle 不存在");
        }
        return printBundleInfo(bundle, show);
    }

    public static String printBundleInfo(Bundle bundle, boolean show){
        if (bundle == null){
            return " bundle is null";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("location:"+bundle.getLocation());
        sb.append("\nid:"+bundle.getBundleId());
        sb.append("\nstate"+bundle.getState());
        BundleImpl b = (BundleImpl)bundle;
        if (b.getArchive() != null){
            File archiveFile = b.getArchive().getArchiveFile();
            if (archiveFile != null){
                sb.append("\narchiveFile:"+archiveFile.getAbsolutePath());
            }
            File revisionDir = b.getArchive().getCurrentRevision().getRevisionDir();
            if (revisionDir != null){
                sb.append("\nRevisionDir:"+revisionDir.getAbsolutePath());
            }
        }

        if (show){
            AppUtil.showToast(sb.toString());
        }
        return sb.toString();
    }
}
