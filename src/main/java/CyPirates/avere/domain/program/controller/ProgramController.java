package CyPirates.avere.domain.program.controller;

import CyPirates.avere.domain.program.dto.ProgramDto;
import CyPirates.avere.domain.program.service.ProgramService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
     @PostMapping(value = "/register",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<ProgramDto.Response> registerProgram(@ModelAttribute ProgramDto.Register request){
          try{
               return ResponseEntity.ok(programService.registerProgram(request));
          } catch (Exception e) {
               e.printStackTrace();
               return ResponseEntity.status(500).build();
          }
     }

     @Operation(summary = "프로그램 수정하기", tags = {"프로그램"})
     @PutMapping(value = "/{programId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<ProgramDto.Response> updateProgram(@PathVariable Long programId,@ModelAttribute ProgramDto.Update request) {
          try{
                return ResponseEntity.ok(programService.updateProgram(programId, request));
             } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(500).build();
          }
     }

     @Operation(summary = "프로그램 삭제하기", tags = {"프로그램"})
     @DeleteMapping("/{programId}")
     public ResponseEntity<Void> deleteProgram(@PathVariable Long programId) {
          programService.deleteProgram(programId);
          return ResponseEntity.noContent().build();
     }
}
