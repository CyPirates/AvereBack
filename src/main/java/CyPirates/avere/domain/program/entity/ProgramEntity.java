package CyPirates.avere.domain.program.entity;

import CyPirates.avere.domain.item.entity.ItemEntity;
import CyPirates.avere.domain.unit.entity.UnitEntity;
import CyPirates.avere.domain.user.entity.User;
import CyPirates.avere.global.entity.BaseEntity;
import CyPirates.avere.global.image.entity.ImageEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

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

    @OneToMany(mappedBy = "program",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<UnitEntity> units;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private ImageEntity image;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
