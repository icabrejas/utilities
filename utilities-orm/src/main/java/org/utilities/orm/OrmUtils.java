package org.utilities.orm;

import java.util.Collection;
import java.util.Iterator;

import org.hibernate.AnnotationException;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;

public class OrmUtils {

	private OrmUtils() {
	}

	public static <T extends HibernateEntity> void saveAll(SessionFactory factory, Collection<T> entities) {
		if (!entities.isEmpty()) {
			factory.getCurrentSession()
					.createSQLQuery(multiInsertIntoSQL(factory, entities))
					.executeUpdate();
		}
	}

	private static <T extends HibernateEntity> String multiInsertIntoSQL(SessionFactory factory,
			Collection<T> entities) {
		Iterator<T> it = entities.iterator();
		T entity = it.next();
		Collection<HibernateEntity.Value> values = entity.values();
		StringBuilder sql = new StringBuilder().append("")
				.append("INSERT INTO " + tableName(factory, entity))
				.append(" " + fields(values))
				.append(" VALUES")
				.append(" " + values(values));
		while (it.hasNext()) {
			entity = it.next();
			sql.append(", ")
					.append(values(entity.values()));
		}
		Collection<HibernateEntity.Value> onDuplicatedKey = entity.onDuplicatedKey();
		if (!onDuplicatedKey.isEmpty()) {
			sql.append(" ON DUPLICATE KEY UPDATE")
					.append(" " + update(onDuplicatedKey));
		}
		sql.append(';');
		return sql.toString();
	}

	private static <T extends HibernateEntity> String tableName(SessionFactory factory, T entity) {
		String tableName = null;
		ClassMetadata hibernateMetadata = factory.getClassMetadata(entity.getClass());
		if (hibernateMetadata != null && hibernateMetadata instanceof AbstractEntityPersister) {
			tableName = ((AbstractEntityPersister) hibernateMetadata).getTableName();
		} else {
			throw new AnnotationException("Can't find table name");
		}
		return tableName;
	}

	private static <T extends HibernateEntity> String fields(Collection<HibernateEntity.Value> values) {
		StringBuilder fields = new StringBuilder("(");
		for (HibernateEntity.Value value : values) {
			fields.append(value.field)
					.append(",");
		}
		if (0 < fields.length()) {
			fields.deleteCharAt(fields.length() - 1);
		}
		return fields.append(")")
				.toString();
	}

	private static Object values(Collection<HibernateEntity.Value> values) {
		StringBuilder fields = new StringBuilder("(");
		for (HibernateEntity.Value value : values) {
			if (value.value instanceof String) {
				fields.append("'" + format((String) value.value) + "'")
						.append(",");
			} else {
				fields.append(value.value)
						.append(",");
			}

		}
		if (0 < fields.length()) {
			fields.deleteCharAt(fields.length() - 1);
		}
		return fields.append(")")
				.toString();
	}

	private static Object update(Collection<HibernateEntity.Value> onDuplicatedKey) {
		StringBuilder fields = new StringBuilder();
		for (HibernateEntity.Value value : onDuplicatedKey) {
			if (value.value instanceof String) {
				fields.append(value.field)
						.append("=")
						.append("'" + format((String) value.value) + "'")
						.append(",");
			} else {
				fields.append(value.field)
						.append("=")
						.append(value.value)
						.append(',');
			}

		}
		if (0 < fields.length()) {
			fields.deleteCharAt(fields.length() - 1);
		}
		return fields.toString();
	}

	private static String format(String text) {
		return (text).replaceAll("\"", "\\\\\"");
	}

}
