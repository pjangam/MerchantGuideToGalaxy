class RomanNumber(private val roman: List<Char> = emptyList()) {
    companion object {
        var romanNotation = hashMapOf('I' to 1, 'V' to 5, 'X' to 10, 'L' to 50, 'C' to 100, 'D' to 500, 'M' to 1000)

        fun isValid(x: String): Boolean {
            return x.length == 1 && romanNotation.containsKey(x.first())
        }
    }

    constructor(data: String) : this(data.toCharArray().toList())

    fun toInt(): Int {
        var res = 0
        var i = 0
        while (i < roman.count()) {
            // Getting value of symbol s[i]
            val s1 = romanNotation[roman[i]]!!

            // Getting value of symbol s[i+1]
            if (i + 1 < roman.count()) {
                val s2 = romanNotation[roman[i + 1]]!!

                if (s1 >= s2) res += s1 else {
                    res = res + s2 - s1
                    i++
                }
            } else {
                res += s1
                i++
            }
            i++
        }
        return res
    }
}