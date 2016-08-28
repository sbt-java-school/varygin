В папке main/java/home лежат решения всех домашних заданий сгруппированные по номерам лекций.

К примеру: main/java/home/lesson2 - первое домашнее задание про грузовик (вторая лекция).

Лекция №6.
Домашнее задание:
1. Вывести на консоль все методы класса, (включая родительские методы и приватные),
все геттерыкласса и проверить, что все String константы имеют значение,
равное их имени.
Реалиация: <a href="https://github.com/sbt-java-school/vladimir.varygin/tree/master/src/main/java/home/lesson6/part1">src/main/java/home/lesson6/part1</a>
Во время выполнения познакомился с интерфейсом Member, за счёт которого удалось
значительно оптимизировать реализацию по сравнению с предыдущей.

2. Реализовать класс BeanUtils по документации.
Реализация: <a href="https://github.com/sbt-java-school/vladimir.varygin/tree/master/src/main/java/home/lesson6/part2">src/main/java/home/lesson6/part2</a>
Во время выполнения научился сравнивать тип возвращаемого значения метода
с типом данных (с возможным расширением до суперкласса).


Лекция №7
Домашнее задание: научиться использовать разные сборщики мусора в java
Реализация: <a href="https://github.com/sbt-java-school/vladimir.varygin/tree/master/src/main/java/home/lesson7">src/main/java/home/lesson7</a>
Во время выполнения научился использовать ранзые сборщики мусора в java и отслеживать
изменения в памяти при помощи плагина VisualVM.


Лекция №8.
Домашнее задание: Реализовать кэширующий прокси.
Реализация: <a href="https://github.com/sbt-java-school/vladimir.varygin/tree/master/src/main/java/home/lesson8">src/main/java/home/lesson8</a>
<sode>EightMain</code> - Класс для запуска и тестирования программы (содержит метод main).
<sode>Calculator</code> - Интерфейс для выполнения операций сложения и умножения.
Содержит два метода:
sum - операция сложения двух чисел.
multiple - операция умножения двух чисел.
<sode>CalculatorImpl</code> - Класс, реализующий интерфейс Calculator.
<sode>utils/ProxyUtils</code> - Класс для создания экземпляра прокси.
<sode>utils/Cache</code> - Аннотация для определения необходимости кеширования метода / класса методов
Поддерживает установку времени хранения данных в кеше.
<sode>utils/CacheInvocationHandler</code> - Содержит реализацию кэширующего прокси.
В качестве кэша используется HashMap с ключами "String" и значениями "HashMap".
В значениях хранится карта следующего формата:
time => срок жизни кэша в милисекундах
value => закешированное значение