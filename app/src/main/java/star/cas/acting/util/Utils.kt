package star.cas.acting.util


import android.os.Build
import star.cas.acting.BuildConfig
import java.util.*

object Utils {
    fun checkIsEmu(): Boolean {
        /*if (BuildConfig.DEBUG) return true*/
        val phoneModel = Build.MODEL
        val buildProduct = Build.PRODUCT
        val buildHardware = Build.HARDWARE
        val brand = Build.BRAND
        return (brand.lowercase().startsWith("generic") && Build.DEVICE.lowercase()
            .startsWith("generic"))
                || (Build.FINGERPRINT.lowercase().startsWith("generic")
                || (Build.FINGERPRINT.lowercase().startsWith("google")
                || phoneModel.lowercase().contains("google_sdk")
                || phoneModel.lowercase(Locale.getDefault()).contains("droid4x")
                || phoneModel.lowercase().contains("emulator")
                || phoneModel.lowercase().contains("android sdk built for x86")
                || Build.MANUFACTURER.lowercase().contains("genymotion")
                || buildHardware == "goldfish"
                || brand.lowercase().contains("google")
                || buildHardware == "vbox86"
                || buildProduct == "sdk"
                || buildProduct == "google_sdk"
                || buildProduct == "sdk_x86"
                || buildProduct == "vbox86p"
                || Build.BOARD.lowercase(Locale.getDefault()).contains("nox")
                || Build.BOOTLOADER.lowercase(Locale.getDefault()).contains("nox")
                || buildHardware.lowercase(Locale.getDefault()).contains("nox")
                || buildProduct.lowercase(Locale.getDefault()).contains("nox")))
    }
}