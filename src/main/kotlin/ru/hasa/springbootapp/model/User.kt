package ru.hasa.springbootapp.model

import javax.persistence.*

@Entity
@Table(name = "users")
open class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "username", unique = true)
    var username: String = "",

    @Column(name = "password")
    var password: String = "",

    @Column(name = "mail", unique = true)
    var mail: String = "",

    @Column(name = "age")
    var age: Byte = 0,

    @ManyToMany
    @JoinTable(
        name = "users_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    var roles: MutableSet<Role> = mutableSetOf()
)
