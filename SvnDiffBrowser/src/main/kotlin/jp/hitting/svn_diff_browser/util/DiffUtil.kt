package jp.hitting.svn_diff_browser.util

import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStreamReader

/**
 * Diff Utility
 * Created by hitting on 2016/03/28.
 */
class DiffUtil {

    companion object {

        private val SEPARATER = "==================================================================="

        /**
         * format a byte array of diff info.
         * @param diffByteArray diff info
         * @return formatted diff
         */
        fun formatDiff(diffByteArray: ByteArray): String {
            var str = StringBuffer()
            var temp = ""

            val inputStream = ByteArrayInputStream(diffByteArray)
            BufferedReader(InputStreamReader(inputStream)).use {
                var counter = 0
                it.lines().forEach {
                    if (counter > 0) {
                        val dir = if (counter == 2) "--- a/" else "+++ b/"
                        str.appendln(dir + it.substring(4))
                        counter--;
                    } else if (SEPARATER.equals(it)) {
                        // separater line
                        counter = 2
                        str.appendln("diff")
                        temp = ""
                    } else {
                        // code line
                        str.appendln(temp)
                        temp = it
                    }
                }
            }
            // add last diff
            str.appendln(temp)
            return str.toString().trim()
        }

    }

}