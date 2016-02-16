package jp.hitting.svn_diff_browser

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class SvnDiffBrowserApplication

fun main(args: Array<String>) {
	SpringApplication.run(SvnDiffBrowserApplication::class.java, *args)
}
