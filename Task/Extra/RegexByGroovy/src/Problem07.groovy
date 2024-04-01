class Problem07 {
    static void main(String[] args) {
        /**@
         *  Problem05 : Matching Anything but a new line
         *  dot(.) match anything (except for a new line)
         *  abc.def.ghi.jkx like this
         *  dot must and
         *  other character is optional,
         *
         */

        /*
        there is a special line comment, often called the shebang line understood by UNIX systems which allows scripts to be run directly from
         */

        //single-line comment


        def scanner = new Scanner(System.in)
        def regex = "\\S{3}\\.\\S{3}\\.\\S{3}\\.\\S{3}"
        def inputText = scanner.nextLine() =~ regex
        if (inputText.find()) {
            println(true)
        } else {
            println(false)
        }


    }
}
