# Генератор текстовых отчетов
1. Используется язык Java 1.8
2. Проект собирается maven. Для этого в папке проекта выполнить mvn package. После этого в папке target/ появится файл TextParser.jar.
3. Генератор можно запустить из командой строки через java -jar TextParser.jar.
4. Если ввести неправильные ключи, программа выведет на экран:<br/>
  Wrong args. Usage: java -jar TextParser.jar settings.xml data.tsv [report.txt]
5. Если не вводить третий параметр, то текстовый отчет будет выведен на экран.
6. Если сумма длин всех колонок, объявленных в settings.xml больше, чем ширина страницы, программа прекратит работу с ошибкой:<br/>
  Wrong settings: Sum of column's width is larger than whole page width!
7. Если число колонок в settings.xml не соответствует числу колонок из data.tsv, программа завершится с ошибкой:<br/>
  Data is not compatible with settings!
