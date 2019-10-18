
object Merchant {
    private val language = Language()
    private val costLedger = CostLedger()

    private fun List<String>.isMapping(): Boolean = Language.isMapping(this)
    private fun List<String>.isCostLedger(): Boolean = CostLedger.isInfo(this)
    private fun List<String>.isQuestion(): Boolean = Question.isQuestion(this)

    fun feed(input: String): String {
        val lines = input.lines()
        val result = arrayListOf<String>()
        for (line in lines) {
            val words = line.split(" ")
            when {
                words.isMapping() -> language.enrich(words)
                words.isCostLedger() -> costLedger.add(words, language)
                words.isQuestion() -> {
                    val question = Question(words, language)
                    result.add(question.getAnswer(costLedger))
                }
            }
        }
        return result.joinToString("\n")
    }

}
