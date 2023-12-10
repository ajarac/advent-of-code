package dev.ajarac.adventofcode.solution.year_2023

import dev.ajarac.adventofcode.solution.Solution
import kotlin.math.pow

object Day7Solution : Solution(2023, 7, 1) {


    override fun solvePart1(input: List<String>): String {
        val hands = buildHands(input = input, withJoker = false)
        val totalWins = calculateWins(hands)
        return totalWins.toString()
    }

    override fun solvePart2(input: List<String>): String {
        val hands = buildHands(input = input, withJoker = true)
        val totalWins = calculateWins(hands)
        return totalWins.toString()
    }

    private fun calculateWins(hands: List<Hand>): Int {
        val grouped = hands
            .groupBy { it.type }
            .mapValues { entry -> entry.value.sortedBy { it.pointHands }.map { it.bid } }

        var totalWinnings = 0
        var ranking = 1
        for (handType in HandType.sortedByValue()) {
            val bids = grouped[handType] ?: emptyList()
            if (bids.isEmpty()) {
                continue
            }

            bids.forEach { bid ->
                totalWinnings += ranking * bid
                ranking++
            }
        }
        return totalWinnings
    }

    data class CardValue(val card: Card, val value: Int) {
        companion object {
            fun fromChar(char: Char, withJoker: Boolean) = if (withJoker) {
                fromCharWithJoker(char)
            } else {
                fromCharNoJoker(char)
            }

            private fun fromCharNoJoker(char: Char): CardValue {
                val card = Card.fromChar(char)
                val value = (Card.values().size - card.ordinal)
                return CardValue(Card.fromChar(char), value)
            }

            private fun fromCharWithJoker(char: Char): CardValue {
                val card = Card.fromChar(char)
                val value = if (card == Card.JACK) 0 else (Card.values().size - card.ordinal)
                return CardValue(Card.fromChar(char), value)
            }
        }
    }

    data class Hand(val cards: List<CardValue>, val bid: Int, val type: HandType) {
        val pointHands = cards.mapIndexed { index, card -> card.value * 14.0.pow(cards.size - index) }.sum()
    }

    enum class HandType(val value: Int) {
        FIVE_KING(6),
        FOUR_KING(5),
        FULL_HOUSE(4),
        THREE_KING(3),
        TWO_PAIRS(2),
        ONE_PAIR(1),
        HIGH_CARD(0);

        companion object {
            fun fromCards(cards: List<CardValue>, withJoker: Boolean) = if (withJoker) {
                fromCardsWithJoker(cards)
            } else {
                fromCardsNoJoker(cards)
            }

            private fun fromCardsWithJoker(cardValues: List<CardValue>): HandType {
                val groupedSize = cardValues.groupBy { it.value }.mapValues { it.value.size }.toMutableMap()
                if (groupedSize.getOrElse(0) { 0 } >= (cardValues.size - 1)) {
                    return FIVE_KING
                }
                var found = false
                val grouped = cardValues.fold(groupedSize) { acc, cardValue ->
                    if (cardValue.card == Card.JACK && !found) {
                        val cardWithMore = acc.keys.filter { it != 0 }
                            .reduce { card1, card2 -> if (acc[card2]!! > acc[card1]!!) card2 else card1 }
                        acc[cardWithMore] = acc[cardWithMore]!! + acc[0]!!
                        found = true
                    }
                    acc
                }
                grouped.remove(0)
                return getHandType(grouped)
            }

            private fun fromCardsNoJoker(cards: List<CardValue>): HandType {
                val grouped = cards.groupBy { it.value }.mapValues { it.value.size }
                return getHandType(grouped)
            }

            private fun getHandType(groupedCardCounter: Map<Int, Int>): HandType {
                val sorted = groupedCardCounter.values.sortedDescending()
                val firstCount = sorted.first()
                val secondCount = sorted.getOrElse(1) { 0 }
                return when {
                    firstCount == 5 -> FIVE_KING
                    firstCount == 4 -> FOUR_KING
                    firstCount == 3 && secondCount == 2 -> FULL_HOUSE
                    firstCount == 3 -> THREE_KING
                    firstCount == 2 && secondCount == 2 -> TWO_PAIRS
                    firstCount == 2 -> ONE_PAIR
                    else -> HIGH_CARD
                }
            }

            fun sortedByValue() = values().sortedBy { it.value }
        }
    }

    private fun buildHands(input: List<String>, withJoker: Boolean): List<Hand> {
        return input.map { line ->
            val split = line.split(" ")
            val cardValues =
                split.first().map { CardValue.fromChar(it, withJoker) }
            val bid = split.last().toInt()
            val type = HandType.fromCards(cardValues, withJoker)
            Hand(cardValues, bid, type)
        }
    }

    enum class Card {
        ACE,
        KING,
        QUEEN,
        JACK,
        TEN,
        NINE,
        EIGHT,
        SEVEN,
        SIX,
        FIVE,
        FOUR,
        THREE,
        TWO;

        companion object {
            fun fromChar(char: Char) = when (char) {
                'A' -> ACE
                'K' -> KING
                'Q' -> QUEEN
                'J' -> JACK
                'T' -> TEN
                '9' -> NINE
                '8' -> EIGHT
                '7' -> SEVEN
                '6' -> SIX
                '5' -> FIVE
                '4' -> FOUR
                '3' -> THREE
                '2' -> TWO
                else -> throw IllegalArgumentException("Invalid card: $char")
            }
        }
    }
}
