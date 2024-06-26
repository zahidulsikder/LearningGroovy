class Problem_09 {
    static void main(String[] args) {
        /**
         * problem : 09
         * Excluding Specific Characters
         * You have a test string S.
         Your task is to write a regex that will match S with  the following conditions:

         S must be of length 6.
         First character should not be a digit (  1,2,3,4,5,6,7,8,9 or 0 ).
         Second character should not be a lowercase vowel ( a,e,i,o or u ).
         Third character should not be b, c, D or F.
         Fourth character should not be a whitespace character ( \r, \n, \t, \f or <space> ).
         Fifth character should not be a uppercase vowel ( A,E,I,O,U   ).
         Sixth character should not be a . or , symbol.
         */

        def scanner = new Scanner(System.in)
        def regex = "^[^0-9][^aeiou][^bcdf][\\\\S][^AEIOU][^.,]\$"
        def inputText = scanner.nextLine() =~regex

        if (inputText.find()){
            println (true)
        }else {
            println(false)
        }

    }
}
