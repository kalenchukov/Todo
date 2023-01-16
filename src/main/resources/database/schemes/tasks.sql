--CREATE DATABASE `bd_todo`;

-- Таблица задач
CREATE TABLE `tasks`
(
	-- ID задачи
	`task_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,

	-- Текст
	`text` TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,

	-- Состояние
	`state` TINYINT UNSIGNED NOT NULL DEFAULT 0,

	-- Дата начала выполнения
	`process_at` TIMESTAMP NULL DEFAULT NULL,

	-- Дата завершения выполнения
	`completed_at` TIMESTAMP NULL DEFAULT NULL,

	-- Дата создания
	`created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

	-- Дата изменения
	`change_at` TIMESTAMP NULL DEFAULT NULL,

	PRIMARY KEY (`task_id`)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;