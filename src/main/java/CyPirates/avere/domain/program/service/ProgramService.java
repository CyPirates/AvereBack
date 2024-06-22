package CyPirates.avere.domain.program.service;

import CyPirates.avere.domain.program.dto.ProgramDto;
import CyPirates.avere.domain.program.entity.ProgramEntity;
import CyPirates.avere.domain.program.repository.ProgramRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j(topic = "ProgramService")
@Transactional
@RequiredArgsConstructor
public class ProgramService {

    private final ProgramRepository programRepository;

    public ProgramDto.Response getProgram(Long programId) {
        ProgramEntity programEntity = programRepository.findById(programId)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로그램이 존재하지 않습니다."));

        return ProgramDto.Response.builder()
                .programId(programEntity.getId())
                .programName(programEntity.getProgramName())
                .programDescription(programEntity.getProgramDescription())
                .build();
    }

    public ProgramDto.Response registerProgram(ProgramDto.Register request) {
        ProgramEntity programEntity = ProgramEntity.builder()
                .programName(request.getProgramName())
                .programDescription(request.getProgramDescription())
                .build();

        ProgramEntity savedProgramEntity = programRepository.save(programEntity);

        return ProgramDto.Response.builder()
                .programId(savedProgramEntity.getId())
                .programName(savedProgramEntity.getProgramName())
                .programDescription(savedProgramEntity.getProgramDescription())
                .build();
    }

    public ProgramDto.Response updateProgram(Long programId, ProgramDto.Update request){
        ProgramEntity programEntity = programRepository.findById(programId)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로그램이 존재하지 않습니다."));

        programEntity.setProgramName(request.getProgramName());
        programEntity.setProgramDescription(request.getProgramDescription());

        ProgramEntity updatedProgramEntity = programRepository.save(programEntity);

        return ProgramDto.Response.builder()
                .programId(updatedProgramEntity.getId())
                .programName(updatedProgramEntity.getProgramName())
                .programDescription(updatedProgramEntity.getProgramDescription())
                .build();
    }

}
