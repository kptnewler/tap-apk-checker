package com.taptap.apk_checker_plugin.data

import org.gradle.api.provider.Property

open class ApkCheckerExt {
    var fileSizeOption: FileSizeOption = FileSizeOption()
    var countMethodOption: CountMethodOption = CountMethodOption()
    var findNonAlphaPngOption: FindNonAlphaPngOption = FindNonAlphaPngOption()
    var uncompressedFileOption: UncompressedFileOption = UncompressedFileOption()
    var unusedResourcesOption: UnusedResourcesOption = UnusedResourcesOption()
    var unusedAssetsOption: UnusedAssetsOption = UnusedAssetsOption()
    var checkVariant = ""
}