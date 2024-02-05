package com.intravan.salesmanagement.core.presentation.base

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.intravan.salesmanagement.core.util.DebugLog

/**
 * Activity.
 */
open class BaseLifecycleLogActivity : AppCompatActivity() {

    /*
     * Activity가 생성 될 때 마다(초기 생성 및 재 생성) 호출.
     * 액티비티를 초기화 한다.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //==================================================
        DebugLog.v { "=========================> ${javaClass.simpleName} onCreate <" }
        //==================================================
    }

    /*
     * onCreate가 종료된 후 호출된다. UI 상태 복구에 사용된다.
     * savedInstanceState를 이용해 UI 상태를 복구한다. 이 번들은 onCreate에도 전달된다.
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        //==================================================
        DebugLog.v { "=========================> ${javaClass.simpleName} onRestoreInstanceState <" }
        //==================================================
    }

    /*override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        //==================================================
        DebugLog.v { "=========================> ${javaClass.simpleName} onNewIntent <" }
        //==================================================
    }*/

    /*
     * Activity를 화면에 표시할 수 있는 상태로 만들기 위한 callback.
     * Activity가 화면에 보이므로, 필요한 모든 UI변경 사항을 적용한다.
     */
    override fun onStart() {
        super.onStart()

        //==================================================
        DebugLog.v { "=========================> ${javaClass.simpleName} onStart <" }
        //==================================================
    }

    /*
     * Activity가 Stop 상태에서 다시 Running 상태로 복귀할 때 처음 호출.
     * 이 메소드 완료 후 Activity가 Foreground에 표시되면 onResume(), 이 외 onStop() 호출.
     * Activity가 이미 화면에 보이고 있다고 생각하고 변경된 내용을 읽어 들인다.
     */
    override fun onRestart() {
        super.onRestart()

        //==================================================
        DebugLog.v { "=========================> ${javaClass.simpleName} onRestart <" }
        //==================================================
    }

    /*
     * Activity가 Foreground에 표시되고 User와 Interract 할 수 있는 상태 전까지 호출.
     * 일시 중지된 UI 업데이트나 스레드, 혹은 Activity가 비활성화 되면서 잠시 중단됐던 처리를모두 재개한다.
     */
    override fun onResume() {
        super.onResume()

        //==================================================
        DebugLog.v { "=========================> ${javaClass.simpleName} onResume <" }
        //==================================================
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        //==================================================
        DebugLog.v { "=========================> ${javaClass.simpleName} onWindowFocusChanged($hasFocus) <" }
        //==================================================
    }

    /*
     *
     */
    override fun startActivity(intent: Intent) {
        super.startActivity(intent)
        //==================================================
        DebugLog.v { "=========================> ${javaClass.simpleName} startActivity <" }
        //==================================================
    }

    /*
     *
     */
    override fun onChildTitleChanged(childActivity: Activity, title: CharSequence) {
        super.onChildTitleChanged(childActivity, title)
        //==================================================
        DebugLog.v { "=========================> ${javaClass.simpleName} onChildTitleChanged <" }
        //==================================================
    }

    /*
     *
     */
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        //==================================================
        DebugLog.v { "=========================> ${javaClass.simpleName} onPostCreate <" }
        //==================================================
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //==================================================
        DebugLog.v { "=========================> ${javaClass.simpleName} onCreateOptionsMenu <" }
        //==================================================

        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        //==================================================
        DebugLog.v { "=========================> ${javaClass.simpleName} onPrepareOptionsMenu <" }
        //==================================================

