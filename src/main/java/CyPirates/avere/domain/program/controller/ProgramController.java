package CyPirates.avere.domain.program.controller;

import CyPirates.avere.domain.program.dto.ProgramDto;
import CyPirates.avere.domain.program.service.ProgramService;
import CyPirates.avere.global.jwt.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
     public ResponseEntity<ProgramDto.Response> registerProgram(@ModelAttribute ProgramDto.Register request,
                                                                @AuthenticationPrincipal CustomUserDetails userDetails){
          try{
               return ResponseEntity.ok(programService.registerProgram(request, userDetails.getUsername()));
          } catch (Exception e) {
               e.printStackTrace();
               return ResponseEntity.status(500).build();
          }
     }

     @Operation(summary = "프로그램 수정하기", tags = {"프로그램"})
     @PutMapping(value = "/{programId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<ProgramDto.Response> updateProgram(@PathVariable Long programId,@ModelAttribute ProgramDto.Update request,
                                                              @AuthenticationPrincipal CustomUserDetails userDetails) {
          try{
                return ResponseEntity.ok(programService.updateProgram(programId, request, userDetails.getUsername()));
             } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(500).build();
          }
     }

     @Operation(summary = "프로그램 삭제하기", tags = {"프로그램"})
     @DeleteMapping("/{programId}")
     public ResponseEntity<Void> deleteProgram(@PathVariable Long programId,@AuthenticationPrincipal CustomUserDetails userDetails) {
          programService.deleteProgram(programId,userDetails.getUsername());
          return ResponseEntity.noContent().build();
     }

     @Operation(summary = "로그인한 유저가 등록한 프로그램 조회하기", tags = {"프로그램"})
     @GetMapping("/my-programs")
     public ResponseEntity<List<ProgramDto.Response>> getProgramsByUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
          List<ProgramDto.Response> programs = programService.getProgramsByUser(userDetails.getUsername());
          return ResponseEntity.ok(programs);
     }
}
