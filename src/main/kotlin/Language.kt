class Language {
    private var romanMap: HashMap<String, String> = hashMapOf()

    companion object {
        fun isMapping(words: List<String>): Boolean {
            return words.count() == 3 && words[1] == "is"
        }
    }

    fun enrich(words: List<String>) {
        require(words.count() == 3)
        require(RomanNumber.isValid(words[2]))
        require(words[1] == "is")

        val galacticPhrase = words[0]
        val romanString = words[2]
        romanMap[galacticPhrase] = romanString//convert string to char
    }

    fun toEnglish(galactic: List<String>): String {
        return galactic
            .map { romanMap[it] }
            .joinToString("")
    }
}