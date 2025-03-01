package br.com.sunshine.stockmanager.api.controllers

import br.com.sunshine.stockmanager.api.adapters.ApiProductAdapter
import br.com.sunshine.stockmanager.api.requests.products.ProductRequest
import br.com.sunshine.stockmanager.api.responses.ProductResponse
import br.com.sunshine.stockmanager.api.responses.ResponseData
import br.com.sunshine.stockmanager.domain.usecase.CreateProductUseCase
import br.com.sunshine.stockmanager.domain.usecase.FindProductUseCase
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("products")
class ProductController(
    private val createProductUseCase: CreateProductUseCase,
    private val findProductsUseCase: FindProductUseCase,

    ) {

    @PostMapping
    fun createProduct(@Valid @RequestBody request: ProductRequest): ResponseData<ProductResponse> {
        val createdProduct = createProductUseCase.create(ApiProductAdapter.requestToModel(request))
        val response = ApiProductAdapter.modelToResponse(createdProduct)

        return ResponseData(response)
    }

    @GetMapping
    fun findAllProducts(): ResponseData<List<ProductResponse>> {
        val productList = findProductsUseCase.findAll()
        val response = productList.map { ApiProductAdapter.modelToResponse(it) }

        return ResponseData(response)
    }

    @GetMapping("/{id}")
    fun findProductById(
        @PathVariable id: Int
    ): ResponseData<ProductResponse> {
        val product = findProductsUseCase.findById(id)
        val response = ApiProductAdapter.modelToResponse(product)

        return ResponseData(response)
    }
}