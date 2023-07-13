package ua.foxminded.javaspring.lenskyi.university.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name = "USER", schema = "HOGWARTS")
public class User {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "GROUP_ID", insertable = false, updatable = false)
    private Long groupId;
    @Column(name = "FIRST_NAME")
    @NotNull
    @NotBlank
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "USER_ROLE", schema = "HOGWARTS",
            joinColumns = {@JoinColumn(name = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID")})
    private Collection<Role> roles = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SUBJECT_ID", referencedColumnName = "ID")
    private Subject professorSubject;

    @ManyToOne
    @JoinColumn(name = "GROUP_ID", insertable = false, updatable = false)
    private Group group;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public Subject getProfessorSubject() {
        return professorSubject;
    }

    public void setProfessorSubject(Subject professorSubject) {
        this.professorSubject = professorSubject;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}