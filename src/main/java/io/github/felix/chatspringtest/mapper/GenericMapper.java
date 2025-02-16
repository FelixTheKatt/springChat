package io.github.felix.chatspringtest.mapper;

import java.util.List;

public interface GenericMapper <E,D> {
    D toDto (E entity);
    E toEntity (D dto);

    default List<D> toDtoList (List<E> entityList) {
        return entityList.stream().map(this::toDto).toList();
    }

    default List<E> toEntityList(List<D> dtoList) {
        return dtoList.stream().map(this::toEntity).toList();
    }
}
