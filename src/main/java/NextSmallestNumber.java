import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Find the next smallest number that can be created from swapping digits in an existing number.
 * Return -1 if next smallest number cannot be found.
 */
class NextSmallestNumber {

    private static final long FAILED_TO_FIND = -1L;

    static long nextSmallest(long number) {
        return number > 10 ? nextSmallest(digitsInReverse(number)) : FAILED_TO_FIND;
    }

    private static long nextSmallest(List<Integer> reverseDigits) {
        List<Integer> seenSoFar = new ArrayList<>();
        int prev = reverseDigits.get(0);
        seenSoFar.add(prev);
        int index = 1;
        Optional<Integer> replaceWith = Optional.empty();
        while (index < reverseDigits.size()) {
            int current = reverseDigits.get(index);
            if (current > prev) {
                replaceWith = maxSmallerThan(seenSoFar, current);
                if (!replaceWith.isPresent() || (replaceWith.get() == 0 && index == reverseDigits.size() - 1)) {
                    return FAILED_TO_FIND;
                }
                seenSoFar.remove(replaceWith.get());
                seenSoFar.add(current);
                break;
            }
            seenSoFar.add(current);
            prev = current;
            index++;
        }
        if (index == reverseDigits.size()) return FAILED_TO_FIND;
        return combineDigits(reverseDigits, (reverseDigits.size() - 1 - index), replaceWith.get(), seenSoFar);
    }

    private static long combineDigits(List<Integer> reverseDigits, int insertPos, int insertValue, List<Integer> remaining) {
        List<Integer> newDigits = new ArrayList<>();
        int index = 0;
        int reverseIndex = reverseDigits.size() - 1;
        while (index < insertPos) {
            newDigits.add(reverseDigits.get(reverseIndex));
            index++;
            reverseIndex--;
        }
        newDigits.add(insertValue);
        remaining.sort(Comparator.reverseOrder());
        newDigits.addAll(remaining);
        return digitsToNumber(newDigits);
    }

    private static long digitsToNumber(List<Integer> digits) {
        String value = digits.stream()
                .map(digit -> Character.forDigit(digit, 10))
                .map(String::valueOf)
                .collect(Collectors.joining());
        return Long.valueOf(value);
    }

    private static Optional<Integer> maxSmallerThan(List<Integer> numbers, int target) {
        return numbers.stream().filter(num -> num < target).max(Comparator.naturalOrder());
    }

    private static List<Integer> digitsInReverse(long number) {
        List<Integer> digits = new ArrayList<>();
        long current = number;
        while (current > 0) {
            digits.add((int) (current % 10));
            current /= 10;
        }
        return digits;
    }
}