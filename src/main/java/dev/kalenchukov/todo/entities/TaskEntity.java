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

package dev.kalenchukov.todo.entities;

import dev.kalenchukov.todo.entities.converters.StateConverter;
import dev.kalenchukov.todo.types.TaskState;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "`tasks`")
public class TaskEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "`task_id`", nullable = false)
	private Long id;

	@Lob
	@Column(name = "`text`", nullable = false)
	private String text;

	@Enumerated(value = EnumType.ORDINAL)
	@Convert(converter = StateConverter.class)
	@Column(name = "`state`", nullable = false)
	private TaskState state = TaskState.NEW;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "`process_at`")
	private Date processAt;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "`completed_at`")
	private Date completedAt;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "`created_at`", nullable = false)
	private Date createdAt = new Date();

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "`change_at`")
	private Date changeAt;
}
