# Perfect Employer

## English

### Description
This application simulates keystrokes in a text editor window, creating the illusion that the text is typing itself. I originally created it for fun, to watch program source code magically appear on the screen. However, this program can also be used to simulate user activity because the keys "seem to be pressed" automatically.

### Requirements
The application uses Java version 8+

### Startup

```shell
java -jar -Xmx3m perfect-employee-1.7.1.jar
```

There should be a pe directory in the user directory and the sources should be in it.
The application has some default settings, you can override them in a customization file.

####
Example of startup with customization file

```shell
java -jar -Xmx3m perfect-employee-1.7.1.jar pe.properties
```

#### Example of a pe.properties customization file

```
root=/home/user/pe
ext=.java,.kt,.sql,.xml
delay.from=300
delay.to=1000
delay.initial=2000
delay.clean=5000
```
#### Comments

| Name          | Meaning                                                | Default           |
|---------------|--------------------------------------------------------|-------------------|
| root          | The directory where the source directories are located | <user profile>/pe |
| ext           | A comma-delimited list of "ends" of filenames          | .java             |
| delay.from    | Minimum delay after pressing a key, ms                 | 300               |
| delay.to      | max. delay after key press, ms                         | 1000              |
| delay.initial | Delay before printing starts, ms                       | 2000              |
| delay.clean   | Delay after the end of file printing, ms               | 5000              |


## Русский

### Описание
Это приложение симулирует нажатия клавиш в окне текстового редактора, создавая иллюзию, будто текст набирается сам по себе. Первоначально я создал её для развлечения, чтобы наблюдать, как на экране волшебным образом появляются исходные коды программ. Однако эту программу можно также использовать для имитации активности пользователя, поскольку клавиши "как бы нажимаются" автоматически.

### Требования
Приложение использует Java версии 8+

### Запуск

```shell
java -jar -Xmx3m perfect-employee-1.7.1.jar
```

В пользовательской директории должна быть директория pe, в ней должны лежать исходники.
У приложения есть некоторые установки по умолчанию, их можно переопределить в настроечном файле.

####
Пример запуска с настроечным файлом

```shell
java -jar -Xmx3m perfect-employee-1.7.1.jar pe.properties
```

#### Пример настроечного файла pe.properties

```
root=/home/user/pe
ext=.java,.kt,.sql,.xml
delay.from=300
delay.to=1000
delay.initial=2000
delay.clean=5000
```
#### Комментарии

| Название      | Смысл                                                | Значение по умолчанию     |
|---------------|------------------------------------------------------|---------------------------|
| root          | Директория, в которой лежат директории с исходниками | <профиль пользователя>/pe |
| ext           | Список "концов" имен файлов через запятую            | .java                     |
| delay.from    | Минимальная задержка после нажатия клавиши, мс       | 300                       |
| delay.to      | Максимальная задержка после нажатия клавиши, мс      | 1000                      |
| delay.initial | Задержка перед началом печати, мс                    | 2000                      |
| delay.clean   | Задержка после конца печати файла, мс                | 5000                      |
