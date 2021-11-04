package group.purr.purrbackend.service.impl;

import group.purr.purrbackend.dto.MomentDTO;
import group.purr.purrbackend.entity.Moment;
import group.purr.purrbackend.repository.MomentRepository;
import group.purr.purrbackend.service.MomentService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MomentServiceImpl implements MomentService {
    final ModelMapper modelMapper;

    final MomentRepository momentRepository;

    public MomentServiceImpl(ModelMapper modelMapper, MomentRepository momentRepository) {
        this.modelMapper = modelMapper;
        this.momentRepository = momentRepository;
    }

    @Override
    public Long createMoment(MomentDTO momentDTO) {
        Moment moment = modelMapper.map(momentDTO, Moment.class);
        Date currentTime = new Date();
        moment.setCreateTime(currentTime);
        moment.setUpdateTime(currentTime);
        Moment result = momentRepository.saveAndFlush(moment);

        return result.getID();
    }

    @Override
    public List<MomentDTO> getRecentMoments(Pageable pageable) {
        Page<Moment> moments = momentRepository.findAll(pageable);

        List<MomentDTO> result = new ArrayList<>();

        for (Moment moment : moments.getContent()) {
            result.add(modelMapper.map(moment, MomentDTO.class));
        }

        return result;
    }
}
