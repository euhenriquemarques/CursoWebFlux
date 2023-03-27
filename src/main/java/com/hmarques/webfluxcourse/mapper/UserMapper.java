package com.hmarques.webfluxcourse.mapper;


import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import com.hmarques.webfluxcourse.entity.User;
import com.hmarques.webfluxcourse.model.request.UserRequest;
import com.hmarques.webfluxcourse.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE, nullValueCheckStrategy = ALWAYS)
public interface UserMapper {

  @Mapping(target = "id", ignore = true)
  User toEntity(final UserRequest request);

  UserResponse toResponse (final User entity);

  @Mapping(target = "id", ignore = true)
  User toEntity(final UserRequest request, @MappingTarget final User entity);
}
