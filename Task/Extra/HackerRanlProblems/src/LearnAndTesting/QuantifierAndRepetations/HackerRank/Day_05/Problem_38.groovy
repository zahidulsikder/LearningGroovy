/**
 * Find HackerRank
 */


def scanner = new Scanner(System.in)
def n = scanner.nextInt()
scanner.nextLine()
def regex = "hackerrank"

(0..<n).each {
    def line = scanner.nextLine().trim().toLowerCase()
    if (line.startsWith(regex) && line.endsWith(regex)) {
        println(0)
    } else if (line.startsWith(regex)) {
        println(1)
    } else if (line.endsWith(regex)) {
        println(2)
    } else {
        println(-1)
    }
}

scanner.close()
