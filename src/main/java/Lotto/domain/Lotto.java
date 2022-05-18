package Lotto.domain;

import Lotto.exception.SameNumberException;
import Lotto.exception.WrongNumberFormatException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Lotto {
    private static final String DELIMITER = ", ";
    private static final int WINNING_NUMBER_SIZE = 6;

    protected List<Number> number = new ArrayList<>();

    public Lotto(List<Integer> numbers) {
        for (int number : numbers) {
            this.number.add(Number.getNumber(number));
        }
    }

    public Lotto(String winningNumbers) {
        String[] winningNumberArr = winningNumbers.split(DELIMITER);

        validate(winningNumberArr);

        for (String number : winningNumberArr) {
            this.number.add(Number.getNumber(Integer.parseInt(number)));
        }
    }

    private void validate(String[] winningNumberArr) {
        validateSameLottoNumber(winningNumberArr);

        validateWrongFormat(winningNumberArr);
    }

    private void validateWrongFormat(String[] winningNumberArr) {
        if (winningNumberArr.length != WINNING_NUMBER_SIZE) {
            throw new WrongNumberFormatException("입력한 당첨번호의 형식을 확인하세요.");
        }
    }

    private void validateSameLottoNumber(String[] winningNumberArr) {
        ArrayList<String> tmpList = new ArrayList<>(Arrays.asList(winningNumberArr));
        if (tmpList.size() != sizeOfDuplicatesRemoved(tmpList)) {
            throw new SameNumberException("중복된 로또 번호가 있습니다.");
        }
    }

    private long sizeOfDuplicatesRemoved(ArrayList<String> tmpList) {
        return tmpList.stream().distinct().count();
    }
}
