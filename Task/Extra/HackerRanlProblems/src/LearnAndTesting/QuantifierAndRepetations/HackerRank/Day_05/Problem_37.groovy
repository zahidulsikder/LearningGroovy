/**
 * lat long validation
 */

def scanner = new Scanner(System.in)
def n = scanner.nextInt()
scanner.nextLine()

def regex ='^\\([+-]?(\\d\\.\\d+|\\d|90\\.0+|90|[0-8]\\d\\.\\d+|[0-8]\\d),\\s[+-]?(180\\.0+|1[0-7]\\d\\.\\d+|1[0-7]\\d|180|\\d\\.\\d*|\\d{2}\\.\\d+|\\d{2}|\\d)\\)$'
(0..<n).each {
    def text = scanner.nextLine()
    def matcher = text =~ regex
    if (matcher.find()) {
       println("Valid")
    }else {
        println("Invalid")
    }
}