package app.models.entity.user;

import app.models.entity.dailyLog.DailyLog;
import app.models.entity.userProfile.UserProfile;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @Column(nullable = false, unique = true)
    private String username;
    @Column(columnDefinition = "TEXT")
    private String profilePicture;

    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Country country;

    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    @OneToOne(mappedBy = "user")
    private UserProfile userProfile;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<DailyLog> dailyLogs = new ArrayList<>();


}
