package jp.hitting.svn_diff_browser.util

import jp.hitting.svn_diff_browser.model.DiffInfo
import org.junit.Assert
import org.junit.Test
import java.util.*

/**
 * DiffUtil Test.
 * Created by hitting on 2016/03/29.
 */
class DiffUtilTest {

    /**
     * {@link DiffUtil#parseDiff} test.
     */
    @Test
    fun testParseDiff() {
        // test data
        val diffStr = """
Index: hoge.txt
===================================================================
--- hoge.txt    (revision 4)
+++ hoge.txt    (revision 5)
@@ -2,6 +2,6 @@

fuga

-piyo
-
updated
+
+aaa
Index: dir/a.txt
===================================================================
--- dir/a.txt   (revision 4)
+++ dir/a.txt   (revision 5)
@@ -0,0 +1,5 @@
+aaaa
+
+bbbb
+
+cccc
""".trimMargin()

        // expected data

        var expectedList = ArrayList<DiffInfo>()
        val diff1 = DiffInfo()
        diff1.path = "hoge.txt"
        diff1.diff = """
--- hoge.txt    (revision 4)
+++ hoge.txt    (revision 5)
@@ -2,6 +2,6 @@

fuga

-piyo
-
updated
+
+aaa
""".trimMargin()
        expectedList.add(diff1)

        val diff2 = DiffInfo()
        diff2.path = "dir/a.txt"
        diff2.diff = """
--- dir/a.txt   (revision 4)
+++ dir/a.txt   (revision 5)
@@ -0,0 +1,5 @@
+aaaa
+
+bbbb
+
+cccc
""".trimMargin()
        expectedList.add(diff2)

        // execute
        val diffList = DiffUtil.parseDiff(diffStr.toByteArray())

        // assert
        Assert.assertEquals(expectedList.size, diffList.size)
        for (i in 0..expectedList.size-1) {
            val e = expectedList.get(i)
            val a = diffList.get(i)
            Assert.assertEquals(e.path, a.path)
            Assert.assertEquals(e.diff, a.diff)
        }
    }

}