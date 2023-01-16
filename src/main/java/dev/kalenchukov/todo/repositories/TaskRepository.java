/*
 * Copyright © 2022 Алексей Каленчуков
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

package dev.kalenchukov.todo.repositories;

import dev.kalenchukov.todo.entities.TaskEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository(value = "taskRepository")
@Transactional
public class TaskRepository implements TaskRepositories
{
	@PersistenceContext
	private EntityManager entityManager;

	@NotNull
	@Override
	public <S extends TaskEntity> S save(@NotNull final S entity)
	{
		this.entityManager.persist(entity);

		return entity;
	}

	@NotNull
	@Override
	public <S extends TaskEntity> Iterable<@NotNull S> saveAll(@NotNull final Iterable<@NotNull S> entities)
	{
		List<S> listEntities = new ArrayList<>();

		for (final S entity : entities) {
			listEntities.add(
				this.save(entity)
			);
		}

		return listEntities;
	}

	@NotNull
	@Override
	public Optional<@NotNull TaskEntity> findById(@NotNull final Long id)
	{
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();;
		CriteriaQuery<TaskEntity> criteriaQuery = criteriaBuilder.createQuery(TaskEntity.class);

		Root<TaskEntity> table = criteriaQuery.from(TaskEntity.class);
		Predicate where = criteriaBuilder.equal(table.get("id"), id);

		criteriaQuery.select(table)
					 .where(where);

		TypedQuery<TaskEntity> query = this.entityManager.createQuery(criteriaQuery);

		try
		{
			return Optional.of(query.getSingleResult());
		}
		catch (NoResultException exception) {
			return Optional.empty();
		}
	}

	@Override
	public boolean existsById(@NotNull final Long id)
	{
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);

		Root<TaskEntity> table = criteriaQuery.from(TaskEntity.class);
		Expression<Long> select = criteriaBuilder.count(table);
		Predicate where = criteriaBuilder.equal(table.get("id"), id);

		criteriaQuery.select(select)
					 .where(where);

		TypedQuery<Long> query = this.entityManager.createQuery(criteriaQuery);

		try {
			return query.getSingleResult() == 1;
		}
		catch (NoResultException exception) {
			return false;
		}
	}

	@NotNull
	@Override
	public Iterable<@NotNull TaskEntity> findAll()
	{
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<TaskEntity> criteriaQuery = criteriaBuilder.createQuery(TaskEntity.class);

		Root<TaskEntity> table = criteriaQuery.from(TaskEntity.class);

		criteriaQuery.select(table);

		TypedQuery<TaskEntity> query = this.entityManager.createQuery(criteriaQuery);

		return query.getResultList();
	}

	@NotNull
	@Override
	public Iterable<@NotNull TaskEntity> findAllById(@NotNull final Iterable<@NotNull Long> ids)
	{
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<TaskEntity> criteriaQuery = criteriaBuilder.createQuery(TaskEntity.class);

		Root<TaskEntity> table = criteriaQuery.from(TaskEntity.class);
		Predicate where = criteriaBuilder.equal(table.get("id"), ids);

		criteriaQuery.select(table).where(where);

		TypedQuery<TaskEntity> query = this.entityManager.createQuery(criteriaQuery);

		return query.getResultList();
	}

	@Override
	public long count()
	{
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);

		Root<TaskEntity> table = criteriaQuery.from(TaskEntity.class);
		Expression<Long> select = criteriaBuilder.count(table);

		criteriaQuery.select(select);

		TypedQuery<Long> query = this.entityManager.createQuery(criteriaQuery);

		try {
			return query.getSingleResult();
		}
		catch (NoResultException exception) {
			return 0L;
		}
	}

	@Override
	public void deleteById(@NotNull final Long id)
	{
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		CriteriaDelete<TaskEntity> criteriaDelete = criteriaBuilder.createCriteriaDelete(TaskEntity.class);

		Root<TaskEntity> table = criteriaDelete.from(TaskEntity.class);
		Predicate where = criteriaBuilder.equal(table.get("id"), id);

		criteriaDelete.where(where);

		this.entityManager.createQuery(criteriaDelete).executeUpdate();
	}

	@Override
	public void delete(@NotNull final TaskEntity entity)
	{
		this.deleteById(entity.getId());
	}

	@Override
	public void deleteAllById(@NotNull final Iterable<? extends Long> ids)
	{
		for (final Long id : ids) {
			this.deleteById(id);
		}
	}

	@Override
	public void deleteAll(@NotNull final Iterable<? extends TaskEntity> entities)
	{
		for (final TaskEntity entity : entities) {
			this.delete(entity);
		}
	}

	@Override
	public void deleteAll()
	{
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		CriteriaDelete<TaskEntity> criteriaDelete = criteriaBuilder.createCriteriaDelete(TaskEntity.class);

		criteriaDelete.from(TaskEntity.class);

		this.entityManager.createQuery(criteriaDelete).executeUpdate();
	}
}
