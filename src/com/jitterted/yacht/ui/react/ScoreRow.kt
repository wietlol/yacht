package com.jitterted.yacht.ui.react

import com.jitterted.yacht.domain.Game
import com.jitterted.yacht.domain.ScoreCategory
import com.jitterted.yacht.domain.ScoredCategory
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.dom.button
import react.dom.td
import react.dom.tr

fun RBuilder.scoreRow(game: Game, category: ScoreCategory, score: ScoredCategory, assign: () -> Unit)
{
	tr {
		td {
			button {
				+category.name
				
				attrs {
					onClickFunction = {
						assign()
					}
					disabled = score.isAssigned || game.roundCompleted()
				}
			}
		}
		
		if (score.isAssigned)
		{
			td {
				+score.diceRoll.asSequence().joinToString(" ")
			}
			td {
				+score.score.toString()
			}
		}
		else
		{
			td { +"-" }
			td { +"-" }
		}
	}
}
