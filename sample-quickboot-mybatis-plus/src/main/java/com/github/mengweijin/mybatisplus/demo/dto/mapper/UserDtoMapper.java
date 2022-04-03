package com.github.mengweijin.mybatisplus.demo.dto.mapper;

import com.github.mengweijin.mybatisplus.demo.dto.UserDto;
import com.github.mengweijin.mybatisplus.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author mengweijin
 * @date 2022/4/3
 */
@Mapper
public interface UserDtoMapper {

    UserDtoMapper INSTANCE = Mappers.getMapper(UserDtoMapper.class);

    @Mapping(source = "gender", target = "gender")
    UserDto toDTO(User user);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "deleted", target = "deleted")
    User ToEntity(UserDto userDTO);
}

