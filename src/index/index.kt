@file:Suppress("UnsafeCastFromDynamic")

package index

import com.jitterted.yacht.ui.react.Palette
import com.jitterted.yacht.ui.react.gameUi
import kotlinext.js.require
import kotlinext.js.requireAll
import kotlinx.css.CSSBuilder
import kotlinx.css.Position.absolute
import kotlinx.css.backgroundColor
import kotlinx.css.body
import kotlinx.css.color
import kotlinx.css.fontFamily
import kotlinx.css.height
import kotlinx.css.html
import kotlinx.css.margin
import kotlinx.css.padding
import kotlinx.css.pct
import kotlinx.css.position
import kotlinx.css.px
import kotlinx.css.top
import kotlinx.css.width
import react.dom.render
import styled.StyledComponents
import styled.injectGlobal
import kotlin.browser.document

fun main()
{
	requireAll(require.context("src", true, js("/\\.css$/")))
	
	render(document.getElementById("root")) {
		gameUi()
	}
	
	val styles = CSSBuilder().apply {
		html {
			backgroundColor = Palette.backgroundColor
			color = Palette.foregroundColor
		}
		
		// no clue how to do `html, body { ... }` without using descendants
		descendants("html, body") {
			top = 0.px
			position = absolute
			width = 100.pct
			height = 100.pct
		}
		
		body {
			margin(0.px)
			padding(0.px)
			fontFamily = "Times New Roman"
		}
		
		// also no clue how to do named tags without descendants
		descendants("#root") {
			padding(8.px)
		}
	}
	
	StyledComponents.injectGlobal(styles.toString())
}
