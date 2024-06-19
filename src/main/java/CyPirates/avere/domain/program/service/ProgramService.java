package CyPirates.avere.domain.program.service;

import CyPirates.avere.domain.program.dto.ProgramDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j(topic = "ProgramService")
@Transactional
@RequiredArgsConstructor
public class ProgramService {

    public ProgramDto.Response getProgram(Long programId) {
        return null;
    }

    public ProgramDto.Response registerProgram(ProgramDto.Register request) {
        return null;
    }
}
