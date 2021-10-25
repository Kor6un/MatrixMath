import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class MatrixMath {
    static Scanner scanner = new Scanner(System.in);
    static String pathMatrixA = "src\\matrix_a.txt";
    static String pathMatrixB = "src\\matrix_b.txt";
    static String outputPath = "src\\result.txt";

    public static void main(String[] args) {


        getResult();

     /*   int[][] a = getArrayFromFile(pathMatrixA);
        System.out.println("Исходная матрица a:");
        display(a);

        int[][] b = getArrayFromFile(pathMatrixB);
        System.out.println("Исходная матрица b:");
        display(b);

        int[][] result = getResult(a, b);

        display(result);
        writeMatrixToFile(result, outputPath);
*/

    }

    public static int choiceMenu() {
        final int MAX = 2;
        final int MIN = 1;
        boolean isCorrect;
        int choice = 0;
        do {
            System.out.print("Введите 1, если вводить/выводить в консоли и 2, если в файле: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
                isCorrect = true;
            } catch (Exception e) {
                System.out.println("Ошибка ввода. Повторите ввод.");
                isCorrect = false;
            }

            if ((isCorrect) && (choice > MAX || choice < MIN)) {
                System.out.println("Число выходит за допустимые пределы. Повторите ввод.");
                isCorrect = false;
            }
        } while (!isCorrect);
        return choice;
    }

    public static void getResult() {
        int choiceMenu = choiceMenu();
        int size;
        int[][] a;
        int[][] b;
       // int[][] result;
        if (choiceMenu == 1) {
            size = getSizeFromCons();
            System.out.println("Введите элементы матрицы a:");
            a = getMatrixFromCons(size);
            System.out.println("Введите элементы матрицы b:");
            b = getMatrixFromCons(size);
        } else {
            String pathMatrixA = getPath();
            String pathMatrixB = getPath();
            a = getMatrixFromFile(pathMatrixA);
            b = getMatrixFromFile(pathMatrixB);
        }

        if (choiceOperation() == 2) {
            System.out.println("Результат вычитания:");
            display(substract(a, b));
        } else {
            System.out.println("Результат сложения:");
            display(add(b, a));
        }
        //return result;
    }

    private static String getPath() {
        boolean isIncorrect;
        String path;
        do {
            System.out.print("Введите ссылку на файл: ");
            isIncorrect = false;
            path = scanner.nextLine();
            File file = new File(path);
            if (!file.exists()) {
                System.out.println("Файл не найден! Введите правильную ссылку");
                isIncorrect = true;
            }
        } while (isIncorrect);
        return path;
    }

    public static void writeMatrixToFile(int[][] matrix, String path) {
        try(FileWriter writer = new FileWriter(path, false))
        {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    writer.write(String.valueOf(matrix[i][j]));
                    writer.write('\t');
                }
                writer.write('\n');
            }
        }
        catch(IOException ex){
            System.out.println("Не удалось записать в файл!" + ex.getMessage());
        }
        System.out.println("ФАЙЛ УСПЕШНО ЗАПИСАН!");
    }

    public static int[][] getMatrixFromCons(int size){
        int[][] result = new int[size][size];
        int elements = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                do {
                    System.out.format("Введите элемент %d-ой строки %d-ого столбца: ", i + 1, j + 1);
                    try {
                        result[i][j] = Integer.parseInt(scanner.nextLine());
                        elements++;
                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка ввода. Повторите ввод.");
                    }

                } while (size*size == elements-1);
            }
        }
        System.out.println();

        return result;
    }

    private static int getSizeFromCons() {
        int size = 0;
        final int MAX = 4;
        final int MIN = 1;
        do {
            System.out.print("Введите размерность матрицы NxN (от 1 до 4): ");
            try {
                size = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Ошибка ввода. Повторите ввод.");
            }
            if (size > MAX || size < MIN) {
                System.out.println("Число выходит за допустимые пределы. Повторите ввод.");
            }
        } while (size < MIN || size > MAX);
        return size;
    }

    public static int[][] getMatrixFromFile(String path)  {
        int[][] result = null;
        File file = new File(path);
        try {
            Scanner sizeMatrix = new Scanner(file);
            String[] temp = sizeMatrix.nextLine().split(" ");
            sizeMatrix.close();
            int n = temp.length;

            Scanner scanner = new Scanner(file);
            result = new int[n][n];
            for (int i = 0; i < n; i++) {
                String[] numbers = scanner.nextLine().split(" ");
                for (int j = 0; j < n; j++) {
                    result[i][j] = Integer.parseInt(numbers[j]);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден!");
            e.printStackTrace();
        }
        return result;
    }

    public static int choiceOperation() {
        Scanner scanner = new Scanner(System.in);
        final int MAX = 2;
        final int MIN = 1;
        int choice = 0;
        do {
            System.out.print("Выбирите 1, если нужно сложить матрицы и 2 , если нужно вычесть: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Ошибка ввода. Повторите ввод.");
            }
            if (choice > MAX || choice < MIN) {
                System.out.println("Число выходит за допустимые пределы. Повторите ввод.");
            }
        } while (choice < MIN || choice > MAX);
        scanner.close();
        return choice;
    }

    public static int[][] add(int[][] a, int[][] b){
        int[][] result = new int[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                result[i][j] = a[i][j] + b[i][j];
            }
        }
        return result;
    }

    public static int[][] substract(int[][] a, int[][] b){
        int[][] result = new int[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                result[i][j] = a[i][j] - b[i][j];
            }
        }
        return result;
    }

    public static void display(int[][] matrix) {
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                System.out.print("\t" + matrix[i][j]);
            }
            System.out.println();
        }
    }
}
