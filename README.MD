# Справка

## Todo
Список задач.

## Использование

#### Поиск задач
HTTP метод: GET

URL: http://localhost:8080/v1/tasks

#### Добавление задачи
HTTP метод: POST

URL: http://localhost:8080/v1/tasks

BODY: {"text":"Заголовок первой задачи"}

#### Получение задачи
HTTP метод: GET

URL: http://localhost:8080/v1/tasks/{taskId}

#### Удаление задачи
HTTP метод: DELETE

URL: http://localhost:8080/v1/tasks/{taskId}

#### Изменение задачи
HTTP метод: PUT

URL: http://localhost:8080/v1/tasks

BODY:
{
    "id": 1, 
    "text": "Новый заголовок первой задачи",
    "state": 1
}

Возможные значения параметра "state":
* "0" - Новая задача
* "1" - Задача в работе
* "2" - Задача выполнена

#### Получение количества задач
HTTP метод: GET

URL: http://localhost:8080/v1/tasks/count

#### Проверка существования задачи
HTTP метод: HEAD

URL: http://localhost:8080/v1/tasks/{taskId}


