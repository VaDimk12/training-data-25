import java.time.LocalTime;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Клас BasicDataOperationUsingList реалізує операції з колекціями типу LinkedList для даних LocalTime.
 * 
 * <p>Методи класу:</p>
 * <ul>
 *   <li>{@link #executeDataOperations()} - Виконує комплекс операцій з даними.</li>
 *   <li>{@link #performArraySorting()} - Упорядковує масив елементів LocalTime.</li>
 *   <li>{@link #findInArray()} - Здійснює пошук елемента в масиві LocalTime.</li>
 *   <li>{@link #locateMinMaxInArray()} - Визначає найменше і найбільше значення в масиві.</li>
 *   <li>{@link #sortList()} - Сортує колекцію List з LocalTime.</li>
 *   <li>{@link #findInList()} - Пошук конкретного значення в списку.</li>
 *   <li>{@link #locateMinMaxInList()} - Пошук мінімального і максимального значення в списку.</li>
 * </ul>
 */
public class BasicDataOperationUsingList {
    private LocalTime localTimeValueToSearch;
    private LocalTime[] localTimeArray;
    private List<LocalTime> localTimeList;

    /**
     * Конструктор, який iнiцiалiзує об'єкт з готовими даними.
     * 
    * @param localTimeValueToSearch Значення для пошуку
    * @param localTimeArray Масив LocalTime
     */
    BasicDataOperationUsingList(LocalTime localTimeValueToSearch, LocalTime[] localTimeArray) {
        this.localTimeValueToSearch = localTimeValueToSearch;
        this.localTimeArray = localTimeArray;
        this.localTimeList = new LinkedList<>(Arrays.asList(localTimeArray));
    }
    
    /**
     * Виконує комплексні операції з структурами даних.
     * 
    * Метод завантажує масив і список об'єктів LocalTime, 
    * здійснює сортування та пошукові операції для типу LocalTime.
     */
    public void executeDataOperations() {
        // спочатку працюємо з колекцією List
        findInList();
        locateMinMaxInList();
        
        sortList();
        
        findInList();
        locateMinMaxInList();

        // потім обробляємо масив дати та часу
        findInArray();
        locateMinMaxInArray();

        performArraySorting();
        
        findInArray();
        locateMinMaxInArray();

        // зберігаємо відсортований масив до окремого файлу
        DataFileHandler.writeArrayToFile(localTimeArray, BasicDataOperation.PATH_TO_DATA_FILE + ".sorted");
    }

    /**
    * Упорядковує масив об'єктів LocalTime за зростанням.
     * Фіксує та виводить тривалість операції сортування в наносекундах.
     */
    void performArraySorting() {
        long timeStart = System.nanoTime();

        Arrays.sort(localTimeArray);

        PerformanceTracker.displayOperationTime(timeStart, "упорядкування масиву дати i часу");
    }

    /**
     * Здійснює пошук конкретного значення в масиві дати та часу.
     */
    void findInArray() {
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
     * Визначає найменше та найбільше значення в масиві дати та часу.
     */
    void locateMinMaxInArray() {
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

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмальної i максимальної дати в масивi");

        System.out.println("Найменше значення в масивi: " + minValue);
        System.out.println("Найбільше значення в масивi: " + maxValue);
    }

    /**
     * Шукає конкретне значення дати та часу в колекції ArrayList.
     */
    void findInList() {
        long timeStart = System.nanoTime();

        int position = Collections.binarySearch(this.localTimeList, localTimeValueToSearch);

        PerformanceTracker.displayOperationTime(timeStart, "пошук елемента в List типу LocalTime");        

        if (position >= 0) {
            System.out.println("Елемент '" + localTimeValueToSearch + "' знайдено в LinkedList за позицією: " + position);
        } else {
            System.out.println("Елемент '" + localTimeValueToSearch + "' відсутній в LinkedList.");
        }
    }

    /**
    * Визначає найменше і найбільше значення в колекції LinkedList для LocalTime.
     */
    void locateMinMaxInList() {
        if (localTimeList == null || localTimeList.isEmpty()) {
            System.out.println("Колекція LinkedList є пустою або не ініціалізованою.");
            return;
        }

        long timeStart = System.nanoTime();

        LocalTime minValue = Collections.min(localTimeList);
        LocalTime maxValue = Collections.max(localTimeList);

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмального i максимального значення в List типу LocalTime");

        System.out.println("Найменше значення в List: " + minValue);
        System.out.println("Найбільше значення в List: " + maxValue);
    }

    /**
    * Упорядковує колекцію List з об'єктами LocalTime за зростанням.
     * Відстежує та виводить час виконання операції сортування.
     */
    void sortList() {
        long timeStart = System.nanoTime();

        Collections.sort(localTimeList);

        PerformanceTracker.displayOperationTime(timeStart, "упорядкування LinkedList типу LocalTime");
    }
}