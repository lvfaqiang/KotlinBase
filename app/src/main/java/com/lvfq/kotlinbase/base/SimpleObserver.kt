package com.lvfq.kotlinbase.base

import android.view.View

/**
 * SimpleObserver
 * @author FaQiang on 2018/9/27 下午7:00
 * @Github: <a href="https://github.com/lvfaqiang"/>
 * @Blog: <a href="http://blog.csdn.net/lv_fq"/>
 * @desc :
 *
 */
abstract class SimpleObserver<T>
@JvmOverloads constructor(override var iBase: IBase?, override var view: View?)
    : BaseObserver<ApiBaseRsp<T>>(iBase, view) {

    abstract fun onSuccess(t: T?, metaRsp: MetaRsp?)

    override fun onSuccess(t: ApiBaseRsp<T>) {
        onSuccess(t.data, t.meta)
    }

}