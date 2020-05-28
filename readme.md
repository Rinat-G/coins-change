### Задача 28.
#### Задача о размене монет.
##### Условие
Предположим, что у нас есть неограниченное количество монет. Любая монета может быть описана при помощи двух параметров, своего достоинства и веса. Все монеты одного достоинства имеют одинаковый вес. Мы не можем предполагать, что монеты меньшего достоинства весят меньше чем монеты большего достоинства. Вес и достоинство монеты задаются при начале работы программы (из файла или консоли). Будем считать, что вес монеты и ее достоинство может быть описано целым числом.
Необходимо разработать алгоритм, который позволит разменять заданную сумму на монеты с наименьшим суммарным весом. Если таких разложений несколько, то взять то разложение, где количество монет минимально.

#### Решение.
Данная задача является частным случаем задачи о нахождении суммы подмножеств, которая в свою очередь является частным случаем задачи о ранце. Таким образом задача является NP полной и не имеет точного решения за полиноминальное время. В общем случае, если действовать методом полного перебора, требутеся найти все возможные способы размена суммы заданными монетами (то есть найти все возможные множества монет, сумма которых равна заданной сумме) и выбрать из всех вариантов ту, что меньше всего по массе. 

##### Алгоритм решения. 
Для решения задачи используется метод динамического программирования с использованием рекурсивной функции.
Для выполнения условия минимальной массы размена, все заданные пользователем монеты сортируются по относительной ценности (достоинство/массу) в убывающем порядке. В случае если массы монет равны, они сортируются по достоинству (так же в убывающем порядке). Это позволяет при прохождении коллекции от начала к концу при получении первого результата размена сразу получить размен наименьшей массы (или меньшее количество монет в случае если есть размен с такой же массой).
Максимальная глубина вызова рекурсивной равняется количеству видов заданных момент.
Каждый вызов рекурсивной функции получает на вход несколько аргументов: 
	1) остаток от суммы (в случае первого вызова всю сумму), которую необходимо разменять монетой, определенной уровнем рекурсии.
	2) индекс монеты в списке монет, по которому определяется достоинство текущей монеты
	3) массив монет отсортированных по относительной ценности в убывающем порядке.

Рекурсивная фунция пытается разменять заданную сумму (поделить без остатка) текущей монетой.
Если это удается, создается положительный результат и в него записывается количество монет текущего уровня, необходимого для размены суммы на текущем уровне, и этот результат возвращается на предыдущий уровень рекурсии.
Если у нее это не удается, она передает остаток от деления и индекс следующей монеты на следуюший уровень рекурсии. Если рекурсивный вызов следующего уровня рекурсии возвращает отрицательный результат, текущая функция уменьшает количество монет на своем уровне на единицу и остаток суммы снова передает на следующий уровень рекурсии. 
В случае успешного ответа от внутреннего рекурсивного вызова, функция создает положительный результат, запоминает текущее количество необходимых монет текущего достоинства и возвращает результат на вышестоящий уровень рекурсии. 
Таким образом осуществляется довольно быстрый поиск результата, в случае если размент текущими монетами возможне в принципе. 

Наихудшим сценарием с точки зрения длительности выполнения функции будет являться сценарий, когда заданную функцию невозможно разменять заданными монетами (например сумма нечетная, а все монеты - четные). В таком случае алгоритм становится аналогом полного перебора и время выполнения экспоненциально возрстает по формуле О((N/S)^M), где N - сумма монет, S - среднее арифметическое номиналов монет, M - количество типов монет. 
Размен нечетной суммы четными монетами - это пока единственный простой сценарий, который мне удалось найти, когда алгоритм может выполняться очень долго (т.е. за экспоненциальное время). Возможно, в дальнейшем стоит добавить подобную проверку перед выполнением. 

##### Как проверить задание.
Варианты запуска (необходимо наличие в системе Java версии не ниже 8й):
1. консольный ввод данных 

	`java -jar coins-1.0.jar`
	
	При запуске консольное приложение потребует ввести сумму и список монет. Список монет вводится целыми числами через пробел в формате: x1 y1 x2 y2, где xi - достоинство монеты, а yi - её масса
2. ввод данных с помощью аргументов запуска

	`java -jar coins-1.0.jar "33" "3 2 4 2"`
	
	где первый строковый агрумент - сумма, второй строковый аргумет - список монет в формате: x1 y1 x2 y2, где xi - достоинство монеты, а yi - её масса