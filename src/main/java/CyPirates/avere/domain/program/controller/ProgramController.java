package CyPirates.avere.domain.program.controller;

import CyPirates.avere.domain.program.dto.ProgramDto;
import CyPirates.avere.domain.program.service.ProgramService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j(topic = "ProgramController")
@RequiredArgsConstructor
@RequestMapping("/program")
public class ProgramController {
     private final ProgramService programService;

     @Operation(summary = "프로그램 조회하기", tags = {"프로그램"})
     @GetMapping("/{programId}")
     public ResponseEntity<ProgramDto.Response> getProgram(@PathVariable Long programId) {
          return ResponseEntity.ok(programService.getProgram(programId));
     }

     @Operation(summary = "프로그램 등록하기", tags = {"프로그램"})
     @PostMapping("/register")
     public ResponseEntity<ProgramDto.Response> registerProgram(@RequestBody ProgramDto.Register request) {
          return ResponseEntity.ok(programService.registerProgram(request));
     }
}
