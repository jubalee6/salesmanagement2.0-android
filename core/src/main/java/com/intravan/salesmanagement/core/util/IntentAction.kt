package com.intravan.salesmanagement.core.util

import com.intravan.salesmanagement.core.BuildConfig

object IntentAction {

	// 종료.
	const val BROADCAST_FINISH = BuildConfig.LIBRARY_PACKAGE_NAME + ".finish"

	// 개인정보동의.
	const val BROADCAST_PRIVACY_CONSENT = BuildConfig.LIBRARY_PACKAGE_NAME + ".privacy_consent"

	// Activity onRestart.
	const val BROADCAST_ACTIVITY_ON_RESTART = BuildConfig.LIBRARY_PACKAGE_NAME + ".activity_on_restart"

	// appears softinput.
	const val BROADCAST_APPEARS_TO_SHOW_SOFTINPUT = BuildConfig.LIBRARY_PACKAGE_NAME + ".appears_to_show_softinput"

	// disappears softinput.
	const val BROADCAST_DISAPPEARS_TO_HIDE_SOFTINPUT = BuildConfig.LIBRARY_PACKAGE_NAME + ".disappears_to_hide_softinput"

	// 메츨 사용시작.
	const val BROADCAST_SALES_USAGE_START = BuildConfig.LIBRARY_PACKAGE_NAME + ".sales_usage_start"

	// 매출 사용중지.
	const val BROADCAST_SALES_USAGE_STOP = BuildConfig.LIBRARY_PACKAGE_NAME + ".sales_usage_stop"

	// 메츨 저장.
	const val BROADCAST_SALES_REMOTE_DIFF = BuildConfig.LIBRARY_PACKAGE_NAME + ".sales_remote_diff"

	// 견적 저장.
	const val BROADCAST_ESTIMATE_REMOTE_DIFF = BuildConfig.LIBRARY_PACKAGE_NAME + ".estimate_remote_diff"

	// Toast.
	const val BROADCAST_TOAST = BuildConfig.LIBRARY_PACKAGE_NAME + ".toast"

	//
	const val BROADCAST_EVENT = BuildConfig.LIBRARY_PACKAGE_NAME + ".event"

	//
	const val BROADCAST_EVENT_ACTIVATED = BuildConfig.LIBRARY_PACKAGE_NAME + ".event_activated"
}