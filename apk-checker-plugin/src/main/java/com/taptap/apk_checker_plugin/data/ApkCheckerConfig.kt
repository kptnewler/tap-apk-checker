package com.taptap.apk_checker_plugin.data

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import sun.jvm.hotspot.debugger.cdbg.basic.BasicLineNumberMapping

data class ApkCheckerConfig(
    @SerializedName("--apk")
    @Expose
    var apk: String = "",
    @SerializedName("--mapping")
    @Expose
    var mapping: String = "",
    @SerializedName("--format")
    @Expose
    var format: String = "mm.json",
    @SerializedName("options")
    @Expose
    var options: List<Option> = listOf(),
    @SerializedName("--output")
    @Expose
    var output: String = ""
)