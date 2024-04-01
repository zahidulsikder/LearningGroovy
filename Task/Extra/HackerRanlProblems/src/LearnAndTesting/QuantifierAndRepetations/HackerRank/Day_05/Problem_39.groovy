import java.util.Scanner
import java.util.regex.Pattern

def scanner = new Scanner(System.in)
def n = scanner.nextInt()
scanner.nextLine() // Consume newline

// Regex pattern for valid languages
def regex = /(C|CPP|JAVA|PYTHON|PERL|PHP|RUBY|CSHARP|HASKELL|CLOJURE|BASH|SCALA|ERLANG|CLISP|LUA|BRAINFUCK|JAVASCRIPT|GO|D|OCAML|R|PASCAL|SBCL|DART|GROOVY|OBJECTIVEC)/

for (int i = 0; i < n; i++) {
    def language = scanner.next()

    def matcher = language =~ regex
    if (matcher.matches()) {
        println("VALID")
    } else {
        println("INVALID")
    }
}

scanner.close()
