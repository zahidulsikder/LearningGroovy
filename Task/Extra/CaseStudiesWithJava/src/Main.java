import java.util.*;

public class Main {
    public static void main(String[] args) throws ValidationException {


        String name ;
        double mark;

        String regex = "^[a-zA-z]+";
        String dicRegex = "^[+-]?\\\\d+(\\\\.\\\\d+)?$";



        System.out.println("How many students add to your database:");

        Scanner input = new Scanner(System.in);
        int databaseSize = Integer.parseInt(input.nextLine());

        HashMap<String, Double> hashMap = new HashMap<>();
        for (int i = 0; i < databaseSize; i++) {

            System.out.println("Enter Student name: ");
            name = input.nextLine();
            if (!name.matches(regex)) {
                throw new ValidationException("Input a valid name.");
            }
            System.out.println("Enter mark: ");
            String marks = input.nextLine();
            if (!marks.matches(dicRegex)) {
                mark = Double.parseDouble(marks);

            } else {
                throw new ValidationException("Input valid decimal marks.");
            }

            hashMap.put(name, mark);
        }

        List<Map.Entry<String, Double>> hashList = new ArrayList<>(hashMap.entrySet());
        System.out.println("Unsorted result: " + hashList);
        Collections.sort(hashList, (hashList1, hashList2) -> hashList2.getValue().compareTo(hashList1.getValue())); // compare two value using lambda expression
        System.out.println("Sort result: " + hashList); // sorted desc


        LinkedHashMap<String, Double> hashMapSort = new LinkedHashMap<>();  // after sort as descending value it will store insertion order so that we can get first, second and third position from sorted place

        for (Map.Entry<String, Double> entry : hashList) {
            hashMapSort.put(entry.getKey(), entry.getValue());
        }

        while (true) {
            displayMenu();
            int value = input.nextInt();
            switch (value) {
                case 1: {
                    System.out.println("1. Enter the student name: ");
                    String searchName = input.next();

                    String detailsAndPosition = findDetailsAndPositionByName(hashList, searchName);

                    if (!detailsAndPosition.equals("")) {
                        System.out.println(detailsAndPosition);
                    } else {
                        System.out.println("No student found with the entered name");
                    }
                    break;
                }
                case 2: {
                    System.out.print("2. Enter the position: ");
                    int positions = input.nextInt();
                    List<String> nameAndMarks = findNameAndMarksByPosition(hashList, positions);
                    if (!nameAndMarks.isEmpty()) {
                        for (String nameAndMark : nameAndMarks) {
                            System.out.println(nameAndMark);
                        }
                    }
                    break;
                }

                case 3: {
                    System.out.println("3. Enter the mark: ");
                    double searchMark = input.nextDouble();
                    List<String> nameAndPositions = findNameAndPositionByMarks(hashList, searchMark);
                    if (!nameAndPositions.isEmpty()) {

                        for (String result : nameAndPositions) {
                            System.out.println(result);
                        }
                    } else {
                        System.out.println("No one got this marks");
                    }
                    break;
                }
                case 4: {
                    System.out.println("Searching by name and marks \n4. Enter the student name: ");
                    String searchName2 = input.next();
                    System.out.println("Enter the marks: ");
                    double searchMark2 = input.nextDouble();

                    String detailsAndPositionByName = findDetailsAndPositionByNameAndMark(hashList, searchName2, searchMark2);

                    if (!detailsAndPositionByName.equals("")) {
                        System.out.println(detailsAndPositionByName);
                    } else {
                        System.out.println("No student found with the entered name and marks");
                    }
                    break;
                }

                case 0: {
                    System.out.println("You are existed.");
                    return;
                }
                default: {
                    System.out.println("Wrong Input.");
                }
            }

        }
    }


    private static void displayMenu() {
        System.out.println("................................\n" +
                "1. To search by Name press: 1, \n" +
                "2. To search by Position press: 2, \n" +
                "3. To search by Marks press: 3, \n" +
                "4. To search by name and marks press: 4, \n" +
                "5. To close press: 0\n");
    }


    private static String findDetailsAndPositionByName(List<Map.Entry<String, Double>> sortedList, String searchName) {


        try {
            if (sortedList == null || searchName == null) {
                throw new IllegalArgumentException("Input parameters cannot be null");
            }

            String regex = "^[a-zA-Z ]+";
            boolean matchName = searchName.matches(regex);

            if (matchName) {
                for (int i = 0; i < sortedList.size(); i++) {
                    Map.Entry<String, Double> entry = sortedList.get(i);
                    if (entry.getKey().equals(searchName)) {
                        double marks = entry.getValue();
                        int position = i + 1;
                        return "Student " + searchName + " has marks " + marks + " and is at position " + position;
                    }
                }
                return "Student " + searchName + " not found in the list";
            } else {
                throw new ValidationException("Invalid name. Only alphabetical characters and spaces are allowed.");
            }
        } catch (ValidationException e) {
            return e.getMessage();
        } catch (Exception e) {
            System.out.println(e);
            return "An unexpected error occurred";
        }
    }



    private static List<String> findNameAndPositionByMarks(List<Map.Entry<String, Double>> sortedList, double searchMark) {

        try {
            List<String> nameAndPositions = new ArrayList<>();

            if (sortedList == null) {
                throw new IllegalArgumentException("Sorted list cannot be null");
            }

            String regex = "^[+-]?\\d+(\\.\\d+)?$";
            boolean matchMark = String.valueOf(searchMark).matches(regex);

            if (matchMark) {
                for (int i = 0; i < sortedList.size(); i++) {
                    if (sortedList.get(i).getValue().equals(searchMark)) {
                        String name = sortedList.get(i).getKey();
                        int position = i + 1;
                        nameAndPositions.add(position + ". Student name = " + name + " and is at position " + position);
                    }
                }

                if (nameAndPositions.isEmpty()) {
                    return Collections.singletonList("Not found by this mark.");
                }
            } else {
                return Collections.singletonList("Input valid marks.");
            }
            return nameAndPositions;

        } catch (Exception e) {
            System.out.println(e);
            return Collections.singletonList("An unexpected error occurred");
        }

    }


    private static List<String> findNameAndMarksByPosition(List<Map.Entry<String, Double>> sortedList, int searchPosition) {
        try {
            List<String> nameAndMarks = new ArrayList<>();
            if (searchPosition > 0 && searchPosition <= sortedList.size()) {
                String name = sortedList.get(searchPosition - 1).getKey();
                double marks = sortedList.get(searchPosition - 1).getValue();
                nameAndMarks.add("Student name is : " + name + " and marks is : " + marks);
            }else {
                nameAndMarks.add("Invalid position.");
            }
            return nameAndMarks;
        }catch (Exception e){
            return Collections.singletonList("Position is not valid.");
        }
    }


    private static String findDetailsAndPositionByNameAndMark(List<Map.Entry<String, Double>> sortedList, String searchName, double searchMark) {

        try {
            for (int i = 0; i < sortedList.size(); i++) {
                Map.Entry<String, Double> entry = sortedList.get(i);
                if (entry.getKey().equals(searchName) && entry.getValue().equals(searchMark)) {
                    int position = i + 1;
                    return "Student " + searchName + " with marks " + searchMark + " is at position " + position;
                }
            }
            return "";
        }catch (Exception e){
           return e.getMessage();
        }
    }
}

