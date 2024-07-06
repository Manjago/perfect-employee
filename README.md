# Perfect Employee

## English documentation

### Description
This application simulates keystrokes in a text editor window, creating the illusion that the text is typing itself. I originally created it for fun, to watch program source code magically appear on the screen. However, this program can also be used to simulate user activity because the keys "seem to be pressed" automatically.

t is better to use a simple editor like Far Manager, because in VSCode and other "smart" editors, the Smart Indent setting can interfere.

### Requirements
The application uses Java version 8+

### Installation
To install the application, you need to download the `perfect-employee-1.7.2.jar` file and place it in a desired directory.

### Startup

```shell
java -jar -Xmx3m perfect-employee-1.7.2.jar
```

There should be a `pe` directory in the user's directory, and the source files should be in it.
The application has some default settings, which you can override in a customization file.

### Example of startup with a customization file

```shell
java -jar -Xmx3m perfect-employee-1.7.2.jar pe.properties
```

### Example of a `pe.properties` customization file

```
root=/home/user/pe
ext=.java,.kt,.sql,.xml
delay.from=300
delay.to=1000
delay.initial=2000
delay.clean=5000
```

#### Comments to `pe.proerties`

| Name          | Meaning                                                | Default             |
|---------------|--------------------------------------------------------|---------------------|
| root          | The directory where the source directories are located | `<user profile>/pe` |
| ext           | A comma-delimited list of file extensions              | .java               |
| delay.from    | Minimum delay after pressing a key, ms                 | 300                 |
| delay.to      | Maximum delay after pressing a key, ms                 | 1000                |
| delay.initial | Delay before printing starts, ms                       | 2000                |
| delay.clean   | Delay after the end of file printing, ms               | 5000                |

## Usage Examples
Here are some examples of how you can use the application:

1. **Basic Usage**:
    ```shell
    java -jar -Xmx3m perfect-employee-1.7.2.jar
    ```

2. **With Customization File**:
    ```shell
    java -jar -Xmx3m perfect-employee-1.7.2.jar pe.properties
    ```

### Contributing
If you would like to contribute to this project, please fork the repository and submit a pull request. For major changes, please open an issue first to discuss what you would like to change.

### License
This project is licensed under the CC0 1.0 Universal License - see the [LICENSE](LICENSE) file for details.

### Contact
For any questions or suggestions, please contact [temnenkov@yandex.ru](mailto:temnenkov@yandex.ru).

## Русская документация

### Описание
Это приложение симулирует нажатия клавиш в окне текстового редактора, создавая иллюзию, будто текст набирается сам по себе. Первоначально я создал его для развлечения, чтобы наблюдать, как на экране волшебным образом появляются исходные коды программ. Однако эту программу можно также использовать для имитации активности пользователя, поскольку клавиши "как бы нажимаются" автоматически.

Лучше использовать какой-нибудь простой редактор, типа Far Manager, потому что в VsCode и прочих "умных" редакторах мешает настройка Smrt Indent

### Требования
Приложение использует Java версии 8+

### Установка
Для установки приложения необходимо скачать файл `perfect-employee-1.7.2.jar` и поместить его в нужную директорию.

### Запуск

```shell
java -jar -Xmx3m perfect-employee-1.7.2.jar
```

В пользовательской директории должна быть директория `pe`, в которой должны находиться исходные файлы.
У приложения есть некоторые настройки по умолчанию, их можно переопределить в настроечном файле.

### Пример запуска с настроечным файлом

```shell
java -jar -Xmx3m perfect-employee-1.7.2.jar pe.properties
```

### Пример настроечного файла `pe.properties`

```
root=/home/user/pe
ext=.java,.kt,.sql,.xml
delay.from=300
delay.to=1000
delay.initial=2000
delay.clean=5000
```
#### Комментарии к `pe.properties`

| Название      | Смысл                                                | Значение по умолчанию     |
|---------------|------------------------------------------------------|---------------------------|
| root          | Директория, в которой лежат директории с исходниками | <профиль пользователя>/pe |
| ext           | Список "концов" имен файлов через запятую            | .java                     |
| delay.from    | Минимальная задержка после нажатия клавиши, мс       | 300                       |
| delay.to      | Максимальная задержка после нажатия клавиши, мс      | 1000                      |
| delay.initial | Задержка перед началом печати, мс                    | 2000                      |
| delay.clean   | Задержка после конца печати файла, мс                | 5000                      |

### Примеры использования
Вот несколько примеров того, как можно использовать приложение:

1. **Базовое использование**:
    ```shell
    java -jar -Xmx3m perfect-employee-1.7.2.jar
    ```

2. **С настроечным файлом**:
    ```shell
    java -jar -Xmx3m perfect-employee-1.7.2.jar pe.properties
    ```

### Вклад в проект
Если вы хотите внести вклад в этот проект, пожалуйста, сделайте форк репозитория и отправьте pull request. Для значительных изменений, пожалуйста, сначала создайте issue, чтобы обсудить, что вы хотите изменить.

### Лицензия
Этот проект распространяется под лицензией CC0 1.0 Universal - подробнее см. в файле [LICENSE](LICENSE).

### Контакты
Для любых вопросов или предложений, пожалуйста, свяжитесь по электронной почте [temnenkov@yandex.ru](mailto:temnenkov@yandex.ru).