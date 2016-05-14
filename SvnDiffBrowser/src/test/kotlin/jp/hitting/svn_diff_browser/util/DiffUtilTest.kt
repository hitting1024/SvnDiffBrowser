package jp.hitting.svn_diff_browser.util

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

        val expected = """
diff
--- a/hoge.txt    (revision 4)
+++ b/hoge.txt    (revision 5)

@@ -2,6 +2,6 @@

fuga

-piyo
-
updated
+
+aaa
diff
--- a/dir/a.txt   (revision 4)
+++ b/dir/a.txt   (revision 5)

@@ -0,0 +1,5 @@
+aaaa
+
+bbbb
+
+cccc
""".trimMargin()

        // execute
        val actual = DiffUtil.formatDiff(diffStr.toByteArray())

        // assert
        Assert.assertEquals(expected, actual)
    }

}