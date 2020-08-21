package com.jitterted.yacht.domain

import com.jitterted.yacht.domain.ScoreCategory.BIGSTRAIGHT
import com.jitterted.yacht.domain.ScoreCategory.CHOICE
import com.jitterted.yacht.domain.ScoreCategory.FIVES
import com.jitterted.yacht.domain.ScoreCategory.FOUROFAKIND
import com.jitterted.yacht.domain.ScoreCategory.FOURS
import com.jitterted.yacht.domain.ScoreCategory.FULLHOUSE
import com.jitterted.yacht.domain.ScoreCategory.LITTLESTRAIGHT
import com.jitterted.yacht.domain.ScoreCategory.ONES
import com.jitterted.yacht.domain.ScoreCategory.SIXES
import com.jitterted.yacht.domain.ScoreCategory.THREES
import com.jitterted.yacht.domain.ScoreCategory.TWOS
import com.jitterted.yacht.domain.ScoreCategory.YACHT

class Scoreboard
{
	private val scoredCategoryMap: MutableMap<ScoreCategory, ScoredCategory> = HashMap()
	private fun populateScoredCategoriesMap()
	{
		for (scoreCategory: ScoreCategory in ScoreCategory.values())
		{
			scoredCategoryMap[scoreCategory] = ScoredCategory.createUnassignedScoredCategoryFor(scoreCategory)
		}
	}
	
	private fun populateScorerMap()
	{
		val yachtScorer = YachtScorer()
		scorerMap[ONES] = yachtScorer::scoreAsOnes
		scorerMap[TWOS] = yachtScorer::scoreAsTwos
		scorerMap[THREES] = yachtScorer::scoreAsThrees
		scorerMap[FOURS] = yachtScorer::scoreAsFours
		scorerMap[FIVES] = yachtScorer::scoreAsFives
		scorerMap[SIXES] = yachtScorer::scoreAsSixes
		scorerMap[FULLHOUSE] = yachtScorer::scoreAsFullHouse
		scorerMap[FOUROFAKIND] = yachtScorer::scoreAsFourOfAKind
		scorerMap[LITTLESTRAIGHT] = yachtScorer::scoreAsLittleStraight
		scorerMap[BIGSTRAIGHT] = yachtScorer::scoreAsBigStraight
		scorerMap[CHOICE] = yachtScorer::scoreAsChoice
		scorerMap[YACHT] = yachtScorer::scoreAsYacht
	}
	
	fun score(): Int =
		scoredCategoryMap
			.values
			.map { it.score }
			.sum()
	
	fun scoreAs(scoreCategory: ScoreCategory, diceRoll: DiceRoll)
	{
		checkCategoryUnassigned(scoreCategory)
		scoredCategoryMap.put(scoreCategory,
			ScoredCategory(scoreCategory,
				diceRoll,
				scorerMap.getValue(scoreCategory)(diceRoll)))
	}
	
	fun scoredCategories(): List<ScoredCategory>
	{
		return ScoreCategory.values()
			.map { scoreCategory: ScoreCategory -> scoredCategoryFor(scoreCategory) }
			.toList()
	}
	
	val isComplete: Boolean
		get() = scoredCategoryMap
			.values
			.all(ScoredCategory::isAssigned)
	
	val isEmpty: Boolean
		get() = scoredCategoryMap
			.values
			.none(ScoredCategory::isAssigned)
	
	private fun scoredCategoryFor(scoreCategory: ScoreCategory): ScoredCategory
	{
		return scoredCategoryMap.getValue(scoreCategory)
	}
	
	private fun checkCategoryUnassigned(scoreCategory: ScoreCategory)
	{
		val scoredCategory: ScoredCategory = scoredCategoryMap.getValue(scoreCategory)
		if (scoredCategory.isAssigned)
			throw IllegalStateException()
	}
	
	companion object
	{
		private val scorerMap: MutableMap<ScoreCategory, (DiceRoll) -> Int> = HashMap()
	}
	
	init
	{
		populateScorerMap()
		populateScoredCategoriesMap()
	}
}
