package com.lvfq.kotlinbase.base.old

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lvfq.kotlinbase.base.ISimpleBase
import com.lvfq.kotlinbase.utils.basic.FragmentUtil
import com.lvfq.kotlinbase.utils.basic.ToastUtil
import com.lvfq.kotlinbase.views.ILoading
import com.lvfq.kotlinbase.views.LoadingView
import org.greenrobot.eventbus.EventBus

/**
 * BaseFragment
 * @author FaQiang on 2018/8/28 下午3:40
 * @desc :
 *
 */
abstract class BaseFragment_old : Fragment(),
    ISimpleBase {

    protected val mFragmentUtil: FragmentUtil by lazy {
        FragmentUtil(childFragmentManager)
    }

    private var mLoadingView: ILoading? = null

    protected var mContentView: View? = null
    private var mIsFirst = true
        private set

    override fun isFirst(): Boolean {
        return mIsFirst
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mLoadingView = LoadingView(this.requireContext())
        mLoadingView?.setOnDismissListener(DialogInterface.OnDismissListener {
        })

        if (getLayoutId() != 0 && mContentView == null) {
            mContentView = inflater.inflate(getLayoutId(), container, false)
            return mContentView
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (useEventBus()) {
            EventBus.getDefault().register(this)
        }
        initUI(savedInstanceState)
        initListener()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData(savedInstanceState)
        if (mIsFirst) {
            mIsFirst = false
        }
    }

    override fun onDestroyView() {
        if (useEventBus()) {
            EventBus.getDefault().unregister(this)
        }
        super.onDestroyView()
    }

    override fun showLoading() {
        mLoadingView?.let { loading ->
            loading.takeIf {
                !loading.isShowing() && !isResumed
            }?.let {
                loading.show()
            }
        }
    }

    override fun disLoading() {
        mLoadingView?.dismiss()
    }

    override fun toastSuc(message: String) {
        if (isResumed) {
            ToastUtil.showToast(context, message)
        }
    }

    override fun toastSuc(strId: Int) {
        if (isResumed) {
            ToastUtil.showToast(context, getString(strId))
        }
    }

    override fun toastFailed(message: String) {
        if (isResumed) {
            ToastUtil.showToast(context, message)
        }
    }

    override fun toastFailed(strId: Int) {
        if (isResumed) {
            ToastUtil.showToast(context, getString(strId))
        }
    }

    override fun getContext(): Context? = super.getContext()


    abstract fun getLayoutId(): Int

    abstract fun initUI(savedInstanceState: Bundle?)

    abstract fun initData(savedInstanceState: Bundle?)

    abstract fun initListener()


}