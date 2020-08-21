package com.jitterted.yacht.domain

class DiceRoll private constructor(
	private val dice: List<Int>
)
{
	private constructor(die1: Int, die2: Int, die3: Int, die4: Int, die5: Int)
		: this(listOf(die1, die2, die3, die4, die5))
	
	fun countFor(dieValue: Int): Int =
		dice
			.filter { die -> die == dieValue }
			.count()
	
	fun asSequence(): Sequence<Int> =
		dice.asSequence()
	
	override fun toString(): String =
		"DiceRoll: $dice"
	
	override fun equals(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null || other !is DiceRoll) return false
		
		// compare without caring about the order
		val myDiceSorted = dice.sorted()
		val otherDiceSorted = other.dice.sorted()
		return myDiceSorted == otherDiceSorted
	}
	
	override fun hashCode(): Int =
		dice.hashCode()
	
	operator fun get(index: Int): Int =
		dice[index]
	
	val isEmpty: Boolean
		get() = dice.isEmpty()
	
	companion object
	{
		private val NON_EXISTENT_DICE_ROLL = from(emptyList())
		
		fun of(die1: Int, die2: Int, die3: Int, die4: Int, die5: Int): DiceRoll =
			DiceRoll(die1, die2, die3, die4, die5)
		
		fun from(dieRolls: List<Int>): DiceRoll =
			DiceRoll(dieRolls)
		
		fun empty(): DiceRoll =
			NON_EXISTENT_DICE_ROLL
	}
}
