package com.jitterted.yacht.domain

import kotlin.random.Random

class RandomDieRoller : DieRoller
{
	private val random: Random = Random
	
	override fun roll(): Int =
		random.nextInt(6) + 1
}
