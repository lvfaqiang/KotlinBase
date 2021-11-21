package cn.basic.core.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri

/**
 * AndroidUtil2021/11/21 12:14 下午
 * @desc :
 *
 */
object AndroidUtil {

    fun checkPackage(context: Context, packageName: String, needToStore: Boolean = false): Boolean {
        val manager = context.packageManager
        try {
            val info =
                manager.getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES)
            return true
        } catch (e: Exception) {
            if (needToStore) {
                //手机未安装，跳转到应用商店下载，并返回false
                toMarket(context, packageName)
            }
            return false
        }
    }

    fun toMarket(ctx: Context, packageName: String, noMarket: () -> Unit = {}) {
        val uri = Uri.parse("market://details?id=${packageName}")
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = uri
        val packageManager = ctx.packageManager
        val list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        if (list.size > 0) {
            ctx.startActivity(intent)
        } else {
            // 无法跳转到应用市场，请在主流应用市场下载，
            noMarket()
        }
    }
}