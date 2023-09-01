//package lv.semyonmoroshek.chililabstask
//
//import android.app.Application
//import androidx.lifecycle.Lifecycle
//import androidx.lifecycle.ProcessLifecycleOwner
//import dagger.hilt.android.HiltAndroidApp
//
//@HiltAndroidApp
//class App : Application() {
//
//    companion object {
//        private var lifecycle: Lifecycle? = null
//
//        fun getCurrentState(): Lifecycle.State? {
//            return lifecycle!!.currentState
//        }
//    }
//
//    override fun onCreate() {
//        super.onCreate()
//
//        lifecycle = ProcessLifecycleOwner.get().lifecycle;
//
//    }
//
//
//}