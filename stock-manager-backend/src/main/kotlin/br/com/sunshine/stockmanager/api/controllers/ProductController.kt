package br.com.sunshine.stockmanager.api.controllers

import br.com.sunshine.stockmanager.api.adapters.ApiProductAdapter
import br.com.sunshine.stockmanager.api.requests.products.ProductRequest
import br.com.sunshine.stockmanager.api.responses.ProductResponse
import br.com.sunshine.stockmanager.api.responses.ResponseData
import br.com.sunshine.stockmanager.domain.usecase.CreateProductUseCase
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("products")
class ProductController(
    private val createProductUseCase: CreateProductUseCase
) {

    @PostMapping
    fun createProduct(@Valid @RequestBody request: ProductRequest): ResponseData<ProductResponse> {
        val createdProduct = createProductUseCase.create(ApiProductAdapter.requestToModel(request))
        val response = ApiProductAdapter.modelToResponse(createdProduct)

        return ResponseData(response)

    }
}