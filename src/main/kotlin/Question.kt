class Question(private val words: List<String>) {
    private val questionType: QuestionType
    private val crypticRoman: List<String>

    init {
        require(isQuestion(words))
        questionType = when {
            QuestionType.MetalCost.matches(words) -> QuestionType.MetalCost
            QuestionType.NumberValue.matches(words) -> QuestionType.NumberValue
            else -> QuestionType.Blah
        }
        crypticRoman = words
            .drop(questionType.whWordsCount)
            .take(words.count() - questionType.nonNumberWords)
    }

    companion object {
        fun isQuestion(words: List<String>): Boolean {
            return words.last() == "?"
        }
    }

    fun getAnswer(costLedger: CostLedger, language: Language): String {
        val romanValue = getNumber(language)

        return when (questionType) {
            QuestionType.NumberValue -> "${crypticRoman.joinToString(" ")} is $romanValue"
            QuestionType.MetalCost -> {
                val metal = words[words.count() - 2]
                val totalValue: Double = romanValue * (costLedger.get(metal))
                "${crypticRoman.joinToString(" ")} $metal is ${totalValue.toInt()} Credits"
            }
            QuestionType.Blah -> "I have no idea what you are talking about"
        }
    }

    private fun getNumber(language: Language): Int {
        return if (questionType == QuestionType.Blah) 0 else {
            val romanString = language.toEnglish(crypticRoman)
            RomanNumber(romanString)
                .toInt()
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