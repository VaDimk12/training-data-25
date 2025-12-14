import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Клас BasicDataOperationUsingMap реалізує операції з колекціями типу Map для зберігання пар ключ-значення.
 * 
 * <p>Методи класу:</p>
 * <ul>
 *   <li>{@link #executeDataOperations()} - Виконує комплекс операцій з даними Map.</li>
 *   <li>{@link #findByKey()} - Здійснює пошук елемента за ключем в Map.</li>
 *   <li>{@link #findByValue()} - Здійснює пошук елемента за значенням в Map.</li>
 *   <li>{@link #addEntry()} - Додає новий запис до Map.</li>
 *   <li>{@link #removeByKey()} - Видаляє запис з Map за ключем.</li>
 *   <li>{@link #removeByValue()} - Видаляє записи з Map за значенням.</li>
 *   <li>{@link #sortByKey()} - Сортує Map за ключами.</li>
 *   <li>{@link #sortByValue()} - Сортує Map за значеннями.</li>
 * </ul>
 */
public class BasicDataOperationUsingMap {
    private final Mouse KEY_TO_SEARCH_AND_DELETE = new Mouse("Фунтик", 8.1);
    private final Mouse KEY_TO_ADD = new Mouse("Круть", 7.8);

    private final String VALUE_TO_SEARCH_AND_DELETE = "Андрій";
    private final String VALUE_TO_ADD = "Богдан";

    private HashMap<Mouse, String> hashMap;
    private Hashtable<Mouse, String> hashtable;

    /**
     * Компаратор для сортування Map.Entry за значеннями String.
     * Використовує метод String.compareTo() для порівняння імен власників.
     */
    static class OwnerValueComparator implements Comparator<Map.Entry<Mouse, String>> {
        @Override
        public int compare(Map.Entry<Mouse, String> e1, Map.Entry<Mouse, String> e2) {
            String v1 = e1.getValue();
            String v2 = e2.getValue();
            if (v1 == null && v2 == null) return 0;
            if (v1 == null) return -1;
            if (v2 == null) return 1;
            return v1.compareTo(v2);
        }
    }

    /**
     * Внутрішній клас Mouse для зберігання інформації про домашню тварину.
     * 
     * Реалізує Comparable<Mouse> для визначення природного порядку сортування.
     * Природний порядок: спочатку за кличкою (nickname) за спаданням, потім за довжиною хвоста (tailLength) за зростанням.
     */
    public static class Mouse implements Comparable<Mouse> {
        private final String nickname;
        private final Double tailLength;

        public Mouse(String nickname, Double tailLength) {
            this.nickname = nickname;
            this.tailLength = tailLength;
        }

        public String getNickname() { 
            return nickname; 
        }

        public Double getTailLength() {
            return tailLength;
        }

        /**
         * Порівнює цей об'єкт Mouse з іншим для визначення порядку сортування.
         * Природний порядок: спочатку за кличкою (nickname) за спаданням, потім за довжиною хвоста (tailLength) за зростанням.
         * 
         * @param other Mouse об'єкт для порівняння
         * @return негативне число, якщо цей Mouse < other; 
         *         0, якщо цей Mouse == other; 
         *         позитивне число, якщо цей Mouse > other
         * 
         * Критерій порівняння: поля nickname (кличка) за спаданням та tailLength (довжина хвоста) за зростанням.
         * 
         * Цей метод використовується:
         * - TreeMap для автоматичного сортування ключів Mouse за nickname (спадання), потім за tailLength (зростання)
         * - Collections.sort() для сортування Map.Entry за ключами Mouse
         * - Collections.binarySearch() для пошуку в відсортованих колекціях
         */
        @Override
        public int compareTo(Mouse other) {
            if (other == null) return 1;
            
            // Спочатку порівнюємо за кличкою (за спаданням)
            int nicknameComparison = 0;
            if (this.nickname == null && other.nickname == null) {
                nicknameComparison = 0;
            } else if (this.nickname == null) {
                nicknameComparison = 1;  // null йде в кінець при спаданні
            } else if (other.nickname == null) {
                nicknameComparison = -1;
            } else {
                nicknameComparison = -this.nickname.compareTo(other.nickname);  // Інвертоване для спадання
            }
            
            // Якщо клички різні, повертаємо результат
            if (nicknameComparison != 0) {
                return nicknameComparison;
            }
            
            // Якщо клички однакові, порівнюємо за довжиною хвоста (за зростанням)
            if (this.tailLength == null && other.tailLength == null) return 0;
            if (this.tailLength == null) return -1;
            if (other.tailLength == null) return 1;
            return this.tailLength.compareTo(other.tailLength);
        }

        /**
         * Перевіряє рівність цього Mouse з іншим об'єктом.
         * Два Mouse вважаються рівними, якщо їх клички (nickname) та довжини хвоста (tailLength) однакові.
         * 
         * @param obj об'єкт для порівняння
         * @return true, якщо об'єкти рівні; false в іншому випадку
         * 
         * Критерій рівності: поля nickname (кличка) та tailLength (довжина хвоста).
         * 
         * Важливо: метод узгоджений з compareTo() - якщо equals() повертає true,
         * то compareTo() повертає 0, оскільки обидва методи порівнюють за nickname та tailLength.
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Mouse mouse = (Mouse) obj;
            
            boolean nicknameEquals = nickname != null ? nickname.equals(mouse.nickname) : mouse.nickname == null;
            boolean tailLengthEquals = tailLength != null ? tailLength.equals(mouse.tailLength) : mouse.tailLength == null;
            
            return nicknameEquals && tailLengthEquals;
        }

        /**
         * Повертає хеш-код для цього Mouse.
         * 
         * @return хеш-код, обчислений на основі nickname та tailLength
         * 
         * Базується на полях nickname та tailLength для узгодженості з equals().
         * 
         * Важливо: узгоджений з equals() - якщо два Mouse рівні за equals()
         * (мають однакові nickname та tailLength), вони матимуть однаковий hashCode().
         */
        @Override
        public int hashCode() {
            // Початкове значення: хеш-код поля nickname (або 0, якщо nickname == null)
            int result = nickname != null ? nickname.hashCode() : 0;
            
            // Комбінуємо хеш-коди полів за формулою: result = 31 * result + hashCode(поле)
            // Множник 31 - просте число, яке дає хороше розподілення хеш-кодів
            // і оптимізується JVM як (result << 5) - result
            // Додаємо хеш-код tailLength (або 0, якщо tailLength == null) до загального результату
            result = 31 * result + (tailLength != null ? tailLength.hashCode() : 0);
            
            return result;
        }

        /**
         * Повертає строкове представлення Mouse.
         * 
         * @return кличка тварини (nickname), довжина хвоста (tailLength) та hashCode
         */
        @Override
        public String toString() {
            return "Mouse{nickname='" + nickname + "', tailLength=" + tailLength + "}";
        }
    }

    /**
     * Конструктор, який ініціалізує об'єкт з готовими даними.
     * 
     * @param hashMap HashMap з початковими даними (ключ: Mouse, значення: ім'я власника)
     * @param hashtable Hashtable з початковими даними (ключ: Mouse, значення: ім'я власника)
     */
    BasicDataOperationUsingMap(HashMap<Mouse, String> hashMap, Hashtable<Mouse, String> hashtable) {
        this.hashMap = hashMap;
        this.hashtable = hashtable;
    }
    
    /**
     * Виконує комплексні операції з Map.
     * 
     * Метод виконує різноманітні операції з Map: пошук, додавання, видалення та сортування.
     */
    public void executeDataOperations() {
        // Спочатку працюємо з HashMap
        System.out.println("========= Операції з HashMap =========");
        System.out.println("Початковий розмір HashMap: " + hashMap.size());
        
        // Пошук до сортування
        findByKeyInHashMap();
        findByValueInHashMap();

        printHashMap();
        sortHashMap();
        printHashMap();

        // Пошук після сортування
        findByKeyInHashMap();
        findByValueInHashMap();

        addEntryToHashMap();
        
        removeByKeyFromHashMap();
        removeByValueFromHashMap();
               
        System.out.println("Кінцевий розмір HashMap: " + hashMap.size());

        // Потім обробляємо Hashtable
        System.out.println("\n\n========= Операції з Hashtable =========");
        System.out.println("Початковий розмір Hashtable: " + hashtable.size());
        
        findByKeyInHashtable();
        findByValueInHashtable();

        printHashtable();
        sortHashtable();
        printHashtable();

        findByKeyInHashtable();
        findByValueInHashtable();

        addEntryToHashtable();
        
        removeByKeyFromHashtable();
        removeByValueFromHashtable();
        
        System.out.println("Кінцевий розмір Hashtable: " + hashtable.size());
    }


    // ===== Методи для Hashtable =====

    /**
     * Виводить вміст Hashtable без сортування.
     * Hashtable не гарантує жодного порядку елементів.
     */
    private void printHashtable() {
        System.out.println("\n=== Пари ключ-значення в Hashtable ===");
        long timeStart = System.nanoTime();

        for (Map.Entry<Mouse, String> entry : hashtable.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }

        PerformanceTracker.displayOperationTime(timeStart, "виведення пари ключ-значення в Hashtable");
    }

    /**
     * Сортує Hashtable за ключами.
     * Використовує Collections.sort() з природним порядком Mouse (Mouse.compareTo()).
     * Перезаписує hashtable відсортованими даними.
     */
    private void sortHashtable() {
        long timeStart = System.nanoTime();

        // Створюємо список ключів і сортуємо за природним порядком Mouse
        List<Mouse> sortedKeys = new ArrayList<>(hashtable.keySet());
        Collections.sort(sortedKeys);
        
        // Створюємо нову Hashtable з відсортованими ключами
        Hashtable<Mouse, String> sortedHashtable = new Hashtable<>();
        for (Mouse key : sortedKeys) {
            sortedHashtable.put(key, hashtable.get(key));
        }
        
        // Перезаписуємо оригінальну hashtable
        hashtable = sortedHashtable;

        PerformanceTracker.displayOperationTime(timeStart, "сортування Hashtable за ключами");
    }

    /**
     * Здійснює пошук елемента за ключем в Hashtable.
     * Використовує Mouse.hashCode() та Mouse.equals() для пошуку.
     */
    void findByKeyInHashtable() {
        long timeStart = System.nanoTime();

        boolean found = hashtable.containsKey(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "пошук за ключем в Hashtable");

        if (found) {
            String value = hashtable.get(KEY_TO_SEARCH_AND_DELETE);
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' знайдено. Власник: " + value);
        } else {
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' відсутній в Hashtable.");
        }
    }

    /**
     * Здійснює пошук елемента за значенням в Hashtable.
     * Сортує список Map.Entry за значеннями та використовує бінарний пошук.
     */
    void findByValueInHashtable() {
        long timeStart = System.nanoTime();

        // Створюємо список Entry та сортуємо за значеннями
        List<Map.Entry<Mouse, String>> entries = new ArrayList<>(hashtable.entrySet());
        OwnerValueComparator comparator = new OwnerValueComparator();
        Collections.sort(entries, comparator);

        // Створюємо тимчасовий Entry для пошуку
        Map.Entry<Mouse, String> searchEntry = new Map.Entry<Mouse, String>() {
            public Mouse getKey() { return null; }
            public String getValue() { return VALUE_TO_SEARCH_AND_DELETE; }
            public String setValue(String value) { return null; }
        };

        int position = Collections.binarySearch(entries, searchEntry, comparator);

        PerformanceTracker.displayOperationTime(timeStart, "бінарний пошук за значенням в Hashtable");

        if (position >= 0) {
            Map.Entry<Mouse, String> foundEntry = entries.get(position);
            System.out.println("Власник '" + VALUE_TO_SEARCH_AND_DELETE + "' має тварину: " + foundEntry.getKey());
        } else {
            System.out.println("Власник '" + VALUE_TO_SEARCH_AND_DELETE + "' відсутній в Hashtable.");
        }
    }

    /**
     * Додає новий запис до Hashtable.
     */
    void addEntryToHashtable() {
        long timeStart = System.nanoTime();

        hashtable.put(KEY_TO_ADD, VALUE_TO_ADD);

        PerformanceTracker.displayOperationTime(timeStart, "додавання запису до Hashtable");

        System.out.println("Додано новий запис: Pet='" + KEY_TO_ADD + "', власник='" + VALUE_TO_ADD + "'");
    }

    /**
     * Видаляє запис з Hashtable за ключем.
     */
    void removeByKeyFromHashtable() {
        long timeStart = System.nanoTime();

        String removedValue = hashtable.remove(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "видалення за ключем з Hashtable");

        if (removedValue != null) {
            System.out.println("Видалено запис з ключем '" + KEY_TO_SEARCH_AND_DELETE + "'. Власник був: " + removedValue);
        } else {
            System.out.println("Ключ '" + KEY_TO_SEARCH_AND_DELETE + "' не знайдено для видалення.");
        }
    }

    /**
     * Видаляє записи з Hashtable за значенням.
     */
    void removeByValueFromHashtable() {
        long timeStart = System.nanoTime();

        List<Mouse> keysToRemove = new ArrayList<>();
        for (Map.Entry<Mouse, String> entry : hashtable.entrySet()) {
            if (entry.getValue() != null && entry.getValue().equals(VALUE_TO_SEARCH_AND_DELETE)) {
                keysToRemove.add(entry.getKey());
            }
        }
        
        for (Mouse key : keysToRemove) {
            hashtable.remove(key);
        }

        PerformanceTracker.displayOperationTime(timeStart, "видалення за значенням з Hashtable");

        System.out.println("Видалено " + keysToRemove.size() + " записів з власником '" + VALUE_TO_SEARCH_AND_DELETE + "'");
    }

    // ===== Методи для HashMap =====

    /**
     * Виводить вміст HashMap.
     * HashMap не гарантує жодного порядку елементів.
     */
    private void printHashMap() {
        System.out.println("\n=== Пари ключ-значення в HashMap ===");

        long timeStart = System.nanoTime();
        for (Map.Entry<Mouse, String> entry : hashMap.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }

        PerformanceTracker.displayOperationTime(timeStart, "виведення пар ключ-значення в HashMap");
    }

    /**
     * Сортує HashMap за ключами.
     * Використовує Collections.sort() з природним порядком Mouse (Mouse.compareTo()).
     * Перезаписує hashMap відсортованими даними.
     */
    private void sortHashMap() {
        long timeStart = System.nanoTime();

        // Створюємо список ключів і сортуємо за природним порядком Mouse
        List<Mouse> sortedKeys = new ArrayList<>(hashMap.keySet());
        Collections.sort(sortedKeys);
        
        // Створюємо нову HashMap з відсортованими ключами
        HashMap<Mouse, String> sortedHashMap = new HashMap<>();
        for (Mouse key : sortedKeys) {
            sortedHashMap.put(key, hashMap.get(key));
        }
        
        // Перезаписуємо оригінальну hashMap
        hashMap = sortedHashMap;

        PerformanceTracker.displayOperationTime(timeStart, "сортування HashMap за ключами");
    }

    /**
     * Здійснює пошук елемента за ключем в HashMap.
     * Використовує Mouse.hashCode() та Mouse.equals() для пошуку.
     */
    void findByKeyInHashMap() {
        long timeStart = System.nanoTime();

        boolean found = hashMap.containsKey(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "пошук за ключем в HashMap");

        if (found) {
            String value = hashMap.get(KEY_TO_SEARCH_AND_DELETE);
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' знайдено. Власник: " + value);
        } else {
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' відсутній в HashMap.");
        }
    }

    /**
     * Здійснює пошук елемента за значенням в HashMap.
     * Сортує список пар ключ-значення за ключами, потім використовує бінарний пошук.
     */
    void findByValueInHashMap() {
        long timeStart = System.nanoTime();

        // Створюємо список записів і сортуємо за ключами
        List<Map.Entry<Mouse, String>> entries = new ArrayList<>(hashMap.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<Mouse, String>>() {
            @Override
            public int compare(Map.Entry<Mouse, String> e1, Map.Entry<Mouse, String> e2) {
                return e1.getKey().compareTo(e2.getKey());
            }
        });

        // Шукаємо за значенням
        boolean found = false;
        for (Map.Entry<Mouse, String> entry : entries) {
            if (VALUE_TO_SEARCH_AND_DELETE.equals(entry.getValue())) {
                System.out.println("Власник '" + VALUE_TO_SEARCH_AND_DELETE + "' має тварину: " + entry.getKey());
                found = true;
            }
        }

        PerformanceTracker.displayOperationTime(timeStart, "пошук за значенням в HashMap");

        if (!found) {
            System.out.println("Власник '" + VALUE_TO_SEARCH_AND_DELETE + "' відсутній в HashMap.");
        }
    }

    /**
     * Додає новий запис до HashMap.
     */
    private void addEntryToHashMap() {
        long timeStart = System.nanoTime();

        hashMap.put(KEY_TO_ADD, VALUE_TO_ADD);

        PerformanceTracker.displayOperationTime(timeStart, "додавання пари ключ-значення до HashMap");

        System.out.println("Додано: " + KEY_TO_ADD + " -> " + VALUE_TO_ADD);
    }

    /**
     * Видаляє запис з HashMap за ключем.
     */
    private void removeByKeyFromHashMap() {
        long timeStart = System.nanoTime();

        boolean removed = hashMap.remove(KEY_TO_SEARCH_AND_DELETE) != null;

        PerformanceTracker.displayOperationTime(timeStart, "видалення за ключем з HashMap");

        if (removed) {
            System.out.println("Видалено запис з ключем '" + KEY_TO_SEARCH_AND_DELETE + "'");
        } else {
            System.out.println("Запис з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' не знайдено в HashMap.");
        }
    }

    /**
     * Видаляє записи з HashMap за значенням.
     */
    private void removeByValueFromHashMap() {
        long timeStart = System.nanoTime();

        List<Mouse> keysToRemove = new ArrayList<>();
        for (Map.Entry<Mouse, String> entry : hashMap.entrySet()) {
            if (VALUE_TO_SEARCH_AND_DELETE.equals(entry.getValue())) {
                keysToRemove.add(entry.getKey());
            }
        }

        for (Mouse key : keysToRemove) {
            hashMap.remove(key);
        }

        PerformanceTracker.displayOperationTime(timeStart, "видалення за значенням з HashMap");

        System.out.println("Видалено " + keysToRemove.size() + " записів з власником '" + VALUE_TO_SEARCH_AND_DELETE + "'");
    }

    /**
     * Головний метод для запуску програми.
     */
    public static void main(String[] args) {
        // Створюємо початкові дані (ключ: Mouse, значення: ім'я власника)
        HashMap<Mouse, String> hashMap = new HashMap<>();
        hashMap.put(new Mouse("Яшко", 7.2), "Олексій");
        hashMap.put(new Mouse("Юрок", 6.8), "Марія");
        hashMap.put(new Mouse("Фунтик", 8.1), "Андрій");
        hashMap.put(new Mouse("Умка", 7.5), "Софія");
        hashMap.put(new Mouse("Тішка", 6.3), "Наталя");
        hashMap.put(new Mouse("Фунтик", 7.9), "Ірина");
        hashMap.put(new Mouse("Рудик", 8.4), "Андрій");
        hashMap.put(new Mouse("Піксик", 6.7), "Дмитро");
        hashMap.put(new Mouse("Норка", 7.1), "Марія");
        hashMap.put(new Mouse("Мушка", 6.9), "Олена");

        Hashtable<Mouse, String> hashtable = new Hashtable<>();
        hashtable.put(new Mouse("Яшко", 7.2), "Олексій");
        hashtable.put(new Mouse("Юрок", 6.8), "Марія");
        hashtable.put(new Mouse("Фунтик", 8.1), "Андрій");
        hashtable.put(new Mouse("Умка", 7.5), "Софія");
        hashtable.put(new Mouse("Тішка", 6.3), "Наталя");
        hashtable.put(new Mouse("Фунтик", 7.9), "Ірина");
        hashtable.put(new Mouse("Рудик", 8.4), "Андрій");
        hashtable.put(new Mouse("Піксик", 6.7), "Дмитро");
        hashtable.put(new Mouse("Норка", 7.1), "Марія");
        hashtable.put(new Mouse("Мушка", 6.9), "Олена");

        // Створюємо об'єкт і виконуємо операції
        BasicDataOperationUsingMap operations = new BasicDataOperationUsingMap(hashMap, hashtable);
        operations.executeDataOperations();
    }
}