        return super.onCreateOptionsMenu(menu)
    }

    /*
     * 활성 수명이 끝날 때, UI 상태 변화를 저장하기 위해 호출된다.
     * UI 상태 변화를 savedInstanceState에 저장한다.
     * 프로세스가 종료되고 재시작 될 경우 이 번들이 onCreate에 전달될 것이다.
     */
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)

        //==================================================
        DebugLog.v { "=========================> ${javaClass.simpleName} onSaveInstanceState <" }
        //==================================================
    }

    /*
     * A Activity가 새로운 B Activity에 의해 포커스를 잃었을 떄 호출.
     * Activity가 활성 상태의 포그라운드 Activity가 아닐 경우
     * 업데이트할 필요가 없는 UI 업데이트나 스레드, 혹은 CPU 사용량이 많은 처리를 일시 중단한다.
     */
    override fun onPause() {
        super.onPause()

        //==================================================
        DebugLog.v { "=========================> ${javaClass.simpleName} onPause <" }
        //==================================================
    }

    /*
     * Activity 사용자에게 전혀 보이지 않게 되면 호출.
     * 남아 있는 UI 업데이트나 스레드 혹은 Activity가 화면에 보이지 않을 때 필요치 않은 처리를 일시 중단한다.
     * 이 메서드가 호출되고 난 뒤에는 프로세스가 종료될 가능성이 있으므로 바뀐 모든 내용과 상태 변화를 지속시킨다.
     */
    override fun onStop() {
        super.onStop()

        //==================================================
        DebugLog.v { "=========================> ${javaClass.simpleName} onStop <" }
        //==================================================
    }

    override fun onUserInteraction() {
        super.onUserInteraction()

        //==================================================
        DebugLog.v { "=========================> ${javaClass.simpleName} onUserInteraction <" }
        //==================================================
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()

        //==================================================
        DebugLog.v { "=========================> ${javaClass.simpleName} onUserLeaveHint <" }
        //==================================================
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        //==================================================
        DebugLog.v { "=========================> ${javaClass.simpleName} onConfigurationChanged <" }
        //==================================================
    }

    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //==================================================
        DebugLog.v { "=========================> " + javaClass.simpleName + " onActivityResult <" }
        //==================================================
    }

    /*
     * level.
     * ComponentCallbacks2.TRIM_MEMORY_COMPLETE: 80,
     *  시스템 메모리가 매우 부족하며, LRU 목록에서 가장 먼저 종료될 프로세스, 가능한 한 많은 메모리를 해제해야 함.
     * ComponentCallbacks2.TRIM_MEMORY_MODERATE: 60,
     *  시스템 메모리가 더 낮아져서, LRU 목록에서 중간 정도에 위치한 프로세스가 종료될 위험이 있음.
     * ComponentCallbacks2.TRIM_MEMORY_BACKGROUND: 40,
     *  시스템 메모리가 낮아서 백그라운드 프로세스가 종료될 위험이 있고,
     *  이는 앱이 LRU(Least Recently Used) 목록에서 상대적으로 멀리 떨어져 있음을 의미.
     * ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN: 20,
     *  앱의 UI가 사용자에게 보이지 않게 되었고,
     *  이는 앱이 백그라운드로 전환되었음을 의미하며, 이 상태에서는 UI 관련 리소스를 해제 가능.
     * ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL: 15,
     *  시스템 메모리가 극도로 부족하고, 메모리를 즉시 정리하지 않으면 시스템이 백그라운드 프로세스를 종료.
     * ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW: 10,
     *  시스템 메모리가 매우 낮아져서, 앱 프로세스를 종료하지 않기 위해서는 메모리를 정리.
     * ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE: 5
     *  시스템은 메모리가 어느 정도 부족하지만, 앱 프로세스를 종료하지 않아도 될 상태.
     */
    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)

        //==================================================
        DebugLog.w { "=========================> ${javaClass.simpleName} onTrimMemory Level : $level <" }
        //==================================================
    }

    override fun onLowMemory() {
        super.onLowMemory()

        //==================================================
        DebugLog.w { "=========================> ${javaClass.simpleName} onLowMemory <" }
        //==================================================
    }

    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()

        //==================================================
        DebugLog.v { "=========================> ${javaClass.simpleName} onBackPressed <" }
        //==================================================
    }

    open fun onBackPressedCallback() {
        //==================================================
        DebugLog.v { "=========================> ${javaClass.simpleName} onBackPressedCallback <" }
        //==================================================
    }

    /*
     * finish() 호출 등으로 Activity가 종료될때 호출.
     * 스레드를 종료하고, 데이터베이스 연결을 닫는 등 모든 리소스를 해제한다.
     */
    override fun onDestroy() {
        super.onDestroy()

        //==================================================
        DebugLog.v { "=========================> ${javaClass.simpleName} onDestroy <" }
        //==================================================
    }
}