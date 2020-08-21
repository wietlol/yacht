package com.jitterted.yacht.ui.react

import com.jitterted.yacht.domain.Game
import com.jitterted.yacht.domain.ScoreCategory
import kotlinx.css.BorderCollapse.collapse
import kotlinx.css.BorderStyle.solid
import kotlinx.css.borderCollapse
import kotlinx.css.marginRight
import kotlinx.css.minWidth
import kotlinx.css.padding
import kotlinx.css.properties.border
import kotlinx.css.px
import kotlinx.html.InputType.checkBox
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.button
import react.dom.h1
import react.dom.h3
import react.dom.hr
import react.dom.input
import react.dom.label
import react.dom.tbody
import react.dom.td
import react.dom.th
import react.dom.tr
import react.setState
import styled.css
import styled.styledDiv
import styled.styledH2
import styled.styledTable

interface GameState : RState
{
	var game: Game?
	var selectedDiceIndices: List<Int>
}

class GameUi : RComponent<RProps, GameState>()
{
	override fun RBuilder.render()
	{
		styledDiv {
			val game = state.game
			when
			{
				game == null ->
				{
					h1 { +"Yacht!" }
					
					button {
						+"Start Game"
						
						attrs.onClickFunction = {
							setState {
								this.game = Game()
									.also { it.start() }
							}
						}
					}
				}
				game.isOver ->
				{
					h1 { +"Game Over" }
					
					h3 { +"Your Score: ${game.score}" }
					
					styledTable {
						css {
							borderCollapse = collapse
							descendants("th, td") {
								border(1.px, solid, Palette.foregroundColor)
								borderCollapse = collapse
								
								padding(6.px)
								minWidth = 80.px
							}
						}
						tbody {
							tr {
								th { +"Category" }
								th { +"Roll" }
								th { +"Score" }
							}
							
							val scoredCategories = game.scoredCategories()
								.associateBy { it.scoreCategory }
							ScoreCategory.values()
								.map { it to scoredCategories.getValue(it) }
								.forEach { (category, score) ->
									tr {
										td {
											+category.name
										}
										td {
											+score.diceRoll.asSequence().joinToString(" ")
										}
										td {
											+score.score.toString()
										}
									}
								}
						}
					}
					
					button {
						+"Start New Game"
						attrs.onClickFunction = {
							setState {
								this.game = Game()
									.also { it.start() }
							}
						}
					}
				}
				else ->
				{
					h1 { +"Roll Results" }
					
					h3 { +"Current Score: ${game.score}" }
					
					styledTable {
						css {
							borderCollapse = collapse
							descendants("th, td") {
								border(1.px, solid, Palette.foregroundColor)
								borderCollapse = collapse
								
								padding(6.px)
								minWidth = 80.px
							}
						}
						tbody {
							tr {
								th { +"Category" }
								th { +"Roll" }
								th { +"Score" }
							}
							
							val scoredCategories = game.scoredCategories()
								.associateBy { it.scoreCategory }
							ScoreCategory.values()
								.map { it to scoredCategories.getValue(it) }
								.forEach { (category, score) ->
									scoreRow(game, category, score, assign = {
										setState {
											game.assignRollTo(category)
										}
									})
								}
						}
					}
					
					if (game.roundCompleted().not())
					{
						hr { }
						
						styledH2 {
							+"You rolled:"
							
							css {
								children("label:not(:last-child)") {
									marginRight = 10.px
								}
							}
							
							game.lastRoll.asSequence().forEachIndexed { index, roll ->
								label {
									input(checkBox) {
										attrs {
											disabled = game.canReRoll().not()
											checked = state.selectedDiceIndices.contains(index)
											onClickFunction = { event ->
												val checked = (event.target as HTMLInputElement).checked
												setState {
													selectedDiceIndices = if (checked)
														selectedDiceIndices
															.plus(index)
													else
														selectedDiceIndices
															.filter { it != index }
												}
											}
										}
									}
									+roll.toString()
								}
							}
						}
						
						button {
							+"Re-roll"
							
							attrs {
								disabled = game.canReRoll().not()
								
								onClickFunction = {
									setState {
										val keptDice = game.lastRoll.asSequence().filterIndexed { index, _ -> selectedDiceIndices.contains(index) }.toList()
										game.reRoll(keptDice)
										selectedDiceIndices = emptyList()
									}
								}
							}
						}
					}
					
					hr { }
					
					button {
						+"Roll Dice"
						
						attrs {
							disabled = game.roundCompleted().not()
							
							onClickFunction = {
								setState {
									game.rollDice()
									selectedDiceIndices = emptyList()
								}
							}
						}
					}
				}
			}
		}
	}
}

fun RBuilder.gameUi() = child(GameUi::class) {}
