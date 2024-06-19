package CyPirates.avere.domain.program.entity;

import CyPirates.avere.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "program")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramEntity extends BaseEntity {
    @Column(name = "program_name", nullable = false)
    private String programName;

    @Column(name = "program_description", nullable = false)
    private String programDescription;
}
