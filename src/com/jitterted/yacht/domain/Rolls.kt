package com.jitterted.yacht.domain

class Rolls private constructor(
	private var rolls: Int
)
{
	fun increment()
	{
		rolls++
	}
	
	fun canReRoll(): Boolean =
		rolls < MAX_NUMBER_OF_ROLLS_PER_TURN
	
	companion object
	{
		private const val MAX_NUMBER_OF_ROLLS_PER_TURN = 3
		
		fun start(): Rolls =
			Rolls(1)
	}
}
