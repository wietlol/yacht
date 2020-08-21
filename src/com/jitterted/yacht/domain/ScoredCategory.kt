package com.jitterted.yacht.domain

class ScoredCategory(
	val scoreCategory: ScoreCategory,
	val diceRoll: DiceRoll,
	val score: Int
)
{
	val isAssigned: Boolean
		get() = !diceRoll.isEmpty
	
	override fun equals(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null || other !is ScoredCategory) return false
		if (score != other.score) return false
		return if (scoreCategory !== other.scoreCategory)
			false
		else
			diceRoll == other.diceRoll
	}
	
	override fun hashCode(): Int
	{
		var result = scoreCategory.hashCode()
		result = 31 * result + diceRoll.hashCode()
		result = 31 * result + score
		return result
	}
	
	override fun toString(): String =
		"ScoredCategory: " +
			"scoreCategory=" + scoreCategory +
			", diceRoll=" + diceRoll +
			", score=" + score
	
	companion object
	{
		fun createUnassignedScoredCategoryFor(scoreCategory: ScoreCategory): ScoredCategory =
			ScoredCategory(scoreCategory, DiceRoll.empty(), 0)
	}
}
