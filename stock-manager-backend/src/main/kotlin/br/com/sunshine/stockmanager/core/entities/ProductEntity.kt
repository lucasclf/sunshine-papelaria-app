package br.com.sunshine.stockmanager.core.entities

import br.com.sunshine.stockmanager.domain.enums.ProductStatusEnum
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "PRODUCTS")
class ProductEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int? = null,
    @Column(name = "name")
    val name: String? = null,
    @Column(name = "price")
    val price: Double? = null,
    @Column(name = "stock")
    val stock: Int? = null,
    @Column(name = "status")
    val status: ProductStatusEnum? = null,
    @CreationTimestamp
    @Column(name = "create_date", updatable = false)
    val createDate: LocalDateTime? = null,
    @UpdateTimestamp
    @Column(name = "update_date")
    val updateDate: LocalDateTime? = null
)