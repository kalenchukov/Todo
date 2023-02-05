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

package dev.kalenchukov.todo.controllers.v1;

import dev.kalenchukov.todo.entities.TaskEntity;
import dev.kalenchukov.todo.services.TaskServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController(value = "taskController")
@RequestMapping(path = "/v1/tasks")
public class TaskController
{
	private final TaskServices taskService;

	@Autowired
	public TaskController(@Qualifier(value = "taskService") final TaskServices taskService)
	{
		this.taskService = taskService;
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody final TaskEntity taskEntity)
	{
		this.taskService.create(taskEntity);
	}

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<TaskEntity> find()
	{
		return this.taskService.find();
	}

	@RequestMapping(path = "/count", method = RequestMethod.GET)
	public Long count()
	{
		return this.taskService.count();
	}

	@RequestMapping(path = "/{taskId}", method = RequestMethod.GET)
	public ResponseEntity<TaskEntity> get(@PathVariable(value = "taskId") final Long taskId)
	{
		Optional<TaskEntity> task = this.taskService.get(taskId);

		return task.map(
			(taskEntity) -> ResponseEntity.status(HttpStatus.OK).body(taskEntity)
		).orElseGet(
			() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)
		);
	}

	@RequestMapping(path = "/{taskId}", method = RequestMethod.HEAD)
	public ResponseEntity<?> exists(@PathVariable(value = "taskId") final Long taskId)
	{
		if (this.taskService.exists(taskId)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<TaskEntity> change(@RequestBody final TaskEntity taskEntity)
	{
		return ResponseEntity.status(HttpStatus.OK)
							 .body(this.taskService.change(taskEntity));
	}

	@RequestMapping(path = "/{taskId}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable(value = "taskId") final Long taskId)
	{
		this.taskService.delete(taskId);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete()
	{
		this.taskService.delete();
	}
}
