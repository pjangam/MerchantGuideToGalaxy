class Question(words: List<String>, private val language: Language) {
    private val questionType: QuestionType
    private val metal: String
    private val crypticRoman: List<String>
    private val romanNumber: RomanNumber

    init {
        require(isQuestion(words))
        questionType = when {
            QuestionType.MetalCost.matches(words) -> QuestionType.MetalCost
            QuestionType.NumberValue.matches(words) -> QuestionType.NumberValue
            else -> QuestionType.Blah
        }
        metal = if (questionType == QuestionType.MetalCost) words[words.count() - 2] else ""
        crypticRoman = words
            .drop(questionType.whWordsCount)
            .take(words.count() - questionType.nonNumberWords)
        romanNumber = if (questionType != QuestionType.Blah) RomanNumber(getRomanNumber()) else RomanNumber()
    }

    companion object {
        fun isQuestion(line: String): Boolean {
            return line.trim().endsWith('?')
        }

        fun isQuestion(words: List<String>): Boolean {
            return words.last() == "?"
        }
    }

    private fun getRomanNumber(): String = language.toEnglish(crypticRoman)
    private fun metalCost(costLedger: CostLedger): Double = if (metal.isEmpty()) 1.0 else (costLedger.get(metal))

    fun getAnswer(costLedger: CostLedger): String {

        val totalValue: Double = romanNumber.toInt() * (metalCost(costLedger))
        return when (questionType) {
            QuestionType.NumberValue -> "${crypticRoman.joinToString(" ")} is ${totalValue.toInt()}"
            QuestionType.MetalCost -> "${crypticRoman.joinToString(" ")} $metal is ${totalValue.toInt()} Credits"
            QuestionType.Blah -> "I have no idea what you are talking about"
        }
    }
}

enum class QuestionType(val nonNumberWords: Int, private val beginWords: List<String>) {
    Blah(0, emptyList()),
    MetalCost(6, listOf("how", "many", "Credits", "is")), //wh =4 , ?=1, metal=1
    NumberValue(4, listOf("how", "much", "is")); //wh=3, ?=1

    val whWordsCount: Int = beginWords.count()
    fun matches(words: List<String>): Boolean = words.take(whWordsCount) == beginWords
}