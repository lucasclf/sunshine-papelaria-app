package br.com.sunshine.stockmanager.core.entities

import br.com.sunshine.stockmanager.domain.enums.ClientStatusEnum
import jakarta.persistence.Id
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.GenerationType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Column
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "CLIENTS")
class ClientEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int? = null,
    @Column(name = "name")
    val name: String? = null,
    @Column(name = "address")
    val address: String? = null,
    @Column(name = "cep")
    val cep: String? = null,
    @Column(name = "contact")
    val contact: String? = null,
    @Column(name = "status")
    var status: ClientStatusEnum? = null,
    @CreationTimestamp
    @Column(name = "create_date", updatable = false)
    val createDate: LocalDateTime? = null,
    @UpdateTimestamp
    @Column(name = "update_date")
    val updateDate: LocalDateTime? = null
)
