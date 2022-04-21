package Calculator;

import Calculator.exception.WrongPlaceNumberOrOperatorException;
import Calculator.exception.OnlyNumberException;
import Calculator.exception.OnlyOperatorException;

public class ExpressionGenerator {
    private static final String SPECIAL_DELIMITER = "[+\\-*/]";
    private static final String NUMBER_DELIMITER = "\\d*(\\.\\d+)?";

    public static boolean isOnlyNumberExp = true;
    public static boolean isOnlyOperatorExp = true;

    public static int generate(String exp) {
        return compute(validateAndSplitExpression(exp));
    }

    public static String[] validateAndSplitExpression(String exp) {
        validateExpression(exp);

        String[] splitExp = splitExpression(exp);

        checkOnlyNumberExp(splitExp);

        checkOnlyOperator(splitExp);

        checkPossibleExpression(splitExp);

        return splitExp;
    }

    private static void validateExpression(String exp) {
        if (isInvalidExp(exp))
            throw new IllegalArgumentException();
    }

    private static boolean isInvalidExp(String exp) {
        return exp == null || exp == "";
    }

    private static String[] splitExpression(String exp) {
        return exp.split(" ");
    }

    private static void checkOnlyNumberExp(String[] splitExp) {
        for (String exp : splitExp) {
            // 숫자가 아닌 문자열이 존재하는지 체크
            checkNumber(exp);
        }

        if (isOnlyNumberExp == true)
            throw new OnlyNumberException("수식에 피연산자만 존재합니다.");
    }

    private static void checkNumber(String str) {
        if (str.matches(NUMBER_DELIMITER) == false)
            isOnlyNumberExp = false;
    }

    private static void checkOnlyOperator(String[] splitExp) {
        for (String exp : splitExp) {
            // 연산자가 아닌 문자열이 존재하는지 체크
            checkOperatorExp(exp);
        }

        if (isOnlyOperatorExp == true)
            throw new OnlyOperatorException("수식에 연산자만 존재합니다.");
    }

    private static void checkOperatorExp(String str) {
        if (str.matches(SPECIAL_DELIMITER) == false)
            isOnlyOperatorExp = false;
    }

    private static void checkPossibleExpression(String[] splitExp) {
        splitExpLengthIsOdd(splitExp);

        // 홀수자리는 무조건 숫자, 짝수자리는 무조건 연산자가 나와야함.
        for (int i = 0; i < splitExp.length; ++i) {
            if (i % 2 == 0) {
                if (splitExp[i].matches(SPECIAL_DELIMITER))
                    throw new WrongPlaceNumberOrOperatorException("피연산자 자리에 연산자가 존재합니다.");
            } else {
                if (splitExp[i].matches(NUMBER_DELIMITER))
                    throw new WrongPlaceNumberOrOperatorException("연산자 자리에 피연산자가 존재합니다.");
            }
        }
    }

    private static void splitExpLengthIsOdd(String[] splitExp) {
        if (splitExp.length % 2 == 0)
            throw new WrongPlaceNumberOrOperatorException();
    }

    public static int compute(String[] arr) {
        int i = 0;
        String sum = arr[0];

        while (i + 2 < arr.length) {
            String op1 = sum;
            String operator = arr[i + 1];
            String op2 = arr[i + 2];
            Operator c = Operator.findOperator(operator);
            sum = Integer.toString(c.calc(op1, op2));
            i += 2;
        }

        return Integer.parseInt(sum);
    }
}
