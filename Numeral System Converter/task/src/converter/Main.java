package converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isError = true;

        String radNumStr = "0";

        if (scanner.hasNextInt()) {
            radNumStr = scanner.next();
        }

        String numInString = scanner.next();

        String radTarg = "0";

        if (scanner.hasNextInt()) {
            radTarg = scanner.next();
        }

        if (!radNumStr.matches("^[0-9]*$")) {
            isError = false;
        } else if (Integer.parseInt(radNumStr) < 1 || Integer.parseInt(radNumStr) > 36) {
            isError = false;
        } else if (Integer.parseInt(radTarg) < 1 || Integer.parseInt(radTarg) > 36) {
            isError = false;
        }

        if (isError) {
            int radixOfNum = Integer.parseInt(radNumStr);

            int radixTarget = Integer.parseInt(radTarg);

            boolean isFrac = numInString.contains(".");

            long num = 0;

            if (!isFrac) {
                System.out.println(Converter(num, radixOfNum, numInString, radixTarget));
            } else {
                String[] newNumInString = numInString.split("\\.");
                String intNum = Converter(num, radixOfNum, newNumInString[0], radixTarget);
                String fractNum = newNumInString[1];

                double[] charArr = new double[fractNum.length()];

                for (int i = 0; i < charArr.length; i++) {
                    if (fractNum.charAt(i) < 97) {
                        charArr[i] = (fractNum.charAt(i) - 48) / (Math.pow(radixOfNum, i + 1));
                    } else if (fractNum.charAt(i) > 96) {
                        charArr[i] = (fractNum.charAt(i) - 87) / (Math.pow(radixOfNum, i + 1));
                    }
                }

                double decimalValue = Arrays.stream(charArr).sum();

                List<String> list = new ArrayList<>();

                for (int i = 0; i < 5; i++) {
                    int temp = (int) (radixTarget * decimalValue);
                    list.add(String.valueOf(temp));
                    decimalValue = radixTarget * decimalValue - temp;
                    temp = (int) (radixTarget * decimalValue);
                }

                StringBuilder fractNums = new StringBuilder();
                for (String ele:list) {
                    if (Integer.parseInt(ele) > 9) {
                        fractNums.append(((char) (Integer.parseInt(ele) + 87)));
                    } else {
                        fractNums.append(ele);
                    }
                }
                System.out.println(intNum + "." + fractNums);
            }
        } else {
            System.out.println("error");
        }



    }

    public static String Converter(long num, int radixOfNum, String numInString, int radixTarget) {
        String result;
        if (radixOfNum == 1) {
            num = numInString.length();
        } else {
            num = Long.parseLong(numInString, radixOfNum);
        }
        if (radixTarget == 1) {
            result = "1".repeat((int) num);
        } else {
            result = Long.toString(num, radixTarget);
        }
        return result;
    }
}