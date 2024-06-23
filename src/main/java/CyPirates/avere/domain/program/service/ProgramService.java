package CyPirates.avere.domain.program.service;

import CyPirates.avere.domain.item.repository.ItemRepository;
import CyPirates.avere.domain.program.dto.ProgramDto;
import CyPirates.avere.domain.program.entity.ProgramEntity;
import CyPirates.avere.domain.program.repository.ProgramRepository;
import CyPirates.avere.global.image.entity.ImageEntity;
import CyPirates.avere.global.image.repository.ImageRepository;
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
    private final ItemRepository itemRepository;
    private final ImageRepository imageRepository;

    public ProgramDto.Response getProgram(Long programId) {
        ProgramEntity programEntity = programRepository.findById(programId)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로그램이 존재하지 않습니다."));

        return ProgramDto.Response.builder()
                .programId(programEntity.getId())
                .programName(programEntity.getProgramName())
                .programDescription(programEntity.getProgramDescription())
                .imageId(programEntity.getImage() != null ? programEntity.getImage().getId() : null)
                .build();
    }

    public ProgramDto.Response registerProgram(ProgramDto.Register request) {
        ImageEntity imageEntity = imageRepository.findById(request.getImageId())
                .orElse(null);

        ProgramEntity programEntity = ProgramEntity.builder()
                .programName(request.getProgramName())
                .programDescription(request.getProgramDescription())
                .image(imageEntity)
                .build();

        ProgramEntity savedProgramEntity = programRepository.save(programEntity);

        return ProgramDto.Response.builder()
                .programId(savedProgramEntity.getId())
                .programName(savedProgramEntity.getProgramName())
                .programDescription(savedProgramEntity.getProgramDescription())
                .imageId(savedProgramEntity.getImage() != null ? savedProgramEntity.getImage().getId() : null)
                .build();
    }

    public ProgramDto.Response updateProgram(Long programId, ProgramDto.Update request){
        ProgramEntity programEntity = programRepository.findById(programId)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로그램이 존재하지 않습니다."));

        ImageEntity imageEntity = imageRepository.findById(request.getImageId())
                .orElse(null);

        programEntity.setProgramName(request.getProgramName());
        programEntity.setProgramDescription(request.getProgramDescription());
        programEntity.setImage(imageEntity);
        ProgramEntity updatedProgramEntity = programRepository.save(programEntity);

        return ProgramDto.Response.builder()
                .programId(updatedProgramEntity.getId())
                .programName(updatedProgramEntity.getProgramName())
                .programDescription(updatedProgramEntity.getProgramDescription())
                .imageId(updatedProgramEntity.getImage() != null ? updatedProgramEntity.getImage().getId() : null)
                .build();
    }

    public void deleteProgram(Long programId) {
        ProgramEntity programEntity = programRepository.findById(programId)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로그램이 존재하지 않습니다."));

        // 먼저 해당 프로그램에 속한 모든 아이템을 삭제
        itemRepository.deleteAll(programEntity.getItems());

        // 그런 다음 프로그램을 삭제
        programRepository.delete(programEntity);
    }

}
