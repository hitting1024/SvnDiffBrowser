package jp.hitting.svn_diff_browser.util

import jp.hitting.svn_diff_browser.model.DiffInfo
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import java.util.*

/**
 * Diff Utility
 * Created by hitting on 2016/03/28.
 */
class DiffUtil {

    companion object {

        private val SEPARATER = "==================================================================="

        /**
         * parse a byte array of diff info.
         * @param diffByteArray diff info
         * @return diff info list
         */
        fun parseDiff(diffByteArray: ByteArray): List<DiffInfo> {
            val inputStream = ByteArrayInputStream(diffByteArray)
            val reader = BufferedReader(InputStreamReader(inputStream))

            var diffList = ArrayList<DiffInfo>()
            var str = StringBuffer()
            var temp = ""
            var diffInfo = DiffInfo()
            reader.lines().forEach {
                if (diffInfo.path.length == 0) {
                    // at first index info line
                    diffInfo.path = it.split(" ")[1]
                } else if (SEPARATER.equals(it)) {
                    // separater line
                    if (str.length > 0) {
                        diffInfo.diff = str.toString().trim()
                        diffList.add(diffInfo)
                        // init
                        diffInfo = DiffInfo()
                        diffInfo.path = temp.split(" ")[1]
                        str = StringBuffer()
                        temp = ""
                    }
                } else {
                    // code line
                    str.appendln(temp)
                    temp = it
                }
            }
            // add last diff
            str.appendln(temp)
            diffInfo.diff = str.toString().trim()
            diffList.add(diffInfo)

            return diffList
        }

    }

}