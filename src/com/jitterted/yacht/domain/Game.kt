package com.jitterted.yacht.domain

class Game(
	private val diceRoller: DiceRoller = DiceRoller()
)
{
	var lastRoll: DiceRoll = DiceRoll.empty()
		private set
	private var scoreboard: Scoreboard
	private var rolls: Rolls = Rolls.start()
	private var roundCompleted = false
	
	val score: Int
		get() = scoreboard.score()
	
	fun rollDice()
	{
		roundCompleted = false
		rolls = Rolls.start()
		lastRoll = diceRoller.roll()
	}
	
	fun reRoll(keptDice: List<Int>)
	{
		rolls.increment()
		lastRoll = diceRoller.reRoll(keptDice)
	}
	
	fun assignRollTo(scoreCategory: ScoreCategory)
	{
		scoreboard.scoreAs(scoreCategory, lastRoll)
		roundCompleted = true
	}
	
	fun scoredCategories(): List<ScoredCategory> =
		scoreboard.scoredCategories()
	
	fun canReRoll(): Boolean =
		if (roundCompleted())
			false
		else
			rolls.canReRoll()
	
	fun roundCompleted(): Boolean =
		roundCompleted
	
	val isOver: Boolean
		get() = scoreboard.isComplete
	
	fun start()
	{
		scoreboard = Scoreboard()
		roundCompleted = true
	}
	
	init
	{
		scoreboard = Scoreboard()
	}
}
