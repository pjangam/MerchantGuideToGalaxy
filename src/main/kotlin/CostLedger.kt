class CostLedger {
    private val metalCost = hashMapOf<String, Double>()

    companion object {
        fun isInfo(words: List<String>): Boolean {
            return !Language.isMapping(words) && !Question.isQuestion(words)
        }
    }

    fun add(words: List<String>, language: Language) {
        val metalIndex = words.count() - 4
        val totalCostIndex = words.count() - 2

        val metalCountInCrypticRoman = words.take(metalIndex)
        val romanString = language.toEnglish(metalCountInCrypticRoman)
        val metalCount = RomanNumber(romanString)
            .toInt()
            .toDouble()

        val metal: String = words[metalIndex]
        val totalPrice: Double = (words[totalCostIndex])
            .toDouble()


        val price = totalPrice / metalCount
        metalCost[metal] = price
    }

    fun get(metal: String): Double = metalCost[metal]!!
}