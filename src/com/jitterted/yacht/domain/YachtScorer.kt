package com.jitterted.yacht.domain

import kotlin.collections.Map.Entry

class YachtScorer
{
	fun scoreAsOnes(roll: DiceRoll): Int =
		calculateScore(roll, 1)
	
	fun scoreAsTwos(roll: DiceRoll): Int =
		calculateScore(roll, 2)
	
	fun scoreAsThrees(roll: DiceRoll): Int =
		calculateScore(roll, 3)
	
	fun scoreAsFours(roll: DiceRoll): Int =
		calculateScore(roll, 4)
	
	fun scoreAsFives(roll: DiceRoll): Int =
		calculateScore(roll, 5)
	
	fun scoreAsSixes(roll: DiceRoll): Int =
		calculateScore(roll, 6)
	
	fun scoreAsFullHouse(roll: DiceRoll): Int =
		if (!isValidFullHouse(roll))
			0
		else
			roll.asSequence()
				.distinct()
				.map { calculateScore(roll, it) }
				.sum()
	
	private fun isValidFullHouse(roll: DiceRoll): Boolean
	{
		val dieToCountMap: Map<Int, Int> = createDieToCountMap(roll)
		val numberOfDiceOccurringTwoOrThreeTimes = countForDieOccurringTwoOrThreeTimes(dieToCountMap)
		return (hasTwoUniqueDice(dieToCountMap)
			&& numberOfDiceOccurringTwoOrThreeTimes == 2)
	}
	
	private fun createDieToCountMap(roll: DiceRoll): Map<Int, Int> =
		roll
			.asSequence()
			.groupBy { it }
			.mapValues { it.component2().size }
			.toMap()
	
	private fun countForDieOccurringTwoOrThreeTimes(dieToCountMap: Map<Int, Int>): Int =
		dieToCountMap
			.entries
			.filter { e: Entry<Int, Int> -> twoOrThreeOccurrences(e) }
			.count()
	
	private fun hasTwoUniqueDice(dieToCountMap: Map<Int, Int>): Boolean =
		dieToCountMap.size == 2
	
	private fun twoOrThreeOccurrences(e: Entry<Int, Int>): Boolean =
		(2..3).contains(e.value)
	
	private fun calculateScore(dice: DiceRoll, scoreCategory: Int): Int =
		dice.countFor(scoreCategory) * scoreCategory
	
	fun scoreAsFourOfAKind(diceRoll: DiceRoll): Int =
		calculateScoreOfAKind(diceRoll, 4)
	
	fun scoreAsYacht(diceRoll: DiceRoll): Int =
		calculateScoreOfAKind(diceRoll, 5)
	
	private fun calculateScoreOfAKind(diceRoll: DiceRoll, kind: Int): Int =
		createDieToCountMap(diceRoll)
			.filter { die -> occursAtLeast(kind, die) }
			.map { die -> multiply(kind, die) }
			.sum()
	
	fun scoreAsLittleStraight(diceRoll: DiceRoll): Int =
		if (diceRoll == LITTLE_STRAIGHT) 30 else 0
	
	fun scoreAsBigStraight(diceRoll: DiceRoll): Int =
		if (diceRoll == BIG_STRAIGHT) 30 else 0
	
	fun scoreAsChoice(diceRoll: DiceRoll): Int =
		diceRoll.asSequence().sum()
	
	private fun occursAtLeast(kind: Int, die: Entry<Int, Int>): Boolean =
		die.value >= kind
	
	private fun multiply(kind: Int, die: Entry<Int, Int>): Int =
		die.key * kind
	
	companion object
	{
		private val LITTLE_STRAIGHT = DiceRoll.of(1, 2, 3, 4, 5)
		private val BIG_STRAIGHT = DiceRoll.of(2, 3, 4, 5, 6)
	}
}
