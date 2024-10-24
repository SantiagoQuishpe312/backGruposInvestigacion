package ec.edu.espe.GrupoInvestigacion.service;

import ec.edu.espe.GrupoInvestigacion.dao.DaoInvGroup;
import ec.edu.espe.GrupoInvestigacion.dao.DaoLine;
import ec.edu.espe.GrupoInvestigacion.dao.DaoInvGroup_Line;
import ec.edu.espe.GrupoInvestigacion.dto.DtoInvGroup_Line;
import ec.edu.espe.GrupoInvestigacion.mapper.InvGroupMapper;
import ec.edu.espe.GrupoInvestigacion.mapper.InvGroup_LineMapper;
import ec.edu.espe.GrupoInvestigacion.mapper.LineMapper;
import ec.edu.espe.GrupoInvestigacion.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceInvGroupLine implements IServiceInvGroup_Line {
    @Autowired
    private DaoInvGroup_Line daoInvGroupLine;
    @Autowired
    private InvGroupMapper invGroupMapper;
    @Autowired
    private LineMapper lineMapper;
    @Autowired
    private InvGroup_LineMapper invGroupLineMapper;
    @Autowired
    private DaoLine daoLine;
    @Autowired
    private DaoInvGroup daoInvGroup;
    @Override
    public List<DtoInvGroup_Line> find(Long id) {
        return daoInvGroupLine.findByIdEnable(id)
                .orElse(new ArrayList<>())
                .stream()
                .map(invGroupLineMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DtoInvGroup_Line> findAll() {
        return daoInvGroupLine.findAllEnable()
                .orElse(new ArrayList<>())
                .stream()
                .map(invGroupLineMapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public Long save(DtoInvGroup_Line dtoInvGroupLine){
        Long lineId=dtoInvGroupLine.getIdLinea();
        Long GroupId=dtoInvGroupLine.getIdGrupo();
        ModelLine modelLine=daoLine.findById(lineId).orElse(new ModelLine());
        ModelInvGroup modelInvGroup=daoInvGroup.findByIdEnable(GroupId).orElse(new ModelInvGroup());
        ModelInvGroup_Line modelInvGroupLine=invGroupLineMapper.toEntity(dtoInvGroupLine);
        ModelInvGroup_Line_Id id=new ModelInvGroup_Line_Id();
        id.setModelLine(modelLine);
        id.setModelInvGroup(modelInvGroup);
        modelInvGroupLine.setId(id);
        daoInvGroupLine.save(modelInvGroupLine);
        return lineId;
    }
    /*@Override
    public DtoReqGetLine findByReq(Long id){
        Optional<List<ModelLine>> modelLines= daoInvGroupLine.findLine(id);
        DtoReqGetLine dtoReqGetLine=new DtoReqGetLine();
        if(!modelLines.isEmpty()){
            dtoReqGetLine.setCreacion(daoCreationReq.findByIdEnable(id).map(creationReqMapper::toDto).orElse(new DtoCreationReq()));
            dtoReqGetLine.setLinea(modelLines.get().stream().map(lineMapper::toDto).collect(Collectors.toList()));
            return dtoReqGetLine;
        }
        throw new RuntimeException("No se encontraron Areas Academicos para el Formulario");
    }*/
}
