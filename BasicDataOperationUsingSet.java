import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Клас BasicDataOperationUsingSet реалізує операції з множиною HashSet для LocalTime.
 * 
 * <p>Методи класу:</p>
 * <ul>
 *   <li>{@link #executeDataAnalysis()} - Запускає аналіз даних.</li>
 *   <li>{@link #performArraySorting()} - Упорядковує масив LocalTime.</li>
 *   <li>{@link #findInArray()} - Пошук значення в масиві LocalTime.</li>
 *   <li>{@link #locateMinMaxInArray()} - Знаходить граничні значення в масиві.</li>
 *   <li>{@link #findInSet()} - Пошук значення в множині LocalTime.</li>
 *   <li>{@link #locateMinMaxInSet()} - Знаходить мінімальне і максимальне значення в множині.</li>
 *   <li>{@link #analyzeArrayAndSet()} - Аналізує елементи масиву та множини.</li>
 * </ul>
 */
public class BasicDataOperationUsingSet {
    LocalTime localTimeValueToSearch;
    LocalTime[] localTimeArray;
    Set<LocalTime> localTimeSet = new HashSet<>();

    /**
     * Конструктор, який iнiцiалiзує об'єкт з готовими даними.
     * 
    * @param localTimeValueToSearch Значення для пошуку
    * @param localTimeArray Масив LocalTime
     */
    BasicDataOperationUsingSet(LocalTime localTimeValueToSearch, LocalTime[] localTimeArray) {
        this.localTimeValueToSearch = localTimeValueToSearch;
        this.localTimeArray = localTimeArray;
        this.localTimeSet = new HashSet<>(Arrays.asList(localTimeArray));
    }
    
    /**
     * Запускає комплексний аналіз даних з використанням множини HashSet.
     * 
    * Метод завантажує дані, виконує операції з множиною та масивом LocalTime.
     */
    public void executeDataAnalysis() {
        // спочатку аналізуємо множину часу
        findInSet();
        locateMinMaxInSet();
        analyzeArrayAndSet();

        // потім обробляємо масив
        findInArray();
        locateMinMaxInArray();

        performArraySorting();

        findInArray();
        locateMinMaxInArray();

        // зберігаємо відсортований масив до файлу
        DataFileHandler.writeArrayToFile(localTimeArray, BasicDataOperation.PATH_TO_DATA_FILE + ".sorted");
    }

    /**
    * Упорядковує масив об'єктів LocalTime за зростанням.
     * Фіксує та виводить тривалість операції сортування в наносекундах.
     */
    private void performArraySorting() {
        long timeStart = System.nanoTime();

        Arrays.sort(localTimeArray);

        PerformanceTracker.displayOperationTime(timeStart, "упорядкування масиву дати i часу");
    }

    /**
     * Здійснює пошук конкретного значення в масиві дати та часу.
     */
    private void findInArray() {
        long timeStart = System.nanoTime();

        int position = Arrays.binarySearch(this.localTimeArray, localTimeValueToSearch);

        PerformanceTracker.displayOperationTime(timeStart, "пошук елемента в масивi типу LocalTime");

        if (position >= 0) {
            System.out.println("Елемент '" + localTimeValueToSearch + "' знайдено в масивi за позицією: " + position);
        } else {
                System.out.println("Елемент '" + localTimeValueToSearch + "' відсутній в масиві.");
        }
    }

    /**
    * Визначає найменше та найбільше значення в масиві LocalTime.
     */
    private void locateMinMaxInArray() {
        if (localTimeArray == null || localTimeArray.length == 0) {
            System.out.println("Масив є пустим або не ініціалізованим.");
            return;
        }

        long timeStart = System.nanoTime();

        LocalTime minValue = localTimeArray[0];
        LocalTime maxValue = localTimeArray[0];

        for (LocalTime currentTime : localTimeArray) {
            if (currentTime.isBefore(minValue)) {
                minValue = currentTime;
            }
            if (currentTime.isAfter(maxValue)) {
                maxValue = currentTime;
            }
        }

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмального i максимального значення в масивi типу LocalTime");

        System.out.println("Найменше значення в масивi: " + minValue);
        System.out.println("Найбільше значення в масивi: " + maxValue);
    }

    /**
     * Здійснює пошук конкретного значення в множині дати та часу.
     */
    private void findInSet() {
        long timeStart = System.nanoTime();

        boolean elementExists = this.localTimeSet.contains(localTimeValueToSearch);

        PerformanceTracker.displayOperationTime(timeStart, "пошук елемента в HashSet типу LocalTime");

        if (elementExists) {
            System.out.println("Елемент '" + localTimeValueToSearch + "' знайдено в HashSet");
        } else {
            System.out.println("Елемент '" + localTimeValueToSearch + "' відсутній в HashSet.");
        }
    }

    /**
    * Визначає найменше та найбільше значення в множині LocalTime.
     */
    private void locateMinMaxInSet() {
        if (localTimeSet == null || localTimeSet.isEmpty()) {
            System.out.println("HashSet є пустим або не ініціалізованим.");
            return;
        }

        long timeStart = System.nanoTime();

        LocalTime minValue = Collections.min(localTimeSet);
        LocalTime maxValue = Collections.max(localTimeSet);

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмального i максимального значення в HashSet типу LocalTime");

        System.out.println("Найменше значення в HashSet: " + minValue);
        System.out.println("Найбільше значення в HashSet: " + maxValue);
    }

    /**
     * Аналізує та порівнює елементи масиву та множини.
     */
    private void analyzeArrayAndSet() {
        System.out.println("Кiлькiсть елементiв в масивi: " + localTimeArray.length);
        System.out.println("Кiлькiсть елементiв в HashSet: " + localTimeSet.size());

        boolean allElementsPresent = true;
        for (LocalTime timeElement : localTimeArray) {
            if (!localTimeSet.contains(timeElement)) {
                allElementsPresent = false;
                break;
            }
        }

        if (allElementsPresent) {
            System.out.println("Всi елементи масиву наявні в HashSet.");
        } else {
            System.out.println("Не всi елементи масиву наявні в HashSet.");
        }
    }
}