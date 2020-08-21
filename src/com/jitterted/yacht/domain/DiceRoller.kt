package com.jitterted.yacht.domain

class DiceRoller(
	private val dieRoller: DieRoller = RandomDieRoller()
)
{
	fun roll(): DiceRoll =
		reRoll(emptyList())
	
	fun reRoll(keptDice: List<Int>): DiceRoll
	{
		val dieRolls = keptDice.toMutableList()
		rollTheRest(dieRolls)
		return DiceRoll.from(dieRolls)
	}
	
	private fun rollTheRest(dieRolls: MutableList<Int>)
	{
		while (dieRolls.size < YACHT_DICE_COUNT)
			dieRolls.add(dieRoller.roll())
	}
	
	companion object
	{
		private const val YACHT_DICE_COUNT = 5
	}
}
