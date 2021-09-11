# Лабораторная работа 1

**Название:** "Разработка драйверов символьных устройств"

**Цель работы:** Получить знания и навыки разработки драйверов символьных устройств для операционной системы Linux.

## Описание функциональности драйвера

При записи в файл символьного устройства текста типа “5+6” запоминается результат операции, то есть 11 для данного примера.
Поддерживаются операции сложения, вычитания, умножения и деления.
Последовательность полученных результатов с момента загрузки модуля ядра можно получить при чтении созданного файла /proc/var2 в консоль пользователя.
При чтении из файла символьного устройства в кольцевой буфер ядра осуществляется вывод тех же данных, которые выводятся при чтении файла /proc/var2.


## Инструкция по сборке

```shell
make
sudo insmod char_dev.ko
```

## Инструкция пользователя

Чтобы драйвер вычислил результат арифметического выражения и сохранил его, нужно записать выражение в соответствующий файл устройства:
```shell
echo >/dev/var2 5+6
```

Чтобы вывести в консоль последовательность сохраненных результатов, нужно считать файл /proc/var2
```shell
cat /proc/var2
```

Чтобы выполнять указанные выше команды, необходимо предварительно получить права суперпользователя, выполнив следующую команду:
```shell
sudo -i
```

Кроме того, драйвер предоставляет переименовать используемый proc-файл во время работы.
Для этого нужно вывести в /dev/var2 команду вида @new_file_name, где после символа @ следует новое имя proc-файла.

## Примеры использования
```shell
echo >/dev/var2 5+11
cat /proc/var2
16
```
```shell
echo >/dev/var2 5-6
cat /proc/var2
16
-1
```
```shell
echo >/dev/var2 @newfile
cat /proc/var2
cat: /proc/var2: No such file or directory
cat /proc/newfile
16
-1
```