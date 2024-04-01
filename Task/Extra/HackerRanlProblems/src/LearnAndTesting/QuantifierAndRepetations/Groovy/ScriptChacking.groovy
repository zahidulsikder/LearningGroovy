
Scanner sc = new Scanner(System.in)

while (1) {
    def com = sc.nextLine();
    def str = sc.nextLine();
    def ans = str.replaceAll(/[^|][^\s]*[PR:]\s/, ",")
    //  println ans;
    def fina = ans.replaceAll(/\|,/, ";")

    //println fina
    def fina2 = fina.replaceAll(/^,/, "")

    println "\"${com}\"" + ":\"" + fina2 + "\","
}