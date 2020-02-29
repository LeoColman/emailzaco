package br.org.institutoops.emailzaco.service

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Repository
interface UserMailRepository : JpaRepository<UserMail, String>

@Entity
data class UserMail(
    @Column val username: String = "",
    @Column @Id val mail: String = ""
)