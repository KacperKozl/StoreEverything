package com.example.storeeverything.data.database;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SHARED", schema = "PUBLIC", catalog = "DB")
@Data
@NoArgsConstructor
public class SharedEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "SHARE_ID", nullable = false)
    private long shareId;
    @Basic
    @Column(name = "NOTE_ID", nullable = true,insertable=false, updatable=false)
    private long noteId;
    @ManyToOne
    @JoinColumn(name = "NOTE_ID", referencedColumnName = "ID")
    private NotesEntity note;
    @Basic
    @Column(name = "SHARETO_ID", nullable = true,insertable=false, updatable=false)
    private long sharetoId;
    @ManyToOne
    @JoinColumn(name = "SHARETO_ID", referencedColumnName = "USER_ID")
    private UsersEntity user;

}
