package com.taptap.apk_checker_plugin.data


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

open class Option {
    @SerializedName("name")
    @Expose
    open var name: String = ""
}

data class FileSizeOption(

    @SerializedName("--min")
    @Expose
    var min: String = "10",
    @SerializedName("--order")
    @Expose
    var order: String = "desc",
    @SerializedName("--suffix")
    @Expose
    var suffix: String = "png, jpg, jpeg, gif, arsc",

    override var name: String = "-fileSize"
):  Option()

data class UncompressedFileOption(
    @SerializedName("--suffix")
    @Expose
    var suffix: String = "png, jpg, jpeg, gif, arsc",

    override var name: String = "-uncompressedFile"
): Option()

data class UnusedResourcesOption(
    @SerializedName("--ignoreResources")
    @Expose
    var ignoreResources: List<String> = listOf("R.raw.*",
        "R.style.*",
        "R.attr.*",
        "R.id.*",
        "R.string.*",
        "R.dimen.*",
        "R.color.*"),

    @SerializedName("--rTxt")
    @Expose
    internal var rTxt: String = "",

    override var name: String = "-unusedResources"
): Option()

data class CountMethodOption(
    @SerializedName("--group")
    @Expose
    var group: String = "package",

    override var name: String = "-countMethod"
): Option()

data class UnusedAssetsOption(
    @SerializedName("--ignoreAssets")
    @Expose
    var ignoreAssets: List<String> = listOf("*.so"),

    override var name: String = "-unusedAssets"
): Option()

data class FindNonAlphaPngOption(
    override var name: String = "-findNonAlphaPng",

    @SerializedName("--min")
    @Expose
    var min: String = "10"
): Option()