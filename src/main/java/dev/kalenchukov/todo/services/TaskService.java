/*
 * Copyright © 2022-2023 Алексей Каленчуков
 * GitHub: https://github.com/kalenchukov
 * E-mail: mailto:aleksey.kalenchukov@yandex.ru
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.kalenchukov.todo.services;

import dev.kalenchukov.todo.entities.TaskEntity;
import dev.kalenchukov.todo.repositories.TaskRepositories;
import dev.kalenchukov.todo.resources.TaskState;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service(value = "taskService")
public class TaskService implements TaskServices
{
	private final TaskRepositories taskRepository;

	public TaskService(@Qualifier(value = "taskRepository") final TaskRepositories taskRepository)
	{
		this.taskRepository = taskRepository;
	}

	@Override
	public void create(final TaskEntity taskEntity)
	{
		this.taskRepository.save(taskEntity);
	}

	@Override
	public Iterable<TaskEntity> find()
	{
		return this.taskRepository.findAll();
	}

	@Override
	public Long count()
	{
		return this.taskRepository.count();
	}

	@Override
	public Optional<TaskEntity> get(final Long taskId)
	{
		return this.taskRepository.findById(taskId);
	}

	@Override
	public boolean exists(final Long taskId)
	{
		return this.taskRepository.existsById(taskId);
	}

	@Override
	public TaskEntity change(final TaskEntity taskEntity)
	{
		Optional<TaskEntity> entity = this.get(taskEntity.getId());

		if (entity.isPresent())
		{
			TaskEntity task = entity.get();

			if (taskEntity.getText() != null && !taskEntity.getText().equals(task.getText())) {
				task.setText(taskEntity.getText());
				task.setChangeAt(taskEntity.getChangeAt());
			}

			if (taskEntity.getState() != null && !taskEntity.getState().equals(task.getState())) {
				if (taskEntity.getState().equals(TaskState.NEW)) {
					task.setCreatedAt(new Date());
					task.setChangeAt(null);
					task.setProcessAt(null);
					task.setCompletedAt(null);
				}

				if (taskEntity.getState().equals(TaskState.PROCESS)) {
					task.setProcessAt(new Date());
				}

				if (taskEntity.getState().equals(TaskState.COMPLETED)) {
					task.setCompletedAt(new Date());

					if (task.getProcessAt() == null) {
						task.setProcessAt(new Date());
					}
				}

				task.setState(taskEntity.getState());
				task.setChangeAt(taskEntity.getChangeAt());
			}

			return this.taskRepository.save(task);
		}

		return taskEntity;
	}

	@Override
	public void delete(final Long taskId)
	{
		this.taskRepository.deleteById(taskId);
	}

	@Override
	public void delete()
	{
		this.taskRepository.deleteAll();
	}
}
