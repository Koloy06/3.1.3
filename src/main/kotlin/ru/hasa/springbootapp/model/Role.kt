package ru.hasa.springbootapp.model

import javax.persistence.*

@Entity
@Table(name = "roles")
open class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var name: String = ""
)
