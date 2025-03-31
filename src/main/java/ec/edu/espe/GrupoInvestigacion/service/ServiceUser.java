package ec.edu.espe.GrupoInvestigacion.service;


import ec.edu.espe.GrupoInvestigacion.dao.DaoUser;
import ec.edu.espe.GrupoInvestigacion.dto.DtoUser;
import ec.edu.espe.GrupoInvestigacion.mapper.UserMapper;
import ec.edu.espe.GrupoInvestigacion.model.ModelUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ServiceUser implements IServiceUser {
    @Autowired
    private DaoUser daoUser;

    @Autowired
    private UserMapper userMapper;

    @Override
    public DtoUser find(Long id) {
        return userMapper.toDto(daoUser.findById(id).orElse(new ModelUser()));
    }

    @Override
    public DtoUser findByUser(String user) {
        return userMapper.toDto(daoUser.findByUser(user).orElse(new ModelUser()));
    }

    @Override
    public List<DtoUser> findAll() {
        return daoUser.findAllEnable()
                .orElse(new ArrayList<>())
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long save(DtoUser dtoUser) {
        ModelUser modelUser = userMapper.toEntity(dtoUser);
        ModelUser createdEntity = daoUser.save(modelUser);
        return createdEntity.getIdUser();
    }


    @Override
    public void update(DtoUser dtoUser){
        ModelUser existingEntity=daoUser.findByIdEnable(dtoUser.getId()).orElse(null);
        if(existingEntity!=null){
            ModelUser updatedEntity=userMapper.toEntity(dtoUser);
            BeanUtils.copyProperties(updatedEntity,existingEntity,getNullPropertyNames(updatedEntity));
            daoUser.save(existingEntity);
        }
    }
    private String[] getNullPropertyNames(Object source) {
        try {
            final BeanInfo beanInfo = Introspector.getBeanInfo(source.getClass());
            final PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            final Set<String> emptyNames = new HashSet<>();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                final Object propertyValue = propertyDescriptor.getReadMethod().invoke(source);
                if (propertyValue == null) {
                    emptyNames.add(propertyDescriptor.getName());
                }
            }
            String[] result = new String[emptyNames.size()];
            return emptyNames.toArray(result);
        } catch (Exception e) {
            return new String[0];
        }
    }

}
